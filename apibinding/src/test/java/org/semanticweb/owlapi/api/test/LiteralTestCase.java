/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Jul-2010
 */
public class LiteralTestCase extends AbstractAxiomsRoundTrippingTestCase {


    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        OWLClass cls = getOWLClass("A");
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLAnnotationAssertionAxiom ax = getFactory().getOWLAnnotationAssertionAxiom(prop, cls.getIRI(), literalWithLang);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax);
        axioms.add(getFactory().getOWLDeclarationAxiom(cls));
        return axioms;
    }

    public void testHasLangMethod() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        assertTrue(literalWithLang.hasLang());
        OWLLiteral literalWithoutLang = getFactory().getOWLLiteral("abc", "");
        assertFalse(literalWithoutLang.hasLang());
    }

    public void testGetLangMethod() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        assertEquals(literalWithLang.getLang(), "en");
        OWLLiteral literalWithoutLang = getFactory().getOWLLiteral("abc", "");
        assertEquals(literalWithoutLang.getLang(), "");
    }

    public void testNormalisation() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "EN");
        assertEquals(literalWithLang.getLang(), "en");
        assertTrue(literalWithLang.hasLang("EN"));
    }

    public void testPlainLiteralWithLang() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        assertTrue(literalWithLang.getDatatype().getIRI().isPlainLiteral());
        assertTrue(literalWithLang.isRDFPlainLiteral());
    }

    public void testPlainLiteralWithEmbeddedLang() {
        OWLLiteral literal = getFactory().getOWLLiteral("abc@en", getFactory().getRDFPlainLiteral());
        assertTrue(literal.hasLang());
        assertEquals(literal.getLang(), "en");
        assertEquals(literal.getLiteral(), "abc");
        assertEquals(literal.getDatatype(), getFactory().getRDFPlainLiteral());
    }

    public void tesPlainLiteralWithEmbeddedEmptyLang() {
        OWLLiteral literal = getFactory().getOWLLiteral("abc@", getFactory().getRDFPlainLiteral());
        assertTrue(!literal.hasLang());
        assertEquals(literal.getLang(), "");
        assertEquals(literal.getLiteral(), "abc");
        assertEquals(literal.getDatatype(), getFactory().getRDFPlainLiteral());
    }

    public void tesPlainLiteralWithDoubleSep() {
        OWLLiteral literal = getFactory().getOWLLiteral("abc@@en", getFactory().getRDFPlainLiteral());
        assertEquals(literal.getLang(), "en");
        assertEquals(literal.getLiteral(), "abc@");
        assertEquals(literal.getDatatype(), getFactory().getRDFPlainLiteral());
    }



    public void testBoolean() {
        OWLLiteral literal = getFactory().getOWLLiteral(true);
        assertTrue(literal.isBoolean());
        assertTrue(literal.parseBoolean());

        OWLLiteral trueLiteral = getFactory().getOWLLiteral("true", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(trueLiteral.isBoolean());
        assertTrue(trueLiteral.parseBoolean());


        OWLLiteral falseLiteral = getFactory().getOWLLiteral("false", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(falseLiteral.isBoolean());
        assertTrue(!falseLiteral.parseBoolean());

        OWLLiteral oneLiteral = getFactory().getOWLLiteral("1", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(oneLiteral.isBoolean());
        assertTrue(oneLiteral.parseBoolean());

        OWLLiteral zeroLiteral = getFactory().getOWLLiteral("0", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(zeroLiteral.isBoolean());
        assertTrue(!zeroLiteral.parseBoolean());
    }

}
