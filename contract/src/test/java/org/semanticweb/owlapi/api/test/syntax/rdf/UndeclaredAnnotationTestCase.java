package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * Created by ses on 3/10/14.
 */
class UndeclaredAnnotationTestCase extends TestBase {

    @Test
    void testRDFXMLUsingUndeclaredAnnotationProperty() {
        OWLOntology oo =
            loadFrom(TestFiles.undeclaredAnnotationProperty, new RDFXMLDocumentFormat());
        RDFXMLDocumentFormat format = (RDFXMLDocumentFormat) oo.getNonnullFormat();
        assertTrue(format.getOntologyLoaderMetaData().isPresent());
        assertEquals(0, format.getOntologyLoaderMetaData().get().getUnparsedTriples().count());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms =
            asUnorderedSet(oo.axioms(AxiomType.ANNOTATION_ASSERTION));
        assertEquals(2, annotationAxioms.size());
        Set<OWLAnonymousIndividual> anonymousIndividualSet =
            asUnorderedSet(oo.anonymousIndividuals());
        assertEquals(1, anonymousIndividualSet.size());
        OWLAnonymousIndividual anon = anonymousIndividualSet.iterator().next();
        OWLAnnotationAssertionAxiom relAx = AnnotationAssertion(AP, iriTest, anon);
        OWLAnnotationAssertionAxiom predAx =
            AnnotationAssertion(propP, anon, Literal("Not visible", ""));
        assertTrue(annotationAxioms.contains(relAx));
        assertTrue(annotationAxioms.contains(predAx));
    }

    @Test
    void testTurtleUsingUndeclaredAnnotationProperty() {
        OWLOntology o =
            loadFrom(TestFiles.undeclaredAnnotationPropertyTurtle, new TurtleDocumentFormat());
        OWLAnnotationProperty pred = AnnotationProperty(iri("http://www.example.org/", "pred"));
        AtomicInteger countLabels = new AtomicInteger();
        AtomicInteger countPreds = new AtomicInteger();
        AtomicInteger countBNodeAnnotations = new AtomicInteger();
        o.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(oa -> {
            if (oa.getProperty().equals(RDFSLabel())) {
                countLabels.incrementAndGet();
            }
            if (oa.getProperty().equals(pred)) {
                countPreds.incrementAndGet();
            }
            if (oa.getSubject() instanceof OWLAnonymousIndividual) {
                countBNodeAnnotations.incrementAndGet();
            }
        });
        assertEquals(3, countPreds.intValue());
        assertEquals(2, countLabels.intValue());
        assertEquals(3, countBNodeAnnotations.intValue());
    }

    @Test
    void shouldThrowAnExceptionOnError1AndStrictParsing() {
        OWLOntology o = loadWithConfig(new StringDocumentSource(TestFiles.error1OnStrictParsing),
            new OWLOntologyLoaderConfiguration().setStrict(true));
        assertEquals(0, o.getLogicalAxiomCount());
    }
}
