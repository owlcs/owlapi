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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapitools.builders.BuilderAnnotation;
import org.semanticweb.owlapitools.builders.BuilderAnnotationAssertion;
import org.semanticweb.owlapitools.builders.BuilderAnnotationProperty;
import org.semanticweb.owlapitools.builders.BuilderAnnotationPropertyDomain;
import org.semanticweb.owlapitools.builders.BuilderAnnotationPropertyRange;
import org.semanticweb.owlapitools.builders.BuilderAnonymousIndividual;
import org.semanticweb.owlapitools.builders.BuilderAsymmetricObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderClass;
import org.semanticweb.owlapitools.builders.BuilderClassAssertion;
import org.semanticweb.owlapitools.builders.BuilderComplementOf;
import org.semanticweb.owlapitools.builders.BuilderDataAllValuesFrom;
import org.semanticweb.owlapitools.builders.BuilderDataComplementOf;
import org.semanticweb.owlapitools.builders.BuilderDataExactCardinality;
import org.semanticweb.owlapitools.builders.BuilderDataHasValue;
import org.semanticweb.owlapitools.builders.BuilderDataIntersectionOf;
import org.semanticweb.owlapitools.builders.BuilderDataMaxCardinality;
import org.semanticweb.owlapitools.builders.BuilderDataMinCardinality;
import org.semanticweb.owlapitools.builders.BuilderDataOneOf;
import org.semanticweb.owlapitools.builders.BuilderDataProperty;
import org.semanticweb.owlapitools.builders.BuilderDataPropertyAssertion;
import org.semanticweb.owlapitools.builders.BuilderDataPropertyDomain;
import org.semanticweb.owlapitools.builders.BuilderDataPropertyRange;
import org.semanticweb.owlapitools.builders.BuilderDataSomeValuesFrom;
import org.semanticweb.owlapitools.builders.BuilderDataUnionOf;
import org.semanticweb.owlapitools.builders.BuilderDatatype;
import org.semanticweb.owlapitools.builders.BuilderDatatypeDefinition;
import org.semanticweb.owlapitools.builders.BuilderDatatypeRestriction;
import org.semanticweb.owlapitools.builders.BuilderDeclaration;
import org.semanticweb.owlapitools.builders.BuilderDifferentIndividuals;
import org.semanticweb.owlapitools.builders.BuilderDisjointClasses;
import org.semanticweb.owlapitools.builders.BuilderDisjointDataProperties;
import org.semanticweb.owlapitools.builders.BuilderDisjointObjectProperties;
import org.semanticweb.owlapitools.builders.BuilderDisjointUnion;
import org.semanticweb.owlapitools.builders.BuilderEntity;
import org.semanticweb.owlapitools.builders.BuilderEquivalentClasses;
import org.semanticweb.owlapitools.builders.BuilderEquivalentDataProperties;
import org.semanticweb.owlapitools.builders.BuilderEquivalentObjectProperties;
import org.semanticweb.owlapitools.builders.BuilderFacetRestriction;
import org.semanticweb.owlapitools.builders.BuilderFunctionalDataProperty;
import org.semanticweb.owlapitools.builders.BuilderFunctionalObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderHasKey;
import org.semanticweb.owlapitools.builders.BuilderImportsDeclaration;
import org.semanticweb.owlapitools.builders.BuilderInverseFunctionalObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderInverseObjectProperties;
import org.semanticweb.owlapitools.builders.BuilderIrreflexiveObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderLiteral;
import org.semanticweb.owlapitools.builders.BuilderNamedIndividual;
import org.semanticweb.owlapitools.builders.BuilderNegativeDataPropertyAssertion;
import org.semanticweb.owlapitools.builders.BuilderNegativeObjectPropertyAssertion;
import org.semanticweb.owlapitools.builders.BuilderObjectAllValuesFrom;
import org.semanticweb.owlapitools.builders.BuilderObjectExactCardinality;
import org.semanticweb.owlapitools.builders.BuilderObjectHasSelf;
import org.semanticweb.owlapitools.builders.BuilderObjectHasValue;
import org.semanticweb.owlapitools.builders.BuilderObjectIntersectionOf;
import org.semanticweb.owlapitools.builders.BuilderObjectInverseOf;
import org.semanticweb.owlapitools.builders.BuilderObjectMaxCardinality;
import org.semanticweb.owlapitools.builders.BuilderObjectMinCardinality;
import org.semanticweb.owlapitools.builders.BuilderObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderObjectPropertyAssertion;
import org.semanticweb.owlapitools.builders.BuilderObjectPropertyDomain;
import org.semanticweb.owlapitools.builders.BuilderObjectPropertyRange;
import org.semanticweb.owlapitools.builders.BuilderObjectSomeValuesFrom;
import org.semanticweb.owlapitools.builders.BuilderOneOf;
import org.semanticweb.owlapitools.builders.BuilderPropertyChain;
import org.semanticweb.owlapitools.builders.BuilderReflexiveObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderSWRLBuiltInAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLClassAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLDataPropertyAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLDataRangeAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLDifferentIndividualsAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLIndividualArgument;
import org.semanticweb.owlapitools.builders.BuilderSWRLLiteralArgument;
import org.semanticweb.owlapitools.builders.BuilderSWRLObjectPropertyAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLRule;
import org.semanticweb.owlapitools.builders.BuilderSWRLSameIndividualAtom;
import org.semanticweb.owlapitools.builders.BuilderSWRLVariable;
import org.semanticweb.owlapitools.builders.BuilderSameIndividual;
import org.semanticweb.owlapitools.builders.BuilderSubAnnotationPropertyOf;
import org.semanticweb.owlapitools.builders.BuilderSubClass;
import org.semanticweb.owlapitools.builders.BuilderSubDataProperty;
import org.semanticweb.owlapitools.builders.BuilderSubObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderSymmetricObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderTransitiveObjectProperty;
import org.semanticweb.owlapitools.builders.BuilderUnionOf;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

@SuppressWarnings("javadoc")
public class BuildersEqualTestCase {

    private OWLDataFactory df = new OWLDataFactoryImpl();
    private OWLAnnotationProperty ap = df.getOWLAnnotationProperty(IRI
            .create("urn:test#ann"));
    private OWLObjectProperty op = df.getOWLObjectProperty(IRI
            .create("urn:test#op"));
    private OWLDataProperty dp = df.getOWLDataProperty(IRI
            .create("urn:test#dp"));
    private OWLLiteral lit = df.getOWLLiteral(false);
    private IRI iri = IRI.create("urn:test#iri");
    private Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>(
            Arrays.asList(df.getOWLAnnotation(ap, df.getOWLLiteral("test"))));
    private OWLClass ce = df.getOWLClass(IRI.create("urn:test#c"));
    private OWLNamedIndividual i = df.getOWLNamedIndividual(IRI
            .create("urn:test#i"));
    private OWLDatatype d = df.getBooleanOWLDatatype();
    private Set<OWLDataProperty> dps = new HashSet<OWLDataProperty>(
            Arrays.asList(df.getOWLDataProperty(iri), dp));
    private Set<OWLObjectProperty> ops = new HashSet<OWLObjectProperty>(
            Arrays.asList(df.getOWLObjectProperty(iri), op));
    private Set<OWLClass> classes = new HashSet<OWLClass>(Arrays.asList(
            df.getOWLClass(iri), ce));
    private Set<OWLIndividual> inds = new HashSet<OWLIndividual>(Arrays.asList(
            i, df.getOWLNamedIndividual(iri)));
    private SWRLDArgument var1 = df.getSWRLVariable(IRI.create("var1"));
    private SWRLIArgument var2 = df.getSWRLVariable(IRI.create("var2"));
    private SWRLAtom v1 = df.getSWRLBuiltInAtom(
            IRI.create("v1"),
            Arrays.asList(
                    (SWRLDArgument) df.getSWRLVariable(IRI.create("var3")),
                    df.getSWRLVariable(IRI.create("var4"))));
    private SWRLAtom v2 = df.getSWRLBuiltInAtom(
            IRI.create("v2"),
            Arrays.asList(
                    (SWRLDArgument) df.getSWRLVariable(IRI.create("var5")),
                    df.getSWRLVariable(IRI.create("var6"))));
    private Set<SWRLAtom> body = new HashSet<SWRLAtom>(Arrays.asList(v1));
    private Set<SWRLAtom> head = new HashSet<SWRLAtom>(Arrays.asList(v2));

    @Test
    public void shouldBuildAnnotation() {
        // given
        OWLAnnotation expected = df.getOWLAnnotation(ap, lit);
        BuilderAnnotation builder = new BuilderAnnotation(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationAssertion() {
        // given
        OWLAnnotationAssertionAxiom expected = df
                .getOWLAnnotationAssertionAxiom(ap, iri, lit, annotations);
        BuilderAnnotationAssertion builder = new BuilderAnnotationAssertion(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationProperty() {
        // given
        OWLAnnotationProperty expected = df.getOWLAnnotationProperty(iri);
        BuilderAnnotationProperty builder = new BuilderAnnotationProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationPropertyDomain() {
        // given
        OWLAnnotationPropertyDomainAxiom expected = df
                .getOWLAnnotationPropertyDomainAxiom(ap, iri, annotations);
        BuilderAnnotationPropertyDomain builder = new BuilderAnnotationPropertyDomain(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationPropertyRange() {
        // given
        OWLAnnotationPropertyRangeAxiom expected = df
                .getOWLAnnotationPropertyRangeAxiom(ap, iri, annotations);
        BuilderAnnotationPropertyRange builder = new BuilderAnnotationPropertyRange(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnonymousIndividual() {
        // given
        OWLAnonymousIndividual expected = df.getOWLAnonymousIndividual("id");
        BuilderAnonymousIndividual builder = new BuilderAnonymousIndividual(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAsymmetricObjectProperty() {
        // given
        OWLAsymmetricObjectPropertyAxiom expected = df
                .getOWLAsymmetricObjectPropertyAxiom(op, annotations);
        BuilderAsymmetricObjectProperty builder = new BuilderAsymmetricObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildClass() {
        // given
        OWLClass expected = df.getOWLClass(iri);
        BuilderClass builder = new BuilderClass(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildClassAssertion() {
        // given
        OWLClassAssertionAxiom expected = df.getOWLClassAssertionAxiom(ce, i,
                annotations);
        BuilderClassAssertion builder = new BuilderClassAssertion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildComplementOf() {
        // given
        OWLObjectComplementOf expected = df.getOWLObjectComplementOf(ce);
        BuilderComplementOf builder = new BuilderComplementOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataAllValuesFrom() {
        // given
        OWLDataAllValuesFrom expected = df.getOWLDataAllValuesFrom(dp, d);
        BuilderDataAllValuesFrom builder = new BuilderDataAllValuesFrom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataComplementOf() {
        // given
        OWLDataComplementOf expected = df.getOWLDataComplementOf(d);
        BuilderDataComplementOf builder = new BuilderDataComplementOf(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataExactCardinality() {
        // given
        OWLDataExactCardinality expected = df.getOWLDataExactCardinality(1, dp,
                d);
        BuilderDataExactCardinality builder = new BuilderDataExactCardinality(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataHasValue() {
        // given
        OWLDataHasValue expected = df.getOWLDataHasValue(dp, lit);
        BuilderDataHasValue builder = new BuilderDataHasValue(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataIntersectionOf() {
        // given
        OWLDataIntersectionOf expected = df.getOWLDataIntersectionOf(d,
                df.getFloatOWLDatatype());
        BuilderDataIntersectionOf builder = new BuilderDataIntersectionOf(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataMaxCardinality() {
        // given
        OWLDataMaxCardinality expected = df.getOWLDataMaxCardinality(1, dp, d);
        BuilderDataMaxCardinality builder = new BuilderDataMaxCardinality(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataMinCardinality() {
        // given
        OWLDataMinCardinality expected = df.getOWLDataMinCardinality(1, dp, d);
        BuilderDataMinCardinality builder = new BuilderDataMinCardinality(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataOneOf() {
        // given
        OWLDataOneOf expected = df.getOWLDataOneOf(lit);
        BuilderDataOneOf builder = new BuilderDataOneOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataProperty() {
        // given
        OWLDataProperty expected = df.getOWLDataProperty(iri);
        BuilderDataProperty builder = new BuilderDataProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataPropertyAssertion() {
        // given
        OWLDataPropertyAssertionAxiom expected = df
                .getOWLDataPropertyAssertionAxiom(dp, i, lit, annotations);
        BuilderDataPropertyAssertion builder = new BuilderDataPropertyAssertion(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataPropertyDomain() {
        // given
        OWLDataPropertyDomainAxiom expected = df.getOWLDataPropertyDomainAxiom(
                dp, ce, annotations);
        BuilderDataPropertyDomain builder = new BuilderDataPropertyDomain(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataPropertyRange() {
        // given
        OWLDataPropertyRangeAxiom expected = df.getOWLDataPropertyRangeAxiom(
                dp, d, annotations);
        BuilderDataPropertyRange builder = new BuilderDataPropertyRange(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataSomeValuesFrom() {
        // given
        OWLDataSomeValuesFrom expected = df.getOWLDataSomeValuesFrom(dp, d);
        BuilderDataSomeValuesFrom builder = new BuilderDataSomeValuesFrom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDatatype() {
        // given
        OWLDatatype expected = df.getOWLDatatype(iri);
        BuilderDatatype builder = new BuilderDatatype(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDatatypeDefinition() {
        // given
        OWLDatatypeDefinitionAxiom expected = df.getOWLDatatypeDefinitionAxiom(
                d, df.getDoubleOWLDatatype(), annotations);
        BuilderDatatypeDefinition builder = new BuilderDatatypeDefinition(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDatatypeRestriction() {
        // given
        OWLFacetRestriction r = df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH,
                lit);
        OWLDatatypeRestriction expected = df.getOWLDatatypeRestriction(d, r);
        BuilderDatatypeRestriction builder = new BuilderDatatypeRestriction(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataUnionOf() {
        // given
        OWLDataUnionOf expected = df.getOWLDataUnionOf(d,
                df.getDoubleOWLDatatype());
        BuilderDataUnionOf builder = new BuilderDataUnionOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDeclaration() {
        // given
        OWLDeclarationAxiom expected = df.getOWLDeclarationAxiom(ce,
                annotations);
        BuilderDeclaration builder = new BuilderDeclaration(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDifferentIndividuals() {
        // given
        OWLDifferentIndividualsAxiom expected = df
                .getOWLDifferentIndividualsAxiom(i,
                        df.getOWLNamedIndividual(iri));
        BuilderDifferentIndividuals builder = new BuilderDifferentIndividuals(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointClasses() {
        // given
        OWLDisjointClassesAxiom expected = df.getOWLDisjointClassesAxiom(ce,
                df.getOWLClass(iri));
        BuilderDisjointClasses builder = new BuilderDisjointClasses(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointDataProperties() {
        // given
        OWLDisjointDataPropertiesAxiom expected = df
                .getOWLDisjointDataPropertiesAxiom(dps, annotations);
        BuilderDisjointDataProperties builder = new BuilderDisjointDataProperties(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointObjectProperties() {
        // given
        OWLDisjointObjectPropertiesAxiom expected = df
                .getOWLDisjointObjectPropertiesAxiom(ops, annotations);
        BuilderDisjointObjectProperties builder = new BuilderDisjointObjectProperties(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointUnion() {
        // given
        OWLDisjointUnionAxiom expected = df.getOWLDisjointUnionAxiom(ce,
                classes, annotations);
        BuilderDisjointUnion builder = new BuilderDisjointUnion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildEntity() {
        // given
        OWLClass expected = df.getOWLClass(iri);
        BuilderEntity builder = new BuilderEntity(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildEquivalentClasses() {
        // given
        OWLEquivalentClassesAxiom expected = df.getOWLEquivalentClassesAxiom(
                classes, annotations);
        BuilderEquivalentClasses builder = new BuilderEquivalentClasses(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildEquivalentDataProperties() {
        // given
        OWLEquivalentDataPropertiesAxiom expected = df
                .getOWLEquivalentDataPropertiesAxiom(dps, annotations);
        BuilderEquivalentDataProperties builder = new BuilderEquivalentDataProperties(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildEquivalentObjectProperties() {
        // given
        OWLEquivalentObjectPropertiesAxiom expected = df
                .getOWLEquivalentObjectPropertiesAxiom(ops, annotations);
        BuilderEquivalentObjectProperties builder = new BuilderEquivalentObjectProperties(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildFacetRestriction() {
        // given
        OWLFacetRestriction expected = df.getOWLFacetRestriction(
                OWLFacet.MAX_EXCLUSIVE, lit);
        BuilderFacetRestriction builder = new BuilderFacetRestriction(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildFunctionalDataProperty() {
        // given
        OWLFunctionalDataPropertyAxiom expected = df
                .getOWLFunctionalDataPropertyAxiom(dp, annotations);
        BuilderFunctionalDataProperty builder = new BuilderFunctionalDataProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildFunctionalObjectProperty() {
        // given
        OWLFunctionalObjectPropertyAxiom expected = df
                .getOWLFunctionalObjectPropertyAxiom(op, annotations);
        BuilderFunctionalObjectProperty builder = new BuilderFunctionalObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildHasKey() {
        // given
        OWLHasKeyAxiom expected = df.getOWLHasKeyAxiom(ce, ops, annotations);
        BuilderHasKey builder = new BuilderHasKey(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildImportsDeclarationProperty() {
        // given
        OWLImportsDeclaration expected = df.getOWLImportsDeclaration(iri);
        BuilderImportsDeclaration builder = new BuilderImportsDeclaration(
                expected, df);
        // when
        OWLImportsDeclaration built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildInverseFunctionalObjectProperty() {
        // given
        OWLInverseFunctionalObjectPropertyAxiom expected = df
                .getOWLInverseFunctionalObjectPropertyAxiom(op, annotations);
        BuilderInverseFunctionalObjectProperty builder = new BuilderInverseFunctionalObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildInverseObjectProperties() {
        // given
        OWLInverseObjectPropertiesAxiom expected = df
                .getOWLInverseObjectPropertiesAxiom(op, op, annotations);
        BuilderInverseObjectProperties builder = new BuilderInverseObjectProperties(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildIrreflexiveObjectProperty() {
        // given
        OWLIrreflexiveObjectPropertyAxiom expected = df
                .getOWLIrreflexiveObjectPropertyAxiom(op, annotations);
        BuilderIrreflexiveObjectProperty builder = new BuilderIrreflexiveObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildLiteral() {
        // given
        OWLLiteral expected = df.getOWLLiteral(true);
        BuilderLiteral builder = new BuilderLiteral(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildNamedIndividual() {
        // given
        OWLNamedIndividual expected = df.getOWLNamedIndividual(iri);
        BuilderNamedIndividual builder = new BuilderNamedIndividual(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildNegativeDataPropertyAssertion() {
        // given
        OWLNegativeDataPropertyAssertionAxiom expected = df
                .getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit,
                        annotations);
        BuilderNegativeDataPropertyAssertion builder = new BuilderNegativeDataPropertyAssertion(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildNegativeObjectPropertyAssertion() {
        // given
        OWLNegativeObjectPropertyAssertionAxiom expected = df
                .getOWLNegativeObjectPropertyAssertionAxiom(op, i, i,
                        annotations);
        BuilderNegativeObjectPropertyAssertion builder = new BuilderNegativeObjectPropertyAssertion(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectAllValuesFrom() {
        // given
        OWLObjectAllValuesFrom expected = df.getOWLObjectAllValuesFrom(op, ce);
        BuilderObjectAllValuesFrom builder = new BuilderObjectAllValuesFrom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectExactCardinality() {
        // given
        OWLObjectExactCardinality expected = df.getOWLObjectExactCardinality(1,
                op, ce);
        BuilderObjectExactCardinality builder = new BuilderObjectExactCardinality(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectHasSelf() {
        // given
        OWLObjectHasSelf expected = df.getOWLObjectHasSelf(op);
        BuilderObjectHasSelf builder = new BuilderObjectHasSelf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectHasValue() {
        // given
        OWLObjectHasValue expected = df.getOWLObjectHasValue(op, i);
        BuilderObjectHasValue builder = new BuilderObjectHasValue(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectIntersectionOf() {
        // given
        OWLObjectIntersectionOf expected = df
                .getOWLObjectIntersectionOf(classes);
        BuilderObjectIntersectionOf builder = new BuilderObjectIntersectionOf(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectInverseOf() {
        // given
        OWLObjectInverseOf expected = df.getOWLObjectInverseOf(op);
        BuilderObjectInverseOf builder = new BuilderObjectInverseOf(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectMaxCardinality() {
        // given
        OWLObjectMaxCardinality expected = df.getOWLObjectMaxCardinality(1, op,
                ce);
        BuilderObjectMaxCardinality builder = new BuilderObjectMaxCardinality(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectMinCardinality() {
        // given
        OWLObjectMinCardinality expected = df.getOWLObjectMinCardinality(1, op,
                ce);
        BuilderObjectMinCardinality builder = new BuilderObjectMinCardinality(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectProperty() {
        // given
        OWLObjectProperty expected = df.getOWLObjectProperty(iri);
        BuilderObjectProperty builder = new BuilderObjectProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectPropertyAssertion() {
        // given
        OWLObjectPropertyAssertionAxiom expected = df
                .getOWLObjectPropertyAssertionAxiom(op, i, i, annotations);
        BuilderObjectPropertyAssertion builder = new BuilderObjectPropertyAssertion(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectPropertyDomain() {
        // given
        OWLObjectPropertyDomainAxiom expected = df
                .getOWLObjectPropertyDomainAxiom(op, ce, annotations);
        BuilderObjectPropertyDomain builder = new BuilderObjectPropertyDomain(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectPropertyRange() {
        // given
        OWLObjectPropertyRangeAxiom expected = df
                .getOWLObjectPropertyRangeAxiom(op, ce, annotations);
        BuilderObjectPropertyRange builder = new BuilderObjectPropertyRange(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectSomeValuesFrom() {
        // given
        OWLObjectSomeValuesFrom expected = df
                .getOWLObjectSomeValuesFrom(op, ce);
        BuilderObjectSomeValuesFrom builder = new BuilderObjectSomeValuesFrom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildOneOf() {
        // given
        OWLObjectOneOf expected = df.getOWLObjectOneOf(i);
        BuilderOneOf builder = new BuilderOneOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildPropertyChain() {
        // given
        ArrayList<OWLObjectPropertyExpression> chain = new ArrayList<OWLObjectPropertyExpression>(
                ops);
        OWLSubPropertyChainOfAxiom expected = df.getOWLSubPropertyChainOfAxiom(
                chain, op, annotations);
        BuilderPropertyChain builder = new BuilderPropertyChain(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildReflexiveObjectProperty() {
        // given
        OWLReflexiveObjectPropertyAxiom expected = df
                .getOWLReflexiveObjectPropertyAxiom(op, annotations);
        BuilderReflexiveObjectProperty builder = new BuilderReflexiveObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSameIndividual() {
        // given
        OWLSameIndividualAxiom expected = df.getOWLSameIndividualAxiom(inds,
                annotations);
        BuilderSameIndividual builder = new BuilderSameIndividual(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubAnnotationPropertyOf() {
        // given
        OWLSubAnnotationPropertyOfAxiom expected = df
                .getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(),
                        annotations);
        BuilderSubAnnotationPropertyOf builder = new BuilderSubAnnotationPropertyOf(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubClass() {
        // given
        OWLSubClassOfAxiom expected = df.getOWLSubClassOfAxiom(ce,
                df.getOWLThing(), annotations);
        BuilderSubClass builder = new BuilderSubClass(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubDataProperty() {
        // given
        OWLSubDataPropertyOfAxiom expected = df.getOWLSubDataPropertyOfAxiom(
                dp, df.getOWLTopDataProperty());
        BuilderSubDataProperty builder = new BuilderSubDataProperty(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubObjectProperty() {
        // given
        OWLSubObjectPropertyOfAxiom expected = df
                .getOWLSubObjectPropertyOfAxiom(op,
                        df.getOWLTopObjectProperty(), annotations);
        BuilderSubObjectProperty builder = new BuilderSubObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLBuiltInAtom() {
        // given
        SWRLBuiltInAtom expected = df.getSWRLBuiltInAtom(iri,
                Arrays.asList(var1));
        BuilderSWRLBuiltInAtom builder = new BuilderSWRLBuiltInAtom(expected,
                df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLClassAtom() {
        // given
        SWRLClassAtom expected = df.getSWRLClassAtom(ce, var2);
        BuilderSWRLClassAtom builder = new BuilderSWRLClassAtom(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLDataPropertyAtom() {
        // given
        SWRLDataPropertyAtom expected = df.getSWRLDataPropertyAtom(dp, var2,
                var1);
        BuilderSWRLDataPropertyAtom builder = new BuilderSWRLDataPropertyAtom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLDataRangeAtom() {
        // given
        SWRLDataRangeAtom expected = df.getSWRLDataRangeAtom(d, var1);
        BuilderSWRLDataRangeAtom builder = new BuilderSWRLDataRangeAtom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLDifferentIndividualsAtom() {
        // given
        SWRLDifferentIndividualsAtom expected = df
                .getSWRLDifferentIndividualsAtom(var2, var2);
        BuilderSWRLDifferentIndividualsAtom builder = new BuilderSWRLDifferentIndividualsAtom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLIndividualArgument() {
        // given
        SWRLIndividualArgument expected = df.getSWRLIndividualArgument(i);
        BuilderSWRLIndividualArgument builder = new BuilderSWRLIndividualArgument(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLLiteralArgument() {
        // given
        SWRLLiteralArgument expected = df.getSWRLLiteralArgument(lit);
        BuilderSWRLLiteralArgument builder = new BuilderSWRLLiteralArgument(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLObjectPropertyAtom() {
        // given
        SWRLObjectPropertyAtom expected = df.getSWRLObjectPropertyAtom(op,
                var2, var2);
        BuilderSWRLObjectPropertyAtom builder = new BuilderSWRLObjectPropertyAtom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLRule() {
        // given
        SWRLRule expected = df.getSWRLRule(body, head);
        BuilderSWRLRule builder = new BuilderSWRLRule(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLSameIndividualAtom() {
        // given
        SWRLSameIndividualAtom expected = df.getSWRLSameIndividualAtom(
                df.getSWRLIndividualArgument(i),
                df.getSWRLIndividualArgument(i));
        BuilderSWRLSameIndividualAtom builder = new BuilderSWRLSameIndividualAtom(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLVariable() {
        // given
        SWRLVariable expected = df.getSWRLVariable(iri);
        BuilderSWRLVariable builder = new BuilderSWRLVariable(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSymmetricObjectProperty() {
        // given
        OWLSymmetricObjectPropertyAxiom expected = df
                .getOWLSymmetricObjectPropertyAxiom(op, annotations);
        BuilderSymmetricObjectProperty builder = new BuilderSymmetricObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildTransitiveObjectProperty() {
        // given
        OWLTransitiveObjectPropertyAxiom expected = df
                .getOWLTransitiveObjectPropertyAxiom(op, annotations);
        BuilderTransitiveObjectProperty builder = new BuilderTransitiveObjectProperty(
                expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildUnionOf() {
        // given
        OWLObjectUnionOf expected = df.getOWLObjectUnionOf(classes);
        BuilderUnionOf builder = new BuilderUnionOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }
}
