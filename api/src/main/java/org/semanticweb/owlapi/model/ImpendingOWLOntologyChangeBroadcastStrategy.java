package org.semanticweb.owlapi.model;

import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 */
public interface ImpendingOWLOntologyChangeBroadcastStrategy {

    /**
     * Broadcasts the list of changes to the specified listeners.
     * @param changes The changes to be broadcast.
     * @param listener The listeners that the changes should be broadcast to
     */
    void broadcastChanges(ImpendingOWLOntologyChangeListener listener, List<? extends OWLOntologyChange> changes);

}
