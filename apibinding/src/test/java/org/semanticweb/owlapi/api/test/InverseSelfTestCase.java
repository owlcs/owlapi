package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class InverseSelfTestCase extends AbstractOWLAPITestCase {


    public void testInverse() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLObjectProperty propQ = getOWLObjectProperty("q");
        OWLDataFactory df = getFactory();
        OWLAxiom ax = df.getOWLInverseObjectPropertiesAxiom(propP, propQ);
        getManager().addAxiom(ont, ax);
        assertTrue(propP.getInverses(ont).contains(propQ));
        assertFalse(propP.getInverses(ont).contains(propP));
    }

    public void testInverseSelf() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLDataFactory df = getFactory();
        OWLAxiom ax = df.getOWLInverseObjectPropertiesAxiom(propP, propP);
        getManager().addAxiom(ont, ax);
        assertTrue(propP.getInverses(ont).contains(propP));
    }
}
