package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-May-2009
 */
public class AnnotationPropertyReferencesTestCase extends AbstractOWLAPITestCase {

    public void testContainsReferenceForAnnotationAssertion() throws Exception {
        OWLAnnotationProperty ap = getOWLAnnotationProperty("prop");
        OWLLiteral val = getFactory().getOWLLiteral("Test", "");
        OWLAnnotationSubject subject = getOWLClass("A").getIRI();
        OWLAnnotationAssertionAxiom ax = getFactory().getOWLAnnotationAssertionAxiom(ap, subject, val);
        OWLOntology ont = getOWLOntology("Ont");
        getManager().addAxiom(ont, ax);
        assertTrue(ont.containsAnnotationPropertyInSignature(ap.getIRI()));
        assertTrue(ont.getAnnotationPropertiesInSignature().contains(ap));
    }
    

    public void testContainsReferenceForAxiomAnnotation() throws Exception {
        OWLAnnotationProperty ap = getOWLAnnotationProperty("prop");
        OWLLiteral val = getFactory().getOWLLiteral("Test", "");
        OWLAnnotation anno = getFactory().getOWLAnnotation(ap, val);
        Set<OWLAnnotation> annos = Collections.singleton(anno);
        OWLSubClassOfAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), annos);
        OWLOntology ont = getOWLOntology("Ont");
        getManager().addAxiom(ont, ax);
        assertTrue(ont.containsAnnotationPropertyInSignature(anno.getProperty().getIRI()));
        assertTrue(ont.getAnnotationPropertiesInSignature().contains(anno.getProperty()));
    }

    public void testContainsReferenceForOntologyAnnotation() throws Exception {
        OWLAnnotationProperty ap = getOWLAnnotationProperty("prop");
        OWLLiteral val = getFactory().getOWLLiteral("Test");
        OWLAnnotation anno = getFactory().getOWLAnnotation(ap, val);
        OWLOntology ont = getOWLOntology("Ont");
        getManager().applyChange(new AddOntologyAnnotation(ont, anno));
        assertTrue(ont.containsAnnotationPropertyInSignature(anno.getProperty().getIRI()));
        assertTrue(ont.getAnnotationPropertiesInSignature().contains(anno.getProperty()));
    }


}
