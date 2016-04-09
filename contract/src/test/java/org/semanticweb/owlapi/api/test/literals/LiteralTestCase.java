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
package org.semanticweb.owlapi.api.test.literals;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractAxiomsRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class LiteralTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Nonnull
    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLLiteral literalWithLang = Literal("abc", "en");
        OWLClass cls = Class(iri("A"));
        OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(prop, cls.getIRI(), literalWithLang);
        Set<OWLAxiom> axioms = new HashSet<>();
        axioms.add(ax);
        axioms.add(Declaration(cls));
        return axioms;
    }

    @Test
    public void testHasLangMethod() {
        OWLLiteral literalWithLang = Literal("abc", "en");
        assertTrue(literalWithLang.hasLang());
        OWLLiteral literalWithoutLang = Literal("abc", "");
        assertFalse(literalWithoutLang.hasLang());
    }

    @Test
    public void testGetLangMethod() {
        OWLLiteral literalWithLang = Literal("abc", "en");
        assertEquals("en", literalWithLang.getLang());
        OWLLiteral literalWithoutLang = Literal("abc", "");
        assertEquals("", literalWithoutLang.getLang());
    }

    @Test
    public void testNormalisation() {
        OWLLiteral literalWithLang = Literal("abc", "EN");
        assertEquals("en", literalWithLang.getLang());
        assertTrue(literalWithLang.hasLang("EN"));
    }

    @Test
    public void testPlainLiteralWithLang() {
        OWLLiteral literalWithLang = Literal("abc", "en");
        assertTrue(literalWithLang.getDatatype().getIRI().isPlainLiteral());
        assertTrue(literalWithLang.isRDFPlainLiteral());
    }

    @Test
    public void testPlainLiteralWithEmbeddedLang() {
        OWLLiteral literal = Literal("abc@en", PlainLiteral());
        assertTrue(literal.hasLang());
        assertEquals("en", literal.getLang());
        assertEquals("abc", literal.getLiteral());
        assertEquals(literal.getDatatype(), PlainLiteral());
    }

    public void tesPlainLiteralWithEmbeddedEmptyLang() {
        OWLLiteral literal = Literal("abc@", PlainLiteral());
        assertFalse(literal.hasLang());
        assertEquals("", literal.getLang());
        assertEquals("abc", literal.getLiteral());
        assertEquals(literal.getDatatype(), PlainLiteral());
    }

    public void tesPlainLiteralWithDoubleSep() {
        OWLLiteral literal = Literal("abc@@en", PlainLiteral());
        assertEquals("en", literal.getLang());
        assertEquals("abc@", literal.getLiteral());
        assertEquals(literal.getDatatype(), PlainLiteral());
    }

    @Test
    public void testBoolean() {
        OWLLiteral literal = Literal(true);
        assertTrue(literal.isBoolean());
        assertTrue(literal.parseBoolean());
        OWLLiteral trueLiteral = Literal("true", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(trueLiteral.isBoolean());
        assertTrue(trueLiteral.parseBoolean());
        OWLLiteral falseLiteral = Literal("false", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(falseLiteral.isBoolean());
        assertFalse(falseLiteral.parseBoolean());
        OWLLiteral oneLiteral = Literal("1", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(oneLiteral.isBoolean());
        assertTrue(oneLiteral.parseBoolean());
        OWLLiteral zeroLiteral = Literal("0", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(zeroLiteral.isBoolean());
        assertFalse(zeroLiteral.parseBoolean());
    }

    @Test
    public void shouldStoreTagsCorrectly() throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = getOWLOntology("urn:test");
        String in = "See more at <a href=\"http://abc.com\">abc</a>";
        OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(df.getRDFSComment(), createIndividual()
            .getIRI(), df.getOWLLiteral(in));
        o.getOWLOntologyManager().addAxiom(o, ax);
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        assertTrue(o1.containsAxiom(ax));
        equal(o, o1);
    }
}
