package org.semanticweb.owl.inference;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;

import java.util.*;
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Mar-2007<br><br>
 */
public abstract class OWLReasonerAdapter implements OWLReasoner, OWLOntologyChangeListener {

    private OWLOntologyManager manager;

    private Set<OWLOntology> ontologies;


    protected OWLReasonerAdapter(OWLOntologyManager manager) {
        this.manager = manager;
        ontologies = new HashSet<OWLOntology>();
        manager.addOntologyChangeListener(this);
    }


    final public void dispose() {
        manager.removeOntologyChangeListener(this);
        disposeReasoner();
    }


    protected abstract void disposeReasoner();


    protected OWLOntologyManager getOWLOntologyManager() {
        return manager;
    }


    protected OWLDataFactory getOWLDataFactory() {
        return manager.getOWLDataFactory();
    }


    public void loadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException {
        this.ontologies.addAll(ontologies);
        ontologiesChanged();
    }


    public Set<OWLOntology> getLoadedOntologies() {
        return Collections.unmodifiableSet(ontologies);
    }


    public void unloadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException {
        this.ontologies.removeAll(ontologies);
        ontologiesChanged();
    }


    public void clearOntologies() throws OWLReasonerException {
        ontologies.clear();
        ontologiesCleared();
    }


    /**
     * This method will be called when the set of ontologies from
     * which axioms should be retrieved for reasoning has been cleared.
     */
    protected abstract void ontologiesCleared() throws OWLReasonerException;


    /**
     * This method will be called when the set of ontologies from which axioms
     * should be retrieved for reasoning has changed.
     */
    protected abstract void ontologiesChanged() throws OWLReasonerException;


    /**
     * A convenience method which gets the union of all logical axioms from ontologies
     * which have been loaded into this reasoner.
     */
    protected Set<OWLAxiom> getAllAxioms() {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        for (OWLOntology ont : ontologies) {
            result.addAll(ont.getLogicalAxioms());
        }
        return result;
    }


    public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        List<OWLOntologyChange> filteredChanges = null;
        // Filter the changes so that we get the changes that only apply to the ontologies that we
        // know about and the changes which aren't addition/removal of annotation axioms
        for (OWLOntologyChange change : changes) {
            if (ontologies.contains(change.getOntology())) {
                if (change.isAxiomChange()) {
                    OWLAxiomChange axiomChange = (OWLAxiomChange) change;
                    if (axiomChange.getAxiom().isLogicalAxiom()) {
                        if (filteredChanges == null) {
                            filteredChanges = new ArrayList<OWLOntologyChange>();
                        }
                        filteredChanges.add(axiomChange);
                    }
                }
            }
        }
        if (filteredChanges != null) {
            handleOntologyChanges(filteredChanges);
        }
    }


    /**
     * This method will be called when any of the loaded ontologies have had
     * logical axioms added to or removed from them.
     * @param changes The set of changes that represent changes to loaded
     *                ontologies.
     */
    protected abstract void handleOntologyChanges(List<OWLOntologyChange> changes) throws OWLException;


    /**
     * A utility method that will flatten a set of sets.
     * @param setOfSets The set of sets to be flattened.
     */
    public static <E> Set<E> flattenSetOfSets(Set<Set<E>> setOfSets) {
        Set<E> result = new HashSet<E>();
        for (Set<E> set : setOfSets) {
            result.addAll(set);
        }
        return result;
    }


    public static <O> Set<Set<O>> createSetOfSets(Collection<O> objs, Map<O, Collection<O>> equivalentsMap) {
        Set<Set<O>> result = CollectionFactory.createSet();
        for (O obj : objs) {
            Set<O> equivalentResultSet = CollectionFactory.createSet();
            Collection<O> equivalents = equivalentsMap.get(obj);
            if (equivalents != null) {
                equivalentResultSet.addAll(equivalents);
            }
            equivalentResultSet.add(obj);
            result.add(equivalentResultSet);
        }
        return result;
    }
}
