package uk.ac.manchester.cs.factplusplusad;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.semanticweb.owlapi.model.HasOperands;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

/**
 * Determine how many instances can an expression have. All methods return minimal n such that
 * expr\in C^{<= n}, n >= 0
 */
class UpperBoundDirectEvaluator extends CardinalityEvaluatorBase {

    /**
     * init c'tor
     *
     * @param s signature
     */
    UpperBoundDirectEvaluator(Signature s) {
        super(s);
    }

    @Override
    int getEntityValue(OWLEntity entity) {
        if (entity.isTopEntity()) {
            return noUpperValue();
        }
        if (entity.isBottomEntity()) {
            return anyUpperValue();
        }
        return getAllNoneUpper(botCLocal() && nc(entity));
    }

    @Override
    int getForallValue(OWLPropertyExpression r, OWLPropertyRange c) {
        return getAllNoneUpper(isTopEquivalent(r) && isLowerGE(getLowerBoundComplement(c), 1));
    }

    @Override
    int getMinValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // m > 0 and...
        if (m <= 0) {
            return getNoneValue();
        }
        // R = \bot or...
        if (isBotEquivalent(r)) {
            return anyUpperValue();
        }
        // C \in C^{<= m-1}
        return getAllNoneUpper(isUpperLT(getUpperBoundDirect(c), m));
    }

    @Override
    int getMaxValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // R = \top and...
        if (!isTopEquivalent(r)) {
            return noUpperValue();
        }
        // C\in C^{>= m+1}
        return getAllNoneUpper(isLowerGT(getLowerBoundDirect(c), m));
    }

    @Override
    int getExactValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // conjunction of Min and Max values
        return minUpperValue(getMinValue(m, r, c), getMaxValue(m, r, c));
    }

    <C extends OWLObject> int getAndValue(HasOperands<C> expr) {
        // noUpperValue is a maximal element
        AtomicInteger min = new AtomicInteger(noUpperValue());
        // we are looking for the minimal value here, use an appropriate helper
        expr.operands().forEach(p -> min.set(minUpperValue(min.get(), getUpperBoundDirect(p))));
        return min.get();
    }

    <C extends OWLObject> int getOrValue(HasOperands<C> expr) {
        int sum = 0;
        int n;
        Iterator<C> it = expr.operands().iterator();
        while (it.hasNext()) {
            C p = it.next();
            n = getUpperBoundDirect(p);
            if (n == noUpperValue()) {
                return noUpperValue();
            }
            sum += n;
        }
        return sum;
    }

    // concept expressions
    @Override
    public void visit(OWLObjectComplementOf expr) {
        value = getUpperBoundComplement(expr.getOperand());
    }

    @Override
    public void visit(OWLObjectIntersectionOf expr) {
        value = getAndValue(expr);
    }

    @Override
    public void visit(OWLObjectUnionOf expr) {
        value = getOrValue(expr);
    }

    @Override
    public void visit(OWLObjectOneOf expr) {
        value = (int) expr.individuals().count();
    }

    @Override
    public void visit(OWLObjectHasSelf expr) {
        value = getAllNoneUpper(isBotEquivalent(expr.getProperty()));
    }

    @Override
    public void visit(OWLObjectHasValue expr) {
        value = getAllNoneUpper(isBotEquivalent(expr.getProperty()));
    }

    @Override
    public void visit(OWLDataHasValue expr) {
        value = getAllNoneUpper(isBotEquivalent(expr.getProperty()));
    }

    // object role expressions
    @Override
    public void visit(OWLObjectInverseOf expr) {
        value = getUpperBoundDirect(expr.getInverseProperty());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom expr) {
        for (OWLObjectPropertyExpression p : expr.getPropertyChain()) {
            if (isBotEquivalent(p)) {
                value = anyUpperValue();
                return;
            }
        }
        value = noUpperValue();
    }

    @Override
    public void visit(OWLLiteral o) {
        value = 1;
    }

    @Override
    public void visit(OWLDataComplementOf expr) {
        value = getUpperBoundComplement(expr.getDataRange());
    }

    @Override
    public void visit(OWLDataIntersectionOf expr) {
        value = getAndValue(expr);
    }

    @Override
    public void visit(OWLDataUnionOf expr) {
        value = getOrValue(expr);
    }

    @Override
    public void visit(OWLDataOneOf expr) {
        value = (int) expr.values().count();
    }
}
