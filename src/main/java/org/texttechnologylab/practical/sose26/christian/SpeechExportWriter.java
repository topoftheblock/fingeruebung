package org.texttechnologylab.practical.sose26.christian;

import org.texttechnologylab.practical.sose26.christian.type.Redeinhalt;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import org.hucompute.textimager.uima.type.category.CategoryCoveredTagged;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UIMA Writer-Komponente zum Exportieren der NLP-Ergebnisse.
 * Extrahiert die häufigsten Named Entities und die Top 3 Topics pro Rede.
 */
public class SpeechExportWriter extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        // 1. Rede- und Redner-Metadaten abrufen
        // Da wir pro JCas genau eine Rede haben, reicht es, das erste Element zu nehmen.
        Collection<Redeinhalt> reden = JCasUtil.select(jCas, Redeinhalt.class);
        if (reden.isEmpty()) {
            return; // Keine Rede im CAS gefunden
        }

        Redeinhalt rede = reden.iterator().next();
        String rednerName = rede.getRedner() != null ?
                rede.getRedner().getVorname() + " " + rede.getRedner().getNachname() : "Unbekannt";
        String partei = rede.getRedner() != null ? rede.getRedner().getPartei() : "?";

        System.out.println("=====================================================");
        System.out.println("Rede von: " + rednerName + " (" + partei + ") | TOP: " + rede.getTagesordnungspunkt());
        System.out.println("=====================================================");

        // 2. Häufigste Named Entities (spaCy) ermitteln
        // Zählt das Vorkommen jedes Entity-Strings in einer Map
        Map<String, Integer> entityCounts = new HashMap<>();
        for (NamedEntity ne : JCasUtil.select(jCas, NamedEntity.class)) {
            String entityText = ne.getCoveredText().trim();

            if (entityText.length() > 1) { // Ignoriere Rauschen (einzelne Satzzeichen etc.)
                entityCounts.put(entityText, entityCounts.getOrDefault(entityText, 0) + 1);
            }
        }

        System.out.println("➤ Top 5 Häufigste Named Entities:");
        if (entityCounts.isEmpty()) {
            System.out.println("   - Keine Entitäten gefunden.");
        } else {
            entityCounts.entrySet().stream()
                    // Sortiere absteigend nach Häufigkeit (Value der Map)
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(5) // Nimm nur die Top 5
                    .forEach(e -> System.out.println("   - " + e.getKey() + " (" + e.getValue() + "x)"));
        }

        // 3. Die drei dominierenden Topics (ParlBERT) extrahieren
        System.out.println("\n➤ Top 3 Dominierende Topics:");
        List<CategoryCoveredTagged> topics = new ArrayList<>(JCasUtil.select(jCas, CategoryCoveredTagged.class));

        if (topics.isEmpty()) {
            System.out.println("   - Keine Topics gefunden.");
        } else {
            topics.stream()
                    // Sortiere absteigend nach dem Wahrscheinlichkeits-Score von ParlBERT
                    .sorted(Comparator.comparingDouble(CategoryCoveredTagged::getScore).reversed())
                    .limit(3) // Nimm nur die Top 3
                    .forEach(t -> {
                        // Score formatieren für bessere Lesbarkeit (z.B. 0.8543 -> 85.43%)
                        String formattedScore = String.format("%.2f%%", t.getScore() * 100);
                        System.out.println("   - " + t.getValue() + " (Konfidenz: " + formattedScore + ")");
                    });
        }
        System.out.println("\n");
    }
}