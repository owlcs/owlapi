package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class ObjectOneOfTestCase extends AbstractFileRoundTrippingTestCase {


     public void testCorrectAxioms() {
         Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
         OWLClass clsA = getOWLClass("A");
         OWLNamedIndividual indA = getOWLIndividual("a");
         OWLNamedIndividual indB = getOWLIndividual("b");
         axioms.add(getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLObjectOneOf(indA, indB)));
         assertEquals(getOnt().getAxioms(), axioms);
    }

    @Override
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    @Override
	protected String getFileName() {
        return "ObjectOneOf.rdf";
    }
}
