package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * Created by ses on 3/10/14.
 */
@SuppressWarnings("javadoc")
public class UndeclaredAnnotationTestCase extends TestBase {

    @Test
    public void testRDFXMLUsingUndeclaredAnnotationProperty() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
            + "<!DOCTYPE rdf:RDF [\n <!ENTITY ns \"http://example.com/ns#\" >\n <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n <!ENTITY xml \"http://www.w3.org/XML/1998/namespace\" >\n <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n ]>\n"
            + "<rdf:RDF xmlns=\"http://www.org/\" xml:base=\"http://www.org/\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:ns=\"http://example.com/ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\">\n"
            + "    <owl:Ontology rdf:about=\"http://www.org/\"/>\n"
            + "    <rdf:Description rdf:about=\"&ns;test\"><ns:rel><rdf:Description ns:pred =\"Not visible\"/></ns:rel></rdf:Description>\n"
            + "</rdf:RDF>";
        StringDocumentSource documentSource = new StringDocumentSource(input,
            IRI.generateDocumentIRI(), new RDFXMLDocumentFormat(), null);
        OWLOntology oo = loadOntologyFromString(documentSource);
        assertTrue(documentSource.getOntologyLoaderMetaData().isPresent());
        assertEquals("Should have no unparsed triples", 0,
            documentSource.getOntologyLoaderMetaData().get().getUnparsedTriples().count());
        Set<OWLAnnotationAssertionAxiom> annotationAxioms =
            asUnorderedSet(oo.axioms(AxiomType.ANNOTATION_ASSERTION));
        assertEquals("annotation axiom count should be 2", 2, annotationAxioms.size());
        OWLAnnotationProperty relProperty =
            df.getOWLAnnotationProperty("http://example.com/ns#", "rel");
        OWLAnnotationProperty predProperty =
            df.getOWLAnnotationProperty("http://example.com/ns#", "pred");
        Set<OWLAnonymousIndividual> anonymousIndividualSet =
            asUnorderedSet(oo.anonymousIndividuals());
        assertEquals("should be one anonymous individual", 1, anonymousIndividualSet.size());
        @Nonnull
        OWLAnonymousIndividual anonymousIndividual = anonymousIndividualSet.iterator().next();
        OWLAnnotationAssertionAxiom relAx = df.getOWLAnnotationAssertionAxiom(relProperty,
            IRI.create("http://example.com/ns#", "test"), anonymousIndividual);
        OWLLiteral notVisible = df.getOWLLiteral("Not visible", "");
        OWLAnnotationAssertionAxiom predAx =
            df.getOWLAnnotationAssertionAxiom(predProperty, anonymousIndividual, notVisible);
        assertTrue("should contain relax", annotationAxioms.contains(relAx));
        assertTrue("should contain predax", annotationAxioms.contains(predAx));
    }

    @Test
    public void testTurtleUsingUndeclaredAnnotationProperty() {
        String input = "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "        @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "        @prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "        @prefix ex: <http://www.example.org/> .\n"
            + "        [] rdfs:label \"Visible\" ;\n" + "           ex:pred ex:Visible ;\n"
            + "           ex:pred \"Not visible\" .\n"
            + "        ex:subj rdfs:label \"Visible\" .\n"
            + "        ex:subj ex:pred \"Visible\" .";
        OWLOntology o = loadOntologyFromString(input, new TurtleDocumentFormat());
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
    public void shouldThrowAnExceptionOnError1AndStrictParsing()
        throws OWLOntologyCreationException {
        String input = " @prefix : <http://www.example.com#> .\n"
            + " @prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + " @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + " @prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + " @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "<urn:test:testonotlogy> a owl:Ontology ." + " :subject rdf:type owl:Class ;\n"
            + "   rdfs:subClassOf [ rdf:type owl:Restriction ;\n"
            + "                owl:onProperty :unknownproperty;\n"
            + "                owl:minCardinality \"0\"^^xsd:nonNegativeInteger\n" + "   ] .";
        OWLOntology o =
            loadOntologyWithConfig(new StringDocumentSource(input, new TurtleDocumentFormat()),
                new OntologyConfigurator().setStrict(true));
        assertEquals(0, o.getLogicalAxiomCount());
    }
}
