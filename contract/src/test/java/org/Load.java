package org;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntology;

public class Load extends TestBase {

    @Test
    public void should() {
        String in = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xml:base=\"http://www.eionet.europa.eu/gemet/\"\n"
            + " xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
            + " xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + " xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + " xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + " xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\n"
            + " xmlns:dcterms=\"http://purl.org/dc/terms/\"\n"
            + " xmlns=\"http://www.eionet.europa.eu/gemet/2004/06/gemet-schema.rdf#\">\n" + "\n"
            + "<skos:ConceptScheme rdf:about=\"gemetThesaurus/\">\n"
            + "    <rdfs:label>GEMET - Concepts, version 4.0.1, 2017-06-28T14:17:23.117209+00:00</rdfs:label>\n"
            + "    <dcterms:licence rdf:resource=\"http://creativecommons.org/licenses/by/2.5/dk/\"/>\n"
            + "</skos:ConceptScheme>" + "</rdf:RDF>";
        OWLOntology o = loadOntologyFromString(in);
        o.getAxioms().forEach(System.out::println);
        o.getIndividualsInSignature().forEach(ind -> o.getReferencingAxioms(ind)
            .forEach(ax -> System.out.println("Individual " + ind.getIRI() + " has axiom " + ax)));
        o.getIndividualsInSignature().forEach(ind -> o.getAnnotationAssertionAxioms(ind.getIRI())
            .forEach(ax -> System.out.println("Iri " + ind.getIRI() + " is annotated with " + ax)));
    }
}
