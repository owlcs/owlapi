package org.semanticweb.owlapi.modularity.comparison;

import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.A;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.B;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.C;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.F;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.I;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.J;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.P;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.Q;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.R;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.S;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * Real and generated ontologies for the comparison, with seed signatures. The
 * real ontologies are copies kept in {@code modularity-comparison/} so that the
 * before/after runs use identical inputs.
 */
final class TestOntologies {

    /** A named axiom base with the seed signatures used on it. */
    static final class Corpus {
        final String name;
        final Set<OWLAxiom> logicalAxioms;
        final Set<OWLAxiom> allAxioms;
        final Map<String, Set<OWLEntity>> signatures;

        Corpus(String name, Set<OWLAxiom> logicalAxioms, Set<OWLAxiom> allAxioms,
            Map<String, Set<OWLEntity>> signatures) {
            this.name = name;
            this.logicalAxioms = Collections.unmodifiableSet(logicalAxioms);
            this.allAxioms = Collections.unmodifiableSet(allAxioms);
            this.signatures = Collections.unmodifiableMap(signatures);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final Map<String, Corpus> CACHE = new LinkedHashMap<>();

    private TestOntologies() {}

    static synchronized List<Corpus> all() {
        return Arrays.asList(koala(), pizza(), random());
    }

    static synchronized Corpus koala() {
        return CACHE.computeIfAbsent("koala", n -> load(n, "koala.owl",
            Arrays.asList("Koala", "Person", "Student,Parent", "KoalaWithPhD,Quokka,University",
                "Habitat,Forest,DryEucalyptForest,Rainforest", "")));
    }

    static synchronized Corpus pizza() {
        return CACHE.computeIfAbsent("pizza", n -> load(n, "pizza.owl",
            Arrays.asList("Margherita", "VegetarianPizza", "hasTopping", "American,SpicyPizza",
                "")));
    }

    /** A deterministic random corpus that mixes many OWL 2 constructs. */
    static synchronized Corpus random() {
        return CACHE.computeIfAbsent("random", n -> {
            Set<OWLAxiom> axioms = new LinkedHashSet<>(new Generator(new Random(20261018L))
                .axioms(80));
            Map<String, Set<OWLEntity>> signatures = new LinkedHashMap<>();
            signatures.put("{}", Collections.emptySet());
            signatures.put("{A}", Vocabulary.sig(A));
            signatures.put("{A,r}", Vocabulary.sig(A, R));
            signatures.put("{A,B,r,p}", Vocabulary.sig(A, B, R, P));
            return new Corpus(n, axioms, axioms, signatures);
        });
    }

    private static Corpus load(String name, String file, List<String> seeds) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        try (InputStream in =
            TestOntologies.class.getResourceAsStream("/modularity-comparison/" + file)) {
            OWLOntology o = manager.loadOntologyFromOntologyDocument(in);
            Set<OWLAxiom> logical = o.logicalAxioms(Imports.INCLUDED).collect(Collectors.toSet());
            Set<OWLAxiom> all = o.axioms(Imports.INCLUDED).collect(Collectors.toSet());
            Map<String, Set<OWLEntity>> signatures = new LinkedHashMap<>();
            for (String seed : seeds) {
                Set<OWLEntity> sigma = new HashSet<>();
                for (String local : seed.split(",")) {
                    if (local.isEmpty()) {
                        continue;
                    }
                    List<OWLEntity> matches = o.signature()
                        .filter(e -> e.getIRI().getRemainder().orElse("").equals(local))
                        .collect(Collectors.toList());
                    if (matches.isEmpty()) {
                        throw new IllegalStateException(local + " not found in " + file);
                    }
                    sigma.addAll(matches);
                }
                signatures.put("{" + seed + "}", sigma);
            }
            return new Corpus(name, logical, all, signatures);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (OWLOntologyCreationException e) {
            throw new IllegalStateException(e);
        }
    }

    /** Deterministic generator for small OWL 2 axioms over the shared vocabulary. */
    static final class Generator {
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
            return pick(Arrays.asList(A, A, B, B, C, F.getOWLThing(), F.getOWLNothing(),
                F.getOWLObjectOneOf(I)));
        }

        private OWLObjectPropertyExpression role() {
            return pick(Arrays.asList(R, R, S, S, R.getInverseProperty(),
                F.getOWLTopObjectProperty(), F.getOWLBottomObjectProperty()));
        }

        private OWLObjectProperty namedRole() {
            return pick(Arrays.asList(R, S));
        }

        private OWLObjectPropertyExpression plainRole() {
            return pick(Arrays.asList(R, S, R.getInverseProperty()));
        }

        private OWLDataPropertyExpression dataProperty() {
            return pick(Arrays.asList(P, P, Q, F.getOWLTopDataProperty(),
                F.getOWLBottomDataProperty()));
        }

        private OWLDataRange dataRange() {
            return pick(Arrays.asList(F.getTopDatatype(), F.getStringOWLDatatype(),
                F.getBooleanOWLDatatype(), F.getOWLDataOneOf(F.getOWLLiteral("a")),
                F.getOWLDataOneOf(F.getOWLLiteral("a"), F.getOWLLiteral("b"))));
        }

        private OWLIndividual individual() {
            return pick(Arrays.asList(I, J));
        }

        private OWLClassExpression and(OWLClassExpression x, OWLClassExpression y) {
            return x.equals(y) ? x : F.getOWLObjectIntersectionOf(x, y);
        }

        private OWLClassExpression or(OWLClassExpression x, OWLClassExpression y) {
            return x.equals(y) ? x : F.getOWLObjectUnionOf(x, y);
        }

        OWLClassExpression cls(int depth) {
            if (depth == 0 || random.nextInt(4) == 0) {
                return atom();
            }
            int n = random.nextInt(3);
            switch (random.nextInt(14)) {
                case 0:
                    return F.getOWLObjectComplementOf(cls(depth - 1));
                case 1:
                    return and(cls(depth - 1), cls(depth - 1));
                case 2:
                    return or(cls(depth - 1), cls(depth - 1));
                case 3:
                    return F.getOWLObjectSomeValuesFrom(role(), cls(depth - 1));
                case 4:
                    return F.getOWLObjectAllValuesFrom(role(), cls(depth - 1));
                case 5:
                    return F.getOWLObjectMinCardinality(n, role(), cls(depth - 1));
                case 6:
                    return F.getOWLObjectMaxCardinality(n, role(), cls(depth - 1));
                case 7:
                    return F.getOWLObjectExactCardinality(n, role(), cls(depth - 1));
                case 8:
                    return F.getOWLObjectHasSelf(role());
                case 9:
                    return F.getOWLObjectHasValue(role(), individual());
                case 10:
                    return F.getOWLDataSomeValuesFrom(dataProperty(), dataRange());
                case 11:
                    return F.getOWLDataAllValuesFrom(dataProperty(), dataRange());
                case 12:
                    return F.getOWLDataMinCardinality(n, dataProperty(), dataRange());
                default:
                    return F.getOWLDataMaxCardinality(n, dataProperty(), dataRange());
            }
        }

        private OWLClassExpression[] distinct(int count) {
            Set<OWLClassExpression> operands = new LinkedHashSet<>();
            while (operands.size() < count) {
                operands.add(cls(2));
            }
            return operands.toArray(new OWLClassExpression[0]);
        }

        OWLAxiom axiom() {
            switch (random.nextInt(20)) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    return F.getOWLSubClassOfAxiom(cls(2), cls(2));
                case 5:
                    return F.getOWLEquivalentClassesAxiom(distinct(2));
                case 6:
                    return F.getOWLDisjointClassesAxiom(distinct(2));
                case 7:
                    return F.getOWLDisjointClassesAxiom(distinct(3));
                case 8:
                    return F.getOWLClassAssertionAxiom(cls(2), individual());
                case 9:
                    return F.getOWLObjectPropertyDomainAxiom(plainRole(), cls(1));
                case 10:
                    return F.getOWLObjectPropertyRangeAxiom(plainRole(), cls(1));
                case 11:
                    return F.getOWLSubObjectPropertyOfAxiom(plainRole(), plainRole());
                case 12:
                    return F.getOWLSubDataPropertyOfAxiom(P, Q);
                case 13:
                    return characteristic();
                case 14:
                    return random.nextBoolean() ? F.getOWLObjectPropertyAssertionAxiom(role(), I, J)
                        : F.getOWLNegativeObjectPropertyAssertionAxiom(role(), I, J);
                case 15:
                    return random.nextBoolean()
                        ? F.getOWLDataPropertyAssertionAxiom(P, I, F.getOWLLiteral("a"))
                        : F.getOWLNegativeDataPropertyAssertionAxiom(P, I, F.getOWLLiteral("a"));
                case 16:
                    return F.getOWLDataPropertyDomainAxiom(P, cls(1));
                case 17:
                    return F.getOWLDataPropertyRangeAxiom(P, dataRange());
                case 18:
                    return random.nextBoolean() ? F.getOWLDisjointObjectPropertiesAxiom(R, S)
                        : F.getOWLEquivalentObjectPropertiesAxiom(R, S);
                default:
                    return F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), namedRole());
            }
        }

        private OWLAxiom characteristic() {
            OWLObjectProperty role = namedRole();
            switch (random.nextInt(8)) {
                case 0:
                    return F.getOWLFunctionalObjectPropertyAxiom(role);
                case 1:
                    return F.getOWLInverseFunctionalObjectPropertyAxiom(role);
                case 2:
                    return F.getOWLReflexiveObjectPropertyAxiom(role);
                case 3:
                    return F.getOWLIrreflexiveObjectPropertyAxiom(role);
                case 4:
                    return F.getOWLSymmetricObjectPropertyAxiom(role);
                case 5:
                    return F.getOWLAsymmetricObjectPropertyAxiom(role);
                case 6:
                    return F.getOWLTransitiveObjectPropertyAxiom(role);
                default:
                    return F.getOWLFunctionalDataPropertyAxiom(P);
            }
        }
    }
}
