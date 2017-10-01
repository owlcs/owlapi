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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.io.OWLParserFactory;

/**
 * Test the generic factory and all the formats it can build.
 *
 * @author ignazio
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class FormatsMimeTestcase {
    @Parameters(name = "{1}")
    public static List<Object[]> params() {
        return Arrays.asList(
            //@formatter:off
            new Object[] { new org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParserFactory(),                           "OBO Format",             null,                       Collections.emptyList()},
            new Object[] { new org.semanticweb.owlapi.krss2.parser.KRSS2OWLParserFactory(),                               "KRSS2 Syntax",           null,                       Collections.emptyList()},
            new Object[] { new org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxOWLParserFactory(),                         "DL Syntax Format",       null,                       Collections.emptyList()},
            new Object[] { new org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory(),                            "RDF/XML Syntax",         "application/rdf+xml",      Arrays.asList("application/rdf+xml",    "application/xml",  "text/xml")},
            new Object[] { new org.semanticweb.owlapi.rio.RioRDFXMLParserFactory(),                                       "RDF/XML",                "application/rdf+xml",      Arrays.asList("application/rdf+xml",    "application/xml",  "text/xml")},
            new Object[] { new org.semanticweb.owlapi.rio.RioNQuadsParserFactory(),                                       "N-Quads",                "application/n-quads",      Arrays.asList("application/n-quads",    "text/x-nquads",    "text/nquads")},
            new Object[] { new org.semanticweb.owlapi.rio.RioRDFaParserFactory(),                                         "RDFa",                   "application/xhtml+xml",    Arrays.asList("application/xhtml+xml",  "application/html", "text/html")},
            new Object[] { new org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory(),                    "Turtle Syntax",          "text/turtle",              Arrays.asList("text/turtle",            "application/x-turtle")},
            new Object[] { new org.semanticweb.owlapi.rio.RioTurtleParserFactory(),                                       "Turtle",                 "text/turtle",              Arrays.asList("text/turtle",            "application/x-turtle")},
            new Object[] { new org.semanticweb.owlapi.rio.RioN3ParserFactory(),                                           "N3",                     "text/n3",                  Arrays.asList("text/n3",                "text/rdf+n3")},
            new Object[] { new org.semanticweb.owlapi.owlxml.parser.OWLXMLParserFactory(),                                "OWL/XML Syntax",         "application/owl+xml",      Arrays.asList("application/owl+xml",    "text/xml")},
            new Object[] { new org.semanticweb.owlapi.rio.RioNTriplesParserFactory(),                                     "N-Triples",              "application/n-triples",    Arrays.asList("application/n-triples",  "text/plain")},
            new Object[] { new org.semanticweb.owlapi.rio.RioTrigParserFactory(),                                         "TriG",                   "application/trig",         Arrays.asList("application/trig",       "application/x-trig")},
            new Object[] { new org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory(), "Manchester OWL Syntax",  "text/owl-manchester",      Arrays.asList("text/owl-manchester")},
            new Object[] { new org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParserFactory(),            "OWL Functional Syntax",  "text/owl-functional",      Arrays.asList("text/owl-functional")},
            new Object[] { new org.semanticweb.owlapi.rio.RioBinaryRdfParserFactory(),                                    "BinaryRDF",              "application/x-binary-rdf", Arrays.asList("application/x-binary-rdf")},
            new Object[] { new org.semanticweb.owlapi.rio.RioJsonLDParserFactory(),                                       "JSON-LD",                "application/ld+json",      Arrays.asList("application/ld+json")},
            new Object[] { new org.semanticweb.owlapi.rio.RioJsonParserFactory(),                                         "RDF/JSON",               "application/rdf+json",     Arrays.asList("application/rdf+json")},
            new Object[] { new org.semanticweb.owlapi.rio.RioTrixParserFactory(),                                         "TriX",                   "application/trix",         Arrays.asList("application/trix")}
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

    // OWLDocumentFormatFactory f = new KRSS2DocumentFormatFactory(); // assertEquals("KRSS2
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new KRSSDocumentFormatFactory(); // assertEquals("KRSS Syntax",
    // f.getKey());
    // OWLDocumentFormatFactory f = new LabelFunctionalDocumentFormatFactory(); //
    // assertEquals("Label functional Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new LatexAxiomsListDocumentFormatFactory(); //
    // assertEquals("Latex Axiom List", f.getKey());
    // OWLDocumentFormatFactory f = new LatexDocumentFormatFactory(); // assertEquals("LaTeX
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new FunctionalSyntaxDocumentFormatFactory(); //
    // assertEquals("OWL Functional Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new OWLXMLDocumentFormatFactory(); // assertEquals("OWL/XML
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new RDFXMLDocumentFormatFactory(); // assertEquals("RDF/XML
    // Syntax", f.getKey());
    // OWLDocumentFormatFactory f = new TurtleDocumentFormatFactory(); // assertEquals("Turtle
    // Syntax", f.getKey());
}
