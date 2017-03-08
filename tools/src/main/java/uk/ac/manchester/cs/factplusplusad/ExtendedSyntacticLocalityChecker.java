package uk.ac.manchester.cs.factplusplusad;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * syntactic locality checker for DL axioms
 */
class ExtendedSyntacticLocalityChecker extends SyntacticLocalityChecker {

    UpperBoundDirectEvaluator ubd;
    LowerBoundDirectEvaluator lbd;
    UpperBoundComplementEvaluator ubc;
    LowerBoundComplementEvaluator lbc;

    /**
     * init c'tor
     *
     * @param s signature
     */
    ExtendedSyntacticLocalityChecker(Signature s) {
        super(s);
        ubd = new UpperBoundDirectEvaluator(s);
        lbd = new LowerBoundDirectEvaluator(s);
        ubc = new UpperBoundComplementEvaluator(s);
        lbc = new LowerBoundComplementEvaluator(s);
        ubd.setEvaluators(ubd, lbd, ubc, lbc);
        lbd.setEvaluators(ubd, lbd, ubc, lbc);
        ubc.setEvaluators(ubd, lbd, ubc, lbc);
        lbc.setEvaluators(ubd, lbd, ubc, lbc);
    }

    /**
     * @return true iff EXPR is top equivalent
     */
    @Override
    public boolean isTopEquivalent(OWLObject expr) {
        return ubc.getUpperBoundComplement(expr) == 0;
    }

    /**
     * @return true iff EXPR is bottom equivalent
     */
    @Override
    public boolean isBotEquivalent(OWLObject expr) {
        return ubd.getUpperBoundDirect(expr) == 0;
    }
}
