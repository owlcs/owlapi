package org.semanticweb.owlapi.modularity.comparison;

import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.modularity.comparison.ComparisonReport.Outcome;
import org.semanticweb.owlapi.modularity.comparison.ExtractorImplementations.ExtractorImpl;
import org.semanticweb.owlapi.modularity.comparison.ExtractorImplementations.Prepared;
import org.semanticweb.owlapi.modularity.comparison.ModuleCases.ModuleCase;
import org.semanticweb.owlapi.modularity.comparison.TestOntologies.Corpus;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;

/**
 * Compares all locality-based module extractors of the tools module.
 *
 * <ul>
 * <li>{@code expected-module}: hand-computed BOTTOM, TOP and STAR modules of small,
 * tautology-free ontologies ({@link ModuleCases}).</li>
 * <li>On real and generated corpora (koala, pizza, a random OWL 2 corpus):
 * {@code subset} (the module is part of the axiom base), {@code idempotent}
 * (extracting again from the module returns the module), {@code depleting}
 * (every axiom left out is semantically local for the seed signature plus the
 * module signature, checked by {@link SemanticOracle}; this is the
 * implementation-independent soundness criterion), {@code star-nesting} (the
 * STAR module is contained in the BOTTOM and the TOP module) and
 * {@code monotone} (a larger seed signature never gives a smaller module).</li>
 * </ul>
 *
 * <p>Module sizes are listed in the report, so that equally sound extractors can
 * be compared by how small their modules are.</p>
 */
@Tag("comparison")
class SharedModuleExtractionComparisonTest {

    static final String EXPECTED = "expected-module";
    static final String SUBSET = "subset";
    static final String IDEMPOTENT = "idempotent";
    static final String DEPLETING = "depleting";
    static final String STAR_NESTING = "star-nesting";
    static final String MONOTONE = "monotone";
    /** Semantic extraction calls a reasoner per locality check: keep it to small bases. */
    static final int SEMANTIC_LIMIT = 200;

    private static final ComparisonReport REPORT =
        new ComparisonReport("modules-shared", "Module extractors: shared cases and invariants");
    private static final List<ExtractorImpl> IMPLS = ExtractorImplementations.all(true);
    private static SemanticOracle oracle;
    private static ModuleStore store;

    @BeforeAll
    static void setUp() {
        IMPLS.forEach(i -> REPORT.implementation(i.name()));
        oracle = new SemanticOracle();
        store = new ModuleStore();
    }

    @AfterAll
    static void tearDown() {
        REPORT.note("Module sizes", "Number of axioms per module; `ERR` marks an exception. "
            + "Smaller is better only if the module is also sound (see `depleting`).");
        REPORT.write();
        store.close();
        oracle.close();
    }

    /** Lazily prepared extractors and computed modules, shared by all dynamic tests. */
    static final class ModuleStore implements AutoCloseable {
        private final Map<List<Object>, Object> prepared = new HashMap<>();
        private final Map<List<Object>, Object> modules = new HashMap<>();

        synchronized Prepared prepared(ExtractorImpl impl, String baseName, Set<OWLAxiom> base,
            LocalityClass type) {
            List<Object> key = Arrays.asList(impl.name(), baseName, type);
            Object value = prepared.get(key);
            if (value == null) {
                try {
                    value = impl.prepare(base, type);
                } catch (RuntimeException e) {
                    value = e;
                }
                prepared.put(key, value);
            }
            if (value instanceof RuntimeException) {
                throw (RuntimeException) value;
            }
            return (Prepared) value;
        }

        @SuppressWarnings("unchecked")
        synchronized Set<OWLAxiom> module(ExtractorImpl impl, String baseName,
            Set<OWLAxiom> base, LocalityClass type, Set<OWLEntity> signature) {
            List<Object> key = Arrays.asList(impl.name(), baseName, type, signature);
            Object value = modules.get(key);
            if (value == null) {
                try {
                    value = new HashSet<>(prepared(impl, baseName, base, type).extract(signature));
                } catch (RuntimeException | StackOverflowError e) {
                    value = e instanceof RuntimeException ? e : new IllegalStateException(e);
                }
                modules.put(key, value);
            }
            if (value instanceof RuntimeException) {
                throw (RuntimeException) value;
            }
            return (Set<OWLAxiom>) value;
        }

        @Override
        public synchronized void close() {
            for (Object value : prepared.values()) {
                if (value instanceof Prepared) {
                    ((Prepared) value).close();
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Hand-computed modules
    // ------------------------------------------------------------------

    @TestFactory
    Stream<DynamicNode> handComputedModules() {
        List<DynamicNode> nodes = new ArrayList<>();
        for (ExtractorImpl impl : IMPLS) {
            List<DynamicNode> tests = new ArrayList<>();
            for (ModuleCase c : ModuleCases.all()) {
                tests.add(DynamicTest.dynamicTest(c.id, () -> checkExpected(impl, c)));
            }
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), tests));
        }
        return nodes.stream();
    }

    private static void checkExpected(ExtractorImpl impl, ModuleCase c) {
        Set<OWLAxiom> actual;
        try {
            actual = store.module(impl, c.ontology.name, c.ontology.base(), c.type, c.signature);
        } catch (RuntimeException e) {
            REPORT.recordFailure(impl.name(), impl.reference(), c.id, EXPECTED, e);
            return;
        }
        Set<OWLAxiom> missing = new HashSet<>(c.expected);
        missing.removeAll(actual);
        Set<OWLAxiom> extra = new HashSet<>(actual);
        extra.removeAll(c.expected);
        String detail = c.ontology + " expected " + c.ontology.labels(c.expected) + " but was "
            + c.ontology.labels(actual);
        Outcome outcome = Outcome.PASS;
        if (!missing.isEmpty()) {
            outcome = Outcome.UNSOUND;
            if (extra.isEmpty() && c.type != LocalityClass.STAR && oracle
                .check(c.ontology.base(), actual, c.signature, c.type).violations.isEmpty()) {
                // A smaller module that the oracle certifies (semantic locality).
                outcome = Outcome.PASS;
                detail = "smaller than the calculus, but sound: " + detail;
            }
        } else if (!extra.isEmpty()) {
            outcome = Outcome.INCOMPLETE;
        }
        REPORT.record(impl.name(), impl.reference(), c.id, EXPECTED, outcome, detail);
    }

    // ------------------------------------------------------------------
    // Invariants on corpora
    // ------------------------------------------------------------------

    static final LocalityClass[] TYPES =
        {LocalityClass.BOTTOM, LocalityClass.TOP, LocalityClass.STAR};

    @TestFactory
    Stream<DynamicNode> corpusInvariants() {
        List<DynamicNode> nodes = new ArrayList<>();
        for (ExtractorImpl impl : IMPLS) {
            List<DynamicNode> perCorpus = new ArrayList<>();
            for (Corpus corpus : TestOntologies.all()) {
                if (impl.semantic() && corpus.logicalAxioms.size() > SEMANTIC_LIMIT) {
                    continue;
                }
                List<DynamicNode> tests = new ArrayList<>();
                for (LocalityClass type : TYPES) {
                    for (Map.Entry<String, Set<OWLEntity>> seed : corpus.signatures.entrySet()) {
                        String id = corpus.name + ":" + type.name().toLowerCase() + ":"
                            + seed.getKey();
                        tests.add(DynamicTest.dynamicTest(id + " " + SUBSET,
                            () -> subset(impl, corpus, type, seed.getValue(), id)));
                        tests.add(DynamicTest.dynamicTest(id + " " + IDEMPOTENT,
                            () -> idempotent(impl, corpus, type, seed.getValue(), id)));
                        if (type == LocalityClass.STAR) {
                            tests.add(DynamicTest.dynamicTest(id + " " + STAR_NESTING,
                                () -> starNesting(impl, corpus, seed.getValue(), id)));
                        } else {
                            tests.add(DynamicTest.dynamicTest(id + " " + DEPLETING,
                                () -> depleting(impl, corpus, type, seed.getValue(), id)));
                        }
                    }
                    String id = corpus.name + ":" + type.name().toLowerCase();
                    tests.add(DynamicTest.dynamicTest(id + " " + MONOTONE,
                        () -> monotone(impl, corpus, type, id)));
                }
                perCorpus.add(DynamicContainer.dynamicContainer(corpus.name, tests));
            }
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), perCorpus));
        }
        return nodes.stream();
    }

    private static Set<OWLAxiom> moduleOrRecord(ExtractorImpl impl, Corpus corpus,
        LocalityClass type, Set<OWLEntity> seed, String id, String category) {
        try {
            Set<OWLAxiom> module =
                store.module(impl, corpus.name, corpus.logicalAxioms, type, seed);
            REPORT.metric("Module sizes", id, impl.name(), Integer.valueOf(module.size()));
            return module;
        } catch (RuntimeException e) {
            REPORT.metric("Module sizes", id, impl.name(), "ERR");
            REPORT.recordFailure(impl.name(), impl.reference(), id, category, e);
            return null;
        }
    }

    private static void subset(ExtractorImpl impl, Corpus corpus, LocalityClass type,
        Set<OWLEntity> seed, String id) {
        Set<OWLAxiom> module = moduleOrRecord(impl, corpus, type, seed, id, SUBSET);
        if (module == null) {
            return;
        }
        Set<OWLAxiom> foreign = new HashSet<>(module);
        foreign.removeAll(corpus.logicalAxioms);
        REPORT.record(impl.name(), impl.reference(), id, SUBSET,
            foreign.isEmpty() ? Outcome.PASS : Outcome.BROKEN,
            foreign.isEmpty() ? "" : "axioms not in the base: " + render(foreign));
    }

    private static void idempotent(ExtractorImpl impl, Corpus corpus, LocalityClass type,
        Set<OWLEntity> seed, String id) {
        Set<OWLAxiom> module = moduleOrRecord(impl, corpus, type, seed, id, IDEMPOTENT);
        if (module == null) {
            return;
        }
        Set<OWLAxiom> again;
        try (Prepared onModule = impl.prepare(module, type)) {
            again = onModule.extract(seed);
        } catch (RuntimeException e) {
            REPORT.recordFailure(impl.name(), impl.reference(), id, IDEMPOTENT, e);
            return;
        }
        boolean same = again.equals(module);
        REPORT.record(impl.name(), impl.reference(), id, IDEMPOTENT,
            same ? Outcome.PASS : Outcome.BROKEN,
            same ? "" : "module has " + module.size() + " axioms, re-extraction has "
                + again.size());
    }

    private static void depleting(ExtractorImpl impl, Corpus corpus, LocalityClass type,
        Set<OWLEntity> seed, String id) {
        Set<OWLAxiom> module = moduleOrRecord(impl, corpus, type, seed, id, DEPLETING);
        if (module == null) {
            return;
        }
        SemanticOracle.Verdict verdict = oracle.check(corpus.logicalAxioms, module, seed, type);
        REPORT.metric("Oracle coverage", id, impl.name(),
            verdict.undecided == 0 ? "all decided" : verdict.undecided + " undecided");
        boolean sound = verdict.violations.isEmpty();
        REPORT.record(impl.name(), impl.reference(), id, DEPLETING,
            sound ? Outcome.PASS : Outcome.UNSOUND,
            sound ? "" : verdict.violations.size() + " axioms left out that are semantically "
                + "NOT local, e.g. " + render(verdict.violations.stream().limit(3)
                    .collect(Collectors.toList())));
    }

    private static void starNesting(ExtractorImpl impl, Corpus corpus, Set<OWLEntity> seed,
        String id) {
        Set<OWLAxiom> star = moduleOrRecord(impl, corpus, LocalityClass.STAR, seed, id,
            STAR_NESTING);
        if (star == null) {
            return;
        }
        Set<OWLAxiom> bottom;
        Set<OWLAxiom> top;
        try {
            bottom = store.module(impl, corpus.name, corpus.logicalAxioms, LocalityClass.BOTTOM,
                seed);
            top = store.module(impl, corpus.name, corpus.logicalAxioms, LocalityClass.TOP, seed);
        } catch (RuntimeException e) {
            REPORT.recordFailure(impl.name(), impl.reference(), id, STAR_NESTING, e);
            return;
        }
        Set<OWLAxiom> outside = new HashSet<>(star);
        outside.removeIf(ax -> bottom.contains(ax) && top.contains(ax));
        REPORT.record(impl.name(), impl.reference(), id, STAR_NESTING,
            outside.isEmpty() ? Outcome.PASS : Outcome.BROKEN,
            outside.isEmpty() ? "" : outside.size() + " STAR axioms outside BOTTOM or TOP, e.g. "
                + render(outside.stream().limit(3).collect(Collectors.toList())));
    }

    private static void monotone(ExtractorImpl impl, Corpus corpus, LocalityClass type,
        String id) {
        List<String> violations = new ArrayList<>();
        for (Map.Entry<String, Set<OWLEntity>> small : corpus.signatures.entrySet()) {
            for (Map.Entry<String, Set<OWLEntity>> large : corpus.signatures.entrySet()) {
                if (small == large || !large.getValue().containsAll(small.getValue())) {
                    continue;
                }
                Set<OWLAxiom> m1;
                Set<OWLAxiom> m2;
                try {
                    m1 = store.module(impl, corpus.name, corpus.logicalAxioms, type,
                        small.getValue());
                    m2 = store.module(impl, corpus.name, corpus.logicalAxioms, type,
                        large.getValue());
                } catch (RuntimeException e) {
                    REPORT.recordFailure(impl.name(), impl.reference(), id, MONOTONE, e);
                    return;
                }
                if (!m2.containsAll(m1)) {
                    violations.add(small.getKey() + " gives " + m1.size() + " axioms, "
                        + large.getKey() + " only " + m2.size());
                }
            }
        }
        REPORT.record(impl.name(), impl.reference(), id, MONOTONE,
            violations.isEmpty() ? Outcome.PASS : Outcome.BROKEN, String.join("; ", violations));
    }
}
