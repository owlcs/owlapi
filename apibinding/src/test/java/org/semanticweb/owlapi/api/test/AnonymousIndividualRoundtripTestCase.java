package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class AnonymousIndividualRoundtripTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnonymousIndividual ind = getFactory().getOWLAnonymousIndividual();
        OWLClass cls = getOWLClass("A");
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLAnnotationAssertionAxiom ax = getFactory().getOWLAnnotationAssertionAxiom(prop, cls.getIRI(), ind);
        return Collections.singleton(ax);
    }
}
