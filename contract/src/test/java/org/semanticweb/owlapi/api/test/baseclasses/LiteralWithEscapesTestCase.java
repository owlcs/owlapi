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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.3.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class LiteralWithEscapesTestCase extends AbstractRoundTrippingTestCase {

    private final String escape;

    public LiteralWithEscapesTestCase(String s) {
        escape = s;
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(
                // LiteralWithBackslash
                "\\",
                // LiteralWithDoubleQuote
                "\"",
                // LiteralWithLeftAngle
                "<",
                // LiteralWithNewLine
                "\n",
                // LiteralWithSingleQuote
                "\'");
    }

    @Override
    protected OWLOntology createOntology() {
        OWLClass cls = Class(IRI("http://owlapi.sourceforge.net/ontology#A"));
        OWLAnnotationProperty prop = AnnotationProperty(IRI("http://owlapi.sourceforge.net/ontology#prop"));
        OWLLiteral lit1 = Literal(escape);
        OWLLiteral lit2 = Literal("Start" + escape);
        OWLLiteral lit3 = Literal(escape + "End");
        OWLLiteral lit4 = Literal("Start" + escape + "End");
        OWLAnnotationAssertionAxiom ax1 = AnnotationAssertion(prop, cls.getIRI(), lit1);
        OWLAnnotationAssertionAxiom ax2 = AnnotationAssertion(prop, cls.getIRI(), lit2);
        OWLAnnotationAssertionAxiom ax3 = AnnotationAssertion(prop, cls.getIRI(), lit3);
        OWLAnnotationAssertionAxiom ax4 = AnnotationAssertion(prop, cls.getIRI(), lit4);
        OWLOntology o = getOWLOntology();
        o.add(ax1, ax2, ax3, ax4, Declaration(cls));
        return o;
    }
}
