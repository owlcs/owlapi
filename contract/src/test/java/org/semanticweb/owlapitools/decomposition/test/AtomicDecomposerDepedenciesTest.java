package org.semanticweb.owlapitools.decomposition.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.DF;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.atomicdecomposition.Atom;
import uk.ac.manchester.cs.atomicdecomposition.AtomicDecomposition;
import uk.ac.manchester.cs.atomicdecomposition.AtomicDecompositionImpl;

class AtomicDecomposerDepedenciesTest {

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
        o.addAxiom(DF.SubClassOf(DF.powerYoga, DF.yoga));
        o.addAxiom(DF.SubClassOf(DF.yoga, DF.relaxation));
        o.addAxiom(DF.SubClassOf(DF.relaxation, DF.activity));
        return o;
    }
}
