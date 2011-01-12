package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class OWLSubAnnotationPropertyOfAxiomTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLDataFactory df = getFactory();
        OWLAnnotationProperty subProp = df.getOWLAnnotationProperty(IRI.create("http://ont.com#myLabel"));
        OWLAnnotationProperty superProp = df.getRDFSLabel();
        OWLAxiom ax = df.getOWLSubAnnotationPropertyOfAxiom(subProp, superProp);
        return Collections.singleton(ax);
    }
}
