package org.coode.owlapi.latex;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
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
 * Medical Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 */
@SuppressWarnings("unused")
public class LatexBracketChecker implements OWLClassExpressionVisitor {

    private boolean requiresBracket;

    private static LatexBracketChecker instance = new LatexBracketChecker();

    private LatexBracketChecker() {

    }

    public void visit(OWLObjectIntersectionOf node) {
        requiresBracket = true;
    }

    public void visit(OWLDataAllValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLDataCardinalityRestriction node) {
        requiresBracket = true;
    }

    public void visit(OWLDataSomeValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLDataHasValue node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectAllValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectCardinalityRestriction node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectSomeValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectHasValue node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectComplementOf node) {
        requiresBracket = false;
    }

    public void visit(OWLObjectUnionOf node) {
        requiresBracket = true;
    }

    public void visit(OWLClass node) {
        requiresBracket = false;
    }

    public void visit(OWLObjectOneOf node) {
        requiresBracket = true;
    }


    public void visit(OWLDataExactCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLDataMaxCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLDataMinCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectExactCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectMaxCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectMinCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectHasSelf owlHasSelf) {
        requiresBracket = true;
    }


    public static boolean requiresBracket(OWLClassExpression classExpression) {
        
        instance.requiresBracket = true;
        classExpression.accept(instance);
        return instance.requiresBracket;

    }
}
