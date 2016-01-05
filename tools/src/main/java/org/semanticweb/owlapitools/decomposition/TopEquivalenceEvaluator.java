package org.semanticweb.owlapitools.decomposition;

import org.semanticweb.owlapi.model.*;

/**
 * check whether class expressions are equivalent to top wrt given locality
 * class
 */
public class TopEquivalenceEvaluator extends SigAccessor implements OWLObjectVisitor {

    /** keep the value here */
    boolean isTopEq = false;

    // non-empty Concept/Data expression
    // / @return true iff C^I is non-empty
    private boolean isBotDistinct(OWLObject C) {
        // TOP is non-empty
        if (isTopEquivalent(C)) {
            return true;
        }
        // built-in DT are non-empty
        // FIXME!! that's it for now
        return C instanceof OWLDatatype;
    }

    // cardinality of a concept/data expression interpretation
    // / @return true if #C^I > n
    private boolean isCardLargerThan(OWLObject C, int n) {
        if (n == 0) {
            return isBotDistinct(C);
        }
        if (C instanceof OWLDatatype) {
            return ((OWLDatatype) C).isBuiltIn()
                && !((OWLDatatype) C).getBuiltInDatatype().isFinite();
        }
        // FIXME!! try to be more precise
        return false;
    }

    // QCRs
    // / @return true iff (>= n R.C) is topEq
    private boolean isMinTopEquivalent(int n, OWLObject R, OWLObject C) {
        return n == 0 || isTopEquivalent(R) && isCardLargerThan(C, n - 1);
    }

    // / @return true iff (<= n R.C) is topEq
    private boolean isMaxTopEquivalent(int n, OWLObject R, OWLObject C) {
        return localityChecker.isBotEquivalent(R) || localityChecker.isBotEquivalent(C);
    }

    /**
     * @param l
     *        locality checker
     */
    public TopEquivalenceEvaluator(LocalityChecker l) {
        super(l);
    }

    // ported from: boolean isTopEquivalent(Expression expr) {
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
    // ported from: public void visit(ConceptName expr) {
    @Override
    public void visit(OWLClass expr) {
        isTopEq = getSignature().topCLocal() && !getSignature().contains(expr);
    }

    // ported from: public void visit(ConceptNot expr) {
    @Override
    public void visit(OWLObjectComplementOf expr) {
        isTopEq = localityChecker.isBotEquivalent(expr.getOperand());
    }

    // ported from:public void visit(ConceptAnd expr) {
    @Override
    public void visit(OWLObjectIntersectionOf expr) {
        isTopEq = !expr.operands().anyMatch(p -> !isTopEquivalent(p));
    }

    // ported from: public void visit(ConceptOr expr) {
    @Override
    public void visit(OWLObjectUnionOf expr) {
        isTopEq = expr.operands().anyMatch(p -> isTopEquivalent(p));
    }

    // ported from: public void visit(ConceptOneOf expr) {
    @Override
    public void visit(OWLObjectOneOf expr) {
        isTopEq = false;
    }

    // ported from: public void visit(ConceptObjectSelf expr) {
    @Override
    public void visit(OWLObjectHasSelf expr) {
        isTopEq = isTopEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptObjectValue expr) {
    @Override
    public void visit(OWLObjectHasValue expr) {
        isTopEq = isTopEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptObjectExists expr) {
    @Override
    public void visit(OWLObjectSomeValuesFrom expr) {
        isTopEq = isMinTopEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectForall expr) {
    @Override
    public void visit(OWLObjectAllValuesFrom expr) {
        isTopEq = isTopEquivalent(expr.getFiller())
            || localityChecker.isBotEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptObjectMinCardinality expr) {
    @Override
    public void visit(OWLObjectMinCardinality expr) {
        isTopEq = isMinTopEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectMaxCardinality expr) {
    @Override
    public void visit(OWLObjectMaxCardinality expr) {
        isTopEq = isMaxTopEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectExactCardinality expr) {
    @Override
    public void visit(OWLObjectExactCardinality expr) {
        int n = expr.getCardinality();
        isTopEq = isMinTopEquivalent(n, expr.getProperty(), expr.getFiller())
            && isMaxTopEquivalent(n, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ConceptDataValue expr) {
    @Override
    public void visit(OWLDataHasValue expr) {
        isTopEq = isTopEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptDataExists expr) {
    @Override
    public void visit(OWLDataSomeValuesFrom expr) {
        isTopEq = isMinTopEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ConceptDataForall expr) {
    @Override
    public void visit(OWLDataAllValuesFrom expr) {
        isTopEq = isTopEquivalent(expr.getFiller())
            || localityChecker.isBotEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptDataMinCardinality expr) {
    @Override
    public void visit(OWLDataMinCardinality expr) {
        isTopEq = isMinTopEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptDataMaxCardinality expr) {
    @Override
    public void visit(OWLDataMaxCardinality expr) {
        isTopEq = isMaxTopEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptDataExactCardinality expr) {
    @Override
    public void visit(OWLDataExactCardinality expr) {
        int n = expr.getCardinality();
        isTopEq = isMinTopEquivalent(n, expr.getProperty(), expr.getFiller())
            && isMaxTopEquivalent(n, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ObjectRoleName expr) {
    @Override
    public void visit(OWLObjectProperty expr) {
        isTopEq = getSignature().topRLocal() && !getSignature().contains(expr);
    }

    // ported from: public void visit(ObjectRoleInverse expr) {
    @Override
    public void visit(OWLObjectInverseOf expr) {
        isTopEq = isTopEquivalent(expr.getInverse());
    }

    // data role expressions
    // ported from: public void visit(DataRoleName expr) {
    @Override
    public void visit(OWLDataProperty expr) {
        isTopEq = getSignature().topRLocal() && !getSignature().contains(expr);
    }

    // ported from: public void visit(Datatype<?> arg)
    @Override
    public void visit(OWLDatatype node) {
        isTopEq = node.isTopDatatype();
    }

    // ported from: public void visit(Literal<?> arg) {
    @Override
    public void visit(OWLLiteral node) {
        isTopEq = false;
    }

    // ported from: public void visit(DataNot expr) {
    @Override
    public void visit(OWLDataComplementOf node) {
        isTopEq = localityChecker.isBotEquivalent(node.getDataRange());
    }

    // ported from: public void visit(DataAnd expr)
    @Override
    public void visit(OWLDatatypeRestriction node) {
        isTopEq = node.isTopDatatype();
    }

    // ported from: public void visit(DataOneOf arg) {
    @Override
    public void visit(OWLDataOneOf node) {
        isTopEq = false;
    }
}
