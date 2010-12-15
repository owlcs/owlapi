package org.semanticweb.owlapi;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Jul-2007<br><br>
 * A composite ontology change encapsulates a list of
 * ontology changes, which should be applied as a logical
 * unit.
 */
public interface OWLCompositeOntologyChange {

    /**
     * Gets the changes which compose this composite change.  Once this method
     * has been invoked, it will <i>always<i> return the same list of changes.
     * @return A list of ontology changes.
     */
    List<OWLOntologyChange> getChanges();
}
