/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class DefaultPrefixManagerTestCase {

    @Test
    public void getPrefixIRIEmpty() {
        DefaultPrefixManager pm = new DefaultPrefixManager();
        pm.setPrefix("foaf:", "http://xmlns.com/foaf/0.1/");
        assertEquals("foaf:",
                pm.getPrefixIRI(IRI("http://xmlns.com/foaf/0.1/")));
    }

    @Test
    public void testContainsDefaultPrefixNames() {
        PrefixManager pm = new DefaultPrefixManager();
        assertTrue(pm.containsPrefixMapping("owl:"));
        assertTrue(pm.containsPrefixMapping("rdf:"));
        assertTrue(pm.containsPrefixMapping("rdfs:"));
        assertTrue(pm.containsPrefixMapping("xml:"));
        assertTrue(pm.containsPrefixMapping("xsd:"));
        assertFalse(pm.containsPrefixMapping(":"));
        assertNull(pm.getDefaultPrefix());
    }

    @Test
    public void testPrefixIRIExpansion() {
        PrefixManager pm = new DefaultPrefixManager();
        IRI iri = pm.getIRI("rdfs:comment");
        assertEquals(iri, OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    }

    @Test
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
