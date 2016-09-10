package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLRule;

@SuppressWarnings("javadoc")
public class OWLXMLTestCase extends TestBase {

    @Test
    public void shouldFindExpectedAxiomsForBlankNodes() throws OWLOntologyCreationException {
        OWLObjectProperty r = ObjectProperty(IRI.create(
            "http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o = m.loadOntologyFromOntologyDocument(new File(RESOURCES, "owlxml_anonloop.owx"));
        for (OWLClassAssertionAxiom ax : o.getAxioms(AxiomType.CLASS_ASSERTION)) {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(expected + " not found", o.containsAxiom(expected));
        }
    }
    @Test
    public void shouldParseSWRLVariables() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String in="<?xml version=\"1.0\"?>\n" + 
            "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n" + 
            "     xml:base=\"http://www.semanticweb.org/z002yycx/ontologies/2016/5/untitled-ontology-9\"\n" + 
            "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" + 
            "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n" + 
            "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" + 
            "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" + 
            "     ontologyIRI=\"http://www.semanticweb.org/z002yycx/ontologies/2016/5/untitled-ontology-9\">\n" + 
            "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n" + 
            "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n" + 
            "    <Prefix name=\"xml\" IRI=\"http://www.w3.org/XML/1998/namespace\"/>\n" + 
            "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n" + 
            "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n" + 
            "    <DLSafeRule>\n" + 
            "        <Body>\n" + 
            "            <SameIndividualAtom>\n" + 
            "                <Variable IRI=\"x\"/>\n" + 
            "                <Variable IRI=\"y\"/>\n" + 
            "            </SameIndividualAtom>\n" + 
            "        </Body>\n" + 
            "        <Head/>\n" + 
            "    </DLSafeRule>\n" + 
            "</Ontology>";
        OWLOntology o = loadOntologyFromString(in);
        for (SWRLRule r : o.getAxioms(AxiomType.SWRL_RULE)) {
            assertEquals("DLSafeRule( Body(SameAsAtom(Variable(<urn:swrl#x>) Variable(<urn:swrl#y>))) Head() )", r
                .toString());
        }
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        assertTrue(out, out.contains("<Variable IRI=\"x\"/>"));
        assertTrue(out, out.contains("<Variable IRI=\"y\"/>"));
    }
}
