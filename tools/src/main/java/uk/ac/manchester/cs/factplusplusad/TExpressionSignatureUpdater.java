package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.*;

/** update the signature by adding all signature elements from the expression */
class TExpressionSignatureUpdater implements OWLObjectVisitor {

    /** Signature to be filled */
    Signature sig;

    /**
     * init c'tor
     * 
     * @param s
     *        signature
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
     * @param e
     *        entity to add
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
