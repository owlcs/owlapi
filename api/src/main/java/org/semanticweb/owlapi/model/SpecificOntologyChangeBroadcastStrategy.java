package org.semanticweb.owlapi.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Nov-2006<br><br>
 *
 * A change broadcast strategy that broadcasts changes that have been applied to a
 * specific ontology.
 */
public class SpecificOntologyChangeBroadcastStrategy implements OWLOntologyChangeBroadcastStrategy {

    private OWLOntology ontology;

    /**
     * Constructs a change broadcast strategy which only causes changes that
     * have been applied to the specific ontology to be broadcast.
     * @param ontology  The ontology.
     */
    public SpecificOntologyChangeBroadcastStrategy(OWLOntology ontology) {
        this.ontology = ontology;
    }


    public void broadcastChanges(OWLOntologyChangeListener listener, List<? extends OWLOntologyChange> changes) throws Exception {
        List<OWLOntologyChange> broadcastChanges = new ArrayList<OWLOntologyChange>();
        for(OWLOntologyChange change : changes) {
            if(change.getOntology().equals(ontology)) {
                broadcastChanges.add(change);
            }
        }
        listener.ontologiesChanged(broadcastChanges);
    }
}
