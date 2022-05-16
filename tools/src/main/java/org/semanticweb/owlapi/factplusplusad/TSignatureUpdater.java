package org.semanticweb.owlapi.factplusplusad;

import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * update signature by adding the signature of a given axiom to it
 */
class TSignatureUpdater implements OWLAxiomVisitor {

    /**
     * helper with expressions
     */
    TExpressionSignatureUpdater updater;

    /**
     * init c'tor
     *
     * @param sig signature
     */
    TSignatureUpdater(Signature sig) {
        updater = new TExpressionSignatureUpdater(sig);
    }

    @Override
    public void doDefault(OWLObject object) {
        object.signature().forEach(v -> v.accept(updater));
    }
}
