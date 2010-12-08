package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Apr-2009
 */
public class DataMinCardinalityTestCase extends AbstractFileRoundTrippingTestCase {

    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass clsA = getOWLClass("A");
        OWLDataProperty prop = getOWLDataProperty("p");
        axioms.add(getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLDataMinCardinality(3, prop)));
        axioms.add(getFactory().getOWLDeclarationAxiom(prop));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    protected String getFileName() {
        return "DataMinCardinality.rdf";
    }
}
