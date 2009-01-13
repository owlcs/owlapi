package org.semanticweb.owl.profiles;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
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

    private URI ontologyURI;

    private Set<ConstructNotAllowed> disallowedConstructs;


    public OWLProfileReport(OWLProfile profile, URI ontologyURI, Set<ConstructNotAllowed> disallowedConstructs) {
        this.profile = profile;
        this.ontologyURI = ontologyURI;
        this.disallowedConstructs = new HashSet<ConstructNotAllowed>(disallowedConstructs);
    }


    public OWLProfile getProfile() {
        return profile;
    }


    public URI getOntologyURI() {
        return ontologyURI;
    }


    public boolean isInProfile() {
        return disallowedConstructs.isEmpty();
    }


    public Set<ConstructNotAllowed> getDisallowedConstructs() {
        return disallowedConstructs;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(profile.getName());
        sb.append(" Profile Report: ");
        sb.append(ontologyURI);
        sb.append("\n");
        if(isInProfile()) {
            sb.append("[Ontology and imports closure in profile]\n");
        }
        else {
            sb.append("Ontology and imports closure NOT in profile. ");
            sb.append("The following are not allowed in ");
            sb.append(getProfile().getName());
            sb.append(":\n");

        }

        for(ConstructNotAllowed na : disallowedConstructs) {
            sb.append(na);
            sb.append("\n");
        }
        return sb.toString();
    }
}
