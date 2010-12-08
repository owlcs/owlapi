package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Mar-2007<br><br>
 */
public interface OWLOntologyManagerFactory {

    public OWLOntologyManager createOWLOntologyManager(OWLDataFactory dataFactory) throws OWLException;
}
