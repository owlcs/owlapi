package org.semanticweb.owlapi.io;/**
 * Created by ses on 3/12/15.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.test.IntegrationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(IntegrationTest.class)
public class LoadXZOntologyViaHTTPTest {
    @SuppressWarnings("UnusedDeclaration")
    private static Logger logger = LoggerFactory.getLogger(LoadXZOntologyViaHTTPTest.class);
    @Test
    public void testLoadOverHTTP() throws OWLOntologyCreationException {
        String urlString = "http://owlcs.github.io/owlapibenchmarks/horridge-iswc-2014-corpus/BioPortal-Corpus-xz/BPOntology-100.owl.xml.xz";
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(urlString));
        assertNotNull(ontology);
        assertEquals("axiomCount",122,ontology.getAxiomCount());
    }
}
