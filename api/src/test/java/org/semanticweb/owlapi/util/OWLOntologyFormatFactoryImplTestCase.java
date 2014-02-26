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

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.annotations.HasIdentifierKey;
import org.semanticweb.owlapi.formats.DLSyntaxHTMLOntologyFormat;
import org.semanticweb.owlapi.formats.DLSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.KRSS2OntologyFormat;
import org.semanticweb.owlapi.formats.KRSSOntologyFormat;
import org.semanticweb.owlapi.formats.LabelFunctionalFormat;
import org.semanticweb.owlapi.formats.LatexAxiomsListOntologyFormat;
import org.semanticweb.owlapi.formats.LatexOntologyFormat;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

/**
 * Test the generic factory and all the formats it can build
 * 
 * @author ignazio
 */
@SuppressWarnings("javadoc")
public class OWLOntologyFormatFactoryImplTestCase {

    @Test
    public void testDLSyntaxHTMLFormat() {
        Class<DLSyntaxHTMLOntologyFormat> format = DLSyntaxHTMLOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<DLSyntaxHTMLOntologyFormat>(
                format);
        assertEquals("DL Syntax - HTML Format",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("DL Syntax - HTML Format", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testDLSyntaxOntologyFormat() {
        Class<DLSyntaxOntologyFormat> format = DLSyntaxOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<DLSyntaxOntologyFormat>(
                format);
        assertEquals("DL Syntax Format",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("DL Syntax Format", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testKRSS2OntologyFormat() {
        Class<KRSS2OntologyFormat> format = KRSS2OntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<KRSS2OntologyFormat>(
                format);
        assertEquals("KRSS2 Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("KRSS2 Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testKRSSOntologyFormat() {
        Class<KRSSOntologyFormat> format = KRSSOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<KRSSOntologyFormat>(
                format);
        assertEquals("KRSS Syntax", format
                .getAnnotation(HasIdentifierKey.class).value());
        assertEquals("KRSS Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLabelFunctionalFormat() {
        Class<LabelFunctionalFormat> format = LabelFunctionalFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<LabelFunctionalFormat>(
                format);
        assertEquals("Label functional Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("Label functional Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLatexAxiomsListOntologyFormat() {
        Class<LatexAxiomsListOntologyFormat> format = LatexAxiomsListOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<LatexAxiomsListOntologyFormat>(
                format);
        assertEquals("Latex Axiom List",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("Latex Axiom List", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLatexOntologyFormat() {
        Class<LatexOntologyFormat> format = LatexOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<LatexOntologyFormat>(
                format);
        assertEquals("LaTeX Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("LaTeX Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testOWLFunctionalSyntaxOntologyFormat() {
        Class<OWLFunctionalSyntaxOntologyFormat> format = OWLFunctionalSyntaxOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<OWLFunctionalSyntaxOntologyFormat>(
                format);
        assertEquals("OWL Functional Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("OWL Functional Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testOWLXMLOntologyFormat() {
        Class<OWLXMLOntologyFormat> format = OWLXMLOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<OWLXMLOntologyFormat>(
                format);
        assertEquals("OWL/XML Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("OWL/XML Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testRDFXMLOntologyFormat() {
        Class<RDFXMLOntologyFormat> format = RDFXMLOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<RDFXMLOntologyFormat>(
                format);
        assertEquals("RDF/XML Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("RDF/XML Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testTurtleOntologyFormat() {
        Class<TurtleOntologyFormat> format = TurtleOntologyFormat.class;
        OWLOntologyFormatFactory f = new OWLOntologyFormatFactoryImpl<TurtleOntologyFormat>(
                format);
        assertEquals("Turtle Syntax",
                format.getAnnotation(HasIdentifierKey.class).value());
        assertEquals("Turtle Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }
}
