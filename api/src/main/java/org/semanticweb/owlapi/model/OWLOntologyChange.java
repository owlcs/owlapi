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
     *         or <code>OWLRemoveAxiomChange</code> otherwise <code>false</code>.
     */
    public abstract boolean isAxiomChange();

    /**
     * If the change is an axiom change (i.e. AddAxiom or RemoveAxiom)
     * this method obtains the axiom.
     * @return The Axiom if this change is an axiom change
     * @throws UnsupportedOperationException If the change is not an axiom change (check
     *                                       with the <code>isAxiomChange</code> method first).
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
    public abstract <O> O accept(OWLOntologyChangeVisitorEx<O> visitor);

}
