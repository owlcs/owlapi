package org.semanticweb.owlapi.api.test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings("javadoc")
public class AnonymousFunctionalRoundtripTestCase {
    private static String NS = "http://namespace.owl";
    private static String broken = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF xmlns=\"http://namespace.owl#\"\n"
            + "     xml:base=\"http://namespace.owl\"\n"
            + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
            + "    <owl:Ontology rdf:about=\"http://namespace.owl\"/>\n"
            + "    <owl:Class rdf:about=\"http://namespace.owl#A\"/>\n"
            + "<A/></rdf:RDF>";
    private static String fixed = "Prefix(:=<http://namespace.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n"
            + "\n"
            + "\n"
            + "Ontology(<http://namespace.owl>\n"
            + "\n"
            + "Declaration(Class(:C))\n"
            + "SubClassOf(:C ObjectHasValue(:p _:genid2))\n"
            + "Declaration(Class(:D))\n"
            + "Declaration(ObjectProperty(:p))\n"
            + "Declaration(DataProperty(:q))\n"
            + "ClassAssertion(:D _:genid2)\n"
            + "DataPropertyAssertion(:q _:genid2 \"hello\"^^xsd:string)\n"
            + ")";



    @Test
    public void shouldRoundTripFixed() throws OWLOntologyCreationException {
        loadOntology(fixed);
    }

    @Test
    public void shouldRoundTripBroken() throws OWLOntologyCreationException,
    OWLOntologyStorageException, IOException {
        OWLOntology o = loadOntology(broken);
        String s = saveOntology(o, new OWLFunctionalSyntaxOntologyFormat());
        OWLOntology o1 = loadOntology(s);
        assertEquals(o.getLogicalAxioms(), o1.getLogicalAxioms());
    }

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
    OWLOntologyStorageException, IOException {
        OWLDataFactory df = Factory.getFactory();
        OWLClass C = df.getOWLClass(IRI.create(NS + "#C"));
        OWLClass D = df.getOWLClass(IRI.create(NS + "#D"));
        OWLObjectProperty P = df.getOWLObjectProperty(IRI.create(NS + "#p"));
        OWLDataProperty Q = df.getOWLDataProperty(IRI.create(NS + "#q"));
        OWLIndividual i = df.getOWLAnonymousIndividual();
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI.create(NS));
        OWLDataFactory factory = manager.getOWLDataFactory();
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

        changes.add(new AddAxiom(ontology, factory.getOWLSubClassOfAxiom(C, factory.getOWLObjectHasValue(P, i))));
        changes.add(new AddAxiom(ontology, factory.getOWLClassAssertionAxiom(D, i)));
        changes.add(new AddAxiom(ontology, factory.getOWLDataPropertyAssertionAxiom(Q, i, factory.getOWLLiteral("hello"))));

        manager.applyChanges(changes);
        String saved = saveOntology(ontology, new RDFXMLOntologyFormat());
        ontology = loadOntology(saved);
        saved = saveOntology(ontology, new OWLFunctionalSyntaxOntologyFormat());
        ontology = loadOntology(saved);
    }

    String saveOntology(OWLOntology ontology, PrefixOWLOntologyFormat format)
            throws IOException, OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        format.setDefaultPrefix(NS + "#");
        StringDocumentTarget target = new StringDocumentTarget();
        manager.saveOntology(ontology, format, target);
        return target.toString();
    }

    OWLOntology loadOntology(String ontologyFile) throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(ontologyFile));
        return ontology;
    }

}
