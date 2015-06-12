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
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapitools.builders.*;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

@SuppressWarnings({ "javadoc", })
public class BuildersEqualTestCase {

    @Nonnull
    private final OWLDataFactory df = new OWLDataFactoryImpl();
    @Nonnull
    private final OWLAnnotationProperty ap = df.getOWLAnnotationProperty(IRI.create("urn:test#ann"));
    @Nonnull
    private final OWLObjectProperty op = df.getOWLObjectProperty(IRI.create("urn:test#op"));
    @Nonnull
    private final OWLDataProperty dp = df.getOWLDataProperty(IRI.create("urn:test#dp"));
    @Nonnull
    private final OWLLiteral lit = df.getOWLLiteral(false);
    @Nonnull
    private final IRI iri = IRI.create("urn:test#iri");
    @Nonnull
    private final Set<OWLAnnotation> annotations = new HashSet<>(Arrays.asList(df.getOWLAnnotation(ap, df.getOWLLiteral(
        "test"))));
    @Nonnull
    private final OWLClass ce = df.getOWLClass(IRI.create("urn:test#c"));
    @Nonnull
    private final OWLNamedIndividual i = df.getOWLNamedIndividual(IRI.create("urn:test#i"));
    @Nonnull
    private final OWLDatatype d = df.getBooleanOWLDatatype();
    @Nonnull
    private final Set<OWLDataProperty> dps = new HashSet<>(Arrays.asList(df.getOWLDataProperty(iri), dp));
    @Nonnull
    private final Set<OWLObjectProperty> ops = new HashSet<>(Arrays.asList(df.getOWLObjectProperty(iri), op));
    @Nonnull
    private final Set<OWLClass> classes = new HashSet<>(Arrays.asList(df.getOWLClass(iri), ce));
    @Nonnull
    private final Set<OWLNamedIndividual> inds = new HashSet<>(Arrays.asList(i, df.getOWLNamedIndividual(iri)));
    @Nonnull
    private final SWRLDArgument var1 = df.getSWRLVariable(IRI.create("var1"));
    @Nonnull
    private final SWRLIArgument var2 = df.getSWRLVariable(IRI.create("var2"));
    @Nonnull
    private final SWRLAtom v1 = df.getSWRLBuiltInAtom(IRI.create("v1"), Arrays.asList((SWRLDArgument) df
        .getSWRLVariable(IRI.create("var3")), df.getSWRLVariable(IRI.create("var4"))));
    @Nonnull
    private final SWRLAtom v2 = df.getSWRLBuiltInAtom(IRI.create("v2"), Arrays.asList((SWRLDArgument) df
        .getSWRLVariable(IRI.create("var5")), df.getSWRLVariable(IRI.create("var6"))));
    @Nonnull
    private final Set<SWRLAtom> body = new HashSet<>(Arrays.asList(v1));
    @Nonnull
    private final Set<SWRLAtom> head = new HashSet<>(Arrays.asList(v2));

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
        OWLAnnotationAssertionAxiom expected = df.getOWLAnnotationAssertionAxiom(ap, iri, lit, annotations);
        BuilderAnnotationAssertion builder = new BuilderAnnotationAssertion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationProperty() {
        // given
        OWLAnnotationProperty expected = df.getOWLAnnotationProperty(iri);
        BuilderAnnotationProperty builder = new BuilderAnnotationProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationPropertyDomain() {
        // given
        OWLAnnotationPropertyDomainAxiom expected = df.getOWLAnnotationPropertyDomainAxiom(ap, iri, annotations);
        BuilderAnnotationPropertyDomain builder = new BuilderAnnotationPropertyDomain(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnnotationPropertyRange() {
        // given
        OWLAnnotationPropertyRangeAxiom expected = df.getOWLAnnotationPropertyRangeAxiom(ap, iri, annotations);
        BuilderAnnotationPropertyRange builder = new BuilderAnnotationPropertyRange(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAnonymousIndividual() {
        // given
        OWLAnonymousIndividual expected = df.getOWLAnonymousIndividual("id");
        BuilderAnonymousIndividual builder = new BuilderAnonymousIndividual(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildAsymmetricObjectProperty() {
        // given
        OWLAsymmetricObjectPropertyAxiom expected = df.getOWLAsymmetricObjectPropertyAxiom(op, annotations);
        BuilderAsymmetricObjectProperty builder = new BuilderAsymmetricObjectProperty(expected, df);
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
        OWLClassAssertionAxiom expected = df.getOWLClassAssertionAxiom(ce, i, annotations);
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
        BuilderDataAllValuesFrom builder = new BuilderDataAllValuesFrom(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataComplementOf() {
        // given
        OWLDataComplementOf expected = df.getOWLDataComplementOf(d);
        BuilderDataComplementOf builder = new BuilderDataComplementOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataExactCardinality() {
        // given
        OWLDataExactCardinality expected = df.getOWLDataExactCardinality(1, dp, d);
        BuilderDataExactCardinality builder = new BuilderDataExactCardinality(expected, df);
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
        OWLDataIntersectionOf expected = df.getOWLDataIntersectionOf(d, df.getFloatOWLDatatype());
        BuilderDataIntersectionOf builder = new BuilderDataIntersectionOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataMaxCardinality() {
        // given
        OWLDataMaxCardinality expected = df.getOWLDataMaxCardinality(1, dp, d);
        BuilderDataMaxCardinality builder = new BuilderDataMaxCardinality(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataMinCardinality() {
        // given
        OWLDataMinCardinality expected = df.getOWLDataMinCardinality(1, dp, d);
        BuilderDataMinCardinality builder = new BuilderDataMinCardinality(expected, df);
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
        OWLDataPropertyAssertionAxiom expected = df.getOWLDataPropertyAssertionAxiom(dp, i, lit, annotations);
        BuilderDataPropertyAssertion builder = new BuilderDataPropertyAssertion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataPropertyDomain() {
        // given
        OWLDataPropertyDomainAxiom expected = df.getOWLDataPropertyDomainAxiom(dp, ce, annotations);
        BuilderDataPropertyDomain builder = new BuilderDataPropertyDomain(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataPropertyRange() {
        // given
        OWLDataPropertyRangeAxiom expected = df.getOWLDataPropertyRangeAxiom(dp, d, annotations);
        BuilderDataPropertyRange builder = new BuilderDataPropertyRange(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataSomeValuesFrom() {
        // given
        OWLDataSomeValuesFrom expected = df.getOWLDataSomeValuesFrom(dp, d);
        BuilderDataSomeValuesFrom builder = new BuilderDataSomeValuesFrom(expected, df);
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
        OWLDatatypeDefinitionAxiom expected = df.getOWLDatatypeDefinitionAxiom(d, df.getDoubleOWLDatatype(),
            annotations);
        BuilderDatatypeDefinition builder = new BuilderDatatypeDefinition(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDatatypeRestriction() {
        // given
        OWLFacetRestriction r = df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH, lit);
        OWLDatatypeRestriction expected = df.getOWLDatatypeRestriction(d, r);
        BuilderDatatypeRestriction builder = new BuilderDatatypeRestriction(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDataUnionOf() {
        // given
        OWLDataUnionOf expected = df.getOWLDataUnionOf(d, df.getDoubleOWLDatatype());
        BuilderDataUnionOf builder = new BuilderDataUnionOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDeclaration() {
        // given
        OWLDeclarationAxiom expected = df.getOWLDeclarationAxiom(ce, annotations);
        BuilderDeclaration builder = new BuilderDeclaration(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDifferentIndividuals() {
        // given
        OWLDifferentIndividualsAxiom expected = df.getOWLDifferentIndividualsAxiom(i, df.getOWLNamedIndividual(iri));
        BuilderDifferentIndividuals builder = new BuilderDifferentIndividuals(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointClasses() {
        // given
        OWLDisjointClassesAxiom expected = df.getOWLDisjointClassesAxiom(ce, df.getOWLClass(iri));
        BuilderDisjointClasses builder = new BuilderDisjointClasses(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointDataProperties() {
        // given
        OWLDisjointDataPropertiesAxiom expected = df.getOWLDisjointDataPropertiesAxiom(dps, annotations);
        BuilderDisjointDataProperties builder = new BuilderDisjointDataProperties(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointObjectProperties() {
        // given
        OWLDisjointObjectPropertiesAxiom expected = df.getOWLDisjointObjectPropertiesAxiom(ops, annotations);
        BuilderDisjointObjectProperties builder = new BuilderDisjointObjectProperties(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildDisjointUnion() {
        // given
        OWLDisjointUnionAxiom expected = df.getOWLDisjointUnionAxiom(ce, classes, annotations);
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
        OWLEquivalentClassesAxiom expected = df.getOWLEquivalentClassesAxiom(classes, annotations);
        BuilderEquivalentClasses builder = new BuilderEquivalentClasses(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildEquivalentDataProperties() {
        // given
        OWLEquivalentDataPropertiesAxiom expected = df.getOWLEquivalentDataPropertiesAxiom(dps, annotations);
        BuilderEquivalentDataProperties builder = new BuilderEquivalentDataProperties(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildEquivalentObjectProperties() {
        // given
        OWLEquivalentObjectPropertiesAxiom expected = df.getOWLEquivalentObjectPropertiesAxiom(ops, annotations);
        BuilderEquivalentObjectProperties builder = new BuilderEquivalentObjectProperties(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildFacetRestriction() {
        // given
        OWLFacetRestriction expected = df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, lit);
        BuilderFacetRestriction builder = new BuilderFacetRestriction(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildFunctionalDataProperty() {
        // given
        OWLFunctionalDataPropertyAxiom expected = df.getOWLFunctionalDataPropertyAxiom(dp, annotations);
        BuilderFunctionalDataProperty builder = new BuilderFunctionalDataProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildFunctionalObjectProperty() {
        // given
        OWLFunctionalObjectPropertyAxiom expected = df.getOWLFunctionalObjectPropertyAxiom(op, annotations);
        BuilderFunctionalObjectProperty builder = new BuilderFunctionalObjectProperty(expected, df);
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
        BuilderImportsDeclaration builder = new BuilderImportsDeclaration(expected, df);
        // when
        OWLImportsDeclaration built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildInverseFunctionalObjectProperty() {
        // given
        OWLInverseFunctionalObjectPropertyAxiom expected = df.getOWLInverseFunctionalObjectPropertyAxiom(op,
            annotations);
        BuilderInverseFunctionalObjectProperty builder = new BuilderInverseFunctionalObjectProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildInverseObjectProperties() {
        // given
        OWLInverseObjectPropertiesAxiom expected = df.getOWLInverseObjectPropertiesAxiom(op, op, annotations);
        BuilderInverseObjectProperties builder = new BuilderInverseObjectProperties(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildIrreflexiveObjectProperty() {
        // given
        OWLIrreflexiveObjectPropertyAxiom expected = df.getOWLIrreflexiveObjectPropertyAxiom(op, annotations);
        BuilderIrreflexiveObjectProperty builder = new BuilderIrreflexiveObjectProperty(expected, df);
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
        BuilderNamedIndividual builder = new BuilderNamedIndividual(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildNegativeDataPropertyAssertion() {
        // given
        OWLNegativeDataPropertyAssertionAxiom expected = df.getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit,
            annotations);
        BuilderNegativeDataPropertyAssertion builder = new BuilderNegativeDataPropertyAssertion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildNegativeObjectPropertyAssertion() {
        // given
        OWLNegativeObjectPropertyAssertionAxiom expected = df.getOWLNegativeObjectPropertyAssertionAxiom(op, i, i,
            annotations);
        BuilderNegativeObjectPropertyAssertion builder = new BuilderNegativeObjectPropertyAssertion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectAllValuesFrom() {
        // given
        OWLObjectAllValuesFrom expected = df.getOWLObjectAllValuesFrom(op, ce);
        BuilderObjectAllValuesFrom builder = new BuilderObjectAllValuesFrom(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectExactCardinality() {
        // given
        OWLObjectExactCardinality expected = df.getOWLObjectExactCardinality(1, op, ce);
        BuilderObjectExactCardinality builder = new BuilderObjectExactCardinality(expected, df);
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
        OWLObjectIntersectionOf expected = df.getOWLObjectIntersectionOf(classes);
        BuilderObjectIntersectionOf builder = new BuilderObjectIntersectionOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectInverseOf() {
        // given
        OWLObjectInverseOf expected = df.getOWLObjectInverseOf(op);
        BuilderObjectInverseOf builder = new BuilderObjectInverseOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectMaxCardinality() {
        // given
        OWLObjectMaxCardinality expected = df.getOWLObjectMaxCardinality(1, op, ce);
        BuilderObjectMaxCardinality builder = new BuilderObjectMaxCardinality(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectMinCardinality() {
        // given
        OWLObjectMinCardinality expected = df.getOWLObjectMinCardinality(1, op, ce);
        BuilderObjectMinCardinality builder = new BuilderObjectMinCardinality(expected, df);
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
        OWLObjectPropertyAssertionAxiom expected = df.getOWLObjectPropertyAssertionAxiom(op, i, i, annotations);
        BuilderObjectPropertyAssertion builder = new BuilderObjectPropertyAssertion(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectPropertyDomain() {
        // given
        OWLObjectPropertyDomainAxiom expected = df.getOWLObjectPropertyDomainAxiom(op, ce, annotations);
        BuilderObjectPropertyDomain builder = new BuilderObjectPropertyDomain(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectPropertyRange() {
        // given
        OWLObjectPropertyRangeAxiom expected = df.getOWLObjectPropertyRangeAxiom(op, ce, annotations);
        BuilderObjectPropertyRange builder = new BuilderObjectPropertyRange(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildObjectSomeValuesFrom() {
        // given
        OWLObjectSomeValuesFrom expected = df.getOWLObjectSomeValuesFrom(op, ce);
        BuilderObjectSomeValuesFrom builder = new BuilderObjectSomeValuesFrom(expected, df);
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
        List<OWLObjectProperty> chain = new ArrayList<>(ops);
        OWLSubPropertyChainOfAxiom expected = df.getOWLSubPropertyChainOfAxiom(chain, op, annotations);
        BuilderPropertyChain builder = new BuilderPropertyChain(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildReflexiveObjectProperty() {
        // given
        OWLReflexiveObjectPropertyAxiom expected = df.getOWLReflexiveObjectPropertyAxiom(op, annotations);
        BuilderReflexiveObjectProperty builder = new BuilderReflexiveObjectProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSameIndividual() {
        // given
        OWLSameIndividualAxiom expected = df.getOWLSameIndividualAxiom(inds, annotations);
        BuilderSameIndividual builder = new BuilderSameIndividual(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubAnnotationPropertyOf() {
        // given
        OWLSubAnnotationPropertyOfAxiom expected = df.getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(),
            annotations);
        BuilderSubAnnotationPropertyOf builder = new BuilderSubAnnotationPropertyOf(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubClass() {
        // given
        OWLSubClassOfAxiom expected = df.getOWLSubClassOfAxiom(ce, df.getOWLThing(), annotations);
        BuilderSubClass builder = new BuilderSubClass(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubDataProperty() {
        // given
        OWLSubDataPropertyOfAxiom expected = df.getOWLSubDataPropertyOfAxiom(dp, df.getOWLTopDataProperty());
        BuilderSubDataProperty builder = new BuilderSubDataProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSubObjectProperty() {
        // given
        OWLSubObjectPropertyOfAxiom expected = df.getOWLSubObjectPropertyOfAxiom(op, df.getOWLTopObjectProperty(),
            annotations);
        BuilderSubObjectProperty builder = new BuilderSubObjectProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLBuiltInAtom() {
        // given
        SWRLBuiltInAtom expected = df.getSWRLBuiltInAtom(iri, Arrays.asList(var1));
        BuilderSWRLBuiltInAtom builder = new BuilderSWRLBuiltInAtom(expected, df);
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
        SWRLDataPropertyAtom expected = df.getSWRLDataPropertyAtom(dp, var2, var1);
        BuilderSWRLDataPropertyAtom builder = new BuilderSWRLDataPropertyAtom(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLDataRangeAtom() {
        // given
        SWRLDataRangeAtom expected = df.getSWRLDataRangeAtom(d, var1);
        BuilderSWRLDataRangeAtom builder = new BuilderSWRLDataRangeAtom(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLDifferentIndividualsAtom() {
        // given
        SWRLDifferentIndividualsAtom expected = df.getSWRLDifferentIndividualsAtom(var2, var2);
        BuilderSWRLDifferentIndividualsAtom builder = new BuilderSWRLDifferentIndividualsAtom(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLIndividualArgument() {
        // given
        SWRLIndividualArgument expected = df.getSWRLIndividualArgument(i);
        BuilderSWRLIndividualArgument builder = new BuilderSWRLIndividualArgument(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLLiteralArgument() {
        // given
        SWRLLiteralArgument expected = df.getSWRLLiteralArgument(lit);
        BuilderSWRLLiteralArgument builder = new BuilderSWRLLiteralArgument(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildSWRLObjectPropertyAtom() {
        // given
        SWRLObjectPropertyAtom expected = df.getSWRLObjectPropertyAtom(op, var2, var2);
        BuilderSWRLObjectPropertyAtom builder = new BuilderSWRLObjectPropertyAtom(expected, df);
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
        SWRLSameIndividualAtom expected = df.getSWRLSameIndividualAtom(df.getSWRLIndividualArgument(i), df
            .getSWRLIndividualArgument(i));
        BuilderSWRLSameIndividualAtom builder = new BuilderSWRLSameIndividualAtom(expected, df);
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
        OWLSymmetricObjectPropertyAxiom expected = df.getOWLSymmetricObjectPropertyAxiom(op, annotations);
        BuilderSymmetricObjectProperty builder = new BuilderSymmetricObjectProperty(expected, df);
        // when
        OWLObject built = builder.buildObject();
        // then
        assertEquals(expected, built);
    }

    @Test
    public void shouldBuildTransitiveObjectProperty() {
        // given
        OWLTransitiveObjectPropertyAxiom expected = df.getOWLTransitiveObjectPropertyAxiom(op, annotations);
        BuilderTransitiveObjectProperty builder = new BuilderTransitiveObjectProperty(expected, df);
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
