package org.semanticweb.owlapi.apitest.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.rioformats.RioTurtleDocumentFormat;

class RoundTripOWLXMLToRioTurtleTestCase extends TestBase {

    OWLOntology original() {
        try {
            return m.loadOntologyFromOntologyDocument(
                new StringDocumentSource(TestFiles.original, new OWLXMLDocumentFormat()));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Test
    void shouldRoundTripThroughOWLXML() throws OWLOntologyStorageException {
        OWLOntology ontology = original();
        StringDocumentTarget targetOWLXML = new StringDocumentTarget();
        ontology.saveOntology(new OWLXMLDocumentFormat(), targetOWLXML);
        OWLOntology o1 = loadFrom(targetOWLXML, new OWLXMLDocumentFormat());
        equal(ontology, o1);
    }

    @Test
    void shouldRoundTripThroughOWLXMLOrTurtle() {
        OWLOntology ontology = original();
        OWLOntology o1 = roundTrip(ontology, new RioTurtleDocumentFormat());
        equal(ontology, o1);
        OWLOntology o2 = roundTrip(o1, new OWLXMLDocumentFormat());
        equal(o2, o1);
    }

    @Test
    void shouldRoundTripThroughOWLXMLToTurtle() throws OWLOntologyStorageException {
        OWLOntology ontology = original();
        StringDocumentTarget targetTTL = new StringDocumentTarget();
        ontology.saveOntology(new TurtleDocumentFormat(), targetTTL);
        StringDocumentTarget targetTTLFromTTL = new StringDocumentTarget();
        ontology.saveOntology(new TurtleDocumentFormat(), targetTTLFromTTL);
        assertEquals(targetTTL.toString(), targetTTLFromTTL.toString());
    }

    @Test
    void shouldRoundTripThroughOWLXMLToRioTurtle() throws OWLOntologyStorageException {
        OWLOntology ontology = original();
        StringDocumentTarget target1 = new StringDocumentTarget();
        ontology.saveOntology(new RioTurtleDocumentFormat(), target1);
        StringDocumentTarget target2 = new StringDocumentTarget();
        ontology.saveOntology(new RioTurtleDocumentFormat(), target2);
        assertEquals(target1.toString().replaceAll("_:genid[0-9]+", "_:genid"),
            target2.toString().replaceAll("_:genid[0-9]+", "_:genid"));
    }
}
