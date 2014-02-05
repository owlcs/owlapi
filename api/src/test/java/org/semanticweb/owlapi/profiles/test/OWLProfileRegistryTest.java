/**
 * 
 */
package org.semanticweb.owlapi.profiles.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileRegistry;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
public class OWLProfileRegistryTest
{
    
    private OWLProfileRegistry testRegistry;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        testRegistry = new OWLProfileRegistry();
        
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void testServiceDiscovery()
    {
        assertEquals(5, testRegistry.getAll().size());
    }
    
    /**
     * Test method for {@link org.semanticweb.owlapi.formats.OWLProfileRegistry#getKey(org.semanticweb.owlapi.profiles.OWLProfile)}.
     */
    @Test
    public void testGetKeyOWLProfile()
    {
        for(OWLProfile nextFactory : testRegistry.getAll())
        {
            assertNotNull(nextFactory.getName());
            assertNotNull(nextFactory.getIRI());
        }
    }
    
}
