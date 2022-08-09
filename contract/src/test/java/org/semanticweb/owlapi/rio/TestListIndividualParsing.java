package org.semanticweb.owlapi.rio;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestListIndividualParsing {

    @Test
    public void testListIndividualParsing() throws OWLOntologyCreationException, IOException {
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        try (InputStream ttlStream = this.getClass().getResourceAsStream("/rdfListsIndividualsTest.ttl")) {
            OWLOntology ontTTL = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                    new StreamDocumentSource(ttlStream), config.setStrict(false));
            assertEquals(19, ontTTL.getAxiomCount());
            assertEquals(5, ontTTL.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION).size());
        }
        try (InputStream ttlStream = this.getClass().getResourceAsStream("/rdfListsIndividualsTest.ttl")) {
            OWLOntology ontTTL = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                    new StreamDocumentSource(ttlStream), config.setStrict(true));
            assertEquals(15, ontTTL.getAxiomCount());
            assertEquals(1, ontTTL.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION).size(), "List object property assertions shouldn't be created in strict mode.");
        }
        try (InputStream ttlStream = this.getClass().getResourceAsStream("/rdfListsIndividualsWithoutDeclarationsTest.ttl")) {
            OWLOntology ontTTL = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                    new StreamDocumentSource(ttlStream), config.setStrict(false));
            assertEquals(10, ontTTL.getAxiomCount());
            assertEquals(1, ontTTL.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION).size(), "List object property assertions shouldn't be created if list properties weren't declared or used in Rbox axioms.");
        }
    }

}
