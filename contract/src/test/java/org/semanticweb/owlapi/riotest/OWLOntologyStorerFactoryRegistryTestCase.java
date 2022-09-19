package org.semanticweb.owlapi.riotest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLStorerFactory;
import org.semanticweb.owlapi.utilities.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class OWLOntologyStorerFactoryRegistryTestCase {

    private static final int EXPECTED_STORERS = 21;

    @Test
    void setUp() {
        PriorityCollection<OWLStorerFactory> ontologyStorers =
            OWLManager.createOWLOntologyManager().getOntologyStorers();
        assertEquals(EXPECTED_STORERS, ontologyStorers.size());
    }
}
