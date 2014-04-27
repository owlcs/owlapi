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
package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Generates axioms which relate to inferred information for a specific entity.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 * @param <E>
 *        the entity type
 * @param <A>
 *        the axiom type
 */
public abstract class InferredEntityAxiomGenerator<E extends OWLEntity, A extends OWLAxiom>
        implements InferredAxiomGenerator<A> {

    @Override
    public Set<A> createAxioms(@Nonnull OWLDataFactory df,
            @Nonnull OWLReasoner reasoner) {
        Set<E> processedEntities = new HashSet<E>();
        Set<A> result = new HashSet<A>();
        for (OWLOntology ont : reasoner.getRootOntology().getImportsClosure()) {
            assert ont != null;
            for (E entity : getEntities(ont)) {
                assert entity != null;
                if (!processedEntities.contains(entity)) {
                    processedEntities.add(entity);
                    addAxioms(entity, reasoner, df, result);
                }
            }
        }
        return result;
    }

    /**
     * Adds inferred axioms to a results set. The inferred axioms are generated
     * for the specific entity.
     * 
     * @param entity
     *        The entity
     * @param reasoner
     *        The reasoner that has inferred the new axioms
     * @param dataFactory
     *        A data factory which should be used to create the new axioms
     * @param result
     *        The results set, which the new axioms should be added to.
     */
    protected abstract void addAxioms(@Nonnull E entity,
            @Nonnull OWLReasoner reasoner, @Nonnull OWLDataFactory dataFactory,
            @Nonnull Set<A> result);

    /**
     * Gets the entities from the specified ontology that this generator
     * processes
     * 
     * @param ont
     *        The ontology from which entities are to be retrieved.
     * @return A set of entities.
     */
    @Nonnull
    protected abstract Set<E> getEntities(@Nonnull OWLOntology ont);

    protected Set<E> getAllEntities(OWLReasoner reasoner) {
        Set<E> results = new HashSet<E>();
        for (OWLOntology ont : reasoner.getRootOntology().getImportsClosure()) {
            assert ont != null;
            results.addAll(getEntities(ont));
        }
        return results;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
