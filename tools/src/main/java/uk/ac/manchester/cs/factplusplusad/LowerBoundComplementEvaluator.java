package uk.ac.manchester.cs.factplusplusad;

import java.util.Iterator;

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
 * Determine how many instances can an expression have. All methods return maximal n such that
 * expr\in CC^{>= n}, n >= 1
 */
class LowerBoundComplementEvaluator extends CardinalityEvaluatorBase {

    LowerBoundComplementEvaluator(Signature s) {
        super(s);
    }

    @Override
    int getNoneValue() {
        return 0;
    }

    @Override
    int getAllValue() {
        return -1;
    }

    @Override
    int getOneNoneLower(boolean v) {
        return v ? 1 : getNoneValue();
    }

    // TODO: checks only C top-locality, not R */
    @Override
    int getEntityValue(OWLEntity entity) {
        if (entity.isTopEntity()) {
            return noLowerValue();
        }
        if ((entity.isOWLObjectProperty() || entity.isOWLDataProperty() || entity.isOWLDatatype())
            && entity.isBottomEntity()) {
            return anyLowerValue();
        }
        if (entity.isBottomEntity()) {
            return 1;
        }
        return getOneNoneLower(botCLocal() && nc(entity));
    }

    @Override
    int getForallValue(OWLPropertyExpression r, OWLPropertyRange c) {
        return getOneNoneLower(isTopEquivalent(r) && isLowerGE(getLowerBoundComplement(c), 1));
    }

    @Override
    int getMinValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // m > 0 and...
        if (m <= 0) {
            return noLowerValue();
        }
        // R = \bot or...
        if (isBotEquivalent(r)) {
            return 1;
        }
        // C \in C^{<= m-1}
        return getOneNoneLower(isUpperLT(getUpperBoundDirect(c), m));
    }

    @Override
    int getMaxValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // R = \top and...
        if (!isTopEquivalent(r)) {
            return noLowerValue();
        }
        // C\in C^{>= m+1}
        if (isLowerGT(getLowerBoundDirect(c), m)) {
            return m + 1;
        } else {
            return noLowerValue();
        }
    }

    @Override
    int getExactValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // here the maximal value between Mix and Max is an answer. The -1
        // case will be dealt with automagically
        // because both min and max are between 0 and m+1
        return Math.max(getMinValue(m, r, c), getMaxValue(m, r, c));
    }

    <C extends OWLObject> int getAndValue(HasOperands<C> expr) {
        int max = getNoneValue();
        // we are looking for the maximal value here; ANY need to be
        // special-cased
        Iterator<C> it = expr.operands().iterator();
        while (it.hasNext()) {
            C p = it.next();
            int n = getLowerBoundComplement(p);
            if (n == anyLowerValue()) {
                return anyLowerValue();
            }
            max = Math.max(max, n);
        }
        return max;
    }

    <C extends OWLObject> int getOrValue(HasOperands<C> expr) {
        // return m - sumK, where
        // true if found a conjunct that is in C^{>=}
        boolean foundC = false;
        int foundM = 0;
        // the m- and k- values for the C_j with max m+k
        int mMax = 0, kMax = 0;
        // sum of all known k
        int sumK = 0;
        // 1st pass: check for none-case, deals with deterministic cases
        Iterator<C> it = expr.operands().iterator();
        while (it.hasNext()) {
            C p = it.next();
            // C_j \in CC^{>= m}
            int m = getLowerBoundComplement(p);
            // C_j \in C^{<= k}
            int k = getUpperBoundDirect(p);
            // note bound flip for K
            // case 0: if both aren't known then we don't know
            if (m == noLowerValue() && k == noUpperValue()) {
                return noLowerValue();
            }
            // if only k exists then add it to k
            if (m == noLowerValue()) {
                sumK += k;
                continue;
            }
            // if only m exists then set it to m
            if (k == noUpperValue()) {
                if (foundC) {
                    // should not have 2 elements in C
                    return noLowerValue();
                }
                foundC = true;
                foundM = m;
                continue;
            }
            // here both k and m are values
            sumK += k;
            // count k for the
            if (k + m > kMax + mMax) {
                kMax = k;
                mMax = m;
            }
        }
        // here we know the best candidate for M, and only need to set it up
        if (foundC) {
            // found during the deterministic case
            foundM -= sumK;
            return foundM > 0 ? foundM : noLowerValue();
        } else {
            // no deterministic option; choose the best one
            sumK -= kMax;
            mMax -= sumK;
            return mMax > 0 ? mMax : noLowerValue();
        }
    }

    // concept expressions
    @Override
    public void visit(OWLObjectComplementOf expr) {
        value = getLowerBoundDirect(expr.getOperand());
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
    public void visit(OWLObjectOneOf o) {
        value = noLowerValue();
    }

    @Override
    public void visit(OWLObjectHasSelf expr) {
        value = getOneNoneLower(isBotEquivalent(expr.getProperty()));
    }

    @Override
    public void visit(OWLObjectHasValue expr) {
        value = getOneNoneLower(isBotEquivalent(expr.getProperty()));
    }

    @Override
    public void visit(OWLDataHasValue expr) {
        value = getOneNoneLower(isBotEquivalent(expr.getProperty()));
    }

    // object role expressions
    @Override
    public void visit(OWLObjectInverseOf expr) {
        value = getLowerBoundComplement(expr.getInverseProperty());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom expr) {
        for (OWLObjectPropertyExpression p : expr.getPropertyChain()) {
            if (isBotEquivalent(p)) {
                value = anyLowerValue();
                return;
            }
        }
        value = noLowerValue();
    }

    @Override
    public void visit(OWLLiteral o) {
        value = 1;
    }

    @Override
    public void visit(OWLDataComplementOf expr) {
        value = getLowerBoundDirect(expr.getDataRange());
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
    public void visit(OWLDataOneOf o) {
        value = noLowerValue();
    }
}
