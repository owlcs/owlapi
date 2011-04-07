/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Apr-2009
 */
public class SetOntologyID extends OWLOntologyChange {


    private OWLOntologyID ontologyID;

    private OWLOntologyID newOntologyID;

    /**
     * Creates a set ontology URI change, which will set the URI of the
     * ontology to the specified new URI.
     *
     * @param ont    The ontology whose URI is to be changed
     * @param ontologyID The ontology ID
     */
    public SetOntologyID(OWLOntology ont, OWLOntologyID ontologyID) {
        super(ont);
        this.ontologyID = ont.getOntologyID();
        this.newOntologyID = ontologyID;
    }


    /**
     * Determines if this change is an import change
     * @return <code>true</code> if this change is an import change, otherwise <code>false</code>.
     */
    @Override
	public boolean isImportChange() {
        return false;
    }


    @Override
	public int hashCode() {
        return 57 + ontologyID.hashCode() + newOntologyID.hashCode() * 3;
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SetOntologyID)) {
            return false;
        }
        SetOntologyID change = (SetOntologyID) obj;
        return change.getOriginalOntologyID().equals(ontologyID) && change.getNewOntologyID().equals(this.getNewOntologyID());
    }


    @Override
	public boolean isAxiomChange() {
        return false;
    }


    /**
     * If the change is an axiom change (i.e. AddAxiom or RemoveAxiom)
     * this method obtains the axiom.
     *
     * @return The Axiom if this change is an axiom change
     * @throws UnsupportedOperationException If the change is not an axiom change (check
     *                                       with the <code>isAxiomChange</code> method first).
     */
    @Override
	public OWLAxiom getAxiom() {
        throw new UnsupportedOperationException("Not an axiom change");
    }


    /**
     * Gets the original ID of the ontology whose URI was changed
     *
     * @return The original ID
     */
    public OWLOntologyID getOriginalOntologyID() {
        return ontologyID;
    }

    /**
     * Gets the new URI - i.e. the URI of the ontology after the change
     * was applied.
     */
    public OWLOntologyID getNewOntologyID() {
        return newOntologyID;
    }


    @Override
	public void accept(OWLOntologyChangeVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public <O> O accept(OWLOntologyChangeVisitorEx<O> visitor) {
    	return visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SET ONTOLOGY ID: ");
        sb.append(getNewOntologyID().toString());
        return sb.toString();
    }
}
