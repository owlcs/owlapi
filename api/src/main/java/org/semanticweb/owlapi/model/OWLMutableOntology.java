package org.semanticweb.owlapi.model;

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public interface OWLMutableOntology extends OWLOntology {

    public List<OWLOntologyChange> applyChange(OWLOntologyChange change) throws OWLOntologyChangeException;

    public List<OWLOntologyChange> applyChanges(List<OWLOntologyChange> changes) throws OWLOntologyChangeException;
}
