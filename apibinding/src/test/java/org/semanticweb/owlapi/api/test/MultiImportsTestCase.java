package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class MultiImportsTestCase extends AbstractOWLAPITestCase {

    public void testImports() {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            manager.loadOntologyFromOntologyDocument(IRI.create("http://purl.obolibrary.org/obo/iao.owl"));
        }
        catch (OWLOntologyCreationException e) {
            Thread.dumpStack();
            fail(e.getMessage());
            e.printStackTrace();
        }
    }

}
