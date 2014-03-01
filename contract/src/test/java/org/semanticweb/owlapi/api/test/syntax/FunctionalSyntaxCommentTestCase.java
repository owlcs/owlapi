package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class FunctionalSyntaxCommentTestCase {

    @Test
    public void shouldParseCommentAndSkipIt()
            throws OWLOntologyCreationException {
        String input = "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\nPrefix(owl:=<http://www.w3.org/2002/07/owl#>)\nPrefix(xml:=<http://www.w3.org/XML/1998/namespace>)\nPrefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\nPrefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\nPrefix(skos:=<http://www.w3.org/2004/02/skos/core#>)\n\n"
                + "Ontology(<file:test.owl>\n"
                + "Declaration(Class(<urn:test.owl#ContactInformation>))\n"
                + "#Test comment\n"
                + "//second Test comment\n"
                + "Declaration(DataProperty(<urn:test.owl#city>))\n"
                + "SubClassOf(<urn:test.owl#ContactInformation> DataMaxCardinality(1 <urn:test.owl#city> xsd:string))\n"
                + ")";
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        OWLAxiom ax1 = Declaration(DataProperty(IRI("urn:test.owl#city")));
        OWLAxiom ax2 = SubClassOf(
                Class(IRI("urn:test.owl#ContactInformation")),
                DataMaxCardinality(1, DataProperty(IRI("urn:test.owl#city")),
                        Datatype(OWL2Datatype.XSD_STRING.getIRI())));
        OWLAxiom ax3 = Declaration(Class(IRI("urn:test.owl#ContactInformation")));
        assertTrue(o.containsAxiom(ax1));
        assertTrue(o.containsAxiom(ax2));
        assertTrue(o.containsAxiom(ax3));
    }
}
