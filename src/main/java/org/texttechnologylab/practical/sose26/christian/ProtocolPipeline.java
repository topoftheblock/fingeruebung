package org.texttechnologylab.practical.sose26.christian;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;

public class ProtocolPipeline {

    public static void main(String[] args) {
        try {
            System.out.println("Starting Protocol Pipeline...");

            // 1. Initialize the Reader (reading from data/input/ as seen in your structure)
            CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                    PlenarprotokollDuurReader.class,
                    PlenarprotokollDuurReader.PARAM_INPUT_DIRECTORY, "data/input/"
            );

            /**
             * Das ergibt keinen Sinn =)
             */
            // 2. Initialize the DUUI NLP Processor
            // (Assuming DUUIConnection is implemented as an Analysis Engine)
//            AnalysisEngineDescription duuiProcessor = AnalysisEngineFactory.createEngineDescription(
//                    DUUIConnection.class
//            );

            // 3. Initialize the Writers
            AnalysisEngineDescription xmiWriter = AnalysisEngineFactory.createEngineDescription(
                    XmiWriter.class,
                    XmiWriter.PARAM_OUTPUT_DIRECTORY, "data/output/xmi/"
            );

            /**
             * Hat keine Parameter
             */
            AnalysisEngineDescription speechWriter = AnalysisEngineFactory.createEngineDescription(
                    SpeechExportWriter.class,
                    SpeechExportWriter.PARAM_OUTPUT_DIRECTORY, "data/output/speech/"
            );

            // 4. Run the Pipeline
            SimplePipeline.runPipeline(
                    reader,
//                    duuiProcessor,
                    xmiWriter,
                    speechWriter
            );

            System.out.println("Pipeline executed successfully!");

        } catch (Exception e) {
            System.err.println("An error occurred during pipeline execution:");
            e.printStackTrace();
        }
    }
}
