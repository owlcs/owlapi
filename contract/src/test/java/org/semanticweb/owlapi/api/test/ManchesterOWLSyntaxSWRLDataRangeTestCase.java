package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Test;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class ManchesterOWLSyntaxSWRLDataRangeTestCase {

    private String inputManSyntax = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
            + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
            + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "Ontology: <http://www.owl-ontologies.com/Ontology1307394066.owl>\n"
            + "Import: <http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl>\n"
            + "Import: <http://swrl.stanford.edu/ontologies/3.3/swrla.owl>\n"
            + "Datatype: xsd:decimal\n"
            + "Datatype: xsd:int\n"
            + "Datatype: xsd:dateTime\n"
            + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>\n"
            + "    Characteristics: \n"
            + "        Functional\n"
            + "    Range: \n"
            + "        xsd:int\n"
            + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasDate>\n"
            + "    Range: \n"
            + "        xsd:dateTime\n"
            + "Class: <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
            + "Individual: <http://www.owl-ontologies.com/Ontology1307394066.owl#p1>\n"
            + "    Types: \n"
            + "        <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
            + "Rule: \n"
            + "    xsd:decimal(?<urn:swrl#x>), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?<urn:swrl#p>, ?<urn:swrl#x>) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?<urn:swrl#p>)";

    @Test
    public void shouldParseRuleInManSyntax() throws OWLOntologyCreationException,
    OWLOntologyStorageException {
        OWLOntology o = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(inputManSyntax));
        StringDocumentTarget t = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o,
                new ManchesterOWLSyntaxOntologyFormat(), t);
        OWLOntology o1 = Factory.getManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(t.toString()));
        o1.getOWLOntologyManager().saveOntology(o1,
                new ManchesterOWLSyntaxOntologyFormat(), t);
        assertEquals(o.getLogicalAxioms(), o1.getLogicalAxioms());
    }
}
