package org.semanticweb.owlapi.profiles;

import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 30-Jul-2008<br><br>
 */
public class OWL2ProfileReport extends OWLProfileReport {

    private Set<OWLObjectPropertyExpression> nonSimpleRoles;

    private Set<OWLObjectPropertyExpression> simpleRoles;


    public OWL2ProfileReport(OWLProfile profile, Set<OWLProfileViolation> disallowedConstructs, Set<OWLObjectPropertyExpression> nonSimpleRoles, Set<OWLObjectPropertyExpression> simpleRoles) {
        super(profile, disallowedConstructs);
        this.nonSimpleRoles = new TreeSet<OWLObjectPropertyExpression>(nonSimpleRoles);
        this.simpleRoles = new TreeSet<OWLObjectPropertyExpression>(simpleRoles);
    }


    public Set<OWLObjectPropertyExpression> getNonSimpleRoles() {
        return nonSimpleRoles;
    }


    public Set<OWLObjectPropertyExpression> getSimpleRoles() {
        return simpleRoles;
    }


    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n[Simple properties]\n");
        for (OWLObjectPropertyExpression prop : simpleRoles) {
            sb.append("\t");
            sb.append(prop);
            sb.append("\n");
        }
        sb.append("\n[Non-simple properties]\n");
        for (OWLObjectPropertyExpression prop : nonSimpleRoles) {
            sb.append("\t");
            sb.append(prop);
            sb.append("\n");
        }
        return sb.toString();
    }
}
