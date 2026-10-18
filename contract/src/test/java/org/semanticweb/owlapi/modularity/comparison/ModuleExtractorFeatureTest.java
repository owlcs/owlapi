package org.semanticweb.owlapi.modularity.comparison;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.A;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.B;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.C;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.D;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.F;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.I;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.J;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sig;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sub;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SemanticLocalityModuleExtractor;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityModuleExtractor;
import org.semanticweb.owlapitools.decomposition.AxiomSelector;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.Decomposer;
import org.semanticweb.owlapitools.decomposition.SyntacticLocalityChecker;

import uk.ac.manchester.cs.factplusplusad.OntologyBasedModularizer;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * Documents (and, for the reference implementation, asserts) the non-logical
 * behaviour of the module extractors: which declarations, annotation assertions
 * and individual axioms end up in a module, and which side effects an extractor
 * has. These are design differences rather than right or wrong answers, so the
 * deprecated implementations are only recorded. The table is written to
 * {@code target/modularity-comparison/features.md}.
 */
@Tag("comparison")
@SuppressWarnings("deprecation")
class ModuleExtractorFeatureTest {

    private static final ComparisonReport REPORT =
        new ComparisonReport("features", "Module extractors: non-logical behaviour");
    private static final String TABLE = "Content of the BOTTOM module for {A}";

    private static final OWLAxiom LOGICAL = sub(A, B);
    private static final OWLAxiom UNRELATED = sub(C, D);
    private static final OWLAxiom DECL_A = F.getOWLDeclarationAxiom(A);
    private static final OWLAxiom DECL_B = F.getOWLDeclarationAxiom(B);
    private static final OWLAxiom DECL_C = F.getOWLDeclarationAxiom(C);
    private static final OWLAxiom LABEL_A =
        F.getOWLAnnotationAssertionAxiom(F.getRDFSLabel(), A.getIRI(), F.getOWLLiteral("a"));
    private static final OWLAxiom LABEL_C =
        F.getOWLAnnotationAssertionAxiom(F.getRDFSLabel(), C.getIRI(), F.getOWLLiteral("c"));
    private static final OWLAxiom ASSERT_I = F.getOWLClassAssertionAxiom(A, I);
    private static final OWLAxiom DIFFERENT = F.getOWLDifferentIndividualsAxiom(I, J);

    private static final Map<String, OWLAxiom> PROBES = new LinkedHashMap<>();

    static {
        PROBES.put("A SubClassOf B (needed)", LOGICAL);
        PROBES.put("C SubClassOf D (unrelated)", UNRELATED);
        PROBES.put("Declaration(A)", DECL_A);
        PROBES.put("Declaration(B)", DECL_B);
        PROBES.put("Declaration(C) (unrelated)", DECL_C);
        PROBES.put("label of A", LABEL_A);
        PROBES.put("label of C (unrelated)", LABEL_C);
        PROBES.put("ClassAssertion(A i)", ASSERT_I);
        PROBES.put("DifferentIndividuals(i j)", DIFFERENT);
    }

    private static Set<OWLAxiom> base() {
        return new HashSet<>(Arrays.asList(LOGICAL, UNRELATED, DECL_A, DECL_B, DECL_C, LABEL_A,
            LABEL_C, ASSERT_I, DIFFERENT));
    }

    @AfterAll
    static void tearDown() {
        REPORT.note(TABLE, "Base: " + PROBES.keySet() + ". `x` = contained in the module. "
            + "`ontologies created` counts ontologies left behind in the caller's manager.");
        REPORT.write();
    }

    private static Set<OWLAxiom> probe(String name, boolean reference,
        Function<OWLOntologyManager, Set<OWLAxiom>> extractor) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        Set<OWLAxiom> module;
        try {
            module = extractor.apply(manager);
        } catch (RuntimeException e) {
            REPORT.metric(TABLE, "error", name, e.getClass().getSimpleName());
            REPORT.recordFailure(name, reference, "probe", "features", e);
            return Collections.emptySet();
        }
        PROBES.forEach((label, axiom) -> REPORT.metric(TABLE, label, name,
            module.contains(axiom) ? "x" : ""));
        REPORT.metric(TABLE, "ontologies created", name,
            Integer.valueOf(manager.getOntologies().size()));
        return module;
    }

    @Test
    void nolteSyntacticReturnsExactlyTheNonLocalAxioms() {
        Set<OWLAxiom> module = probe("nolte-syntactic", true,
            m -> new SyntacticLocalityModuleExtractor(LocalityClass.BOTTOM, base().stream())
                .extract(sig(A).stream()).collect(Collectors.toSet()));
        // Declarations and annotation assertions are local, so they are never part of the
        // module; there is no option to add them back. ClassAssertion(A i) and
        // DifferentIndividuals(i j) are non-local under BOTTOM.
        assertEquals(new HashSet<>(Arrays.asList(LOGICAL, ASSERT_I, DIFFERENT)), module);
    }

    @Test
    void nolteSemanticReturnsExactlyTheNonLocalAxioms() {
        Set<OWLAxiom> module = probe("nolte-semantic", true, m -> {
            SemanticLocalityModuleExtractor extractor = new SemanticLocalityModuleExtractor(
                LocalityClass.BOTTOM, base().stream(), m, new ReasonerFactory());
            try {
                return extractor.extract(sig(A).stream()).collect(Collectors.toSet());
            } finally {
                extractor.dispose();
            }
        });
        assertEquals(new HashSet<>(Arrays.asList(LOGICAL, ASSERT_I, DIFFERENT)), module);
    }

    @Test
    void manchesterSegmenterEnrichesWithDeclarationsAndAnnotations() {
        Set<OWLAxiom> module = probe("manchester-segmenter", false,
            m -> new uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor(m,
                base().stream(), ModuleType.BOT).extract(Collections.singleton((OWLEntity) A)));
        // Documented behaviour (see ModuleAnnotationsTestCase): declarations and annotation
        // assertions of the module signature are added.
        assertTrue(module.contains(DECL_A));
        assertTrue(module.contains(LABEL_A));
        assertFalse(module.contains(LABEL_C));
    }

    @Test
    void manchesterSegmenterWithoutEnrichment() {
        probe("manchester-segmenter (skip annotations)", false,
            m -> new uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor(m,
                m.getOntologyLoaderConfiguration().withSkipModuleAnnotations(true),
                base().stream(), ModuleType.BOT, false)
                    .extract(Collections.singleton((OWLEntity) A)));
    }

    @Test
    void owlapitoolsDecomposer() {
        probe("owlapitools-decomposer", false, m -> {
            Decomposer decomposer = new Decomposer(
                AxiomSelector.wrap(new java.util.ArrayList<>(base())),
                new SyntacticLocalityChecker());
            return decomposer.getModule(sig(A).stream(), false, ModuleType.BOT).stream()
                .map(AxiomWrapper::getAxiom).collect(Collectors.toSet());
        });
    }

    @Test
    void factPlusPlusModularizer() {
        probe("factplusplusad-modularizer", false, m -> {
            OWLOntology o;
            try {
                o = m.createOntology(base());
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            Set<OWLAxiom> module = new HashSet<>(
                new OntologyBasedModularizer(o, ModuleMethod.SYNTACTIC_STANDARD)
                    .getModule(sig(A).stream(), ModuleType.BOT));
            // The caller created this ontology; do not count it as a side effect.
            m.removeOntology(o);
            return module;
        });
    }
}
