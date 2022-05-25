package org.semanticweb.owlapi.atomicdecompositiontest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.atomicdecomposition.Atom;
import org.semanticweb.owlapi.atomicdecomposition.AtomicDecomposition;
import org.semanticweb.owlapi.atomicdecomposition.AtomicDecompositionImpl;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AtomicDecomposerDepedenciesTest {

    @Test
    public void atomicDecomposerDepedenciesTest() throws OWLOntologyCreationException {
        // given
        OWLOntology o = getOntology();
        assertEquals(3, o.getAxiomCount());
        AtomicDecomposition ad = new AtomicDecompositionImpl(o);
        assertEquals(3, ad.getAtoms().size());
        Atom atom = ad.getBottomAtoms().iterator().next();
        assertNotNull(atom);
        // when
        Set<Atom> dependencies = ad.getDependencies(atom, true);
        Set<Atom> dependencies2 = ad.getDependencies(atom, false);
        dependencies2.remove(atom);
        // then
        assertEquals(0, dependencies2.size());
        assertEquals(0, dependencies.size());
    }

    private static OWLOntology getOntology() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o = m.createOntology();
        OWLDataFactory f = m.getOWLDataFactory();
        OWLClass powerYoga = f.getOWLClass(f.getIRI("urn:test#", "PowerYoga"));
        OWLClass yoga = f.getOWLClass(f.getIRI("urn:test#", "Yoga"));
        OWLClass relaxation = f.getOWLClass(f.getIRI("urn:test#", "Relaxation"));
        OWLClass activity = f.getOWLClass(f.getIRI("urn:test#", "Activity"));
        o.addAxiom(f.getOWLSubClassOfAxiom(powerYoga, yoga));
        o.addAxiom(f.getOWLSubClassOfAxiom(yoga, relaxation));
        o.addAxiom(f.getOWLSubClassOfAxiom(relaxation, activity));
        return o;
    }
}
