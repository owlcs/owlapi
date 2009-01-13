package org.semanticweb.owl.model;

import java.net.URI;
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
 * Date: 12-Dec-2006<br><br>
 *
 * A change which sets the URI of an ontology.
 */
public class SetOntologyURI extends OWLOntologyChange {

    private URI originalURI;

    private URI newURI;

    /**
     * Creates a set ontology URI change, which will set the URI of the
     * ontology to the specified new URI.
     * @param ont The ontology whose URI is to be changed
     * @param newURI The new ontology URI
     */
    public SetOntologyURI(OWLOntology ont, URI newURI) {
        super(ont);
        this.originalURI = ont.getURI();
        this.newURI = newURI;
    }


    public int hashCode() {
        return 57 + getOriginalURI().hashCode() * 13 + getNewURI().hashCode() * 3;
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof SetOntologyURI)) {
            return false;
        }
        SetOntologyURI change = (SetOntologyURI) obj;
        return change.getOriginalURI().equals(this.getOriginalURI()) && change.getNewURI().equals(this.getNewURI());
    }


    public boolean isAxiomChange() {
        return false;
    }


    /**
     * If the change is an axiom change (i.e. AddAxiom or RemoveAxiom)
     * this method obtains the axiom.
     * @return The Axiom if this change is an axiom change
     * @throws UnsupportedOperationException
     *          If the change is not an axiom change (check
     *          with the <code>isAxiomChange</code> method first).
     */
    public OWLAxiom getAxiom() {
        throw new UnsupportedOperationException("Not an axiom change");
    }


    /**
     * Gets the original URI - i.e. the URI of the ontology before the
     * change was applied.
     */
    public URI getOriginalURI() {
        return originalURI;
    }


    /**
     * Gets the new URI - i.e. the URI of the ontology after the change
     * was applied.
     */
    public URI getNewURI() {
        return newURI;
    }


    public void accept(OWLOntologyChangeVisitor visitor) {
        visitor.visit(this);
    }
}
