package org.semanticweb.owlapi.api.test;

import java.util.Collections;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class GetAxiomsIgnoringAnnotationsTestCase extends AbstractOWLAPITestCase {

    public void testGetAxiomsIgnoringAnnoations() throws Exception {
        OWLLiteral annoLiteral = getFactory().getOWLLiteral("value");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");
        OWLAnnotation anno = getFactory().getOWLAnnotation(annoProp, annoLiteral);
        OWLAxiom axiom = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(anno));
        OWLOntology ont = getOWLOntology("testont");
        getManager().addAxiom(ont, axiom);
        assertTrue(ont.getAxiomsIgnoreAnnotations(axiom).contains(axiom));
        assertFalse(ont.getAxiomsIgnoreAnnotations(axiom).contains(axiom.getAxiomWithoutAnnotations()));
        assertTrue(ont.getAxiomsIgnoreAnnotations(axiom.getAxiomWithoutAnnotations()).contains(axiom));
        assertFalse(ont.getAxiomsIgnoreAnnotations(axiom.getAxiomWithoutAnnotations()).contains(axiom.getAxiomWithoutAnnotations()));

    }
}
