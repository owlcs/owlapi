package org.semanticweb.owlapi.normalform;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
 * Date: 13-Oct-2007<br><br>
 * <p/>
 * Extracts the parts of a class expression which are negated.
 * For example, A and not (B or C or not D) would extract
 * {(B or C or notD), D}
 */
@SuppressWarnings("unused")
public class OWLObjectComplementOfExtractor implements OWLClassExpressionVisitor {

    private Set<OWLClassExpression> result;


    public OWLObjectComplementOfExtractor() {
        result = new HashSet<OWLClassExpression>();
    }

    public Set<OWLClassExpression> getComplementedClassExpressions(OWLClassExpression desc) {
    	// XXX a stateless visitor would not need copies
        reset();
        desc.accept(this);
        return new HashSet<OWLClassExpression>(result);
    }

    public void reset() {
        result.clear();
    }

    public void visit(OWLClass desc) {
    }


    public void visit(OWLDataAllValuesFrom desc) {
    }


    public void visit(OWLDataExactCardinality desc) {
    }


    public void visit(OWLDataMaxCardinality desc) {
    }


    public void visit(OWLDataMinCardinality desc) {
    }


    public void visit(OWLDataSomeValuesFrom desc) {
    }


    public void visit(OWLDataHasValue desc) {
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectComplementOf desc) {
        result.add(desc.getOperand());
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectExactCardinality desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectIntersectionOf desc) {
        for (OWLClassExpression op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectMaxCardinality desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectMinCardinality desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {

    }


    public void visit(OWLObjectHasSelf desc) {

    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectUnionOf desc) {
        for (OWLClassExpression op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectHasValue desc) {

    }
}
