package org.semanticweb.owlapi.rio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLStorerFactory;
import org.semanticweb.owlapi.util.PriorityCollection;

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
