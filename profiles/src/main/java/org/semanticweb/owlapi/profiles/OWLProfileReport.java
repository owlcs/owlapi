package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLOntologyID;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.LinkedHashSet;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 */
public class OWLProfileReport {

    private OWLProfile profile;

    private Set<OWLProfileViolation> violations;


    public OWLProfileReport(OWLProfile profile, Set<OWLProfileViolation> violations) {
        this.profile = profile;
        this.violations = Collections.unmodifiableSet(new LinkedHashSet<OWLProfileViolation>(violations));
    }


    public OWLProfile getProfile() {
        return profile;
    }

    public boolean isInProfile() {
        return violations.isEmpty();
    }


    public Set<OWLProfileViolation> getViolations() {
        return violations;
    }


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
