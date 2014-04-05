/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 * @param <T>
 *        change type
 */
public abstract class OWLOntologyChange<T> implements HasSignature {

    private final OWLOntology ont;

    /**
     * @param ont
     *        the ontology to which the change is to be applied
     */
    public OWLOntologyChange(@Nonnull OWLOntology ont) {
        this.ont = checkNotNull(ont, "ontology must not be null");
    }

    /**
     * Determines if the change will cause the addition or removal of an axiom
     * from an ontology.
     * 
     * @return {@code true} if the change is an {@code OWLAddAxiomChange} or
     *         {@code OWLRemoveAxiomChange} otherwise {@code false}.
     */
    public abstract boolean isAxiomChange();

    /**
     * Determines if the change will add an axiom to an ontology.
     * 
     * @return {@code true} if the change is an AddAxiom change and it will add
     *         an axiom to an ontology, {@code false} otherwise.
     */
    public abstract boolean isAddAxiom();

    /**
     * Determines if the change will remove an axiom from an ontology.
     * 
     * @return {@code true} if the change is a RemoveAxiom change and it will
     *         remove an axiom from an ontology, {@code false} otherwise.
     */
    public boolean isRemoveAxiom() {
        return isAxiomChange() && !isAddAxiom();
    }

    /**
     * If the change is an axiom change (i.e. AddAxiom or RemoveAxiom) this
     * method obtains the axiom.
     * 
     * @return The Axiom if this change is an axiom change
     * @throws UnsupportedOperationException
     *         If the change is not an axiom change (check with the
     *         {@code isAxiomChange} method first).
     */
    @Nonnull
    public abstract OWLAxiom getAxiom();

    /**
     * Determines if this change is an import change and hence causes a change
     * to the imports closure of an ontology.
     * 
     * @return {@code true} if this change is an import change, otherwise
     *         {@code false}.
     */
    public abstract boolean isImportChange();

    /**
     * Gets the ontology that the change is/was applied to.
     * 
     * @return The ontology that the change is applicable to
     */
    @Nonnull
    public OWLOntology getOntology() {
        return ont;
    }

    /**
     * Gets the data (independent of the ontology) associated with this specific
     * change.
     * 
     * @return The {@link OWLOntologyChangeData} associated with this
     *         {@link OWLOntologyChange}.
     */
    @Nonnull
    public abstract OWLOntologyChangeData<T> getChangeData();

    /**
     * Gets a {@link OWLOntologyChangeRecord} that is derived from this
     * {@link OWLOntologyChange}'s {@link OWLOntologyID} and it's
     * {@link OWLOntologyChangeData}.
     * 
     * @return An {@link OWLOntologyChangeRecord} containing an
     *         {@link OWLOntologyID} equal to the {@link OWLOntologyID} of this
     *         {@link OWLOntologyChange}'s {@link OWLOntology}. Not {@code null}
     *         .
     */
    @Nonnull
    public OWLOntologyChangeRecord<T> getChangeRecord() {
        return new OWLOntologyChangeRecord<T>(ont.getOntologyID(),
                getChangeData());
    }

    /**
     * Gets the signature of this ontology change. That is, the set of entities
     * appearing in objects in this change.
     * 
     * @return A set of entities that correspond to the signature of this
     *         object. The set is a copy, changes are not reflected back.
     */
    @Override
    @Nonnull
    public abstract Set<OWLEntity> getSignature();

    /**
     * Accepts a visitor
     * 
     * @param visitor
     *        The visitor
     */
    public abstract void accept(OWLOntologyChangeVisitor visitor);

    /**
     * Accepts a visitor
     * 
     * @param visitor
     *        The visitor
     * @param <O>
     *        visitor return type
     * @return visitor value
     */
    public abstract <O> O accept(OWLOntologyChangeVisitorEx<O> visitor);
}
