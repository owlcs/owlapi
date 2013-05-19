package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

@SuppressWarnings("javadoc")
public class SWRLAnnotationTestCase {
    String NS = "http://protege.org/ontologies/SWRLAnnotation.owl";
    OWLClass A;
    OWLClass B;
    OWLAxiom AXIOM;

    @Before
    public void setUp() {
        OWLDataFactory factory = Factory.getFactory();
        A = Class(IRI(NS + "#A"));
        B = Class(IRI(NS + "#B"));
        SWRLVariable x = factory.getSWRLVariable(IRI(NS + "#x"));
        SWRLAtom atom1 = factory.getSWRLClassAtom(A, x);
        SWRLAtom atom2 = factory.getSWRLClassAtom(B, x);
        Set<SWRLAtom> consequent = new TreeSet<SWRLAtom>();
        consequent.add(atom1);
        OWLAnnotation annotation = factory.getOWLAnnotation(RDFSComment(),
                Literal("Not a great rule"));
        Set<OWLAnnotation> annotations = new TreeSet<OWLAnnotation>();
        annotations.add(annotation);
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(atom2);
        AXIOM = factory.getSWRLRule(body, consequent, annotations);
        // System.out.println("Using " + AXIOM + " as a rule");
    }

    @Test
    public void shouldRoundTripAnnotation() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAxiom(AXIOM));
        String saved = saveOntology(ontology);
        ontology = loadOntology(saved);
        assertTrue(ontology.containsAxiom(AXIOM));
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        changes.add(new AddAxiom(ontology, AXIOM));
        manager.applyChanges(changes);
        return ontology;
    }

    public String saveOntology(OWLOntology ontology) throws OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        StringDocumentTarget target = new StringDocumentTarget();
        manager.saveOntology(ontology, target);
        return target.toString();
    }

    public OWLOntology loadOntology(String ontologyFile)
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        return manager.loadOntologyFromOntologyDocument(new StringDocumentSource(
                ontologyFile));
    }
}
