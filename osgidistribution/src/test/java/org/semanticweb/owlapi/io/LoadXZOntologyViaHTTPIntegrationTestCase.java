package org.semanticweb.owlapi.io;
/**
* Created by ses on 3/12/15.
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@Tag("IntegrationTest")
class LoadXZOntologyViaHTTPIntegrationTestCase {

    @Test
    void testLoadOverHTTP() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(
            "http://owlcs.github.io/owlapibenchmarks/horridge-iswc-2014-corpus/BioPortal-Corpus-xz/",
            "BPOntology-100.owl.xml.xz"));
        assertNotNull(ontology);
        assertEquals(122, ontology.getAxiomCount());
    }
}
