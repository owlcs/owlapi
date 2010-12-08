package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-May-2007<br><br>
 * <p/>
 * A test case which ensures that an ontology contains
 * entity references when that ontology only contains
 * entity declaration axioms.  In other words, entity
 * declaration axioms produce the correct entity references.
 */
public class DeclarationEntityReferencesTestCase extends AbstractOWLAPITestCase {

    public void testOWLClassDeclarationAxiom() throws Exception {
        OWLClass cls = getFactory().getOWLClass(TestUtils.createIRI());
        OWLAxiom ax = getFactory().getOWLDeclarationAxiom(cls);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getClassesInSignature().contains(cls));
    }

    public void testOWLObjectPropertyDeclarationAxiom() throws Exception {
        OWLObjectProperty prop = getFactory().getOWLObjectProperty(TestUtils.createIRI());
        OWLAxiom ax = getFactory().getOWLDeclarationAxiom(prop);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getObjectPropertiesInSignature().contains(prop));
    }

    public void testOWLDataPropertyDeclarationAxiom() throws Exception {
        OWLDataProperty prop = getFactory().getOWLDataProperty(TestUtils.createIRI());
        OWLAxiom ax = getFactory().getOWLDeclarationAxiom(prop);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getDataPropertiesInSignature().contains(prop));
    }

    public void testOWLIndividualDeclarationAxiom() throws Exception {
        OWLNamedIndividual ind = getFactory().getOWLNamedIndividual(TestUtils.createIRI());
        OWLAxiom ax = getFactory().getOWLDeclarationAxiom(ind);
        OWLOntologyManager man = getManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getIndividualsInSignature().contains(ind));
    }
}
