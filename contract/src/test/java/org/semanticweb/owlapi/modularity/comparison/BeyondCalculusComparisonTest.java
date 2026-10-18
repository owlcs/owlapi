package org.semanticweb.owlapi.modularity.comparison;

import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.A;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.B;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.C;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.D;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.E;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.F;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.R;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.S;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.or;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sig;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.some;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.semanticweb.owlapi.modularity.comparison.ExtractorImplementations.ExtractorImpl;
import org.semanticweb.owlapi.modularity.comparison.ExtractorImplementations.Prepared;
import org.semanticweb.owlapi.modularity.comparison.ModuleCases.NamedOntology;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;

/**
 * Compares the implementations on axioms that are semantically local but NOT
 * covered by the standard syntactic locality calculus, e.g. {@code A SubClassOf A}
 * or {@code r SubPropertyOf r}. An implementation that recognises such tautologies
 * produces smaller modules (and more tautologies in the atomic decomposition)
 * than the calculus prescribes, without losing soundness.
 *
 * <p>These cases are deliberately kept out of the shared suites, where they would
 * either penalise the extension or require weaker expectations. Here, a
 * syntactic checker that misses such a tautology is recorded as
 * {@code incomplete} but never fails the build; answering "not local" is
 * always allowed for syntactic locality. Every module must still be sound, which
 * is checked with {@link SemanticOracle}.</p>
 */
@Tag("comparison")
class BeyondCalculusComparisonTest {

    static final String MODULE = "beyond-calculus-module";

    private static final ComparisonReport REPORT = new ComparisonReport("beyond-calculus",
        "Beyond the standard locality calculus");
    private static LocalityImplementations localities;
    private static SemanticOracle oracle;

    @BeforeAll
    static void setUp() {
        localities = new LocalityImplementations(true);
        localities.all().forEach(i -> REPORT.implementation(i.name()));
        oracle = new SemanticOracle();
    }

    @AfterAll
    static void tearDown() {
        REPORT.note("Module sizes on the tautology ontology", "Calculus = the module the "
            + "standard calculus prescribes; minimum = the module once all tautologies "
            + "(t1, t3, t6) are recognised. Every listed module passed the soundness check "
            + "unless reported under Deviations.");
        REPORT.note("Tautologies found by atomic decomposition",
            "Axioms of the tautology ontology classified as tautologies. Semantically, t1, t3, "
                + "t5 and t6 are tautologies; the standard calculus recognises none of them.");
        REPORT.write();
        localities.close();
        oracle.close();
    }

    @TestFactory
    Stream<DynamicNode> localityBeyondTheCalculus() {
        return SharedLocalityComparisonTest.dynamicTests(localities.all(),
            LocalityCases.beyondCalculus(), REPORT);
    }

    /**
     * t1, t3, t5 and t6 are tautologies the calculus does not see (t5 is irrelevant for
     * the seed {A} anyway). With seed {A}, the calculus BOTTOM module drags in C and D
     * through t3.
     */
    static NamedOntology tautologyOntology() {
        return new NamedOntology("tautology-ontology").add("t1", sub(A, A)).add("t2", sub(A, B))
            .add("t3", sub(B, or(B, C))).add("t4", sub(C, D))
            .add("t5", F.getOWLSubObjectPropertyOfAxiom(R, R))
            .add("t6", F.getOWLSubObjectPropertyOfAxiom(S, S)).add("t7", sub(A, some(S, E)));
    }

    @TestFactory
    Stream<DynamicNode> modulesBeyondTheCalculus() {
        NamedOntology o = tautologyOntology();
        List<DynamicNode> nodes = new ArrayList<>();
        for (ExtractorImpl impl : ExtractorImplementations.all(true)) {
            REPORT.implementation(impl.name());
            List<DynamicNode> tests = new ArrayList<>();
            tests.add(DynamicTest.dynamicTest("bottom {A}", () -> module(impl, o,
                LocalityClass.BOTTOM, o.select("t1", "t2", "t3", "t4", "t6", "t7"),
                o.select("t2", "t7"))));
            tests.add(DynamicTest.dynamicTest("top {A}", () -> module(impl, o, LocalityClass.TOP,
                o.select("t1"), o.select())));
            tests.add(DynamicTest.dynamicTest("star {A}", () -> module(impl, o,
                LocalityClass.STAR, o.select("t1"), o.select())));
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), tests));
        }
        return nodes.stream();
    }

    private static void module(ExtractorImpl impl, NamedOntology o, LocalityClass type,
        Set<OWLAxiom> calculus, Set<OWLAxiom> minimum) {
        Set<OWLEntity> seed = sig(A);
        String id = o.name + ":" + type.name().toLowerCase() + ":{A}";
        Set<OWLAxiom> module;
        try (Prepared prepared = impl.prepare(o.base(), type)) {
            module = new HashSet<>(prepared.extract(seed));
        } catch (RuntimeException e) {
            REPORT.recordFailure(impl.name(), impl.reference(), id, MODULE, e);
            return;
        }
        REPORT.metric("Module sizes on the tautology ontology", id, "calculus",
            o.labels(calculus));
        REPORT.metric("Module sizes on the tautology ontology", id, "minimum",
            o.labels(minimum));
        REPORT.metric("Module sizes on the tautology ontology", id, impl.name(),
            o.labels(module));
        List<String> problems = new ArrayList<>();
        if (!module.containsAll(minimum)) {
            problems.add("misses non-tautological axioms");
        }
        if (type != LocalityClass.STAR && !oracle.check(o.base(), module, seed, type)
            .violations.isEmpty()) {
            problems.add("not depleting");
        }
        Outcome outcome;
        if (!problems.isEmpty()) {
            outcome = Outcome.UNSOUND;
        } else if (!calculus.containsAll(module)) {
            outcome = Outcome.INCOMPLETE;
            problems.add("larger than the calculus module");
        } else {
            outcome = Outcome.PASS;
        }
        // Being no smaller than the calculus is fine; only soundness is asserted.
        boolean asserted = impl.reference() && outcome == Outcome.UNSOUND;
        REPORT.record(impl.name(), asserted, id, MODULE, outcome,
            o.labels(module) + (problems.isEmpty() ? "" : " - " + String.join(", ", problems)));
    }

    @TestFactory
    Stream<DynamicNode> decompositionTautologies() {
        NamedOntology o = tautologyOntology();
        List<DynamicNode> nodes = new ArrayList<>();
        for (DecompositionImpl impl : DecompositionImplementations.all()) {
            REPORT.implementation(impl.name());
            List<DynamicNode> tests = new ArrayList<>();
            for (LocalityClass type : SharedModuleExtractionComparisonTest.TYPES) {
                tests.add(DynamicTest.dynamicTest(type.name().toLowerCase(), () -> {
                    String id = o.name + ":" + type.name().toLowerCase();
                    try {
                        Decomposed d = impl.decompose(o.base(), type);
                        REPORT.metric("Tautologies found by atomic decomposition", id,
                            impl.name(), o.labels(d.tautologies));
                        // t2, t4 and t7 are never tautologies.
                        boolean sound = !d.tautologies.contains(o.axioms.get("t2"))
                            && !d.tautologies.contains(o.axioms.get("t4"))
                            && !d.tautologies.contains(o.axioms.get("t7"));
                        REPORT.record(impl.name(), impl.reference(), id, "beyond-calculus-ad",
                            sound ? Outcome.PASS : Outcome.UNSOUND, o.labels(d.tautologies));
                    } catch (RuntimeException e) {
                        REPORT.metric("Tautologies found by atomic decomposition", id,
                            impl.name(), "ERR");
                        REPORT.recordFailure(impl.name(), impl.reference(), id,
                            "beyond-calculus-ad", e);
                    }
                }));
            }
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), tests));
        }
        return nodes.stream();
    }
}
