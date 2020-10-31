package org.semanticweb.owlapi.api.test.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;

public class IRITestCase extends TestBase {
    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceManchesterSyntax() {
        OWLDocumentFormat f = new ManchesterSyntaxDocumentFormat();
        String bad = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
            + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
            + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "Prefix: : <http://x.org>\n" + "Ontology: <http://x.org>\n"
            + "AnnotationProperty: < https://example.org/bad-url>";
        roundTrip(f, bad);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceTriG() {
        OWLDocumentFormat f = new TrigDocumentFormat();
        String bad =
            "@prefix : <http://x.org#> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "\n" + "{\n"
                + "  <http://x.org> a owl:Ontology .\n"
                + "  <http://x.org/myprop> a owl:AnnotationProperty .\n"
                + "  <http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .\n"
                + "}";
        loadOntologyFromString(bad, f);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceRDFJSON() {
        OWLDocumentFormat f = new RDFJsonDocumentFormat();
        String bad = "{\n" + "  \"http://x.org\" : {\n"
            + "    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" : [\n" + "      {\n"
            + "        \"value\" : \"http://www.w3.org/2002/07/owl#Ontology\",\n"
            + "        \"type\" : \"uri\"\n" + "      }\n" + "    ]\n" + "  },\n"
            + "  \"http://x.org/myobj\" : {\n" + "    \"http://x.org/myprop\" : [\n" + "      {\n"
            + "        \"value\" : \" https://example.org/bad-url\",\n"
            + "        \"type\" : \"uri\"\n" + "      }\n" + "    ]\n" + "  },\n"
            + "  \"http://x.org/myprop\" : {\n"
            + "    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" : [\n" + "      {\n"
            + "        \"value\" : \"http://www.w3.org/2002/07/owl#AnnotationProperty\",\n"
            + "        \"type\" : \"uri\"\n" + "      }\n" + "    ]\n" + "  }\n" + "}";
        roundTrip(f, bad);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceNTriples() {
        OWLDocumentFormat f = new NTriplesDocumentFormat();
        String bad =
            "<http://x.org> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Ontology> .\n"
                + "<http://x.org/myprop> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#AnnotationProperty> .\n"
                + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
        roundTrip(f, bad);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceRioRDFXML() {
        OWLDocumentFormat f = new RioRDFXMLDocumentFormat();

        String bad = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<rdf:RDF\n"
            + "    xmlns=\"http://x.org#\"\n" + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "    xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
            + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "<rdf:Description rdf:about=\"http://x.org\">\n"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>\n"
            + "</rdf:Description>\n" + "<rdf:Description rdf:about=\"http://x.org/myprop\">\n"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AnnotationProperty\"/>\n"
            + "</rdf:Description>\n" + "<rdf:Description rdf:about=\"http://x.org/myobj\">\n"
            + "    <myprop xmlns=\"http://x.org/\" rdf:resource=\" https://example.org/bad-url\"/>\n"
            + "</rdf:Description>\n" + "</rdf:RDF>";
        loadOntologyFromString(bad, f);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceRioTurtle() {
        OWLDocumentFormat f = new RioTurtleDocumentFormat();

        String bad =
            "@prefix : <http://x.org#> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "<http://x.org> a owl:Ontology .\n"
                + "<http://x.org/myprop> a owl:AnnotationProperty .\n"
                + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
        loadOntologyFromString(bad, f);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceJSONLD() {
        OWLDocumentFormat f = new RDFJsonLDDocumentFormat();

        String bad = "[ {\n" + "  \"@id\" : \"http://x.org\",\n"
            + "  \"@type\" : [ \"http://www.w3.org/2002/07/owl#Ontology\" ]\n" + "}, {\n"
            + "  \"@id\" : \"http://x.org/myobj\",\n" + "  \"http://x.org/myprop\" : [ {\n"
            + "    \"@id\" : \" https://example.org/bad-url\"\n" + "  } ]\n" + "}, {\n"
            + "  \"@id\" : \"http://x.org/myprop\",\n"
            + "  \"@type\" : [ \"http://www.w3.org/2002/07/owl#AnnotationProperty\" ]\n" + "} ]";
        roundTrip(f, bad);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceNQUADS() {
        OWLDocumentFormat f = new NQuadsDocumentFormat();
        String bad =
            "<http://x.org> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Ontology> .\n"
                + "<http://x.org/myprop> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#AnnotationProperty> .\n"
                + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
        roundTrip(f, bad);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceOWLXML() {
        OWLDocumentFormat f = new OWLXMLDocumentFormat();
        String bad = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xml:base=\"http://x.org\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "     ontologyIRI=\"http://x.org\">\n"
            + "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n"
            + "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n"
            + "    <Prefix name=\"xml\" IRI=\"http://www.w3.org/XML/1998/namespace\"/>\n"
            + "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n"
            + "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n"
            + "    <Declaration>\n" + "        <AnnotationProperty IRI=\"/myprop\"/>\n"
            + "    </Declaration>\n" + "    <AnnotationAssertion>\n"
            + "        <AnnotationProperty IRI=\"/myprop\"/>\n" + "        <IRI>/myobj</IRI>\n"
            + "        <IRI> https://example.org/bad-url</IRI>\n" + "    </AnnotationAssertion>\n"
            + "</Ontology>";
        roundTrip(f, bad);
    }

    @Test(expected = OWLRuntimeException.class)
    public void shouldParseIRIAndSkipPrefixedSpaceRDFXML() {
        OWLDocumentFormat f = new RDFXMLDocumentFormat();
        String bad =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<rdf:RDF xmlns=\"http://x.org#\"\n"
                + "     xml:base=\"http://x.org\"\n" + "     xmlns:x=\"http://x.org/\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
                + "    <owl:Ontology rdf:about=\"http://x.org\"/>\n"
                + "    <owl:AnnotationProperty rdf:about=\"http://x.org/myprop\"/>\n"
                + "    <rdf:Description rdf:about=\"http://x.org/myobj\">\n"
                + "        <x:myprop rdf:resource=\" https://example.org/bad-url\"/>\n"
                + "    </rdf:Description>\n" + "</rdf:RDF>";
        // exception thrown by the parser
        loadOntologyFromString(bad, f);
    }

    @Test(expected = OWLRuntimeException.class)
    public void shouldParseIRIAndSkipPrefixedSpaceTurtle() {
        OWLDocumentFormat f = new TurtleDocumentFormat();
        String bad =
            "@prefix : <http://x.org#> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@base <http://x.org#> .\n" + "<http://x.org> rdf:type owl:Ontology .\n"
                + "<http://x.org/myprop> rdf:type owl:AnnotationProperty .\n"
                + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
        // exception thrown by the parser
        loadOntologyFromString(bad, f);
    }

    @Test
    public void shouldParseIRIAndSkipPrefixedSpaceFunctionalSyntax() {
        OWLDocumentFormat f = new FunctionalSyntaxDocumentFormat();
        String bad = "Prefix(:=<http://x.org#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n"
            + "Ontology(<http://x.org>\n"
            + "Declaration(AnnotationProperty(<http://x.org/myprop>))\n"
            + "AnnotationAssertion(<http://x.org/myprop> <http://x.org/myobj> < https://example.org/bad-url>)\n)";
        roundTrip(f, bad);
    }

    protected void roundTrip(OWLDocumentFormat f, String bad) {
        String good = bad.replace(" https://example.org/bad-url", "https://example.org/bad-url");
        OWLOntology o1 = loadOntologyFromString(bad, f);
        OWLOntology o2 = loadOntologyFromString(good, f);
        equal(o1, o2);
    }
}
