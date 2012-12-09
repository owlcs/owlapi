package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.Assert.assertEquals;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@Ignore
public class DisjointClassesRoundTripTestCase {
    String NS = "http://ns.owl";
    OWLClass C;
    OWLClass D;
    OWLClass E;
    OWLClass F;

    @Before
    public void setUp() {
        C = OWLManager.getOWLDataFactory().getOWLClass(IRI.create(NS + "#C"));
        D = OWLManager.getOWLDataFactory().getOWLClass(IRI.create(NS + "#D"));
        E = OWLManager.getOWLDataFactory().getOWLClass(IRI.create(NS + "#E"));
        F = OWLManager.getOWLDataFactory().getOWLClass(IRI.create(NS + "#F"));
    }


    @Test
    public void shouldParse() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology(IRI.create(NS));
        OWLDataFactory factory = manager.getOWLDataFactory();
        manager.addAxiom(ontology, factory.getOWLDisjointClassesAxiom(
                factory.getOWLObjectUnionOf(C, D), factory.getOWLObjectUnionOf(C, E),
                factory.getOWLObjectUnionOf(C, F)));
        String input = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
                + "        Prefix: piz: <http://ns.owl#>\n"
                + "        Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "        Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
                + "        Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "        Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "\n" + "        Ontology: <http://ns.owl>\n" + "        Class: piz:F\n"
                + "        Class: piz:E\n" + "        Class: piz:D\n"
                + "        Class: piz:C\n" + "        DisjointClasses: \n"
                + "             or piz:D,piz:C,\n" + "             or piz:E,piz:C,\n"
                + "             or piz:F,piz:C";
        OWLOntology roundtripped = loadOntology(input);
        for (OWLAxiom ax : ontology.getLogicalAxioms()) {
            System.out.println("o1 " + ax);
        }
        for (OWLAxiom ax : roundtripped.getLogicalAxioms()) {
            System.out.println("o2 " + ax);
        }
        assertEquals(input, ontology.getLogicalAxioms(), roundtripped.getLogicalAxioms());
    }

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology(IRI.create(NS));
        OWLDataFactory factory = manager.getOWLDataFactory();
        manager.addAxiom(ontology, factory.getOWLDisjointClassesAxiom(
                factory.getOWLObjectUnionOf(C, D), factory.getOWLObjectUnionOf(C, E),
                factory.getOWLObjectUnionOf(C, F)));
        String s = saveOntology(ontology);
        OWLOntology roundtripped = loadOntology(s);
        for (OWLAxiom ax : ontology.getLogicalAxioms()) {
            System.out.println("o1 " + ax);
        }
        for (OWLAxiom ax : roundtripped.getLogicalAxioms()) {
            System.out.println("o2 " + ax);
        }
        assertEquals(s, ontology.getLogicalAxioms(), roundtripped.getLogicalAxioms());
    }

    public String saveOntology(OWLOntology ontology) throws
            OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        PrefixOWLOntologyFormat format = new ManchesterOWLSyntaxOntologyFormat();
        format.setPrefix("piz", NS + "#");
        StringDocumentTarget target = new StringDocumentTarget();
        manager.saveOntology(ontology, format, target);
        return target.toString();
    }

    public OWLOntology loadOntology(String ontologyFile)
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(ontologyFile));
        return ontology;
    }
}
