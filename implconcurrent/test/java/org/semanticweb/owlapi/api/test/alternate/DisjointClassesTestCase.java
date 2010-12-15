package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class DisjointClassesTestCase extends AbstractFileRoundTrippingTestCase {
    public void testContains() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        axioms.add(getFactory().getOWLDisjointClassesAxiom(clsA, clsB, clsC));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    @Override
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    @Override
	protected String getFileName() {
        return "DisjointClasses.rdf";
    }
}
