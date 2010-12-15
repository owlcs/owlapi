package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Apr-2009
 */
public class DataHasValueTestCase extends AbstractFileRoundTrippingTestCase {

    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass clsA = getOWLClass("A");
        OWLLiteral literal = getFactory().getOWLLiteral(3);
        OWLLiteral stringLiteral = getFactory().getOWLLiteral("A", "");
        OWLDataProperty propP = getOWLDataProperty("p");
        axioms.add(getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLDataHasValue(propP, literal)));
        axioms.add(getFactory().getOWLDeclarationAxiom(propP));
        axioms.add(getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLDataHasValue(propP, stringLiteral)));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    @Override
	protected String getFileName() {
        return "DataHasValue.rdf";
    }

    @Override
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }
}
