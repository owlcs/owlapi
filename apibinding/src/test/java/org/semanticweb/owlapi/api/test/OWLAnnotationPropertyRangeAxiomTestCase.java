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
public class OWLAnnotationPropertyRangeAxiomTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLDataFactory df = getFactory();
        OWLAnnotationProperty prop = df.getRDFSComment();
        OWLAxiom ax = df.getOWLAnnotationPropertyRangeAxiom(prop, IRI.create("http://ont.com#A"));
        return Collections.singleton(ax);
    }
}
