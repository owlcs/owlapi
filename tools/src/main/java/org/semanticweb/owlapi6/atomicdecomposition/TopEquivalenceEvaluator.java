package org.semanticweb.owlapi6.atomicdecomposition;

import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectHasValue;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.OWLPropertyRange;

/**
 * check whether class expressions are equivalent to top wrt given locality
 * class
 */
public class TopEquivalenceEvaluator extends SigAccessor implements OWLObjectVisitor {

    /**
     * keep the value here
     */
    boolean isTopEq = false;

    /**
     * @param l
     *        locality checker
     */
    public TopEquivalenceEvaluator(LocalityChecker l) {
        super(l);
    }

    // non-empty Concept/Data expression
    /**
     * @param c
     *        C
     * @return true iff C^I is non-empty
     */
    private boolean isBotDistinct(OWLObject c) {
        // TOP is non-empty
        if (isTopEquivalent(c)) {
            return true;
        }
        // built-in DT are non-empty
        // FIXME!! that's it for now
        return c instanceof OWLDatatype;
    }

    // cardinality of a concept/data expression interpretation
    /**
     * @param c
     *        C
     * @param n
     *        cardinality
     * @return true if {@code #C^I > n}
     */
    private boolean isCardLargerThan(OWLObject c, int n) {
        if (n == 0) {
            return isBotDistinct(c);
        }
        if (c instanceof OWLDatatype) {
            return ((OWLDatatype) c).isBuiltIn() && !((OWLDatatype) c).getBuiltInDatatype().isFinite();
        }
        // FIXME!! try to be more precise
        return false;
    }

    // QCRs
    /**
     * @param n
     *        cardinality
     * @param r
     *        role
     * @param c
     *        filler
     * @return true iff {@code (>= n R.C)} is topEq
     */
    private boolean isMinTopEquivalent(int n, OWLPropertyExpression r, OWLPropertyRange c) {
        return n == 0 || isTopEquivalent(r) && isCardLargerThan(c, n - 1);
    }

    /**
     * @param n
     *        cardinality
     * @param r
     *        role
     * @param c
     *        filler
     * @return true iff {@code (<= n R.C)} is topEq
     */
    private boolean isMaxTopEquivalent(@SuppressWarnings("unused") int n, OWLPropertyExpression r, OWLPropertyRange c) {
        return localityChecker.isBotEquivalent(r) || localityChecker.isBotEquivalent(c);
    }

    /**
     * @param expr
     *        expression to check
     * @return true iff an EXPRession is equivalent to top wrt defined policy
     */
    public boolean isTopEquivalent(OWLObject expr) {
        if (expr.isTopEntity()) {
            return true;
        }
        if (expr.isBottomEntity()) {
            return false;
        }
        expr.accept(this);
        return isTopEq;
    }

    // concept expressions
    @Override
    public void visit(OWLClass expr) {
        isTopEq = getSignature().topCLocal() && !getSignature().contains(expr);
    }

    @Override
    public void visit(OWLObjectComplementOf expr) {
        isTopEq = localityChecker.isBotEquivalent(expr.getOperand());
    }

    @Override
    public void visit(OWLObjectIntersectionOf expr) {
        isTopEq = expr.operands().allMatch(p -> isTopEquivalent(p));
    }

    @Override
    public void visit(OWLObjectUnionOf expr) {
        isTopEq = expr.operands().anyMatch(this::isTopEquivalent);
    }

    @Override
    public void visit(OWLObjectOneOf expr) {
        isTopEq = false;
    }

    @Override
    public void visit(OWLObjectHasSelf expr) {
        isTopEq = isTopEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLObjectHasValue expr) {
        isTopEq = isTopEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom expr) {
        isTopEq = isMinTopEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom expr) {
        isTopEq = isTopEquivalent(expr.getFiller()) || localityChecker.isBotEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality expr) {
        isTopEq = isMinTopEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectMaxCardinality expr) {
        isTopEq = isMaxTopEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectExactCardinality expr) {
        int n = expr.getCardinality();
        isTopEq = isMinTopEquivalent(n, expr.getProperty(), expr.getFiller())
            && isMaxTopEquivalent(n, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataHasValue expr) {
        isTopEq = isTopEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom expr) {
        isTopEq = isMinTopEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataAllValuesFrom expr) {
        isTopEq = isTopEquivalent(expr.getFiller()) || localityChecker.isBotEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality expr) {
        isTopEq = isMinTopEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataMaxCardinality expr) {
        isTopEq = isMaxTopEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataExactCardinality expr) {
        int n = expr.getCardinality();
        isTopEq = isMinTopEquivalent(n, expr.getProperty(), expr.getFiller())
            && isMaxTopEquivalent(n, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectProperty expr) {
        isTopEq = getSignature().topRLocal() && !getSignature().contains(expr);
    }

    @Override
    public void visit(OWLObjectInverseOf expr) {
        isTopEq = isTopEquivalent(expr.getInverse());
    }

    // data role expressions
    @Override
    public void visit(OWLDataProperty expr) {
        isTopEq = getSignature().topRLocal() && !getSignature().contains(expr);
    }

    @Override
    public void visit(OWLDatatype node) {
        isTopEq = node.isTopDatatype();
    }

    @Override
    public void visit(OWLLiteral node) {
        isTopEq = false;
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        isTopEq = localityChecker.isBotEquivalent(node.getDataRange());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        isTopEq = node.isTopDatatype();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        isTopEq = false;
    }
}
