package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.ProgressMonitor;
import org.semanticweb.owlapi.apibinding.OWLManager;

import java.util.concurrent.*;
import java.util.*;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Aug-2009
 */
public abstract class OWLReasonerBase {

    private OWLOntologyManager manager;

    private OWLOntology rootOntology;

    private BufferingMode bufferingMode;

    private List<OWLOntologyChange> rawChanges = new ArrayList<OWLOntologyChange>();

    private Set<OWLAxiom> reasonerAxioms;

    private OWLOntologyChangeListener ontologyChangeListener = new OWLOntologyChangeListener() {
        public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
            handleRawOntologyChanges(changes);
        }
    };

    protected OWLReasonerBase(OWLOntology rootOntology, BufferingMode bufferingMode) {
        this.rootOntology = rootOntology;
        this.bufferingMode = bufferingMode;
        OWLOntologyManager ontologyManager = rootOntology.getOWLOntologyManager();
        ontologyManager.addOntologyChangeListener(ontologyChangeListener);
        reasonerAxioms = new HashSet<OWLAxiom>();
        for (OWLOntology ont : rootOntology.getImportsClosure()) {
            reasonerAxioms.addAll(ont.getLogicalAxioms());
        }
    }

    private void handleRawOntologyChanges(List<? extends OWLOntologyChange> changes) {
        rawChanges.addAll(changes);
    }

    public List<OWLOntologyChange> getPendingChanges() {
        return new ArrayList<OWLOntologyChange>(rawChanges);
    }

    public void flush() {
        // Process the changes
        final Set<OWLAxiom> added = new HashSet<OWLAxiom>();
        final Set<OWLAxiom> removed = new HashSet<OWLAxiom>();
        computeDiff(added, removed);
        reasonerAxioms.removeAll(removed);
        reasonerAxioms.addAll(added);
        handleChanges(added, removed);
    }

    private void computeDiff(Set<OWLAxiom> added, Set<OWLAxiom> removed) {
        for (OWLOntology ont : rootOntology.getImportsClosure()) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                if (!reasonerAxioms.contains(ax)) {
                    added.add(ax);
                }
            }
        }
        for(OWLAxiom ax : reasonerAxioms) {
            if(!rootOntology.containsAxiom(ax, true)) {
                removed.add(ax);
            }
        }
    }

    protected abstract void handleChanges(Set<OWLAxiom> addAxioms, Set<OWLAxiom> removeAxioms);

    void dispose() {

    }

}
