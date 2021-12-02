package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Created by @ssz on 29.08.2020.
 */
class HeaderSignatureTestCase extends TestBase {

    @Test
    void testContainsAnnotationPropertyIssue928() {
        OWLAnnotationProperty seeAlso = df.getRDFSSeeAlso();
        OWLDatatype intType = df.getIntegerOWLDatatype();
        OWLDatatype stringType = OWL2Datatype.XSD_STRING.getDatatype(df);

        OWLOntology o = create(IRI.create("http://XXXX"));
        OWLLiteral intLiteral = df.getOWLLiteral("42", intType);
        OWLLiteral stringLiteral = df.getOWLLiteral("xxx", stringType);
        OWLAnnotation a1 = df.getOWLAnnotation(df.getRDFSComment(), stringLiteral);
        OWLAnnotation a2 = df.getOWLAnnotation(seeAlso, intLiteral);
        m.applyChange(new AddOntologyAnnotation(o, a1));
        m.applyChange(new AddOntologyAnnotation(o, a2));

        assertTrue(o.containsEntityInSignature(df.getRDFSComment()));
        assertTrue(o.containsEntityInSignature(seeAlso));
        assertEquals(2, o.getAnnotationPropertiesInSignature().size());

        assertTrue(o.containsEntityInSignature(intType));
        assertTrue(o.containsEntityInSignature(stringType));
    }

    @Test
    void testContainsDatatypesInHeaderIssue965() {
        String s = "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix owl:   <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "<http://xxx>    a             owl:Ontology ;  " + "rdfs:comment  \"the_comment\" . ";

        OWLOntology o = loadOntologyFromString(s, new TurtleDocumentFormat());

        Set<OWLAnnotation> header = o.getAnnotations();
        assertEquals(1, header.size());
        OWLAnnotation a = header.iterator().next();

        assertEquals(2, a.getSignature().size());
        assertEquals(1, a.getAnnotationPropertiesInSignature().size());
        assertEquals(1, a.getDatatypesInSignature().size());

        assertEquals(2, o.getSignature().size());
        assertEquals(1, o.getAnnotationPropertiesInSignature().size());
        assertEquals(1, o.getDatatypesInSignature().size());
    }
}
