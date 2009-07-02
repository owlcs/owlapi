package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
        assertEquals(iri.toURI(), OWLRDFVocabulary.RDFS_COMMENT.getURI());
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
