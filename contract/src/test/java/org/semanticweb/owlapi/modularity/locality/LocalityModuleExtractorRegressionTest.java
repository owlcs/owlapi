package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;

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
}
