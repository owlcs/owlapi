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
package org.semanticweb.owlapi6.builders.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.builders.Builder;
import org.semanticweb.owlapi6.builders.BuilderAnnotation;
import org.semanticweb.owlapi6.builders.BuilderAnnotationAssertion;
import org.semanticweb.owlapi6.builders.BuilderAnnotationProperty;
import org.semanticweb.owlapi6.builders.BuilderAnnotationPropertyDomain;
import org.semanticweb.owlapi6.builders.BuilderAnnotationPropertyRange;
import org.semanticweb.owlapi6.builders.BuilderAnonymousIndividual;
import org.semanticweb.owlapi6.builders.BuilderAsymmetricObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderClass;
import org.semanticweb.owlapi6.builders.BuilderClassAssertion;
import org.semanticweb.owlapi6.builders.BuilderComplementOf;
import org.semanticweb.owlapi6.builders.BuilderDataAllValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderDataComplementOf;
import org.semanticweb.owlapi6.builders.BuilderDataExactCardinality;
import org.semanticweb.owlapi6.builders.BuilderDataHasValue;
import org.semanticweb.owlapi6.builders.BuilderDataIntersectionOf;
import org.semanticweb.owlapi6.builders.BuilderDataMaxCardinality;
import org.semanticweb.owlapi6.builders.BuilderDataMinCardinality;
import org.semanticweb.owlapi6.builders.BuilderDataOneOf;
import org.semanticweb.owlapi6.builders.BuilderDataProperty;
import org.semanticweb.owlapi6.builders.BuilderDataPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderDataPropertyDomain;
import org.semanticweb.owlapi6.builders.BuilderDataPropertyRange;
import org.semanticweb.owlapi6.builders.BuilderDataSomeValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderDataUnionOf;
import org.semanticweb.owlapi6.builders.BuilderDatatype;
import org.semanticweb.owlapi6.builders.BuilderDatatypeDefinition;
import org.semanticweb.owlapi6.builders.BuilderDatatypeRestriction;
import org.semanticweb.owlapi6.builders.BuilderDeclaration;
import org.semanticweb.owlapi6.builders.BuilderDifferentIndividuals;
import org.semanticweb.owlapi6.builders.BuilderDisjointClasses;
import org.semanticweb.owlapi6.builders.BuilderDisjointDataProperties;
import org.semanticweb.owlapi6.builders.BuilderDisjointObjectProperties;
import org.semanticweb.owlapi6.builders.BuilderDisjointUnion;
import org.semanticweb.owlapi6.builders.BuilderEntity;
import org.semanticweb.owlapi6.builders.BuilderEquivalentClasses;
import org.semanticweb.owlapi6.builders.BuilderEquivalentDataProperties;
import org.semanticweb.owlapi6.builders.BuilderEquivalentObjectProperties;
import org.semanticweb.owlapi6.builders.BuilderFacetRestriction;
import org.semanticweb.owlapi6.builders.BuilderFunctionalDataProperty;
import org.semanticweb.owlapi6.builders.BuilderFunctionalObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderHasKey;
import org.semanticweb.owlapi6.builders.BuilderImportsDeclaration;
import org.semanticweb.owlapi6.builders.BuilderInverseFunctionalObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderInverseObjectProperties;
import org.semanticweb.owlapi6.builders.BuilderIrreflexiveObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderLiteral;
import org.semanticweb.owlapi6.builders.BuilderNamedIndividual;
import org.semanticweb.owlapi6.builders.BuilderNegativeDataPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderNegativeObjectPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderObjectAllValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderObjectExactCardinality;
import org.semanticweb.owlapi6.builders.BuilderObjectHasSelf;
import org.semanticweb.owlapi6.builders.BuilderObjectHasValue;
import org.semanticweb.owlapi6.builders.BuilderObjectIntersectionOf;
import org.semanticweb.owlapi6.builders.BuilderObjectInverseOf;
import org.semanticweb.owlapi6.builders.BuilderObjectMaxCardinality;
import org.semanticweb.owlapi6.builders.BuilderObjectMinCardinality;
import org.semanticweb.owlapi6.builders.BuilderObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderObjectPropertyAssertion;
import org.semanticweb.owlapi6.builders.BuilderObjectPropertyDomain;
import org.semanticweb.owlapi6.builders.BuilderObjectPropertyRange;
import org.semanticweb.owlapi6.builders.BuilderObjectSomeValuesFrom;
import org.semanticweb.owlapi6.builders.BuilderOneOf;
import org.semanticweb.owlapi6.builders.BuilderPropertyChain;
import org.semanticweb.owlapi6.builders.BuilderReflexiveObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderSWRLBuiltInAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLClassAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLDataPropertyAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLDataRangeAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLIndividualArgument;
import org.semanticweb.owlapi6.builders.BuilderSWRLLiteralArgument;
import org.semanticweb.owlapi6.builders.BuilderSWRLObjectPropertyAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLRule;
import org.semanticweb.owlapi6.builders.BuilderSWRLSameIndividualAtom;
import org.semanticweb.owlapi6.builders.BuilderSWRLVariable;
import org.semanticweb.owlapi6.builders.BuilderSameIndividual;
import org.semanticweb.owlapi6.builders.BuilderSubAnnotationPropertyOf;
import org.semanticweb.owlapi6.builders.BuilderSubClass;
import org.semanticweb.owlapi6.builders.BuilderSubDataProperty;
import org.semanticweb.owlapi6.builders.BuilderSubObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderSymmetricObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderTransitiveObjectProperty;
import org.semanticweb.owlapi6.builders.BuilderUnionOf;
import org.semanticweb.owlapi6.model.EntityType;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.SWRLAtom;
import org.semanticweb.owlapi6.model.SWRLDArgument;
import org.semanticweb.owlapi6.model.SWRLIArgument;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLFacet;

class BuildersTestCase extends TestBase {

    static final SWRLAtom v1 = SWRLBuiltInAtom(iri("urn:test#", "v1"), SWRL.var3, SWRL.var4);
    static final SWRLAtom v2 = SWRLBuiltInAtom(iri("urn:test#", "v2"), SWRL.var5, SWRL.var6);
    static final Set<SWRLAtom> body = set(v1);
    static final Set<SWRLAtom> head = set(v2);
    static final String TEST = "test";
    static final Set<OWLAnnotation> anns = set(Annotation(ANNPROPS.ap, Literal(TEST)));

    @BeforeEach
    void setup() {
        m = setupConcurrentManager();
    }

    static Stream<Arguments> getData() {
        return Stream.of(
        //@formatter:off
            of(value->new BuilderAnnotation(value, df),                      new BuilderAnnotation(df).withProperty(ANNPROPS.ap).withValue(LITERALS.lit),                                                               Annotation(ANNPROPS.ap, LITERALS.lit)),
            of(value->new BuilderAnnotationAssertion(value, df),             new BuilderAnnotationAssertion(df).withAnnotations(anns).withProperty(ANNPROPS.ap).withSubject(IRIS.iri).withValue(LITERALS.lit),          AnnotationAssertion(anns, ANNPROPS.ap, IRIS.iri, LITERALS.lit)),
            of(value->new BuilderAnnotationProperty(value, df),              new BuilderAnnotationProperty(df).withIRI(IRIS.iri),                                                                                       ANNPROPS.IRI_AP),
            of(value->new BuilderAnnotationPropertyDomain(value, df),        new BuilderAnnotationPropertyDomain(df).withProperty(ANNPROPS.ap).withDomain(IRIS.iri).withAnnotations(anns),                              AnnotationPropertyDomain(anns, ANNPROPS.ap, IRIS.iri)),
            of(value->new BuilderAnnotationPropertyRange(value, df),         new BuilderAnnotationPropertyRange(df).withProperty(ANNPROPS.ap).withRange(IRIS.iri).withAnnotations(anns),                                AnnotationPropertyRange(anns, ANNPROPS.ap, IRIS.iri)),
            of(value->new BuilderAnonymousIndividual(value, df),             new BuilderAnonymousIndividual(df).withId("id"),                                                                                           AnonymousIndividual("id")),
            of(value->new BuilderAsymmetricObjectProperty(value, df),        new BuilderAsymmetricObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                                   AsymmetricObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderClass(value, df),                           new BuilderClass(df).withIRI(IRIS.iri),                                                                                                    CLASSES.IRI_CLASS),
            of(value->new BuilderClassAssertion(value, df),                  new BuilderClassAssertion(df).withClass(CLASSES.ce).withIndividual(INDIVIDUALS.i).withAnnotations(anns),                                   ClassAssertion(anns, CLASSES.ce, INDIVIDUALS.i)),
            of(value->new BuilderComplementOf(value, df),                    new BuilderComplementOf(df).withClass(CLASSES.ce),                                                                                         ObjectComplementOf(CLASSES.ce)),
            of(value->new BuilderDataAllValuesFrom(value, df),               new BuilderDataAllValuesFrom(df).withProperty(DATAPROPS.dp).withRange(Boolean()),                                                          DataAllValuesFrom(DATAPROPS.dp, Boolean())),
            of(value->new BuilderDataComplementOf(value, df),                new BuilderDataComplementOf(df).withRange(Boolean()),                                                                                      DataComplementOf(Boolean())),
            of(value->new BuilderDataExactCardinality(value, df),            new BuilderDataExactCardinality(df).withCardinality(1).withProperty(DATAPROPS.dp).withRange(Boolean()),                                    DataExactCardinality(1, DATAPROPS.dp, Boolean())),
            of(value->new BuilderDataHasValue(value, df),                    new BuilderDataHasValue(df).withProperty(DATAPROPS.dp).withLiteral(LITERALS.lit),                                                          DataHasValue(DATAPROPS.dp, LITERALS.lit)),
            of(value->new BuilderDataIntersectionOf(value, df),              new BuilderDataIntersectionOf(df).withItem(Boolean()).withItem(Float()),                                                                   DataIntersectionOf(Boolean(), Float())),
            of(value->new BuilderDataMaxCardinality(value, df),              new BuilderDataMaxCardinality(df).withCardinality(1).withProperty(DATAPROPS.dp).withRange(Boolean()),                                      DataMaxCardinality(1, DATAPROPS.dp, Boolean())),
            of(value->new BuilderDataMinCardinality(value, df),              new BuilderDataMinCardinality(df).withCardinality(1).withProperty(DATAPROPS.dp).withRange(Boolean()),                                      DataMinCardinality(1, DATAPROPS.dp, Boolean())),
            of(value->new BuilderDataOneOf(value, df),                       new BuilderDataOneOf(df).withItem(LITERALS.lit),                                                                                           DataOneOf(LITERALS.lit)),
            of(value->new BuilderDataProperty(value, df),                    new BuilderDataProperty(df).withIRI(IRIS.iri),                                                                                             DATAPROPS.IRI_DP),
            of(value->new BuilderDataPropertyAssertion(value, df),           new BuilderDataPropertyAssertion(df).withProperty(DATAPROPS.dp).withSubject(INDIVIDUALS.i).withValue(LITERALS.lit).withAnnotations(anns),  DataPropertyAssertion(anns, DATAPROPS.dp, INDIVIDUALS.i, LITERALS.lit)),
            of(value->new BuilderDataPropertyDomain(value, df),              new BuilderDataPropertyDomain(df).withProperty(DATAPROPS.dp).withDomain(CLASSES.ce).withAnnotations(anns),                                 DataPropertyDomain(anns, DATAPROPS.dp, CLASSES.ce)),
            of(value->new BuilderDataPropertyRange(value, df),               new BuilderDataPropertyRange(df).withProperty(DATAPROPS.dp).withRange(Boolean()).withAnnotations(anns),                                    DataPropertyRange(anns, DATAPROPS.dp, Boolean())),
            of(value->new BuilderDataSomeValuesFrom(value, df),              new BuilderDataSomeValuesFrom(df).withProperty(DATAPROPS.dp).withRange(Boolean()),                                                         DataSomeValuesFrom(DATAPROPS.dp, Boolean())),
            of(value->new BuilderDatatype(value, df),                        new BuilderDatatype(df).withIRI(IRIS.iri).withAnnotations(anns),                                                                           DATATYPES.IRI_DT),
            of(value->new BuilderDatatypeDefinition(value, df),              new BuilderDatatypeDefinition(df).with(DATATYPES.DT1).withType(DATATYPES.DT2).withAnnotations(anns),                                       DatatypeDefinition(anns, DATATYPES.DT1, DATATYPES.DT2)),
            of(value->new BuilderDatatypeRestriction(value, df),             new BuilderDatatypeRestriction(df).withItem(FacetRestriction(OWLFacet.MAX_LENGTH, LITERALS.lit)).withDatatype(Boolean()),                  DatatypeRestriction(Boolean(), FacetRestriction(OWLFacet.MAX_LENGTH, LITERALS.lit))),
            of(value->new BuilderDataUnionOf(value, df),                     new BuilderDataUnionOf(df).withItem(Boolean()).withItem(Double()),                                                                         DataUnionOf(Boolean(), Double())),
            of(value->new BuilderDeclaration(value, df),                     new BuilderDeclaration(df).withEntity(CLASSES.ce).withAnnotations(anns),                                                                   Declaration(anns, CLASSES.ce)),
            of(value->new BuilderDifferentIndividuals(value, df),            new BuilderDifferentIndividuals(df).withItem(INDIVIDUALS.i).withItem(INDIVIDUALS.IRI_IND),                                                 DifferentIndividuals(INDIVIDUALS.i, INDIVIDUALS.IRI_IND)),
            of(value->new BuilderDisjointClasses(value, df),                 new BuilderDisjointClasses(df).withItem(CLASSES.ce).withItem(CLASSES.IRI_CLASS),                                                           DisjointClasses(CLASSES.ce, CLASSES.IRI_CLASS)),
            of(value->new BuilderDisjointDataProperties(value, df),          new BuilderDisjointDataProperties(df).withItems(DATAPROPS.IRI_DP, DATAPROPS.dp).withAnnotations(anns),                                     DisjointDataProperties(anns, DATAPROPS.IRI_DP, DATAPROPS.dp)),
            of(value->new BuilderDisjointObjectProperties(value, df),        new BuilderDisjointObjectProperties(df).withItems(OBJPROPS.P, OBJPROPS.op).withAnnotations(anns),                                          DisjointObjectProperties(anns, OBJPROPS.P, OBJPROPS.op)),
            of(value->new BuilderDisjointUnion(value, df),                   new BuilderDisjointUnion(df).withClass(CLASSES.ce).withItems(CLASSES.IRI_CLASS, CLASSES.ce).withAnnotations(anns),                         DisjointUnion(anns, CLASSES.ce, CLASSES.IRI_CLASS, CLASSES.ce)),
            of(value->new BuilderEntity(value, df),                          new BuilderEntity(df).withIRI(IRIS.iri).withType(EntityType.CLASS),                                                                        CLASSES.IRI_CLASS),
            of(value->new BuilderEquivalentClasses(value, df),               new BuilderEquivalentClasses(df).withItems(CLASSES.IRI_CLASS, CLASSES.ce).withAnnotations(anns),                                           EquivalentClasses(anns, CLASSES.IRI_CLASS, CLASSES.ce)),
            of(value->new BuilderEquivalentDataProperties(value, df),        new BuilderEquivalentDataProperties(df).withItems(DATAPROPS.IRI_DP, DATAPROPS.dp).withAnnotations(anns),                                   EquivalentDataProperties(anns, DATAPROPS.IRI_DP, DATAPROPS.dp)),
            of(value->new BuilderEquivalentObjectProperties(value, df),      new BuilderEquivalentObjectProperties(df).withItems(OBJPROPS.P, OBJPROPS.op).withAnnotations(anns),                                        EquivalentObjectProperties(anns, OBJPROPS.P, OBJPROPS.op)),
            of(value->new BuilderFacetRestriction(value, df),                new BuilderFacetRestriction(df).withLiteral(LITERALS.lit).withFacet(OWLFacet.MAX_EXCLUSIVE),                                               FacetRestriction(OWLFacet.MAX_EXCLUSIVE, LITERALS.lit)),
            of(value->new BuilderFunctionalDataProperty(value, df),          new BuilderFunctionalDataProperty(df).withProperty(DATAPROPS.dp).withAnnotations(anns),                                                    FunctionalDataProperty(anns, DATAPROPS.dp)),
            of(value->new BuilderFunctionalObjectProperty(value, df),        new BuilderFunctionalObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                                   FunctionalObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderHasKey(value, df),                          new BuilderHasKey(df).withAnnotations(anns).withClass(CLASSES.ce).withItems(OBJPROPS.P, OBJPROPS.op),                                      HasKey(anns, CLASSES.ce, OBJPROPS.P, OBJPROPS.op)),
            of(value->new BuilderImportsDeclaration(value, df),              new BuilderImportsDeclaration(df).withImportedOntology(IRIS.iri),                                                                          ImportsDeclaration(IRIS.iri)),
            of(value->new BuilderInverseFunctionalObjectProperty(value, df), new BuilderInverseFunctionalObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                            InverseFunctionalObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderInverseObjectProperties(value, df),         new BuilderInverseObjectProperties(df).withProperty(OBJPROPS.op).withInverseProperty(OBJPROPS.op).withAnnotations(anns),                   InverseObjectProperties(anns, OBJPROPS.op, OBJPROPS.op)),
            of(value->new BuilderIrreflexiveObjectProperty(value, df),       new BuilderIrreflexiveObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                                  IrreflexiveObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withValue(true).withAnnotations(anns),                                                                              Literal(true)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withValue(1).withAnnotations(anns),                                                                                 Literal(1)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withValue(1L).withAnnotations(anns),                                                                                Literal(1L)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withValue(1.1D).withAnnotations(anns),                                                                              Literal(1.1D)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withValue(1.2F).withAnnotations(anns),                                                                              Literal(1.2F)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withLiteralForm(TEST).withAnnotations(anns),                                                                        Literal(TEST)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withLiteralForm(TEST).withLanguage("en").withAnnotations(anns),                                                     Literal(TEST, "en")),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withLiteralForm(TEST).withDatatype(OWL2Datatype.XSD_STRING).withAnnotations(anns),                                  Literal(TEST, OWL2Datatype.XSD_STRING)),
            of(value->new BuilderLiteral(value, df),                         new BuilderLiteral(df).withLiteralForm("3.14").withDatatype(OWL2Datatype.OWL_REAL).withAnnotations(anns),                                  Literal("3.14", OWL2Datatype.OWL_REAL)),
            of(value->new BuilderNamedIndividual(value, df),                 new BuilderNamedIndividual(df).withIRI(IRIS.iri),                                                                                          INDIVIDUALS.IRI_IND),
            of(value->new BuilderNegativeDataPropertyAssertion(value, df),   new BuilderNegativeDataPropertyAssertion(df).withAnnotations(anns).withProperty(DATAPROPS.dp).withValue(LITERALS.lit).withSubject(INDIVIDUALS.i), NegativeDataPropertyAssertion(anns, DATAPROPS.dp, INDIVIDUALS.i, LITERALS.lit)),
            of(value->new BuilderNegativeObjectPropertyAssertion(value, df), new BuilderNegativeObjectPropertyAssertion(df).withAnnotations(anns).withProperty(OBJPROPS.op).withValue(INDIVIDUALS.i).withSubject(INDIVIDUALS.i), NegativeObjectPropertyAssertion(anns, OBJPROPS.op, INDIVIDUALS.i, INDIVIDUALS.i)),
            of(value->new BuilderObjectAllValuesFrom(value, df),             new BuilderObjectAllValuesFrom(df).withProperty(OBJPROPS.op).withRange(CLASSES.ce),                                                        ObjectAllValuesFrom(OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderObjectExactCardinality(value, df),          new BuilderObjectExactCardinality(df).withCardinality(1).withProperty(OBJPROPS.op).withRange(CLASSES.ce),                                  ObjectExactCardinality(1, OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderObjectHasSelf(value, df),                   new BuilderObjectHasSelf(df).withProperty(OBJPROPS.op),                                                                                    ObjectHasSelf(OBJPROPS.op)),
            of(value->new BuilderObjectHasValue(value, df),                  new BuilderObjectHasValue(df).withProperty(OBJPROPS.op).withValue(INDIVIDUALS.i),                                                          ObjectHasValue(OBJPROPS.op, INDIVIDUALS.i)),
            of(value->new BuilderObjectIntersectionOf(value, df),            new BuilderObjectIntersectionOf(df).withItems(CLASSES.ce, CLASSES.IRI_CLASS),                                                              ObjectIntersectionOf(CLASSES.ce, CLASSES.IRI_CLASS)),
            of(value->new BuilderObjectInverseOf(value, df),                 new BuilderObjectInverseOf(df).withProperty(OBJPROPS.op),                                                                                  ObjectInverseOf(OBJPROPS.op)),
            of(value->new BuilderObjectMaxCardinality(value, df),            new BuilderObjectMaxCardinality(df).withCardinality(1).withProperty(OBJPROPS.op).withRange(CLASSES.ce),                                    ObjectMaxCardinality(1, OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderObjectMinCardinality(value, df),            new BuilderObjectMinCardinality(df).withCardinality(1).withProperty(OBJPROPS.op).withRange(CLASSES.ce),                                    ObjectMinCardinality(1, OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderObjectProperty(value, df),                  new BuilderObjectProperty(df).withIRI(OBJPROPS.P.getIRI()),                                                                                OBJPROPS.P),
            of(value->new BuilderObjectPropertyAssertion(value, df),         new BuilderObjectPropertyAssertion(df).withProperty(OBJPROPS.op).withSubject(INDIVIDUALS.i).withValue(INDIVIDUALS.i).withAnnotations(anns),ObjectPropertyAssertion(anns, OBJPROPS.op, INDIVIDUALS.i, INDIVIDUALS.i)),
            of(value->new BuilderObjectPropertyDomain(value, df),            new BuilderObjectPropertyDomain(df).withDomain(CLASSES.ce).withProperty(OBJPROPS.op).withAnnotations(anns),                                ObjectPropertyDomain(anns, OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderObjectPropertyRange(value, df),             new BuilderObjectPropertyRange(df).withProperty(OBJPROPS.op).withRange(CLASSES.ce).withAnnotations(anns),                                  ObjectPropertyRange(anns, OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderObjectSomeValuesFrom(value, df),            new BuilderObjectSomeValuesFrom(df).withProperty(OBJPROPS.op).withRange(CLASSES.ce),                                                       ObjectSomeValuesFrom(OBJPROPS.op, CLASSES.ce)),
            of(value->new BuilderOneOf(value, df),                           new BuilderOneOf(df).withItem(INDIVIDUALS.i),                                                                                              ObjectOneOf(INDIVIDUALS.i)),
            of(value->new BuilderPropertyChain(value, df),                   new BuilderPropertyChain(df).withProperty(OBJPROPS.op).withAnnotations(anns).withPropertiesInChain(l(OBJPROPS.P, OBJPROPS.op)),            SubPropertyChainOf(anns, l(OBJPROPS.P, OBJPROPS.op), OBJPROPS.op)),
            of(value->new BuilderReflexiveObjectProperty(value, df),         new BuilderReflexiveObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                                    ReflexiveObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderSameIndividual(value, df),                  new BuilderSameIndividual(df).withItems(INDIVIDUALS.i, INDIVIDUALS.IRI_IND).withAnnotations(anns),                                         SameIndividual(anns, INDIVIDUALS.i, INDIVIDUALS.IRI_IND)),
            of(value->new BuilderSubAnnotationPropertyOf(value, df),         new BuilderSubAnnotationPropertyOf(df).withSub(ANNPROPS.ap).withSup(RDFSLabel()).withAnnotations(anns),                                    SubAnnotationPropertyOf(anns, ANNPROPS.ap, RDFSLabel())),
            of(value->new BuilderSubClass(value, df),                        new BuilderSubClass(df).withAnnotations(anns).withSub(CLASSES.ce).withSup(OWLThing()),                                                     SubClassOf(anns, CLASSES.ce, OWLThing())),
            of(value->new BuilderSubDataProperty(value, df),                 new BuilderSubDataProperty(df).withSub(DATAPROPS.dp).withSup(TopDataProperty()),                                                           SubDataPropertyOf(DATAPROPS.dp, TopDataProperty())),
            of(value->new BuilderSubObjectProperty(value, df),               new BuilderSubObjectProperty(df).withSub(OBJPROPS.op).withSup(TopObjectProperty()).withAnnotations(anns),                                  SubObjectPropertyOf(anns, OBJPROPS.op, TopObjectProperty())),
            of(value->new BuilderSWRLBuiltInAtom(value, df),                 new BuilderSWRLBuiltInAtom(df).with(IRIS.iri).with(SWRL.var1),                                                                             SWRLBuiltInAtom(IRIS.iri, SWRL.var1)),
            of(value->new BuilderSWRLClassAtom(value, df),                   new BuilderSWRLClassAtom(df).with(CLASSES.ce).with(SWRL.var2),                                                                             SWRLClassAtom(CLASSES.ce, SWRL.var2)),
            of(value->new BuilderSWRLDataPropertyAtom(value, df),            new BuilderSWRLDataPropertyAtom(df).withProperty(DATAPROPS.dp).with((SWRLIArgument)SWRL.var2).with((SWRLDArgument)SWRL.var1),              SWRLDataPropertyAtom(DATAPROPS.dp, SWRL.var2, SWRL.var1)),
            of(value->new BuilderSWRLDataRangeAtom(value, df),               new BuilderSWRLDataRangeAtom(df).with(Boolean()).with(SWRL.var1),                                                                          SWRLDataRangeAtom(Boolean(), SWRL.var1)),
            of(value->new BuilderSWRLDifferentIndividualsAtom(value, df),    new BuilderSWRLDifferentIndividualsAtom(df).withArg0(SWRL.var2).withArg1(SWRL.var2).withAnnotations(anns),                                 SWRLDifferentIndividualsAtom(SWRL.var2, SWRL.var2)),
            of(value->new BuilderSWRLIndividualArgument(value, df),          new BuilderSWRLIndividualArgument(df).with(INDIVIDUALS.i),                                                                                 SWRLIndividualArgument(INDIVIDUALS.i)),
            of(value->new BuilderSWRLLiteralArgument(value, df),             new BuilderSWRLLiteralArgument(df).with(LITERALS.lit),                                                                                     SWRLLiteralArgument(LITERALS.lit)),
            of(value->new BuilderSWRLObjectPropertyAtom(value, df),          new BuilderSWRLObjectPropertyAtom(df).withProperty(OBJPROPS.op).withArg0(SWRL.var2).withArg1(SWRL.var2),                                   SWRLObjectPropertyAtom(OBJPROPS.op, SWRL.var2, SWRL.var2)),
            of(value->new BuilderSWRLRule(value, df),                        new BuilderSWRLRule(df).withBody(v1).withHead(v2).withAnnotations(anns),                                                                   SWRLRule(anns, body, head)),
            of(value->new BuilderSWRLSameIndividualAtom(value, df),          new BuilderSWRLSameIndividualAtom(df).withArg0(SWRLIndividualArgument(INDIVIDUALS.i)).withArg1(SWRLIndividualArgument(INDIVIDUALS.i)),     SWRLSameIndividualAtom(SWRLIndividualArgument(INDIVIDUALS.i), SWRLIndividualArgument(INDIVIDUALS.i))),
            of(value->new BuilderSWRLVariable(value, df),                    new BuilderSWRLVariable(df).with(IRIS.iri),                                                                                                SWRLVariable(IRIS.iri)),
            of(value->new BuilderSymmetricObjectProperty(value, df),         new BuilderSymmetricObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                                    SymmetricObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderTransitiveObjectProperty(value, df),        new BuilderTransitiveObjectProperty(df).withProperty(OBJPROPS.op).withAnnotations(anns),                                                   TransitiveObjectProperty(anns, OBJPROPS.op)),
            of(value->new BuilderUnionOf(value, df),                         new BuilderUnionOf(df).withItems(CLASSES.ce, CLASSES.IRI_CLASS),                                                                           ObjectUnionOf(CLASSES.ce, CLASSES.IRI_CLASS)));
        //@formatter:on
    }

    static <Q> Arguments of(Function<Q, Builder<Q>> p, Builder<Q> b, Q expected) {
        return Arguments.of(p, b, expected);
    }

    @ParameterizedTest
    @MethodSource("getData")
    <Q> void shouldTest(Function<Q, Builder<Q>> p, Builder<Q> b, Q expected) {
        assertEquals(expected, b.buildObject());
        if (expected instanceof OWLAxiom) {
            OWLOntology o = createAnon();
            b.applyChanges(o);
            assertTrue(o.containsAxiom((OWLAxiom) expected),
                expected.toString() + " but " + o.toString());
        }
        Q created = p.apply(expected).buildObject();
        assertEquals(expected.hashCode(), created.hashCode());
        assertEquals(expected, created);
    }
}
