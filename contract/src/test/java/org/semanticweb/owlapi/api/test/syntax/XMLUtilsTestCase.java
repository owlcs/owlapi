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

import org.junit.Test;
import org.semanticweb.owlapi.io.XMLUtils;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.3.0
 */
@SuppressWarnings("javadoc")
public class XMLUtilsTestCase {

    private static final int CODE_POINT = 0xEFFFF;
    private static final String CODE_POINT_STRING;
    static {
        StringBuilder sb = new StringBuilder();
        sb.appendCodePoint(CODE_POINT);
        CODE_POINT_STRING = sb.toString();
    }

    @Test
    public void testIsNCName() {
        assertTrue(XMLUtils.isNCName(CODE_POINT_STRING + "abc"
                + CODE_POINT_STRING));
        assertTrue(XMLUtils.isNCName(CODE_POINT_STRING + "abc123"
                + CODE_POINT_STRING));
        assertFalse(XMLUtils.isNCName("123" + CODE_POINT_STRING));
        assertFalse(XMLUtils.isNCName(CODE_POINT_STRING + ":a"));
        assertFalse(XMLUtils.isNCName(""));
        assertFalse(XMLUtils.isNCName(null));
    }

    @Test
    public void testIsQName() {
        assertTrue(XMLUtils.isQName(CODE_POINT_STRING + "p1:abc"
                + CODE_POINT_STRING));
        assertFalse(XMLUtils.isQName(CODE_POINT_STRING + "p1:2abc"
                + CODE_POINT_STRING));
        assertFalse(XMLUtils.isQName("11" + CODE_POINT_STRING + ":abc"
                + CODE_POINT_STRING));
        assertFalse(XMLUtils.isQName("ab:c%20d"));
    }

    @Test
    public void testEndsWithNCName() {
        assertEquals("abc" + CODE_POINT_STRING,
                XMLUtils.getNCNameSuffix("1abc" + CODE_POINT_STRING));
        assertTrue(XMLUtils.hasNCNameSuffix("1abc" + CODE_POINT_STRING));
        assertNull(XMLUtils.getNCNameSuffix(CODE_POINT_STRING + "p1:123"));
        assertFalse(XMLUtils.hasNCNameSuffix(CODE_POINT_STRING + "p1:123"));
        assertEquals(
                "ABC",
                XMLUtils.getNCNameSuffix("http://owlapi.sourceforge.net/ontology/ABC"));
        assertEquals(
                "ABC",
                XMLUtils.getNCNameSuffix("http://owlapi.sourceforge.net/ontology#ABC"));
        assertEquals(
                "ABC",
                XMLUtils.getNCNameSuffix("http://owlapi.sourceforge.net/ontology:ABC"));
    }

    @Test
    public void testParsesBNode() {
        assertEquals("_:test", XMLUtils.getNCNamePrefix("_:test"));
        assertEquals(null, XMLUtils.getNCNameSuffix("_:test"));
    }
}
