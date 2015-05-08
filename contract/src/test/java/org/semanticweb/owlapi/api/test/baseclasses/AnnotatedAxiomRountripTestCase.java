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
import java.util.Set;
import java.util.function.Function;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class AnnotatedAxiomRountripTestCase
    extends AnnotatedAxiomRoundTrippingTestCase {

    public AnnotatedAxiomRountripTestCase(
        Function<Set<OWLAnnotation>, OWLAxiom> f) {
        super(f);
    }

    @Parameters
    public static List<Function<Set<OWLAnnotation>, OWLAxiom>> getData() {
        return Arrays.asList(
            a -> AsymmetricObjectProperty(ObjectProperty(iri("p")), a),
            a -> ClassAssertion(Class(iri("A")), NamedIndividual(iri("i")), a),
            a -> DataPropertyAssertion(DataProperty(iri("p")),
                NamedIndividual(iri("i")), Literal("xyz"), a),
            a -> DataPropertyDomain(DataProperty(iri("p")), Class(iri("A")), a),
            a -> DataPropertyRange(DataProperty(iri("p")), TopDatatype(), a),
            a -> DisjointClasses(a, Class(iri("A")), Class(iri("B"))),
            a -> DisjointClasses(a, Class(iri("A")), Class(iri("B")),
                Class(iri("C")), Class(iri("D"))),
            a -> DisjointDataProperties(a, DataProperty(iri("p")),
                DataProperty(iri("q"))),
            a -> DisjointDataProperties(a, DataProperty(iri("p")),
                DataProperty(iri("q")), DataProperty(iri("r"))),
            a -> DisjointObjectProperties(a, ObjectProperty(iri("p")),
                ObjectProperty(iri("q"))),
            a -> DisjointObjectProperties(a, ObjectProperty(iri("p")),
                ObjectProperty(iri("q")), ObjectProperty(iri("r"))),
            a -> EquivalentClasses(a, Class(iri("A")), Class(iri("B"))),
            a -> EquivalentDataProperties(a, DataProperty(iri("p")),
                DataProperty(iri("q"))),
            a -> EquivalentObjectProperties(a, ObjectProperty(iri("p")),
                ObjectProperty(iri("q"))),
            a -> FunctionalDataProperty(DataProperty(iri("p")), a),
            a -> FunctionalObjectProperty(ObjectProperty(iri("p")), a),
            a -> InverseFunctionalObjectProperty(ObjectProperty(iri("p")), a),
            a -> IrreflexiveObjectProperty(ObjectProperty(iri("p")), a),
            a -> NegativeDataPropertyAssertion(DataProperty(iri("p")),
                NamedIndividual(iri("i")), Literal("xyz"), a),
            a -> NegativeObjectPropertyAssertion(ObjectProperty(iri("p")),
                NamedIndividual(iri("i")), NamedIndividual(iri("j")), a),
            a -> ObjectPropertyAssertion(ObjectProperty(iri("p")),
                NamedIndividual(iri("i")), NamedIndividual(iri("j")), a),
            a -> ObjectPropertyDomain(ObjectProperty(iri("p")), Class(iri("A")),
                a),
            a -> ObjectPropertyRange(ObjectProperty(iri("p")), Class(iri("A")),
                a),
            a -> ReflexiveObjectProperty(ObjectProperty(iri("p")), a),
            a -> df.getOWLSubClassOfAxiom(Class(iri("A")), Class(iri("B")), a),
            a -> SubDataPropertyOf(DataProperty(iri("p")),
                DataProperty(iri("q")), a),
            a -> SubObjectPropertyOf(ObjectProperty(iri("p")),
                ObjectProperty(iri("q")), a),
            a -> SymmetricObjectProperty(ObjectProperty(iri("p")), a),
            a -> TransitiveObjectProperty(ObjectProperty(iri("p")), a),
            a -> SubPropertyChainOf(Arrays.asList(ObjectProperty(iri("p")),
                ObjectProperty(iri("q"))), ObjectProperty(iri("r")), a));
    }
}
