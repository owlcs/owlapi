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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.3.0
 */
@SuppressWarnings({"javadoc"})
public class XMLUtilsTestCase extends TestBase {

    private static final int CODE_POINT = 0xEFFFF;
    private static final String CODE_POINT_STRING = init();

    static String init() {
        StringBuilder sb = new StringBuilder();
        sb.appendCodePoint(CODE_POINT);
        return sb.toString();
    }

    @Test
    public void testIsNCName() {
        assertTrue(XMLUtils.isNCName(CODE_POINT_STRING + "abc" + CODE_POINT_STRING));
        assertTrue(XMLUtils.isNCName(CODE_POINT_STRING + "abc123" + CODE_POINT_STRING));
        assertFalse(XMLUtils.isNCName("123" + CODE_POINT_STRING));
        assertFalse(XMLUtils.isNCName(CODE_POINT_STRING + ":a"));
        assertFalse(XMLUtils.isNCName(""));
        assertFalse(XMLUtils.isNCName(null));
    }

    @Test
    public void testIsQName() {
        assertTrue(XMLUtils.isQName(CODE_POINT_STRING + "p1:abc" + CODE_POINT_STRING));
        assertFalse(XMLUtils.isQName(CODE_POINT_STRING + "p1:2abc" + CODE_POINT_STRING));
        assertFalse(XMLUtils.isQName("11" + CODE_POINT_STRING + ":abc" + CODE_POINT_STRING));
        assertFalse(XMLUtils.isQName("ab:c%20d"));
    }

    @Test
    public void testEndsWithNCName() {
        assertEquals("abc" + CODE_POINT_STRING,
                        XMLUtils.getNCNameSuffix("1abc" + CODE_POINT_STRING));
        assertTrue(XMLUtils.hasNCNameSuffix("1abc" + CODE_POINT_STRING));
        assertNull(XMLUtils.getNCNameSuffix(CODE_POINT_STRING + "p1:123"));
        assertFalse(XMLUtils.hasNCNameSuffix(CODE_POINT_STRING + "p1:123"));
        assertEquals("ABC", XMLUtils.getNCNameSuffix("http://owlapi.sourceforge.net/ontology/ABC"));
        assertEquals("ABC", XMLUtils.getNCNameSuffix("http://owlapi.sourceforge.net/ontology#ABC"));
        assertEquals("ABC", XMLUtils.getNCNameSuffix("http://owlapi.sourceforge.net/ontology:ABC"));
    }

    @Test
    public void testParsesBNode() {
        assertEquals("_:test", XMLUtils.getNCNamePrefix("_:test"));
        assertNull(XMLUtils.getNCNameSuffix("_:test"));
    }

    @Test
    public void testmissingTypes() {
        // given
        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<rdf:RDF\n"
                        + "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                        + "xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\n"
                        + "xmlns:dc=\"http://purl.org/dc/elements/1.1#\"\n" + ">\n"
                        + "<skos:ConceptScheme rdf:about=\"http://www.thesaurus.gc.ca/#CoreSubjectThesaurus\">\n"
                        + "<dc:title xml:lang=\"en\">Government of Canada Core Subject Thesaurus</dc:title>\n"
                        + "<dc:creator xml:lang=\"en\">Government of Canada</dc:creator>\n"
                        + "</skos:ConceptScheme>\n" + "\n"
                        + "<skos:Concept rdf:about=\"http://www.thesaurus.gc.ca/concept/#Abbreviations\">\n"
                        + "<skos:prefLabel>Abbreviations</skos:prefLabel>\n"
                        + "<skos:related rdf:resource=\"http://www.thesaurus.gc.ca/#Terminology\"/>\n"
                        + "<skos:inScheme rdf:resource=\"http://www.thesaurus.gc.ca/#CoreSubjectThesaurus\"/>\n"
                        + "<skos:prefLabel xml:lang=\"fr\">Abr&#233;viation</skos:prefLabel>\n"
                        + "</skos:Concept>\n"
                        + "<skos:Concept rdf:about=\"http://www.thesaurus.gc.ca/concept/#Aboriginal%20affairs\">\n"
                        + "<skos:prefLabel>Aboriginal affairs</skos:prefLabel>\n"
                        + "<skos:altLabel>Aboriginal issues</skos:altLabel>\n"
                        + "<skos:related rdf:resource=\"http://www.thesaurus.gc.ca/#Aboriginal%20rights\"/>\n"
                        + "<skos:related rdf:resource=\"http://www.thesaurus.gc.ca/#Land claims\"/>\n"
                        + "<skos:inScheme rdf:resource=\"http://www.thesaurus.gc.ca/#CoreSubjectThesaurus\"/>\n"
                        + "<skos:prefLabel xml:lang=\"fr\">Affaires autochtones</skos:prefLabel>\n"
                        + "</skos:Concept>\n" + "\n" + "</rdf:RDF>";
        // when
        OWLOntology o = loadOntologyFromString(input,
                        IRI.getNextDocumentIRI("testuriwithblankspace"),
                        new RDFXMLDocumentFormat());
        // then
        assertEquals(15, o.getAxiomCount());
    }
}
