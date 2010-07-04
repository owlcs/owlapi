package org.semanticweb.owlapi.model;

import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 */
public interface ImpendingOWLOntologyChangeListener {

    /**
     * Called when a list of ontology changes is about to be applied.  Note that not all of the
     * changes in the list may be applied.  This is due to the fact that change objects that would
     * not cause any change to the associated ontology are typically not enacted.
     * @param impendingChanges A list of ontology changes that will be applied to an ontology.
     * Note that the list of changes represents the requested changes.  Not all change might
     * be applied.
     * @throws OWLOntologyChangeVetoException The listener may throw a change veto exception, which will prevent
     * all of the changes being applied.
     */
    void handleImpendingOntologyChanges(List<? extends OWLOntologyChange> impendingChanges) throws OWLOntologyChangeVetoException;

}
