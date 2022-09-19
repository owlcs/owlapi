package org.semanticweb.owlapi.atomicdecompositiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.modularity.ModuleType;
import org.semanticweb.owlapi.modularity.SyntacticLocalityModuleExtractor;

class ModuleAnnotationsTestCase extends TestBase {

    private final OWLAnnotationProperty a = df.getOWLAnnotationProperty("urn:test:a");
    private final OWLAnnotationProperty b = df.getOWLAnnotationProperty("urn:test:b");
    private final OWLAnnotationProperty replaced =
        df.getOWLAnnotationProperty("urn:test:term_replaced_by");
    OWLDeclarationAxiom dA = df.getOWLDeclarationAxiom(a);
    OWLDeclarationAxiom dR = df.getOWLDeclarationAxiom(replaced);
    OWLDeclarationAxiom dB = df.getOWLDeclarationAxiom(b);
    OWLAnnotationAssertionAxiom ax =
        df.getOWLAnnotationAssertionAxiom(replaced, a.getIRI(), b.getIRI());
    Set<OWLEntity> e = new HashSet<>(Arrays.asList(a));

    @Test
    void shouldNotAddAnnotations() throws OWLOntologyCreationException {
        Set<OWLAxiom> expected = new HashSet<>();
        OWLOntology o = m.createOntology(df.getIRI("urn:test:noanns"));
        o.getOntologyConfigurator().withSkipModuleAnnotations(true);
        o.add(dR, dA, dB, ax);
        Set<OWLAxiom> module =
            new SyntacticLocalityModuleExtractor(m, o, ModuleType.STAR).extract(e);
        assertEquals(expected, module);
    }

    @Test
    void shouldAddAnnotations() throws OWLOntologyCreationException {
        Set<OWLAxiom> expected = new HashSet<>();
        expected.add(ax);
        expected.add(dA);
        OWLOntology o = m.createOntology(df.getIRI("urn:test:anns"));
        o.add(dR, dA, dB, ax);
        Set<OWLAxiom> module =
            new SyntacticLocalityModuleExtractor(m, o, ModuleType.STAR).extract(e);
        assertEquals(expected, module);
    }
}
