package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/** Regression tests for indexed locality-based module extraction. */
@Tag("regression")
public class LocalityModuleExtractorRegressionTest {

    private final OWLDataFactory df = OWLManager.createOWLOntologyManager().getOWLDataFactory();
    private final OWLClass a = named("A");
    private final OWLClass b = named("B");
    private final OWLClass c = named("C");
    private final OWLClass d = named("D");
    private final OWLClass e = named("E");
    private final OWLClass unknown = named("Unknown");

    private OWLClass named(String name) {
        return df.getOWLClass(IRI.create("urn:locality-module:regression#" + name));
    }

    private OWLAxiom sub(org.semanticweb.owlapi.model.OWLClassExpression left,
                         org.semanticweb.owlapi.model.OWLClassExpression right) {
        return df.getOWLSubClassOfAxiom(left, right);
    }

    private Set<OWLAxiom> extract(LocalityClass kind, Set<OWLAxiom> axioms,
                                  OWLEntity... seed) {
        return new SyntacticLocalityModuleExtractor(kind, axioms.stream())
                .extract(Arrays.stream(seed)).collect(Collectors.toSet());
    }

    @Test
    void bottomIncludesGlobalAxiomForEmptySignature() {
        OWLAxiom global = sub(df.getOWLThing(), a);
        assertEquals(Collections.singleton(global),
                extract(LocalityClass.BOTTOM, Collections.singleton(global)));
    }

    @Test
    void topIncludesGlobalAxiomForEmptySignature() {
        OWLAxiom global = sub(a, df.getOWLNothing());
        assertEquals(Collections.singleton(global),
                extract(LocalityClass.TOP, Collections.singleton(global)));
    }

    @Test
    void globalBottomAxiomStartsTheSignatureClosure() {
        OWLAxiom global = sub(df.getOWLThing(), a);
        OWLAxiom dependent = sub(a, b);
        Set<OWLAxiom> axioms = new HashSet<>(Arrays.asList(global, dependent));
        assertEquals(axioms, extract(LocalityClass.BOTTOM, axioms));
    }

    @Test
    void globalTopAxiomStartsTheSignatureClosure() {
        OWLAxiom global = sub(a, df.getOWLNothing());
        OWLAxiom dependent = sub(b, a);
        Set<OWLAxiom> axioms = new HashSet<>(Arrays.asList(global, dependent));
        assertEquals(axioms, extract(LocalityClass.TOP, axioms));
    }

    @Test
    void starKeepsAxiomsNonLocalUnderBothLocalities() {
        OWLAxiom contradiction = sub(df.getOWLThing(), df.getOWLNothing());
        assertEquals(Collections.singleton(contradiction),
                extract(LocalityClass.STAR, Collections.singleton(contradiction)));
    }

    @Test
    void unknownSeedEntityDoesNotCauseNullPointerException() {
        OWLAxiom unrelated = sub(a, b);
        assertTrue(extract(LocalityClass.BOTTOM,
                Collections.singleton(unrelated), unknown).isEmpty());
        assertTrue(extract(LocalityClass.TOP,
                Collections.singleton(unrelated), unknown).isEmpty());
    }

    @Test
    void unknownSeedEntityDoesNotSuppressGlobalAxioms() {
        OWLAxiom global = sub(df.getOWLThing(), a);
        assertEquals(Collections.singleton(global),
                extract(LocalityClass.BOTTOM, Collections.singleton(global), unknown));
    }

    @Test
    void axiomFilterAlsoAppliesToGlobalAxioms() {
        OWLAxiom global = sub(df.getOWLThing(), a);
        OWLAxiom dependent = sub(a, b);
        Set<OWLAxiom> axioms = new HashSet<>(Arrays.asList(global, dependent));
        SyntacticLocalityModuleExtractor extractor =
                new SyntacticLocalityModuleExtractor(LocalityClass.BOTTOM, axioms.stream());
        Set<OWLAxiom> result = extractor.extract(Stream.empty(),
                Optional.of(axiom -> !axiom.equals(global))).collect(Collectors.toSet());
        assertTrue(result.isEmpty());
        assertEquals(axioms, extractor.extract(Stream.empty()).collect(Collectors.toSet()));
    }

    @Test
    void cachedGlobalAxiomsAreFilteredPerInvocation() {
        OWLAxiom global = sub(df.getOWLThing(), a);
        SyntacticLocalityModuleExtractor extractor =
                new SyntacticLocalityModuleExtractor(LocalityClass.BOTTOM, Stream.of(global));
        Predicate<OWLAxiom> rejectAll = axiom -> false;
        assertTrue(extractor.extract(Stream.empty(), Optional.of(rejectAll))
                .collect(Collectors.toSet()).isEmpty());
        assertEquals(Collections.singleton(global),
                extractor.extract(Stream.empty()).collect(Collectors.toSet()));
    }

    @Test
    void indexedExtractionMatchesIndependentFullScanFixpoint() {
        Set<OWLAxiom> base = new HashSet<>(List.of(
                sub(df.getOWLThing(), a),
                sub(a, b),
                sub(b, c),
                sub(c, d),
                sub(e, df.getOWLNothing()),
                sub(df.getOWLThing(), df.getOWLNothing()),
                sub(a, a)));
        OWLEntity[] choices = {a, b, c, e, unknown};

        for (LocalityClass kind : LocalityClass.values()) {
            SyntacticLocalityModuleExtractor extractor =
                    new SyntacticLocalityModuleExtractor(kind, base.stream());
            for (int mask = 0; mask < (1 << choices.length); mask++) {
                Set<OWLEntity> seed = new HashSet<>();
                for (int bit = 0; bit < choices.length; bit++) {
                    if ((mask & (1 << bit)) != 0) {
                        seed.add(choices[bit]);
                    }
                }
                Set<OWLAxiom> actual = extractor.extract(seed.stream())
                        .collect(Collectors.toSet());
                Set<OWLAxiom> expected = kind == LocalityClass.STAR
                        ? fullScanStar(base, seed)
                        : fullScan(base, seed,
                                kind == LocalityClass.BOTTOM
                                        ? SyntacticLocalityEvaluator.BOTTOM
                                        : SyntacticLocalityEvaluator.TOP);
                assertEquals(expected, actual,
                        "locality=" + kind + ", signature=" + seed);
            }
        }
    }

    private static Set<OWLAxiom> fullScan(Set<OWLAxiom> base, Set<OWLEntity> seed,
                                           LocalityEvaluator evaluator) {
        Set<OWLAxiom> module = new HashSet<>();
        Set<OWLEntity> signature = new HashSet<>(seed);
        boolean changed;
        do {
            changed = false;
            for (OWLAxiom axiom : base) {
                if (!module.contains(axiom) && !evaluator.isLocal(axiom, signature)) {
                    module.add(axiom);
                    axiom.signature().forEach(signature::add);
                    changed = true;
                }
            }
        } while (changed);
        return module;
    }

    private static Set<OWLAxiom> fullScanStar(Set<OWLAxiom> base, Set<OWLEntity> seed) {
        Set<OWLAxiom> module = fullScan(base, seed, SyntacticLocalityEvaluator.BOTTOM);
        LocalityEvaluator evaluator = SyntacticLocalityEvaluator.TOP;
        int previousSize;
        do {
            previousSize = module.size();
            module = fullScan(module, seed, evaluator);
            evaluator = evaluator == SyntacticLocalityEvaluator.TOP
                    ? SyntacticLocalityEvaluator.BOTTOM
                    : SyntacticLocalityEvaluator.TOP;
        } while (previousSize != module.size());
        return module;
    }

    @FunctionalInterface
    private interface AnonymousExtractorCheck {
        void run(LocalityModuleExtractor extractor) throws Exception;
    }

    private OWLNamedIndividual anonymousTestName(String name) {
        return df.getOWLNamedIndividual(IRI.create("urn:locality-module:anonymous#" + name));
    }

    private OWLObjectProperty anonymousTestRole(String name) {
        return df.getOWLObjectProperty(IRI.create("urn:locality-module:anonymous#" + name));
    }

    private OWLDataProperty anonymousTestDataRole(String name) {
        return df.getOWLDataProperty(IRI.create("urn:locality-module:anonymous#" + name));
    }

    private Stream<DynamicTest> anonymousExtractorCases(String label,
                                                        List<OWLAxiom> source, AnonymousExtractorCheck check) {
        List<OWLAxiom> snapshot = new ArrayList<>(source);
        AnonymousExtractorCheck checked = extractor -> {
            assertEquals(new HashSet<>(snapshot),
                    extractor.axiomBase().collect(Collectors.toSet()),
                    "The constructor changed the source axioms or anonymous node IDs");
            check.run(extractor);
        };
        return Stream.of(false, true).flatMap(semantic ->
                Arrays.stream(LocalityClass.values()).map(kind -> dynamicTest(
                        label + "/" + (semantic ? "semantic/" : "syntactic/") + kind,
                        () -> {
                            if (!semantic) {
                                checked.run(new SyntacticLocalityModuleExtractor(
                                        kind, snapshot.stream()));
                                return;
                            }
                            OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
                            try (SemanticLocalityEvaluator bottom = new SemanticLocalityEvaluator(
                                    LocalityClass.BOTTOM, ownManager, new ReasonerFactory());
                                 SemanticLocalityEvaluator top = new SemanticLocalityEvaluator(
                                         LocalityClass.TOP, ownManager, new ReasonerFactory())) {
                                checked.run(new LocalityModuleExtractor(kind, snapshot.stream()) {
                                    @Override
                                    protected LocalityEvaluator bottomEvaluator() {
                                        return bottom;
                                    }

                                    @Override
                                    protected LocalityEvaluator topEvaluator() {
                                        return top;
                                    }
                                });
                            } finally {
                                assertEquals(0, ownManager.getOntologies().size(),
                                        "Extraction leaked an evaluator-owned ontology");
                            }
                        })));
    }

    private Set<OWLAxiom> anonymousExtract(LocalityModuleExtractor extractor,
                                           Optional<Predicate<OWLAxiom>> filter, OWLEntity... seed) {
        Set<OWLAxiom> original = extractor.axiomBase().collect(Collectors.toSet());
        Set<OWLAxiom> allowed = original.stream()
                .filter(filter.orElse(axiom -> true)).collect(Collectors.toSet());
        Set<OWLAxiom> result = extractor.extract(Arrays.stream(seed), filter)
                .collect(Collectors.toSet());
        assertAll(
                () -> assertTrue(allowed.containsAll(result),
                        () -> "Returned a filtered-out or rewritten axiom: " + result),
                () -> assertEquals(original,
                        extractor.axiomBase().collect(Collectors.toSet()),
                        "Extraction mutated its axiom base"));
        return result;
    }

    private OWLOntology anonymousReasoningOntology(OWLOntologyManager ownManager,
                                                   Collection<OWLAxiom> premises, OWLAxiom... goals)
            throws OWLOntologyCreationException {
        Set<OWLAxiom> declared = new HashSet<>(premises);
        Stream.concat(premises.stream(), Arrays.stream(goals))
                .flatMap(OWLAxiom::signature).filter(entity -> !entity.isBuiltIn())
                .map(df::getOWLDeclarationAxiom).forEach(declared::add);
        OWLOntology ontology = ownManager.createOntology(declared);
        OWLProfileReport profile = new OWL2DLProfile().checkOntology(ontology);
        assertTrue(profile.isInProfile(), () -> "Invalid OWL 2 DL fixture: " + profile);
        return ontology;
    }

    private void assertAnonymousEntailment(boolean expected,
                                           Collection<OWLAxiom> premises, OWLAxiom goal)
            throws OWLOntologyCreationException {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        OWLReasoner reasoner = null;
        try {
            OWLOntology ontology = anonymousReasoningOntology(ownManager, premises, goal);
            reasoner = new ReasonerFactory().createReasoner(ontology);
            assertTrue(reasoner.isConsistent(),
                    "This entailment fixture must be consistent; do not pass by explosion");
            assertTrue(reasoner.isEntailmentCheckingSupported(goal.getAxiomType()),
                    "The test reasoner cannot check this consequence");
            assertEquals(expected, reasoner.isEntailed(goal),
                    () -> "Entailment of " + goal + " from " + premises);
        } finally {
            try {
                if (reasoner != null) {
                    reasoner.dispose();
                }
            } finally {
                new ArrayList<>(ownManager.getOntologies()).forEach(ownManager::removeOntology);
            }
        }
    }

    private void assertAnonymousConsistency(boolean expected,
                                            Collection<OWLAxiom> premises) throws OWLOntologyCreationException {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        OWLReasoner reasoner = null;
        try {
            OWLOntology ontology = anonymousReasoningOntology(ownManager, premises);
            reasoner = new ReasonerFactory().createReasoner(ontology);
            assertEquals(expected, reasoner.isConsistent(),
                    () -> "Consistency of " + premises);
        } finally {
            try {
                if (reasoner != null) {
                    reasoner.dispose();
                }
            } finally {
                new ArrayList<>(ownManager.getOntologies()).forEach(ownManager::removeOntology);
            }
        }
    }

    private Set<OWLAxiom> assertAnonymousConsequencePreserved(
            LocalityModuleExtractor extractor, Collection<OWLAxiom> source,
            OWLAxiom consequence, OWLEntity... seed) throws OWLOntologyCreationException {
        Set<OWLEntity> signature = new HashSet<>(Arrays.asList(seed));
        assertTrue(consequence.signature().filter(entity -> !entity.isBuiltIn())
                        .allMatch(signature::contains),
                "The tested consequence must be over the seed signature");
        assertAnonymousEntailment(true, source, consequence);
        Set<OWLAxiom> module = anonymousExtract(extractor, Optional.empty(), seed);
        assertAnonymousEntailment(true, module, consequence);
        return module;
    }

// -------------------------------------------------------------------------
// Shared witnesses: nominal anchors must not be discarded independently.
// -------------------------------------------------------------------------

    @TestFactory
    Stream<DynamicTest> anonymousNominalAnchorPreservesClassAssertion() {
        OWLNamedIndividual i = anonymousTestName("class-i");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-class-x");
        OWLAxiom anchor = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x);
        OWLAxiom fact = df.getOWLClassAssertionAxiom(a, x);
        OWLAxiom goal = df.getOWLClassAssertionAxiom(a, i);
        List<OWLAxiom> source = Arrays.asList(anchor, fact);
        return anonymousExtractorCases("class-assertion", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, a, i));
    }

    @TestFactory
    Stream<DynamicTest> anonymousNominalAnchorPreservesDataAssertion() {
        OWLNamedIndividual i = anonymousTestName("data-i");
        OWLDataProperty p = anonymousTestDataRole("data-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-data-x");
        OWLAxiom anchor = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x);
        OWLAxiom fact = df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1));
        OWLAxiom goal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        List<OWLAxiom> source = Arrays.asList(anchor, fact);
        return anonymousExtractorCases("data-assertion", source, extractor -> {
            // Without the anchor, an arbitrary subject can carry the value.
            assertAnonymousEntailment(false, Collections.singleton(fact), goal);
            assertAnonymousConsequencePreserved(extractor, source, goal, p, i);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousObjectPropertySubjectPreservesItsAnchor() {
        OWLNamedIndividual i = anonymousTestName("subject-i");
        OWLNamedIndividual j = anonymousTestName("subject-j");
        OWLObjectProperty r = anonymousTestRole("subject-r");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-subject-x");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLObjectPropertyAssertionAxiom(r, x, j));
        OWLAxiom goal = df.getOWLObjectPropertyAssertionAxiom(r, i, j);
        return anonymousExtractorCases("object-subject", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, r, i, j));
    }

    @TestFactory
    Stream<DynamicTest> anonymousObjectPropertyTargetPreservesItsAnchor() {
        OWLNamedIndividual i = anonymousTestName("target-i");
        OWLNamedIndividual j = anonymousTestName("target-j");
        OWLObjectProperty r = anonymousTestRole("target-r");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-target-x");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLObjectPropertyAssertionAxiom(r, j, x));
        OWLAxiom goal = df.getOWLObjectPropertyAssertionAxiom(r, j, i);
        return anonymousExtractorCases("object-target", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, r, i, j));
    }

    @TestFactory
    Stream<DynamicTest> anonymousObjectPropertyEndpointsPreserveBothAnchors() {
        OWLNamedIndividual i = anonymousTestName("endpoints-i");
        OWLNamedIndividual j = anonymousTestName("endpoints-j");
        OWLObjectProperty r = anonymousTestRole("endpoints-r");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-endpoints-x");
        OWLAnonymousIndividual y = df.getOWLAnonymousIndividual("module-endpoints-y");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLObjectPropertyAssertionAxiom(r, x, y),
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(j), y));
        OWLAxiom goal = df.getOWLObjectPropertyAssertionAxiom(r, i, j);
        return anonymousExtractorCases("two-anonymous-endpoints", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, r, i, j));
    }

    @TestFactory
    Stream<DynamicTest> anonymousObjectChainPreservesDataValueAtItsLeaf() {
        OWLNamedIndividual i = anonymousTestName("chain-i");
        OWLObjectProperty r = anonymousTestRole("chain-r");
        OWLObjectProperty s = anonymousTestRole("chain-s");
        OWLDataProperty p = anonymousTestDataRole("chain-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-chain-x");
        OWLAnonymousIndividual y = df.getOWLAnonymousIndividual("module-chain-y");
        OWLAnonymousIndividual z = df.getOWLAnonymousIndividual("module-chain-z");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLObjectPropertyAssertionAxiom(r, x, y),
                df.getOWLObjectPropertyAssertionAxiom(s, y, z),
                df.getOWLDataPropertyAssertionAxiom(p, z, df.getOWLLiteral(1)));
        OWLClassExpression path = df.getOWLObjectSomeValuesFrom(r,
                df.getOWLObjectSomeValuesFrom(s, df.getOWLDataHasValue(p, df.getOWLLiteral(1))));
        OWLAxiom goal = df.getOWLClassAssertionAxiom(path, i);
        return anonymousExtractorCases("object-chain-with-data-leaf", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, r, s, p, i));
    }

    @TestFactory
    Stream<DynamicTest> anonymousHasSelfAssertionPreservesItsAnchor() {
        OWLNamedIndividual i = anonymousTestName("self-i");
        OWLObjectProperty r = anonymousTestRole("self-r");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-self-x");
        OWLClassExpression self = df.getOWLObjectHasSelf(r);
        // Use a class assertion, not an anonymous object-property self-loop:
        // anonymous object-property assertion graphs must be forests in OWL 2 DL.
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLClassAssertionAxiom(self, x));
        OWLAxiom goal = df.getOWLClassAssertionAxiom(self, i);
        return anonymousExtractorCases("has-self", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, r, i));
    }

    @TestFactory
    Stream<DynamicTest> anonymousIndividuallyLocalAnchorsJointlyForceNamedEquality() {
        OWLNamedIndividual i = anonymousTestName("equality-i");
        OWLNamedIndividual j = anonymousTestName("equality-j");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-equality-x");
        OWLAxiom left = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x);
        OWLAxiom right = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(j), x);
        OWLAxiom goal = df.getOWLSameIndividualAxiom(i, j);
        List<OWLAxiom> source = Arrays.asList(left, right);
        return anonymousExtractorCases("all-members-individually-local", source, extractor -> {
            // Each existential assertion alone is valid. Together they imply i=j.
            // A repair triggered ONLY by a non-local member misses this component.
            assertAnonymousEntailment(false, Collections.singleton(left), goal);
            assertAnonymousEntailment(false, Collections.singleton(right), goal);
            assertAnonymousConsequencePreserved(extractor, source, goal, i, j);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousInconsistentSharedWitnessIsPreservedForEmptySeed() {
        OWLNamedIndividual i = anonymousTestName("inconsistent-i");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-inconsistent-x");
        OWLClassExpression nominal = df.getOWLObjectOneOf(i);
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(nominal, x),
                df.getOWLClassAssertionAxiom(df.getOWLObjectComplementOf(nominal), x));
        return anonymousExtractorCases("inconsistent-empty-seed", source, extractor -> {
            // The SAME witness cannot both equal i and differ from i.
            assertAnonymousConsistency(false, source);
            Set<OWLAxiom> module = anonymousExtract(extractor, Optional.empty());
            assertAnonymousConsistency(false, module);
        });
    }

// -------------------------------------------------------------------------
// Signature closure, node IDs, and data-value constraints.
// -------------------------------------------------------------------------

    @TestFactory
    Stream<DynamicTest> anonymousDataWitnessStartsAndCompletesOrdinarySignatureClosure() {
        OWLNamedIndividual i = anonymousTestName("closure-i");
        OWLDataProperty p = anonymousTestDataRole("closure-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-closure-x");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1)),
                df.getOWLDataPropertyDomainAxiom(p, a),
                sub(a, b));
        OWLAxiom goal = df.getOWLClassAssertionAxiom(b, i);
        // Neither p nor A is in the seed. Keeping a witness-related axiom must
        // still trigger the ordinary class/data-property signature fixed point.
        return anonymousExtractorCases("data-domain-signature-closure", source, extractor ->
                assertAnonymousConsequencePreserved(extractor, source, goal, b, i));
    }

    @TestFactory
    Stream<DynamicTest> anonymousRepeatedNodeIdDenotesTheSameSharedWitness() {
        OWLNamedIndividual i = anonymousTestName("node-id-i");
        OWLDataProperty p = anonymousTestDataRole("node-id-p");
        OWLAnonymousIndividual x1 = df.getOWLAnonymousIndividual("module-repeated-id");
        OWLAnonymousIndividual x2 = df.getOWLAnonymousIndividual("module-repeated-id");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x1),
                df.getOWLDataPropertyAssertionAxiom(p, x2, df.getOWLLiteral(1)));
        OWLAxiom goal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        return anonymousExtractorCases("same-node-id", source, extractor -> {
            // This concerns a SINGLE axiom base, not equal IDs across imports.
            // Do not assume the factory returns different Java object instances.
            assertEquals(x1, x2);
            assertAnonymousConsequencePreserved(extractor, source, goal, p, i);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousDifferentNodeIdsDoNotTransferADataAssertion() {
        OWLNamedIndividual i = anonymousTestName("different-ids-i");
        OWLDataProperty p = anonymousTestDataRole("different-ids-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-different-x");
        OWLAnonymousIndividual y = df.getOWLAnonymousIndividual("module-different-y");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLDataPropertyAssertionAxiom(p, y, df.getOWLLiteral(1)));
        OWLAxiom notEntailed = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        return anonymousExtractorCases("different-node-ids", source, extractor -> {
            // Different IDs do not force different domain elements, but neither
            // do they force equality. This is NON-entailment, not an inequality.
            assertAnonymousEntailment(false, source, notEntailed);
            Set<OWLAxiom> module = anonymousExtract(extractor, Optional.empty(), p, i);
            assertAnonymousEntailment(false, module, notEntailed);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousSharedSubjectKeepsConflictingFunctionalDataValues() {
        OWLDataProperty p = anonymousTestDataRole("functional-shared-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-functional-shared-x");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLFunctionalDataPropertyAxiom(p),
                df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1)),
                df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(2)));
        return anonymousExtractorCases("functional-data-shared-subject", source, extractor -> {
            assertAnonymousConsistency(false, source);
            Set<OWLAxiom> module = anonymousExtract(extractor, Optional.empty(), p);
            assertAnonymousConsistency(false, module);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousSeparateSubjectsAllowDifferentFunctionalDataValues() {
        OWLDataProperty p = anonymousTestDataRole("functional-separate-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-functional-separate-x");
        OWLAnonymousIndividual y = df.getOWLAnonymousIndividual("module-functional-separate-y");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLFunctionalDataPropertyAxiom(p),
                df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1)),
                df.getOWLDataPropertyAssertionAxiom(p, y, df.getOWLLiteral(2)));
        return anonymousExtractorCases("functional-data-separate-subjects", source, extractor -> {
            assertAnonymousConsistency(true, source);
            Set<OWLAxiom> module = anonymousExtract(extractor, Optional.empty(), p);
            // With p retained, all three axioms are non-local in both modes.
            assertEquals(new HashSet<>(source), module);
            assertAnonymousConsistency(true, module);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousAnnotatedAnchorIsReturnedAsTheOriginalAxiom() {
        OWLNamedIndividual i = anonymousTestName("annotated-i");
        OWLDataProperty p = anonymousTestDataRole("annotated-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-annotated-x");
        OWLAxiom anchor = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x)
                .getAnnotatedAxiom(Collections.singleton(df.getOWLAnnotation(
                        df.getRDFSComment(), df.getOWLLiteral("Keep the original witness axiom"))));
        OWLAxiom fact = df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1));
        OWLAxiom goal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        List<OWLAxiom> source = Arrays.asList(anchor, fact);
        return anonymousExtractorCases("annotated-anchor", source, extractor -> {
            Set<OWLAxiom> module = assertAnonymousConsequencePreserved(
                    extractor, source, goal, p, i);
            assertTrue(module.contains(anchor), "Retain the original annotated anchor");
        });
    }

// -------------------------------------------------------------------------
// Filtering, repeated calls, and axiom iteration order.
// -------------------------------------------------------------------------

    @TestFactory
    Stream<DynamicTest> anonymousWitnessFiltersAreAppliedOnEveryInvocation() {
        OWLNamedIndividual i = anonymousTestName("filter-i");
        OWLDataProperty p = anonymousTestDataRole("filter-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-filter-x");
        OWLAxiom anchor = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x);
        OWLAxiom fact = df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1));
        OWLAxiom goal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        List<OWLAxiom> source = Arrays.asList(anchor, fact);
        return anonymousExtractorCases("per-call-witness-filters", source, extractor ->
                assertAll(
                        () -> assertAnonymousConsequencePreserved(extractor, source, goal, p, i),
                        () -> {
                            Set<OWLAxiom> module = anonymousExtract(extractor,
                                    Optional.of(axiom -> !axiom.equals(anchor)), p, i);
                            assertEquals(Collections.singleton(fact), module);
                            assertAnonymousEntailment(false, module, goal);
                        },
                        () -> {
                            Set<OWLAxiom> module = anonymousExtract(extractor,
                                    Optional.of(axiom -> !axiom.equals(fact)), p, i);
                            // The isolated anchor may be retained conservatively
                            // or omitted. Neither outcome may entail the data fact.
                            assertAnonymousEntailment(false, module, goal);
                        },
                        () -> assertTrue(anonymousExtract(extractor,
                                Optional.of(axiom -> false), p, i).isEmpty()),
                        () -> assertAnonymousConsequencePreserved(extractor, source, goal, p, i)));
    }

    @TestFactory
    Stream<DynamicTest> anonymousFilteredBridgeDoesNotExpandTheWorkingSignature() {
        OWLDataProperty p = anonymousTestDataRole("filtered-bridge-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-filtered-bridge-x");
        OWLAxiom fact = df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1));
        OWLAxiom bridge = df.getOWLClassAssertionAxiom(a, x);
        OWLAxiom dependent = df.getOWLEquivalentClassesAxiom(a, b);
        List<OWLAxiom> source = Arrays.asList(dependent, bridge, fact);
        return anonymousExtractorCases("filtered-bridge-signature", source, extractor -> {
            // A and B occur only in the filtered bridge and the A=B axiom.
            // A=B is local in either mode while neither class is in the signature.
            // This is an indexed-closure/filter regression, not a minimality claim
            // about all possible sound module extraction algorithms.
            Set<OWLAxiom> module = anonymousExtract(extractor,
                    Optional.of(axiom -> !axiom.equals(bridge)), p);
            assertEquals(Collections.singleton(fact), module);
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousRepeatedExtractionsWithDifferentSeedsDoNotLeakState() {
        OWLNamedIndividual i = anonymousTestName("repeat-i");
        OWLNamedIndividual j = anonymousTestName("repeat-j");
        OWLDataProperty p = anonymousTestDataRole("repeat-p");
        OWLDataProperty q = anonymousTestDataRole("repeat-q");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-repeat-x");
        OWLAnonymousIndividual y = df.getOWLAnonymousIndividual("module-repeat-y");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1)),
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(j), y),
                df.getOWLDataPropertyAssertionAxiom(q, y, df.getOWLLiteral(2)));
        OWLAxiom firstGoal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        OWLAxiom secondGoal = df.getOWLDataPropertyAssertionAxiom(q, j, df.getOWLLiteral(2));
        return anonymousExtractorCases("different-seeds-same-extractor", source, extractor -> {
            Set<OWLAxiom> first = assertAnonymousConsequencePreserved(
                    extractor, source, firstGoal, p, i);
            assertAnonymousConsequencePreserved(extractor, source, secondGoal, q, j);
            Set<OWLAxiom> again = assertAnonymousConsequencePreserved(
                    extractor, source, firstGoal, p, i);
            assertEquals(first, again, "An intervening seed changed the earlier module");
            // No requirement to omit the other component: BOTTOM can legitimately
            // retain both because positive data assertions are globally non-local.
        });
    }

    @TestFactory
    Stream<DynamicTest> anonymousWitnessClosureIsIndependentOfSourceOrder() {
        OWLNamedIndividual i = anonymousTestName("order-i");
        OWLDataProperty p = anonymousTestDataRole("order-p");
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("module-order-x");
        List<OWLAxiom> source = Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x),
                df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1)),
                df.getOWLDataPropertyDomainAxiom(p, a),
                sub(a, b));
        OWLAxiom goal = df.getOWLClassAssertionAxiom(b, i);
        List<OWLAxiom> reversed = new ArrayList<>(source);
        Collections.reverse(reversed);
        List<OWLAxiom> rotated = new ArrayList<>(source);
        Collections.rotate(rotated, 1);
        return Stream.of(source, reversed, rotated).flatMap(order -> {
            String label = order == source ? "forward" : order == reversed ? "reverse" : "rotate";
            return anonymousExtractorCases("source-order-" + label, order, extractor -> {
                Set<OWLAxiom> module = assertAnonymousConsequencePreserved(
                        extractor, source, goal, b, i);
                // All four original axioms are necessary for B(i) in this fixture.
                assertEquals(new HashSet<>(source), module);
            });
        });
    }

}
