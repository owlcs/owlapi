package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 30-Jun-2010
 */
public class AnnotatedPropertyAssertionsTestCase extends AbstractFileTestCase {

    @Override
    protected String getFileName() {
        return "AnnotatedPropertyAssertions.rdf";
    }

    public void testCorrectAxiomAnnotated() {
        OWLOntology ontology = createOntology();
        OWLDataFactory df = getFactory();
        OWLNamedIndividual subject = df.getOWLNamedIndividual(IRI.create("http://Example.com#myBuilding"));
        OWLObjectProperty predicate = df.getOWLObjectProperty(IRI.create("http://Example.com#located_at"));
        OWLNamedIndividual object = df.getOWLNamedIndividual(IRI.create("http://Example.com#myLocation"));
        OWLAxiom ax = df.getOWLObjectPropertyAssertionAxiom(predicate, subject, object);
        assertTrue(ontology.containsAxiomIgnoreAnnotations(ax));
        Set<OWLAxiom> axioms = ontology.getAxiomsIgnoreAnnotations(ax);
        assertTrue(axioms.size() == 1);
        OWLAxiom theAxiom = axioms.iterator().next();
        assertTrue(theAxiom.isAnnotated());
    }
}
