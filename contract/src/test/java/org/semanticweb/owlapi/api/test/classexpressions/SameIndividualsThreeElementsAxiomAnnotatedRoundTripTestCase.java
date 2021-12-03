package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.util.Arrays;
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
        OWLAxiom ax = df.getOWLSameIndividualAxiom(Arrays.asList(I, J, indA));
        axioms.add(ax.getAnnotatedAxiom(Stream.of(anno1, anno2)));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno1)));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno2)));
        OWLOntology ont = create();
        ont.add(axioms);
        ont.unsortedSignature().filter(e -> !e.isBuiltIn() && !ont.isDeclared(e, INCLUDED))
            .forEach(e -> ont.add(Declaration(e)));
        return ont;
    }

    @ParameterizedTest
    @MethodSource("formatList")
    void testSyntaxWithoutNAryAxioms(OWLDocumentFormatFactory f) {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        OWLOntology ont = createOntology();
        OWLOntology o =
            loadOntologyFromString(saveOntology(ont, f.createFormat()), f.createFormat());
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
