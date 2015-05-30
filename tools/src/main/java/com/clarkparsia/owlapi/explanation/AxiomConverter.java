package com.clarkparsia.owlapi.explanation;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;

/** The Class AxiomConverter. */
class AxiomConverter implements OWLAxiomVisitorEx<OWLClassExpression> {

    private final OWLDataFactory factory;

    AxiomConverter(OWLDataFactory df) {
        super();
        factory = df;
    }

    private OWLObjectIntersectionOf and(OWLClassExpression desc1, OWLClassExpression desc2) {
        return factory.getOWLObjectIntersectionOf(set(desc1, desc2));
    }

    private OWLObjectIntersectionOf and(Stream<OWLClassExpression> set) {
        return factory.getOWLObjectIntersectionOf(asList(set));
    }

    private OWLObjectComplementOf not(OWLClassExpression desc) {
        return factory.getOWLObjectComplementOf(desc);
    }

    private OWLObjectOneOf oneOf(OWLIndividual ind) {
        return factory.getOWLObjectOneOf(CollectionFactory.createSet(ind));
    }

    private OWLObjectUnionOf or(OWLClassExpression desc1, OWLClassExpression desc2) {
        return factory.getOWLObjectUnionOf(set(desc1, desc2));
    }

    private static <T> Set<T> set(T desc1, T desc2) {
        Set<T> set = new HashSet<>();
        set.add(desc1);
        set.add(desc2);
        return set;
    }

    @Override
    public OWLClassExpression doDefault(Object object) {
        throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + object);
    }

    @Override
    public OWLClassExpression visit(OWLClassAssertionAxiom axiom) {
        OWLIndividual ind = axiom.getIndividual();
        OWLClassExpression c = axiom.getClassExpression();
        return and(oneOf(ind), not(c));
    }

    @Override
    public OWLClassExpression visit(OWLDataPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLDataHasValue(axiom.getProperty(), axiom.getObject());
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
        return ax.accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression sub = factory.getOWLDataSomeValuesFrom(axiom.getProperty(), factory.getTopDatatype());
        return and(sub, not(axiom.getDomain()));
    }

    @Override
    public OWLClassExpression visit(OWLDataPropertyRangeAxiom axiom) {
        return factory.getOWLDataSomeValuesFrom(axiom.getProperty(), factory.getOWLDataComplementOf(axiom.getRange()));
    }

    @Override
    public OWLClassExpression visit(OWLDifferentIndividualsAxiom axiom) {
        Set<OWLClassExpression> nominals = new HashSet<>();
        axiom.individuals().forEach(ind -> nominals.add(oneOf(ind)));
        return factory.getOWLObjectIntersectionOf(nominals);
    }

    @Override
    public OWLClassExpression visit(OWLDisjointClassesAxiom axiom) {
        return and(axiom.classExpressions());
    }

    @Override
    public OWLClassExpression visit(OWLEquivalentClassesAxiom axiom) {
        Iterator<OWLClassExpression> classes = axiom.classExpressions().iterator();
        OWLClassExpression c1 = classes.next();
        OWLClassExpression c2 = classes.next();
        // apply simplification for the cases where either concept is
        // owl:Thing or owlapi:Nothing
        if (c1.isOWLNothing()) {
            return verifyNotNull(c2);
        } else if (c2.isOWLNothing()) {
            return c1;
        } else if (c1.isOWLThing()) {
            return not(c2);
        } else if (c2.isOWLThing()) {
            return not(c1);
        } else {
            return or(and(c1, not(c2)), and(not(c1), c2));
        }
    }

    @Override
    public OWLClassExpression visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLDataHasValue(axiom.getProperty(), axiom.getObject());
        return factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLObjectHasValue(axiom.getProperty(), axiom.getObject());
        return factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLObjectPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLObjectHasValue(axiom.getProperty(), axiom.getObject());
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
        return ax.accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLObjectPropertyDomainAxiom axiom) {
        return and(factory.getOWLObjectSomeValuesFrom(axiom.getProperty(), factory.getOWLThing()),
                not(axiom.getDomain()));
    }

    @Override
    public OWLClassExpression visit(OWLObjectPropertyRangeAxiom axiom) {
        return factory.getOWLObjectSomeValuesFrom(axiom.getProperty(), not(axiom.getRange()));
    }

    @Override
    public OWLClassExpression visit(OWLSameIndividualAxiom axiom) {
        return and(axiom.individuals().map(ind -> not(oneOf(ind))));
    }

    @Override
    public OWLClassExpression visit(OWLSubClassOfAxiom axiom) {
        OWLClassExpression sub = axiom.getSubClass();
        OWLClassExpression sup = axiom.getSuperClass();
        if (sup.isOWLNothing()) {
            return sub;
        } else if (sub.isOWLThing()) {
            return not(sup);
        } else {
            return and(sub, not(sup));
        }
    }
}
