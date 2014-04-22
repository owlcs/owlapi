package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.*;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class AnonymousOntologyWithImportsTestCase {

    @Nonnull
    String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\">\n"
            + "    <owl:Ontology><owl:imports rdf:resource=\"urn:test\"/></owl:Ontology></rdf:RDF>";

    @Test
    public void shouldNotLoadWrong() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.createOntology(IRI.create("urn:test"));
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntology o = m.loadOntologyFromOntologyDocument(documentSource);
        assertTrue(o.getOntologyID().toString(), o.isAnonymous());
        assertFalse(o.getOntologyID().getDefaultDocumentIRI().isPresent());
    }
}
