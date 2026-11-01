package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.AtomicDecomposition;
import org.semanticweb.owlapi.modularity.ModuleExtractor;

/**
 * Regressions for the atomic-decomposition algorithm, independent of a specific
 * locality checker. The synthetic extractor supplies a consistent, nested family
 * of modules, so each expected atom and dependency is unambiguous.
 */
class AtomicDecompositionRegressionTest {

    private final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
    private final OWLDataFactory df = manager.getOWLDataFactory();

    private OWLAxiom axiom(String name) {
        return df.getOWLClassAssertionAxiom(df.getOWLClass(IRI.create("urn:atomic-decomposition:regression#" + name)), df.getOWLNamedIndividual("urn:atomic-decomposition:regression#" + name + "_ind"));
    }

    private static Set<OWLAxiom> axioms(AtomicDecomposition.Atom atom) {
        return atom.axioms().collect(Collectors.toSet());
    }

    private static Set<AtomicDecomposition.Atom> atoms(Stream<AtomicDecomposition.Atom> stream) {
        return stream.collect(Collectors.toSet());
    }

    private static class TableExtractor implements ModuleExtractor, AutoCloseable {

        private final Set<OWLAxiom> base;
        private final Map<OWLEntity, Set<OWLAxiom>> modules = new HashMap<>();
        private int closeCount;
        private RuntimeException extractionFailure;
        private boolean failOnClose;

        TableExtractor(OWLAxiom... axioms) {
            base = new HashSet<>(Arrays.asList(axioms));
        }

        TableExtractor module(OWLAxiom key, OWLAxiom... members) {
            Set<OWLEntity> signature = key.signature().collect(Collectors.toSet());
            if (signature.size() != 2) {
                throw new IllegalArgumentException("Test axiom must have one entity");
            }
            modules.put(signature.iterator().next(), new HashSet<>(Arrays.asList(members)));
            return this;
        }

        @Override
        public Stream<OWLAxiom> axiomBase() {
            return base.stream();
        }

        @Override
        public Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
                Optional<Predicate<OWLAxiom>> filter) {
            if (extractionFailure != null) {
                throw extractionFailure;
            }
            Set<OWLAxiom> result = new HashSet<>();
            signature.forEach(entity ->
                result.addAll(modules.getOrDefault(entity, Collections.emptySet())));
            return result.stream().filter(filter.orElse(axiom -> true));
        }

        @Override
        public void close() {
            closeCount++;
            if (failOnClose) {
                throw new IllegalStateException("cleanup failure");
            }
        }
    }

    @Test
    void fullRootModuleStillCreatesAllDependencies() {
        OWLAxiom a = axiom("A");
        OWLAxiom b = axiom("B");
        OWLAxiom c = axiom("C");
        TableExtractor extractor = new TableExtractor(a, b, c)
            .module(a, a, b, c)
            .module(b, b, c)
            .module(c, c);

        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.of(a, b, c), ignored -> extractor);
        AtomicDecomposition.Atom aa = decomposition.atomOf(a);
        AtomicDecomposition.Atom ab = decomposition.atomOf(b);
        AtomicDecomposition.Atom ac = decomposition.atomOf(c);

        assertEquals(3, decomposition.atoms().count());
        assertNotSame(aa, ab);
        assertNotSame(ab, ac);
        assertEquals(Set.of(ab, ac), atoms(aa.dependencies()));
        assertEquals(Set.of(ac), atoms(ab.dependencies()));
        assertEquals(Set.of(aa, ab), atoms(ac.dependents()));
        assertEquals(1, extractor.closeCount);
    }

    @Test
    void directDependenciesAreTransitiveReductionNotEmpty() {
        OWLAxiom a = axiom("A");
        OWLAxiom b = axiom("B");
        OWLAxiom c = axiom("C");
        TableExtractor extractor = new TableExtractor(a, b, c)
            .module(a, a, b, c)
            .module(b, b, c)
            .module(c, c);
        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.of(c, b, a), ignored -> extractor);
        AtomicDecomposition.Atom aa = decomposition.atomOf(a);
        AtomicDecomposition.Atom ab = decomposition.atomOf(b);
        AtomicDecomposition.Atom ac = decomposition.atomOf(c);

        assertEquals(Set.of(ab), atoms(aa.directDependencies()));
        assertEquals(Set.of(ac), atoms(ab.directDependencies()));
        assertEquals(Set.of(ab), atoms(ac.directDependents()));
        assertEquals(Set.of(aa), atoms(ab.directDependents()));
        assertTrue(aa.directlyDependsOn(ab));
        assertFalse(aa.directlyDependsOn(ac));
        assertTrue(ac.isDirectDependencyOf(ab));
        assertFalse(ac.isDirectDependencyOf(aa));
    }

    @Test
    void equalModulesCreateOneAtom() {
        OWLAxiom a = axiom("A");
        OWLAxiom b = axiom("B");
        OWLAxiom c = axiom("C");
        TableExtractor extractor = new TableExtractor(a, b, c)
            .module(a, a, b, c)
            .module(b, a, b, c)
            .module(c, c);
        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.of(a, b, c), ignored -> extractor);

        assertSame(decomposition.atomOf(a), decomposition.atomOf(b));
        assertEquals(Set.of(a, b), axioms(decomposition.atomOf(a)));
        assertEquals(Set.of(decomposition.atomOf(c)),
            atoms(decomposition.atomOf(a).dependencies()));
    }

    @Test
    void emptyModulesCreateOneTautologyAtom() {
        OWLAxiom a = axiom("A");
        OWLAxiom t = axiom("T");
        OWLAxiom u = axiom("U");
        TableExtractor extractor = new TableExtractor(a, t, u)
            .module(a, a)
            .module(t)
            .module(u);
        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.of(a, t, u), ignored -> extractor);

        assertSame(decomposition.atomOf(t), decomposition.atomOf(u));
        assertEquals(Set.of(t, u), axioms(decomposition.atomOf(t)));
        assertEquals(2, decomposition.atoms().count());
        assertEquals(0, decomposition.moduleToSignatureOf(t).count());
        assertTrue(atoms(decomposition.atomOf(t).dependencies()).isEmpty());
        assertTrue(atoms(decomposition.atomOf(t).dependents()).isEmpty());
    }

    @Test
    void tautologyWithNonEmptyModuleIsNotPutIntoAnAtom() {
        // t is a tautology: it is not in the module for its own signature. That module
        // is not empty, though, because another axiom is non-local for it (as for
        // "A SubClassOf owl:Thing" next to "A SubClassOf B"). An axiom must never share
        // an atom with axioms of a module it is not part of.
        OWLAxiom a = axiom("A");
        OWLAxiom t = axiom("T");
        TableExtractor extractor = new TableExtractor(a, t)
            .module(a, a)
            .module(t, a);
        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.of(t, a), ignored -> extractor);

        assertEquals(Set.of(a), axioms(decomposition.atomOf(a)));
        assertFalse(decomposition.atomOf(t).containsAxiom(a));
        assertTrue(atoms(decomposition.atomOf(a).dependencies()).isEmpty());
        assertTrue(atoms(decomposition.atomOf(a).dependents()).isEmpty());
    }

    @Test
    void decompositionOfAnEmptyAxiomBaseIsEmpty() {
        TableExtractor extractor = new TableExtractor();
        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.empty(), ignored -> extractor);
        assertEquals(0, decomposition.atoms().count());
        assertEquals(1, extractor.closeCount);
    }

    @Test
    void atomAndDependenciesAgreeWithEachComputedNonemptyModule() {
        OWLAxiom a = axiom("A");
        OWLAxiom b = axiom("B");
        OWLAxiom c = axiom("C");
        TableExtractor extractor = new TableExtractor(a, b, c)
            .module(a, a, b, c)
            .module(b, b, c)
            .module(c, c);
        AtomicDecomposition decomposition = new AtomicDecomposition(
            Stream.of(a, b, c), ignored -> extractor);
        for (OWLAxiom alpha : Set.of(a, b, c)) {
            AtomicDecomposition.Atom atom = decomposition.atomOf(alpha);
            Set<OWLAxiom> closure = axioms(atom);
            atom.dependencies().forEach(dep -> closure.addAll(axioms(dep)));
            assertEquals(decomposition.moduleToSignatureOf(alpha).collect(Collectors.toSet()),
                closure);
        }
    }

    @Test
    void decomposeFailureStillClosesExtractorWithoutMaskingFailure() {
        OWLAxiom a = axiom("A");
        TableExtractor extractor = new TableExtractor(a);
        RuntimeException failure = new IllegalArgumentException("extraction failure");
        extractor.extractionFailure = failure;
        extractor.failOnClose = true;
        RuntimeException actual = assertThrows(RuntimeException.class,
            () -> new AtomicDecomposition(Stream.of(a), ignored -> extractor));
        assertSame(failure, actual);
        assertEquals(1, extractor.closeCount);
        assertEquals(1, actual.getSuppressed().length);
    }

    @Test
    void nullExtractorIsRejectedInConstructor() {
        OWLAxiom a = axiom("A");
        assertThrows(NullPointerException.class,
            () -> new AtomicDecomposition(Stream.of(a), ignored -> null));
    }

    @Test
    void ontologyAndCustomExtractorConstructorIncludesImports() throws Exception {
        OWLOntology imported = manager.createOntology(
            IRI.create("urn:atomic-decomposition:imported"));
        OWLOntology root = manager.createOntology(
            IRI.create("urn:atomic-decomposition:root"));
        OWLAxiom axiom = axiom("Imported");
        manager.addAxiom(imported, axiom);
        manager.applyChange(new AddImport(root,
            df.getOWLImportsDeclaration(imported.getOntologyID().getOntologyIRI().get())));

        AtomicDecomposition decomposition = new AtomicDecomposition(root,
            stream -> new TableExtractor(stream.toArray(OWLAxiom[]::new))
                .module(axiom, axiom));
        assertTrue(decomposition.containsAxiom(axiom));
        assertEquals(Set.of(axiom), axioms(decomposition.atomOf(axiom)));
    }

    // --- With the real locality-based extractor ---

    private OWLAxiom subClassOf(String sub, String sup) {
        return df.getOWLSubClassOfAxiom(
            df.getOWLClass(IRI.create("urn:atomic-decomposition:regression#" + sub)),
            df.getOWLClass(IRI.create("urn:atomic-decomposition:regression#" + sup)));
    }

    private AtomicDecomposition star(OWLAxiom... axioms) {
        return new AtomicDecomposition(Stream.of(axioms),
            s -> new SyntacticLocalityModuleExtractor(LocalityClass.STAR, s, df));
    }

    @Test
    void tautologiesFormOneIsolatedAtom() {
        OWLAxiom tautology = df.getOWLSubClassOfAxiom(
            df.getOWLClass(IRI.create("urn:atomic-decomposition:regression#A")), df.getOWLThing());
        OWLAxiom assertion = axiom("A");
        AtomicDecomposition decomposition = star(tautology, assertion);
        assertEquals(Set.of(tautology), decomposition.tautologies().collect(Collectors.toSet()));
        AtomicDecomposition.Atom tautologies = decomposition.atomOf(tautology);
        assertEquals(2, decomposition.atoms().count());
        assertFalse(tautologies.dependencies().findAny().isPresent());
        assertFalse(tautologies.dependents().findAny().isPresent());
        assertFalse(star(assertion).tautologies().findAny().isPresent());
    }

    @Test
    void axiomsSharingAnAnonymousIndividualStayInOneAtom() {
        // The pair is only ever included as a whole: in the module for {A}, and in
        // none for {}. Thing(_:x) alone would be a tautology, but not as part of it.
        var x = df.getOWLAnonymousIndividual();
        OWLAxiom thing = df.getOWLClassAssertionAxiom(df.getOWLThing(), x);
        OWLAxiom typed = df.getOWLClassAssertionAxiom(
            df.getOWLClass(IRI.create("urn:atomic-decomposition:regression#A")), x);
        AtomicDecomposition decomposition = star(thing, typed);
        assertFalse(decomposition.tautologies().findAny().isPresent());
        assertSame(decomposition.atomOf(thing), decomposition.atomOf(typed));
        assertEquals(Set.of(thing, typed), axioms(decomposition.atomOf(thing)));
    }

    @Test
    void ontologyConstructorRollsUpAnonymousIndividualsWithItsFactory() throws Exception {
        // A(_:x) is TOP-local for {B, C}, so it is not part of that atom's module.
        // Without a factory it would be kept in every module, creating a dependency.
        OWLAxiom anonymous = df.getOWLClassAssertionAxiom(
            df.getOWLClass(IRI.create("urn:atomic-decomposition:regression#A")),
            df.getOWLAnonymousIndividual());
        OWLAxiom ordinary = subClassOf("B", "C");
        OWLOntology ontology = manager.createOntology(Stream.of(anonymous, ordinary));
        AtomicDecomposition decomposition = new AtomicDecomposition(ontology);
        assertFalse(decomposition.atomOf(ordinary).dependencies().findAny().isPresent());
        @SuppressWarnings("deprecation")
        AtomicDecomposition withoutFactory =
            new AtomicDecomposition(Stream.of(anonymous, ordinary));
        assertEquals(Set.of(withoutFactory.atomOf(anonymous)),
            atoms(withoutFactory.atomOf(ordinary).dependencies()));
    }
}
