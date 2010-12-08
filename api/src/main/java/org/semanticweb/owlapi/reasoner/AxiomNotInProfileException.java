package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.profiles.OWLProfile;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 22-Nov-2009
 */
public class AxiomNotInProfileException extends OWLReasonerRuntimeException {

    private OWLAxiom axiom;

    private OWLProfile profile;

    public AxiomNotInProfileException(OWLAxiom axiom, OWLProfile profile) {
        this.axiom = axiom;
        this.profile = profile;
    }

    public OWLAxiom getAxiom() {
        return axiom;
    }

    public OWLProfile getProfile() {
        return profile;
    }
}
