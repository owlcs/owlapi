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
package org.semanticweb.owlapi6.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.io.OWLParserFactory;

/**
 * Test the generic factory and all the formats it can build.
 *
 * @author ignazio
 */
@RunWith(Parameterized.class)
public class FormatsMimeTestcase {

    private static final String X_TRIG = "application/x-trig";
    private static final String HTML = "text/html";
    private static final String RDF_N3 = "text/rdf+n3";
    private static final String APP_JSON = "application/ld+json";
    private static final String APP_X_BINARY_RDF = "application/x-binary-rdf";
    private static final String FS = "text/owl-functional";
    private static final String MANCH = "text/owl-manchester";
    private static final String APP_TRIG = "application/trig";
    private static final String PLAIN = "text/plain";
    private static final String NQUADS = "text/nquads";
    private static final String X_NQUADS = "text/x-nquads";
    private static final String APP_HTML = "application/html";
    private static final String APP_XHTML = "application/xhtml+xml";
    private static final String APP_NT = "application/n-triples";
    private static final String APP_OWL_XML = "application/owl+xml";
    private static final String N3 = "text/n3";
    private static final String APP_RDF_JSON = "application/rdf+json";
    private static final String APP_TRIX = "application/trix";
    private static final String APP_X_TURTLE = "application/x-turtle";
    private static final String XML = "text/xml";
    private static final String APP_XML = "application/xml";
    private static final String TURTLE = "text/turtle";
    private static final String APP_N_QUADS = "application/n-quads";
    private static final String APP_RDF_XML = "application/rdf+xml";

    @Parameters(name = "{1}")
    public static List<Object[]> params() {
        return Arrays.asList(
        //@formatter:off
            new Object[] { new org.semanticweb.owlapi6.oboformat.OBOFormatOWLAPIParserFactory(),                           "OBO Format",            null,             Collections.emptyList()},
            new Object[] { new org.semanticweb.owlapi6.krss2.parser.KRSS2OWLParserFactory(),                               "KRSS2 Syntax",          null,             Collections.emptyList()},
            new Object[] { new org.semanticweb.owlapi6.dlsyntax.parser.DLSyntaxOWLParserFactory(),                         "DL Syntax Format",      null,             Collections.emptyList()},
            new Object[] { new org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParserFactory(),                            "RDF/XML Syntax",        APP_RDF_XML,      Arrays.asList(APP_RDF_XML, APP_XML,  XML)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioRDFXMLParserFactory(),                                       "RDF/XML",               APP_RDF_XML,      Arrays.asList(APP_RDF_XML, APP_XML,  XML)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioNQuadsParserFactory(),                                       "N-Quads",               APP_N_QUADS,      Arrays.asList(APP_N_QUADS, X_NQUADS, NQUADS)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioRDFaParserFactory(),                                         "RDFa",                  APP_XHTML,        Arrays.asList(APP_XHTML,   APP_HTML, HTML)},
            new Object[] { new org.semanticweb.owlapi6.rdf.turtle.parser.TurtleOntologyParserFactory(),                    "Turtle Syntax",         TURTLE,           Arrays.asList(TURTLE,      APP_X_TURTLE)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioTurtleParserFactory(),                                       "Turtle",                TURTLE,           Arrays.asList(TURTLE,      APP_X_TURTLE)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioN3ParserFactory(),                                           "N3",                    N3,               Arrays.asList(N3,          RDF_N3)},
            new Object[] { new org.semanticweb.owlapi6.owlxml.parser.OWLXMLParserFactory(),                                "OWL/XML Syntax",        APP_OWL_XML,      Arrays.asList(APP_OWL_XML, XML)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioNTriplesParserFactory(),                                     "N-Triples",             APP_NT,           Arrays.asList(APP_NT,      PLAIN)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioTrigParserFactory(),                                         "TriG",                  APP_TRIG,         Arrays.asList(APP_TRIG,    X_TRIG)},
            new Object[] { new org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory(), "Manchester OWL Syntax", MANCH,            Arrays.asList(MANCH)},
            new Object[] { new org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxOWLParserFactory(),            "OWL Functional Syntax", FS,               Arrays.asList(FS)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioBinaryRdfParserFactory(),                                    "BinaryRDF",             APP_X_BINARY_RDF, Arrays.asList(APP_X_BINARY_RDF)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioJsonLDParserFactory(),                                       "JSON-LD",               APP_JSON,         Arrays.asList(APP_JSON)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioJsonParserFactory(),                                         "RDF/JSON",              APP_RDF_JSON,     Arrays.asList(APP_RDF_JSON)},
            new Object[] { new org.semanticweb.owlapi6.rio.RioTrixParserFactory(),                                         "TriX",                  APP_TRIX,         Arrays.asList(APP_TRIX)}
            //@formatter:on
        );
    }

    @Parameter(0)
    public OWLParserFactory f;
    @Parameter(1)
    public String key;
    @Parameter(2)
    public String defaultmime;
    @Parameter(3)
    public List<String> mimes;

    @Test
    public void shouldMatchExpectedValues() {
        assertEquals(key, f.getSupportedFormat().getKey());
        assertEquals(mimes, f.getMIMETypes());
        assertEquals(defaultmime, f.getDefaultMIMEType());
    }
    // OWLDocumentFormatFactory f = new KRSS2DocumentFormatFactory(); //
    // assertEquals("KRSS2
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new KRSSDocumentFormatFactory(); //
    // assertEquals("KRSS Syntax",
    // f.getKey());
    // OWLDocumentFormatFactory f = new LabelFunctionalDocumentFormatFactory();
    // //
    // assertEquals("Label functional Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new LatexAxiomsListDocumentFormatFactory();
    // //
    // assertEquals("Latex Axiom List", f.getKey());
    // OWLDocumentFormatFactory f = new LatexDocumentFormatFactory(); //
    // assertEquals("LaTeX
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new FunctionalSyntaxDocumentFormatFactory();
    // //
    // assertEquals("OWL Functional Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new OWLXMLDocumentFormatFactory(); //
    // assertEquals("OWL/XML
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new RDFXMLDocumentFormatFactory(); //
    // assertEquals("RDF/XML
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new TurtleDocumentFormatFactory(); //
    // assertEquals("Turtle
    // Syntax", f.getKey());
}
