package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@Ignore
@SuppressWarnings("javadoc")
public class SWRLRoundTripTestCase {
    public static final String NS = "http://protege.org/ontologies/SWRLTest.owl";
    private OWLClass A;
    private OWLDataProperty P;
    private SWRLVariable X;
    private SWRLVariable Y;
    private OWLOntology ontology;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        A = factory.getOWLClass(IRI.create(NS + "#A"));
        P = factory.getOWLDataProperty(IRI.create(NS + "#P"));
        X = factory.getSWRLVariable(IRI.create(NS + "#X"));
        Y = factory.getSWRLVariable(IRI.create(NS + "#Y"));
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        ontology = manager.createOntology(IRI.create(NS));
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(factory.getSWRLDataPropertyAtom(P, X, Y));
        body.add(factory.getSWRLDataRangeAtom(
                factory.getOWLDatatype(XSDVocabulary.STRING.getIRI()), Y));
        Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
        head.add(factory.getSWRLClassAtom(A, X));
        SWRLRule rule = factory.getSWRLRule(body, head);
        manager.addAxiom(ontology, rule);
    }

    @Test
    public void shouldRoundtrip() throws OWLOntologyCreationException,
    OWLOntologyStorageException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();
        format.setPrefix(":", NS);
        manager.saveOntology(ontology, format, out);
        manager = OWLManager.createOWLOntologyManager();
        byte[] byteArray = out.toByteArray();
        System.out.println("SWRLRoundTripTestCase.shouldRoundtrip() "
                + new String(byteArray));
        OWLOntology result = manager
                .loadOntologyFromOntologyDocument(new ByteArrayInputStream(byteArray));
        assertNotNull(result);
    }
}
