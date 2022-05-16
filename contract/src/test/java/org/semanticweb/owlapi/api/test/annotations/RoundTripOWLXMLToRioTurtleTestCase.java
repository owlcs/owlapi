package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestFiles;
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

public class RoundTripOWLXMLToRioTurtleTestCase extends AbstractRoundTrippingTestCase {

    @Override
    protected OWLOntology createOntology() {
        try {
            return m.loadOntologyFromOntologyDocument(
                new StringDocumentSource(TestFiles.original, new OWLXMLDocumentFormat()));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Test
    public void shouldRoundTripThroughOWLXML() throws OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(TestFiles.original, new OWLXMLDocumentFormat());
        StringDocumentTarget targetOWLXML = new StringDocumentTarget();
        ontology.saveOntology(new OWLXMLDocumentFormat(), targetOWLXML);
        OWLOntology o1 = loadOntologyFromString(targetOWLXML, new OWLXMLDocumentFormat());
        equal(ontology, o1);
    }

    @Test
    public void shouldRoundTripThroughOWLXMLOrTurtle() throws OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(TestFiles.original, new OWLXMLDocumentFormat());
        OWLOntology o1 = roundTrip(ontology, new RioTurtleDocumentFormat());
        equal(ontology, o1);
        OWLOntology o2 = roundTrip(o1, new OWLXMLDocumentFormat());
        equal(o2, o1);
    }

    @Test
    public void shouldRoundTripThroughOWLXMLToTurtle() throws OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(TestFiles.original, new OWLXMLDocumentFormat());
        StringDocumentTarget targetTTL = new StringDocumentTarget();
        ontology.saveOntology(new TurtleDocumentFormat(), targetTTL);
        StringDocumentTarget targetTTLFromTTL = new StringDocumentTarget();
        ontology.saveOntology(new TurtleDocumentFormat(), targetTTLFromTTL);
        assertEquals(targetTTL.toString(), targetTTLFromTTL.toString());
    }

    @Test
    public void shouldRoundTripThroughOWLXMLToRioTurtle() throws OWLOntologyStorageException {
        OWLOntology ontology = loadOntologyFromString(TestFiles.original, new OWLXMLDocumentFormat());
        StringDocumentTarget target1 = new StringDocumentTarget();
        ontology.saveOntology(new RioTurtleDocumentFormat(), target1);
        StringDocumentTarget target2 = new StringDocumentTarget();
        ontology.saveOntology(new RioTurtleDocumentFormat(), target2);
        assertEquals(target1.toString().replaceAll("_:genid[0-9]+", "_:genid"),
            target2.toString().replaceAll("_:genid[0-9]+", "_:genid"));
    }
}
