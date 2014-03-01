package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings("javadoc")
public class DisjointClassesRoundTripTestCase {

    String NS = "http://ns.owl";
    OWLClass C;
    OWLClass D;
    OWLClass E;
    OWLClass F;

    @Before
    public void setUp() {
        C = Class(IRI(NS + "#C"));
        D = Class(IRI(NS + "#D"));
        E = Class(IRI(NS + "#E"));
        F = Class(IRI(NS + "#F"));
    }

    @Test
    public void shouldParse() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        manager.addAxiom(
                ontology,
                DisjointClasses(ObjectUnionOf(C, D), ObjectUnionOf(C, E),
                        ObjectUnionOf(C, F)));
        String input = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
                + "        Prefix: piz: <http://ns.owl#>\n"
                + "        Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "        Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
                + "        Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "        Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "\n" + "        Ontology: <http://ns.owl>\n"
                + "        Class: piz:F\n" + "        Class: piz:E\n"
                + "        Class: piz:D\n" + "        Class: piz:C\n"
                + "        DisjointClasses: \n"
                + "             ( piz:D or piz:C),\n"
                + "             (piz:E or piz:C),\n"
                + "             (piz:F or piz:C)";
        OWLOntology roundtripped = loadOntology(input);
        assertEquals(input, ontology.getLogicalAxioms(),
                roundtripped.getLogicalAxioms());
    }

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        OWLDisjointClassesAxiom disjointClasses = DisjointClasses(
                ObjectUnionOf(C, D), ObjectUnionOf(C, E), ObjectUnionOf(C, F));
        manager.addAxiom(ontology, disjointClasses);
        String s = saveOntology(ontology);
        OWLOntology roundtripped = loadOntology(s);
        assertEquals(s, ontology.getLogicalAxioms(),
                roundtripped.getLogicalAxioms());
    }

    public String saveOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
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
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        ontologyFile));
        return ontology;
    }
}
