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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingNoManchesterSyntaxTestCase extends
AxiomsRoundTrippingBase {

    public AxiomsRoundTrippingNoManchesterSyntaxTestCase(AxiomBuilder f) {
        super(f);
    }

    @Override
    public void testManchesterOWLSyntax() throws Exception {
        // no valid Manchester OWL Syntax roundtrip
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
        // AsymmetricObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(AsymmetricObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } ,
        // EquivalentObjectPropertiesWithInverses
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            OWLObjectProperty propA = ObjectProperty(iri("propA"));
            OWLObjectProperty propB = ObjectProperty(iri("propB"));
            axioms.add(EquivalentObjectProperties(propA.getInverseProperty(),
            propB.getInverseProperty()));
            axioms.add(Declaration(propA));
            axioms.add(Declaration(propB));
            return axioms;
        } ,
        // FunctionalObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(FunctionalObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } ,
        // InverseFunctionalObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(InverseFunctionalObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } ,
        // IrreflexiveObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(IrreflexiveObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } ,
        // ObjectPropertyDomainInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(ObjectPropertyDomain(ObjectProperty(iri("p"))
            .getInverseProperty(), Class(iri("A"))));
            return axioms;
        } ,
        // ObjectPropertyRangeInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(ObjectPropertyRange(ObjectProperty(iri("p"))
            .getInverseProperty(), Class(iri("A"))));
            return axioms;
        } ,
        // ReflexiveObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(ReflexiveObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } ,
        // SubObjectPropertyOfInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            OWLObjectPropertyExpression propA = ObjectProperty(iri("p"))
            .getInverseProperty();
            OWLObjectPropertyExpression propB = ObjectProperty(iri("q"))
            .getInverseProperty();
            axioms.add(SubObjectPropertyOf(propA, propB));
            return axioms;
        } ,
        // SymmetricObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(SymmetricObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } ,
        // TransitiveObjectPropertyInverse
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(TransitiveObjectProperty(ObjectProperty(iri("p"))
            .getInverseProperty()));
            return axioms;
        } );
    }

    private static void addAxiomForLiteral(OWLLiteral lit,
    Set<OWLAxiom> axioms) {
        OWLDataProperty prop = DataProperty(iri("p"));
        OWLNamedIndividual ind = NamedIndividual(iri("i"));
        axioms.add(DataPropertyAssertion(prop, ind, lit));
    }
}
