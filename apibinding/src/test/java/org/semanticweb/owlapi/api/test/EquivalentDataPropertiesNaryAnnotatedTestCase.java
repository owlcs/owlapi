package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 25-Nov-2009
 */
public class EquivalentDataPropertiesNaryAnnotatedTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @Override@SuppressWarnings("unused")
	protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        return getFactory().getOWLEquivalentDataPropertiesAxiom(getOWLDataProperty("p"), getOWLDataProperty("q"), getOWLDataProperty("r"));
    }

    @Override
    public void testRDFXML() throws Exception {
        // Can't serialise nary equivalent data properties in RDF
    }

    @Override
    public void testTurtle() throws Exception {
        // Can't serialise nary equivalent data properties in RDF
    }
}
