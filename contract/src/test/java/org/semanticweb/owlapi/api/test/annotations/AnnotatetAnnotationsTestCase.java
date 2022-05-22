package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class AnnotatetAnnotationsTestCase extends TestBase {

    private String ns = "urn:n:a#";
    private OWLObjectProperty r = df.getOWLObjectProperty(ns, "r");

    @Test
    public void shouldRoundtripMultipleNestedAnnotationsdebug() {
        OWLNamedIndividual a = df.getOWLNamedIndividual(ns, "a");
        OWLNamedIndividual b = df.getOWLNamedIndividual(ns, "b");
        OWLLiteral _1 = df.getOWLLiteral(1);
        OWLLiteral _3 = df.getOWLLiteral(3);
        OWLLiteral _2 = df.getOWLLiteral(2);
        OWLLiteral _4 = df.getOWLLiteral(4);
        OWLAnnotation c3 = df.getRDFSComment(_3);
        OWLAnnotation c4 = df.getRDFSComment(_4);
        OWLAnnotation c1 = df.getOWLAnnotation(df.getRDFSLabel(), _1, c3);
        OWLAnnotation c2 = df.getOWLAnnotation(df.getRDFSLabel(), _2, c4);
        OWLObjectPropertyAssertionAxiom ax = df.getOWLObjectPropertyAssertionAxiom(r, a, b, Arrays.asList(c1, c2));
        List<OWLAxiom> axioms = Arrays.asList(ax);
        OWLOntology ont = loadOntologyFromString(TestFiles.nestedAnnotations, new RDFXMLDocumentFormat());
        assertEquals(axioms, asList(ont.logicalAxioms()));
    }

    @Test
    public void shouldLoadAnnotatedannotationsCorrectly() throws OWLOntologyStorageException {
        IRI subject = df.getIRI("http://purl.obolibrary.org/obo/", "UBERON_0000033");
        OWLOntology testcase = loadOntologyFromString(TestFiles.annotatedAnnotation, new RDFXMLDocumentFormat());
        long before = testcase.annotationAssertionAxioms(subject).count();
        OWLOntology result = roundTrip(testcase, new RDFXMLDocumentFormat());
        long after = result.annotationAssertionAxioms(subject).count();
        assertEquals(before, after);
    }

    @Test
    public void shouldRecognizeAnnotationsOnAxiomsWithDifferentannotationsAsDistinct() {
        OWLAnnotationProperty p = AnnotationProperty(iri("p"));
        OWLAnnotationSubject i = iri("i");
        OWLLiteral v = Literal("value");
        OWLLiteral ann1 = Literal("value1");
        OWLLiteral ann2 = Literal("value2");
        OWLAnnotationAssertionAxiom ax1 = AnnotationAssertion(p, i, v, singleton(Annotation(RDFSLabel(), ann1)));
        OWLAnnotationAssertionAxiom ax2 = AnnotationAssertion(p, i, v, singleton(Annotation(RDFSLabel(), ann2)));
        Set<OWLAnnotationAssertionAxiom> set = new TreeSet<>();
        set.add(ax1);
        set.add(ax2);
        assertEquals(2, set.size());
    }

    @Test
    public void shouldAnnotateOntologyAnnotations() throws OWLOntologyCreationException, OWLOntologyStorageException {
        IRI create = df.getIRI("urn:test:onto");
        OWLOntology o = m.createOntology(create);
        OWLAnnotation a2 = df.getOWLAnnotation(AnnotationProperty(iri("p2")), Literal("value2"),
            df.getRDFSLabel("nested ontology annotation"));
        OWLAnnotation a1 = df.getOWLAnnotation(AnnotationProperty(iri("p1")), Literal("value1"), a2);
        o.applyChange(new AddOntologyAnnotation(o, a1));
        OWLAnnotation a3 = df.getOWLAnnotation(AnnotationProperty(iri("p2")), Literal("value3"),
            df.getRDFSLabel("nested ontology annotation 1"));
        OWLAnnotation a4 = df.getOWLAnnotation(AnnotationProperty(iri("p1")), iri("p5"), a3);
        o.applyChange(new AddOntologyAnnotation(o, a4));
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        equal(o, o1);
    }
}
