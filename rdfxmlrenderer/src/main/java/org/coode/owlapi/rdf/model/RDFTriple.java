package org.coode.owlapi.rdf.model;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 06-Dec-2006<br><br>
 *
 * Represents an RDF triple (S, P, O)
 */
public class RDFTriple {

    private RDFResourceNode subject;

    private RDFResourceNode property;

    private RDFNode object;

    private int hashCode = 0;


    public RDFTriple(RDFResourceNode subject, RDFResourceNode property, RDFNode object) {
        this.object = object;
        this.property = property;
        this.subject = subject;
    }


    public RDFResourceNode getSubject() {
        return subject;
    }


    public RDFResourceNode getProperty() {
        return property;
    }


    public RDFNode getObject() {
        return object;
    }


    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 17;
            hashCode = hashCode * 37 + subject.hashCode();
            hashCode = hashCode * 37 + property.hashCode();
            hashCode = hashCode * 37 + object.hashCode();
        }
        return hashCode;
    }


    public boolean equals(Object obj) {
        if(!(obj instanceof RDFTriple)) {
            return false;
        }
        RDFTriple other = (RDFTriple) obj;
        return other.subject.equals(subject) &&
                other.property.equals(property) &&
                other.object.equals(object);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject.toString());
        sb.append(" -> ");
        sb.append(property.toString());
        sb.append(" -> ");
        sb.append(object.toString());
        return sb.toString();
    }
}
