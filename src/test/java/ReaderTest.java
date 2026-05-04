import io.kubernetes.client.openapi.apis.CoreV1Api;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIUIMADriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.lua.DUUILuaContext;
import org.texttechnologylab.practical.sose26.christian.PlenarprotokollDuurReader;
import org.texttechnologylab.practical.sose26.christian.SpeechExportWriter;
import org.texttechnologylab.practical.sose26.christian.XmiWriter;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ReaderTest {

    private static DUUIComposer composer = null;

    @BeforeAll
    public static void init() throws URISyntaxException, IOException {
        DUUILuaContext ctx = new DUUILuaContext().withJsonLibrary();

        composer = new DUUIComposer()
                .withLuaContext(ctx)
                .withSkipVerification(true)
                .withWorkers(4);

        DUUIUIMADriver uimaDriver = new DUUIUIMADriver();
        DUUIRemoteDriver remoteDriver = new DUUIRemoteDriver();

        composer.addDriver(uimaDriver, remoteDriver);
    }

    @Test
    @DisplayName("Simpler Test")
    public void test1() throws Exception {

        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                PlenarprotokollDuurReader.class,
                PlenarprotokollDuurReader.PARAM_INPUT_DIRECTORY, "data/input/"
        );


        AnalysisEngineDescription xmiWriter = AnalysisEngineFactory.createEngineDescription(
                XmiWriter.class,
                XmiWriter.PARAM_OUTPUT_DIRECTORY, "data/output/xmi/"
        );


        AnalysisEngineDescription speechWriter = AnalysisEngineFactory.createEngineDescription(
                SpeechExportWriter.class,
                SpeechExportWriter.PARAM_OUTPUT_DIRECTORY, "data/output/speech/"
        );

        composer.resetPipeline();

        composer.add(new DUUIUIMADriver.Component(xmiWriter).build());
        composer.add(new DUUIUIMADriver.Component(speechWriter).build());

        composer.run(reader);

    }

}
