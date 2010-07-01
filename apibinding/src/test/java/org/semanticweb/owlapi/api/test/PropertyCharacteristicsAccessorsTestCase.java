package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class PropertyCharacteristicsAccessorsTestCase extends AbstractOWLAPITestCase {

    public void testTransitive() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isTransitive(ont));
        OWLAxiom ax = getFactory().getOWLTransitiveObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isTransitive(ont));
    }
    
    public void testSymmetric() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isSymmetric(ont));
        OWLAxiom ax = getFactory().getOWLSymmetricObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isSymmetric(ont));
    }
    
    public void testAsymmetric() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isAsymmetric(ont));
        OWLAxiom ax = getFactory().getOWLAsymmetricObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isAsymmetric(ont));
    }
    
    public void testReflexive() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isReflexive(ont));
        OWLAxiom ax = getFactory().getOWLReflexiveObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isReflexive(ont));
    }
    
    public void testIrreflexive() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isIrreflexive(ont));
        OWLAxiom ax = getFactory().getOWLIrreflexiveObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isIrreflexive(ont));
    }
    
    public void testFunctional() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isFunctional(ont));
        OWLAxiom ax = getFactory().getOWLFunctionalObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isFunctional(ont));
    }
    
    public void testInverseFunctional() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        assertFalse(prop.isInverseFunctional(ont));
        OWLAxiom ax = getFactory().getOWLInverseFunctionalObjectPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isInverseFunctional(ont));
    }
    
    public void testFunctionalDataProperty() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLDataProperty prop = getOWLDataProperty("prop");
        assertFalse(prop.isFunctional(ont));
        OWLAxiom ax = getFactory().getOWLFunctionalDataPropertyAxiom(prop);
        getManager().addAxiom(ont, ax);
        assertTrue(prop.isFunctional(ont));
    }
}
