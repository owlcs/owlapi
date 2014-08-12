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
package org.semanticweb.owlapi.manchestersyntax.parser;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.OWLAPIConfigProvider;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLExpressionParser;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class ManchesterOWLSyntaxFramesParser implements
        OWLExpressionParser<Set<OntologyAxiomPair>> {

    @Nonnull
    private final OWLDataFactory dataFactory;
    @Nonnull
    private OWLEntityChecker checker;
    private OWLOntologyChecker ontologyChecker;
    private OWLOntology defaultOntology;

    /**
     * @param dataFactory
     *        the data factory
     * @param checker
     *        the entity checker
     */
    public ManchesterOWLSyntaxFramesParser(@Nonnull OWLDataFactory dataFactory,
            @Nonnull OWLEntityChecker checker) {
        this.dataFactory = dataFactory;
        this.checker = checker;
    }

    @Override
    public void setOWLEntityChecker(@Nonnull OWLEntityChecker entityChecker) {
        checker = entityChecker;
    }

    /**
     * @param ontologyChecker
     *        the ontology checker
     */
    public void setOWLOntologyChecker(
            @Nonnull OWLOntologyChecker ontologyChecker) {
        this.ontologyChecker = ontologyChecker;
    }

    /**
     * @param ontology
     *        the ontology to use
     */
    public void setDefaultOntology(@Nonnull OWLOntology ontology) {
        defaultOntology = ontology;
    }

    @Nonnull
    @Override
    public Set<OntologyAxiomPair> parse(String expression) {
        ManchesterOWLSyntaxParser parser = new ManchesterOWLSyntaxParserImpl(
                new OWLAPIConfigProvider(), dataFactory);
        parser.setOWLEntityChecker(checker);
        parser.setStringToParse(expression);
        parser.setDefaultOntology(verifyNotNull(defaultOntology));
        parser.setOWLOntologyChecker(verifyNotNull(ontologyChecker));
        return parser.parseFrames();
    }
}
