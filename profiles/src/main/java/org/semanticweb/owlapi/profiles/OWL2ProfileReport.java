package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.util.Set;
import java.util.TreeSet;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 30-Jul-2008<br><br>
 */
public class OWL2ProfileReport extends OWLProfileReport {

    private Set<OWLObjectPropertyExpression> nonSimpleRoles;

    private Set<OWLObjectPropertyExpression> simpleRoles;


    public OWL2ProfileReport(OWLProfile profile, Set<OWLProfileViolation> disallowedConstructs, Set<OWLObjectPropertyExpression> nonSimpleRoles,
                             Set<OWLObjectPropertyExpression> simpleRoles) {
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


    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n[Simple properties]\n");
        for(OWLObjectPropertyExpression prop : simpleRoles) {
            sb.append("\t");
            sb.append(prop);
            sb.append("\n");
        }
        sb.append("\n[Non-simple properties]\n");
        for(OWLObjectPropertyExpression prop : nonSimpleRoles) {
            sb.append("\t");
            sb.append(prop);
            sb.append("\n");
        }
        return sb.toString();
    }
}
