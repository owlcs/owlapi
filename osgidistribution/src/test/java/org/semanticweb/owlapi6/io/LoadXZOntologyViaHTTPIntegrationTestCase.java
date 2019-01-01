package org.semanticweb.owlapi6.io;/**
 * Created by ses on 3/12/15.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.test.IntegrationTest;

@SuppressWarnings("javadoc")
public class LoadXZOntologyViaHTTPIntegrationTestCase {

    @Test
    public void testLoadOverHTTP() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(
            "http://owlcs.github.io/owlapibenchmarks/horridge-iswc-2014-corpus/BioPortal-Corpus-xz/",
            "BPOntology-100.owl.xml.xz"));
        assertNotNull(ontology);
        assertEquals("axiomCount", 122, ontology.getAxiomCount());
    }
}
