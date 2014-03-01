package org.semanticweb.owlapi.rio.io.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class OWLParserFactoryRegistryTest {

    private static final int EXPECTED_PARSERS = 15;

    @Test
    public void setUp() {
        // assertEquals(EXPECTED_PARSERS, testRegistry.getKeys().size());
        assertEquals(EXPECTED_PARSERS, OWLManager.createOWLOntologyManager()
                .getOntologyParsers().size());
    }
}
