package org.semanticweb.owlapi6.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;

public class LoadFromJarTestCase extends TestBase {

    @Test
    public void shouldLoadWithFileName() throws OWLOntologyCreationException {
        OWLOntology loadOntology = m.loadOntology(df.getIRI("jar:!/koala.owl"));
        assertEquals(70, loadOntology.getAxiomCount());
    }

    @Test
    public void shouldLoadWithRelativeFileName() throws OWLOntologyCreationException {
        OWLOntology loadOntology = m.loadOntology(df.getIRI("jar:!koala.owl"));
        assertEquals(70, loadOntology.getAxiomCount());
    }
}
