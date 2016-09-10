package org.semanticweb.owlapi.rio.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLStorerFactory;
import org.semanticweb.owlapi.util.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class OWLOntologyStorerFactoryRegistryTest extends TestBase {

    private static final int EXPECTED_STORERS = 20;

    @Test
    public void setUp() {
        // XXX refactor this to be able to check what ontology storers would be
        // injected before creating an OWLOntologyManager
        PriorityCollection<OWLStorerFactory> ontologyStorers = m.getOntologyStorers();
        assertEquals(EXPECTED_STORERS, ontologyStorers.size());
    }
}
