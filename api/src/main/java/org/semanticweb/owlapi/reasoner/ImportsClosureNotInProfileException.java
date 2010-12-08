package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.profiles.OWLProfile;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 22-Nov-2009
 * <p>
 * This exception indicates that the reasoner cannot handle the set of axioms that are in the imports closure
 * of the root ontology because the axioms fall outside of the "largest" OWL profile (i.e.
 * OWL2DL or OWL2EL or OWL2QL or OWL2RL) that the reasoner can handle.  The reasoner will indicate in this exception
 * which profile it expects the axioms to be in.
 */
public class ImportsClosureNotInProfileException extends OWLReasonerRuntimeException {

    private OWLProfile profile;

    public ImportsClosureNotInProfileException(OWLProfile profile) {
        this.profile = profile;
    }

    /**
     * Gets the profile which the reasoner can handle.
     * @return The profile.
     */
    public OWLProfile getProfile() {
        return profile;
    }
}
