package org.semanticweb.owlapi.model;

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Nov-2006<br><br>
 * </p>
 * A change broadcast strategy that simple broadcasts all changes made to all
 * ontologies.
 */
public class DefaultChangeBroadcastStrategy implements OWLOntologyChangeBroadcastStrategy {

    public void broadcastChanges(OWLOntologyChangeListener listener, List<? extends OWLOntologyChange> changes) throws Exception {
        // Just broadcast all changes
        listener.ontologiesChanged(changes);
    }
}
