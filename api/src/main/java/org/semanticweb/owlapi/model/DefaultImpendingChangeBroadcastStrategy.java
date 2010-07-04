package org.semanticweb.owlapi.model;

import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 */
public class DefaultImpendingChangeBroadcastStrategy implements ImpendingOWLOntologyChangeBroadcastStrategy {

    public void broadcastChanges(ImpendingOWLOntologyChangeListener listener, List<? extends OWLOntologyChange> changes) {
        listener.handleImpendingOntologyChanges(changes);
    }
}
