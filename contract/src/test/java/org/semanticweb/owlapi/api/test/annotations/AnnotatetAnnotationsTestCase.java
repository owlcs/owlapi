package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

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
import org.semanticweb.owlapi.model.OWLOntology;

class AnnotatetAnnotationsTestCase extends TestBase {

    static final String ns = "urn:n:a#";

    @Test
    void shouldRoundtripMultipleNestedAnnotationsdebug() {
        OWLAnnotation c3 = RDFSComment(LIT_THREE);
        OWLAnnotation c4 = RDFSComment(LIT_FOUR);
        OWLAnnotation c1 = RDFSLabel(LIT_ONE, c3);
        OWLAnnotation c2 = RDFSLabel(LIT_TWO, c4);
        OWLOntology ont = loadFrom(TestFiles.nestedAnnotations, new RDFXMLDocumentFormat());
        assertEquals(asList(ont.logicalAxioms()), l(ObjectPropertyAssertion(l(c1, c2), op1, I, J)));
    }

    @Test
    void shouldLoadAnnotatedannotationsCorrectly() {
        IRI subj = iri("http://purl.obolibrary.org/obo/", "UBERON_0000033");
        OWLOntology testcase = loadFrom(TestFiles.annotatedAnnotation, new RDFXMLDocumentFormat());
        long before = testcase.annotationAssertionAxioms(subj).count();
        OWLOntology result = roundTrip(testcase, new RDFXMLDocumentFormat());
        long after = result.annotationAssertionAxioms(subj).count();
        assertEquals(before, after);
    }

    @Test
    void shouldRecognizeAnnotationsOnAxiomsWithDifferentannotationsAsDistinct() {
        OWLAnnotationAssertionAxiom ax1 =
            AnnotationAssertion(AP, I.getIRI(), LITVALUE, RDFSLabel(LITVALUE1));
        OWLAnnotationAssertionAxiom ax2 =
            AnnotationAssertion(AP, I.getIRI(), LITVALUE, RDFSLabel(LITVALUE2));
        Set<OWLAnnotationAssertionAxiom> set = new TreeSet<>();
        set.add(ax1);
        set.add(ax2);
        assertEquals(2, set.size());
    }

    @Test
    void shouldAnnotateOntologyAnnotations() {
        IRI create = iri("urn:test:", "onto");
        OWLOntology o = create(create);
        OWLAnnotation a2 = Annotation(propP, LITVALUE2, RDFSLabel("nested ontology annotation"));
        OWLAnnotation a1 = Annotation(propQ, LITVALUE1, a2);
        o.applyChange(new AddOntologyAnnotation(o, a1));
        OWLAnnotation a3 = Annotation(propP, LITVALUE3, RDFSLabel("nested ontology annotation 1"));
        OWLAnnotation a4 = Annotation(propQ, iri("p5"), a3);
        o.applyChange(new AddOntologyAnnotation(o, a4));
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        equal(o, o1);
    }
}
