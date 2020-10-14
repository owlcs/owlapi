package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Created by @ssz on 29.08.2020.
 */
public class HeaderSignatureTestCase extends TestBase {

    @Test
    public void testContainsAnnotationPropertyIssue928() throws OWLOntologyCreationException {
        OWLAnnotationProperty seeAlso = df.getRDFSSeeAlso();
        OWLDatatype intType = df.getIntegerOWLDatatype();
        OWLDatatype stringType = df.getStringOWLDatatype();

        OWLOntology o = m.createOntology(IRI.create("http://XXXX"));
        m.applyChange(
            new AddOntologyAnnotation(o, df.getRDFSComment(df.getOWLLiteral("xxx", stringType))));
        m.applyChange(new AddOntologyAnnotation(o,
            df.getOWLAnnotation(seeAlso, df.getOWLLiteral("42", intType))));

        assertTrue(o.containsEntityInSignature(df.getRDFSComment()));
        assertTrue(o.containsEntityInSignature(seeAlso));
        assertEquals(2, o.annotationPropertiesInSignature().count());

        assertTrue(o.containsEntityInSignature(intType));
        assertTrue(o.containsEntityInSignature(stringType));
    }

    @Test
    public void testContainsDatatypesInHeaderIssue965() throws OWLOntologyCreationException {
        String s = "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix owl:   <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "<http://xxx>    a             owl:Ontology ;  " + "rdfs:comment  \"the_comment\" . ";

        OWLOntology o = loadOntologyFromString(s, new TurtleDocumentFormat());

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
