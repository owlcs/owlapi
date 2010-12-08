package org.semanticweb.owlapi.api.test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 25-Nov-2009
 */
public class SubObjectPropertyChainOfAnnotatedTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        List<OWLObjectProperty> props = Arrays.asList(getOWLObjectProperty("p"), getOWLObjectProperty("q"));
        return getFactory().getOWLSubPropertyChainOfAxiom(props, getOWLObjectProperty("r"));
    }
}
