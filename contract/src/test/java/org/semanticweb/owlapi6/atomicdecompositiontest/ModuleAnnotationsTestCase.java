package org.semanticweb.owlapi6.atomicdecompositiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.modularity.ModuleType;
import org.semanticweb.owlapi6.modularity.SyntacticLocalityModuleExtractor;

class ModuleAnnotationsTestCase extends TestBase {

    OWLDeclarationAxiom dA = Declaration(ANNPROPS.AP);
    OWLDeclarationAxiom dR = Declaration(ANNPROPS.replaced);
    OWLDeclarationAxiom dB = Declaration(ANNPROPS.propP);
    OWLAnnotationAssertionAxiom ax =
        AnnotationAssertion(ANNPROPS.replaced, ANNPROPS.AP.getIRI(), ANNPROPS.propP.getIRI());
    Set<OWLEntity> entities = set(ANNPROPS.AP);

    @Test
    void shouldNotAddAnnotations() {
        Set<OWLAxiom> expected = new HashSet<>();
        OWLOntology o = create(iri("urn:test:", "noanns"));
        o.getOntologyConfigurator().withSkipModuleAnnotations(true);
        o.add(dR, dA, dB, ax);
        Set<OWLAxiom> module =
            new SyntacticLocalityModuleExtractor(m, o, ModuleType.STAR).extract(entities);
        assertEquals(expected, module);
    }

    @Test
    void shouldAddAnnotations() {
        Set<OWLAxiom> expected = new HashSet<>();
        expected.add(ax);
        expected.add(dA);
        OWLOntology o = create(iri("urn:test:", "anns"));
        o.add(dR, dA, dB, ax);
        Set<OWLAxiom> module =
            new SyntacticLocalityModuleExtractor(m, o, ModuleType.STAR).extract(entities);
        assertEquals(expected, module);
    }
}
