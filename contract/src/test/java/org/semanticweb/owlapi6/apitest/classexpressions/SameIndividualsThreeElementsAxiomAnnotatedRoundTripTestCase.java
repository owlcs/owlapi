package org.semanticweb.owlapi6.apitest.classexpressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.rioformats.NQuadsDocumentFormatFactory;
import org.semanticweb.owlapi6.rioformats.NTriplesDocumentFormatFactory;
import org.semanticweb.owlapi6.rioformats.RDFJsonDocumentFormatFactory;
import org.semanticweb.owlapi6.rioformats.RioRDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormatFactory;
import org.semanticweb.owlapi6.rioformats.TrigDocumentFormatFactory;

class SameIndividualsThreeElementsAxiomAnnotatedRoundTripTestCase extends TestBase {

    protected OWLOntology createOntology() {
        OWLAnnotation anno1 = Annotation(ANNPROPS.AP, LITERALS.val);
        OWLAnnotation anno2 = Annotation(ANNPROPS.propP, LITERALS.val);
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = SameIndividual(INDIVIDUALS.I, INDIVIDUALS.J, INDIVIDUALS.indA);
        axioms.add(ax.getAnnotatedAxiom(l(anno1, anno2)));
        axioms.add(ax.getAnnotatedAxiom(l(anno1)));
        axioms.add(ax.getAnnotatedAxiom(l(anno2)));
        return o(axioms);
    }

    @ParameterizedTest
    @MethodSource("formatList")
    void testSyntaxWithoutNAryAxioms(OWLDocumentFormatFactory format) {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        OWLOntology ont = createOntology();
        OWLOntology o = loadFrom(saveOntology(ont, format.createFormat()), format.createFormat());
        assertEquals(6, o.getAxiomCount(AxiomType.SAME_INDIVIDUAL));
        ont.axioms(AxiomType.SAME_INDIVIDUAL).map(OWLSameIndividualAxiom::splitToAnnotatedPairs)
            .forEach(axs -> axs.forEach(a -> assertTrue(o.containsAxiom(a))));
    }

    static Stream<OWLDocumentFormatFactory> formatList() {
        return Stream.of(new RDFXMLDocumentFormatFactory(), new RioRDFXMLDocumentFormatFactory(),
            new TrigDocumentFormatFactory(), new RDFJsonDocumentFormatFactory(),
            new NTriplesDocumentFormatFactory(), new RDFJsonDocumentFormatFactory(),
            new NQuadsDocumentFormatFactory(), new TurtleDocumentFormatFactory(),
            new RioTurtleDocumentFormatFactory());
    }
}
