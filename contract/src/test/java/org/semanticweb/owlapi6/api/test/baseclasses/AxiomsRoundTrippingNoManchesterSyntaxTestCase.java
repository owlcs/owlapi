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
package org.semanticweb.owlapi6.api.test.baseclasses;

import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi6.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingNoManchesterSyntaxTestCase extends AxiomsRoundTrippingBase {

    public AxiomsRoundTrippingNoManchesterSyntaxTestCase(AxiomBuilder f) {
        super(f);
    }

    @Override
    public void testManchesterOWLSyntax() {
        // no valid Manchester OWL Syntax roundtrip
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        OWLObjectPropertyExpression p = ObjectProperty(iri("p")).getInverseProperty();
        OWLObjectPropertyExpression q = ObjectProperty(iri("q")).getInverseProperty();
        OWLClass clsA = Class(iri("A"));
        return Arrays.asList(
                        // AsymmetricObjectPropertyInverse
            () -> Arrays.asList(AsymmetricObjectProperty(p)),
                        // EquivalentObjectPropertiesWithInverses
            () -> Arrays.asList(EquivalentObjectProperties(p, q)),
                        // FunctionalObjectPropertyInverse
            () -> Arrays.asList(FunctionalObjectProperty(p)),
                        // InverseFunctionalObjectPropertyInverse
            () -> Arrays.asList(InverseFunctionalObjectProperty(p)),
                        // IrreflexiveObjectPropertyInverse
            () -> Arrays.asList(IrreflexiveObjectProperty(p)),
                        // ObjectPropertyDomainInverse
            () -> Arrays.asList(ObjectPropertyDomain(p, clsA)),
                        // ObjectPropertyRangeInverse
            () -> Arrays.asList(ObjectPropertyRange(p, clsA)),
                        // ReflexiveObjectPropertyInverse
            () -> Arrays.asList(ReflexiveObjectProperty(p)),
                        // SubObjectPropertyOfInverse
            () -> Arrays.asList(SubObjectPropertyOf(p, q)),
                        // SymmetricObjectPropertyInverse
            () -> Arrays.asList(SymmetricObjectProperty(p)),
                        // TransitiveObjectPropertyInverse
            () -> Arrays.asList(TransitiveObjectProperty(p)));
    }
}
