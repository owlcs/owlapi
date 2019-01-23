package org.semanticweb.owlapi6.api.test.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.TestFiles;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

public class AnnotationStreamTestCase extends TestBase {

    @Test
    public void shouldFindUniqueProperties() {
        OWLOntology ontology = loadOntologyFromString(TestFiles.uniquePropertiesTurtle, new TurtleDocumentFormat());
        assertEquals(ontology.annotationPropertiesInSignature().distinct().count(),
            ontology.annotationPropertiesInSignature().count());
    }
}
