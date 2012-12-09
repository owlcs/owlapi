package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class SWRLRoundTripTestCase {
    // test for 3562978
    @Test
    public void shouldDoCompleteRoundtrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        String NS = "urn:test";

        OWLDataFactory factory = Factory.getFactory();
        OWLClass A = factory.getOWLClass(IRI.create(NS + "#A"));
        OWLDataProperty P = factory.getOWLDataProperty(IRI.create(NS + "#P"));
        SWRLVariable X = factory.getSWRLVariable(IRI.create(NS + "#X"));
        SWRLVariable Y = factory.getSWRLVariable(IRI.create(NS + "#Y"));
        OWLOntology ontology = Factory.getManager().createOntology(
                IRI.create(NS));
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(factory.getSWRLDataPropertyAtom(P, X, Y));
        body.add(factory.getSWRLDataRangeAtom(
                factory.getOWLDatatype(XSDVocabulary.STRING.getIRI()), Y));
        Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
        head.add(factory.getSWRLClassAtom(A, X));
        SWRLRule rule = factory.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        StringDocumentTarget t = new StringDocumentTarget();
        OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();
        // format.setPrefix(":", NS);
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto1 = t.toString();
        ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(t.toString()));
        t = new StringDocumentTarget();
        format = new OWLXMLOntologyFormat();
        // format.setPrefix(":", NS);
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto2 = t.toString();
        assertEquals(onto1, onto2);
    }

    @Ignore
    @Test
    public void shouldDoCompleteRoundtripManchesterOWLSyntax()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String NS = "urn:test";
        OWLDataFactory factory = Factory.getFactory();
        OWLClass A = factory.getOWLClass(IRI.create(NS + "#A"));
        OWLDataProperty P = factory.getOWLDataProperty(IRI.create(NS + "#P"));
        SWRLVariable X = factory.getSWRLVariable(IRI.create(NS + "#X"));
        SWRLVariable Y = factory.getSWRLVariable(IRI.create(NS + "#Y"));
        OWLOntology ontology = Factory.getManager().createOntology(
                IRI.create(NS));
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(factory.getSWRLDataPropertyAtom(P, X, Y));
        body.add(factory.getSWRLDataRangeAtom(
                factory.getOWLDatatype(XSDVocabulary.STRING.getIRI()), Y));
        Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
        head.add(factory.getSWRLClassAtom(A, X));
        SWRLRule rule = factory.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        StringDocumentTarget t = new StringDocumentTarget();
        ManchesterOWLSyntaxOntologyFormat format = new ManchesterOWLSyntaxOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto1 = t.toString();
        ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(t.toString()));
        t = new StringDocumentTarget();
        format = new ManchesterOWLSyntaxOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto2 = t.toString();
        assertEquals(onto1, onto2);
    }
}
