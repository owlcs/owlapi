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
public class EquivalentObjectPropertiesNaryAnnotatedTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @SuppressWarnings("unused")
	@Override
	protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        return getFactory().getOWLEquivalentObjectPropertiesAxiom(getOWLObjectProperty("p"), getOWLObjectProperty("q"), getOWLObjectProperty("r"));
    }

    @Override
    public void testTurtle() throws Exception {
        // Can't serialise nary equivalent properties axioms in RDF
    }

    @Override
    public void testRDFXML() throws Exception {
        // Can't serialise nary equivalent properties axioms in RDF
    }
}
