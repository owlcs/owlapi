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

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * A convenience object that generates the changes which are necessary to remove
 * an entity from a set of ontologies. This is accomplished by removing all
 * axioms that refer to the entity. The entity remover follows the visitor
 * design pattern, entities that need to be removed from an ontology should
 * accept visits from the entity remover. Changes are accumulated as the entity
 * remover visits various entities.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEntityRemover implements OWLEntityVisitor {

    private final List<RemoveAxiom> changes = new ArrayList<>();
    @Nonnull
    private final Collection<OWLOntology> ontologies;

    /**
     * Creates an entity remover, which will remove entities (axioms referring
     * to the entities from the specified ontologies).
     * 
     * @param ontologies
     *        The set of ontologies that contain references to axioms to be
     *        removed.
     */
    public OWLEntityRemover(@Nonnull Set<OWLOntology> ontologies) {
        this.ontologies = new ArrayList<>(checkNotNull(ontologies,
                "ontologies cannot be null"));
    }

    /**
     * @return the list of ontology changes that are required in order to remove
     *         visited entities from the set of ontologies.
     */
    @Nonnull
    public List<RemoveAxiom> getChanges() {
        return new ArrayList<>(changes);
    }

    /**
     * Clears any changes which have accumulated over the course of visiting
     * different entities.
     */
    public void reset() {
        changes.clear();
    }

    private void generateChanges(@Nonnull OWLEntity entity) {
        checkNotNull(entity, "entity cannot be null");
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            for (OWLAxiom ax : ont.getReferencingAxioms(entity, EXCLUDED)) {
                assert ax != null;
                changes.add(new RemoveAxiom(ont, ax));
            }
            for (OWLAnnotationAssertionAxiom ax : ont
                    .getAnnotationAssertionAxioms(entity.getIRI())) {
                assert ax != null;
                changes.add(new RemoveAxiom(ont, ax));
            }
        }
    }

    @Override
    public void visit(OWLClass cls) {
        generateChanges(cls);
    }

    @Override
    public void visit(OWLDatatype datatype) {
        generateChanges(datatype);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        generateChanges(individual);
    }

    @Override
    public void visit(OWLDataProperty property) {
        generateChanges(property);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        generateChanges(property);
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        generateChanges(property);
    }
}
