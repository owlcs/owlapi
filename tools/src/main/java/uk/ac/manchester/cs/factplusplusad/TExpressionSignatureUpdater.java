package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.HasProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

/**
 * update the signature by adding all signature elements from the expression
 */
class TExpressionSignatureUpdater implements OWLObjectVisitor {

    /**
     * Signature to be filled
     */
    Signature sig;

    /**
     * init c'tor
     *
     * @param s signature
     */
    TExpressionSignatureUpdater(Signature s) {
        sig = s;
    }

    void vC(OWLClassExpression expr) {
        expr.accept(this);
    }

    void vI(OWLIndividual expr) {
        expr.accept(this);
    }

    void vOR(HasProperty<OWLObjectPropertyExpression> expr) {
        expr.getProperty().accept(this);
    }

    void vDR(HasProperty<OWLDataPropertyExpression> expr) {
        expr.getProperty().accept(this);
    }

    /**
     * @param e entity to add
     */
    void vE(OWLEntity e) {
        sig.add(e);
    }

    // concept expressions
    @Override
    public void visit(OWLClass expr) {
        if (expr.isTopEntity() || expr.isBottomEntity()) {
            return;
        }
        vE(expr);
    }

    @Override
    public void visit(OWLObjectComplementOf expr) {
        vC(expr);
    }

    @Override
    public void visit(OWLObjectIntersectionOf expr) {
        expr.operands().forEach(p -> p.accept(this));
    }

    @Override
    public void visit(OWLObjectUnionOf expr) {
        expr.operands().forEach(p -> p.accept(this));
    }

    @Override
    public void visit(OWLObjectOneOf expr) {
        expr.operands().forEach(p -> p.accept(this));
    }

    @Override
    public void visit(OWLObjectHasSelf expr) {
        vOR(expr);
    }

    @Override
    public void visit(OWLObjectHasValue expr) {
        vOR(expr);
        vI(expr.getFiller());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom expr) {
        vOR(expr);
        vC(expr);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom expr) {
        vOR(expr);
        vC(expr);
    }

    @Override
    public void visit(OWLObjectMinCardinality expr) {
        vOR(expr);
        vC(expr);
    }

    @Override
    public void visit(OWLObjectMaxCardinality expr) {
        vOR(expr);
        vC(expr);
    }

    @Override
    public void visit(OWLObjectExactCardinality expr) {
        vOR(expr);
        vC(expr);
    }

    @Override
    public void visit(OWLDataHasValue expr) {
        vDR(expr);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom expr) {
        vDR(expr);
    }

    @Override
    public void visit(OWLDataAllValuesFrom expr) {
        vDR(expr);
    }

    @Override
    public void visit(OWLDataMinCardinality expr) {
        vDR(expr);
    }

    @Override
    public void visit(OWLDataMaxCardinality expr) {
        vDR(expr);
    }

    @Override
    public void visit(OWLDataExactCardinality expr) {
        vDR(expr);
    }

    // individual expressions
    @Override
    public void visit(OWLNamedIndividual expr) {
        vE(expr);
    }

    // object role expressions
    @Override
    public void visit(OWLObjectProperty expr) {
        if (expr.isTopEntity() || expr.isBottomEntity()) {
            return;
        }
        vE(expr);
    }

    @Override
    public void visit(OWLObjectInverseOf expr) {
        expr.getNamedProperty().accept(this);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom expr) {
        expr.signature().forEach(p -> p.accept(this));
    }

    // data role expressions
    @Override
    public void visit(OWLDataProperty expr) {
        if (expr.isTopEntity() || expr.isBottomEntity()) {
            return;
        }
        vE(expr);
    }
}
