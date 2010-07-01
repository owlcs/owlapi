package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(factory.getRDFSLabel(), i, factory.getOWLStringLiteral(ANONYMOUS_INDIVIDUAL_ANNOTATION)));
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
