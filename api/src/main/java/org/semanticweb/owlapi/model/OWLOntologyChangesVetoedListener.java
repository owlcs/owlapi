package org.semanticweb.owlapi.model;

import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 */
public interface OWLOntologyChangesVetoedListener {

    /**
     * Called when a list of ontology changes has been vetoed for some reason.
     * @param changes The changes that were vetoed.
     * @param veto The cause of the veto.
     */
    void ontologyChangesVetoed(List<? extends OWLOntologyChange> changes, OWLOntologyChangeVetoException veto);

}
