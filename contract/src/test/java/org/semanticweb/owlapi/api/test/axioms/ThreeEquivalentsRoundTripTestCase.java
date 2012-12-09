package org.semanticweb.owlapi.api.test.axioms;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** test for 3178902 adapted from the report Thimoty provided. */
@SuppressWarnings("javadoc")
public class ThreeEquivalentsRoundTripTestCase {
    private OWLDataFactory factory;
    private static final String NS = "http://protege.org/ontologies";
    private OWLClass A;
    private OWLClass B;
    private OWLClass C;
    private OWLObjectProperty p;
    private OWLObjectProperty q;
    private OWLAxiom axiomToAdd;

    @Before
    public void setUp() {
        factory = Factory.getFactory();
        A = factory.getOWLClass(IRI.create(NS + "#A"));
        B = factory.getOWLClass(IRI.create(NS + "#B"));
        C = factory.getOWLClass(IRI.create(NS + "#C"));
        p = factory.getOWLObjectProperty(IRI.create(NS + "#p"));
        q = factory.getOWLObjectProperty(IRI.create(NS + "#q"));
        Set<OWLClassExpression> equivalents = new HashSet<OWLClassExpression>();
        equivalents.add(A);
        equivalents.add(factory.getOWLObjectSomeValuesFrom(p, B));
        equivalents.add(factory.getOWLObjectSomeValuesFrom(q, C));
        axiomToAdd = factory.getOWLEquivalentClassesAxiom(equivalents);
    }

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        OWLOntology ontology = Factory.getManager().createOntology();
        ontology.getOWLOntologyManager().addAxiom(ontology, axiomToAdd);
        String saved = saveOntology(ontology);
        // when
        ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(saved));
        // then
        saved = saveOntology(ontology);
        assertTrue(ontology.containsObjectPropertyInSignature(p.getIRI()));
        assertTrue(ontology.containsObjectPropertyInSignature(q.getIRI()));
        assertTrue(ontology.containsClassInSignature(B.getIRI()));
        assertTrue(ontology.containsClassInSignature(C.getIRI()));
    }

    private String saveOntology(OWLOntology o) throws
    OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o, target);
        return target.toString();
    }
}
