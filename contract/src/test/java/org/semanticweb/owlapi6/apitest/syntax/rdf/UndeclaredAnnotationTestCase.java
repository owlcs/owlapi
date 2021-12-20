package org.semanticweb.owlapi6.apitest.syntax.rdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OntologyConfigurator;

/**
 * Created by ses on 3/10/14.
 */
class UndeclaredAnnotationTestCase extends TestBase {

    @Test
    void testRDFXMLUsingUndeclaredAnnotationProperty() {
        StringDocumentSource documentSource = new StringDocumentSource(
            TestFiles.undeclaredAnnotationProperty, new RDFXMLDocumentFormat());
        OWLOntology oo = loadFrom(documentSource);
        assertTrue(documentSource.getOntologyLoaderMetaData().isPresent());
        assertEquals(0,
            documentSource.getOntologyLoaderMetaData().get().getUnparsedTriples().count());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms =
            asUnorderedSet(oo.axioms(AxiomType.ANNOTATION_ASSERTION));
        assertEquals(2, annotationAxioms.size());
        Set<OWLAnonymousIndividual> anonymousIndividualSet =
            asUnorderedSet(oo.anonymousIndividuals());
        assertEquals(1, anonymousIndividualSet.size());
        OWLAnonymousIndividual anon = anonymousIndividualSet.iterator().next();
        OWLAnnotationAssertionAxiom relAx = AnnotationAssertion(ANNPROPS.AP, IRIS.iriTest, anon);
        OWLAnnotationAssertionAxiom predAx =
            AnnotationAssertion(ANNPROPS.propP, anon, Literal("Not visible", ""));
        assertTrue(annotationAxioms.contains(relAx));
        assertTrue(annotationAxioms.contains(predAx));
    }

    @Test
    void testTurtleUsingUndeclaredAnnotationProperty() {
        OWLOntology o =
            loadFrom(TestFiles.undeclaredAnnotationPropertyTurtle, new TurtleDocumentFormat());
        AtomicInteger countLabels = new AtomicInteger();
        AtomicInteger countPreds = new AtomicInteger();
        AtomicInteger countBNodeAnnotations = new AtomicInteger();
        o.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(oa -> {
            if (oa.getProperty().equals(RDFSLabel())) {
                countLabels.incrementAndGet();
            }
            if (oa.getProperty().equals(ANNPROPS.pred)) {
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
        OWLOntology o = loadWithConfig(
            new StringDocumentSource(TestFiles.error1OnStrictParsing, new TurtleDocumentFormat()),
            new OntologyConfigurator().setStrict(true));
        assertEquals(0, o.getLogicalAxiomCount());
    }
}
