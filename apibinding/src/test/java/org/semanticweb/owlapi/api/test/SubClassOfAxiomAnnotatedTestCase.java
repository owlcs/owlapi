package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-May-2009
 */
public class SubClassOfAxiomAnnotatedTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @Override
	protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        return getFactory().getOWLSubClassOfAxiom(clsA, clsB, annos);
    }
}
