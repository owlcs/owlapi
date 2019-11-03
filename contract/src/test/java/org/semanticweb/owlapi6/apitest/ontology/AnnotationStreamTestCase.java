package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
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
