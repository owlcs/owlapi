package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

class AnnotatetAnnotationsTestCase extends TestBase {

    static final String ns = "urn:n:a#";

    @Test
    void shouldRoundtripMultipleNestedAnnotationsdebug() {
        OWLObjectProperty r = df.getOWLObjectProperty(iri(ns, "r"));
        OWLNamedIndividual a = df.getOWLNamedIndividual(iri(ns, "a"));
        OWLNamedIndividual b = df.getOWLNamedIndividual(iri(ns, "b"));
        OWLLiteral _1 = df.getOWLLiteral(1);
        OWLLiteral _3 = df.getOWLLiteral(3);
        OWLLiteral _2 = df.getOWLLiteral(2);
        OWLLiteral _4 = df.getOWLLiteral(4);
        OWLAnnotation c3 = df.getOWLAnnotation(df.getRDFSComment(), _3);
        OWLAnnotation c4 = df.getOWLAnnotation(df.getRDFSComment(), _4);
        OWLAnnotation c1 = df.getOWLAnnotation(df.getRDFSLabel(), _1, Collections.singleton(c3));
        OWLAnnotation c2 = df.getOWLAnnotation(df.getRDFSLabel(), _2, Collections.singleton(c4));
        OWLAxiom ax =
            df.getOWLObjectPropertyAssertionAxiom(r, a, b, new HashSet<>(Arrays.asList(c1, c2)));
        Set<OWLAxiom> axioms = Collections.singleton(ax);
        OWLOntology ont =
            loadOntologyFromString(TestFiles.nestedAnnotations, new RDFXMLDocumentFormat());
        assertEquals(axioms, ont.getLogicalAxioms());
    }

    @Test
    void shouldLoadAnnotatedannotationsCorrectly() {
        IRI subject = iri("http://purl.obolibrary.org/obo/", "UBERON_0000033");
        OWLOntology testcase =
            loadOntologyFromString(TestFiles.annotatedAnnotation, new RDFXMLDocumentFormat());
        int before = testcase.getAnnotationAssertionAxioms(subject).size();
        OWLOntology result = roundTrip(testcase);
        int after = result.getAnnotationAssertionAxioms(subject).size();
        assertEquals(before, after);
    }

    @Test
    void shouldRecognizeAnnotationsOnAxiomsWithDifferentannotationsAsDistinct() {
        OWLLiteral v = Literal("value");
        OWLLiteral ann1 = Literal("value1");
        OWLLiteral ann2 = Literal("value2");
        OWLAnnotationAssertionAxiom ax1 =
            AnnotationAssertion(AP, I.getIRI(), v, Annotation(RDFSLabel(), ann1));
        OWLAnnotationAssertionAxiom ax2 =
            AnnotationAssertion(AP, I.getIRI(), v, Annotation(RDFSLabel(), ann2));
        Set<OWLAnnotationAssertionAxiom> set = new TreeSet<>();
        set.add(ax1);
        set.add(ax2);
        assertEquals(2, set.size());
    }

    @Test
    void shouldAnnotateOntologyAnnotations() {
        IRI create = iri("urn:test:", "onto");
        OWLOntology o = create(create);
        OWLAnnotation a2 = df.getOWLAnnotation(propP, Literal("value2"), Collections.singleton(df
            .getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("nested ontology annotation"))));
        OWLAnnotation a1 = df.getOWLAnnotation(propQ, Literal("value1"), Collections.singleton(a2));
        o.getOWLOntologyManager().applyChange(new AddOntologyAnnotation(o, a1));
        OWLAnnotation a3 = df.getOWLAnnotation(propP, Literal("value3"),
            Collections.singleton(df.getOWLAnnotation(df.getRDFSLabel(),
                df.getOWLLiteral("nested ontology annotation 1"))));
        OWLAnnotation a4 = df.getOWLAnnotation(propQ, iri("p5"), Collections.singleton(a3));
        o.getOWLOntologyManager().applyChange(new AddOntologyAnnotation(o, a4));
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        equal(o, o1);
    }
}
