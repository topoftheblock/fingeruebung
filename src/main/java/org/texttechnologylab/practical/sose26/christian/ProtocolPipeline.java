package org.texttechnologylab.practical.sose26.christian;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import org.apache.uima.cas.SerialFormat;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.CasIOUtils;
import org.hucompute.textimager.uima.type.Sentiment;
import org.hucompute.textimager.uima.type.category.CategoryCoveredTagged;
import org.texttechnologylab.ppr.db.DatabaseConnection;
import org.texttechnologylab.ppr.model.interfaces.Rede;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Führt die NLP-Analyse für alle Reden durch.
 * Angepasst auf das dynamische Video-Namensschema (ID + Zusatz + .mp4).
 */
public class ProtocolPipeline {

    private final DUUIConnection duui;
    private JCas jcas;

    /**
     * Konstruktor erwartet nun die Liste der verfügbaren Videodateien.
     */
    public NLPPipeline( ) {
        this.duui = new DUUIConnection();
    }


    private void processRede(Rede rede) throws Exception {
        boolean analysisRequired = true;
        String existingXmi = db.getCasXmi(rede.getId());

        if (existingXmi != null && !existingXmi.isEmpty()) {
            try {
                jcas = rede.toCAS();
                jcas.reset();
                try (ByteArrayInputStream bais = new ByteArrayInputStream(existingXmi.getBytes("UTF-8"))) {
                    CasIOUtils.load(bais, jcas.getCas());
                }
                if (JCasUtil.select(jcas, POS.class).size() > 0) {
                    analysisRequired = false;
                }
            } catch (Exception e) {
                jcas = null;
                analysisRequired = true;
            }
        }

        if (jcas == null) {
            jcas = rede.toCAS();
        }

        String text = jcas.getDocumentText();
        if (text == null || text.isEmpty()) return;

        boolean casChanged = false;

        if (analysisRequired && duui.isAvailable()) {
            duui.process(jcas);
            casChanged = true;
        }


        Map<String, Integer> neMap = new HashMap<>();
        for (NamedEntity ne : JCasUtil.select(jcas, NamedEntity.class)) {
            String value = ne.getValue();
            if (value != null) neMap.merge(value, 1, Integer::sum);
        }

        double sentiment = 0.0;
        var sentiments = JCasUtil.select(jcas, Sentiment.class);
        if (!sentiments.isEmpty()) {
            sentiment = sentiments.iterator().next().getSentiment();
        }

        Map<String, Map<String, Object>> uniqueTopics = new HashMap<>();
        for (CategoryCoveredTagged cat : JCasUtil.select(jcas, CategoryCoveredTagged.class)) {
            String topicName = cat.getValue();
            if (topicName == null) continue;
            topicName = topicName.trim();
            if (topicName.isEmpty()) continue;

            double score = cat.getScore();
            if (!uniqueTopics.containsKey(topicName) || score > (Double) uniqueTopics.get(topicName).get("probability")) {
                Map<String, Object> m = new HashMap<>();
                m.put("name", topicName);
                m.put("probability", score);
                m.put("segments", List.of(Map.of("index", 0, "intensity", score)));
                uniqueTopics.put(topicName, m);
            }
        }

        List<Map<String, Object>> topicStats = uniqueTopics.values().stream()
                .sorted((m1, m2) -> Double.compare((Double)m2.get("probability"), (Double)m1.get("probability")))
                .limit(5)
                .collect(Collectors.toList());

    }
}