package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

public class AnnotationStreamTestCase extends TestBase {

    @Test
    public void shouldFindUniqueProperties() {
        OWLOntology ontology = loadOntologyFromString(TestFiles.uniquePropertiesTurtle, new TurtleDocumentFormat());
        assertEquals(ontology.annotationPropertiesInSignature().distinct().count(),
            ontology.annotationPropertiesInSignature().count());
    }
}
