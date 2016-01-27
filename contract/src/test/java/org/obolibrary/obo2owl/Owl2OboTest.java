package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class Owl2OboTest extends OboFormatTestBasics {

    @Test
    public void testConversion() throws Exception {
        OWLOntology ontology = convert(parseOBOFile("caro.obo"));
        OBODoc doc = convert(ontology);
        writeOBO(doc);
    }

    @Test
    public void testIRTsConversion() throws Exception {
        IRI ontologyIRI = IRI.create("http://purl.obolibrary.org/obo/test.owl");
        OWLOntology ontology = m.createOntology(ontologyIRI);
        convert(ontology);
        String ontId = OWLAPIOwl2Obo.getOntologyId(ontology);
        assertEquals("test", ontId);
        IRI iri = IRI.create("http://purl.obolibrary.org/obo/OBI_0000306");
        String id = OWLAPIOwl2Obo.getIdentifier(iri);
        assertTrue("OBI:0000306".endsWith(id));
        iri = IRI.create("http://purl.obolibrary.org/obo/IAO_0000119");
        id = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("IAO:0000119", id);
        iri = IRI.create("http://purl.obolibrary.org/obo/caro_part_of");
        id = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("http://purl.obolibrary.org/obo/caro_part_of", id);
        iri = IRI.create("http://purl.obolibrary.org/obo/MyOnt#_part_of");
        id = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("MyOnt:part_of", id);
        iri = IRI.create("http://purl.obolibrary.org/obo/MyOnt#termid");
        id = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("termid", id);
        // unprefixed IDs from different ontology
        iri = IRI.create("http://purl.obolibrary.org/obo/MyOnt#termid");
        id = OWLAPIOwl2Obo.getIdentifier(iri);
        // assertTrue("http://purl.obolibrary.org/obo/MyOnt#termid".equals(id));
        iri = IRI.create("http://www.w3.org/2002/07/owl#topObjectProperty");
        id = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("owl:topObjectProperty", id);
    }
}
