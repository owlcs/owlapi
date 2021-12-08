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
package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.api.test.baseclasses.DF.l;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.io.OWLParserFactory;

/**
 * Test the generic factory and all the formats it can build.
 *
 * @author ignazio
 */
class FormatsMimeTestCase {
    private static final String TEXT_XML = "text/xml";
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_OWL_XML = "application/owl+xml";
    private static final String TEXT_TURTLE = "text/turtle";
    private static final String TEXT_OWL_FUNCTIONAL = "text/owl-functional";
    private static final String APPLICATION_RDF_XML = "application/rdf+xml";

    public static Stream<Arguments> params() {
        return Stream.of(
        //@formatter:off
            Arguments.of(new org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParserFactory(),                           "OBO Format",           null,                       l()),
            Arguments.of(new org.semanticweb.owlapi.krss2.parser.KRSS2OWLParserFactory(),                               "KRSS2 Syntax",         null,                       l()),
            Arguments.of(new org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxOWLParserFactory(),                         "DL Syntax Format",     null,                       l()),
            Arguments.of(new org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory(),                            "RDF/XML Syntax",       APPLICATION_RDF_XML,        l(APPLICATION_RDF_XML,      APPLICATION_XML,        TEXT_XML)),
            Arguments.of(new org.semanticweb.owlapi.rio.RioRDFXMLParserFactory(),                                       "RDF/XML",              APPLICATION_RDF_XML,        l(APPLICATION_RDF_XML,      APPLICATION_XML,        TEXT_XML)),
            Arguments.of(new org.semanticweb.owlapi.rio.RioNQuadsParserFactory(),                                       "N-Quads",              "application/n-quads",      l("application/n-quads",    "text/x-nquads",        "text/nquads")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioRDFaParserFactory(),                                         "RDFa",                 "application/xhtml+xml",    l("application/xhtml+xml",  "application/html",     "text/html")),
            Arguments.of(new org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory(),                    "Turtle Syntax",        TEXT_TURTLE,                l(TEXT_TURTLE,              "application/x-turtle")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioTurtleParserFactory(),                                       "Turtle",               TEXT_TURTLE,                l(TEXT_TURTLE,              "application/x-turtle")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioN3ParserFactory(),                                           "N3",                   "text/n3",                  l("text/n3",                "text/rdf+n3")),
            Arguments.of(new org.semanticweb.owlapi.owlxml.parser.OWLXMLParserFactory(),                                "OWL/XML Syntax",       APPLICATION_OWL_XML,        l(APPLICATION_OWL_XML,      TEXT_XML)),
            Arguments.of(new org.semanticweb.owlapi.rio.RioNTriplesParserFactory(),                                     "N-Triples",            "application/n-triples",    l("application/n-triples",  "text/plain")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioTrigParserFactory(),                                         "TriG",                 "application/trig",         l("application/trig",       "application/x-trig")),
            Arguments.of(new org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory(), "Manchester OWL Syntax","text/owl-manchester",      l("text/owl-manchester")),
            Arguments.of(new org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParserFactory(),            "OWL Functional Syntax",TEXT_OWL_FUNCTIONAL,        l(TEXT_OWL_FUNCTIONAL)),
            Arguments.of(new org.semanticweb.owlapi.rio.RioBinaryRdfParserFactory(),                                    "BinaryRDF",            "application/x-binary-rdf", l("application/x-binary-rdf")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioJsonLDParserFactory(),                                       "JSON-LD",              "application/ld+json",      l("application/ld+json")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioJsonParserFactory(),                                         "RDF/JSON",             "application/rdf+json",     l("application/rdf+json")),
            Arguments.of(new org.semanticweb.owlapi.rio.RioTrixParserFactory(),                                         "TriX",                 "application/trix",         l("application/trix"))
            //@formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void shouldMatchExpectedValues(OWLParserFactory f, String key, String defaultmime,
        List<String> mimes) {
        assertEquals(key, f.getSupportedFormat().getKey());
        assertEquals(mimes, f.getMIMETypes());
        assertEquals(defaultmime, f.getDefaultMIMEType());
    }
}
