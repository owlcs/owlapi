package org.semanticweb.owlapi.api.test.anonymous;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;

@SuppressWarnings("javadoc")
public class AnonymousOntologyTestCase extends TestBase {

    @Test
    public void shouldNotFailOnAnonymousOntologySearch() throws OWLOntologyCreationException {
        m.createOntology(new OWLOntologyID());
        assertNull(m.getOntology(new OWLOntologyID()));
    }
}
