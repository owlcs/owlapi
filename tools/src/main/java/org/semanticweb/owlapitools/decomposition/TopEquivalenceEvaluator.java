package org.semanticweb.owlapitools.decomposition;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
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
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;

/**
 * check whether class expressions are equivalent to top wrt given locality class
 */
public class TopEquivalenceEvaluator extends SigAccessor implements OWLObjectVisitor {

    /**
     * keep the value here
     */
    boolean isTopEq = false;

    /**
     * @param l locality checker
     */
    public TopEquivalenceEvaluator(LocalityChecker l) {
        super(l);
    }

    // non-empty Concept/Data expression

    /**
     * @param c C
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
     * @param c C
     * @param n cardinality
     * @return true if #C^I > n
     */
    private boolean isCardLargerThan(OWLObject c, int n) {
        if (n == 0) {
            return isBotDistinct(c);
        }
        if (c instanceof OWLDatatype) {
            return ((OWLDatatype) c).isBuiltIn()
                && !((OWLDatatype) c).getBuiltInDatatype().isFinite();
        }
        // FIXME!! try to be more precise
        return false;
    }

    // QCRs

    /**
     * @param n cardinality
     * @param r role
     * @param c filler
     * @return true iff (>= n R.C) is topEq
     */
    private boolean isMinTopEquivalent(int n, OWLPropertyExpression r, OWLPropertyRange c) {
        return n == 0 || isTopEquivalent(r) && isCardLargerThan(c, n - 1);
    }

    /**
     * @param n cardinality
     * @param r role
     * @param c filler
     * @return true iff (<= n R.C) is topEq
     */
    private boolean isMaxTopEquivalent(@SuppressWarnings("unused") int n, OWLPropertyExpression r,
        OWLPropertyRange c) {
        return localityChecker.isBotEquivalent(r) || localityChecker.isBotEquivalent(c);
    }

    /**
     * @param expr expression to check
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
        isTopEq = !expr.operands().anyMatch(p -> !isTopEquivalent(p));
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
        isTopEq = isTopEquivalent(expr.getFiller())
            || localityChecker.isBotEquivalent(expr.getProperty());
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
        isTopEq = isTopEquivalent(expr.getFiller())
            || localityChecker.isBotEquivalent(expr.getProperty());
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
