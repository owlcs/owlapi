package org.semanticweb.owlapi.model;

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Nov-2006<br><br>
 */
public interface OWLOntologyChangeBroadcastStrategy {

    /**
     * Broadcasts the list of changes to the specified listeners.
     * @param changes The changes to be broadcast.
     * @param listener The listeners that the changes should be broadcast to
     * @throws OWLException
     */
    void broadcastChanges(OWLOntologyChangeListener listener, List<? extends OWLOntologyChange> changes) throws Exception;

}
