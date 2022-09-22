package org.semanticweb.owlapi.atomicdecompositiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.atomicdecomposition.Atom;
import org.semanticweb.owlapi.atomicdecomposition.AtomicDecomposition;
import org.semanticweb.owlapi.atomicdecomposition.AtomicDecompositionImpl;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

class AtomicDecomposerDepedenciesTest extends TestBase {

    @Test
    void atomicDecomposerDepedenciesTest() throws OWLOntologyCreationException {
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
        o.addAxiom(SubClassOf(CLASSES.powerYoga, CLASSES.yoga));
        o.addAxiom(SubClassOf(CLASSES.yoga, CLASSES.relaxation));
        o.addAxiom(SubClassOf(CLASSES.relaxation, CLASSES.activity));
        return o;
    }
}
