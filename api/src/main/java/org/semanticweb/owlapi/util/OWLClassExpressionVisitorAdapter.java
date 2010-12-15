package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Nov-2006<br><br>
 */
@SuppressWarnings("unused")
public class OWLClassExpressionVisitorAdapter implements OWLClassExpressionVisitor {

    public void visit(OWLClass desc) {
    }


    public void visit(OWLObjectIntersectionOf desc) {
    }


    public void visit(OWLObjectUnionOf desc) {
    }


    public void visit(OWLObjectComplementOf desc) {
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
    }


    public void visit(OWLObjectAllValuesFrom desc) {
    }


    public void visit(OWLObjectHasValue desc) {
    }


    public void visit(OWLObjectMinCardinality desc) {
    }


    public void visit(OWLObjectExactCardinality desc) {
    }


    public void visit(OWLObjectMaxCardinality desc) {
    }


    public void visit(OWLObjectHasSelf desc) {
    }


    public void visit(OWLObjectOneOf desc) {
    }


    public void visit(OWLDataSomeValuesFrom desc) {
    }


    public void visit(OWLDataAllValuesFrom desc) {
    }


    public void visit(OWLDataHasValue desc) {
    }


    public void visit(OWLDataMinCardinality desc) {
    }


    public void visit(OWLDataExactCardinality desc) {
    }


    public void visit(OWLDataMaxCardinality desc) {
    }
}
