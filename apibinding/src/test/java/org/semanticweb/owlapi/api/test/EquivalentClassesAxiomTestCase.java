package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 12-Oct-2008<br><br>
 */
public class EquivalentClassesAxiomTestCase extends AbstractOWLAPITestCase {

    public void testContainsNamedClass() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLClassExpression desc2 = getFactory().getOWLObjectSomeValuesFrom(propP, clsA);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clsA, desc);
        assertTrue(ax.containsNamedEquivalentClass());
        OWLEquivalentClassesAxiom ax2 = getFactory().getOWLEquivalentClassesAxiom(desc, desc2);
        assertFalse(ax2.containsNamedEquivalentClass());
    }


    public void testGetNamedClasses() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clsA, desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertEquals(clses.size(), 1);
        assertTrue(clses.contains(clsA));
    }

    public void testGetNamedClassesWithNothing() {
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLNothing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLThing());
        assertTrue(ax.containsOWLNothing());
    }

    public void testGetNamedClassesWithThing() {
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLThing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLNothing());
        assertTrue(ax.containsOWLThing());
    }

    public void testSplit() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        Set<OWLClass> clses = new HashSet<OWLClass>();
        clses.add(clsA);
        clses.add(clsB);
        clses.add(clsC);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clses);
        Set<OWLSubClassOfAxiom> scas = ax.asOWLSubClassOfAxioms();
        assertEquals(scas.size(), 6);
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsA, clsB)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsB, clsA)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsA, clsC)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsC, clsA)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsB, clsC)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsC, clsB)));
    }
}
