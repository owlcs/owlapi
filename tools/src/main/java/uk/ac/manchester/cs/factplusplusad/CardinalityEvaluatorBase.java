package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.*;

abstract class CardinalityEvaluatorBase extends SigAccessor implements OWLObjectVisitor {

    UpperBoundDirectEvaluator ubd;
    LowerBoundDirectEvaluator lbd;
    UpperBoundComplementEvaluator ubc;
    LowerBoundComplementEvaluator lbc;
    /** keep the value here */
    int value = 0;

    /** init c'tor */
    CardinalityEvaluatorBase(Signature s) {
        super(s);
    }

    /** return minimal of the two Upper Bounds */
    int minUpperValue(int uv1, int uv2) {
        // noUpperValue is a maximal element
        if (uv1 == noUpperValue()) {
            return uv2;
        }
        if (uv2 == noUpperValue()) {
            return uv1;
        }
        // now return the smallest value, with anyUpperValue being
        // the smallest and deal with automatically
        return Math.min(uv1, uv2);
    }

    /** define a special value for concepts that are not in C[C}^{<= n} */
    int noUpperValue() {
        return -1;
    }

    /** define a special value for concepts that are in C[C]^{<= n} for all n */
    int anyUpperValue() {
        return 0;
    }

    /** return all or none values depending on the condition */
    int getAllNoneUpper(boolean condition) {
        return condition ? anyUpperValue() : noUpperValue();
    }

    /** define a special value for concepts that are not in C[C]^{>= n} */
    int noLowerValue() {
        return 0;
    }

    /** define a special value for concepts that are in C[C]^{>= n} for all n */
    int anyLowerValue() {
        return -1;
    }

    /** return 1 or none values depending on the condition */
    int getOneNoneLower(boolean condition) {
        return condition ? 1 : noLowerValue();
    }

    /** define a special value for concepts that are in C^{<= n} for all n */
    int getAllValue() {
        return 0;
    }

    /** define a special value for concepts that are not in C^{<= n} */
    int getNoneValue() {
        return -1;
    }

    /** implementation of evaluation */
    int getUpperBoundDirect(OWLObject expr) {
        return ubd.getValue(expr);
    }

    /** implementation of evaluation */
    int getUpperBoundComplement(OWLObject expr) {
        return ubc.getValue(expr);
    }

    /** implementation of evaluation */
    int getLowerBoundDirect(OWLObject expr) {
        return lbd.getValue(expr);
    }

    /** implementation of evaluation */
    int getLowerBoundComplement(OWLObject expr) {
        return lbc.getValue(expr);
    }

    /** main method to use */
    int getValue(OWLObject expr) {
        expr.accept(this);
        return value;
    }

    /** @return true if given upper VALUE is less than M */
    boolean isUpperLT(int value, int m) {
        if (value == noUpperValue()) {
            return false;
        }
        return value == anyUpperValue() || value < m;
    }

    /** @return true if given upper VALUE is less than or equal to M */
    boolean isUpperLE(int value, int m) {
        return isUpperLT(value, m + 1);
    }

    /** @return true if given lower VALUE is greater than or equal to M */
    boolean isLowerGE(int value, int m) {
        if (value == noLowerValue()) {
            return false;
        }
        return value == anyLowerValue() || value >= m;
    }

    /** @return true if given upper VALUE is greater than M */
    boolean isLowerGT(int value, int m) {
        return isLowerGE(value, m + 1);
    }

    boolean isBotEquivalent(OWLObject expr) {
        return isUpperLE(getUpperBoundDirect(expr), 0);
    }

    boolean isTopEquivalent(OWLObject expr) {
        return isUpperLE(getUpperBoundComplement(expr), 0);
    }

    abstract int getEntityValue(OWLEntity entity);

    abstract int getForallValue(OWLPropertyExpression r, OWLPropertyRange c);

    abstract int getMinValue(int m, OWLPropertyExpression r, OWLPropertyRange c);

    abstract int getMaxValue(int m, OWLPropertyExpression r, OWLPropertyRange c);

    abstract int getExactValue(int m, OWLPropertyExpression r, OWLPropertyRange c);

    void setEvaluators(UpperBoundDirectEvaluator pUD, LowerBoundDirectEvaluator pLD, UpperBoundComplementEvaluator pUC,
        LowerBoundComplementEvaluator pLC) {
        ubd = pUD;
        lbd = pLD;
        ubc = pUC;
        lbc = pLC;
        assert ubd == this || lbd == this || ubc == this || lbc == this;
    }

    /** implementation of evaluation */
    int getUpperBoundDirect(OWLClassExpression expr) {
        return getUpperBoundDirect(expr);
    }

    /** implementation of evaluation */
    int getUpperBoundComplement(OWLClassExpression expr) {
        return getUpperBoundComplement(expr);
    }

    /** implementation of evaluation */
    int getLowerBoundDirect(OWLClassExpression expr) {
        return getLowerBoundDirect(expr);
    }

    /** implementation of evaluation */
    int getLowerBoundComplement(OWLClassExpression expr) {
        return getLowerBoundComplement(expr);
    }

    // concept expressions
    @Override
    public void visit(OWLClass expr) {
        value = getEntityValue(expr);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom expr) {
        value = getMinValue(1, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom expr) {
        value = getForallValue(expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectMinCardinality expr) {
        value = getMinValue(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectMaxCardinality expr) {
        value = getMaxValue(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectExactCardinality expr) {
        value = getExactValue(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom expr) {
        value = getMinValue(1, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataAllValuesFrom expr) {
        value = getForallValue(expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataMinCardinality expr) {
        value = getMinValue(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataMaxCardinality expr) {
        value = getMaxValue(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataExactCardinality expr) {
        value = getExactValue(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    // object role expressions
    @Override
    public void visit(OWLObjectProperty p) {
        value = getEntityValue(p);
    }

    // data role expressions
    @Override
    public void visit(OWLDataProperty expr) {
        value = getEntityValue(expr);
    }
}
