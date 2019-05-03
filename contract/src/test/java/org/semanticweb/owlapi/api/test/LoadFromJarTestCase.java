package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class LoadFromJarTestCase extends TestBase {

    @Test
    public void shouldLoadWithFileName() throws OWLOntologyCreationException {
        OWLOntology loadOntology = m.loadOntology(IRI.create("jar:!/koala.owl"));
        assertEquals(70, loadOntology.getAxiomCount());
    }

    @Test
    public void shouldLoadWithRelativeFileName() throws OWLOntologyCreationException {
        OWLOntology loadOntology = m.loadOntology(IRI.create("jar:!koala.owl"));
        assertEquals(70, loadOntology.getAxiomCount());
    }
}
