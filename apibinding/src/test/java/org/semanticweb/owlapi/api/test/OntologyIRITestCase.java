package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 10-Dec-2009
 */
public class OntologyIRITestCase extends AbstractFileTestCase {

    public void testCorrectOntologyIRI() {
        OWLOntology ont = createOntology();
        OWLOntologyID id = ont.getOntologyID();
        assertEquals("http://www.test.com/right.owl", id.getOntologyIRI().toString());
    }

    @Override
	protected String getFileName() {
        return "ontologyIRI.rdf";
    }
}
