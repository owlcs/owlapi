package org.semanticweb.owlapi6.atomicdecompositiontest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.atomicdecomposition.Atom;
import org.semanticweb.owlapi6.atomicdecomposition.AtomicDecomposition;
import org.semanticweb.owlapi6.atomicdecomposition.AtomicDecompositionImpl;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;

public class AtomicDecomposerDepedenciesTest {

    private static final String URN_TEST = "urn:test#";

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
        OWLClass powerYoga = f.getOWLClass(f.getIRI(URN_TEST, "PowerYoga"));
        OWLClass yoga = f.getOWLClass(f.getIRI(URN_TEST, "Yoga"));
        OWLClass relaxation = f.getOWLClass(f.getIRI(URN_TEST, "Relaxation"));
        OWLClass activity = f.getOWLClass(f.getIRI(URN_TEST, "Activity"));
        o.addAxiom(f.getOWLSubClassOfAxiom(powerYoga, yoga));
        o.addAxiom(f.getOWLSubClassOfAxiom(yoga, relaxation));
        o.addAxiom(f.getOWLSubClassOfAxiom(relaxation, activity));
        return o;
    }
}
