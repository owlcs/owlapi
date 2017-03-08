package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * check whether class expressions are equivalent to top wrt given locality class
 */
class TopEquivalenceEvaluator extends SigAccessor implements OWLObjectVisitor {

    /**
     * corresponding bottom evaluator
     */
    BotEquivalenceEvaluator botEval;
    /**
     * keep the value here
     */
    boolean isTopEq = false;

    /**
     * init c'tor
     *
     * @param s signature
     */
    @SuppressWarnings("null")
    TopEquivalenceEvaluator(Signature s) {
        super(s);
    }

    boolean isBotEquivalent(OWLObject expr) {
        return botEval.isBotEquivalent(expr);
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
        return isBotEquivalent(r) || isBotEquivalent(c);
    }

    void setBotEval(BotEquivalenceEvaluator eval) {
        botEval = eval;
    }

    /**
     * @param expr expression to check
     * @return true iff an EXPRession is equivalent to top wrt defined policy
     */
    public boolean isTopEquivalent(OWLObject expr) {
        // XXX check
        if (expr.isTopEntity()) {
            return true;
        }
        if (expr.isBottomEntity()) {
            return false;
        }
        expr.accept(this);
        return isTopEq;
    }

    @Override
    public void visit(OWLClass expr) {
        // XXX check
        isTopEq = topCLocal() && nc(expr);
    }

    @Override
    public void visit(OWLObjectComplementOf expr) {
        isTopEq = isBotEquivalent(expr.getOperand());
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
        isTopEq = isTopEquivalent(expr.getFiller()) || isBotEquivalent(expr.getProperty());
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
        isTopEq = isTopEquivalent(expr.getFiller()) || isBotEquivalent(expr.getProperty());
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
        // XXX check
        isTopEq = topRLocal() && nc(expr);
    }

    @Override
    public void visit(OWLObjectInverseOf expr) {
        isTopEq = isTopEquivalent(expr.getInverse());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom expr) {
        isTopEq = false;
        for (OWLObjectPropertyExpression p : expr.getPropertyChain()) {
            if (!isTopEquivalent(p)) {
                return;
            }
        }
        isTopEq = true;
    }

    // data role expressions
    @Override
    public void visit(OWLDataProperty expr) {
        // XXX check
        isTopEq = topRLocal() && nc(expr);
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
        isTopEq = isBotEquivalent(node.getDataRange());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        isTopEq = node.isTopDatatype();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        isTopEq = false;
    }

    @Override
    public void visit(OWLDataIntersectionOf expr) {
        isTopEq = expr.operands().anyMatch(p -> !isTopEquivalent(p));
    }

    @Override
    public void visit(OWLDataUnionOf expr) {
        isTopEq = expr.operands().anyMatch(this::isTopEquivalent);
    }
}
