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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Renames entities that have a particular IRI. Entities with the specified IRI
 * are renamed regardless of whether they are classes, object properties, data
 * properties, individuals or data types.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEntityRenamer {

    private final OWLDataFactory df;
    private final Set<OWLOntology> ontologies;

    /**
     * @param owlOntologyManager
     *        the ontology manager to use
     * @param ontologies
     *        the ontologies to use
     */
    public OWLEntityRenamer(@Nonnull OWLOntologyManager owlOntologyManager,
            @Nonnull Set<OWLOntology> ontologies) {
        df = owlOntologyManager.getOWLDataFactory();
        this.ontologies = checkNotNull(ontologies, "ontologies cannot be null");
    }

    /**
     * Changes a IRI for another IRI. This creates the appropriate changes to be
     * applied in order to change a IRI.
     * 
     * @param iri
     *        The IRI to be changed
     * @param newIRI
     *        The IRI that the IRI should be changed to.
     * @return A list of ontology changes that should be applied to change the
     *         specified IRI.
     */
    @Nonnull
    public List<OWLOntologyChange> changeIRI(@Nonnull IRI iri,
            @Nonnull IRI newIRI) {
        checkNotNull(iri, "iri cannot be null");
        checkNotNull(newIRI, "newIRI cannot be null");
        Map<IRI, IRI> uriMap = new HashMap<>();
        uriMap.put(iri, newIRI);
        List<OWLOntologyChange> changes = new ArrayList<>();
        OWLObjectDuplicator dup = new OWLObjectDuplicator(df, uriMap);
        ontologies.forEach(o -> fillListWithTransformChanges(changes,
                o.referencingAxioms(iri), o, dup));
        return changes;
    }

    /**
     * Changes the IRI of an entity for another IRI.
     * 
     * @param entity
     *        The entity whose IRI is to be changed.
     * @param newIRI
     *        The new IRI
     * @return A list of ontology changes that should be applied to change the
     *         specified entity IRI.
     */
    @Nonnull
    public List<OWLOntologyChange> changeIRI(@Nonnull OWLEntity entity,
            @Nonnull IRI newIRI) {
        Map<OWLEntity, IRI> iriMap = new HashMap<>();
        iriMap.put(entity, newIRI);
        List<OWLOntologyChange> changes = new ArrayList<>();
        OWLObjectDuplicator duplicator = new OWLObjectDuplicator(iriMap, df);
        ontologies.forEach(o -> fillListWithTransformChanges(changes,
                getAxioms(o, entity), o, duplicator));
        return changes;
    }

    /**
     * @param entity2IRIMap
     *        map of IRIs to rename
     * @return list of changes
     */
    public List<OWLOntologyChange> changeIRI(
            @Nonnull Map<OWLEntity, IRI> entity2IRIMap) {
        List<OWLOntologyChange> changes = new ArrayList<>();
        OWLObjectDuplicator duplicator = new OWLObjectDuplicator(entity2IRIMap,
                df);
        for (OWLOntology ont : ontologies) {
            entity2IRIMap.keySet().forEach(
                    e -> fillListWithTransformChanges(changes,
                            getAxioms(ont, e), ont, duplicator));
        }
        return changes;
    }

    private static Stream<OWLAxiom> getAxioms(@Nonnull OWLOntology ont,
            @Nonnull OWLEntity entity) {
        return Stream.of(ont.referencingAxioms(entity),
                ont.declarationAxioms(entity),
                ont.annotationAssertionAxioms(entity.getIRI())).flatMap(x -> x);
    }

    /**
     * Fills a list with ontology changes which will replace a set of axioms
     * with duplicated/transformed axioms.
     * 
     * @param changes
     *        A list that will be filled with ontology changes which will remove
     *        the specified axioms from the specified ontology, and add the
     *        duplicated/transformed version
     * @param axioms
     *        The axioms to be duplicated/transformed
     * @param ont
     *        The ontology to which the changed should be applied
     * @param duplicator
     *        The duplicator that will do the duplicating
     */
    private static void fillListWithTransformChanges(
            List<OWLOntologyChange> changes, Stream<OWLAxiom> axioms,
            @Nonnull OWLOntology ont, OWLObjectDuplicator duplicator) {
        axioms.forEach(ax -> {
            changes.add(new RemoveAxiom(ont, ax));
            OWLAxiom dupAx = duplicator.duplicateObject(ax);
            changes.add(new AddAxiom(ont, dupAx));
        });
    }
}
