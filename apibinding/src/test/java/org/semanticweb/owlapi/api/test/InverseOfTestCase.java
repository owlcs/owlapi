package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 */
public class InverseOfTestCase extends AbstractFileRoundTrippingTestCase {


    public void testContains() {
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLObjectProperty propQ = getOWLObjectProperty("q");
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLInverseObjectPropertiesAxiom(propP, propQ));
        assertEquals(axioms, getOnt().getAxioms());
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
    }

    protected String getFileName() {
        return "InverseOf.rdf";
    }
}
