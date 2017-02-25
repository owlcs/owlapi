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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.3.0
 */
@SuppressWarnings("javadoc")
public class IRICharSequenceTestCase {

    @Test
    public void testCharAt() {
        String str = "http://owlapi.sourceforge.net#ABC";
        IRI iri = IRI("http://owlapi.sourceforge.net#", "ABC");
        for (int i = 0; i < str.length(); i++) {
            assertEquals(str.charAt(i), iri.charAt(i));
        }
    }

    @Test
    public void testCharAtNoRemainder() {
        String str = "http://owlapi.sourceforge.net";
        IRI iri = IRI(str, "");
        for (int i = 0; i < str.length(); i++) {
            assertEquals(str.charAt(i), iri.charAt(i));
        }
    }

    @Test
    public void testCharAtNoPrefix() {
        String str = "#ABC";
        IRI iri = IRI("#", "ABC");
        for (int i = 0; i < str.length(); i++) {
            assertEquals(str.charAt(i), iri.charAt(i));
        }
    }

    @Test
    public void testSubSequence() {
        String str = "http://owlapi.sourceforge.net#ABC";
        IRI iri = IRI("http://owlapi.sourceforge.net#", "ABC");
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                assertEquals(str.subSequence(i, j), iri.subSequence(i, j));
            }
        }
    }

    @Test
    public void testLength() {
        IRI iri = IRI("http://owlapi.sourceforge.net#", "ABC");
        assertEquals(33, iri.length());
    }

    @Test
    public void testLengthNoRemainder() {
        IRI iri = IRI("http://owlapi.sourceforge.net", "");
        assertEquals(29, iri.length());
    }

    @Test
    public void testLengthNoPrefix() {
        IRI iri = IRI("#", "ABC");
        assertEquals(4, iri.length());
    }
}
