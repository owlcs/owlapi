package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jun-2009
 */
public class DefaultPrefixManagerTestCase extends TestCase {

    public void testContainsDefaultPrefixNames() {
        PrefixManager pm = new DefaultPrefixManager();
        assertTrue(pm.containsPrefixMapping("owl:"));
        assertTrue(pm.containsPrefixMapping("rdf:"));
        assertTrue(pm.containsPrefixMapping("rdfs:"));
        assertTrue(pm.containsPrefixMapping("xml:"));
        assertTrue(pm.containsPrefixMapping("xsd:"));
        assertTrue(pm.containsPrefixMapping("skos:"));
        assertTrue(!pm.containsPrefixMapping(":"));
        assertNull(pm.getDefaultPrefix());
    }

    public void testPrefixIRIExpansion() {
        PrefixManager pm = new DefaultPrefixManager();
        IRI iri = pm.getIRI("rdfs:comment");
        assertEquals(iri, OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    }

    public void testDefaultPrefixExpansion() {
        String defaultPrefix = "http://www.semanticweb.org/test/ont";
        PrefixManager pm = new DefaultPrefixManager(defaultPrefix);
        assertTrue(pm.containsPrefixMapping(":"));
        assertNotNull(pm.getDefaultPrefix());
        assertEquals(pm.getDefaultPrefix(), pm.getPrefix(":"));
        String expansion = defaultPrefix + "A";
        IRI iri = pm.getIRI(":A");
        assertEquals(iri.toString(), expansion);
    }

}
