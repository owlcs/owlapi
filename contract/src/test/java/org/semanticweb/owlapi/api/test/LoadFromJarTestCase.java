package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntology;

class LoadFromJarTestCase extends TestBase {

    @Test
    void shouldLoadWithFileName() {
        OWLOntology loadOntology = loadFrom(iri("jar:!/koala.owl", ""));
        assertEquals(70, loadOntology.getAxiomCount());
    }

    @Test
    void shouldLoadWithRelativeFileName() {
        OWLOntology loadOntology = loadFrom(iri("jar:!koala.owl", ""));
        assertEquals(70, loadOntology.getAxiomCount());
    }
}
