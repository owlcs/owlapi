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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapitools.builders.*;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class BuildersTestCase {

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Map<Builder<?>, Object> map = new LinkedHashMap<>();
        map.put(new BuilderAnnotation(df).withProperty(ap).withValue(lit), df.getOWLAnnotation(ap, lit));
        map.put(new BuilderAnnotationAssertion(df).withAnnotations(annotations).withProperty(ap).withSubject(iri)
                .withValue(lit), df.getOWLAnnotationAssertionAxiom(ap, iri, lit, annotations));
        map.put(new BuilderAnnotationProperty(df).withIRI(iri), df.getOWLAnnotationProperty(iri));
        map.put(new BuilderAnnotationPropertyDomain(df).withProperty(ap).withDomain(iri).withAnnotations(annotations),
                df.getOWLAnnotationPropertyDomainAxiom(ap, iri, annotations));
        map.put(new BuilderAnnotationPropertyRange(df).withProperty(ap).withRange(iri).withAnnotations(annotations),
                df.getOWLAnnotationPropertyRangeAxiom(ap, iri, annotations));
        map.put(new BuilderAnonymousIndividual(df).withId("id"), df.getOWLAnonymousIndividual("id"));
        map.put(new BuilderAsymmetricObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLAsymmetricObjectPropertyAxiom(op, annotations));
        map.put(new BuilderClass(df).withIRI(iri), df.getOWLClass(iri));
        map.put(new BuilderClassAssertion(df).withClass(ce).withIndividual(i).withAnnotations(annotations),
                df.getOWLClassAssertionAxiom(ce, i, annotations));
        map.put(new BuilderComplementOf(df).withClass(ce), df.getOWLObjectComplementOf(ce));
        map.put(new BuilderDataAllValuesFrom(df).withProperty(dp).withRange(d), df.getOWLDataAllValuesFrom(dp, d));
        map.put(new BuilderDataComplementOf(df).withRange(d), df.getOWLDataComplementOf(d));
        map.put(new BuilderDataExactCardinality(df).withCardinality(1).withProperty(dp).withRange(d),
                df.getOWLDataExactCardinality(1, dp, d));
        map.put(new BuilderDataHasValue(df).withProperty(dp).withLiteral(lit), df.getOWLDataHasValue(dp, lit));
        map.put(new BuilderDataIntersectionOf(df).withItem(d).withItem(df.getFloatOWLDatatype()),
                df.getOWLDataIntersectionOf(d, df.getFloatOWLDatatype()));
        map.put(new BuilderDataMaxCardinality(df).withCardinality(1).withProperty(dp).withRange(d),
                df.getOWLDataMaxCardinality(1, dp, d));
        map.put(new BuilderDataMinCardinality(df).withCardinality(1).withProperty(dp).withRange(d),
                df.getOWLDataMinCardinality(1, dp, d));
        map.put(new BuilderDataOneOf(df).withItem(lit), df.getOWLDataOneOf(lit));
        map.put(new BuilderDataProperty(df).withIRI(iri), df.getOWLDataProperty(iri));
        map.put(new BuilderDataPropertyAssertion(df).withProperty(dp).withSubject(i).withValue(lit)
                .withAnnotations(annotations), df.getOWLDataPropertyAssertionAxiom(dp, i, lit, annotations));
        map.put(new BuilderDataPropertyDomain(df).withProperty(dp).withDomain(ce).withAnnotations(annotations),
                df.getOWLDataPropertyDomainAxiom(dp, ce, annotations));
        map.put(new BuilderDataPropertyRange(df).withProperty(dp).withRange(d).withAnnotations(annotations),
                df.getOWLDataPropertyRangeAxiom(dp, d, annotations));
        map.put(new BuilderDataSomeValuesFrom(df).withProperty(dp).withRange(d), df.getOWLDataSomeValuesFrom(dp, d));
        map.put(new BuilderDatatype(df).withIRI(iri).withAnnotations(annotations), df.getOWLDatatype(iri));
        map.put(new BuilderDatatypeDefinition(df).with(d).withType(df.getDoubleOWLDatatype()).withAnnotations(
                annotations), df.getOWLDatatypeDefinitionAxiom(d, df.getDoubleOWLDatatype(), annotations));
        map.put(new BuilderDatatypeRestriction(df).withItem(df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH, lit))
                .withDatatype(d), df.getOWLDatatypeRestriction(d, df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH, lit)));
        map.put(new BuilderDataUnionOf(df).withItem(d).withItem(df.getDoubleOWLDatatype()),
                df.getOWLDataUnionOf(d, df.getDoubleOWLDatatype()));
        map.put(new BuilderDeclaration(df).withEntity(ce).withAnnotations(annotations),
                df.getOWLDeclarationAxiom(ce, annotations));
        map.put(new BuilderDifferentIndividuals(df).withItem(i).withItem(df.getOWLNamedIndividual(iri)),
                df.getOWLDifferentIndividualsAxiom(i, df.getOWLNamedIndividual(iri)));
        map.put(new BuilderDisjointClasses(df).withItem(ce).withItem(df.getOWLClass(iri)),
                df.getOWLDisjointClassesAxiom(ce, df.getOWLClass(iri)));
        map.put(new BuilderDisjointDataProperties(df).withItems(dps).withAnnotations(annotations),
                df.getOWLDisjointDataPropertiesAxiom(dps, annotations));
        map.put(new BuilderDisjointObjectProperties(df).withItems(ops).withAnnotations(annotations),
                df.getOWLDisjointObjectPropertiesAxiom(ops, annotations));
        map.put(new BuilderDisjointUnion(df).withClass(ce).withItems(classes).withAnnotations(annotations),
                df.getOWLDisjointUnionAxiom(ce, classes, annotations));
        map.put(new BuilderEntity(df).withIRI(iri).withType(EntityType.CLASS), df.getOWLClass(iri));
        map.put(new BuilderEquivalentClasses(df).withItems(classes).withAnnotations(annotations),
                df.getOWLEquivalentClassesAxiom(classes, annotations));
        map.put(new BuilderEquivalentDataProperties(df).withItems(dps).withAnnotations(annotations),
                df.getOWLEquivalentDataPropertiesAxiom(dps, annotations));
        map.put(new BuilderEquivalentObjectProperties(df).withItems(ops).withAnnotations(annotations),
                df.getOWLEquivalentObjectPropertiesAxiom(ops, annotations));
        map.put(new BuilderFacetRestriction(df).withLiteral(lit).withFacet(OWLFacet.MAX_EXCLUSIVE),
                df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, lit));
        map.put(new BuilderFunctionalDataProperty(df).withProperty(dp).withAnnotations(annotations),
                df.getOWLFunctionalDataPropertyAxiom(dp, annotations));
        map.put(new BuilderFunctionalObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLFunctionalObjectPropertyAxiom(op, annotations));
        map.put(new BuilderHasKey(df).withAnnotations(annotations).withClass(ce).withItems(ops),
                df.getOWLHasKeyAxiom(ce, ops, annotations));
        map.put(new BuilderImportsDeclaration(df).withImportedOntology(iri), df.getOWLImportsDeclaration(iri));
        map.put(new BuilderInverseFunctionalObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLInverseFunctionalObjectPropertyAxiom(op, annotations));
        map.put(new BuilderInverseObjectProperties(df).withProperty(op).withInverseProperty(op)
                .withAnnotations(annotations), df.getOWLInverseObjectPropertiesAxiom(op, op, annotations));
        map.put(new BuilderIrreflexiveObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLIrreflexiveObjectPropertyAxiom(op, annotations));
        map.put(new BuilderLiteral(df).withValue(true).withAnnotations(annotations), df.getOWLLiteral(true));
        map.put(new BuilderLiteral(df).withValue(1).withAnnotations(annotations), df.getOWLLiteral(1));
        map.put(new BuilderLiteral(df).withValue(1.1D).withAnnotations(annotations), df.getOWLLiteral(1.1D));
        map.put(new BuilderLiteral(df).withValue(1.2F).withAnnotations(annotations), df.getOWLLiteral(1.2F));
        map.put(new BuilderLiteral(df).withLiteralForm("test").withAnnotations(annotations), df.getOWLLiteral("test"));
        map.put(new BuilderLiteral(df).withLiteralForm("test").withLanguage("en").withAnnotations(annotations),
                df.getOWLLiteral("test", "en"));
        map.put(new BuilderLiteral(df).withLiteralForm("test").withDatatype(OWL2Datatype.XSD_STRING)
                .withAnnotations(annotations), df.getOWLLiteral("test", OWL2Datatype.XSD_STRING));
        map.put(new BuilderLiteral(df).withLiteralForm("3.14").withDatatype(OWL2Datatype.OWL_REAL)
                .withAnnotations(annotations), df.getOWLLiteral("3.14", OWL2Datatype.OWL_REAL));
        map.put(new BuilderNamedIndividual(df).withIRI(iri), df.getOWLNamedIndividual(iri));
        map.put(new BuilderNegativeDataPropertyAssertion(df).withAnnotations(annotations).withProperty(dp)
                .withValue(lit).withSubject(i), df.getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit, annotations));
        map.put(new BuilderNegativeObjectPropertyAssertion(df).withAnnotations(annotations).withProperty(op)
                .withValue(i).withSubject(i), df.getOWLNegativeObjectPropertyAssertionAxiom(op, i, i, annotations));
        map.put(new BuilderObjectAllValuesFrom(df).withProperty(op).withRange(ce),
                df.getOWLObjectAllValuesFrom(op, ce));
        map.put(new BuilderObjectExactCardinality(df).withCardinality(1).withProperty(op).withRange(ce),
                df.getOWLObjectExactCardinality(1, op, ce));
        map.put(new BuilderObjectHasSelf(df).withProperty(op), df.getOWLObjectHasSelf(op));
        map.put(new BuilderObjectHasValue(df).withProperty(op).withValue(i), df.getOWLObjectHasValue(op, i));
        map.put(new BuilderObjectIntersectionOf(df).withItems(classes), df.getOWLObjectIntersectionOf(classes));
        map.put(new BuilderObjectInverseOf(df).withProperty(op), df.getOWLObjectInverseOf(op));
        map.put(new BuilderObjectMaxCardinality(df).withCardinality(1).withProperty(op).withRange(ce),
                df.getOWLObjectMaxCardinality(1, op, ce));
        map.put(new BuilderObjectMinCardinality(df).withCardinality(1).withProperty(op).withRange(ce),
                df.getOWLObjectMinCardinality(1, op, ce));
        map.put(new BuilderObjectProperty(df).withIRI(iri), df.getOWLObjectProperty(iri));
        map.put(new BuilderObjectPropertyAssertion(df).withProperty(op).withSubject(i).withValue(i)
                .withAnnotations(annotations), df.getOWLObjectPropertyAssertionAxiom(op, i, i, annotations));
        map.put(new BuilderObjectPropertyDomain(df).withDomain(ce).withProperty(op).withAnnotations(annotations),
                df.getOWLObjectPropertyDomainAxiom(op, ce, annotations));
        map.put(new BuilderObjectPropertyRange(df).withProperty(op).withRange(ce).withAnnotations(annotations),
                df.getOWLObjectPropertyRangeAxiom(op, ce, annotations));
        map.put(new BuilderObjectSomeValuesFrom(df).withProperty(op).withRange(ce),
                df.getOWLObjectSomeValuesFrom(op, ce));
        map.put(new BuilderOneOf(df).withItem(i), df.getOWLObjectOneOf(i));
        map.put(new BuilderPropertyChain(df).withProperty(op).withAnnotations(annotations)
                .withPropertiesInChain(new ArrayList<OWLObjectPropertyExpression>(ops)),
                df.getOWLSubPropertyChainOfAxiom(new ArrayList<OWLObjectPropertyExpression>(ops), op, annotations));
        map.put(new BuilderReflexiveObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLReflexiveObjectPropertyAxiom(op, annotations));
        map.put(new BuilderSameIndividual(df).withItems(inds).withAnnotations(annotations),
                df.getOWLSameIndividualAxiom(inds, annotations));
        map.put(new BuilderSubAnnotationPropertyOf(df).withSub(ap).withSup(df.getRDFSLabel()).withAnnotations(
                annotations), df.getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(), annotations));
        map.put(new BuilderSubClass(df).withAnnotations(annotations).withSub(ce).withSup(df.getOWLThing()),
                df.getOWLSubClassOfAxiom(ce, df.getOWLThing(), annotations));
        map.put(new BuilderSubDataProperty(df).withSub(dp).withSup(df.getOWLTopDataProperty()),
                df.getOWLSubDataPropertyOfAxiom(dp, df.getOWLTopDataProperty()));
        map.put(new BuilderSubObjectProperty(df).withSub(op).withSup(df.getOWLTopObjectProperty()).withAnnotations(
                annotations), df.getOWLSubObjectPropertyOfAxiom(op, df.getOWLTopObjectProperty(), annotations));
        map.put(new BuilderSWRLBuiltInAtom(df).with(iri).with(var1), df.getSWRLBuiltInAtom(iri, Arrays.asList(var1)));
        map.put(new BuilderSWRLClassAtom(df).with(ce).with(var2), df.getSWRLClassAtom(ce, var2));
        map.put(new BuilderSWRLDataPropertyAtom(df).withProperty(dp).with(var2).with(var1),
                df.getSWRLDataPropertyAtom(dp, var2, var1));
        map.put(new BuilderSWRLDataRangeAtom(df).with(d).with(var1), df.getSWRLDataRangeAtom(d, var1));
        map.put(new BuilderSWRLDifferentIndividualsAtom(df).withArg0(var2).withArg1(var2).withAnnotations(annotations),
                df.getSWRLDifferentIndividualsAtom(var2, var2));
        map.put(new BuilderSWRLIndividualArgument(df).with(i), df.getSWRLIndividualArgument(i));
        map.put(new BuilderSWRLLiteralArgument(df).with(lit), df.getSWRLLiteralArgument(lit));
        map.put(new BuilderSWRLObjectPropertyAtom(df).withProperty(op).withArg0(var2).withArg1(var2),
                df.getSWRLObjectPropertyAtom(op, var2, var2));
        map.put(new BuilderSWRLRule(df).withBody(v1).withHead(v2), df.getSWRLRule(body, head));
        map.put(new BuilderSWRLSameIndividualAtom(df).withArg0(df.getSWRLIndividualArgument(i))
                .withArg1(df.getSWRLIndividualArgument(i)),
                df.getSWRLSameIndividualAtom(df.getSWRLIndividualArgument(i), df.getSWRLIndividualArgument(i)));
        map.put(new BuilderSWRLVariable(df).with(iri), df.getSWRLVariable(iri));
        map.put(new BuilderSymmetricObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLSymmetricObjectPropertyAxiom(op, annotations));
        map.put(new BuilderTransitiveObjectProperty(df).withProperty(op).withAnnotations(annotations),
                df.getOWLTransitiveObjectPropertyAxiom(op, annotations));
        map.put(new BuilderUnionOf(df).withItems(classes), df.getOWLObjectUnionOf(classes));
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    private final Builder<?> b;
    private final Object expected;

    public BuildersTestCase(Builder<?> b, Object o) {
        this.b = b;
        expected = o;
    }

    @Test
    public void shouldTest() {
        assertEquals(expected, b.buildObject());
    }
}
