package org.texttechnologylab.practical.sose26.christian;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UIMA Writer-Komponente zum Serialisieren des CAS im XMI-Format.
 */
public class XmiWriter extends JCasAnnotator_ImplBase {

    public static final String PARAM_OUTPUT_DIRECTORY = "OutputDirectory";
    @ConfigurationParameter(name = PARAM_OUTPUT_DIRECTORY, description = "Verzeichnis für die XMI-Ausgabe", defaultValue = "src/main/resources/xmi_output/")
    private String outputDirectory;

    // Ein simpler Counter, um eindeutige Dateinamen zu generieren
    private static final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        File outDir = new File(outputDirectory);
        if (!outDir.exists()) {
            outDir.mkdirs(); // Erstellt das Verzeichnis, falls es nicht existiert
        }

        // Erzeugt einen Dateinamen, z.B. speech_1.xmi, speech_2.xmi
        File outFile = new File(outDir, "speech_" + counter.getAndIncrement() + ".xmi");

        try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
            // Nutzt den UIMA-Standard-Serializer für das XMI-Format
            XmiCasSerializer.serialize(jCas.getCas(), outputStream);
            System.out.println("   -> CAS erfolgreich als XMI exportiert: " + outFile.getName());
        } catch (IOException | SAXException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}