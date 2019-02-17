package org.semanticweb.owlapi6.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.semanticweb.owlapi6.io.OWLParserFactory;
import org.semanticweb.owlapi6.utilities.PriorityCollection;

/**
 * Class to build HTTP accept headers.
 */
public class AcceptHeaderBuilder {
    private AcceptHeaderBuilder() {}

    /**
     * @param parsers parsers to use
     * @return accept headers
     */
    public static String headersFromParsers(PriorityCollection<OWLParserFactory> parsers) {
        Map<String, TreeSet<Integer>> map = new HashMap<>();
        parsers.forEach(p -> addToMap(map, p.getMIMETypes()));
        return map.entrySet().stream().sorted(AcceptHeaderBuilder::compare)
            .map(AcceptHeaderBuilder::toAcceptString).collect(Collectors.joining(", "));
    }

    private static void addToMap(Map<String, TreeSet<Integer>> map, List<String> mimes) {
        // The map will contain all mime types with their position in all lists mentioning them; the
        // smallest position first
        for (int i = 0; i < mimes.size(); i++) {
            map.computeIfAbsent(mimes.get(i), k -> new TreeSet<>()).add(Integer.valueOf(i + 1));
        }
    }

    private static String toAcceptString(Map.Entry<String, TreeSet<Integer>> e) {
        return String.format("%s; q=%.1f", e.getKey(),
            Double.valueOf(1D / e.getValue().first().intValue()));
    }

    private static int compare(Map.Entry<String, TreeSet<Integer>> a,
        Map.Entry<String, TreeSet<Integer>> b) {
        return a.getValue().first().compareTo(b.getValue().first());
    }
}
