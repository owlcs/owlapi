package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class OWLAnnotationPropertyDomainTestCase extends  AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLDataFactory df = getFactory();
        OWLAnnotationProperty prop = df.getRDFSComment();
        OWLAxiom ax = df.getOWLAnnotationPropertyDomainAxiom(prop, IRI.create("http://ont.com#A"));
        return Collections.singleton(ax);
    }
}
