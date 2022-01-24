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
package org.semanticweb.owlapi.api.test.individuals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class DifferentIndividualsPairRoundTripTestCase extends TestBase {

    OWLOntology differentIndividualsPair(OWLAxiom... ax) {
        OWLOntology ont = o(ax);
        ont.getSignature().stream()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, INCLUDED))
            .forEach(entity -> ont.getOWLOntologyManager().addAxiom(ont, Declaration(entity)));
        return ont;
    }

    @ParameterizedTest
    @MethodSource("formats")
    void testFormatSingleTriple(OWLDocumentFormat format) {
        OWLOntology o = differentIndividualsPair(DifferentIndividuals(I, J),
            DifferentIndividuals(k, AnonymousIndividual()));
        String string = saveOntology(o, format).toString();
        assertSingleTriple(Boolean.FALSE, string, format);
        equal(o, roundTrip(o, format));
    }

    @ParameterizedTest
    @MethodSource("formats")
    void testFormatMultipleTriples(OWLDocumentFormat format) {
        OWLOntology o = differentIndividualsPair(DifferentIndividuals(I, J, k),
            DifferentIndividuals(AnonymousIndividual(), l, indA));
        assertSingleTriple(Boolean.TRUE, saveOntology(o, format).toString(), format);
        equal(o, roundTrip(o, format));
    }

    private static void assertSingleTriple(Boolean expectation, String savedOntology,
        OWLDocumentFormat format) {
        switch (format.getClass().getName()) {
            case "org.semanticweb.owlapi.formats.RDFXMLDocumentFormat":
                assertEquals(expectation,
                    Boolean.valueOf(
                        savedOntology.contains("<rdf:type rdf:resource=\"&owl;AllDifferent\"/>")
                            || savedOntology.contains("<rdf:type rdf:resource=\""
                                + OWLRDFVocabulary.OWL_ALL_DIFFERENT.getIRI().toString() + "\"/>")),
                    savedOntology);
                assertEquals(expectation,
                    Boolean.valueOf(
                        savedOntology.contains("<distinctMembers rdf:parseType=\"Collection\">")),
                    savedOntology);
                break;
            case "org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat":
                assertEquals(expectation,
                    Boolean.valueOf(savedOntology.contains("<rdf:type rdf:resource=\""
                        + OWLRDFVocabulary.OWL_ALL_DIFFERENT.getIRI().toString() + "\"/>")),
                    savedOntology);
                assertEquals(expectation,
                    Boolean.valueOf(savedOntology.contains(
                        "<" + OWLRDFVocabulary.OWL_DISTINCT_MEMBERS.getPrefixedName() + " ")),
                    savedOntology);
                break;
            case "org.semanticweb.owlapi.formats.RDFJsonDocumentFormat":
            case "org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat":
            case "org.semanticweb.owlapi.formats.NTriplesDocumentFormat":
            case "org.semanticweb.owlapi.formats.NQuadsDocumentFormat":
                assertEquals(expectation,
                    Boolean.valueOf(savedOntology
                        .contains(OWLRDFVocabulary.OWL_ALL_DIFFERENT.getIRI().toString())),
                    savedOntology);
                assertEquals(expectation,
                    Boolean.valueOf(savedOntology
                        .contains(OWLRDFVocabulary.OWL_DISTINCT_MEMBERS.getIRI().toString())),
                    savedOntology);
                break;
            case "org.semanticweb.owlapi.formats.TurtleDocumentFormat":
            case "org.semanticweb.owlapi.formats.RioTurtleDocumentFormat":
            case "org.semanticweb.owlapi.formats.TrigDocumentFormat":
                assertEquals(expectation,
                    Boolean.valueOf(savedOntology
                        .contains(OWLRDFVocabulary.OWL_ALL_DIFFERENT.getPrefixedName())),
                    savedOntology);
                assertEquals(expectation,
                    Boolean.valueOf(savedOntology
                        .contains(OWLRDFVocabulary.OWL_DISTINCT_MEMBERS.getPrefixedName())),
                    savedOntology);
                break;
            default:
                break;
        }
    }
}
