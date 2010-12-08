package org.semanticweb.owlapi.api.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class OWLXMLNullPointerTestCase extends AbstractOWLAPITestCase {

    private static String NS = "http://www.co-ode.org/ontologies/pizza/pizza.owl";

    public static final String ANONYMOUS_INDIVIDUAL_ANNOTATION = "Anonymous individual for testing";

    public void testRoundTrip() {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.createOntology(IRI.create(NS));
            OWLDataFactory factory = manager.getOWLDataFactory();

            OWLAnonymousIndividual i = factory.getOWLAnonymousIndividual();
            manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(factory.getRDFSLabel(), i, factory.getOWLLiteral(ANONYMOUS_INDIVIDUAL_ANNOTATION)));
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(factory.getOWLClass(IRI.create(NS + "#CheeseyPizza")), i));
            OWLIndividual j = factory.getOWLAnonymousIndividual();
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(factory.getOWLClass(IRI.create(NS + "#CheeseTopping")), j));
            manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(factory.getOWLObjectProperty(IRI.create(NS + "#hasTopping")), i, j));

            File tmpFile = File.createTempFile("Test", ".owl");
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), new StreamDocumentTarget(new FileOutputStream(tmpFile)));

            OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();
            manager2.loadOntologyFromOntologyDocument(tmpFile);
        }
        catch (OWLOntologyCreationException e) {
            fail(e.getMessage());
        }
        catch (IOException e) {
            fail(e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            fail(e.getMessage());
        }
    }

}
