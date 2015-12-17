package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * For axioms with nested annotations, an id needs to be outputted.
 * 
 * @author ignazio
 */
public class OWLAxiomsWithNestedAnnotations implements AxiomAppearance {

    @Override
    public boolean appearsMultipleTimes(OWLAxiom ax) {
        for (OWLAnnotation a : ax.getAnnotations()) {
            if (!a.getAnnotations().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
