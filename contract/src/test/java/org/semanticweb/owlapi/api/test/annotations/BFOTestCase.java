package org.semanticweb.owlapi.api.test.annotations;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;

@SuppressWarnings("javadoc")
public class BFOTestCase extends TestBase {

    @Test
    public void shouldparseBFO() throws OWLOntologyCreationException {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadOntologyFromString("<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF xmlns=\"http://purl.obolibrary.org/obo/bfo.owl#\"\n"
            + "     xml:base=\"http://purl.obolibrary.org/obo/bfo.owl\"\n"
            + "     xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
            + "     xmlns:obo=\"http://purl.obolibrary.org/obo/\"\n"
            + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "     xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\n"
            + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
            + "    <owl:Ontology rdf:about=\"http://purl.obolibrary.org/obo/bfo.owl\"/>\n"
            + "\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/BFO_0000179\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/BFO_0000180\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://xmlns.com/foaf/0.1/homepage\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.org/dc/elements/1.1/member\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0010000\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000115\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000601\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://www.w3.org/2000/01/rdf-schema#seeAlso\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000116\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000602\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.org/dc/elements/1.1/contributor\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000412\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000232\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000119\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://www.w3.org/2000/01/rdf-schema#isDefinedBy\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000117\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000118\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000600\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000111\"/>\n"
            + "    <owl:AnnotationProperty rdf:about=\"http://purl.obolibrary.org/obo/IAO_0000112\"/>\n"
            + "    \n" + "\n" + "    <owl:Axiom>\n"
            + "        <owl:annotatedTarget rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000110\"/><!-- has continuant part at all times -->\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000186\"/><!-- part of continuant at all times that whole exists -->\n"
            + "        <!-- has axiom label --><obo:IAO_0010000 rdf:resource=\"http://purl.obolibrary.org/obo/bfo/axiom/0000602\"/>\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2002/07/owl#inverseOf\"/>\n"
            + "    </owl:Axiom>\n" + "    <owl:Axiom>\n"
            + "        <owl:annotatedTarget rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000177\"/><!-- part of continuant at all times -->\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000187\"/><!-- has continuant part at all times that part exists -->\n"
            + "        <!-- has axiom label --><obo:IAO_0010000 rdf:resource=\"http://purl.obolibrary.org/obo/bfo/axiom/0000601\"/>\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2002/07/owl#inverseOf\"/>\n"
            + "    </owl:Axiom>\n" + "    <owl:Axiom>\n"
            + "        <owl:annotatedTarget rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000110\"/><!-- has continuant part at all times -->\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000186\"/><!-- part of continuant at all times that whole exists -->\n"
            + "        <!-- has axiom label --><obo:IAO_0010000 rdf:resource=\"http://purl.obolibrary.org/obo/bfo/axiom/0000602\"/>\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2002/07/owl#inverseOf\"/>\n"
            + "    </owl:Axiom>\n" + "    <owl:Axiom>\n"
            + "        <owl:annotatedTarget>This is a binary version of a ternary time-indexed, instance level, relation. Unlike the rest of the temporalized relations which temporally quantify over existence of the subject of the relation, this relation temporally quantifies over the existence of the object of the relation. The relation is provided tentatively, to assess whether the GO needs such a relation. It is inverse of &apos;part of continuant at all times&apos;</owl:annotatedTarget>\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000187\"/><!-- has continuant part at all times that part exists -->\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://purl.obolibrary.org/obo/IAO_0000116\"/><!-- editor note -->\n"
            + "        <!-- has axiom label --><obo:IAO_0010000 rdf:resource=\"http://purl.obolibrary.org/obo/bfo/axiom/0000600\"/>\n"
            + "    </owl:Axiom>\n" + "    <owl:Axiom>\n"
            + "        <owl:annotatedTarget rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000177\"/><!-- part of continuant at all times -->\n"
            + "        <owl:annotatedSource rdf:resource=\"http://purl.obolibrary.org/obo/BFO_0000187\"/><!-- has continuant part at all times that part exists -->\n"
            + "        <!-- has axiom label --><obo:IAO_0010000 rdf:resource=\"http://purl.obolibrary.org/obo/bfo/axiom/0000601\"/>\n"
            + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2002/07/owl#inverseOf\"/>\n"
            + "    </owl:Axiom>\n" + "</rdf:RDF>");
    }
}
