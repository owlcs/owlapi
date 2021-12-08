package org.semanticweb.owlapi.atomicdecomposition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

class ModuleAnnotationsTestCase extends TestBase {

    static final OWLAnnotationProperty replaced =
        AnnotationProperty(iri("urn:test:", "term_replaced_by"));
    OWLDeclarationAxiom dA = Declaration(AP);
    OWLDeclarationAxiom dR = Declaration(replaced);
    OWLDeclarationAxiom dB = Declaration(propP);
    OWLAnnotationAssertionAxiom ax = AnnotationAssertion(replaced, AP.getIRI(), propP.getIRI());
    Set<OWLEntity> entities = set(AP);

    @Test
    void shouldNotAddAnnotations() {
        Set<OWLAxiom> expected = new HashSet<>();
        IRI iri = iri("urn:test:", "noanns");
        OWLOntology o = create(iri);
        o.add(dR, dA, dB, ax);
        Set<OWLAxiom> module = new SyntacticLocalityModuleExtractor(m,
            m.getOntologyLoaderConfiguration().withSkipModuleAnnotations(true),
            Stream.of(dR, dA, dB, ax), uk.ac.manchester.cs.owlapi.modularity.ModuleType.STAR, false)
                .extract(entities);
        assertEquals(expected, module);
    }

    @Test
    void shouldAddAnnotations() {
        Set<OWLAxiom> expected = new HashSet<>();
        expected.add(ax);
        expected.add(dA);
        IRI iri = iri("urn:test:", "anns");
        OWLOntology o = create(iri);
        Set<OWLAxiom> axioms = set(dR, dA, dB, ax);
        o.add(axioms);
        Set<OWLAxiom> module = new SyntacticLocalityModuleExtractor(m, axioms.stream(),
            uk.ac.manchester.cs.owlapi.modularity.ModuleType.STAR, false).extract(entities);
        assertEquals(expected, module);
    }
}
