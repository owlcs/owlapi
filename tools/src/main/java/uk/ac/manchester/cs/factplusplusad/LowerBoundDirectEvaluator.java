package uk.ac.manchester.cs.factplusplusad;

import java.util.Iterator;

import org.semanticweb.owlapi.model.HasOperands;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
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
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Determine how many instances can an expression have. All methods return maximal n such that
 * expr\in C^{>= n}, n >= 1
 */
class LowerBoundDirectEvaluator extends CardinalityEvaluatorBase {

    LowerBoundDirectEvaluator(Signature s) {
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

    // TODO: checks only C top-locality, not R
    @Override
    int getEntityValue(OWLEntity entity) {
        if (entity.isTopEntity()) {
            if (OWLRDFVocabulary.OWL_THING.getIRI().equals(entity.getIRI())) {
                return 1;
            }
            return anyLowerValue();
        }
        if (entity.isBottomEntity()) {
            return noLowerValue();
        }
        return getOneNoneLower(topCLocal() && nc(entity));
    }

    /**
     * helper for All
     */
    @Override
    int getForallValue(OWLPropertyExpression r, OWLPropertyRange c) {
        return getOneNoneLower(isBotEquivalent(r) || isUpperLE(getUpperBoundComplement(c), 0));
    }

    @Override
    int getMinValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // m == 0 or...
        if (m == 0) {
            return anyLowerValue();
        }
        // R = \top and...
        if (!isTopEquivalent(r)) {
            return noLowerValue();
        }
        // C \in C^{>= m}
        return isLowerGE(getLowerBoundDirect(c), m) ? m : noLowerValue();
    }

    @Override
    int getMaxValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        // R = \bot or...
        if (isBotEquivalent(r)) {
            return 1;
        }
        // C\in C^{<= m}
        return getOneNoneLower(isUpperLE(getUpperBoundDirect(c), m));
    }

    @Override
    int getExactValue(int m, OWLPropertyExpression r, OWLPropertyRange c) {
        int min = getMinValue(m, r, c), max = getMaxValue(m, r, c);
        // we need to take the lowest value
        if (min == noLowerValue() || max == noLowerValue()) {
            return noLowerValue();
        }
        if (min == anyLowerValue()) {
            return max;
        }
        if (max == anyLowerValue()) {
            return min;
        }
        return Math.min(min, max);
    }

    // FIXME!! not done yet
    <C extends OWLObject> int getAndValue(HasOperands<C> expr) {
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
            // C_j \in C^{>= m}
            int m = getLowerBoundDirect(p);
            // C_j \in CC^{<= k}
            int k = getUpperBoundComplement(p);
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
                    return noLowerValue();
                }
                foundC = true;
                foundM = m;
                continue;
            }
            // here both k and m are values
            // count k for the
            sumK += k;
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

    <C extends OWLObject> int getOrValue(HasOperands<C> expr) {
        int max = noLowerValue();
        // we are looking for the maximal value here; ANY need to be
        // special-cased
        Iterator<C> it = expr.operands().iterator();
        while (it.hasNext()) {
            C p = it.next();
            int n = getLowerBoundDirect(p);
            if (n == anyLowerValue()) {
                return anyLowerValue();
            }
            max = Math.max(max, n);
        }
        return max;
    }

    // concept expressions
    @Override
    public void visit(OWLObjectComplementOf expr) {
        value = getLowerBoundComplement(expr.getOperand());
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
        value = getOneNoneLower(expr.individuals().count() > 0);
    }

    @Override
    public void visit(OWLObjectHasSelf expr) {
        value = getOneNoneLower(isTopEquivalent(expr.getProperty()));
    }

    // FIXME!! differ from the paper
    @Override
    public void visit(OWLObjectHasValue expr) {
        value = getOneNoneLower(isTopEquivalent(expr.getProperty()));
    }

    @Override
    public void visit(OWLDataHasValue expr) {
        value = getOneNoneLower(isTopEquivalent(expr.getProperty()));
    }

    // object role expressions
    @Override
    public void visit(OWLObjectInverseOf expr) {
        value = getLowerBoundDirect(expr.getInverseProperty());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom expr) {
        for (OWLObjectPropertyExpression p : expr.getPropertyChain()) {
            if (!isTopEquivalent(p)) {
                value = noLowerValue();
                return;
            }
        }
        value = anyLowerValue();
    }

    // negated datatype is a union of all other DTs that are infinite
    @Override
    public void visit(OWLDatatype o) {
        value = noLowerValue();
    }

    // negated restriction include negated DT
    @Override
    public void visit(OWLDatatypeRestriction o) {
        value = noLowerValue();
    }

    @Override
    public void visit(OWLLiteral o) {
        value = noLowerValue();
    }

    @Override
    public void visit(OWLDataComplementOf expr) {
        value = getLowerBoundComplement(expr.getDataRange());
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
        value = getOneNoneLower(expr.values().count() > 0);
    }
}
