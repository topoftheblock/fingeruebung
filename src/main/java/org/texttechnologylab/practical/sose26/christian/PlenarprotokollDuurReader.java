package org.texttechnologylab.practical.sose26.christian;

import org.texttechnologylab.practical.sose26.christian.type.Redeinhalt;
import org.texttechnologylab.practical.sose26.christian.type.Redner;
import org.texttechnologylab.practical.sose26.christian.type.Sitzungsinformationen;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Docker Unified UIMA Reader (DUUR) für Plenarprotokolle des Deutschen Bundestages.
 * Liest XML-Dateien ein, nutzt Jsoup zum Parsen und erzeugt pro <rede>-Element
 * ein eigenes CAS-Objekt inklusive der TypeSystem-Annotationen.
 */
public class PlenarprotokollDuurReader extends JCasCollectionReader_ImplBase {

    public static final String PARAM_INPUT_DIRECTORY = "InputDirectory";
    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, description = "Verzeichnis mit den XML-Plenarprotokollen des Bundestags")
    private String inputDirectory;

    private File[] protocolFiles;
    private int currentFileIndex = 0;

    // Zwischenspeicher für die extrahierten Reden eines XML-Protokolls
    private Queue<ParsedSpeechData> speechQueue = new LinkedList<>();

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        File dir = new File(inputDirectory);

        if (dir.exists() && dir.isDirectory()) {
            // Nur .xml Dateien einlesen
            protocolFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".xml"));
        }

        if (protocolFiles == null) {
            protocolFiles = new File[0];
        }
    }

    @Override
    public boolean hasNext() throws CollectionException {
        // Solange die Queue leer ist und noch XML-Dateien übrig sind: nächste Datei parsen
        while (speechQueue.isEmpty() && currentFileIndex < protocolFiles.length) {
            File nextFile = protocolFiles[currentFileIndex++];
            try {
                List<ParsedSpeechData> speeches = parseProtocolToSpeeches(nextFile);
                speechQueue.addAll(speeches);
            } catch (IOException e) {
                throw new CollectionException(e);
            }
        }
        return !speechQueue.isEmpty();
    }

    @Override
    public void getNext(JCas jCas) throws CollectionException {
        if (hasNext()) {
            ParsedSpeechData currentData = speechQueue.poll();

            // 1. Dokumententext setzen
            String documentText = currentData.text;
            jCas.setDocumentText(documentText);
            jCas.setDocumentLanguage("de");

            int textLength = documentText.length();

            // 2. Sitzungsinformation (erstreckt sich über das ganze Dokument)
            Sitzungsinformationen sitzung = new Sitzungsinformationen(jCas);
            sitzung.setBegin(0);
            sitzung.setEnd(textLength);
            sitzung.setWahlperiode(currentData.wahlperiode);
            sitzung.setSitzungsNummer(currentData.sitzungsNummer);
            sitzung.setDatum(currentData.datum);
            sitzung.addToIndexes();

            // 3. Redner-Annotation
            Redner redner = new Redner(jCas);
            redner.setBegin(0);
            redner.setEnd(textLength);
            redner.setVorname(currentData.vorname);
            redner.setNachname(currentData.nachname);
            redner.setPartei(currentData.partei);
            redner.setFunktion(currentData.funktion);
            redner.addToIndexes();

            // 4. Rede-Annotation (verlinkt den Redner)
            Redeinhalt rede = new Redeinhalt(jCas);
            rede.setBegin(0);
            rede.setEnd(textLength);
            rede.setRedner(redner);
            rede.setTagesordnungspunkt(currentData.tagesordnungspunkt);
            rede.setRedeTyp(currentData.redeTyp);
            rede.addToIndexes();
        }
    }

    @Override
    public Progress[] getProgress() {
        return new Progress[]{
                new ProgressImpl(currentFileIndex, protocolFiles.length, Progress.ENTITIES)
        };
    }

    /**
     * Parst eine Bundestags-XML-Datei mittels Jsoup und extrahiert alle Reden.
     */
    private List<ParsedSpeechData> parseProtocolToSpeeches(File xmlFile) throws IOException {
        List<ParsedSpeechData> extractedSpeeches = new LinkedList<>();

        // XML mit Jsoup einlesen (Parser.xmlParser() ist wichtig für korrekte XML-Verarbeitung)
        Document doc = Jsoup.parse(xmlFile, "UTF-8", "", Parser.xmlParser());

        // Globale Sitzungsinformationen aus dem XML-Kopf extrahierten
        int wahlperiode = parseInteger(doc.select("wahlperiode").text(), 0);
        int sitzungsNummer = parseInteger(doc.select("sitzungsnr").text(), 0);
        String datum = doc.select("datum").attr("date"); // Alternativ: .text() je nach XML-Format

        // Alle Tagesordnungspunkte (TOPs) durchlaufen, um den Kontext der Rede zu kennen
        Elements tagesordnungspunkte = doc.select("tagesordnungspunkt");
        for (Element top : tagesordnungspunkte) {
            String topTitel = top.attr("top-id"); // Oder top.select("p.titel").text()

            // Alle Reden innerhalb dieses Tagesordnungspunkts durchlaufen
            Elements reden = top.select("rede");
            for (Element redeKnoten : reden) {
                ParsedSpeechData speechData = new ParsedSpeechData();
                speechData.wahlperiode = wahlperiode;
                speechData.sitzungsNummer = sitzungsNummer;
                speechData.datum = datum;
                speechData.tagesordnungspunkt = topTitel;

                // Redner-Metadaten extrahieren
                Element rednerKnoten = redeKnoten.selectFirst("redner");
                if (rednerKnoten != null) {
                    speechData.vorname = rednerKnoten.select("vorname").text();
                    speechData.nachname = rednerKnoten.select("nachname").text();
                    speechData.partei = rednerKnoten.select("fraktion").text();
                    speechData.funktion = rednerKnoten.select("rolle").text();
                }

                // Redeinhalt extrahieren (Text aus allen <p> und <kommentar> Tags)
                // Jsoup's .text() Methode bereinigt automatisch HTML/XML-Tags und liefert reinen Text
                speechData.text = redeKnoten.text();
                speechData.redeTyp = "Plenarrede"; // Standardwert

                extractedSpeeches.add(speechData);
            }
        }

        return extractedSpeeches;
    }

    /**
     * Sicheres Parsen von Integern (vermeidet NumberFormatException bei leeren XML-Knoten)
     */
    private int parseInteger(String value, int defaultValue) {
        try {
            return value != null && !value.isBlank() ? Integer.parseInt(value.trim()) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Internes DTO zur Zwischenspeicherung der extrahierten Daten.
     */
    private static class ParsedSpeechData {
        public int wahlperiode;
        public int sitzungsNummer;
        public String datum;

        public String vorname = "";
        public String nachname = "";
        public String partei = "";
        public String funktion = "";

        public String text = "";
        public String tagesordnungspunkt = "";
        public String redeTyp = "";
    }
}