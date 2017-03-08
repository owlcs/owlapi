package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class AnnotatetAnnotationsTestCase extends TestBase {

    @Test
    public void shouldRoundtripMultipleNestedAnnotationsdebug() {
        String ns = "urn:n:a#";
        Set<OWLAxiom> axioms = Sets.newHashSet(df.getOWLObjectPropertyAssertionAxiom(
                        df.getOWLObjectProperty(ns, "r"), df.getOWLNamedIndividual(ns, "a"),
                        df.getOWLNamedIndividual(ns, "b"), Arrays.asList(
                                        df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(1),
                                                        df.getOWLAnnotation(df.getRDFSComment(),
                                                                        df.getOWLLiteral(3))),
                                        df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(2),
                                                        df.getOWLAnnotation(df.getRDFSComment(),
                                                                        df.getOWLLiteral(4))))));
        String input = "<?xml version=\"1.0\"?>\n"
                        + "<rdf:RDF xmlns=\"urn:t:o#\" xml:base=\"urn:t:o\"\n xmlns:ann=\"urn:n:a#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
                        + "    <owl:Ontology rdf:about=\"urn:t:o\"/>\n"
                        + "    <owl:ObjectProperty rdf:about=\"urn:n:a#r\"/>\n"
                        + "    <owl:NamedIndividual rdf:about=\"urn:n:a#a\"><ann:r rdf:resource=\"urn:n:a#b\"/></owl:NamedIndividual>\n"
                        + "    <owl:Annotation>\n" + "        <owl:annotatedSource>\n"
                        + "            <owl:Axiom rdf:nodeID=\"_:genid1\">\n"
                        + "                <owl:annotatedSource rdf:resource=\"urn:n:a#a\"/><owl:annotatedProperty rdf:resource=\"urn:n:a#r\"/><owl:annotatedTarget rdf:resource=\"urn:n:a#b\"/>\n"
                        + "                <rdfs:label rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</rdfs:label><rdfs:label rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">2</rdfs:label>\n"
                        + "            </owl:Axiom>\n" + "        </owl:annotatedSource>\n"
                        + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2000/01/rdf-schema#label\"/><owl:annotatedTarget rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</owl:annotatedTarget>\n"
                        + "        <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">3</rdfs:comment></owl:Annotation>\n"
                        + "    <owl:Annotation>\n" + "        <owl:annotatedSource>\n"
                        + "            <owl:Axiom rdf:nodeID=\"_:genid1\">\n"
                        + "                <owl:annotatedSource rdf:resource=\"urn:n:a#a\"/><owl:annotatedProperty rdf:resource=\"urn:n:a#r\"/><owl:annotatedTarget rdf:resource=\"urn:n:a#b\"/>\n"
                        + "                <rdfs:label rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</rdfs:label><rdfs:label rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">2</rdfs:label>\n"
                        + "            </owl:Axiom>\n" + "        </owl:annotatedSource>\n"
                        + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2000/01/rdf-schema#label\"/><owl:annotatedTarget rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">2</owl:annotatedTarget>\n"
                        + "        <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">4</rdfs:comment></owl:Annotation>\n"
                        + "    <owl:NamedIndividual rdf:about=\"urn:n:a#b\"/></rdf:RDF>";
        OWLOntology ont = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        assertEquals(axioms, asUnorderedSet(ont.logicalAxioms()));
    }

    @Test
    public void shouldLoadAnnotatedannotationsCorrectly() throws OWLOntologyStorageException {
        IRI subject = IRI.create("http://purl.obolibrary.org/obo/", "UBERON_0000033");
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF xmlns=\"http://example.com#\"\n"
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
        OWLOntology testcase = loadOntologyFromString(input, new RDFXMLDocumentFormat());
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
        OWLAnnotationAssertionAxiom ax1 =
                        AnnotationAssertion(p, i, v, singleton(Annotation(RDFSLabel(), ann1)));
        OWLAnnotationAssertionAxiom ax2 =
                        AnnotationAssertion(p, i, v, singleton(Annotation(RDFSLabel(), ann2)));
        Set<OWLAnnotationAssertionAxiom> set = new TreeSet<>();
        set.add(ax1);
        set.add(ax2);
        assertEquals(2, set.size());
    }
}
