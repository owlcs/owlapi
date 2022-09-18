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
package org.semanticweb.owlapi.apitest.baseclasses;

import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apitest.TestEntities.A;
import static org.semanticweb.owlapi.apitest.TestEntities.AP;
import static org.semanticweb.owlapi.apitest.TestEntities.DT;
import static org.semanticweb.owlapi.apitest.TestEntities.P;
import static org.semanticweb.owlapi.apitest.TestEntities.PD;
import static org.semanticweb.owlapi.apitest.TestEntities.i;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@RunWith(Parameterized.class)
public class AnnotatedAxiomRoundtripExceptManchesterSyntaxTestCase extends AnnotatedAxiomRoundTrippingTestCase {

    public AnnotatedAxiomRoundtripExceptManchesterSyntaxTestCase(Function<List<OWLAnnotation>, OWLAxiom> f) {
        super(f);
    }

    @Parameters
    public static List<Function<List<OWLAnnotation>, OWLAxiom>> getData() {
        return Arrays.asList(a -> Declaration(P, a), a -> Declaration(DT, a), a -> Declaration(i, a),
            a -> Declaration(PD, a), a -> Declaration(AP, a), a -> Declaration(A, a));
    }

    @Override
    @Test
    public void testManchesterOWLSyntax() {
        // Can't represent annotated declarations in Manchester Syntax
        // super.testManchesterOWLSyntax();
    }
}