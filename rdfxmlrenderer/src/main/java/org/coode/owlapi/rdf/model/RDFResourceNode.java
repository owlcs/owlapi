package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.IRI;
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
 */
public class RDFResourceNode extends RDFNode {

    private IRI iri;

    private int anonId;


    /**
     * Constructs a named resource (i.e. a resource with
     * a URI).
     */
    public RDFResourceNode(IRI iri) {
        this.iri = iri;
    }


    /**
     * Constructs an anonymous node, which has the specified ID.
     * @param anonId The id of the node
     */
    public RDFResourceNode(int anonId) {
        this.anonId = anonId;
    }


    public IRI getIRI() {
        return iri;
    }


    public boolean isLiteral() {
        return false;
    }


    public boolean isAnonymous() {
        return iri == null;
    }


    public int hashCode() {
        int hashCode = 17;
        hashCode = hashCode * 37 + (iri == null ? anonId : iri.hashCode());
        return hashCode;
    }


    public boolean equals(Object obj) {
        if (!(obj instanceof RDFResourceNode)) {
            return false;
        }
        RDFResourceNode other = (RDFResourceNode) obj;
        if (iri != null) {
            if (other.iri != null) {
                return other.iri.equals(iri);
            }
            else {
                return false;
            }
        }
        else {
            return other.anonId == anonId;
        }
    }


    public String toString() {
        return (iri != null ? "<" + iri.toString() + ">" : "genid" + Integer.toString(anonId));
    }
}
