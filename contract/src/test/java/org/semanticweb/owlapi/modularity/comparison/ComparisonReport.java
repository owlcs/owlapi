package org.semanticweb.owlapi.modularity.comparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

/**
 * Collects the outcome of every (implementation, case) pair of one comparison
 * suite and writes a scoreboard to {@code target/modularity-comparison}.
 *
 * <p>Assertion policy: a deviation of a <em>reference</em> implementation (the
 * {@code org.semanticweb.owlapi.modularity} code that is proposed to be kept)
 * fails the test. A deviation of a deprecated implementation aborts the test,
 * so that it is reported as skipped together with the reason and the build
 * stays green. An explicit {@link UnsupportedOperationException} is recorded as
 * {@link Outcome#UNSUPPORTED} and never fails the build: it is a documented
 * limitation, not a wrong answer. Run with
 * {@code -Dmodularity.comparison.strict=true} to make every deviation a failure.</p>
 */
final class ComparisonReport {

    /** Outcome of one implementation on one case. */
    enum Outcome {
        /** Result agrees with the expectation. */
        PASS("pass"),
        /** Locality claimed for a non-local axiom, or a needed axiom is missing. */
        UNSOUND("UNSOUND"),
        /** Non-locality claimed for a local axiom, or superfluous axioms. */
        INCOMPLETE("incomplete"),
        /** A structural invariant (partition, ideal, order, idempotence) is broken. */
        BROKEN("BROKEN"),
        /** The implementation threw an exception. */
        ERROR("ERROR"),
        /** The implementation explicitly rejected the input as unsupported. */
        UNSUPPORTED("unsupported");

        final String label;

        Outcome(String label) {
            this.label = label;
        }
    }

    static final boolean STRICT = Boolean.getBoolean("modularity.comparison.strict");

    private static final class Entry {
        final String implementation;
        final String caseId;
        final String category;
        final Outcome outcome;
        final String detail;

        Entry(String implementation, String caseId, String category, Outcome outcome,
            String detail) {
            this.implementation = implementation;
            this.caseId = caseId;
            this.category = category;
            this.outcome = outcome;
            this.detail = detail;
        }
    }

    private final String name;
    private final String title;
    private final List<String> implementations = new ArrayList<>();
    private final ConcurrentLinkedQueue<Entry> entries = new ConcurrentLinkedQueue<>();
    /** table -> row -> column -> value; insertion ordered. */
    private final Map<String, Map<String, Map<String, String>>> metrics = new LinkedHashMap<>();
    private final Map<String, String> notes = new LinkedHashMap<>();

    ComparisonReport(String name, String title) {
        this.name = name;
        this.title = title;
    }

    /** Registers an implementation so that it shows up even if it has no results. */
    void implementation(String implementation) {
        synchronized (implementations) {
            if (!implementations.contains(implementation)) {
                implementations.add(implementation);
            }
        }
    }

    /**
     * Records an outcome and applies the assertion policy.
     *
     * @param reference whether the implementation is a reference implementation
     */
    void record(String implementation, boolean reference, String caseId, String category,
        Outcome outcome, String detail) {
        implementation(implementation);
        entries.add(new Entry(implementation, caseId, category, outcome,
            detail == null ? "" : detail));
        if (outcome == Outcome.PASS) {
            return;
        }
        String message = implementation + " / " + caseId + ": " + outcome.label
            + (detail == null || detail.isEmpty() ? "" : " - " + detail);
        if (STRICT || reference && outcome != Outcome.UNSUPPORTED) {
            Assertions.fail(message);
        }
        // Explicitly rejected input is a documented limitation, not a wrong answer.
        Assumptions.abort((reference ? "documented limitation: "
            : "deprecated implementation deviates: ") + message);
    }

    /** Records a thrown exception; unsupported-operation exceptions are recorded as such. */
    void recordFailure(String implementation, boolean reference, String caseId,
        String category, Throwable failure) {
        Outcome outcome = failure instanceof UnsupportedOperationException ? Outcome.UNSUPPORTED
            : Outcome.ERROR;
        record(implementation, reference, caseId, category, outcome,
            failure.getClass().getSimpleName() + ": "
                + Vocabulary.render(Objects.toString(failure.getMessage(), "")));
    }

    /** Records a descriptive value (e.g. a module size) for the metrics tables. */
    void metric(String table, String row, String column, Object value) {
        synchronized (metrics) {
            metrics.computeIfAbsent(table, t -> new LinkedHashMap<>())
                .computeIfAbsent(row, r -> new LinkedHashMap<>())
                .put(column, String.valueOf(value));
        }
    }

    /** Adds an explanatory paragraph below a metrics table. */
    void note(String table, String text) {
        synchronized (metrics) {
            notes.put(table, text);
        }
    }

    private void writeMetrics(StringBuilder md) {
        synchronized (metrics) {
            for (Map.Entry<String, Map<String, Map<String, String>>> table : metrics
                .entrySet()) {
                md.append("\n## ").append(table.getKey()).append("\n\n");
                List<String> columns = new ArrayList<>();
                table.getValue().values().forEach(row -> row.keySet().forEach(c -> {
                    if (!columns.contains(c)) {
                        columns.add(c);
                    }
                }));
                md.append("| |");
                columns.forEach(c -> md.append(' ').append(c).append(" |"));
                md.append("\n|---|");
                columns.forEach(c -> md.append("---|"));
                md.append('\n');
                table.getValue().forEach((row, values) -> {
                    md.append("| ").append(escape(row)).append(" |");
                    columns.forEach(c -> md.append(' ')
                        .append(escape(values.getOrDefault(c, "")))
                        .append(" |"));
                    md.append('\n');
                });
                if (notes.containsKey(table.getKey())) {
                    md.append('\n').append(notes.get(table.getKey())).append('\n');
                }
            }
        }
    }

    /** Writes {@code <name>.md} and {@code <name>.tsv}. Called from {@code @AfterAll}. */
    void write() {
        List<Entry> all = new ArrayList<>(entries);
        all.sort(Comparator.comparing((Entry e) -> e.category).thenComparing(e -> e.caseId)
            .thenComparing(e -> implementations.indexOf(e.implementation)));
        StringBuilder md = new StringBuilder();
        md.append("# ").append(title).append("\n\n");
        md.append("Generated by `").append(name).append("`. Strict mode: ").append(STRICT)
            .append(".\n\n");
        List<String> categories =
            all.stream().map(e -> e.category).distinct().sorted().collect(Collectors.toList());
        md.append("## Scoreboard\n\n");
        md.append("| Implementation | Category | Cases | pass | UNSOUND | incomplete | BROKEN"
            + " | ERROR | unsupported |\n");
        md.append("|---|---|---:|---:|---:|---:|---:|---:|---:|\n");
        for (String impl : implementations) {
            for (String category : categories) {
                Map<Outcome, Integer> counts = new EnumMap<>(Outcome.class);
                int total = 0;
                for (Entry e : all) {
                    if (e.implementation.equals(impl) && e.category.equals(category)) {
                        counts.merge(e.outcome, 1, Integer::sum);
                        total++;
                    }
                }
                if (total == 0) {
                    continue;
                }
                md.append("| ").append(impl).append(" | ").append(category).append(" | ")
                    .append(total);
                for (Outcome o : Outcome.values()) {
                    md.append(" | ").append(counts.getOrDefault(o, 0));
                }
                md.append(" |\n");
            }
        }
        writeMetrics(md);
        md.append("\n## Deviations\n\n");
        Map<String, List<Entry>> byCase = new LinkedHashMap<>();
        for (Entry e : all) {
            if (e.outcome != Outcome.PASS) {
                byCase.computeIfAbsent(e.category + " / " + e.caseId, k -> new ArrayList<>())
                    .add(e);
            }
        }
        if (byCase.isEmpty()) {
            md.append("None.\n");
        } else {
            md.append("| Case | Implementation | Outcome | Detail |\n|---|---|---|---|\n");
            byCase.forEach((c, list) -> list.forEach(e -> md.append("| ").append(escape(c))
                .append(" | ").append(e.implementation).append(" | ").append(e.outcome.label)
                .append(" | ").append(escape(e.detail)).append(" |\n")));
        }
        StringBuilder tsv = new StringBuilder("implementation\tcategory\tcase\toutcome\tdetail\n");
        for (Entry e : all) {
            tsv.append(e.implementation).append('\t').append(e.category).append('\t')
                .append(e.caseId).append('\t').append(e.outcome.name()).append('\t')
                .append(e.detail.replace('\t', ' ').replace('\n', ' ')).append('\n');
        }
        try {
            Path dir = Paths.get("target", "modularity-comparison");
            Files.createDirectories(dir);
            Files.write(dir.resolve(name + ".md"), md.toString().getBytes(StandardCharsets.UTF_8));
            Files.write(dir.resolve(name + ".tsv"),
                tsv.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String escape(String s) {
        String flat = s.replace("|", "\\|").replace("\n", " ");
        return flat.length() > 400 ? flat.substring(0, 400) + " ..." : flat;
    }
}
