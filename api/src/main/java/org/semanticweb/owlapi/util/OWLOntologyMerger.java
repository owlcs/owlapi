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

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

/**
 * A very very simple merger, which just creates an ontology which contains the
 * union of axioms from a set of ontologies.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyMerger implements OWLAxiomFilter {

    private final OWLOntologySetProvider setProvider;
    private final OWLAxiomFilter axiomFilter;
    private final boolean mergeOnlyLogicalAxioms;

    /**
     * @param setProvider
     *        the ontology provider
     */
    public OWLOntologyMerger(OWLOntologySetProvider setProvider) {
        this.setProvider = checkNotNull(setProvider, "setProvider cannot be null");
        axiomFilter = this;
        mergeOnlyLogicalAxioms = false;
    }

    /**
     * @param setProvider
     *        the ontology provider
     * @param mergeOnlyLogicalAxioms
     *        true if only logical axioms should be included
     */
    public OWLOntologyMerger(OWLOntologySetProvider setProvider, boolean mergeOnlyLogicalAxioms) {
        this.setProvider = checkNotNull(setProvider, "setProvider cannot be null");
        this.mergeOnlyLogicalAxioms = mergeOnlyLogicalAxioms;
        axiomFilter = this;
    }

    /**
     * @param setProvider
     *        the ontology provider
     * @param axiomFilter
     *        the filter to use
     */
    public OWLOntologyMerger(OWLOntologySetProvider setProvider, OWLAxiomFilter axiomFilter) {
        this.setProvider = checkNotNull(setProvider, "setProvider cannot be null");
        this.axiomFilter = axiomFilter;
        mergeOnlyLogicalAxioms = false;
    }

    /**
     * @param ontologyManager
     *        the manager containing the ontologies
     * @param ontologyIRI
     *        the new ontology IRI
     * @return the new ontology
     * @throws OWLOntologyCreationException
     *         if any creation exception arises
     */
    public OWLOntology createMergedOntology(OWLOntologyManager ontologyManager, @Nullable IRI ontologyIRI)
            throws OWLOntologyCreationException {
        OWLOntology ontology;
        if (ontologyIRI != null) {
            ontology = ontologyManager.createOntology(ontologyIRI);
        } else {
            ontology = ontologyManager.createOntology();
        }
        setProvider.ontologies().flatMap(ont -> getAxioms(ont)).filter(ax -> axiomFilter.passes(ax))
                .forEach(ax -> ontology.add(ax));
        return ontology;
    }

    private Stream<? extends OWLAxiom> getAxioms(OWLOntology ont) {
        if (mergeOnlyLogicalAxioms) {
            return ont.logicalAxioms();
        } else {
            return ont.axioms();
        }
    }

    @Override
    public boolean passes(OWLAxiom axiom) {
        return true;
    }
}
