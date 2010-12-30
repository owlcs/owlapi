package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyRenameException;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 22-Dec-2009
 */
public class RenameToExistingOntologyTestCase extends AbstractOWLAPITestCase {

    public void testRenameToExistingOntology() throws Exception {
        try {
            OWLOntologyManager manager = getManager();
            IRI ontologyAIRI = IRI.create("http://www.semanticweb.org/ontologies/ontologyA");
            manager.createOntology(ontologyAIRI);
            IRI ontologyBIRI = IRI.create("http://www.semanticweb.org/ontologies/ontologyB");
            OWLOntology ontologyB = manager.createOntology(ontologyBIRI);
            manager.applyChange(new SetOntologyID(ontologyB, new OWLOntologyID(ontologyAIRI)));
            fail();
        }
        catch (OWLOntologyRenameException e) {
            System.out.println("Got expected rename exception: " + e.getMessage());
        }
    }

}
