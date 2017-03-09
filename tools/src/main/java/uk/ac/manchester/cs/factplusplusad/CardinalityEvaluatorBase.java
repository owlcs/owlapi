package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;

abstract class CardinalityEvaluatorBase extends SigAccessor implements OWLObjectVisitor {

    UpperBoundDirectEvaluator ubd;
    LowerBoundDirectEvaluator lbd;
    UpperBoundComplementEvaluator ubc;
    LowerBoundComplementEvaluator lbc;
    /**
     * keep the value here
     */
    int value = 0;

    /**
     * init c'tor
     *
     * @param s signature
     */
    CardinalityEvaluatorBase(Signature s) {
        super(s);
    }

    /**
     * return minimal of the two Upper Bounds
     *
     * @param uv1 values to compare
     * @param uv2 values to compare
     * @return min value
     */
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

    /**
     * @return special value for concepts that are not in C[C}^{<= n}
     */
    int noUpperValue() {
        return -1;
    }

    /**
     * @return special value for concepts that are in C[C]^{<= n} for all n
     */
    int anyUpperValue() {
        return 0;
    }

    /**
     * @param condition condition to test
     * @return all or none values depending on the condition
     */
    int getAllNoneUpper(boolean condition) {
        return condition ? anyUpperValue() : noUpperValue();
    }

    /**
     * @return special value for concepts that are not in C[C]^{>= n}
     */
    int noLowerValue() {
        return 0;
    }

    /**
     * @return special value for concepts that are in C[C]^{>= n} for all n
     */
    int anyLowerValue() {
        return -1;
    }

    /**
     * @param condition condition to test
     * @return 1 or none values depending on the condition
     */
    int getOneNoneLower(boolean condition) {
        return condition ? 1 : noLowerValue();
    }

    /**
     * @return special value for concepts that are in C^{<= n} for all n
     */
    int getAllValue() {
        return 0;
    }

    /**
     * @return special value for concepts that are not in C^{<= n}
     */
    int getNoneValue() {
        return -1;
    }

    int getUpperBoundDirect(OWLObject expr) {
        return ubd.getValue(expr);
    }

    int getUpperBoundComplement(OWLObject expr) {
        return ubc.getValue(expr);
    }

    int getLowerBoundDirect(OWLObject expr) {
        return lbd.getValue(expr);
    }

    int getLowerBoundComplement(OWLObject expr) {
        return lbc.getValue(expr);
    }

    /**
     * Main method to use.
     *
     * @param expr expression
     * @return value
     */
    int getValue(OWLObject expr) {
        expr.accept(this);
        return value;
    }

    /**
     * @param v value to test
     * @param m upper limit
     * @return true if given upper VALUE is less than M
     */
    boolean isUpperLT(int v, int m) {
        if (v == noUpperValue()) {
            return false;
        }
        return v == anyUpperValue() || v < m;
    }

    /**
     * @param v value to test
     * @param m upper limit
     * @return true if given upper VALUE is less than or equal to M
     */
    boolean isUpperLE(int v, int m) {
        return isUpperLT(v, m + 1);
    }

    /**
     * @param v value to test
     * @param m upper limit
     * @return true if given lower VALUE is greater than or equal to M
     */
    boolean isLowerGE(int v, int m) {
        if (v == noLowerValue()) {
            return false;
        }
        return v == anyLowerValue() || v >= m;
    }

    /**
     * @param v value to test
     * @param m upper limit
     * @return true if given upper VALUE is greater than M
     */
    boolean isLowerGT(int v, int m) {
        return isLowerGE(v, m + 1);
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

    void setEvaluators(UpperBoundDirectEvaluator pUD, LowerBoundDirectEvaluator pLD,
        UpperBoundComplementEvaluator pUC, LowerBoundComplementEvaluator pLC) {
        ubd = pUD;
        lbd = pLD;
        ubc = pUC;
        lbc = pLC;
        assert ubd == this || lbd == this || ubc == this || lbc == this;
    }

    int getUpperBoundDirect(OWLClassExpression expr) {
        return ubd.getUpperBoundDirect(expr);
    }

    int getUpperBoundComplement(OWLClassExpression expr) {
        return ubc.getUpperBoundComplement(expr);
    }

    int getLowerBoundDirect(OWLClassExpression expr) {
        return lbd.getLowerBoundDirect(expr);
    }

    int getLowerBoundComplement(OWLClassExpression expr) {
        return lbc.getLowerBoundComplement(expr);
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
