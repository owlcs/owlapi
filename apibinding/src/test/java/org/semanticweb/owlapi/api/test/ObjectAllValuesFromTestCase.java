package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class ObjectAllValuesFromTestCase extends AbstractFileRoundTrippingTestCase {


    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        axioms.add(getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLObjectAllValuesFrom(propP, clsB)));
        axioms.add(getFactory().getOWLDeclarationAxiom(clsB));
        axioms.add(getFactory().getOWLDeclarationAxiom(propP));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    protected String getFileName() {
        return "ObjectAllValuesFrom.rdf";
    }
}
