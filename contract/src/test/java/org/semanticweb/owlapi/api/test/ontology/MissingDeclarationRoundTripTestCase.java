package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** Test for 3186250 */
@SuppressWarnings("javadoc")
public class MissingDeclarationRoundTripTestCase {
    private static final String NS = "http://test.org/MissingDeclaration.owl";
    OWLClass a;
    OWLAnnotationProperty p;

    @Before
    public void setUp() {
        a = Class(IRI(NS + "#A"));
        p = AnnotationProperty(IRI(NS + "#p"));
    }

    @Test
    public void shouldFindOneAxiom() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAnnotationPropertyInSignature(p.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
        String saved = saveOntology(ontology);
        ontology = loadOntology(saved);
        assertTrue(ontology.containsAnnotationPropertyInSignature(p.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
    }

    private OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        OWLAnnotationAssertionAxiom axiom = AnnotationAssertion(p, a.getIRI(),
                Literal("Hello"));
        manager.addAxiom(ontology, axiom);
        return ontology;
    }

    public String saveOntology(OWLOntology ontology) throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
        format.setAddMissingTypes(false);
        manager.saveOntology(ontology, format, target);
        return target.toString();
    }

    public OWLOntology loadOntology(String o) throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        config.setStrict(true);
        return manager.loadOntologyFromOntologyDocument(new StringDocumentSource(o),
                config);
    }
}
