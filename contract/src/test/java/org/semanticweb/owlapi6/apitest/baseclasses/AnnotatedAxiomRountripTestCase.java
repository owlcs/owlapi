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
package org.semanticweb.owlapi6.apitest.baseclasses;

import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubPropertyChainOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.D;
import static org.semanticweb.owlapi6.apitest.TestEntities.DP;
import static org.semanticweb.owlapi6.apitest.TestEntities.DQ;
import static org.semanticweb.owlapi6.apitest.TestEntities.DR;
import static org.semanticweb.owlapi6.apitest.TestEntities.I;
import static org.semanticweb.owlapi6.apitest.TestEntities.J;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.apitest.TestEntities.Q;
import static org.semanticweb.owlapi6.apitest.TestEntities.R;
import static org.semanticweb.owlapi6.apitest.TestEntities.i;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@RunWith(Parameterized.class)
public class AnnotatedAxiomRountripTestCase extends AnnotatedAxiomRoundTrippingTestCase {

    public AnnotatedAxiomRountripTestCase(Function<List<OWLAnnotation>, OWLAxiom> f) {
        super(f);
    }

    @Parameters
    public static List<Function<List<OWLAnnotation>, OWLAxiom>> getData() {
        return Arrays.asList(a -> AsymmetricObjectProperty(P, a), a -> ClassAssertion(A, I, a),
            a -> DataPropertyAssertion(DP, I, Literal("xyz"), a), a -> DataPropertyDomain(DP, A, a),
            a -> DataPropertyRange(DP, TopDatatype(), a), a -> DisjointClasses(a, A, B),
            a -> DisjointClasses(a, A, B, C, D), a -> DisjointDataProperties(a, DP, DQ),
            a -> DisjointDataProperties(a, DP, DQ, DR), a -> DisjointObjectProperties(a, P, Q),
            a -> DisjointObjectProperties(a, P, Q, R), a -> EquivalentClasses(a, A, B),
            a -> EquivalentDataProperties(a, DP, DQ), a -> EquivalentObjectProperties(a, P, Q),
            a -> FunctionalDataProperty(DP, a), a -> FunctionalObjectProperty(P, a),
            a -> InverseFunctionalObjectProperty(P, a), a -> IrreflexiveObjectProperty(P, a),
            a -> NegativeDataPropertyAssertion(DP, I, Literal("xyz"), a),
            a -> NegativeObjectPropertyAssertion(P, I, J, a),
            a -> ObjectPropertyAssertion(P, I, J, a), a -> ObjectPropertyDomain(P, A, a),
            a -> ObjectPropertyRange(P, A, a), a -> ReflexiveObjectProperty(P, a),
            a -> df.getOWLSubClassOfAxiom(A, B, a), a -> SubDataPropertyOf(DP, DQ, a),
            a -> SubObjectPropertyOf(P, Q, a), a -> SymmetricObjectProperty(P, a),
            a -> TransitiveObjectProperty(P, a), a -> SubPropertyChainOf(Arrays.asList(P, Q), R, a),
            a -> DifferentIndividuals(Arrays.asList(i, I, J), a),
            a -> DifferentIndividuals(Arrays.asList(I, J), a),
            a -> DifferentIndividuals(Arrays.asList(i, I, J), a),
            a -> DifferentIndividuals(Arrays.asList(I, J), a));
    }
}
