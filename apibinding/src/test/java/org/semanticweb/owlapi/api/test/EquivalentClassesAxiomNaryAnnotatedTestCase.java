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
public class EquivalentClassesAxiomNaryAnnotatedTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @Override
	protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        return getFactory().getOWLEquivalentClassesAxiom(getOWLClass("A"), getOWLClass("B"), getOWLClass("C"), getOWLClass("D"));
    }

    @Override
    public void testRDFXML() throws Exception {
        // Not supported in RDF.  Only binary equivalent classes axioms can be saved in RDF representations
    }

    @Override
    public void testTurtle() throws Exception {
        // Not supported in RDF.  Only binary equivalent classes axioms can be saved in RDF representations
    }
}
