package org.semanticweb.owlapi.apitest.ontology;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

class AnnotationStreamTestCase extends TestBase {

    @Test
    void shouldFindUniqueProperties() {
        OWLOntology ontology = loadOntologyFromString(TestFiles.uniquePropertiesTurtle, new TurtleDocumentFormat());
        assertEquals(ontology.annotationPropertiesInSignature().distinct().count(),
            ontology.annotationPropertiesInSignature().count());
    }
}
