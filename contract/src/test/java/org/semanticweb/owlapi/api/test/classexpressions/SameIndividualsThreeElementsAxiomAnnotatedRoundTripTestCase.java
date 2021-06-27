package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
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
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

class SameIndividualsThreeElementsAxiomAnnotatedRoundTripTestCase extends TestBase {


    protected OWLOntology createOntology() {
        Function<Set<OWLAnnotation>, OWLAxiom> f =
            a -> df.getOWLSameIndividualAxiom(Arrays.asList(NamedIndividual(iri("A")),
                NamedIndividual(iri("B")), NamedIndividual(iri("C"))), a);

        OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
        OWLLiteral lit = Literal("Test", "");
        OWLAnnotation anno1 = Annotation(prop, lit);
        OWLAnnotationProperty prop2 = AnnotationProperty(iri("prop2"));
        OWLAnnotation anno2 = Annotation(prop2, lit);
        Set<OWLAnnotation> annos = new HashSet<>(Arrays.asList(anno1, anno2));
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = f.apply(annos);
        axioms.add(ax.getAnnotatedAxiom(annos));
        axioms.add(Declaration(prop));
        axioms.add(Declaration(prop2));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno1)));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno2)));
        OWLOntology ont = getOWLOntology();
        ont.add(axioms);
        ont.unsortedSignature().filter(e -> !e.isBuiltIn() && !ont.isDeclared(e, INCLUDED))
            .forEach(e -> ont.add(Declaration(e)));
        return ont;
    }

    @Test
    public void testRDFXML() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RDFXMLDocumentFormatFactory());
    }

    @Test
    public void testRioRDFXML() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RioRDFXMLDocumentFormatFactory());
    }

    @Test
    public void testTrig() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new TrigDocumentFormatFactory());
    }

    @Test
    public void testRDFJSON() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RDFJsonDocumentFormatFactory());
    }

    @Test
    public void testNTriples() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new NTriplesDocumentFormatFactory());
    }

    @Test
    public void roundTripRDFXMLAndFunctionalShouldBeSame() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        // super.roundTripRDFXMLAndFunctionalShouldBeSame();
    }

    @Test
    public void testJSONLD() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RDFJsonDocumentFormatFactory());
    }

    @Test
    public void testNQuads() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new NQuadsDocumentFormatFactory());
    }

    @Test
    public void testTurtle() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new TurtleDocumentFormatFactory());
    }

    @Test
    public void testRioTurtle() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RioTurtleDocumentFormatFactory());
    }

    protected void sameIndividualAssertion(OWLOntology ont, OWLDocumentFormatFactory f) {
        OWLOntology o =
            loadOntologyFromString(saveOntology(ont, f.createFormat()), f.createFormat());
        assertEquals(3, o.getAxiomCount(AxiomType.SAME_INDIVIDUAL));
        ont.axioms(AxiomType.SAME_INDIVIDUAL).map(OWLSameIndividualAxiom::splitToAnnotatedPairs)
            .forEach(axs -> axs.forEach(a -> assertTrue(o.containsAxiom(a))));
    }
}
