package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntology;

class LoadFromJarTestCase extends TestBase {

    @ParameterizedTest
    @ValueSource(strings = {"jar:!/koala.owl", "jar:!koala.owl"})
    void shouldLoadWithRelativeFileName(String koala) {
        OWLOntology loadOntology = loadFrom(iri(koala, ""));
        assertEquals(70, loadOntology.getAxiomCount());
    }
}
