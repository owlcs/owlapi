package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class InvalidAxiomRoundTripTestCase {

    private OWLOntology o;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        o = Factory.getManager().createOntology();
    }

    private void assertCorrectResult(OWLAxiom wrongAxiom, OWLAxiom validAxiom,
            OWLOntology reloaded) {
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    private void addAxioms(OWLAxiom... axioms) {
        for (OWLAxiom ax : axioms) {
            o.getOWLOntologyManager().addAxiom(o, ax);
        }
    }

    private OWLOntology saveAndReload() throws OWLOntologyStorageException,
            OWLOntologyCreationException {
        StringDocumentTarget target = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o,
                new OWLFunctionalSyntaxOntologyFormat(), target);
        OWLOntology reloaded = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(target.toString()));
        return reloaded;
    }

    @Test
    public void shouldRoundTripInvalidDifferentIndividuals()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLNamedIndividual e1 = NamedIndividual(IRI("urn:test1"));
        OWLNamedIndividual e2 = NamedIndividual(IRI("urn:test2"));
        OWLNamedIndividual e3 = NamedIndividual(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DifferentIndividuals(e1);
        OWLAxiom validAxiom = DifferentIndividuals(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    public void shouldRoundTripInvalidDisjointClasses()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLClass e1 = Class(IRI("urn:test1"));
        OWLClass e2 = Class(IRI("urn:test2"));
        OWLClass e3 = Class(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DisjointClasses(e1);
        OWLAxiom validAxiom = DisjointClasses(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    @Test
    public void shouldRoundTripInvalidDisjointObjectProperties()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLObjectProperty e1 = ObjectProperty(IRI("urn:test1"));
        OWLObjectProperty e2 = ObjectProperty(IRI("urn:test2"));
        OWLObjectProperty e3 = ObjectProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DisjointObjectProperties(e1);
        OWLAxiom validAxiom = DisjointObjectProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    @Test
    public void shouldRoundTripInvalidDisjointDataProperties()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLDataProperty e1 = DataProperty(IRI("urn:test1"));
        OWLDataProperty e2 = DataProperty(IRI("urn:test2"));
        OWLDataProperty e3 = DataProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DisjointDataProperties(e1);
        OWLAxiom validAxiom = DisjointDataProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    @Test
    public void shouldRoundTripInvalidSameIndividuals()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLNamedIndividual e1 = NamedIndividual(IRI("urn:test1"));
        OWLNamedIndividual e2 = NamedIndividual(IRI("urn:test2"));
        OWLNamedIndividual e3 = NamedIndividual(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = SameIndividual(e1);
        OWLAxiom validAxiom = SameIndividual(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    public void shouldRoundTripInvalidEquivalentClasses()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLClass e1 = Class(IRI("urn:test1"));
        OWLClass e2 = Class(IRI("urn:test2"));
        OWLClass e3 = Class(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = EquivalentClasses(e1);
        OWLAxiom validAxiom = EquivalentClasses(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    @Test
    public void shouldRoundTripInvalidEquivalentObjectProperties()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLObjectProperty e1 = ObjectProperty(IRI("urn:test1"));
        OWLObjectProperty e2 = ObjectProperty(IRI("urn:test2"));
        OWLObjectProperty e3 = ObjectProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = EquivalentObjectProperties(e1);
        OWLAxiom validAxiom = EquivalentObjectProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    @Test
    public void shouldRoundTripInvalidEquivalentDataProperties()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLDataProperty e1 = DataProperty(IRI("urn:test1"));
        OWLDataProperty e2 = DataProperty(IRI("urn:test2"));
        OWLDataProperty e3 = DataProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = EquivalentDataProperties(e1);
        OWLAxiom validAxiom = EquivalentDataProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }
}
