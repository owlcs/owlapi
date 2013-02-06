package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class ManchesterOWLSyntaxAnnotations {
    String input = "Prefix: : <http://example.com/owl/families/>\n"
            + "Ontology: <http://example.com/owl/families>\n" + "Class: Person\n"
            + "  Annotations:  rdfs:comment \"Represents the set of all people.\"\n"
            + "Class: Man\n"
            + "Annotations: rdfs:comment \"States that every man is a person.\"\n"
            + "  SubClassOf:  Person";

    @Test
    public void shouldAnnotateAndRoundTrip() throws OWLOntologyCreationException {
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        Set<OWLAxiom> axioms = o.getAxioms();
        OWLClass p = Class(IRI("http://example.com/owl/families/Person"));
        OWLClass m = Class(IRI("http://example.com/owl/families/Man"));
        assertTrue(axioms.contains(Declaration(p)));
        assertTrue(axioms.contains(Declaration(m)));
        assertTrue(axioms.contains(SubClassOf(m, p)));
    }
}
