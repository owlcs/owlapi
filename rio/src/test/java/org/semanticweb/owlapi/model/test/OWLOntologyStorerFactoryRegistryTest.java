/**
 * 
 */
package org.semanticweb.owlapi.model.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.formats.TurtleOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;
import org.semanticweb.owlapi.registries.OWLOntologyStorerFactoryRegistry;
import org.semanticweb.owlapi.rio.RioTurtleOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class OWLOntologyStorerFactoryRegistryTest {

    public static final int EXPECTED_STORERS = 19;
    private OWLOntologyStorerFactoryRegistry testRegistry;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testRegistry = new OWLOntologyStorerFactoryRegistry();
        assertEquals(EXPECTED_STORERS, testRegistry.getKeys().size());
        testRegistry.clearStorerFactories();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        testRegistry.clearStorerFactories();
        testRegistry = null;
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#add(java.lang.Object)}
     * .
     */
    @Test
    public void testAdd() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
        testRegistry.add(new RioTurtleOntologyStorerFactory());
        final List<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(1, afterStorerFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLOntologyStorerFactoryRegistry#clearStorerFactories()}
     * .
     */
    @Test
    public void testClearStorerFactories() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
        testRegistry
                .registerStorerFactory(new RioTurtleOntologyStorerFactory());
        final List<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(1, afterStorerFactories.size());
        testRegistry.clearStorerFactories();
        final List<OWLOntologyStorerFactory> clearedStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, clearedStorerFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#get(java.lang.Object)}
     * .
     */
    @Test
    public void testGet() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
        testRegistry.add(new RioTurtleOntologyStorerFactory());
        final List<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(1, afterStorerFactories.size());
        final OWLOntologyStorerFactory formatStorer = testRegistry
                .getStorerFactory(new TurtleOntologyFormatFactory());
        assertNotNull(formatStorer);
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#getAll()}.
     */
    @Test
    public void testGetAll() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
        testRegistry.add(new RioTurtleOntologyStorerFactory());
        final List<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(1, afterStorerFactories.size());
        final Collection<OWLOntologyStorerFactory> getAllStorers = testRegistry
                .getAll();
        assertEquals(1, getAllStorers.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#getKeys()}.
     */
    @Test
    public void testGetKeys() {
        final Set<OWLOntologyFormatFactory> initialKeys = testRegistry
                .getKeys();
        assertEquals(0, initialKeys.size());
        testRegistry.add(new RioTurtleOntologyStorerFactory());
        final Set<OWLOntologyFormatFactory> afterKeys = testRegistry.getKeys();
        assertEquals(1, afterKeys.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLOntologyStorerFactoryRegistry#getStorerFactories()}
     * .
     */
    @Test
    public void testGetStorerFactories() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#has(java.lang.Object)}
     * .
     */
    @Test
    public void testHas() {
        final Set<OWLOntologyFormatFactory> initialKeys = testRegistry
                .getKeys();
        assertEquals(0, initialKeys.size());
        testRegistry.add(new RioTurtleOntologyStorerFactory());
        final Set<OWLOntologyFormatFactory> afterKeys = testRegistry.getKeys();
        assertEquals(1, afterKeys.size());
        assertTrue(testRegistry.has(new TurtleOntologyFormatFactory()));
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLOntologyStorerFactoryRegistry#OWLOntologyStorerFactoryRegistry()}
     * .
     */
    @Test
    public void testOWLOntologyStorerFactoryRegistry() {
        assertNotNull(testRegistry.getAll());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLOntologyStorerFactoryRegistry#registerStorerFactory(org.semanticweb.owlapi.model.OWLOntologyStorerFactory)}
     * .
     */
    @Test
    public void testRegisterStorerFactory() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
        testRegistry
                .registerStorerFactory(new RioTurtleOntologyStorerFactory());
        final List<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(1, afterStorerFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.util.AbstractServiceLoader#remove(java.lang.Object)}
     * .
     */
    @Test
    public void testRemove() {
        final Collection<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getAll();
        assertEquals(0, initialStorerFactories.size());
        testRegistry.add(new RioTurtleOntologyStorerFactory());
        final Collection<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getAll();
        assertEquals(1, afterStorerFactories.size());
        testRegistry.remove(afterStorerFactories.iterator().next());
        final Collection<OWLOntologyStorerFactory> emptyStorerFactories = testRegistry
                .getAll();
        assertEquals(0, emptyStorerFactories.size());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.model.OWLOntologyStorerFactoryRegistry#unregisterStorerFactory(org.semanticweb.owlapi.model.OWLOntologyStorerFactory)}
     * .
     */
    @Test
    public void testUnregisterStorerFactory() {
        final List<OWLOntologyStorerFactory> initialStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, initialStorerFactories.size());
        testRegistry
                .registerStorerFactory(new RioTurtleOntologyStorerFactory());
        final List<OWLOntologyStorerFactory> afterStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(1, afterStorerFactories.size());
        testRegistry.unregisterStorerFactory(afterStorerFactories.get(0));
        final List<OWLOntologyStorerFactory> emptyStorerFactories = testRegistry
                .getStorerFactories();
        assertEquals(0, emptyStorerFactories.size());
    }
}
