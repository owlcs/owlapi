package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
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

    @Test
    public void shouldRoundTripVersionInfo()
        throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
        String in = "Prefix(:=<http://purl.obolibrary.org/obo/myont.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n\n"
            + "Ontology(<http://purl.obolibrary.org/obo/myont.owl>\n"
            + "Annotation(<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion> \"1.2\")\n"
            + "Annotation(owl:versionInfo \"2020-06-30\")\n"
            + "Declaration(AnnotationProperty(<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion>))\n"
            + "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion> \"has_obo_format_version\")\n)";

        OWLOntology o = loadOntologyFromString(in, new FunctionalSyntaxDocumentFormat());
        StringDocumentTarget saved = saveOntology(o, new OBODocumentFormat());
        OWLOntology o1 = loadOntologyFromString(saved, new OBODocumentFormat());
        equal(o, o1);

        OBODoc oboDoc1 = convert(o);
        // write OBO
        String expected = "format-version: 1.2\n" + "ontology: myont\n"
            + "property_value: owl:versionInfo \"2020-06-30\" xsd:string";
        String actual = renderOboToString(oboDoc1).trim();
        assertEquals(expected, actual);
        // parse OBO
        OBOFormatParser p = new OBOFormatParser();
        OBODoc oboDoc2 = p.parse(new BufferedReader(new StringReader(actual)));
        assertEquals(expected, renderOboToString(oboDoc2).trim());

        List<Diff> diffs = OBODocDiffer.getDiffs(oboDoc1, oboDoc2);
        assertEquals(diffs.toString(), 0, diffs.size());
    }
}
