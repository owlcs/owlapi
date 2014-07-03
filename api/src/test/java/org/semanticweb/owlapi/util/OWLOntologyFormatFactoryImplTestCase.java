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
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.KRSS2DocumentFormatFactory;
import org.semanticweb.owlapi.formats.KRSSDocumentFormatFactory;
import org.semanticweb.owlapi.formats.LabelFunctionalDocumentFormatFactory;
import org.semanticweb.owlapi.formats.LatexAxiomsListDocumentFormatFactory;
import org.semanticweb.owlapi.formats.LatexDocumentFormatFactory;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
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
        OWLDocumentFormatFactory f = new DLSyntaxHTMLDocumentFormatFactory();
        assertEquals("DL Syntax - HTML Format", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testDLSyntaxOntologyFormat() {
        OWLDocumentFormatFactory f = new DLSyntaxDocumentFormatFactory();
        assertEquals("DL Syntax Format", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testKRSS2OntologyFormat() {
        OWLDocumentFormatFactory f = new KRSS2DocumentFormatFactory();
        assertEquals("KRSS2 Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testKRSSOntologyFormat() {
        OWLDocumentFormatFactory f = new KRSSDocumentFormatFactory();
        assertEquals("KRSS Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLabelFunctionalFormat() {
        OWLDocumentFormatFactory f = new LabelFunctionalDocumentFormatFactory();
        assertEquals("Label functional Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLatexAxiomsListOntologyFormat() {
        OWLDocumentFormatFactory f = new LatexAxiomsListDocumentFormatFactory();
        assertEquals("Latex Axiom List", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testLatexOntologyFormat() {
        OWLDocumentFormatFactory f = new LatexDocumentFormatFactory();
        assertEquals("LaTeX Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testOWLFunctionalSyntaxOntologyFormat() {
        OWLDocumentFormatFactory f = new FunctionalSyntaxDocumentFormatFactory();
        assertEquals("OWL Functional Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testOWLXMLOntologyFormat() {
        OWLDocumentFormatFactory f = new OWLXMLDocumentFormatFactory();
        assertEquals("OWL/XML Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testRDFXMLOntologyFormat() {
        OWLDocumentFormatFactory f = new RDFXMLDocumentFormatFactory();
        assertEquals("RDF/XML Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }

    @Test
    public void testTurtleOntologyFormat() {
        OWLDocumentFormatFactory f = new TurtleDocumentFormatFactory();
        assertEquals("Turtle Syntax", f.getKey());
        assertEquals(0, f.getMIMETypes().size());
        assertNull(f.getDefaultMIMEType());
    }
}
