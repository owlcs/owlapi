package org.semanticweb.owlapi6.apitest.syntax.rdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OntologyConfigurator;

/**
 * Created by ses on 3/10/14.
 */
public class UndeclaredAnnotationTestCase extends TestBase {

    @Test
    public void testRDFXMLUsingUndeclaredAnnotationProperty() throws OWLOntologyCreationException {
        StringDocumentSource documentSource = new StringDocumentSource(TestFiles.undeclaredAnnotationProperty,
            "uri:owlapi:ontology", new RDFXMLDocumentFormat(), null);
        OWLOntology oo = loadOntologyFromString(documentSource);
        assertTrue(documentSource.getOntologyLoaderMetaData().isPresent());
        assertEquals(0, documentSource.getOntologyLoaderMetaData().get().getUnparsedTriples().count());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms = asUnorderedSet(oo.axioms(AxiomType.ANNOTATION_ASSERTION));
        assertEquals(2, annotationAxioms.size());
        OWLAnnotationProperty relProperty = df.getOWLAnnotationProperty("http://example.com/ns#", "rel");
        OWLAnnotationProperty predProperty = df.getOWLAnnotationProperty("http://example.com/ns#", "pred");
        Set<OWLAnonymousIndividual> anonymousIndividualSet = asUnorderedSet(oo.anonymousIndividuals());
        assertEquals("should be one anonymous individual", 1, anonymousIndividualSet.size());
        OWLAnonymousIndividual anonymousIndividual = anonymousIndividualSet.iterator().next();
        OWLAnnotationAssertionAxiom relAx = df.getOWLAnnotationAssertionAxiom(relProperty,
            df.getIRI("http://example.com/ns#", "test"), anonymousIndividual);
        OWLLiteral notVisible = df.getOWLLiteral("Not visible", "");
        OWLAnnotationAssertionAxiom predAx = df.getOWLAnnotationAssertionAxiom(predProperty, anonymousIndividual,
            notVisible);
        assertTrue("should contain relax", annotationAxioms.contains(relAx));
        assertTrue("should contain predax", annotationAxioms.contains(predAx));
    }

    @Test
    public void testTurtleUsingUndeclaredAnnotationProperty() {
        OWLOntology o = loadOntologyFromString(TestFiles.undeclaredAnnotationPropertyTurtle,
            new TurtleDocumentFormat());
        OWLAnnotationProperty pred = df.getOWLAnnotationProperty("http://www.example.org/", "pred");
        AtomicInteger countLabels = new AtomicInteger();
        AtomicInteger countPreds = new AtomicInteger();
        AtomicInteger countBNodeAnnotations = new AtomicInteger();
        o.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(oa -> {
            if (oa.getProperty().equals(df.getRDFSLabel())) {
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
    public void shouldThrowAnExceptionOnError1AndStrictParsing() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyWithConfig(
            new StringDocumentSource(TestFiles.error1OnStrictParsing, new TurtleDocumentFormat()),
            new OntologyConfigurator().setStrict(true));
        assertEquals(0, o.getLogicalAxiomCount());
    }
}
