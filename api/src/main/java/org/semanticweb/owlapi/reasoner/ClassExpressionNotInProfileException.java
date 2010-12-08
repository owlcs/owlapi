package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.profiles.OWLProfile;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 22-Nov-2009
 */
public class ClassExpressionNotInProfileException extends OWLReasonerRuntimeException {

    private OWLClassExpression classExpression;

    private OWLProfile profile;

    public ClassExpressionNotInProfileException(OWLClassExpression classExpression, OWLProfile profile) {
        this.classExpression = classExpression;
        this.profile = profile;
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }

    public OWLProfile getProfile() {
        return profile;
    }
}
