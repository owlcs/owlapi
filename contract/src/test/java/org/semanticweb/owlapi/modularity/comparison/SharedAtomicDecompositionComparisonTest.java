package org.semanticweb.owlapi.modularity.comparison;

import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.A;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.B;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.C;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.D;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.E;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.nothing;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.render;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sub;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.thing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
import org.semanticweb.owlapi.modularity.comparison.DecompositionImplementations.Decomposed;
import org.semanticweb.owlapi.modularity.comparison.DecompositionImplementations.DecompositionImpl;
import org.semanticweb.owlapi.modularity.comparison.ExtractorImplementations.Prepared;
import org.semanticweb.owlapi.modularity.comparison.ModuleCases.NamedOntology;
import org.semanticweb.owlapi.modularity.comparison.TestOntologies.Corpus;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;

/**
 * Compares the three atomic decomposition implementations.
 *
 * <ul>
 * <li>{@code expected-ad}: hand-computed atoms, dependencies and tautologies of
 * small ontologies for BOTTOM, TOP and STAR locality.</li>
 * <li>On the corpora: {@code partition} (atoms and tautologies partition the axiom
 * base), {@code principal-ideal} (for every axiom, its atom plus all atoms it
 * depends on is exactly the module for the axiom's signature, as computed by the
 * implementation's own extractor), {@code tautologies} (exactly the axioms that
 * are not in the module for their own signature; that module need not be empty)
 * and {@code order} (dependencies form a strict partial order).</li>
 * </ul>
 */
@Tag("comparison")
class SharedAtomicDecompositionComparisonTest {

    static final String EXPECTED = "expected-ad";
    static final String PARTITION = "partition";
    static final String IDEAL = "principal-ideal";
    static final String TAUTOLOGIES = "tautologies";
    static final String ORDER = "order";

    private static final ComparisonReport REPORT = new ComparisonReport("decomposition-shared",
        "Atomic decompositions: shared cases and invariants");
    private static final List<DecompositionImpl> IMPLS = DecompositionImplementations.all();
    private static final Map<List<Object>, Object> CACHE = new HashMap<>();

    @BeforeAll
    static void setUp() {
        IMPLS.forEach(i -> REPORT.implementation(i.name()));
    }

    @AfterAll
    static void tearDown() {
        REPORT.note("Decomposition shape", "atoms / tautologies / dependency edges "
            + "(transitive). `same as modularity-ad` compares the atoms as axiom sets.");
        REPORT.write();
    }

    private static synchronized Decomposed decomposition(DecompositionImpl impl, String name,
        Set<OWLAxiom> base, LocalityClass type) {
        List<Object> key = java.util.Arrays.asList(impl.name(), name, type);
        Object value = CACHE.get(key);
        if (value == null) {
            try {
                value = impl.decompose(base, type);
            } catch (RuntimeException | StackOverflowError e) {
                value = e instanceof RuntimeException ? e : new IllegalStateException(e);
            }
            CACHE.put(key, value);
        }
        if (value instanceof RuntimeException) {
            throw (RuntimeException) value;
        }
        return (Decomposed) value;
    }

    // ------------------------------------------------------------------
    // Hand-computed decompositions
    // ------------------------------------------------------------------

    /** Expected atoms (as label groups), dependencies and tautologies. */
    private static final class AdCase {
        final NamedOntology ontology;
        final LocalityClass type;
        final Set<Set<OWLAxiom>> atoms = new HashSet<>();
        final Map<Set<OWLAxiom>, Set<Set<OWLAxiom>>> dependencies = new HashMap<>();
        final Set<OWLAxiom> tautologies;

        /**
         * @param atoms e.g. "a1|a2,b2" for the atoms {a1} and {a2, b2}
         * @param deps e.g. "a1>a2|a2>" lists, per atom (by first label), its dependencies
         */
        AdCase(NamedOntology ontology, LocalityClass type, String atoms, String deps,
            String tautologies) {
            this.ontology = ontology;
            this.type = type;
            Map<String, Set<OWLAxiom>> byLabel = new HashMap<>();
            for (String group : atoms.split("\\|")) {
                Set<OWLAxiom> atom = ontology.select(group.split(","));
                this.atoms.add(atom);
                for (String label : group.split(",")) {
                    byLabel.put(label, atom);
                }
            }
            for (Set<OWLAxiom> atom : this.atoms) {
                dependencies.put(atom, new HashSet<>());
            }
            if (!deps.isEmpty()) {
                for (String entry : deps.split("\\|")) {
                    String[] parts = entry.split(">", -1);
                    Set<Set<OWLAxiom>> targets = new HashSet<>();
                    if (!parts[1].isEmpty()) {
                        for (String label : parts[1].split(",")) {
                            targets.add(byLabel.get(label));
                        }
                    }
                    dependencies.put(byLabel.get(parts[0]), targets);
                }
            }
            this.tautologies = tautologies.isEmpty() ? new HashSet<>()
                : ontology.select(tautologies.split(","));
        }

        String id() {
            return ontology.name + ":" + type.name().toLowerCase();
        }
    }

    private static List<AdCase> cases() {
        List<AdCase> list = new ArrayList<>();
        NamedOntology chain = new NamedOntology("chain").add("a1", sub(A, B)).add("a2", sub(B, C))
            .add("a3", sub(C, D)).add("a4", sub(E, Vocabulary.cls("F")));
        list.add(new AdCase(chain, LocalityClass.BOTTOM, "a1|a2|a3|a4", "a1>a2,a3|a2>a3", ""));
        list.add(new AdCase(chain, LocalityClass.TOP, "a1|a2|a3|a4", "a3>a1,a2|a2>a1", ""));
        list.add(new AdCase(chain, LocalityClass.STAR, "a1|a2|a3|a4", "", ""));
        NamedOntology cycle = new NamedOntology("cycle").add("b1", sub(A, B)).add("b2", sub(B, A))
            .add("b3", sub(C, A));
        list.add(new AdCase(cycle, LocalityClass.BOTTOM, "b1,b2|b3", "b3>b1", ""));
        list.add(new AdCase(cycle, LocalityClass.TOP, "b1,b2,b3", "", ""));
        list.add(new AdCase(cycle, LocalityClass.STAR, "b1,b2|b3", "b3>b1", ""));
        NamedOntology taut = new NamedOntology("tautologies").add("t1", sub(A, thing()))
            .add("t2", sub(nothing(), B)).add("t3", sub(A, B));
        for (LocalityClass type : SharedModuleExtractionComparisonTest.TYPES) {
            list.add(new AdCase(taut, type, "t3", "", "t1,t2"));
        }
        NamedOntology global = new NamedOntology("global").add("g1", sub(thing(), A))
            .add("g2", sub(B, C));
        list.add(new AdCase(global, LocalityClass.BOTTOM, "g1|g2", "g2>g1", ""));
        list.add(new AdCase(global, LocalityClass.TOP, "g1|g2", "", ""));
        list.add(new AdCase(global, LocalityClass.STAR, "g1|g2", "", ""));
        return list;
    }

    @TestFactory
    Stream<DynamicNode> handComputedDecompositions() {
        List<DynamicNode> nodes = new ArrayList<>();
        for (DecompositionImpl impl : IMPLS) {
            List<DynamicNode> tests = new ArrayList<>();
            for (AdCase c : cases()) {
                tests.add(DynamicTest.dynamicTest(c.id(), () -> checkExpected(impl, c)));
            }
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), tests));
        }
        return nodes.stream();
    }

    private static String show(NamedOntology o, Decomposed d) {
        List<String> atoms = new ArrayList<>();
        for (Set<OWLAxiom> atom : d.atoms) {
            String deps = d.dependencies.getOrDefault(atom, new HashSet<>()).stream()
                .map(o::labels).sorted().collect(Collectors.joining(","));
            atoms.add(o.labels(atom) + (deps.isEmpty() ? "" : ">" + deps));
        }
        atoms.sort(null);
        return "atoms " + atoms + ", tautologies " + o.labels(d.tautologies);
    }

    private static void checkExpected(DecompositionImpl impl, AdCase c) {
        Decomposed d;
        try {
            d = decomposition(impl, c.ontology.name, c.ontology.base(), c.type);
        } catch (RuntimeException e) {
            REPORT.recordFailure(impl.name(), impl.reference(), c.id(), EXPECTED, e);
            return;
        }
        Set<Set<OWLAxiom>> atoms = new HashSet<>(d.atoms);
        Map<Set<OWLAxiom>, Set<Set<OWLAxiom>>> deps = new HashMap<>();
        d.atoms.forEach(a -> deps.put(a, d.dependencies.getOrDefault(a, new HashSet<>())));
        boolean same = atoms.equals(c.atoms) && deps.equals(c.dependencies)
            && d.tautologies.equals(c.tautologies);
        REPORT.record(impl.name(), impl.reference(), c.id(), EXPECTED,
            same ? Outcome.PASS : Outcome.BROKEN, same ? ""
                : c.ontology + ": expected " + expected(c) + " but was " + show(c.ontology, d));
    }

    private static String expected(AdCase c) {
        Decomposed d = new Decomposed();
        d.atoms.addAll(c.atoms);
        d.dependencies.putAll(c.dependencies);
        d.tautologies.addAll(c.tautologies);
        return show(c.ontology, d);
    }

    // ------------------------------------------------------------------
    // Invariants on corpora
    // ------------------------------------------------------------------

    @TestFactory
    Stream<DynamicNode> corpusInvariants() {
        List<DynamicNode> nodes = new ArrayList<>();
        for (DecompositionImpl impl : IMPLS) {
            List<DynamicNode> tests = new ArrayList<>();
            for (Corpus corpus : TestOntologies.all()) {
                for (LocalityClass type : SharedModuleExtractionComparisonTest.TYPES) {
                    String id = corpus.name + ":" + type.name().toLowerCase();
                    tests.add(DynamicTest.dynamicTest(id + " " + PARTITION,
                        () -> partition(impl, corpus, type, id)));
                    tests.add(DynamicTest.dynamicTest(id + " " + IDEAL,
                        () -> ideals(impl, corpus, type, id)));
                    tests.add(DynamicTest.dynamicTest(id + " " + TAUTOLOGIES,
                        () -> tautologies(impl, corpus, type, id)));
                    tests.add(DynamicTest.dynamicTest(id + " " + ORDER,
                        () -> order(impl, corpus, type, id)));
                }
            }
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), tests));
        }
        return nodes.stream();
    }

    private static Decomposed orRecord(DecompositionImpl impl, Corpus corpus, LocalityClass type,
        String id, String category) {
        try {
            Decomposed d = decomposition(impl, corpus.name, corpus.logicalAxioms, type);
            String shape = d.atoms.size() + " / " + d.tautologies.size() + " / " + d.edges();
            REPORT.metric("Decomposition shape", id, impl.name(), shape);
            if (!impl.reference()) {
                try {
                    Decomposed reference = decomposition(DecompositionImplementations.MODULARITY,
                        corpus.name, corpus.logicalAxioms, type);
                    REPORT.metric("Decomposition shape", id, "same as modularity-ad",
                        new HashSet<>(reference.atoms).equals(new HashSet<>(d.atoms)) ? "yes"
                            : "no");
                } catch (RuntimeException e) {
                    // reported by the reference implementation itself
                }
            }
            return d;
        } catch (RuntimeException e) {
            REPORT.metric("Decomposition shape", id, impl.name(), "ERR");
            REPORT.recordFailure(impl.name(), impl.reference(), id, category, e);
            return null;
        }
    }

    private static void partition(DecompositionImpl impl, Corpus corpus, LocalityClass type,
        String id) {
        Decomposed d = orRecord(impl, corpus, type, id, PARTITION);
        if (d == null) {
            return;
        }
        List<String> problems = new ArrayList<>();
        Set<OWLAxiom> seen = new HashSet<>();
        for (Set<OWLAxiom> atom : d.atoms) {
            if (atom.isEmpty()) {
                problems.add("empty atom");
            }
            for (OWLAxiom axiom : atom) {
                if (!seen.add(axiom)) {
                    problems.add("in two atoms: " + render(axiom));
                }
            }
        }
        for (OWLAxiom axiom : d.tautologies) {
            if (!seen.add(axiom)) {
                problems.add("atom axiom also reported as tautology: " + render(axiom));
            }
        }
        Set<OWLAxiom> missing = new HashSet<>(corpus.logicalAxioms);
        missing.removeAll(seen);
        if (!missing.isEmpty()) {
            problems.add(missing.size() + " axioms neither in an atom nor a tautology, e.g. "
                + render(missing.iterator().next()));
        }
        Set<OWLAxiom> foreign = new HashSet<>(seen);
        foreign.removeAll(corpus.logicalAxioms);
        if (!foreign.isEmpty()) {
            problems.add(foreign.size() + " axioms not in the base");
        }
        REPORT.record(impl.name(), impl.reference(), id, PARTITION,
            problems.isEmpty() ? Outcome.PASS : Outcome.BROKEN,
            problems.stream().limit(3).collect(Collectors.joining("; ")));
    }

    /** Principal ideals and tautologies compared with the paired extractor. */
    private static final class IdealCheck {
        int wrongIdeals;
        int wrongTautologies;
        String example = "";
        RuntimeException failure;
    }

    private static synchronized IdealCheck idealCheck(DecompositionImpl impl, Corpus corpus,
        LocalityClass type, Decomposed d) {
        List<Object> key = java.util.Arrays.asList("ideal", impl.name(), corpus.name, type);
        IdealCheck cached = (IdealCheck) CACHE.get(key);
        if (cached != null) {
            return cached;
        }
        IdealCheck check = new IdealCheck();
        try (Prepared extractor = impl.extractor().prepare(corpus.logicalAxioms, type)) {
            for (OWLAxiom axiom : corpus.logicalAxioms) {
                Set<OWLEntity> signature = axiom.signature().collect(Collectors.toSet());
                Set<OWLAxiom> module = new HashSet<>(extractor.extract(signature));
                // An axiom is a tautology iff it is local for its own signature, i.e. iff
                // it is not in the module for its own signature. That module need not be
                // empty: other axioms (e.g. global ones) can be non-local for it.
                if (!module.contains(axiom)) {
                    if (!d.tautologies.contains(axiom)) {
                        check.wrongTautologies++;
                        if (check.example.isEmpty()) {
                            check.example = "e.g. tautology " + render(axiom)
                                + " is not reported as one";
                        }
                    }
                    continue;
                }
                if (d.tautologies.contains(axiom)) {
                    check.wrongTautologies++;
                }
                Set<OWLAxiom> ideal = d.ideal(axiom);
                if (!module.equals(ideal)) {
                    check.wrongIdeals++;
                    if (check.example.isEmpty()) {
                        check.example = "e.g. " + render(axiom) + ": module has " + module.size()
                            + " axioms, ideal " + (ideal == null ? "missing" : ideal.size());
                    }
                }
            }
        } catch (RuntimeException e) {
            check.failure = e;
        }
        CACHE.put(key, check);
        return check;
    }

    private static void ideals(DecompositionImpl impl, Corpus corpus, LocalityClass type,
        String id) {
        Decomposed d = orRecord(impl, corpus, type, id, IDEAL);
        if (d == null) {
            return;
        }
        IdealCheck check = idealCheck(impl, corpus, type, d);
        if (check.failure != null) {
            REPORT.recordFailure(impl.name(), impl.reference(), id, IDEAL, check.failure);
            return;
        }
        REPORT.record(impl.name(), impl.reference(), id, IDEAL,
            check.wrongIdeals == 0 ? Outcome.PASS : Outcome.BROKEN, check.wrongIdeals == 0 ? ""
                : check.wrongIdeals + " of " + corpus.logicalAxioms.size()
                    + " principal ideals differ from " + impl.extractor().name() + ", "
                    + check.example);
    }

    private static void tautologies(DecompositionImpl impl, Corpus corpus, LocalityClass type,
        String id) {
        Decomposed d = orRecord(impl, corpus, type, id, TAUTOLOGIES);
        if (d == null) {
            return;
        }
        IdealCheck check = idealCheck(impl, corpus, type, d);
        if (check.failure != null) {
            REPORT.recordFailure(impl.name(), impl.reference(), id, TAUTOLOGIES, check.failure);
            return;
        }
        REPORT.record(impl.name(), impl.reference(), id, TAUTOLOGIES,
            check.wrongTautologies == 0 ? Outcome.PASS : Outcome.BROKEN,
            check.wrongTautologies == 0 ? ""
                : check.wrongTautologies + " axioms misclassified as (non-)tautologies"
                    + (check.example.startsWith("e.g. tautology") ? ", " + check.example : ""));
    }

    private static void order(DecompositionImpl impl, Corpus corpus, LocalityClass type,
        String id) {
        Decomposed d = orRecord(impl, corpus, type, id, ORDER);
        if (d == null) {
            return;
        }
        List<String> problems = new ArrayList<>();
        Set<Set<OWLAxiom>> atoms = new HashSet<>(d.atoms);
        for (Set<OWLAxiom> atom : d.atoms) {
            Set<Set<OWLAxiom>> deps = d.dependencies.getOrDefault(atom, new HashSet<>());
            if (deps.contains(atom)) {
                problems.add("self dependency");
            }
            for (Set<OWLAxiom> dep : deps) {
                if (!atoms.contains(dep)) {
                    problems.add("dependency on an unknown atom");
                } else if (d.dependencies.getOrDefault(dep, new HashSet<>()).contains(atom)) {
                    problems.add("cycle " + new TreeSet<>(ModuleCases.labelsOf(atom)) + " <-> "
                        + new TreeSet<>(ModuleCases.labelsOf(dep)));
                } else if (!deps.containsAll(d.dependencies.getOrDefault(dep, new HashSet<>()))) {
                    problems.add("not transitive");
                }
            }
        }
        REPORT.record(impl.name(), impl.reference(), id, ORDER,
            problems.isEmpty() ? Outcome.PASS : Outcome.BROKEN,
            problems.stream().distinct().limit(3).collect(Collectors.joining("; ")));
    }
}
