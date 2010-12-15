package org.semanticweb.owlapi.reasoner.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Aug-2009
 */
public abstract class OWLReasonerBase implements OWLReasoner {

    private OWLOntologyManager manager;

    private OWLOntology rootOntology;

    private BufferingMode bufferingMode;

    private List<OWLOntologyChange> rawChanges = new ArrayList<OWLOntologyChange>();

    private Set<OWLAxiom> reasonerAxioms;

    private long timeOut;

    private OWLReasonerConfiguration configuration;

    private OWLOntologyChangeListener ontologyChangeListener = new OWLOntologyChangeListener() {
        public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
            handleRawOntologyChanges(changes);
        }
    };

    protected OWLReasonerBase(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
        this.rootOntology = rootOntology;
        this.bufferingMode = bufferingMode;
        this.configuration = configuration;
        this.timeOut = configuration.getTimeOut();
        manager = rootOntology.getOWLOntologyManager();
        manager.addOntologyChangeListener(ontologyChangeListener);
        reasonerAxioms = new HashSet<OWLAxiom>();
        for (OWLOntology ont : rootOntology.getImportsClosure()) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                reasonerAxioms.add(ax.getAxiomWithoutAnnotations());
            }
            for (OWLAxiom ax : ont.getAxioms(AxiomType.DECLARATION)) {
                reasonerAxioms.add(ax.getAxiomWithoutAnnotations());
            }
        }
    }

    public OWLReasonerConfiguration getReasonerConfiguration() {
        return configuration;
    }

    public BufferingMode getBufferingMode() {
        return bufferingMode;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public OWLOntology getRootOntology() {
        return rootOntology;
    }

    /**
     * Handles raw ontology changes.  If the reasoner is a buffering reasoner then the changes will be stored
     * in a buffer.  If the reasoner is a non-buffering reasoner then the changes will be automatically flushed
     * through to the change filter and passed on to the reasoner.
     * @param changes The list of raw changes.
     */
    private final boolean log=false;
    private synchronized void handleRawOntologyChanges(List<? extends OWLOntologyChange> changes) {
    	if(log) {System.out.println(Thread.currentThread().getName()+ " OWLReasonerBase.handleRawOntologyChanges() "+changes);}
        rawChanges.addAll(changes);
        // We auto-flush the changes if the reasoner is non-buffering
        if (bufferingMode.equals(BufferingMode.NON_BUFFERING)) {
            flush();
        }
    }

    public List<OWLOntologyChange> getPendingChanges() {
        return new ArrayList<OWLOntologyChange>(rawChanges);
    }

    public Set<OWLAxiom> getPendingAxiomAdditions() {
        Set<OWLAxiom> added = new HashSet<OWLAxiom>();
        computeDiff(added, new HashSet<OWLAxiom>());
        return added;
    }

    public Set<OWLAxiom> getPendingAxiomRemovals() {
        Set<OWLAxiom> removed = new HashSet<OWLAxiom>();
        computeDiff(new HashSet<OWLAxiom>(), removed);
        return removed;
    }

    /**
     * Flushes the pending changes from the pending change list.  The changes will be analysed to dermine which
     * axioms have actually been added and removed from the imports closure of the root ontology and then the
     * reasoner will be asked to handle these changes via the {@link #handleChanges(java.util.Set, java.util.Set)}
     * method.
     */
    public void flush() {
        // Process the changes
        final Set<OWLAxiom> added = new HashSet<OWLAxiom>();
        final Set<OWLAxiom> removed = new HashSet<OWLAxiom>();
        computeDiff(added, removed);
        reasonerAxioms.removeAll(removed);
        reasonerAxioms.addAll(added);
        rawChanges.clear();
        if (!added.isEmpty() || !removed.isEmpty()) {
            handleChanges(added, removed);
        }
    }


    /**
     * Computes a diff of what axioms have been added and what axioms have been removed from the list
     * of pending changes.  Note that even if the list of pending changes is non-empty then there may be
     * no changes for the reasoner to deal with.
     * @param added The logical axioms that have been added to the imports closure of the reasoner root ontology
     * @param removed The logical axioms that have been removed from the imports closure of the reasoner root
     * ontology
     */
    private void computeDiff(Set<OWLAxiom> added, Set<OWLAxiom> removed) {
        if (rawChanges.isEmpty()) {
            return;
        }
        for (OWLOntology ont : rootOntology.getImportsClosure()) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                if (!reasonerAxioms.contains(ax.getAxiomWithoutAnnotations())) {
                    added.add(ax);
                }
            }
            for (OWLAxiom ax : ont.getAxioms(AxiomType.DECLARATION)) {
                if (!reasonerAxioms.contains(ax.getAxiomWithoutAnnotations())) {
                    added.add(ax);
                }
            }
        }
        for (OWLAxiom ax : reasonerAxioms) {
            if (!rootOntology.containsAxiomIgnoreAnnotations(ax, true)) {
                removed.add(ax);
            }
        }
    }

    /**
     * Gets the axioms that should be currently being reasoned over.
     * @return A collections of axioms (not containing duplicates) that the reasoner should be taking into consideration
     *         when reasoning.  This set of axioms many not correspond to the current state of the imports closure of the
     *         reasoner root ontology if the reasoner is buffered.
     */
    public Collection<OWLAxiom> getReasonerAxioms() {
        return new ArrayList<OWLAxiom>(reasonerAxioms);
    }

    /**
     * Asks the reasoner implementation to handle axiom additions and removals from the imports closure of the root
     * ontology.  The changes will not include annotation axiom additions and removals.
     * @param addAxioms The axioms to be added to the reasoner.
     * @param removeAxioms The axioms to be removed from the reasoner
     */
    protected abstract void handleChanges(Set<OWLAxiom> addAxioms, Set<OWLAxiom> removeAxioms);

    public void dispose() {
        manager.removeOntologyChangeListener(ontologyChangeListener);
    }

    public FreshEntityPolicy getFreshEntityPolicy() {
        return configuration.getFreshEntityPolicy();
    }

    public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
        return configuration.getIndividualNodeSetPolicy();
    }

    public OWLDataFactory getOWLDataFactory() {
        return rootOntology.getOWLOntologyManager().getOWLDataFactory();
    }
}
