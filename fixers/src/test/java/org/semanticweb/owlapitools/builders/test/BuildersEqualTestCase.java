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
package org.semanticweb.owlapitools.builders.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapitools.builders.test.BuildersTestUtil.*;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapitools.builders.*;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class BuildersEqualTestCase {

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Map<Builder<?>, Object> map = new LinkedHashMap<>();
        map.put(new BuilderAnnotation(df.getOWLAnnotation(ap, lit), df), df.getOWLAnnotation(ap, lit));
        map.put(new BuilderAnnotationAssertion(df.getOWLAnnotationAssertionAxiom(ap, iri, lit, annotations), df),
                df.getOWLAnnotationAssertionAxiom(ap, iri, lit, annotations));
        map.put(new BuilderAnnotationProperty(df.getOWLAnnotationProperty(iri), df), df.getOWLAnnotationProperty(iri));
        map.put(new BuilderAnnotationPropertyDomain(df.getOWLAnnotationPropertyDomainAxiom(ap, iri, annotations), df),
                df.getOWLAnnotationPropertyDomainAxiom(ap, iri, annotations));
        map.put(new BuilderAnnotationPropertyRange(df.getOWLAnnotationPropertyRangeAxiom(ap, iri, annotations), df),
                df.getOWLAnnotationPropertyRangeAxiom(ap, iri, annotations));
        map.put(new BuilderAnonymousIndividual(df.getOWLAnonymousIndividual("id"), df),
                df.getOWLAnonymousIndividual("id"));
        map.put(new BuilderAsymmetricObjectProperty(df.getOWLAsymmetricObjectPropertyAxiom(op, annotations), df),
                df.getOWLAsymmetricObjectPropertyAxiom(op, annotations));
        map.put(new BuilderSWRLDataRangeAtom(df.getSWRLDataRangeAtom(d, var1), df), df.getSWRLDataRangeAtom(d, var1));
        map.put(new BuilderSWRLDifferentIndividualsAtom(df.getSWRLDifferentIndividualsAtom(var2, var2), df),
                df.getSWRLDifferentIndividualsAtom(var2, var2));
        map.put(new BuilderSWRLIndividualArgument(df.getSWRLIndividualArgument(i), df),
                df.getSWRLIndividualArgument(i));
        map.put(new BuilderSWRLLiteralArgument(df.getSWRLLiteralArgument(lit), df), df.getSWRLLiteralArgument(lit));
        map.put(new BuilderSWRLObjectPropertyAtom(df.getSWRLObjectPropertyAtom(op, var2, var2), df),
                df.getSWRLObjectPropertyAtom(op, var2, var2));
        map.put(new BuilderSWRLRule(df.getSWRLRule(body, head), df), df.getSWRLRule(body, head));
        map.put(new BuilderSWRLSameIndividualAtom(
                df.getSWRLSameIndividualAtom(df.getSWRLIndividualArgument(i), df.getSWRLIndividualArgument(i)), df),
                df.getSWRLSameIndividualAtom(df.getSWRLIndividualArgument(i), df.getSWRLIndividualArgument(i)));
        map.put(new BuilderSWRLVariable(df.getSWRLVariable(iri), df), df.getSWRLVariable(iri));
        map.put(new BuilderSymmetricObjectProperty(df.getOWLSymmetricObjectPropertyAxiom(op, annotations), df),
                df.getOWLSymmetricObjectPropertyAxiom(op, annotations));
        map.put(new BuilderTransitiveObjectProperty(df.getOWLTransitiveObjectPropertyAxiom(op, annotations), df),
                df.getOWLTransitiveObjectPropertyAxiom(op, annotations));
        map.put(new BuilderUnionOf(df.getOWLObjectUnionOf(classes), df), df.getOWLObjectUnionOf(classes));
        map.put(new BuilderClass(df.getOWLClass(iri), df), df.getOWLClass(iri));
        map.put(new BuilderClassAssertion(df.getOWLClassAssertionAxiom(ce, i, annotations), df),
                df.getOWLClassAssertionAxiom(ce, i, annotations));
        map.put(new BuilderComplementOf(df.getOWLObjectComplementOf(ce), df), df.getOWLObjectComplementOf(ce));
        map.put(new BuilderDataAllValuesFrom(df.getOWLDataAllValuesFrom(dp, d), df), df.getOWLDataAllValuesFrom(dp, d));
        map.put(new BuilderDataComplementOf(df.getOWLDataComplementOf(d), df), df.getOWLDataComplementOf(d));
        map.put(new BuilderDataExactCardinality(df.getOWLDataExactCardinality(1, dp, d), df),
                df.getOWLDataExactCardinality(1, dp, d));
        map.put(new BuilderDataHasValue(df.getOWLDataHasValue(dp, lit), df), df.getOWLDataHasValue(dp, lit));
        map.put(new BuilderDataIntersectionOf(df.getOWLDataIntersectionOf(d, df.getFloatOWLDatatype()), df),
                df.getOWLDataIntersectionOf(d, df.getFloatOWLDatatype()));
        map.put(new BuilderDataMaxCardinality(df.getOWLDataMaxCardinality(1, dp, d), df),
                df.getOWLDataMaxCardinality(1, dp, d));
        map.put(new BuilderDataMinCardinality(df.getOWLDataMinCardinality(1, dp, d), df),
                df.getOWLDataMinCardinality(1, dp, d));
        map.put(new BuilderDataOneOf(df.getOWLDataOneOf(lit), df), df.getOWLDataOneOf(lit));
        map.put(new BuilderDataProperty(df.getOWLDataProperty(iri), df), df.getOWLDataProperty(iri));
        map.put(new BuilderDataPropertyAssertion(df.getOWLDataPropertyAssertionAxiom(dp, i, lit, annotations), df),
                df.getOWLDataPropertyAssertionAxiom(dp, i, lit, annotations));
        map.put(new BuilderDataPropertyDomain(df.getOWLDataPropertyDomainAxiom(dp, ce, annotations), df),
                df.getOWLDataPropertyDomainAxiom(dp, ce, annotations));
        map.put(new BuilderDataPropertyRange(df.getOWLDataPropertyRangeAxiom(dp, d, annotations), df),
                df.getOWLDataPropertyRangeAxiom(dp, d, annotations));
        map.put(new BuilderDataSomeValuesFrom(df.getOWLDataSomeValuesFrom(dp, d), df),
                df.getOWLDataSomeValuesFrom(dp, d));
        map.put(new BuilderDatatype(df.getOWLDatatype(iri), df), df.getOWLDatatype(iri));
        map.put(new BuilderDatatypeDefinition(
                df.getOWLDatatypeDefinitionAxiom(d, df.getDoubleOWLDatatype(), annotations), df),
                df.getOWLDatatypeDefinitionAxiom(d, df.getDoubleOWLDatatype(), annotations));
        OWLFacetRestriction r = df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH, lit);
        map.put(new BuilderDatatypeRestriction(df.getOWLDatatypeRestriction(d, r), df),
                df.getOWLDatatypeRestriction(d, r));
        map.put(new BuilderDataUnionOf(df.getOWLDataUnionOf(d, df.getDoubleOWLDatatype()), df),
                df.getOWLDataUnionOf(d, df.getDoubleOWLDatatype()));
        map.put(new BuilderDeclaration(df.getOWLDeclarationAxiom(ce, annotations), df),
                df.getOWLDeclarationAxiom(ce, annotations));
        map.put(new BuilderDifferentIndividuals(df.getOWLDifferentIndividualsAxiom(i, df.getOWLNamedIndividual(iri)),
                df), df.getOWLDifferentIndividualsAxiom(i, df.getOWLNamedIndividual(iri)));
        map.put(new BuilderDisjointClasses(df.getOWLDisjointClassesAxiom(ce, df.getOWLClass(iri)), df),
                df.getOWLDisjointClassesAxiom(ce, df.getOWLClass(iri)));
        map.put(new BuilderDisjointDataProperties(df.getOWLDisjointDataPropertiesAxiom(dps, annotations), df),
                df.getOWLDisjointDataPropertiesAxiom(dps, annotations));
        map.put(new BuilderDisjointObjectProperties(df.getOWLDisjointObjectPropertiesAxiom(ops, annotations), df),
                df.getOWLDisjointObjectPropertiesAxiom(ops, annotations));
        map.put(new BuilderDisjointUnion(df.getOWLDisjointUnionAxiom(ce, classes, annotations), df),
                df.getOWLDisjointUnionAxiom(ce, classes, annotations));
        map.put(new BuilderEntity(df.getOWLClass(iri), df), df.getOWLClass(iri));
        map.put(new BuilderEquivalentClasses(df.getOWLEquivalentClassesAxiom(classes, annotations), df),
                df.getOWLEquivalentClassesAxiom(classes, annotations));
        map.put(new BuilderEquivalentDataProperties(df.getOWLEquivalentDataPropertiesAxiom(dps, annotations), df),
                df.getOWLEquivalentDataPropertiesAxiom(dps, annotations));
        map.put(new BuilderEquivalentObjectProperties(df.getOWLEquivalentObjectPropertiesAxiom(ops, annotations), df),
                df.getOWLEquivalentObjectPropertiesAxiom(ops, annotations));
        map.put(new BuilderFacetRestriction(df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, lit), df),
                df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, lit));
        map.put(new BuilderFunctionalDataProperty(df.getOWLFunctionalDataPropertyAxiom(dp, annotations), df),
                df.getOWLFunctionalDataPropertyAxiom(dp, annotations));
        map.put(new BuilderFunctionalObjectProperty(df.getOWLFunctionalObjectPropertyAxiom(op, annotations), df),
                df.getOWLFunctionalObjectPropertyAxiom(op, annotations));
        map.put(new BuilderHasKey(df.getOWLHasKeyAxiom(ce, ops, annotations), df),
                df.getOWLHasKeyAxiom(ce, ops, annotations));
        map.put(new BuilderImportsDeclaration(df.getOWLImportsDeclaration(iri), df), df.getOWLImportsDeclaration(iri));
        map.put(new BuilderInverseFunctionalObjectProperty(
                df.getOWLInverseFunctionalObjectPropertyAxiom(op, annotations), df),
                df.getOWLInverseFunctionalObjectPropertyAxiom(op, annotations));
        map.put(new BuilderInverseObjectProperties(df.getOWLInverseObjectPropertiesAxiom(op, op, annotations), df),
                df.getOWLInverseObjectPropertiesAxiom(op, op, annotations));
        map.put(new BuilderIrreflexiveObjectProperty(df.getOWLIrreflexiveObjectPropertyAxiom(op, annotations), df),
                df.getOWLIrreflexiveObjectPropertyAxiom(op, annotations));
        map.put(new BuilderLiteral(df.getOWLLiteral(true), df), df.getOWLLiteral(true));
        map.put(new BuilderLiteral(df.getOWLLiteral(1), df), df.getOWLLiteral(1));
        map.put(new BuilderLiteral(df.getOWLLiteral(3.14D), df), df.getOWLLiteral(3.14D));
        map.put(new BuilderLiteral(df.getOWLLiteral(3.14F), df), df.getOWLLiteral(3.14F));
        map.put(new BuilderLiteral(df.getOWLLiteral("test"), df), df.getOWLLiteral("test"));
        map.put(new BuilderLiteral(df.getOWLLiteral("test", OWL2Datatype.XSD_STRING), df),
                df.getOWLLiteral("test", OWL2Datatype.XSD_STRING));
        map.put(new BuilderLiteral(df.getOWLLiteral("3.14", OWL2Datatype.OWL_REAL), df),
                df.getOWLLiteral("3.14", OWL2Datatype.OWL_REAL));
        map.put(new BuilderNamedIndividual(df.getOWLNamedIndividual(iri), df), df.getOWLNamedIndividual(iri));
        map.put(new BuilderNegativeDataPropertyAssertion(
                df.getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit, annotations), df),
                df.getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit, annotations));
        map.put(new BuilderNegativeObjectPropertyAssertion(
                df.getOWLNegativeObjectPropertyAssertionAxiom(op, i, i, annotations), df),
                df.getOWLNegativeObjectPropertyAssertionAxiom(op, i, i, annotations));
        map.put(new BuilderObjectAllValuesFrom(df.getOWLObjectAllValuesFrom(op, ce), df),
                df.getOWLObjectAllValuesFrom(op, ce));
        map.put(new BuilderObjectExactCardinality(df.getOWLObjectExactCardinality(1, op, ce), df),
                df.getOWLObjectExactCardinality(1, op, ce));
        map.put(new BuilderObjectHasSelf(df.getOWLObjectHasSelf(op), df), df.getOWLObjectHasSelf(op));
        map.put(new BuilderObjectHasValue(df.getOWLObjectHasValue(op, i), df), df.getOWLObjectHasValue(op, i));
        map.put(new BuilderObjectIntersectionOf(df.getOWLObjectIntersectionOf(classes), df),
                df.getOWLObjectIntersectionOf(classes));
        map.put(new BuilderObjectInverseOf(df.getOWLObjectInverseOf(op), df), df.getOWLObjectInverseOf(op));
        map.put(new BuilderObjectMaxCardinality(df.getOWLObjectMaxCardinality(1, op, ce), df),
                df.getOWLObjectMaxCardinality(1, op, ce));
        map.put(new BuilderObjectMinCardinality(df.getOWLObjectMinCardinality(1, op, ce), df),
                df.getOWLObjectMinCardinality(1, op, ce));
        map.put(new BuilderObjectProperty(df.getOWLObjectProperty(iri), df), df.getOWLObjectProperty(iri));
        map.put(new BuilderObjectPropertyAssertion(df.getOWLObjectPropertyAssertionAxiom(op, i, i, annotations), df),
                df.getOWLObjectPropertyAssertionAxiom(op, i, i, annotations));
        map.put(new BuilderObjectPropertyDomain(df.getOWLObjectPropertyDomainAxiom(op, ce, annotations), df),
                df.getOWLObjectPropertyDomainAxiom(op, ce, annotations));
        map.put(new BuilderObjectPropertyRange(df.getOWLObjectPropertyRangeAxiom(op, ce, annotations), df),
                df.getOWLObjectPropertyRangeAxiom(op, ce, annotations));
        map.put(new BuilderObjectSomeValuesFrom(df.getOWLObjectSomeValuesFrom(op, ce), df),
                df.getOWLObjectSomeValuesFrom(op, ce));
        map.put(new BuilderOneOf(df.getOWLObjectOneOf(i), df), df.getOWLObjectOneOf(i));
        List<OWLObjectProperty> chain = new ArrayList<>(ops);
        map.put(new BuilderPropertyChain(df.getOWLSubPropertyChainOfAxiom(chain, op, annotations), df),
                df.getOWLSubPropertyChainOfAxiom(chain, op, annotations));
        map.put(new BuilderReflexiveObjectProperty(df.getOWLReflexiveObjectPropertyAxiom(op, annotations), df),
                df.getOWLReflexiveObjectPropertyAxiom(op, annotations));
        map.put(new BuilderSameIndividual(df.getOWLSameIndividualAxiom(inds, annotations), df),
                df.getOWLSameIndividualAxiom(inds, annotations));
        map.put(new BuilderSubAnnotationPropertyOf(
                df.getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(), annotations), df),
                df.getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(), annotations));
        map.put(new BuilderSubClass(df.getOWLSubClassOfAxiom(ce, df.getOWLThing(), annotations), df),
                df.getOWLSubClassOfAxiom(ce, df.getOWLThing(), annotations));
        map.put(new BuilderSubDataProperty(df.getOWLSubDataPropertyOfAxiom(dp, df.getOWLTopDataProperty()), df),
                df.getOWLSubDataPropertyOfAxiom(dp, df.getOWLTopDataProperty()));
        map.put(new BuilderSubObjectProperty(
                df.getOWLSubObjectPropertyOfAxiom(op, df.getOWLTopObjectProperty(), annotations), df),
                df.getOWLSubObjectPropertyOfAxiom(op, df.getOWLTopObjectProperty(), annotations));
        map.put(new BuilderSWRLBuiltInAtom(df.getSWRLBuiltInAtom(iri, Arrays.asList(var1)), df),
                df.getSWRLBuiltInAtom(iri, Arrays.asList(var1)));
        map.put(new BuilderSWRLClassAtom(df.getSWRLClassAtom(ce, var2), df), df.getSWRLClassAtom(ce, var2));
        map.put(new BuilderSWRLDataPropertyAtom(df.getSWRLDataPropertyAtom(dp, var2, var1), df),
                df.getSWRLDataPropertyAtom(dp, var2, var1));
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    private final Builder<?> b;
    private final Object expected;

    public BuildersEqualTestCase(Builder<?> b, Object o) {
        this.b = b;
        expected = o;
    }

    @Test
    public void shouldTest() {
        assertEquals(expected, b.buildObject());
    }
}
