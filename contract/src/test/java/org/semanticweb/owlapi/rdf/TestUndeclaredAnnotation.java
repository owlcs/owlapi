package org.semanticweb.owlapi.rdf;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ses on 3/10/14.
 */
public class TestUndeclaredAnnotation {
    private static Logger logger = LoggerFactory.getLogger(TestUndeclaredAnnotation.class);

    @Test
    public void testRDFXMLUsingUndeclaredAnnotationProperty() throws FileNotFoundException, OWLOntologyCreationException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        DLSyntaxObjectRenderer pp = new DLSyntaxObjectRenderer();

        InputStream is = this.getClass().getResourceAsStream("/undeclared-annotation.rdf.xml");
        OWLOntology oo = manager.loadOntologyFromOntologyDocument(is);
        RDFXMLOntologyFormat format = (RDFXMLOntologyFormat) oo.getOWLOntologyManager().getOntologyFormat(oo);
        assertEquals("Should have no unparsed triples", 0, format.getOntologyLoaderMetaData().getUnparsedTriples().size());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms = oo.getAxioms(AxiomType.ANNOTATION_ASSERTION);
        assertEquals("annotation axiom count should be 2", 2, annotationAxioms.size());
        OWLDataFactory df = manager.getOWLDataFactory();
        IRI TEST_IRI = IRI.create("http://example.com/ns#test");
        IRI REL_IRI = IRI.create("http://example.com/ns#rel");
        IRI PRED_IRI = IRI.create("http://example.com/ns#pred");
        OWLAnnotationProperty relProperty = df.getOWLAnnotationProperty(REL_IRI);
        OWLAnnotationProperty predProperty = df.getOWLAnnotationProperty(PRED_IRI);
        OWLAnonymousIndividual anonymousIndividual = df.getOWLAnonymousIndividual("genid1");
        OWLAnnotationAssertionAxiom relAx = df.getOWLAnnotationAssertionAxiom(relProperty, TEST_IRI, anonymousIndividual);

        OWLLiteral notVisible = df.getOWLLiteral("Not visible","");
        OWLAnnotationAssertionAxiom predAx = df.getOWLAnnotationAssertionAxiom(predProperty, anonymousIndividual, notVisible);
        assertTrue("should contain relax", annotationAxioms.contains(relAx));
        assertTrue("should contain predax", annotationAxioms.contains(predAx));


    }
}
