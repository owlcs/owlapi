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
package org.semanticweb.owlapi.reasoner.impl;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public abstract class OWLReasonerBase implements OWLReasoner {

    private final OWLOntologyManager manager;
    private final OWLOntology rootOntology;
    private final BufferingMode bufferingMode;
    private final List<OWLOntologyChange> rawChanges = new ArrayList<>();
    private final Set<OWLAxiom> reasonerAxioms;
    private final long timeOut;
    private final OWLReasonerConfiguration configuration;

    protected OWLReasonerBase(OWLOntology rootOntology, OWLReasonerConfiguration configuration,
        BufferingMode bufferingMode) {
        this.rootOntology = checkNotNull(rootOntology, "rootOntology cannot be null");
        this.bufferingMode = checkNotNull(bufferingMode, "bufferingMode cannot be null");
        this.configuration = checkNotNull(configuration, "configuration cannot be null");
        timeOut = configuration.getTimeOut();
        manager = rootOntology.getOWLOntologyManager();
        manager.addOntologyChangeListener(this::handleRawOntologyChanges);
        reasonerAxioms = asUnorderedSet(rootOntology.importsClosure().flatMap(
            o -> Stream.concat(o.logicalAxioms(), o.axioms(AxiomType.DECLARATION)))
            .map(ax -> OWLAxiom.getAxiomWithoutAnnotations(ax)));
    }

    /**
     * @return the configuration
     */
    public OWLReasonerConfiguration getReasonerConfiguration() {
        return configuration;
    }

    @Override
    public BufferingMode getBufferingMode() {
        return bufferingMode;
    }

    @Override
    public long getTimeOut() {
        return timeOut;
    }

    @Override
    public OWLOntology getRootOntology() {
        return rootOntology;
    }

    /**
     * Handles raw ontology changes. If the reasoner is a buffering reasoner then the changes will
     * be stored in a buffer. If the reasoner is a non-buffering reasoner then the changes will be
     * automatically flushed through to the change filter and passed on to the reasoner.
     *
     * @param changes The list of raw changes.
     */
    protected synchronized void handleRawOntologyChanges(
        List<? extends OWLOntologyChange> changes) {
        rawChanges.addAll(changes);
        // We auto-flush the changes if the reasoner is non-buffering
        if (bufferingMode.equals(BufferingMode.NON_BUFFERING)) {
            flush();
        }
    }

    @Override
    public List<OWLOntologyChange> getPendingChanges() {
        return new ArrayList<>(rawChanges);
    }

    @Override
    public Set<OWLAxiom> getPendingAxiomAdditions() {
        Set<OWLAxiom> added = new HashSet<>();
        computeDiff(added, new HashSet<OWLAxiom>());
        return added;
    }

    @Override
    public Set<OWLAxiom> getPendingAxiomRemovals() {
        Set<OWLAxiom> removed = new HashSet<>();
        computeDiff(new HashSet<OWLAxiom>(), removed);
        return removed;
    }

    @Override
    public void flush() {
        // Process the changes
        Set<OWLAxiom> added = new HashSet<>();
        Set<OWLAxiom> removed = new HashSet<>();
        computeDiff(added, removed);
        reasonerAxioms.removeAll(removed);
        reasonerAxioms.addAll(added);
        rawChanges.clear();
        if (!added.isEmpty() || !removed.isEmpty()) {
            handleChanges(added, removed);
        }
    }

    /**
     * Computes a diff of what axioms have been added and what axioms have been removed from the
     * list of pending changes. Note that even if the list of pending changes is non-empty then
     * there may be no changes for the reasoner to deal with.
     *
     * @param added The logical axioms that have been added to the imports closure of the reasoner
     * root ontology
     * @param removed The logical axioms that have been removed from the imports closure of the
     * reasoner root ontology
     */
    private void computeDiff(Set<OWLAxiom> added, Set<OWLAxiom> removed) {
        if (rawChanges.isEmpty()) {
            return;
        }
        rootOntology.importsClosure().flatMap(o -> o.logicalAxioms())
            .filter(ax -> !reasonerAxioms.contains(ax.getAxiomWithoutAnnotations()))
            .forEach(added::add);
        rootOntology.importsClosure().flatMap(o -> o.axioms(AxiomType.DECLARATION))
            .filter(ax -> !reasonerAxioms.contains(ax.getAxiomWithoutAnnotations()))
            .forEach(added::add);
        for (OWLAxiom ax : reasonerAxioms) {
            if (!rootOntology.containsAxiom(ax, Imports.INCLUDED,
                AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS)) {
                removed.add(ax);
            }
        }
    }

    /**
     * Gets the axioms that should be currently being reasoned over.
     *
     * @return A collections of axioms (not containing duplicates) that the reasoner should be
     * taking into consideration when reasoning. This set of axioms many not correspond to the
     * current state of the imports closure of the reasoner root ontology if the reasoner is
     * buffered.
     */
    public Collection<OWLAxiom> getReasonerAxioms() {
        return new ArrayList<>(reasonerAxioms);
    }

    /**
     * Asks the reasoner implementation to handle axiom additions and removals from the imports
     * closure of the root ontology. The changes will not include annotation axiom additions and
     * removals.
     *
     * @param addAxioms The axioms to be added to the reasoner.
     * @param removeAxioms The axioms to be removed from the reasoner
     */
    protected abstract void handleChanges(Set<OWLAxiom> addAxioms, Set<OWLAxiom> removeAxioms);

    @Override
    public void dispose() {
        manager.removeOntologyChangeListener(this::handleRawOntologyChanges);
    }

    @Override
    public FreshEntityPolicy getFreshEntityPolicy() {
        return configuration.getFreshEntityPolicy();
    }

    @Override
    public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
        return configuration.getIndividualNodeSetPolicy();
    }

    /**
     * @return the data factory
     */
    public OWLDataFactory getOWLDataFactory() {
        return rootOntology.getOWLOntologyManager().getOWLDataFactory();
    }
}
