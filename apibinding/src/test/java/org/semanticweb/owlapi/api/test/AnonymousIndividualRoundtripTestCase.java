package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
