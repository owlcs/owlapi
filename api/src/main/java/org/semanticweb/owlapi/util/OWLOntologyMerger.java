package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;


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

    @SuppressWarnings("unused")
    public boolean passes(OWLAxiom axiom) {
        return true;
    }
}
