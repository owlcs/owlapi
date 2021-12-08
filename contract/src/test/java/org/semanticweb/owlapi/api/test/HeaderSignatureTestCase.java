package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Created by @ssz on 29.08.2020.
 */
class HeaderSignatureTestCase extends TestBase {

    @Test
    void testContainsAnnotationPropertyIssue928() {
        OWLOntology o = create(iri("http://XXXX", ""));
        o.applyChange(new AddOntologyAnnotation(o, RDFSComment("xxx")));
        o.applyChange(new AddOntologyAnnotation(o, Annotation(seeAlso, Literal(42))));

        assertTrue(o.containsEntityInSignature(RDFSComment()));
        assertTrue(o.containsEntityInSignature(seeAlso));
        assertEquals(2, o.annotationPropertiesInSignature().count());

        assertTrue(o.containsEntityInSignature(intType));
        assertTrue(o.containsEntityInSignature(stringType));
    }

    @Test
    void testContainsDatatypesInHeaderIssue965() {

        OWLOntology o = loadFrom(TestFiles.ContainsDatatypesInHeader, new TurtleDocumentFormat());

        List<OWLAnnotation> header = o.annotations().collect(Collectors.toList());
        assertEquals(1, header.size());
        OWLAnnotation a = header.get(0);

        assertEquals(2, a.signature().count());
        assertEquals(1, a.annotationPropertiesInSignature().count());
        assertEquals(1, a.datatypesInSignature().count());

        assertEquals(2, o.signature().count());
        assertEquals(1, o.annotationPropertiesInSignature().count());
        assertEquals(1, o.datatypesInSignature().count());
    }
}
