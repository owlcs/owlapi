package org.semanticweb.owlapi.profiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 */
public class OWLProfileReport {

    private OWLProfile profile;

    private List<OWLProfileViolation> violations;


    public OWLProfileReport(OWLProfile profile, Set<OWLProfileViolation> violations) {
        this.profile = profile;
        this.violations = new ArrayList<OWLProfileViolation>(violations);
    }


    public OWLProfile getProfile() {
        return profile;
    }

    public boolean isInProfile() {
        return violations.isEmpty();
    }


    public List<OWLProfileViolation> getViolations() {
        return violations;
    }


    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(profile.getName());
        sb.append(" Profile Report: ");
        if(isInProfile()) {
            sb.append("[Ontology and imports closure in profile]\n");
        }
        else {
            sb.append("Ontology and imports closure NOT in profile. ");
            sb.append("The following violations are present: ");
            sb.append(":\n");
        }

        for(OWLProfileViolation na : violations) {
            sb.append(na);
            sb.append("\n");
        }
        return sb.toString();
    }
}
