package org.semanticweb.owlapi.api.test.anonymous;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author ignazio
 */
public class AnonymousOntologyTestCase extends TestBase {

    @Test
    public void shouldNotFailOnAnonymousOntologySearch()
            throws OWLOntologyCreationException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        man.createOntology(new OWLOntologyID());
        man.getOntology(new OWLOntologyID());
    }
}
