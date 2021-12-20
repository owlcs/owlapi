package org.semanticweb.owlapi6.riotest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.io.OWLStorerFactory;
import org.semanticweb.owlapi6.utilities.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class OWLOntologyStorerFactoryRegistryTestCase extends TestBase {

    private static final int EXPECTED_STORERS = 21;

    @Test
    void setUp() {
        PriorityCollection<OWLStorerFactory> ontologyStorers = setupManager().getOntologyStorers();
        assertEquals(EXPECTED_STORERS, ontologyStorers.size());
    }
}
