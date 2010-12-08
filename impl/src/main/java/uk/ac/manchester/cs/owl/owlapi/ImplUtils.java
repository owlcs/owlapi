package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Mar-2007<br><br>
 */
public class ImplUtils {

    public static Set<OWLAnnotationAssertionAxiom> getAnnotationAxioms(OWLEntity entity, Set<OWLOntology> ontologies) {
        Set<OWLAnnotationAssertionAxiom> result = new HashSet<OWLAnnotationAssertionAxiom>();
        for (OWLOntology ont : ontologies) {
            result.addAll(ont.getAnnotationAssertionAxioms(entity.getIRI()));
        }
        return result;
    }

    public static Set<OWLAnnotation> getAnnotations(OWLEntity entity, Set<OWLOntology> ontologies) {
        Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
        for (OWLAnnotationAssertionAxiom ax : getAnnotationAxioms(entity, ontologies)) {
            result.add(ax.getAnnotation());
        }
        return result;
    }

    public static Set<OWLAnnotation> getAnnotations(OWLEntity entity, OWLAnnotationProperty annotationProperty, Set<OWLOntology> ontologies) {
        Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
        for (OWLAnnotationAssertionAxiom ax : getAnnotationAxioms(entity, ontologies)) {
            if (ax.getAnnotation().getProperty().equals(annotationProperty)) {
                result.add(ax.getAnnotation());
            }
        }
        return result;
    }
}
