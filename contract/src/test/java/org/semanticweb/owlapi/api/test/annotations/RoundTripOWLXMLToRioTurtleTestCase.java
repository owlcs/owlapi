package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;

@SuppressWarnings("javadoc")
public class RoundTripOWLXMLToRioTurtleTestCase extends AbstractRoundTrippingTestCase {

//@formatter:off
    private static final String original = "<?xml version=\"1.0\"?>\n" + 
        "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n" + 
        "     xml:base=\"http://www.derivo.de/ontologies/examples/nested_annotations\"\n" + 
        "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" + 
        "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n" + 
        "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" + 
        "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" + 
        "     ontologyIRI=\"http://www.derivo.de/ontologies/examples/nested_annotations\">\n" + 
        "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n" + 
        "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n" + 
        "    <Prefix name=\"xml\" IRI=\"http://www.w3.org/XML/1998/namespace\"/>\n" + 
        "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n" + 
        "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n" + 
        "    <Declaration>\n" + 
        "        <NamedIndividual IRI=\"#b\"/>\n" + 
        "    </Declaration>\n" + 
        "    <Declaration>\n" + 
        "        <NamedIndividual IRI=\"#c\"/>\n" + 
        "    </Declaration>\n" + 
        "    <Declaration>\n" + 
        "        <NamedIndividual IRI=\"#a\"/>\n" + 
        "    </Declaration>\n" + 
        "    <Declaration>\n" + 
        "        <ObjectProperty IRI=\"#r\"/>\n" + 
        "    </Declaration>\n" + 
        "    <Declaration>\n" + 
        "        <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" + 
        "    </Declaration>\n" + 
        "    <ObjectPropertyAssertion>\n" + 
        "        <Annotation>\n" + 
        "            <Annotation>\n" + 
        "                <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" + 
        "                <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">comment for one</Literal>\n" + 
        "            </Annotation>\n" + 
        "            <AnnotationProperty abbreviatedIRI=\"rdfs:label\"/>\n" + 
        "            <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">one</Literal>\n" + 
        "        </Annotation>\n" + 
        "        <Annotation>\n" + 
        "            <Annotation>\n" + 
        "                <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" + 
        "                <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">comment for two</Literal>\n" + 
        "            </Annotation>\n" + 
        "            <AnnotationProperty abbreviatedIRI=\"rdfs:label\"/>\n" + 
        "            <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">two</Literal>\n" + 
        "        </Annotation>\n" + 
        "        <ObjectProperty IRI=\"#r\"/>\n" + 
        "        <NamedIndividual IRI=\"#a\"/>\n" + 
        "        <NamedIndividual IRI=\"#b\"/>\n" + 
        "    </ObjectPropertyAssertion>\n" + 
        "    <ObjectPropertyAssertion>\n" + 
        "        <Annotation>\n" + 
        "            <Annotation>\n" + 
        "                <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" + 
        "                <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">comment for three</Literal>\n" + 
        "            </Annotation>\n" + 
        "            <AnnotationProperty abbreviatedIRI=\"rdfs:label\"/>\n" + 
        "            <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">three</Literal>\n" + 
        "        </Annotation>\n" + 
        "        <ObjectProperty IRI=\"#r\"/>\n" + 
        "        <NamedIndividual IRI=\"#b\"/>\n" + 
        "        <NamedIndividual IRI=\"#c\"/>\n" + 
        "    </ObjectPropertyAssertion>\n" + 
        "</Ontology>";
//@formatter:on
    @Override
    protected OWLOntology createOntology() {
        try {
            return m.loadOntologyFromOntologyDocument(new StringDocumentSource(original));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Test
    public void shouldRoundTripThroughOWLXML() throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(original);
        StringDocumentTarget targetOWLXML = new StringDocumentTarget();
        ontology.saveOntology(new OWLXMLDocumentFormat(), targetOWLXML);
        OWLOntology o1 = loadOntologyFromString(targetOWLXML, new OWLXMLDocumentFormat());
        equal(ontology, o1);
    }

    @Test
    public void shouldRoundTripThroughOWLXMLOrTurtle() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(original);
        OWLOntology o1 = roundTrip(ontology, new RioTurtleDocumentFormat());
        equal(ontology, o1);
        OWLOntology o2 = roundTrip(o1, new OWLXMLDocumentFormat());
        equal(o2, o1);
    }

    @Test
    public void shouldRoundTripThroughOWLXMLToTurtle() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(original);
        StringDocumentTarget targetTTL = new StringDocumentTarget();
        ontology.saveOntology(new TurtleDocumentFormat(), targetTTL);
        StringDocumentTarget targetTTLFromTTL = new StringDocumentTarget();
        ontology.saveOntology(new TurtleDocumentFormat(), targetTTLFromTTL);
        assertEquals(targetTTL.toString(), targetTTLFromTTL.toString());
    }

    @Test
    public void shouldRoundTripThroughOWLXMLToRioTurtle() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(original);
        StringDocumentTarget target1 = new StringDocumentTarget();
        ontology.saveOntology(new RioTurtleDocumentFormat(), target1);
        StringDocumentTarget target2 = new StringDocumentTarget();
        ontology.saveOntology(new RioTurtleDocumentFormat(), target2);
        assertEquals(target1.toString().replaceAll("_:genid[0-9]+", "_:genid"), target2.toString().replaceAll(
            "_:genid[0-9]+", "_:genid"));
    }
}
