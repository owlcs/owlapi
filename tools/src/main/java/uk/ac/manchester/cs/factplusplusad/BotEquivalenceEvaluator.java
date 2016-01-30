package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * check whether class expressions are equivalent to bottom wrt given locality
 * class
 */
class BotEquivalenceEvaluator extends SigAccessor implements OWLObjectVisitor {

    /** keep the value here */
    boolean isBotEq = false;
    /** corresponding top evaluator */
    TopEquivalenceEvaluator topEval;

    public BotEquivalenceEvaluator(Signature s) {
        super(s);
    }

    /**
     * check whether the expression is top-equivalent
     * 
     * @param expr
     *        expression
     * @return true if top equivalent
     */
    boolean isTopEquivalent(OWLObject expr) {
        return topEval.isTopEquivalent(expr);
    }

    /**
     * non-empty Concept/Data expression
     * 
     * @param c
     *        class
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

    /**
     * cardinality of a concept/data expression interpretation
     * 
     * @return true if #C^I > n
     * @param c
     *        class
     * @param n
     *        cardinality
     */
    private boolean isCardLargerThan(OWLObject c, int n) {
        if (n == 0) {
            return isBotDistinct(c);
        }
        // data top is infinite
        if (c instanceof OWLDataRange && isTopEquivalent(c)) {
            return true;
        }
        if (c instanceof OWLDatatype) {
            // string/time are infinite DT
            OWLDatatype dt = ((OWLDatatype) c).asOWLDatatype();
            if (OWL2Datatype.XSD_STRING.matches(dt) || OWL2Datatype.XSD_DATE_TIME.matches(dt)
                || OWL2Datatype.XSD_DATE_TIME_STAMP.matches(dt)) {
                return true;
            }
        }
        // FIXME!! try to be more precise
        return false;
    }

    /**
     * @param n
     *        cardinality
     * @param r
     *        property
     * @param c
     *        class
     * @return true iff (<= n R.C) is botEq
     */
    private boolean isMaxBotEquivalent(int n, OWLPropertyExpression r, OWLPropertyRange c) {
        // XXX check
        return isBotEquivalent(r) && isCardLargerThan(c, n);
    }

    /**
     * QCRs
     * 
     * @param n
     *        cardinality
     * @param r
     *        property
     * @param c
     *        class
     * @return true iff (>= n R.C) is botEq
     */
    private boolean isMinBotEquivalent(int n, OWLPropertyExpression r, OWLPropertyRange c) {
        return n > 0 && (isBotEquivalent(r) || isBotEquivalent(c));
    }

    // set fields
    /**
     * set the corresponding top evaluator
     * 
     * @param eval
     *        top evaluator
     */
    void setTopEval(TopEquivalenceEvaluator eval) {
        topEval = eval;
    }

    /**
     * @param expr
     *        expression
     * @return true iff an EXPRession is equivalent to bottom wrt defined policy
     */
    boolean isBotEquivalent(OWLObject expr) {
        // XXX check
        if (expr.isBottomEntity()) {
            return true;
        }
        if (expr.isTopEntity()) {
            return false;
        }
        expr.accept(this);
        return isBotEq;
    }

    @Override
    public void visit(OWLClass expr) {
        // XXX check
        isBotEq = !topCLocal() && nc(expr);
    }

    @Override
    public void visit(OWLObjectComplementOf expr) {
        isBotEq = isTopEquivalent(expr.getOperand());
    }

    @Override
    public void visit(OWLObjectIntersectionOf expr) {
        isBotEq = expr.operands().anyMatch(this::isBotEquivalent);
    }

    @Override
    public void visit(OWLObjectUnionOf expr) {
        isBotEq = !expr.operands().anyMatch(p -> !isBotEquivalent(p));
    }

    @Override
    public void visit(OWLObjectOneOf expr) {
        isBotEq = expr.individuals().count() == 0;
    }

    @Override
    public void visit(OWLObjectHasSelf expr) {
        isBotEq = isBotEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLObjectHasValue expr) {
        isBotEq = isBotEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom expr) {
        isBotEq = isMinBotEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom expr) {
        isBotEq = isTopEquivalent(expr.getProperty()) && isBotEquivalent(expr.getFiller());
    }

    @Override
    public void visit(OWLObjectMinCardinality expr) {
        isBotEq = isMinBotEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectMaxCardinality expr) {
        isBotEq = isMaxBotEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectExactCardinality expr) {
        int n = expr.getCardinality();
        isBotEq = isMinBotEquivalent(n, expr.getProperty(), expr.getFiller()) || isMaxBotEquivalent(n, expr
            .getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataHasValue expr) {
        isBotEq = isBotEquivalent(expr.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom expr) {
        isBotEq = isMinBotEquivalent(1, expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataAllValuesFrom expr) {
        isBotEq = isTopEquivalent(expr.getProperty()) && !isTopEquivalent(expr.getFiller());
    }

    @Override
    public void visit(OWLDataMinCardinality expr) {
        isBotEq = isMinBotEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataMaxCardinality expr) {
        isBotEq = isMaxBotEquivalent(expr.getCardinality(), expr.getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLDataExactCardinality expr) {
        int n = expr.getCardinality();
        isBotEq = isMinBotEquivalent(n, expr.getProperty(), expr.getFiller()) || isMaxBotEquivalent(n, expr
            .getProperty(), expr.getFiller());
    }

    @Override
    public void visit(OWLObjectProperty expr) {
        // XXX check
        isBotEq = !topRLocal() && nc(expr);
    }

    @Override
    public void visit(OWLObjectInverseOf expr) {
        isBotEq = isBotEquivalent(expr.getInverseProperty());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom expr) {
        isBotEq = true;
        for (OWLObjectPropertyExpression p : expr.getPropertyChain()) {
            if (isBotEquivalent(p)) {
                return;
            }
        }
        isBotEq = false;
    }

    @Override
    public void visit(OWLDataProperty expr) {
        if (expr.isTopEntity()) {
            isBotEq = false;
            return;
        }
        if (expr.isBottomEntity()) {
            isBotEq = true;
            return;
        }
        isBotEq = !topRLocal() && nc(expr);
    }

    @Override
    public void visit(OWLDatatype node) {
        isBotEq = node.isBottomEntity();
    }

    @Override
    public void visit(OWLLiteral node) {
        isBotEq = false;
    }

    @Override
    public void visit(OWLDatatypeRestriction o) {
        isBotEq = false;
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        isBotEq = isTopEquivalent(node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        isBotEq = node.values().count() == 0;
    }

    @Override
    public void visit(OWLDataIntersectionOf expr) {
        if (expr.operands().anyMatch(this::isBotEquivalent)) {
            return;
        }
        isBotEq = false;
    }

    @Override
    public void visit(OWLDataUnionOf expr) {
        if (expr.operands().anyMatch(p -> !isBotEquivalent(p))) {
            return;
        }
        isBotEq = true;
    }
}
