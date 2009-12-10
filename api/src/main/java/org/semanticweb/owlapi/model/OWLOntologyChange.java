package org.semanticweb.owlapi.model;

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
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class OWLOntologyChange {

    private OWLOntology ont;

    public OWLOntologyChange(OWLOntology ont) {
        this.ont = ont;
    }


    /**
     * Determines if the change will cause the addition or
     * removal of an axiom from an ontology.
     * @return <code>true</code> if the change is an <code>OWLAddAxiomChange</code>
     * or <code>OWLRemoveAxiomChange</code> otherwise <code>false</code>.
     */
    public abstract boolean isAxiomChange();

    /**
     * If the change is an axiom change (i.e. AddAxiom or RemoveAxiom)
     * this method obtains the axiom.
     * @return The Axiom if this change is an axiom change
     * @throws UnsupportedOperationException If the change is not an axiom change (check
     * with the <code>isAxiomChange</code> method first).
     */
    public abstract OWLAxiom getAxiom();


    /**
     * Determines if this change is an import change and hence causes a change to the imports closure of an ontology.
     * @return <code>true</code> if this change is an import change, otherwise <code>false</code>.
     */
    public abstract boolean isImportChange();

    /**
     * Gets the ontology that the change is/was applied to
     * @return The ontology that the change is applicable to
     */
    public OWLOntology getOntology() {
        return ont;
    }



    public abstract void accept(OWLOntologyChangeVisitor visitor);

}
