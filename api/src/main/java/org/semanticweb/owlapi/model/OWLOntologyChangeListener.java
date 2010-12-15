package org.semanticweb.owlapi.model;

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Nov-2006<br><br>
 */
public interface OWLOntologyChangeListener {

    /**
     * Called when some changes have been applied to various ontologies.  These
     * may be an axiom added or an axiom removed changes.
     * @param changes A list of changes that have occurred.  Each change may be examined
     * to determine which ontology it was applied to.
     * @throws OWLException
     */
    void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException;
}
