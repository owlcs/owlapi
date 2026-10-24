package org.semanticweb.owlapi.modularity.comparison;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Shared vocabulary and expression builders for the implementation comparison.
 * Only OWL API 5 model types are used, so the suite compiles against every
 * release that ships the compared implementations (5.5.1 and later).
 */
final class Vocabulary {

    static final OWLDataFactory F = OWLManager.getOWLDataFactory();
    static final String NS = "urn:modularity-comparison#";

    static final OWLClass A = cls("A");
    static final OWLClass B = cls("B");
    static final OWLClass C = cls("C");
    static final OWLClass D = cls("D");
    static final OWLClass E = cls("E");
    static final OWLClass G = cls("G");
    static final OWLClass H = cls("H");
    static final OWLObjectProperty R = F.getOWLObjectProperty(iri("r"));
    static final OWLObjectProperty S = F.getOWLObjectProperty(iri("s"));
    static final OWLObjectProperty T = F.getOWLObjectProperty(iri("t"));
    static final OWLDataProperty P = F.getOWLDataProperty(iri("p"));
    static final OWLDataProperty Q = F.getOWLDataProperty(iri("q"));
    static final OWLNamedIndividual I = F.getOWLNamedIndividual(iri("i"));
    static final OWLNamedIndividual J = F.getOWLNamedIndividual(iri("j"));
    static final OWLNamedIndividual K = F.getOWLNamedIndividual(iri("k"));
    static final OWLLiteral X = F.getOWLLiteral("x");

    private Vocabulary() {}

    static IRI iri(String name) {
        return IRI.create(NS + name);
    }

    static OWLClass cls(String name) {
        return F.getOWLClass(iri(name));
    }

    static Set<OWLEntity> sig(OWLEntity... entities) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(entities)));
    }

    @SafeVarargs
    static <T> Set<T> set(T... elements) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(elements)));
    }

    static <T> List<T> list(@SuppressWarnings("unchecked") T... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }

    static OWLClass thing() {
        return F.getOWLThing();
    }

    static OWLClass nothing() {
        return F.getOWLNothing();
    }

    static OWLAxiom sub(OWLClassExpression subClass, OWLClassExpression superClass) {
        return F.getOWLSubClassOfAxiom(subClass, superClass);
    }

    static OWLAxiom disjoint(OWLClassExpression... operands) {
        return F.getOWLDisjointClassesAxiom(operands);
    }

    static OWLAxiom equivalent(OWLClassExpression... operands) {
        return F.getOWLEquivalentClassesAxiom(operands);
    }

    static OWLClassExpression not(OWLClassExpression operand) {
        return F.getOWLObjectComplementOf(operand);
    }

    static OWLClassExpression and(OWLClassExpression... operands) {
        return F.getOWLObjectIntersectionOf(operands);
    }

    static OWLClassExpression or(OWLClassExpression... operands) {
        return F.getOWLObjectUnionOf(operands);
    }

    static OWLClassExpression some(OWLObjectPropertyExpression role, OWLClassExpression filler) {
        return F.getOWLObjectSomeValuesFrom(role, filler);
    }

    static OWLClassExpression all(OWLObjectPropertyExpression role, OWLClassExpression filler) {
        return F.getOWLObjectAllValuesFrom(role, filler);
    }

    static OWLClassExpression min(int n, OWLObjectPropertyExpression role,
            OWLClassExpression filler) {
        return F.getOWLObjectMinCardinality(n, role, filler);
    }

    static OWLClassExpression max(int n, OWLObjectPropertyExpression role,
            OWLClassExpression filler) {
        return F.getOWLObjectMaxCardinality(n, role, filler);
    }

    static OWLClassExpression exact(int n, OWLObjectPropertyExpression role,
            OWLClassExpression filler) {
        return F.getOWLObjectExactCardinality(n, role, filler);
    }

    /** Readable rendering without the comparison namespace. */
    static String render(Object o) {
        return String.valueOf(o).replace(NS, "").replace("<", "").replace(">", "")
            .replace("http://www.w3.org/2002/07/owl#", "owl:")
            .replace("http://www.w3.org/2001/XMLSchema#", "xsd:")
            .replace("http://www.w3.org/2000/01/rdf-schema#", "rdfs:");
    }
}
