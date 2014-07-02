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
import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.KRSS2DocumentFormat;
import org.semanticweb.owlapi.formats.KRSSDocumentFormat;
import org.semanticweb.owlapi.formats.LabelFunctionalDocumentFormat;
import org.semanticweb.owlapi.formats.LatexAxiomsListDocumentFormat;
import org.semanticweb.owlapi.formats.LatexDocumentFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;

/**
 * Test the generic factory and all the formats it can build
 * 
 * @author ignazio
 */
@SuppressWarnings("javadoc")
public class OWLOntologyFormatFactoryImplTestCase {

    @Test
    public void testDLSyntaxHTMLFormat() {
        Class<DLSyntaxHTMLDocumentFormat> format = DLSyntaxHTMLDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("DL Syntax - HTML Format", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testDLSyntaxOntologyFormat() {
        Class<DLSyntaxDocumentFormat> format = DLSyntaxDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("DL Syntax Format", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testKRSS2OntologyFormat() {
        Class<KRSS2DocumentFormat> format = KRSS2DocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("KRSS2 Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testKRSSOntologyFormat() {
        Class<KRSSDocumentFormat> format = KRSSDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("KRSS Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLabelFunctionalFormat() {
        Class<LabelFunctionalDocumentFormat> format = LabelFunctionalDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("Label functional Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLatexAxiomsListOntologyFormat() {
        Class<LatexAxiomsListDocumentFormat> format = LatexAxiomsListDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("Latex Axiom List", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLatexOntologyFormat() {
        Class<LatexDocumentFormat> format = LatexDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("LaTeX Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testOWLFunctionalSyntaxOntologyFormat() {
        Class<FunctionalSyntaxDocumentFormat> format = FunctionalSyntaxDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("OWL Functional Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testOWLXMLOntologyFormat() {
        Class<OWLXMLDocumentFormat> format = OWLXMLDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("OWL/XML Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testRDFXMLOntologyFormat() {
        Class<RDFXMLDocumentFormat> format = RDFXMLDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("RDF/XML Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testTurtleOntologyFormat() {
        Class<TurtleDocumentFormat> format = TurtleDocumentFormat.class;
        OWLDocumentFormatFactory f = new OWLDocumentFormatFactoryImpl<>(format);
        assertEquals("Turtle Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }
}
