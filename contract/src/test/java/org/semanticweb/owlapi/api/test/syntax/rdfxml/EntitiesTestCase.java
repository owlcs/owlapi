package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;

@SuppressWarnings("javadoc")
public class EntitiesTestCase extends TestBase {

    @Test
    public void shouldRoundtripEntities() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<!DOCTYPE rdf:RDF [\n"
                + "    <!ENTITY vin  \"http://www.w3.org/TR/2004/REC-owl-guide-20040210/wine#\" >\n"
                + "    <!ENTITY food \"http://www.w3.org/TR/2004/REC-owl-guide-20040210/food#\" > ]>\n"
                + "<rdf:RDF \n"
                + "    xmlns:owl =\"http://www.w3.org/2002/07/owl#\"\n"
                + "    xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "    xmlns:xsd =\"http://www.w3.org/2001/XMLSchema#\"> \n"
                + "<owl:Ontology rdf:about=\"\"> \n"
                + "  <rdfs:comment>An example OWL ontology</rdfs:comment>\n"
                + "  <owl:priorVersion rdf:resource=\"&vin;test\"/> \n"
                + "</owl:Ontology>" + "</rdf:RDF>";
        XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);
        OWLOntology o = m
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        input, IRI.create("urn:test"),
                        new RDFXMLDocumentFormat(), null));
        o.getFormat()
                .asPrefixOWLOntologyFormat()
                .setPrefix("vin:",
                        "http://www.w3.org/TR/2004/REC-owl-guide-20040210/wine#");
        StringDocumentTarget o2 = saveOntology(o);
        assertTrue(
                o2.toString(),
                o2.toString().contains(
                        "<owl:priorVersion rdf:resource=\"&vin;test\"/>"));
    }
}
