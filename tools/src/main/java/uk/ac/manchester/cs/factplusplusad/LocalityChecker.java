package uk.ac.manchester.cs.factplusplusad;

import java.util.Collection;

import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;

/** base class for checking locality of a DL axiom */
class LocalityChecker extends SigAccessor implements OWLAxiomVisitor {

    /** remember the axiom locality value here */
    boolean isLocal = true;

    /** init c'tor */
    LocalityChecker(Signature s) {
        super(s);
    }

    public void preprocessOntology(Collection<AxiomWrapper> axioms) {
        // nothing to do here
    }

    /** @return true iff an AXIOM is local wrt signature */
    boolean local(OWLAxiom axiom) {
        axiom.accept(this);
        return isLocal;
    }

    /**
     * set a new value of a signature (without changing a locality parameters)
     */
    void setSignatureValue(Signature sig) {
        this.sig.setSignature(sig);
    }

    /** get locality checker by a method */
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
}
