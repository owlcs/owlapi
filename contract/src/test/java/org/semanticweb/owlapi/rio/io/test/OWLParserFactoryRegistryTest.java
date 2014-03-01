package org.semanticweb.owlapi.rio.io.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.TurtleOntologyFormatFactory;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.registries.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.rio.RioTurtleParserFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class OWLParserFactoryRegistryTest {

    public static final int EXPECTED_PARSERS = 15;
    private OWLParserFactoryRegistry testRegistry;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testRegistry = new OWLParserFactoryRegistry();
        // assertEquals(EXPECTED_PARSERS, testRegistry.getKeys().size());
        testRegistry.clearParserFactories();
        assertEquals(EXPECTED_PARSERS, OWLManager.createOWLOntologyManager()
                .getOntologyParsers().size());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        testRegistry.clearParserFactories();
        testRegistry = null;
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#add(java.lang.Object)}
     * .
     */
    @Test
    public void testAdd() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
        testRegistry.add(new RioTurtleParserFactory());
        final List<OWLParserFactory> afterParserFactories = testRegistry
                .getParserFactories();
        assertEquals(1, afterParserFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLParserFactoryRegistry#clearParserFactories()}
     * .
     */
    @Test
    public void testClearParserFactories() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
        testRegistry.registerParserFactory(new RioTurtleParserFactory());
        final List<OWLParserFactory> afterParserFactories = testRegistry
                .getParserFactories();
        assertEquals(1, afterParserFactories.size());
        testRegistry.clearParserFactories();
        final List<OWLParserFactory> clearedParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, clearedParserFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#get(java.lang.Object)}
     * .
     */
    @Test
    public void testGet() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
        testRegistry.add(new RioTurtleParserFactory());
        final List<OWLParserFactory> afterParserFactories = testRegistry
                .getParserFactories();
        assertEquals(1, afterParserFactories.size());
        final OWLParserFactory formatStorer = testRegistry
                .getParserFactory(new TurtleOntologyFormatFactory());
        assertNotNull(formatStorer);
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#getAll()}.
     */
    @Test
    public void testGetAll() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
        testRegistry.add(new RioTurtleParserFactory());
        final List<OWLParserFactory> afterParserFactories = testRegistry
                .getParserFactories();
        assertEquals(1, afterParserFactories.size());
        final Collection<OWLParserFactory> getAllStorers = testRegistry
                .getAll();
        assertEquals(1, getAllStorers.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#getKeys()}.
     */
    @Test
    public void testGetKeys() {
        final Set<Set<OWLOntologyFormatFactory>> initialKeys = testRegistry
                .getKeys();
        assertEquals(0, initialKeys.size());
        testRegistry.add(new RioTurtleParserFactory());
        final Set<Set<OWLOntologyFormatFactory>> afterKeys = testRegistry
                .getKeys();
        assertEquals(1, afterKeys.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLParserFactoryRegistry#getParserFactories()}
     * .
     */
    @Test
    public void testGetParserFactories() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#has(java.lang.Object)}
     * .
     */
    @Test
    public void testHas() {
        final Set<Set<OWLOntologyFormatFactory>> initialKeys = testRegistry
                .getKeys();
        assertEquals(0, initialKeys.size());
        testRegistry.add(new RioTurtleParserFactory());
        final Set<Set<OWLOntologyFormatFactory>> afterKeys = testRegistry
                .getKeys();
        assertEquals(1, afterKeys.size());
        assertTrue(testRegistry
                .has(Collections
                        .<OWLOntologyFormatFactory> singleton(new TurtleOntologyFormatFactory())));
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLParserFactoryRegistry#OWLParserFactoryRegistry()}
     * .
     */
    @Test
    public void testOWLParserFactoryRegistry() {
        assertNotNull(testRegistry.getAll());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLParserFactoryRegistry#registerParserFactory(org.semanticweb.owlapi.model.OWLParserFactory)}
     * .
     */
    @Test
    public void testRegisterParserFactory() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
        testRegistry.registerParserFactory(new RioTurtleParserFactory());
        final List<OWLParserFactory> afterParserFactories = testRegistry
                .getParserFactories();
        assertEquals(1, afterParserFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#remove(java.lang.Object)}
     * .
     */
    @Test
    public void testRemove() {
        final Collection<OWLParserFactory> initialParserFactories = testRegistry
                .getAll();
        assertEquals(0, initialParserFactories.size());
        testRegistry.add(new RioTurtleParserFactory());
        final Collection<OWLParserFactory> afterParserFactories = testRegistry
                .getAll();
        assertEquals(1, afterParserFactories.size());
        testRegistry.remove(afterParserFactories.iterator().next());
        final Collection<OWLParserFactory> emptyParserFactories = testRegistry
                .getAll();
        assertEquals(0, emptyParserFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLParserFactoryRegistry#unregisterParserFactory(org.semanticweb.owlapi.model.OWLParserFactory)}
     * .
     */
    @Test
    public void testUnregisterParserFactory() {
        final List<OWLParserFactory> initialParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, initialParserFactories.size());
        testRegistry.registerParserFactory(new RioTurtleParserFactory());
        final List<OWLParserFactory> afterParserFactories = testRegistry
                .getParserFactories();
        assertEquals(1, afterParserFactories.size());
        testRegistry.unregisterParserFactory(afterParserFactories.get(0));
        final List<OWLParserFactory> emptyParserFactories = testRegistry
                .getParserFactories();
        assertEquals(0, emptyParserFactories.size());
    }
}
