package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * Created by ses on 3/10/14.
 */
@SuppressWarnings("javadoc")
public class UndeclaredAnnotationTestCase extends TestBase {

    @Test
    public void shouldThrowAnExceptionOnError1AndStrictParsing() throws OWLOntologyCreationException {
        String input = " @prefix : <http://www.example.com#> .\n" + " @prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + " @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + " @prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + " @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "<urn:test:testonotlogy> a owl:Ontology ."
            + " :subject rdf:type owl:Class ;\n" + "   rdfs:subClassOf [ rdf:type owl:Restriction ;\n"
            + "                owl:onProperty :unknownproperty;\n"
            + "                owl:minCardinality \"0\"^^xsd:nonNegativeInteger\n" + "   ] .";
        OWLOntology o = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input),
            new OWLOntologyLoaderConfiguration().setStrict(true));
        assertEquals(0, o.getLogicalAxiomCount());
    }
}
