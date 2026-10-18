package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Property-based tests for syntactic locality over a deterministic,
 * randomly generated corpus of OWL 2 axioms.
 *
 * <p>Three properties need no reasoner and hold for any correct syntactic
 * evaluator: anti-monotonicity in the signature, independence of entities
 * outside the axiom's signature, and independence of axiom annotations.
 * The fourth uses the semantic evaluator as an oracle: syntactic locality
 * must imply semantic locality (soundness). The converse is not required,
 * because syntactic locality is an incomplete approximation.</p>
 *
 * <p>The corpus is fixed by {@link #SEED}; a failure message names the
 * offending axiom and signature, so failures are reproducible.</p>
 */
class SyntacticLocalityPropertyTest {

    private static final long SEED = 20261018L;
    private static final int CORPUS_SIZE = 300;
    private static final int MAX_REPORTED = 20;

    private static final SyntacticLocalityEvaluator[] SYNTACTIC = {
            SyntacticLocalityEvaluator.BOTTOM, SyntacticLocalityEvaluator.TOP };
    private static final LocalityClass[] SEMANTIC = {
            LocalityClass.BOTTOM, LocalityClass.TOP };

    private static OWLDataFactory f;
    private static OWLClass a;
    private static OWLClass b;
    private static OWLClass c;
    private static OWLObjectProperty r;
    private static OWLObjectProperty s;
    private static OWLDataProperty p;
    private static OWLDataProperty q;
    private static OWLNamedIndividual i;
    private static OWLNamedIndividual j;
    private static List<OWLAxiom> corpus;
    private static List<Set<OWLEntity>> signatures;

    @BeforeAll
    static void setUp() {
        f = OWLManager.createOWLOntologyManager().getOWLDataFactory();
        a = f.getOWLClass(iri("A"));
        b = f.getOWLClass(iri("B"));
        c = f.getOWLClass(iri("C"));
        r = f.getOWLObjectProperty(iri("r"));
        s = f.getOWLObjectProperty(iri("s"));
        p = f.getOWLDataProperty(iri("p"));
        q = f.getOWLDataProperty(iri("q"));
        i = f.getOWLNamedIndividual(iri("i"));
        j = f.getOWLNamedIndividual(iri("j"));
        corpus = new Generator(new Random(SEED)).axioms(CORPUS_SIZE);
        signatures = List.of(
                Set.of(),
                Set.of(a),
                Set.of(a, r),
                Set.of(a, b, r, p),
                Set.of(a, b, c, r, s, p, q));
    }

    private static IRI iri(String name) {
        return IRI.create("urn:syntactic-property#" + name);
    }

    /** Non-built-in entities of an axiom, in a stable order. */
    private static List<OWLEntity> ownSignature(OWLAxiom axiom) {
        return axiom.signature().filter(e -> !e.isBuiltIn())
                .sorted().collect(Collectors.toList());
    }

    private static Set<OWLEntity> subset(List<OWLEntity> universe, int mask) {
        Set<OWLEntity> result = new HashSet<>();
        for (int bit = 0; bit < universe.size(); bit++) {
            if ((mask & (1 << bit)) != 0) {
                result.add(universe.get(bit));
            }
        }
        return result;
    }

    private static void assertNoViolations(List<String> violations, String property) {
        assertTrue(violations.isEmpty(), () -> violations.size() + " violations of "
                + property + " (seed " + SEED + "):\n"
                + violations.stream().limit(MAX_REPORTED).collect(Collectors.joining("\n")));
    }

    // ------------------------------------------------------------------
    // Reasoner-free properties
    // ------------------------------------------------------------------

    @Test @Tag("metamorphic")
    void localityIsAntiMonotoneInTheSignature() {
        // Shrinking Sigma only turns more symbols into empty / universal ones,
        // so a local axiom stays local. It suffices to check every subset
        // that differs by one entity: the property is then transitive.
        List<String> violations = new ArrayList<>();
        for (OWLAxiom axiom : corpus) {
            List<OWLEntity> universe = ownSignature(axiom);
            int subsets = 1 << universe.size();
            for (SyntacticLocalityEvaluator kind : SYNTACTIC) {
                boolean[] local = new boolean[subsets];
                for (int mask = 0; mask < subsets; mask++) {
                    local[mask] = kind.isLocal(axiom, subset(universe, mask));
                }
                for (int mask = 0; mask < subsets; mask++) {
                    for (int bit = 0; local[mask] && bit < universe.size(); bit++) {
                        int smaller = mask & ~(1 << bit);
                        if (smaller != mask && !local[smaller]) {
                            violations.add(kind + ": " + axiom + " local for "
                                    + subset(universe, mask) + " but not for "
                                    + subset(universe, smaller));
                        }
                    }
                }
            }
        }
        assertNoViolations(violations, "anti-monotonicity");
    }

    @Test @Tag("metamorphic")
    void entitiesOutsideTheAxiomDoNotChangeTheResult() {
        // Locality depends only on Sigma intersected with the axiom's
        // signature. Extras include a punned class for every property of
        // the axiom (same IRI, different entity) and all built-ins.
        List<String> violations = new ArrayList<>();
        for (OWLAxiom axiom : corpus) {
            Set<OWLEntity> axiomSignature = axiom.signature().collect(Collectors.toSet());
            Set<OWLEntity> extras = new LinkedHashSet<>(List.of(
                    f.getOWLClass(iri("Unrelated")),
                    f.getOWLObjectProperty(iri("unrelated")),
                    f.getOWLDataProperty(iri("unrelatedData")),
                    f.getOWLThing(), f.getOWLNothing(),
                    f.getOWLTopObjectProperty(), f.getOWLBottomObjectProperty(),
                    f.getOWLTopDataProperty(), f.getOWLBottomDataProperty()));
            axiomSignature.stream().filter(e -> !e.isOWLClass())
                    .map(e -> f.getOWLClass(e.getIRI())).forEach(extras::add);
            extras.removeIf(e -> axiomSignature.contains(e) && !e.isBuiltIn());
            for (Set<OWLEntity> sigma : signatures) {
                Set<OWLEntity> extended = new HashSet<>(sigma);
                extended.addAll(extras);
                for (SyntacticLocalityEvaluator kind : SYNTACTIC) {
                    boolean plain = kind.isLocal(axiom, sigma);
                    boolean withExtras = kind.isLocal(axiom, extended);
                    if (plain != withExtras) {
                        violations.add(kind + ": " + axiom + " gives " + plain + " for "
                                + sigma + " but " + withExtras + " with extras");
                    }
                }
            }
        }
        assertNoViolations(violations, "signature irrelevance");
    }

    @Test @Tag("metamorphic")
    void axiomAnnotationsDoNotChangeTheResult() {
        var annotation = f.getOWLAnnotation(f.getRDFSComment(), f.getOWLLiteral("note"));
        List<String> violations = new ArrayList<>();
        for (OWLAxiom axiom : corpus) {
            OWLAxiom annotated = axiom.getAnnotatedAxiom(Set.of(annotation));
            for (Set<OWLEntity> sigma : signatures) {
                for (SyntacticLocalityEvaluator kind : SYNTACTIC) {
                    if (kind.isLocal(axiom, sigma) != kind.isLocal(annotated, sigma)) {
                        violations.add(kind + ": " + axiom + " / " + sigma);
                    }
                }
            }
        }
        assertNoViolations(violations, "annotation independence");
    }

    // ------------------------------------------------------------------
    // Soundness against the semantic evaluator
    // ------------------------------------------------------------------

    @Test @Tag("oracle") @Tag("slow")
    void syntacticLocalityImpliesSemanticLocality() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        List<String> violations = new ArrayList<>();
        int confirmed = 0;
        int unsupported = 0;
        for (int mode = 0; mode < 2; mode++) {
            try (SemanticLocalityEvaluator oracle = new SemanticLocalityEvaluator(
                    SEMANTIC[mode], manager, new ReasonerFactory())) {
                for (OWLAxiom axiom : corpus) {
                    for (Set<OWLEntity> sigma : signatures) {
                        if (!SYNTACTIC[mode].isLocal(axiom, sigma)) {
                            continue; // nothing to check: incompleteness is allowed
                        }
                        try {
                            if (oracle.isLocal(axiom, sigma)) {
                                confirmed++;
                            } else {
                                violations.add(SYNTACTIC[mode] + " claims local, but it is "
                                        + "semantically NOT local: " + axiom + " / " + sigma);
                            }
                        } catch (UnsupportedOperationException ex) {
                            unsupported++;
                        }
                    }
                }
            }
        }
        // A disagreement can also point at a semantic evaluator bug; the
        // hand-written semantic tests decide which side is wrong.
        assertNoViolations(violations, "syntactic => semantic locality");
        final int checked = confirmed;
        final int skipped = unsupported;
        assertTrue(checked >= 50, () -> "Only " + checked + " syntactically local cases "
                + "were confirmed (" + skipped + " unsupported); the corpus is too weak");
        assertEquals(0, manager.getOntologies().size());
    }

    // ------------------------------------------------------------------
    // Corpus generator
    // ------------------------------------------------------------------

    /** Deterministic generator for small OWL 2 axioms over a fixed vocabulary. */
    private static final class Generator {
        private final Random random;

        Generator(Random random) {
            this.random = random;
        }

        List<OWLAxiom> axioms(int count) {
            Set<OWLAxiom> result = new LinkedHashSet<>();
            int attempts = 0;
            while (result.size() < count && attempts++ < 50 * count) {
                result.add(axiom());
            }
            return new ArrayList<>(result);
        }

        private <T> T pick(List<T> options) {
            return options.get(random.nextInt(options.size()));
        }

        private OWLClassExpression atom() {
            return pick(List.of(a, a, b, b, c, f.getOWLThing(), f.getOWLNothing(),
                    f.getOWLObjectOneOf(i)));
        }

        private OWLObjectPropertyExpression role() {
            return pick(List.of(r, r, s, s, r.getInverseProperty(),
                    f.getOWLTopObjectProperty(), f.getOWLBottomObjectProperty()));
        }

        private OWLObjectProperty namedRole() {
            return pick(List.of(r, s));
        }

        /** Roles for RBox, domain and range axioms: no built-ins there. */
        private OWLObjectPropertyExpression plainRole() {
            return pick(List.of(r, s, r.getInverseProperty()));
        }

        private OWLDataPropertyExpression dataProperty() {
            return pick(List.of(p, p, q, f.getOWLTopDataProperty(),
                    f.getOWLBottomDataProperty()));
        }

        private OWLDataRange dataRange() {
            return pick(List.of(f.getTopDatatype(), f.getStringOWLDatatype(),
                    f.getBooleanOWLDatatype(),
                    f.getOWLDataOneOf(f.getOWLLiteral("a")),
                    f.getOWLDataOneOf(f.getOWLLiteral("a"), f.getOWLLiteral("b"))));
        }

        private OWLIndividual individual() {
            return pick(List.of(i, j));
        }

        private OWLClassExpression and(OWLClassExpression x, OWLClassExpression y) {
            return x.equals(y) ? x : f.getOWLObjectIntersectionOf(x, y);
        }

        private OWLClassExpression or(OWLClassExpression x, OWLClassExpression y) {
            return x.equals(y) ? x : f.getOWLObjectUnionOf(x, y);
        }

        OWLClassExpression cls(int depth) {
            if (depth == 0 || random.nextInt(4) == 0) {
                return atom();
            }
            int n = random.nextInt(3);
            switch (random.nextInt(14)) {
                case 0: return f.getOWLObjectComplementOf(cls(depth - 1));
                case 1: return and(cls(depth - 1), cls(depth - 1));
                case 2: return or(cls(depth - 1), cls(depth - 1));
                case 3: return f.getOWLObjectSomeValuesFrom(role(), cls(depth - 1));
                case 4: return f.getOWLObjectAllValuesFrom(role(), cls(depth - 1));
                case 5: return f.getOWLObjectMinCardinality(n, role(), cls(depth - 1));
                case 6: return f.getOWLObjectMaxCardinality(n, role(), cls(depth - 1));
                case 7: return f.getOWLObjectExactCardinality(n, role(), cls(depth - 1));
                case 8: return f.getOWLObjectHasSelf(role());
                case 9: return f.getOWLObjectHasValue(role(), individual());
                case 10: return f.getOWLDataSomeValuesFrom(dataProperty(), dataRange());
                case 11: return f.getOWLDataAllValuesFrom(dataProperty(), dataRange());
                case 12: return f.getOWLDataMinCardinality(n, dataProperty(), dataRange());
                default: return f.getOWLDataMaxCardinality(n, dataProperty(), dataRange());
            }
        }

        /** Two or three pairwise different class expressions. */
        private OWLClassExpression[] distinct(int count) {
            Set<OWLClassExpression> operands = new LinkedHashSet<>();
            while (operands.size() < count) {
                operands.add(cls(2));
            }
            return operands.toArray(new OWLClassExpression[0]);
        }

        OWLAxiom axiom() {
            Supplier<OWLClassExpression> ce = () -> cls(2);
            switch (random.nextInt(20)) {
                case 0: case 1: case 2: case 3: case 4:
                    return f.getOWLSubClassOfAxiom(ce.get(), ce.get());
                case 5: return f.getOWLEquivalentClassesAxiom(distinct(2));
                case 6: return f.getOWLDisjointClassesAxiom(distinct(2));
                case 7: return f.getOWLDisjointClassesAxiom(distinct(3));
                case 8: return f.getOWLClassAssertionAxiom(ce.get(), individual());
                case 9: return f.getOWLObjectPropertyDomainAxiom(plainRole(), cls(1));
                case 10: return f.getOWLObjectPropertyRangeAxiom(plainRole(), cls(1));
                case 11: return f.getOWLSubObjectPropertyOfAxiom(plainRole(), plainRole());
                case 12: return f.getOWLSubDataPropertyOfAxiom(p, q);
                case 13: return characteristic();
                case 14: return random.nextBoolean()
                        ? f.getOWLObjectPropertyAssertionAxiom(role(), i, j)
                        : f.getOWLNegativeObjectPropertyAssertionAxiom(role(), i, j);
                case 15: return random.nextBoolean()
                        ? f.getOWLDataPropertyAssertionAxiom(p, i, f.getOWLLiteral("a"))
                        : f.getOWLNegativeDataPropertyAssertionAxiom(p, i, f.getOWLLiteral("a"));
                case 16: return f.getOWLDataPropertyDomainAxiom(p, cls(1));
                case 17: return f.getOWLDataPropertyRangeAxiom(p, dataRange());
                case 18: return random.nextBoolean()
                        ? f.getOWLDisjointObjectPropertiesAxiom(r, s)
                        : f.getOWLEquivalentObjectPropertiesAxiom(r, s);
                default: return f.getOWLSubPropertyChainOfAxiom(List.of(r, s), namedRole());
            }
        }

        private OWLAxiom characteristic() {
            OWLObjectProperty role = namedRole();
            switch (random.nextInt(8)) {
                case 0: return f.getOWLFunctionalObjectPropertyAxiom(role);
                case 1: return f.getOWLInverseFunctionalObjectPropertyAxiom(role);
                case 2: return f.getOWLReflexiveObjectPropertyAxiom(role);
                case 3: return f.getOWLIrreflexiveObjectPropertyAxiom(role);
                case 4: return f.getOWLSymmetricObjectPropertyAxiom(role);
                case 5: return f.getOWLAsymmetricObjectPropertyAxiom(role);
                case 6: return f.getOWLTransitiveObjectPropertyAxiom(role);
                default: return f.getOWLFunctionalDataPropertyAxiom(p);
            }
        }
    }
}