package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** Test for 3186250 */
@SuppressWarnings("javadoc")
public class MissingDeclarationRoundTripTestCase {
    OWLDataFactory factory;
    String NS;
    OWLClass A;
    OWLAnnotationProperty P;

    @Before
    public void setUp() {
        factory = OWLManager.getOWLDataFactory();
        NS = "http://test.org/MissingDeclaration.owl";
        A = factory.getOWLClass(IRI.create(NS + "#A"));
        P = factory.getOWLAnnotationProperty(IRI.create(NS + "#p"));
    }

    @Test
    public void shouldFindOneAxiom() throws OWLOntologyCreationException,
    OWLOntologyStorageException, IOException {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAnnotationPropertyInSignature(P.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
        String saved = saveOntology(ontology);
        ontology = loadOntology(saved);
        assertTrue(ontology.containsAnnotationPropertyInSignature(P.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
    }

    private OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology(IRI.create(NS));
        OWLAnnotation annotation = factory.getOWLAnnotation(P, factory.getOWLLiteral("Hello"));
        OWLAnnotationAssertionAxiom axiom = factory.getOWLAnnotationAssertionAxiom(A.getIRI(), annotation);
        manager.addAxiom(ontology, axiom);
        return ontology;
    }


    public String saveOntology(OWLOntology ontology) throws IOException,
    OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
        format.setAddMissingTypes(false);
        manager.saveOntology(ontology, format, target);
        return target.toString();
    }

    public OWLOntology loadOntology(String o) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        config.setStrict(true);
        return manager.loadOntologyFromOntologyDocument(new StringDocumentSource(o),
                config);
    }


}
