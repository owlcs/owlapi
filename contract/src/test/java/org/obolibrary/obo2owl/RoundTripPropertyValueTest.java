package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class RoundTripPropertyValueTest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("property_value_test.obo", true);
    }

    @Test
    public void testRoundTripWithQualifiers() throws Exception {
        roundTripOBOFile("property_value_qualifier_test.obo", true);
    }

    @Test
    public void testRoundTripHeader() throws Exception {
        roundTripOBOFile("dc_header_test.obo", true);
    }

    @Test
    public void testPropertyValueQuotes()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String in = "Prefix(:=<http://purl.obolibrary.org/obo/test.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n\n"
            + "Ontology(<http://purl.obolibrary.org/obo/test.owl>\n"
            + "Declaration(Class(<http://purl.obolibrary.org/obo/X_1>))\n"
            + "Declaration(Class(<http://purl.obolibrary.org/obo/X_2>))\n"
            + "AnnotationAssertion(<http://purl.obolibrary.org/obo/rdfs_seeAlso> <http://purl.obolibrary.org/obo/X_1> \"xx\"^^xsd:string)\n\n"
            + "AnnotationAssertion(<http://purl.obolibrary.org/obo/rdfs_seeAlso> <http://purl.obolibrary.org/obo/X_2> \"1\"^^xsd:int)\n\n"
            + ")";
        OWLOntology o = loadOntologyFromString(in);
        StringDocumentTarget target = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o, new OBODocumentFormat(), target);
        assertEquals(
            "format-version: 1.2\nontology: test\n\n"
                + "[Term]\nid: X:1\nproperty_value: rdfs:seeAlso \"xx\" xsd:string\n\n"
                + "[Term]\nid: X:2\nproperty_value: rdfs:seeAlso \"1\" xsd:int\n\n",
            target.toString());
    }
}
