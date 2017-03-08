package org.semanticweb.owlapi.api.test.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class ParserBanningTestCase extends TestBase {

    @Test(expected = OWLOntologyCreationException.class)
    public void shouldFailWithBanningOfTriX() throws OWLOntologyCreationException {
        // This ontology is malformed RDF/XML but does not fail under a regular
        // parsing because the
        // TriX parser does not throw an exception reading it (although it does
        // not recognise any axioms)
        // This test ensures that TriX can be banned from parsing
        String in = "<?xml version=\"1.0\"?>\n"
                        + "<rdf:RDF xmlns=\"http://www.semanticweb.org/ontologies/ontologies/2016/2/untitled-ontology-199#\"\n"
                        + "     xml:base=\"http://www.semanticweb.org/ontologies/ontologies/2016/2/untitled-ontology-199\"\n"
                        + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                        + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                        + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
                        + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                        + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
                        + "    <owl:Ontology rdf:about=\"http://www.semanticweb.org/ontologies/ontologies/2016/2/untitled-ontology-199\"/>\n"
                        + "    <owl:Class rdf:about=\"http://ontologies.owl/A\">\n"
                        + "        <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">See more at <a href=\"http://abc.com\">abc</a></rdfs:comment>\n"
                        + "    </owl:Class>\n"
                        + "    <owl:Class rdf:about=\"http://ontologies.owl/B\">\n"
                        + "        <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">a regular comment</rdfs:comment>\n"
                        + "    </owl:Class>\n"
                        + "    <owl:Class rdf:about=\"http://ontologies.owl/C\">\n"
                        + "        <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">another regular comment</rdfs:comment>\n"
                        + "    </owl:Class>\n" + "</rdf:RDF>";
        OWLOntologyManager manager = setupManager();
        // org.semanticweb.owlapi.rio.RioBinaryRdfParserFactory
        // org.semanticweb.owlapi.rio.RioJsonLDParserFactory
        // org.semanticweb.owlapi.rio.RioJsonParserFactory
        // org.semanticweb.owlapi.rio.RioN3ParserFactory
        // org.semanticweb.owlapi.rio.RioNQuadsParserFactory
        // org.semanticweb.owlapi.rio.RioNTriplesParserFactory
        // org.semanticweb.owlapi.rio.RioRDFaParserFactory
        // org.semanticweb.owlapi.rio.RioRDFXMLParserFactory
        // org.semanticweb.owlapi.rio.RioTrigParserFactory
        // org.semanticweb.owlapi.rio.RioTrixParserFactory
        // org.semanticweb.owlapi.rio.RioTurtleParserFactory
        String name = "org.semanticweb.owlapi.rio.RioTrixParserFactory";
        manager.getOntologyConfigurator().withBannedParsers(name);
        manager.loadOntologyFromOntologyDocument(
                        new StringDocumentSource(in, new RDFXMLDocumentFormat()));
    }
}
