package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class DataOneOfTestCase extends AbstractFileRoundTrippingTestCase {

    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLDataRange oneOf = getFactory().getOWLDataOneOf(
                getFactory().getOWLLiteral(30),
                getFactory().getOWLLiteral(31f)
        );
        OWLDataProperty p = getOWLDataProperty("p");
        OWLDataPropertyRangeAxiom ax = getFactory().getOWLDataPropertyRangeAxiom(p, oneOf);
        axioms.add(ax);
        axioms.add(getFactory().getOWLDeclarationAxiom(p));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    protected String getFileName() {
        return "DataOneOf.rdf";
    }
}
