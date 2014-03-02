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
package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

@SuppressWarnings("javadoc")
public class ManchesterParseErrorTestCase extends TestBase {

    @Test(expected = ParserException.class)
    public void shouldNotParse() {
        parse("p some rdfs:Literal");
        String text1 = "p some Litera";
        parse(text1);
    }

    @Test(expected = ParserException.class)
    public void shouldNotParseToo() {
        parse("p some rdfs:Literal");
        String text1 = "p some Literal";
        parse(text1);
    }

    private OWLClassExpression parse(String text) {
        MockEntityChecker checker = new MockEntityChecker(df);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                df, text);
        parser.setOWLEntityChecker(checker);
        return parser.parseClassExpression();
    }

    /**
     * A very simple entity checker that only understands that "p" is a property
     * and rdfs:Literal is a datatype. He is an extreme simplification of the
     * entity checker that runs when Protege is set to render entities as
     * qnames.
     * 
     * @author tredmond
     */
    private static class MockEntityChecker implements OWLEntityChecker {

        private final OWLDataFactory factory;

        public MockEntityChecker(OWLDataFactory factory) {
            this.factory = factory;
        }

        @Override
        public OWLClass getOWLClass(String name) {
            return null;
        }

        @Override
        public OWLObjectProperty getOWLObjectProperty(String name) {
            return null;
        }

        @Override
        public OWLDataProperty getOWLDataProperty(String name) {
            if (name != null && name.equals("p")) {
                return factory
                        .getOWLDataProperty(IRI("http://protege.org/Test.owl#p"));
            } else {
                return null;
            }
        }

        @Override
        public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            return null;
        }

        @Override
        public OWLNamedIndividual getOWLIndividual(String name) {
            return null;
        }

        @Override
        public OWLDatatype getOWLDatatype(String name) {
            if (name != null && name.equals("rdfs:Literal")) {
                return factory.getTopDatatype();
            } else {
                return null;
            }
        }
    }
}
