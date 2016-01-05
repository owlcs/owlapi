package org.semanticweb.owlapitools.decomposition;

import org.semanticweb.owlapi.model.*;

/**
 * check whether class expressions are equivalent to bottom wrt given locality
 * class
 */
// XXX verify unused parameters
public class BotEquivalenceEvaluator extends SigAccessor implements OWLObjectVisitor {

    /** keep the value here */
    boolean isBotEq = false;

    /**
     * @param l
     *        l
     */
    public BotEquivalenceEvaluator(LocalityChecker l) {
        super(l);
    }

    /**
     * non-empty Concept/Data expression
     * 
     * @param C
     *        class
     * @return true iff C^I is non-empty
     */
    private boolean isBotDistinct(OWLObject C) {
        // TOP is non-empty
        if (localityChecker.isTopEquivalent(C)) {
            return true;
        }
        // built-in DT are non-empty
        // FIXME!! that's it for now
        return C instanceof OWLDatatype;
    }

    /**
     * cardinality of a concept/data expression interpretation
     * 
     * @return true if #C^I > n
     * @param C
     *        class
     * @param n
     *        cardinality
     */
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

    /**
     * QCRs
     * 
     * @param n
     *        cardinality
     * @param R
     *        property
     * @param C
     *        class
     * @return true iff (>= n R.C) is botEq
     */
    private boolean isMinBotEquivalent(int n, OWLObject R, OWLObject C) {
        return n > 0 && (isBotEquivalent(R) || isBotEquivalent(C));
    }

    /**
     * @param n
     *        cardinality
     * @param R
     *        property
     * @param C
     *        class
     * @return true iff (<= n R.C) is botEq
     */
    private boolean isMaxBotEquivalent(int n, OWLObject R, OWLObject C) {
        return isBotEquivalent(R) && isCardLargerThan(C, n);
    }

    /**
     * @param expr
     *        expression
     * @return true iff an EXPRession is equivalent to bottom wrt defined policy
     */
    boolean isBotEquivalent(OWLObject expr) {
        if (expr.isBottomEntity()) {
            return true;
        }
        if (expr.isTopEntity()) {
            return false;
        }
        expr.accept(this);
        return isBotEq;
    }

    // concept expressions
    // ported from: public void visit(ConceptName expr) {
    @Override
    public void visit(OWLClass expr) {
        isBotEq = !getSignature().topCLocal() && !getSignature().contains(expr);
    }

    // ported from: public void visit(ConceptNot expr) {
    @Override
    public void visit(OWLObjectComplementOf expr) {
        isBotEq = localityChecker.isTopEquivalent(expr.getOperand());
    }

    // ported from:public void visit(ConceptAnd expr) {
    @Override
    public void visit(OWLObjectIntersectionOf expr) {
        isBotEq = expr.operands().anyMatch(p -> isBotEquivalent(p));
    }

    // ported from: public void visit(ConceptOr expr) {
    @Override
    public void visit(OWLObjectUnionOf expr) {
        isBotEq = !expr.operands().anyMatch(p -> !isBotEquivalent(p));
    }

    // ported from: public void visit(ConceptOneOf expr) {
    @Override
    public void visit(OWLObjectOneOf expr) {
        isBotEq = expr.individuals().count() == 0;
    }

    // ported from: public void visit(ConceptObjectSelf expr) {
    @Override
    public void visit(OWLObjectHasSelf expr) {
        isBotEq = isBotEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptObjectValue expr) {
    @Override
    public void visit(OWLObjectHasValue expr) {
        isBotEq = isBotEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptObjectExists expr) {
    @Override
    public void visit(OWLObjectSomeValuesFrom expr) {
        isBotEq = isMinBotEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectForall expr) {
    @Override
    public void visit(OWLObjectAllValuesFrom expr) {
        isBotEq = localityChecker.isTopEquivalent(expr.getProperty())
            && isBotEquivalent(expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectMinCardinality expr) {
    @Override
    public void visit(OWLObjectMinCardinality expr) {
        isBotEq = isMinBotEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectMaxCardinality expr) {
    @Override
    public void visit(OWLObjectMaxCardinality expr) {
        isBotEq = isMaxBotEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptObjectExactCardinality expr) {
    @Override
    public void visit(OWLObjectExactCardinality expr) {
        int n = expr.getCardinality();
        isBotEq = isMinBotEquivalent(n, expr.getProperty(), expr.getFiller())
            || isMaxBotEquivalent(n, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ConceptDataValue expr) {
    @Override
    public void visit(OWLDataHasValue expr) {
        isBotEq = isBotEquivalent(expr.getProperty());
    }

    // ported from: public void visit(ConceptDataExists expr) {
    @Override
    public void visit(OWLDataSomeValuesFrom expr) {
        isBotEq = isMinBotEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    // ported from: public void visit(ConceptDataForall expr) {
    @Override
    public void visit(OWLDataAllValuesFrom expr) {
        isBotEq = localityChecker.isTopEquivalent(expr.getProperty())
            && !localityChecker.isTopEquivalent(expr.getFiller());
    }

    // ported from: public void visit(ConceptDataMinCardinality expr) {
    @Override
    public void visit(OWLDataMinCardinality expr) {
        isBotEq = isMinBotEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptDataMaxCardinality expr) {
    @Override
    public void visit(OWLDataMaxCardinality expr) {
        isBotEq = isMaxBotEquivalent(expr.getCardinality(), expr.getProperty(),
            expr.getFiller());
    }

    // ported from: public void visit(ConceptDataExactCardinality expr) {
    @Override
    public void visit(OWLDataExactCardinality expr) {
        int n = expr.getCardinality();
        isBotEq = isMinBotEquivalent(n, expr.getProperty(), expr.getFiller())
            || isMaxBotEquivalent(n, expr.getProperty(), expr.getFiller());
    }

    // object role expressions
    // ported from: public void visit(ObjectRoleName expr) {
    @Override
    public void visit(OWLObjectProperty expr) {
        isBotEq = !getSignature().topRLocal() && !getSignature().contains(expr);
    }

    // ported from: public void visit(ObjectRoleInverse expr) {
    @Override
    public void visit(OWLObjectInverseOf expr) {
        isBotEq = isBotEquivalent(expr.getInverse());
    }

    // data role expressions
    // ported from: public void visit(DataRoleName expr) {
    @Override
    public void visit(OWLDataProperty expr) {
        isBotEq = !getSignature().topRLocal() && !getSignature().contains(expr);
    }

    // ported from: public void visit(Datatype<?> arg)
    @Override
    public void visit(OWLDatatype node) {
        isBotEq = node.isBottomEntity();
    }

    // ported from: public void visit(Literal<?> arg) {
    @Override
    public void visit(OWLLiteral node) {
        isBotEq = false;
    }

    // ported from: public void visit(DataNot expr) {
    @Override
    public void visit(OWLDataComplementOf node) {
        isBotEq = localityChecker.isTopEquivalent(node.getDataRange());
    }

    // ported from: public void visit(DataOneOf arg) {
    @Override
    public void visit(OWLDataOneOf node) {
        isBotEq = node.values().count() == 0;
    }
}
