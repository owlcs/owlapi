package org.semanticweb.owlapi.api.test.anonymous;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class AnonymousTestCase extends AbstractOWLAPITestCase {

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLClass C = Class(IRI("urn:test#C"));
        OWLClass D = Class(IRI("urn:test#D"));
        OWLObjectProperty P = ObjectProperty(IRI("urn:test#p"));
        OWLDataProperty Q = DataProperty(IRI("urn:test#q"));
        OWLIndividual i = AnonymousIndividual();
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology();
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        changes.add(new AddAxiom(ontology, SubClassOf(C, ObjectHasValue(P, i))));
        changes.add(new AddAxiom(ontology, ClassAssertion(D, i)));
        changes.add(new AddAxiom(ontology, DataPropertyAssertion(Q, i,
                Literal("hello"))));
        manager.applyChanges(changes);
        String saved = saveOntology(ontology);
        OWLOntology ontologyReloaded = loadOntologyFromString(saved);
        saved = saveOntology(ontologyReloaded);
        equal(ontology, ontologyReloaded);
        // assertEquals(asString(ontology), asString(ontologyReloaded));
    }

    public static Set<String> asString(OWLOntology o) {
        Set<String> set = new HashSet<String>();
        for (OWLAxiom ax : o.getLogicalAxioms()) {
            set.add(ax.toString().replaceAll("\\_\\:genid[0-9]+", "genid"));
        }
        return set;
    }

    String saveOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology, target);
        return target.toString();
    }

    OWLOntology loadOntologyFromString(String ontologyFile)
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        ontologyFile));
        return ontology;
    }

    @Test
    public void testRoundTripWithAnonymousIndividuals() throws Exception {
        String NS = "http://test.com/genid#";
        IRI ONT = IRI.create(NS + "ontology.owl");
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLNamedIndividual I = df.getOWLNamedIndividual(IRI.create(NS + "i"));
        OWLObjectProperty P = df.getOWLObjectProperty(IRI.create(NS + "p"));
        OWLDataProperty Q = df.getOWLDataProperty(IRI.create(NS + "q"));
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology(ONT);
        OWLIndividual ind = df.getOWLAnonymousIndividual();
        OWLObjectPropertyAssertionAxiom ax1 = df
                .getOWLObjectPropertyAssertionAxiom(P, I, ind);
        OWLDataPropertyAssertionAxiom ax2 = df
                .getOWLDataPropertyAssertionAxiom(Q, ind, df.getOWLLiteral(5));
        manager.addAxiom(ontology, ax1);
        manager.addAxiom(ontology, ax2);
        StringDocumentTarget savedLocation = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology, savedLocation);
        OWLOntology reloadedOntology = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(savedLocation));
        assertTrue(equal(ontology, reloadedOntology));
    }
}
