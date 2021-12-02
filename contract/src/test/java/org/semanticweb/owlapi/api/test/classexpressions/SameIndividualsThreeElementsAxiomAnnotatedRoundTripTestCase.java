package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;

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
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

class SameIndividualsThreeElementsAxiomAnnotatedRoundTripTestCase extends TestBase {

    protected OWLOntology createOntology() {
        OWLLiteral lit = Literal("Test", "");
        OWLAnnotation anno1 = Annotation(AP, lit);
        OWLAnnotation anno2 = Annotation(propP, lit);
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = df.getOWLSameIndividualAxiom(set(I, J, indA));
        axioms.add(ax.getAnnotatedAxiom(set(anno1, anno2)));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno1)));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno2)));
        axioms.add(df.getOWLDeclarationAxiom(AP));
        axioms.add(df.getOWLDeclarationAxiom(propP));
        OWLOntology ont = create();
        ont.getOWLOntologyManager().addAxioms(ont, axioms);
        return ont;
    }

    @ParameterizedTest
    @MethodSource("formatList")
    void testSyntaxWithoutNAryAxioms(OWLDocumentFormatFactory format) {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        OWLOntology ont = createOntology();
        OWLOntology o =
            loadOntologyFromString(saveOntology(ont, format.createFormat()), format.createFormat());
        assertEquals(6, o.getAxiomCount(AxiomType.SAME_INDIVIDUAL));
        ont.getAxioms(AxiomType.SAME_INDIVIDUAL).stream()
            .map(OWLSameIndividualAxiom::splitToAnnotatedPairs)
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
