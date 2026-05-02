package org.texttechnologylab.practical.sose26.christian;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

/**
 * Die ProtocolPipeline orchestriert den NLP-Ablauf für das Projekt "Fingeruebung".
 * Sie liest Plenarprotokolle ein, analysiert sie via DUUI und exportiert die Ergebnisse.
 */
public class ProtocolPipeline {

    public static void main(String[] args) {
        try {
            System.out.println("--- Initialisiere UIMA-Pipeline ---");

            // 1. Reader: Einlesen der XML-Protokolle und Aufteilung in Reden-Ebene CAS-Objekte
            // Nutzt den PlenarprotokollDuurReader aus Ihrem Projekt
            CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                    PlenarprotokollDuurReader.class,
                    PlenarprotokollDuurReader.PARAM_INPUT_DIRECTORY, "data/input/"
            );

            // 2. DUUI Schritt 1: spaCy (Basis-NLP: Token, POS, Named Entities)
            // Nutzt Ihre DUUIConnection-Klasse als Analysis Engine
            AnalysisEngineDescription spacyAE = AnalysisEngineFactory.createEngineDescription(
                    DUUIConnection.class,
                    "endpoint", "http://duui.texttechnologylab.org/spacy",
                    "model", "de_core_news_lg"
            );

            // 3. DUUI Schritt 2: ParlBERT (Politisches Topic Modeling)
            AnalysisEngineDescription parlBertAE = AnalysisEngineFactory.createEngineDescription(
                    DUUIConnection.class,
                    "endpoint", "http://duui.texttechnologylab.org/parlbert"
            );

            // 4. Export-Writer: Ausgabe der häufigsten Entities und Top 3 Topics in der Konsole
            AnalysisEngineDescription consoleExportWriter = AnalysisEngineFactory.createEngineDescription(
                    SpeechExportWriter.class
            );

            // 5. XMI-Writer: Serialisierung des Analyseergebnisses im XMI-Format
            // Verwendet den konfigurierten Pfad für die XMI-Ausgabe
            AnalysisEngineDescription xmiWriter = AnalysisEngineFactory.createEngineDescription(
                    XmiWriter.class,
                    XmiWriter.PARAM_OUTPUT_DIRECTORY, "data/output/xmi/"
            );

            System.out.println("--- Starte Verarbeitung der Dokumente ---");

            // 6. Pipeline ausführen
            SimplePipeline.runPipeline(
                    reader,
                    spacyAE,
                    parlBertAE,
                    consoleExportWriter,
                    xmiWriter
            );

            System.out.println("--- Verarbeitung erfolgreich abgeschlossen ---");

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Fehler während der Pipeline-Ausführung:");
            e.printStackTrace();
        }
    }
}