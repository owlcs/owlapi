/**
 * 
 */
package org.semanticweb.owlapi.formats.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactoryRegistry;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
public class OWLOntologyFormatFactoryRegistryTest
{

	private static final int EXPECTED_FORMATS = 19;

	private static final int EXPECTED_MIME_TYPES = 14;
    
    private OWLOntologyFormatFactoryRegistry testRegistry;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        testRegistry = new OWLOntologyFormatFactoryRegistry();
        
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
        assertEquals(EXPECTED_FORMATS, testRegistry.getAll().size());
    }
    
    /**
     * Test method for {@link org.semanticweb.owlapi.formats.OWLOntologyFormatFactoryRegistry#getKey(org.semanticweb.owlapi.formats.OWLOntologyFormatFactory)}.
     */
    @Test
    public void testGetKeyOWLOntologyFormatFactory()
    {
        for(OWLOntologyFormatFactory nextFactory : testRegistry.getAll())
        {
            assertNotNull(nextFactory.getKey());
            assertNotNull(nextFactory.getNewFormat());
            // The getMIMETypes collection should never be null
            assertNotNull(nextFactory.getMIMETypes());
            
            if(!nextFactory.getMIMETypes().isEmpty())
            {
                assertNotNull(nextFactory.getDefaultMIMEType());
                assertEquals(nextFactory.getMIMETypes().get(0), nextFactory.getDefaultMIMEType());
            }
            else
            {
                assertNull(nextFactory.getDefaultMIMEType());
            }
        }
    }
    
    /**
     * Test method for {@link org.semanticweb.owlapi.formats.OWLOntologyFormatFactoryRegistry#getByMIMEType(java.lang.String)}.
     */
    @Test
    public void testGetDefaultMIMEType()
    {
        List<String> discoveredMimeTypes = new ArrayList<String>(testRegistry.getAll().size());
        
        for(OWLOntologyFormatFactory nextFactory : testRegistry.getAll())
        {
            String nextType = nextFactory.getDefaultMIMEType();
            if(nextType != null)
            {
                discoveredMimeTypes.add(nextType);
            }
        }
        
        // FIXME: Update this number as more mime types are added
        assertEquals(EXPECTED_MIME_TYPES, discoveredMimeTypes.size());
    }
    
}

