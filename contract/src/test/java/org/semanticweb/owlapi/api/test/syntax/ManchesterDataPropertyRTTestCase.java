package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class ManchesterDataPropertyRTTestCase {
    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        OWLDataFactory factory = Factory.getFactory();
        String NS = "http://protege.org/ontologies";
        OWLDataProperty p = factory.getOWLDataProperty(IRI(NS + "#p"));
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(p));
        String saved = saveOntology(ontology);
        // when
        ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(saved));
        // then
        assertTrue(ontology.containsDataPropertyInSignature(p.getIRI()));
    }

    private String saveOntology(OWLOntology o) throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o, target);
        return target.toString();
    }
}
