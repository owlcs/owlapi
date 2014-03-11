package org.semanticweb.owlapi.api.test.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.UnloadableImportException;

public class ManTestCase extends TestBase {

    @Test
    public void parse() throws ParserException, UnloadableImportException,
            OWLOntologyCreationException, OWLOntologyStorageException {
        System.out.println(string);
        System.out.println("============================");
        OWLOntology o1 = loadOntologyFromString(string);
        System.out.println(saveOntology(o1, new TurtleOntologyFormat()));
        OWLOntology o2 = roundTrip(o1, new TurtleOntologyFormat());
        OWLOntology o3 = roundTrip(o2, new TurtleOntologyFormat());
        equal(o1, o2);
        equal(o1, o3);
        for (OWLAxiom ax : o3.getAxioms()) {
            System.out.println(ax);
        }
    }

    private final String string = "@prefix : <http://ida.org/anno.ttl#> .\n"
            + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "@prefix anno: <http://ida.org/anno.ttl#> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "<http://ida.org/anno.ttl> rdf:type owl:Ontology .\n\n"
            + "anno:prop rdf:type owl:ObjectProperty ; rdfs:label \"prop\"@en .\n"
            + "anno:ap rdf:type owl:AnnotationProperty ; rdfs:label \"ap\"@en .\n"
            + "\n"
            + "anno:Class1 rdf:type owl:Class ; rdfs:label \"Class1\"@en ; rdfs:subClassOf _:a .\n"
            + "anno:Class2 rdf:type owl:Class ; rdfs:label \"Class2\"@en ; rdfs:subClassOf owl:Thing .\n"
            + "\n"
            + "_:a rdf:type owl:Restriction ; owl:onProperty anno:prop ; owl:someValuesFrom anno:Class2 .\n"
            + "\n"
            + "_:b rdf:type owl:Axiom ; anno:ap \"This is my annotation.\"@en ; owl:annotatedSource anno:Class1 ; owl:annotatedProperty rdfs:subClassOf ; owl:annotatedTarget _:a .\n"
            + "";
}
