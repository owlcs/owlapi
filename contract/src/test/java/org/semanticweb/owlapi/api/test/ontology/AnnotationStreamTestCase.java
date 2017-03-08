package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class AnnotationStreamTestCase extends TestBase {

    @Test
    public void shouldFindniqueProperties() {
        String ttl = "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                        + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                        + "<http://example.org/> rdf:type owl:Ontology .\n"
                        + "<http://example.org/> rdfs:label \"foo\" .\n"
                        + "<http://example.org/#bar>    rdfs:label  \"bar\" .";
        OWLOntology ontology = loadOntologyFromString(ttl, new TurtleDocumentFormat());
        assertEquals(ontology.annotationPropertiesInSignature().distinct().count(),
                        ontology.annotationPropertiesInSignature().count());
    }
}
