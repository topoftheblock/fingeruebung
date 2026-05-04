package org.texttechnologylab.practical.sose26.christian;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

public class ProtocolPipeline {

    public static void main(String[] args) throws Exception {

        // 4. Run the Pipeline
        System.out.println("Starting pipeline...");
        SimplePipeline.runPipeline(reader, duuiConnection, writer);
        System.out.println("Pipeline finished successfully.");
    }
}