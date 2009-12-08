package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 29-Apr-2007<br><br>
 * <p/>
 * A very very simple merger, which just creates an ontology which contains the union of axioms from a set of
 * ontologies.
 */
public class OWLOntologyMerger implements OWLAxiomFilter {

    private OWLOntologySetProvider setProvider;

    private OWLAxiomFilter axiomFilter;

    private boolean mergeOnlyLogicalAxioms;


    public OWLOntologyMerger(OWLOntologySetProvider setProvider) {
        this.setProvider = setProvider;
        this.axiomFilter = this;
    }


    public OWLOntologyMerger(OWLOntologySetProvider setProvider, boolean mergeOnlyLogicalAxioms) {
        this.setProvider = setProvider;
        this.mergeOnlyLogicalAxioms = mergeOnlyLogicalAxioms;
        this.axiomFilter = this;
    }


    public OWLOntologyMerger(OWLOntologySetProvider setProvider, OWLAxiomFilter axiomFilter) {
        this.setProvider = setProvider;
        this.axiomFilter = axiomFilter;
    }


    public OWLOntology createMergedOntology(OWLOntologyManager ontologyManager, IRI ontologyIRI) throws
                                                                                                 OWLOntologyCreationException {
        OWLOntology ontology;
        if(ontologyIRI != null) {
            ontology =  ontologyManager.createOntology(ontologyIRI);
        }
        else {
             ontology = ontologyManager.createOntology();
        }
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : setProvider.getOntologies()) {
            for (OWLAxiom ax : getAxioms(ont)) {
                if (axiomFilter.passes(ax)) {
                    changes.add(new AddAxiom(ontology, ax));
                }
            }
        }
        ontologyManager.applyChanges(changes);
        return ontology;
    }


    private Set<? extends OWLAxiom> getAxioms(OWLOntology ont) {
        if (mergeOnlyLogicalAxioms) {
            return ont.getLogicalAxioms();
        }
        else {
            return ont.getAxioms();
        }
    }


    public boolean passes(OWLAxiom axiom) {
        return true;
    }
}
