package org.semanticweb.owlapi6.factplusplusad;

import java.util.Collection;

import org.semanticweb.owlapi6.atomicdecomposition.AxiomWrapper;
import org.semanticweb.owlapi6.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi6.atomicdecomposition.Signature;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLRuntimeException;

/**
 * base class for checking locality of a DL axiom
 */
abstract class LocalityChecker extends SigAccessor
    implements org.semanticweb.owlapi6.atomicdecomposition.LocalityChecker {

    /**
     * remember the axiom locality value here
     */
    boolean isLocal = true;

    /**
     * init c'tor
     *
     * @param s signature
     */
    LocalityChecker(Signature s) {
        super(s);
    }

    /**
     * @param moduleMethod modularisation method
     * @param pSig signature
     * @return locality checker by a method
     */
    static LocalityChecker createLocalityChecker(ModuleMethod moduleMethod, Signature pSig) {
        switch (moduleMethod) {
            case SYNTACTIC_STANDARD:
                return new SyntacticLocalityChecker(pSig);
            case SYNTACTIC_COUNTING:
                return new ExtendedSyntacticLocalityChecker(pSig);
            case QUERY_ANSWERING:
                // return new SemanticLocalityChecker(pSig);
            default:
                throw new OWLRuntimeException("Unsupported module method: " + moduleMethod);
        }
    }

    @Override
    public void preprocessOntology(Collection<AxiomWrapper> axioms) {
        // nothing to do here
    }

    @Override
    public boolean local(OWLAxiom axiom) {
        axiom.accept(this);
        return isLocal;
    }

    @Override
    public void setSignatureValue(Signature sig) {
        this.sig.setSignature(sig);
    }
}
