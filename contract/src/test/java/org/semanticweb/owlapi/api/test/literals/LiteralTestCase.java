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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplNoCompression;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplString;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
class LiteralTestCase extends TestBase {

    private static final OWLDatatype LANG_STRING = Datatype(OWL2Datatype.RDF_LANG_STRING.getIRI());
    private static final String ABC = "abc";
    private static final OWLLiteral LITERAL_ABC = Literal(ABC, "");
    private static final OWLLiteral LITERAL_WITH_LANG = Literal(ABC, "en");

    protected Set<? extends OWLAxiom> createAxioms() {
        OWLLiteral literalWithLang = LITERAL_WITH_LANG;
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(AP, A.getIRI(), literalWithLang);
        Set<OWLAxiom> axioms = new HashSet<>();
        axioms.add(ax);
        axioms.add(Declaration(A));
        return axioms;
    }

    @Test
    void shouldMatchHashCode() {
        OWLLiteral l1 = new OWLLiteralImpl("123", "", String());
        OWLLiteral l2 = new OWLLiteralImplString("123");
        OWLLiteral l3 = new OWLLiteralImplNoCompression("123", "", String());
        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
        assertEquals(l1, l3);
        assertEquals(l1.hashCode(), l3.hashCode());
    }

    @Test
    void testHasLangMethod() {
        assertTrue(LITERAL_WITH_LANG.hasLang());
        assertFalse(LITERAL_ABC.hasLang());
    }

    @Test
    void testGetLangMethod() {
        assertEquals("en", LITERAL_WITH_LANG.getLang());
        assertEquals("", LITERAL_ABC.getLang());
    }

    @Test
    void testNormalisation() {
        OWLLiteral literalWithLang = Literal(ABC, "EN");
        assertEquals("en", literalWithLang.getLang());
        assertTrue(literalWithLang.hasLang("EN"));
    }

    @Test
    void testPlainLiteralWithLang() {
        assertFalse(LITERAL_WITH_LANG.getDatatype().getIRI().isPlainLiteral());
        assertFalse(LITERAL_WITH_LANG.isRDFPlainLiteral());
        assertTrue(LITERAL_WITH_LANG.hasLang());
        assertEquals("en", LITERAL_WITH_LANG.getLang());
        assertEquals(LITERAL_WITH_LANG.getDatatype(), LANG_STRING);
    }

    @Test
    void testPlainLiteralWithEmbeddedLang() {
        OWLLiteral litvalue = Literal("abc@en", PlainLiteral());
        assertTrue(litvalue.hasLang());
        assertFalse(litvalue.isRDFPlainLiteral());
        assertEquals("en", litvalue.getLang());
        assertEquals(ABC, litvalue.getLiteral());
        assertEquals(litvalue.getDatatype(), LANG_STRING);
    }

    @Test
    void tesPlainLiteralWithEmbeddedEmptyLang() {
        OWLLiteral litvalue = Literal("abc@", PlainLiteral());
        assertFalse(litvalue.hasLang());
        assertFalse(litvalue.isRDFPlainLiteral());
        assertEquals("", litvalue.getLang());
        assertEquals(ABC, litvalue.getLiteral());
        assertEquals(litvalue.getDatatype(), LANG_STRING);
    }

    @Test
    void tesPlainLiteralWithDoubleSep() {
        OWLLiteral litvalue = Literal("abc@@en", PlainLiteral());
        assertTrue(litvalue.hasLang());
        assertFalse(litvalue.isRDFPlainLiteral());
        assertEquals("en", litvalue.getLang());
        assertEquals("abc@", litvalue.getLiteral());
        assertEquals(litvalue.getDatatype(), LANG_STRING);
    }

    @Test
    void testBoolean() {
        assertTrue(LIT_TRUE.isBoolean());
        assertTrue(LIT_TRUE.parseBoolean());
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
    void testBuiltInDatatypes() {
        OWL2Datatype dtPlain = OWL2Datatype.getDatatype(OWLRDFVocabulary.RDF_PLAIN_LITERAL);
        assertNotNull(dtPlain);
        dtPlain = OWL2Datatype.getDatatype(OWLRDFVocabulary.RDFS_LITERAL);
        assertNotNull(dtPlain);
        OWLDatatype datatype = Datatype(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        assertNotNull(datatype);
        OWL2Datatype test = datatype.getBuiltInDatatype();
        assertEquals(test, dtPlain);
    }

    @Test
    void testFailure() {
        for (IRI type : OWL2Datatype.getDatatypeIRIs()) {
            OWLDatatype datatype = Datatype(type);
            if (datatype.isBuiltIn()) {
                OWL2Datatype builtInDatatype = datatype.getBuiltInDatatype();
                assertNotNull(builtInDatatype);
            }
        }
    }

    @Test
    void shouldStoreTagsCorrectly() {
        String in = "See more at <a href=\"http://abc.com\">abc</a>";
        OWLAxiom ax = AnnotationAssertion(RDFSComment(), createIndividual().getIRI(), Literal(in));
        OWLOntology o = o(ax);
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        assertTrue(o1.containsAxiom(ax));
        equal(o, o1);
    }

    @Test
    void shouldFindReferencingAxiomsForIntLiteral() {
        OWLLiteral comment = Literal(32);
        OWLAxiom a = SubClassOf(RDFSLabel(Literal("x"), RDFSComment(comment)), A, OWLThing());
        OWLOntology o = o(a);
        assertEquals(1, o.referencingAxioms(comment).count());
    }

    @Test
    void shouldFindReferencingAxiomsForBooleanLiteral() {
        OWLAxiom a = SubClassOf(A, DataHasValue(DP, LIT_TRUE));
        OWLOntology o = o(a);
        assertEquals(1, o.referencingAxioms(LIT_TRUE).count());
    }
}
