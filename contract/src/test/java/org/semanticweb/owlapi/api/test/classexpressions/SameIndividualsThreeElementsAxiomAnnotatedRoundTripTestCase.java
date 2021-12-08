package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormatFactory;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TrigDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

class SameIndividualsThreeElementsAxiomAnnotatedRoundTripTestCase extends TestBase {

    protected OWLOntology createOntology() {
        OWLAnnotation anno1 = Annotation(AP, val);
        OWLAnnotation anno2 = Annotation(propP, val);
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = SameIndividual(I, J, indA);
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
