package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
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

public class AnonymousTestCase {


    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
    OWLOntologyStorageException, IOException {
        OWLDataFactory df = Factory.getFactory();
        OWLClass C = df.getOWLClass(IRI.create("urn:test#C"));
        OWLClass D = df.getOWLClass(IRI.create("urn:test#D"));
        OWLObjectProperty P = df.getOWLObjectProperty(IRI.create("urn:test#p"));
        OWLDataProperty Q = df.getOWLDataProperty(IRI.create("urn:test#q"));
        OWLIndividual i = df.getOWLAnonymousIndividual();
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology();
        OWLDataFactory factory = manager.getOWLDataFactory();
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

        changes.add(new AddAxiom(ontology, factory.getOWLSubClassOfAxiom(C, factory.getOWLObjectHasValue(P, i))));
        changes.add(new AddAxiom(ontology, factory.getOWLClassAssertionAxiom(D, i)));
        changes.add(new AddAxiom(ontology, factory.getOWLDataPropertyAssertionAxiom(Q, i, factory.getOWLLiteral("hello"))));

        manager.applyChanges(changes);
        String saved = saveOntology(ontology);
        // System.out.println("AnonymousFunctionalRoundtripTestCase.shouldRoundTrip() "
        // + saved);
        OWLOntology ontologyReloaded = loadOntology(saved);
        saved = saveOntology(ontologyReloaded);
        System.out.println("AnonymousFunctionalRoundtripTestCase.shouldRoundTrip() "
                + saved);
        assertEquals(asString(ontology), asString(ontologyReloaded));
    }

    public static Set<String> asString(OWLOntology o) {
        Set<String> set = new HashSet<String>();
        for (OWLAxiom ax : o.getLogicalAxioms()) {
            set.add(ax.toString().replaceAll("\\_\\:genid[0-9]+", "genid"));
        }
        System.out.println("AnonymousTestCase.asString() "
                + set.toString().replace(",", "\n"));
        return set;
    }

    String saveOntology(OWLOntology ontology)
            throws IOException, OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology, target);
        return target.toString();
    }

    OWLOntology loadOntology(String ontologyFile) throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(ontologyFile));
        return ontology;
    }

}
