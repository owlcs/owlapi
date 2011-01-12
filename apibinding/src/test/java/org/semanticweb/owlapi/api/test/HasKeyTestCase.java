package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class HasKeyTestCase extends AbstractFileRoundTrippingTestCase {


    public void testCorrectAxioms() {
        OWLClass cls = getOWLClass("A");
        OWLDataProperty propP = getOWLDataProperty("p");
        OWLDataProperty propQ = getOWLDataProperty("q");
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLHasKeyAxiom(cls, propP, propQ));
        assertEquals(axioms, getOnt().getAxioms());
    }

    @Override  @SuppressWarnings("unused")
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    @Override
	protected String getFileName() {
        return "HasKey.rdf";
    }
}
