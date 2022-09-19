package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class LoadFromJarTestCase extends TestBase {

    @Test
    void shouldLoadWithFileName() throws OWLOntologyCreationException {
        OWLOntology loadOntology = m.loadOntology(df.getIRI("jar:!/koala.owl"));
        assertEquals(70, loadOntology.getAxiomCount());
    }

    @Test
    void shouldLoadWithRelativeFileName() throws OWLOntologyCreationException {
        OWLOntology loadOntology = m.loadOntology(df.getIRI("jar:!koala.owl"));
        assertEquals(70, loadOntology.getAxiomCount());
    }
}
