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
package org.coode.owlapi.manchesterowlsyntax;

import java.util.Set;

import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * A parser for the Manchester OWL Syntax. All properties must be defined before
 * they are used. For example, consider the restriction hasPart some Leg. The
 * parser must know in advance whether or not hasPart is an object property or a
 * data property so that Leg gets parsed correctly. In a tool, such as an
 * editor, it is expected that hasPart will already exists as either a data
 * property or an object property. If a complete ontology is being parsed, it is
 * expected that hasPart will have been defined at the top of the file before it
 * is used in any class expressions or property assertions (e.g. ObjectProperty:
 * hasPart)
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 10-Sep-2007
 * @deprecated use org.semanticweb.owlapi.apibinding.OWLManager#
 *             createManchesterParser
 */
@Deprecated
public class ManchesterOWLSyntaxEditorParser extends ManchesterOWLSyntaxParserImpl {

    /**
     * @param dataFactory
     *        dataFactory
     * @param s
     *        s
     */
    public ManchesterOWLSyntaxEditorParser(OWLDataFactory dataFactory, String s) {
        this(new OWLOntologyLoaderConfiguration(), dataFactory, s);
    }

    /**
     * @param configuration
     *        configuration
     * @param dataFactory
     *        dataFactory
     * @param s
     *        s
     */
    public ManchesterOWLSyntaxEditorParser(final OWLOntologyLoaderConfiguration configuration,
            OWLDataFactory dataFactory, String s) {
        super(() -> configuration, dataFactory);
        setStringToParse(s);
    }

    /**
     * @param b
     *        unused
     * @return set of class expressions
     * @deprecated use {@link #parseClassExpressionList()}
     */
    @Deprecated
    public Set<OWLClassExpression> parseClassExpressionList(@SuppressWarnings("unused") boolean b) {
        return parseClassExpressionList();
    }
}
