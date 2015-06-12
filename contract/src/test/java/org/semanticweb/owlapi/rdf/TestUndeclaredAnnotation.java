package org.semanticweb.owlapi.rdf;

import static org.junit.Assert.*;

import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;

/**
 * Created by ses on 3/10/14.
 */
@SuppressWarnings("javadoc")
public class TestUndeclaredAnnotation extends TestBase {

    @Test
    public void testRDFXMLUsingUndeclaredAnnotationProperty() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
            + "<!DOCTYPE rdf:RDF [\n <!ENTITY ns \"http://example.com/ns#\" >\n <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n <!ENTITY xml \"http://www.w3.org/XML/1998/namespace\" >\n <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n ]>\n"
            + "<rdf:RDF xmlns=\"http://www.org/\" xml:base=\"http://www.org/\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:ns=\"http://example.com/ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\">\n"
            + "    <owl:Ontology rdf:about=\"http://www.org/\"/>\n"
            + "    <rdf:Description rdf:about=\"&ns;test\"><ns:rel><rdf:Description ns:pred =\"Not visible\"/></ns:rel></rdf:Description>\n"
            + "</rdf:RDF>";
        OWLOntology oo = loadOntologyFromString(input);
        @SuppressWarnings("null")
        @Nonnull
        RDFXMLDocumentFormat format = (RDFXMLDocumentFormat) oo.getOWLOntologyManager().getOntologyFormat(oo);
        assertEquals("Should have no unparsed triples", 0, format.getOntologyLoaderMetaData().getUnparsedTriples()
            .size());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms = oo.getAxioms(AxiomType.ANNOTATION_ASSERTION);
        assertEquals("annotation axiom count should be 2", 2, annotationAxioms.size());
        OWLAnnotationProperty relProperty = df.getOWLAnnotationProperty(IRI.create("http://example.com/ns#rel"));
        OWLAnnotationProperty predProperty = df.getOWLAnnotationProperty(IRI.create("http://example.com/ns#pred"));
        Set<OWLAnonymousIndividual> anonymousIndividualSet = oo.getAnonymousIndividuals();
        assertEquals("should be one anonymous individual", 1, anonymousIndividualSet.size());
        @Nonnull
        OWLAnonymousIndividual anonymousIndividual = anonymousIndividualSet.iterator().next();
        OWLAnnotationAssertionAxiom relAx = df.getOWLAnnotationAssertionAxiom(relProperty, IRI.create(
            "http://example.com/ns#test"), anonymousIndividual);
        OWLLiteral notVisible = df.getOWLLiteral("Not visible", "");
        OWLAnnotationAssertionAxiom predAx = df.getOWLAnnotationAssertionAxiom(predProperty, anonymousIndividual,
            notVisible);
        assertTrue("should contain relax", annotationAxioms.contains(relAx));
        assertTrue("should contain predax", annotationAxioms.contains(predAx));
    }

    @Test
    public void testTurtleUsingUndeclaredAnnotationProperty() throws OWLOntologyCreationException {
        String input = "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "        @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "        @prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "        @prefix ex: <http://www.example.org/> .\n" + "        [] rdfs:label \"Visible\" ;\n"
            + "           ex:pred ex:Visible ;\n" + "           ex:pred \"Not visible\" .\n"
            + "        ex:subj rdfs:label \"Visible\" .\n" + "        ex:subj ex:pred \"Visible\" .";
        OWLOntology oo = loadOntologyFromString(input);
        OWLAnnotationProperty pred = df.getOWLAnnotationProperty(IRI.create("http://www.example.org/pred"));
        int countLabels = 0;
        int countPreds = 0;
        int countBNodeAnnotations = 0;
        for (OWLAnnotationAssertionAxiom oa : oo.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            if (oa.getProperty().equals(df.getRDFSLabel())) {
                countLabels++;
            }
            if (oa.getProperty().equals(pred)) {
                countPreds++;
            }
            if (oa.getSubject() instanceof OWLAnonymousIndividual) {
                countBNodeAnnotations++;
            }
        }
        assertEquals(3, countPreds);
        assertEquals(2, countLabels);
        assertEquals(3, countBNodeAnnotations);
    }
}
