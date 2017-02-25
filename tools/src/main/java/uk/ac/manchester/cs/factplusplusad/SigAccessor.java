package uk.ac.manchester.cs.factplusplusad;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * helper class to set signature and locality class
 */
class SigAccessor {

    /**
     * signature of a module
     */
    Signature sig;

    /**
     * init c'tor
     *
     * @param s signature
     */
    SigAccessor(Signature s) {
        sig = verifyNotNull(s);
    }
    // locality flags

    /**
     * @return true iff concepts not in the signature are treated as TOPs
     */
    boolean topCLocal() {
        return sig.topCLocal();
    }

    /**
     * @return true iff concepts not in the signature are treated as BOTTOMs
     */
    boolean botCLocal() {
        return !topCLocal();
    }

    /**
     * @return true iff roles not in the signature are treated as TOPs
     */
    boolean topRLocal() {
        return sig.topRLocal();
    }

    /**
     * @return true iff roles not in the signature are treated as BOTTOMs
     */
    boolean botRLocal() {
        return !topRLocal();
    }

    // signature-based calls

    /**
     * @return the signature
     */
    Signature getSignature() {
        return sig;
    }

    /**
     * @param entity entity to check
     * @return true iff SIGnature does NOT contain given entity
     */
    boolean nc(OWLEntity entity) {
        return !sig.contains(entity);
    }
}
