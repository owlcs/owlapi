package org.semanticweb.owlapi.rio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLStorerFactory;
import org.semanticweb.owlapi.util.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class OWLOntologyStorerFactoryRegistryTestCase {

    private static final int EXPECTED_STORERS = 20;

    @Test
    public void setUp() {
        PriorityCollection<OWLStorerFactory> ontologyStorers =
            OWLManager.createOWLOntologyManager().getOntologyStorers();
        assertEquals(EXPECTED_STORERS, ontologyStorers.size());
    }
}
