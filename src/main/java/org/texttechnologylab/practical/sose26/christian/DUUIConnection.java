package org.texttechnologylab.practical.sose26.christian;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import org.apache.uima.jcas.JCas;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIUIMADriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.lua.DUUILuaContext;

public class DUUIConnection {
    private DUUIComposer composer;
    private boolean available = false;

    public DUUIConnection() {
        try {
            DUUILuaContext ctx = new DUUILuaContext().withJsonLibrary();
            composer = new DUUIComposer()
                    .withLuaContext(ctx)
                    .withSkipVerification(true)
                    .withWorkers(4);

            DUUIUIMADriver uimaDriver = new DUUIUIMADriver();
            DUUIRemoteDriver remoteDriver = new DUUIRemoteDriver();
            composer.addDriver(uimaDriver, remoteDriver);

            // Komponenten hinzufügen
            composer.add(new DUUIRemoteDriver.Component("http://spacy.service.component.duui.texttechnologylab.org")
                    .build());

            composer.add(new DUUIRemoteDriver.Component("http://parlbert.service.component.duui.texttechnologylab.org")
                    .withParameter("selection", Sentence.class.getName())
                    .build());

            available = true;
        } catch (Throwable e) {
            e.printStackTrace();
            available = false;
        }
    }

    public void process(JCas jcas) throws Exception {
        if (available && composer != null) {
            composer.run(jcas);
        }
    }

    public boolean isAvailable() {
        return available;

    }
}