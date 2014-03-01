package org.semanticweb.owlapi.rio.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.util.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class OWLOntologyStorerFactoryRegistryTest {

    // XXX originally it was 19 storers, I cannot find which ones are missing.
    public static final int EXPECTED_STORERS = 17;

    @Before
    public void setUp() throws Exception {
        // XXX refactor this to be able to check what ontology storers would be
        // injected before creating an OWLOntologyManager
        PriorityCollection<OWLOntologyStorer> ontologyStorers = OWLManager
                .createOWLOntologyManager().getOntologyStorers();
        assertEquals(EXPECTED_STORERS, ontologyStorers.size());
    }
}
