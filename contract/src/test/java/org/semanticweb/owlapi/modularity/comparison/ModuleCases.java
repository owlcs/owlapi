package org.semanticweb.owlapi.modularity.comparison;

import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.A;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.B;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.C;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.D;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.E;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.F;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.G;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.H;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.I;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.J;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.K;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.P;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.R;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.S;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.T;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.and;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.disjoint;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.equivalent;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.nothing;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.or;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.render;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sig;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.some;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sub;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;

/**
 * Small ontologies with hand-computed BOTTOM, TOP and STAR modules according to
 * the standard locality calculus. The ontologies contain no tautologies, so an
 * implementation that recognises more tautologies than the calculus (and hence
 * produces smaller modules elsewhere) must still produce exactly these modules.
 * Cases with tautologies live in {@code BeyondCalculusComparisonTest}.
 */
final class ModuleCases {

    /** A named axiom base. */
    static final class NamedOntology {
        final String name;
        final Map<String, OWLAxiom> axioms = new LinkedHashMap<>();

        NamedOntology(String name) {
            this.name = name;
        }

        NamedOntology add(String label, OWLAxiom axiom) {
            axioms.put(label, axiom);
            return this;
        }

        Set<OWLAxiom> base() {
            return new HashSet<>(axioms.values());
        }

        Set<OWLAxiom> select(String... labels) {
            Set<OWLAxiom> result = new HashSet<>();
            for (String label : labels) {
                OWLAxiom axiom = axioms.get(label);
                if (axiom == null) {
                    throw new IllegalArgumentException(label);
                }
                result.add(axiom);
            }
            return result;
        }

        /** Labels of the given axioms, for readable failure messages. */
        String labels(Set<OWLAxiom> module) {
            Set<String> names = new TreeSet<>();
            for (OWLAxiom axiom : module) {
                String label = axioms.entrySet().stream().filter(e -> e.getValue().equals(axiom))
                    .map(Map.Entry::getKey).findFirst().orElse(render(axiom));
                names.add(label);
            }
            return names.toString();
        }

        @Override
        public String toString() {
            return name + " " + axioms.entrySet().stream()
                .map(e -> e.getKey() + ": " + render(e.getValue()))
                .collect(Collectors.joining("; ", "{", "}"));
        }
    }

    /** One expected module. */
    static final class ModuleCase {
        final String id;
        final NamedOntology ontology;
        final Set<OWLEntity> signature;
        final LocalityClass type;
        final Set<OWLAxiom> expected;

        ModuleCase(NamedOntology ontology, Set<OWLEntity> signature, LocalityClass type,
            Set<OWLAxiom> expected) {
            this.ontology = ontology;
            this.signature = signature;
            this.type = type;
            this.expected = expected;
            id = ontology.name + ":" + type.name().toLowerCase() + ":" + render(
                signature.stream().sorted().collect(Collectors.toList()));
        }
    }

    private final List<ModuleCase> cases = new ArrayList<>();

    private ModuleCases() {}

    private void expect(NamedOntology o, Set<OWLEntity> signature, String bottom, String top,
        String star) {
        cases.add(new ModuleCase(o, signature, LocalityClass.BOTTOM, o.select(split(bottom))));
        cases.add(new ModuleCase(o, signature, LocalityClass.TOP, o.select(split(top))));
        cases.add(new ModuleCase(o, signature, LocalityClass.STAR, o.select(split(star))));
    }

    private static String[] split(String labels) {
        return labels.isEmpty() ? new String[0] : labels.split(",");
    }

    static List<ModuleCase> all() {
        ModuleCases t = new ModuleCases();
        // M1: a subclass chain and an unrelated axiom.
        NamedOntology chain = new NamedOntology("chain").add("a1", sub(A, B)).add("a2", sub(B, C))
            .add("a3", sub(C, D)).add("a4", sub(E, F.getOWLClass(Vocabulary.iri("F"))));
        t.expect(chain, sig(), "", "", "");
        t.expect(chain, sig(A), "a1,a2,a3", "", "");
        t.expect(chain, sig(D), "", "a1,a2,a3", "");
        t.expect(chain, sig(A, D), "a1,a2,a3", "a1,a2,a3", "a1,a2,a3");
        t.expect(chain, sig(B, C), "a2,a3", "a1,a2", "a2");
        t.expect(chain, sig(E), "a4", "", "");
        // M2: axioms that are non-local for the empty signature ("global" axioms).
        NamedOntology global = new NamedOntology("global").add("b1", sub(thing(), A))
            .add("b2", sub(A, B)).add("b3", sub(C, nothing())).add("b4", sub(D, C));
        t.expect(global, sig(), "b1,b2", "b3,b4", "");
        t.expect(global, sig(G), "b1,b2", "b3,b4", "");
        t.expect(global, sig(B), "b1,b2", "b1,b2,b3,b4", "b1,b2");
        // M3: roles, existential restrictions, role hierarchy and domain.
        NamedOntology roles = new NamedOntology("roles").add("g1", sub(A, some(R, B)))
            .add("g2", sub(some(R, C), D)).add("g3", F.getOWLSubObjectPropertyOfAxiom(R, S))
            .add("g4", F.getOWLObjectPropertyDomainAxiom(S, E))
            .add("g5", F.getOWLTransitiveObjectPropertyAxiom(T));
        t.expect(roles, sig(A), "g1,g3,g4", "", "");
        t.expect(roles, sig(C, D), "", "g1,g2", "");
        t.expect(roles, sig(A, R, B, C, D), "g1,g2,g3,g4", "g1,g2", "g1,g2");
        // M4: assertions, a nominal and individual equality.
        NamedOntology abox = new NamedOntology("abox").add("d1", F.getOWLClassAssertionAxiom(A, I))
            .add("d2", F.getOWLObjectPropertyAssertionAxiom(R, I, J))
            .add("d3", sub(B, F.getOWLObjectOneOf(I)))
            .add("d4", F.getOWLSameIndividualAxiom(I, K));
        t.expect(abox, sig(), "d1,d2,d4", "d3,d4", "d4");
        t.expect(abox, sig(B), "d1,d2,d3,d4", "d3,d4", "d3,d4");
        // M5: equivalence and disjointness.
        NamedOntology bool = new NamedOntology("boolean").add("e1", equivalent(A, and(B, C)))
            .add("e2", disjoint(B, D)).add("e3", sub(D, E));
        t.expect(bool, sig(A), "e1", "e1,e2", "e1");
        t.expect(bool, sig(D), "e3", "e1,e2", "");
        // M6: a data property with a proper range (non-local under TOP).
        NamedOntology data = new NamedOntology("data")
            .add("z1", sub(A, F.getOWLDataSomeValuesFrom(P, F.getStringOWLDatatype())))
            .add("z2", F.getOWLDataPropertyRangeAxiom(P, F.getIntegerOWLDatatype()))
            .add("z3", F.getOWLFunctionalDataPropertyAxiom(P));
        t.expect(data, sig(), "", "z1,z2,z3", "");
        t.expect(data, sig(A), "z1,z2,z3", "z1,z2,z3", "z1,z2,z3");
        // M7: unions, so that De Morgan and the n-ary handling are exercised.
        NamedOntology union = new NamedOntology("union").add("u1", sub(or(A, B), C))
            .add("u2", sub(C, or(D, H))).add("u3", disjoint(D, H));
        // Under TOP, Disjoint(D, H) is global: absent D and H are both Thing.
        t.expect(union, sig(), "", "u1,u2,u3", "");
        t.expect(union, sig(A), "u1,u2,u3", "u1,u2,u3", "u1,u2,u3");
        t.expect(union, sig(D), "", "u1,u2,u3", "");
        return Collections.unmodifiableList(t.cases);
    }

    static List<String> labelsOf(Set<OWLAxiom> axioms) {
        return Arrays.asList(axioms.stream().map(Vocabulary::render).sorted().toArray(String[]::new));
    }
}
