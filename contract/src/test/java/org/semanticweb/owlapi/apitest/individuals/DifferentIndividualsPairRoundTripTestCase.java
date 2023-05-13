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
package org.semanticweb.owlapi.apitest.individuals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.rioformats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.rioformats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.rioformats.TrigDocumentFormat;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class DifferentIndividualsPairRoundTripTestCase extends TestBase {

    @ParameterizedTest
    @MethodSource("formats")
    void testFormatSingleTriple(OWLDocumentFormat format) {
        OWLOntology o = o(DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.J),
            DifferentIndividuals(INDIVIDUALS.k, AnonymousIndividual()));
        String string = saveOntology(o, format).toString();
        assertSingleTriple(Boolean.FALSE, string, format);
        equal(o, roundTrip(o, format));
    }

    @ParameterizedTest
    @MethodSource("formats")
    void testFormatMultipleTriples(OWLDocumentFormat format) {
        OWLOntology o = o(DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.J, INDIVIDUALS.k),
            DifferentIndividuals(AnonymousIndividual(), INDIVIDUALS.l, INDIVIDUALS.indA));
        assertSingleTriple(Boolean.TRUE, saveOntology(o, format).toString(), format);
        equal(o, roundTrip(o, format));
    }

    private static void assertSingleTriple(Boolean expect, String saved, OWLDocumentFormat format) {
        java.lang.String allDiff = OWLRDFVocabulary.OWL_ALL_DIFFERENT.getIRI().toString();
        String lAllDiff = "<rdf:type rdf:resource=\"" + allDiff + "\"/>";
        String pAllDiff = "<rdf:type rdf:resource=\"&owl;AllDifferent\"/>";
        java.lang.String dist = "<distinctMembers rdf:parseType=\"Collection\">";
        java.lang.String pDist = OWLRDFVocabulary.OWL_DISTINCT_MEMBERS.getPrefixedName();
        java.lang.String lDist = "<" + pDist + " ";
        java.lang.String distinct = OWLRDFVocabulary.OWL_DISTINCT_MEMBERS.getIRI().toString();
        java.lang.String prefixAllDiff = OWLRDFVocabulary.OWL_ALL_DIFFERENT.getPrefixedName();
        switch (format) {
            case RDFXMLDocumentFormat f -> xmlAllDiffDist(expect, saved, lAllDiff, pAllDiff, dist);
            case RioRDFXMLDocumentFormat f -> allDiffDist(expect, saved, lAllDiff, lDist);
            case RDFJsonDocumentFormat f -> allDiffDist(expect, saved, allDiff, distinct);
            case RDFJsonLDDocumentFormat f -> allDiffDist(expect, saved, allDiff, distinct);
            case NTriplesDocumentFormat f -> allDiffDist(expect, saved, allDiff, distinct);
            case NQuadsDocumentFormat f -> allDiffDist(expect, saved, allDiff, distinct);
            case TurtleDocumentFormat f -> allDiffDist(expect, saved, prefixAllDiff, pDist);
            case RioTurtleDocumentFormat f -> allDiffDist(expect, saved, prefixAllDiff, pDist);
            case TrigDocumentFormat f -> allDiffDist(expect, saved, prefixAllDiff, pDist);
            default -> {
            }
        }
    }

    protected static void xmlAllDiffDist(Boolean expectation, String saved, String longAllDifferent,
        String shortAllDifferent, java.lang.String distinctMembers) {
        {
            assertEquals(expectation, Boolean.valueOf(
                saved.contains(shortAllDifferent) || saved.contains(longAllDifferent)), saved);
            assertEquals(expectation, Boolean.valueOf(saved.contains(distinctMembers)), saved);
        }
    }

    protected static void allDiffDist(Boolean expectation, String saved,
        java.lang.String allDifferent, java.lang.String distinct) {
        assertEquals(expectation, Boolean.valueOf(saved.contains(allDifferent)), saved);
        assertEquals(expectation, Boolean.valueOf(saved.contains(distinct)), saved);
    }
}
