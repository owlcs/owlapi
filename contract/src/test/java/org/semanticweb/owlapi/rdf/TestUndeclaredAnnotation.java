package org.semanticweb.owlapi.rdf;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Created by ses on 3/10/14.
 */
public class TestUndeclaredAnnotation extends TestBase {

    @Test
    public void testRDFXMLUsingUndeclaredAnnotationProperty()
            throws FileNotFoundException, OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<!DOCTYPE rdf:RDF [\n <!ENTITY ns \"http://example.com/ns#\" >\n <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n <!ENTITY xml \"http://www.w3.org/XML/1998/namespace\" >\n <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n ]>\n"
                + "<rdf:RDF xmlns=\"http://www.org/\" xml:base=\"http://www.org/\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:ns=\"http://example.com/ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\">\n"
                + "    <owl:Ontology rdf:about=\"http://www.org/\"/>\n"
                + "    <rdf:Description rdf:about=\"&ns;test\"><ns:rel><rdf:Description ns:pred =\"Not visible\"/></ns:rel></rdf:Description>\n"
                + "</rdf:RDF>";
        OWLOntology oo = loadOntologyFromString(input);
        RDFXMLOntologyFormat format = (RDFXMLOntologyFormat) oo
                .getOWLOntologyManager().getOntologyFormat(oo);
        assertEquals("Should have no unparsed triples", 0, format
                .getOntologyLoaderMetaData().getUnparsedTriples().size());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms = oo
                .getAxioms(AxiomType.ANNOTATION_ASSERTION);
        assertEquals("annotation axiom count should be 2", 2,
                annotationAxioms.size());
        OWLAnnotationProperty relProperty = df.getOWLAnnotationProperty(IRI
                .create("http://example.com/ns#rel"));
        OWLAnnotationProperty predProperty = df.getOWLAnnotationProperty(IRI
                .create("http://example.com/ns#pred"));
        Set<OWLAnonymousIndividual> anonymousIndividualSet = oo
                .getAnonymousIndividuals();
        assertEquals("should be one anonymous individual", 1,
                anonymousIndividualSet.size());
        OWLAnonymousIndividual anonymousIndividual = anonymousIndividualSet
                .iterator().next();
        OWLAnnotationAssertionAxiom relAx = df.getOWLAnnotationAssertionAxiom(
                relProperty, IRI.create("http://example.com/ns#test"),
                anonymousIndividual);
        OWLLiteral notVisible = df.getOWLLiteral("Not visible", "");
        OWLAnnotationAssertionAxiom predAx = df.getOWLAnnotationAssertionAxiom(
                predProperty, anonymousIndividual, notVisible);
        assertTrue("should contain relax", annotationAxioms.contains(relAx));
        assertTrue("should contain predax", annotationAxioms.contains(predAx));
    }
}
