package org.semanticweb.owlapi.rio.io.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.semanticweb.owlapi.apibinding.OWLManager;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class OWLParserFactoryRegistryTest {

    public static final int EXPECTED_PARSERS = 15;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // assertEquals(EXPECTED_PARSERS, testRegistry.getKeys().size());
        assertEquals(EXPECTED_PARSERS, OWLManager.createOWLOntologyManager()
                .getOntologyParsers().size());
    }
}
