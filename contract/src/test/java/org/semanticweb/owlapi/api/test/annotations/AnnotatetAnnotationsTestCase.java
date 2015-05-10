package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;

@SuppressWarnings("javadoc")
public class AnnotatetAnnotationsTestCase extends TestBase {

    @Test
    public void shouldLoadAnnotatedannotationsCorrectly()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        IRI subject = IRI.create(
            "http://purl.obolibrary.org/obo/UBERON_0000033");
        String input = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF xmlns=\"http://example.com#\"\n"
            + "     xml:base=\"http://example.com\"\n"
            + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "     xmlns:oboInOwl=\"http://www.geneontology.org/formats/oboInOwl#\">\n"
            + "    <owl:Ontology rdf:about=\"http://example.com\"/>\n" + "\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://www.geneontology.org/formats/oboInOwl#source\"/>\n"
            + "\n"
            + "    <owl:Class rdf:about=\"http://purl.obolibrary.org/obo/UBERON_0000033\">\n"
            + "        <rdfs:label rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">head</rdfs:label>\n"
            + "        <oboInOwl:hasDbXref rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">UMLS:C0018670</oboInOwl:hasDbXref>\n"
            + "    </owl:Class>\n" + "    <owl:Axiom>\n"
            + "        <oboInOwl:source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">NIFSTD:birnlex_1230</oboInOwl:source>\n"
            + "        <owl:annotatedTarget rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">UMLS:C0018670</owl:annotatedTarget>\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/UBERON_0000033\"/>\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://www.geneontology.org/formats/oboInOwl#hasDbXref\"/>\n"
            + "    </owl:Axiom>\n" + "    <owl:Axiom>\n"
            + "        <owl:annotatedTarget rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">UMLS:C0018670</owl:annotatedTarget>\n"
            + "        <oboInOwl:source rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">ncithesaurus:Head</oboInOwl:source>\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/UBERON_0000033\"/>\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://www.geneontology.org/formats/oboInOwl#hasDbXref\"/>\n"
            + "    </owl:Axiom>\n" + "</rdf:RDF>";
        OWLOntology testcase = loadOntologyFromString(input);
        long before = testcase.annotationAssertionAxioms(subject).count();
        OWLOntology result = roundTrip(testcase);
        long after = result.annotationAssertionAxioms(subject).count();
        assertEquals(before, after);
    }

    @Test
    public void
        shouldRecognizeAnnotationsOnAxiomsWithDifferentannotationsAsDistinct() {
        OWLAnnotationProperty p = AnnotationProperty(iri("p"));
        OWLAnnotationSubject i = iri("i");
        OWLLiteral v = Literal("value");
        OWLLiteral ann1 = Literal("value1");
        OWLLiteral ann2 = Literal("value2");
        OWLAnnotationAssertionAxiom ax1 = AnnotationAssertion(p, i, v,
            singleton(Annotation(RDFSLabel(), ann1)));
        OWLAnnotationAssertionAxiom ax2 = AnnotationAssertion(p, i, v,
            singleton(Annotation(RDFSLabel(), ann2)));
        Set<OWLAnnotationAssertionAxiom> set = new TreeSet<>();
        set.add(ax1);
        set.add(ax2);
        assertEquals(2, set.size());
    }
}
