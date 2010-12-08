package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class NNFTestCase extends AbstractOWLAPITestCase {

    public void testPosOWLClass() {
        OWLClass cls = getOWLClass("A");
        assertEquals(cls.getNNF(), cls);
    }


    public void testNegOWLClass() {
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(getOWLClass("A"));
        assertEquals(cls.getNNF(), cls);
    }

    public void testPosAllValuesFrom() {
        OWLClassExpression cls = getFactory().getOWLObjectAllValuesFrom(getOWLObjectProperty("p"), getOWLClass("A"));
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegAllValuesFrom() {
        OWLObjectProperty property = getOWLObjectProperty("p");
        OWLClass filler = getOWLClass("A");
        OWLObjectAllValuesFrom allValuesFrom = getFactory().getOWLObjectAllValuesFrom(property, filler);
        OWLClassExpression cls = allValuesFrom.getObjectComplementOf();
        OWLClassExpression nnf = getFactory().getOWLObjectSomeValuesFrom(property, filler.getObjectComplementOf());
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosSomeValuesFrom() {
        OWLClassExpression cls = getFactory().getOWLObjectSomeValuesFrom(getOWLObjectProperty("p"), getOWLClass("A"));
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegSomeValuesFrom() {
        OWLObjectProperty property = getOWLObjectProperty("p");
        OWLClass filler = getOWLClass("A");
        OWLObjectSomeValuesFrom someValuesFrom = getFactory().getOWLObjectSomeValuesFrom(property, filler);
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(someValuesFrom);
        OWLClassExpression nnf = getFactory().getOWLObjectAllValuesFrom(property, getFactory().getOWLObjectComplementOf(filler));
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosObjectIntersectionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectIntersectionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C"));
        assertEquals(cls.getNNF(), cls);
    }


    public void testNegObjectIntersectionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(getFactory().getOWLObjectIntersectionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C")));
        OWLClassExpression nnf = getFactory().getOWLObjectUnionOf(getFactory().getOWLObjectComplementOf(getOWLClass("A")), getFactory().getOWLObjectComplementOf(getOWLClass("B")), getFactory().getOWLObjectComplementOf(getOWLClass("C")));
        assertEquals(cls.getNNF(), nnf);
    }


    public void testPosObjectUnionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectUnionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C"));
        assertEquals(cls.getNNF(), cls);
    }


    public void testNegObjectUnionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(getFactory().getOWLObjectUnionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C")));
        OWLClassExpression nnf = getFactory().getOWLObjectIntersectionOf(getFactory().getOWLObjectComplementOf(getOWLClass("A")), getFactory().getOWLObjectComplementOf(getOWLClass("B")), getFactory().getOWLObjectComplementOf(getOWLClass("C")));
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosObjectMinCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMinCardinality(3, prop, filler);
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegObjectMinCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMinCardinality(3, prop, filler).getObjectComplementOf();
        OWLClassExpression nnf = getFactory().getOWLObjectMaxCardinality(2, prop, filler);
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosObjectMaxCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMaxCardinality(3, prop, filler);
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegObjectMaxCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMaxCardinality(3, prop, filler).getObjectComplementOf();
        OWLClassExpression nnf = getFactory().getOWLObjectMinCardinality(4, prop, filler);
        assertEquals(cls.getNNF(), nnf);
    }
}
