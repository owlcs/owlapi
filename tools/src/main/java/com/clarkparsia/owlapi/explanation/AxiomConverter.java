package com.clarkparsia.owlapi.explanation;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

/** The Class AxiomConverter. */
class AxiomConverter extends OWLAxiomVisitorExAdapter<OWLClassExpression> {

    private final OWLDataFactory factory;

    AxiomConverter(OWLDataFactory df) {
        super(df.getOWLThing());
        factory = df;
    }

    @Nonnull
    private OWLObjectIntersectionOf and(@Nonnull OWLClassExpression desc1,
            @Nonnull OWLClassExpression desc2) {
        return factory.getOWLObjectIntersectionOf(set(desc1, desc2));
    }

    @Nonnull
    private OWLObjectIntersectionOf and(@Nonnull Set<OWLClassExpression> set) {
        return factory.getOWLObjectIntersectionOf(set);
    }

    @Nonnull
    private OWLObjectComplementOf not(@Nonnull OWLClassExpression desc) {
        return factory.getOWLObjectComplementOf(desc);
    }

    @Nonnull
    private OWLObjectOneOf oneOf(@Nonnull OWLIndividual ind) {
        return factory.getOWLObjectOneOf(CollectionFactory.createSet(ind));
    }

    @Nonnull
    private OWLObjectUnionOf or(@Nonnull OWLClassExpression desc1,
            @Nonnull OWLClassExpression desc2) {
        return factory.getOWLObjectUnionOf(set(desc1, desc2));
    }

    @Nonnull
    private static <T> Set<T> set(@Nonnull T desc1, @Nonnull T desc2) {
        Set<T> set = new HashSet<>();
        set.add(desc1);
        set.add(desc2);
        return set;
    }

    @Override
    protected OWLClassExpression doDefault(OWLAxiom object) {
        throw new OWLRuntimeException(
                "Not implemented: Cannot generate explanation for " + object);
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
        OWLClassExpression sup = factory.getOWLDataHasValue(
                axiom.getProperty(), axiom.getObject());
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
        return ax.accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression sub = factory.getOWLDataSomeValuesFrom(
                axiom.getProperty(), factory.getTopDatatype());
        return and(sub, not(axiom.getDomain()));
    }

    @Override
    public OWLClassExpression visit(OWLDataPropertyRangeAxiom axiom) {
        return factory.getOWLDataSomeValuesFrom(axiom.getProperty(),
                factory.getOWLDataComplementOf(axiom.getRange()));
    }

    @Override
    public OWLClassExpression visit(OWLDifferentIndividualsAxiom axiom) {
        Set<OWLClassExpression> nominals = new HashSet<>();
        for (OWLIndividual ind : axiom.getIndividuals()) {
            assert ind != null;
            nominals.add(oneOf(ind));
        }
        return factory.getOWLObjectIntersectionOf(nominals);
    }

    @Override
    public OWLClassExpression visit(OWLDisjointClassesAxiom axiom) {
        return and(axiom.getClassExpressions());
    }

    @Override
    public OWLClassExpression visit(OWLEquivalentClassesAxiom axiom) {
        Iterator<OWLClassExpression> classes = axiom.getClassExpressions()
                .iterator();
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
    public OWLClassExpression
            visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLDataHasValue(
                axiom.getProperty(), axiom.getObject());
        return factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
    }

    @Override
    public OWLClassExpression visit(
            OWLNegativeObjectPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLObjectHasValue(
                axiom.getProperty(), axiom.getObject());
        return factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLObjectPropertyAssertionAxiom axiom) {
        OWLClassExpression sub = oneOf(axiom.getSubject());
        OWLClassExpression sup = factory.getOWLObjectHasValue(
                axiom.getProperty(), axiom.getObject());
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
        return ax.accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLObjectPropertyDomainAxiom axiom) {
        return and(
                factory.getOWLObjectSomeValuesFrom(axiom.getProperty(),
                        factory.getOWLThing()), not(axiom.getDomain()));
    }

    @Override
    public OWLClassExpression visit(OWLObjectPropertyRangeAxiom axiom) {
        return factory.getOWLObjectSomeValuesFrom(axiom.getProperty(),
                not(axiom.getRange()));
    }

    @Override
    public OWLClassExpression visit(OWLSameIndividualAxiom axiom) {
        Set<OWLClassExpression> nominals = new HashSet<>();
        for (OWLIndividual ind : axiom.getIndividuals()) {
            assert ind != null;
            nominals.add(not(oneOf(ind)));
        }
        return and(nominals);
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
