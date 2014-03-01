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
 * Copyright 2011, The University of Manchester
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
package org.semanticweb.owlapi.change;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Represents the specific non-ontology data required by an {@link RemoveAxiom}
 * change. <br>
 * Instances of this class are immutable.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 27/04/2012
 * @since 3.5
 */
public final class RemoveAxiomData extends AxiomChangeData {

    private static final long serialVersionUID = 30406L;

    /**
     * Constructs an {@link RemoveAxiomData} object which specifies the removal
     * of an axiom from "some ontology".
     * 
     * @param axiom
     *        The {@link OWLAxiom} being added. Not {@code null}.
     * @throws NullPointerException
     *         if {@code axiom} is {@code null}.
     */
    public RemoveAxiomData(OWLAxiom axiom) {
        super(axiom);
    }

    /**
     * Creates the {@link org.semanticweb.owlapi.model.RemoveAxiom} change that
     * describes an removal of an {@link org.semanticweb.owlapi.model.OWLAxiom}
     * from an {@link OWLOntology} specified by the {@code ontology} parameter.
     * 
     * @param ontology
     *        The {@link OWLOntology} that the change should apply to. Not
     *        {@code null}.
     * @return The {@link org.semanticweb.owlapi.model.RemoveAxiom} change for
     *         the {@link OWLOntology} specified by {@code ontology} and the
     *         {@link org.semanticweb.owlapi.model.OWLAxiom} associated with
     *         this {@link RemoveAxiomData} object.
     * @throws NullPointerException
     *         if {@code ontology} is {@code null}.
     */
    @Override
    public RemoveAxiom createOntologyChange(OWLOntology ontology) {
        return new RemoveAxiom(ontology, getAxiom());
    }

    @Override
    public <O, E extends Exception> O accept(
            OWLOntologyChangeDataVisitor<O, E> visitor) throws E {
        return visitor.visit(this);
    }

    @Override
    public int hashCode() {
        return "RemoveAxiomData".hashCode() + getAxiom().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RemoveAxiomData)) {
            return false;
        }
        RemoveAxiomData other = (RemoveAxiomData) obj;
        return getAxiom().equals(other.getAxiom());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RemoveAxiomData");
        sb.append("(");
        sb.append(getAxiom());
        sb.append(")");
        return sb.toString();
    }
}
