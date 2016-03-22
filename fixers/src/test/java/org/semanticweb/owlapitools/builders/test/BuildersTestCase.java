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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapitools.builders.*;

import com.google.common.collect.Sets;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class BuildersTestCase<Q> {

    protected static @Nonnull OWLDataFactory df = new OWLDataFactoryImpl();
    protected static @Nonnull OWLAnnotationProperty ap = df.getOWLAnnotationProperty("urn:test#", "ann");
    protected static @Nonnull OWLObjectProperty op = df.getOWLObjectProperty("urn:test#", "op");
    protected static @Nonnull OWLDataProperty dp = df.getOWLDataProperty("urn:test#", "dp");
    protected static @Nonnull OWLLiteral lit = df.getOWLLiteral(false);
    protected static @Nonnull IRI iri = IRI.create("urn:test#", "iri");
    protected static @Nonnull Set<OWLAnnotation> anns = Sets.newHashSet(df.getOWLAnnotation(ap, df.getOWLLiteral(
        "test")));
    protected static @Nonnull OWLClass ce = df.getOWLClass("urn:test#", "c");
    protected static @Nonnull OWLNamedIndividual i = df.getOWLNamedIndividual("urn:test#", "i");
    protected static @Nonnull OWLDatatype d = df.getBooleanOWLDatatype();
    protected static @Nonnull Set<OWLDataProperty> dps = Sets.newHashSet(df.getOWLDataProperty(iri), dp);
    protected static @Nonnull Set<OWLObjectProperty> ops = Sets.newHashSet(df.getOWLObjectProperty(iri), op);
    protected static @Nonnull Set<OWLClass> classes = Sets.newHashSet(df.getOWLClass(iri), ce);
    protected static @Nonnull Set<OWLNamedIndividual> inds = Sets.newHashSet(i, df.getOWLNamedIndividual(iri));
    protected static @Nonnull SWRLDArgument var1 = df.getSWRLVariable("urn:test#", "var1");
    protected static @Nonnull SWRLIArgument var2 = df.getSWRLVariable("urn:test#", "var2");
    protected static @Nonnull SWRLAtom v1 = df.getSWRLBuiltInAtom(IRI.create("urn:test#", "v1"), Arrays.asList(
        (SWRLDArgument) df.getSWRLVariable("urn:test#", "var3"), df.getSWRLVariable("urn:test#", "var4")));
    protected static @Nonnull SWRLAtom v2 = df.getSWRLBuiltInAtom(IRI.create("urn:test#", "v2"), Arrays.asList(
        (SWRLDArgument) df.getSWRLVariable("urn:test#", "var5"), df.getSWRLVariable("urn:test#", "var6")));
    protected static @Nonnull Set<SWRLAtom> body = Sets.newHashSet(v1);
    protected static @Nonnull Set<SWRLAtom> head = Sets.newHashSet(v2);

    private static interface Prepare<T> {

        Builder<T> build(T t);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Collection<Object[]> toReturn = new ArrayList<>();
        //@formatter:off
        toReturn.add(new Object[] {(Prepare<OWLAnnotation>)                             x->new BuilderAnnotation(x, df),                     new BuilderAnnotation(df).withProperty(ap).withValue(lit),                                                                     df.getOWLAnnotation(ap, lit)});
        toReturn.add(new Object[] {(Prepare<OWLAnnotationAssertionAxiom>)               x->new BuilderAnnotationAssertion(x,df),             new BuilderAnnotationAssertion(df).withAnnotations(anns).withProperty(ap).withSubject(iri).withValue(lit),              df.getOWLAnnotationAssertionAxiom(ap, iri, lit, anns)});
        toReturn.add(new Object[] {(Prepare<OWLAnnotationProperty>)                     x->new BuilderAnnotationProperty(x,df),              new BuilderAnnotationProperty(df).withIRI(iri),                                                                                df.getOWLAnnotationProperty(iri)});
        toReturn.add(new Object[] {(Prepare<OWLAnnotationPropertyDomainAxiom>)          x->new BuilderAnnotationPropertyDomain(x,df),        new BuilderAnnotationPropertyDomain(df).withProperty(ap).withDomain(iri).withAnnotations(anns),                         df.getOWLAnnotationPropertyDomainAxiom(ap, iri, anns)});
        toReturn.add(new Object[] {(Prepare<OWLAnnotationPropertyRangeAxiom>)           x->new BuilderAnnotationPropertyRange(x,df),         new BuilderAnnotationPropertyRange(df).withProperty(ap).withRange(iri).withAnnotations(anns),                           df.getOWLAnnotationPropertyRangeAxiom(ap, iri, anns)});
        toReturn.add(new Object[] {(Prepare<OWLAnonymousIndividual>)                    x->new BuilderAnonymousIndividual(x,df),             new BuilderAnonymousIndividual(df).withId("id"),                                                                               df.getOWLAnonymousIndividual("id")});
        toReturn.add(new Object[] {(Prepare<OWLAsymmetricObjectPropertyAxiom>)          x->new BuilderAsymmetricObjectProperty(x,df),        new BuilderAsymmetricObjectProperty(df).withProperty(op).withAnnotations(anns),                                         df.getOWLAsymmetricObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLClass>)                                  x->new BuilderClass(x,df),                           new BuilderClass(df).withIRI(iri),                                                                                             df.getOWLClass(iri)});
        toReturn.add(new Object[] {(Prepare<OWLClassAssertionAxiom>)                    x->new BuilderClassAssertion(x,df),                  new BuilderClassAssertion(df).withClass(ce).withIndividual(i).withAnnotations(anns),                                    df.getOWLClassAssertionAxiom(ce, i, anns)});
        toReturn.add(new Object[] {(Prepare<OWLObjectComplementOf>)                     x->new BuilderComplementOf(x,df),                    new BuilderComplementOf(df).withClass(ce),                                                                                     df.getOWLObjectComplementOf(ce)});
        toReturn.add(new Object[] {(Prepare<OWLDataAllValuesFrom>)                      x->new BuilderDataAllValuesFrom(x,df),               new BuilderDataAllValuesFrom(df).withProperty(dp).withRange(d),                                                                df.getOWLDataAllValuesFrom(dp, d)});
        toReturn.add(new Object[] {(Prepare<OWLDataComplementOf>)                       x->new BuilderDataComplementOf(x,df),                new BuilderDataComplementOf(df).withRange(d),                                                                                  df.getOWLDataComplementOf(d)});
        toReturn.add(new Object[] {(Prepare<OWLDataExactCardinality>)                   x->new BuilderDataExactCardinality(x,df),            new BuilderDataExactCardinality(df).withCardinality(1).withProperty(dp).withRange(d),                                          df.getOWLDataExactCardinality(1, dp, d)});
        toReturn.add(new Object[] {(Prepare<OWLDataHasValue>)                           x->new BuilderDataHasValue(x,df),                    new BuilderDataHasValue(df).withProperty(dp).withLiteral(lit),                                                                 df.getOWLDataHasValue(dp, lit)});
        toReturn.add(new Object[] {(Prepare<OWLDataIntersectionOf>)                     x->new BuilderDataIntersectionOf(x,df),              new BuilderDataIntersectionOf(df).withItem(d).withItem(df.getFloatOWLDatatype()),                                              df.getOWLDataIntersectionOf(d, df.getFloatOWLDatatype())});
        toReturn.add(new Object[] {(Prepare<OWLDataMaxCardinality>)                     x->new BuilderDataMaxCardinality(x,df),              new BuilderDataMaxCardinality(df).withCardinality(1).withProperty(dp).withRange(d),                                            df.getOWLDataMaxCardinality(1, dp, d)});
        toReturn.add(new Object[] {(Prepare<OWLDataMinCardinality>)                     x->new BuilderDataMinCardinality(x,df),              new BuilderDataMinCardinality(df).withCardinality(1).withProperty(dp).withRange(d),                                            df.getOWLDataMinCardinality(1, dp, d)});
        toReturn.add(new Object[] {(Prepare<OWLDataOneOf>)                              x->new BuilderDataOneOf(x,df),                       new BuilderDataOneOf(df).withItem(lit),                                                                                        df.getOWLDataOneOf(lit)});
        toReturn.add(new Object[] {(Prepare<OWLDataProperty>)                           x->new BuilderDataProperty(x,df),                    new BuilderDataProperty(df).withIRI(iri),                                                                                      df.getOWLDataProperty(iri)});
        toReturn.add(new Object[] {(Prepare<OWLDataPropertyAssertionAxiom>)             x->new BuilderDataPropertyAssertion(x,df),           new BuilderDataPropertyAssertion(df).withProperty(dp).withSubject(i).withValue(lit).withAnnotations(anns),              df.getOWLDataPropertyAssertionAxiom(dp, i, lit, anns)});
        toReturn.add(new Object[] {(Prepare<OWLDataPropertyDomainAxiom>)                x->new BuilderDataPropertyDomain(x,df),              new BuilderDataPropertyDomain(df).withProperty(dp).withDomain(ce).withAnnotations(anns),                                df.getOWLDataPropertyDomainAxiom(dp, ce, anns)});
        toReturn.add(new Object[] {(Prepare<OWLDataPropertyRangeAxiom>)                 x->new BuilderDataPropertyRange(x,df),               new BuilderDataPropertyRange(df).withProperty(dp).withRange(d).withAnnotations(anns),                                   df.getOWLDataPropertyRangeAxiom(dp, d, anns)});
        toReturn.add(new Object[] {(Prepare<OWLDataSomeValuesFrom>)                     x->new BuilderDataSomeValuesFrom(x,df),              new BuilderDataSomeValuesFrom(df).withProperty(dp).withRange(d),                                                               df.getOWLDataSomeValuesFrom(dp, d)});
        toReturn.add(new Object[] {(Prepare<OWLDatatype>)                               x->new BuilderDatatype(x,df),                        new BuilderDatatype(df).withIRI(iri).withAnnotations(anns),                                                             df.getOWLDatatype(iri)});
        toReturn.add(new Object[] {(Prepare<OWLDatatypeDefinitionAxiom>)                x->new BuilderDatatypeDefinition(x,df),              new BuilderDatatypeDefinition(df).with(df.getOWLDatatype("urn:test:datatype1")).withType(df.getOWLDatatype("urn:test:datatype2")).withAnnotations(anns), df.getOWLDatatypeDefinitionAxiom(df.getOWLDatatype("urn:test:datatype1"), df.getOWLDatatype("urn:test:datatype2"), anns)});
        toReturn.add(new Object[] {(Prepare<OWLDatatypeRestriction>)                    x->new BuilderDatatypeRestriction(x,df),             new BuilderDatatypeRestriction(df).withItem(df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH, lit)).withDatatype(d),              df.getOWLDatatypeRestriction(d, df.getOWLFacetRestriction(OWLFacet.MAX_LENGTH, lit))});
        toReturn.add(new Object[] {(Prepare<OWLDataUnionOf>)                            x->new BuilderDataUnionOf(x,df),                     new BuilderDataUnionOf(df).withItem(d).withItem(df.getDoubleOWLDatatype()),                                                    df.getOWLDataUnionOf(d, df.getDoubleOWLDatatype())});
        toReturn.add(new Object[] {(Prepare<OWLDeclarationAxiom>)                       x->new BuilderDeclaration(x,df),                     new BuilderDeclaration(df).withEntity(ce).withAnnotations(anns),                                                        df.getOWLDeclarationAxiom(ce, anns)});
        toReturn.add(new Object[] {(Prepare<OWLDifferentIndividualsAxiom>)              x->new BuilderDifferentIndividuals(x,df),            new BuilderDifferentIndividuals(df).withItem(i).withItem(df.getOWLNamedIndividual(iri)),                                       df.getOWLDifferentIndividualsAxiom(i, df.getOWLNamedIndividual(iri))});
        toReturn.add(new Object[] {(Prepare<OWLDisjointClassesAxiom>)                   x->new BuilderDisjointClasses(x,df),                 new BuilderDisjointClasses(df).withItem(ce).withItem(df.getOWLClass(iri)),                                                     df.getOWLDisjointClassesAxiom(ce, df.getOWLClass(iri))});
        toReturn.add(new Object[] {(Prepare<OWLDisjointDataPropertiesAxiom>)            x->new BuilderDisjointDataProperties(x,df),          new BuilderDisjointDataProperties(df).withItems(dps).withAnnotations(anns),                                             df.getOWLDisjointDataPropertiesAxiom(dps, anns)});
        toReturn.add(new Object[] {(Prepare<OWLDisjointObjectPropertiesAxiom>)          x->new BuilderDisjointObjectProperties(x,df),        new BuilderDisjointObjectProperties(df).withItems(ops).withAnnotations(anns),                                           df.getOWLDisjointObjectPropertiesAxiom(ops, anns)});
        toReturn.add(new Object[] {(Prepare<OWLDisjointUnionAxiom>)                     x->new BuilderDisjointUnion(x,df),                   new BuilderDisjointUnion(df).withClass(ce).withItems(classes).withAnnotations(anns),                                    df.getOWLDisjointUnionAxiom(ce, classes, anns)});
        toReturn.add(new Object[] {(Prepare<OWLEntity>)                                 x->new BuilderEntity(x,df),                          new BuilderEntity(df).withIRI(iri).withType(EntityType.CLASS),                                                                 df.getOWLClass(iri)});
        toReturn.add(new Object[] {(Prepare<OWLEquivalentClassesAxiom>)                 x->new BuilderEquivalentClasses(x,df),               new BuilderEquivalentClasses(df).withItems(classes).withAnnotations(anns),                                              df.getOWLEquivalentClassesAxiom(classes, anns)});
        toReturn.add(new Object[] {(Prepare<OWLEquivalentDataPropertiesAxiom>)          x->new BuilderEquivalentDataProperties(x,df),        new BuilderEquivalentDataProperties(df).withItems(dps).withAnnotations(anns),                                           df.getOWLEquivalentDataPropertiesAxiom(dps, anns)});
        toReturn.add(new Object[] {(Prepare<OWLEquivalentObjectPropertiesAxiom>)        x->new BuilderEquivalentObjectProperties(x,df),      new BuilderEquivalentObjectProperties(df).withItems(ops).withAnnotations(anns),                                         df.getOWLEquivalentObjectPropertiesAxiom(ops, anns)});
        toReturn.add(new Object[] {(Prepare<OWLFacetRestriction>)                       x->new BuilderFacetRestriction(x,df),                new BuilderFacetRestriction(df).withLiteral(lit).withFacet(OWLFacet.MAX_EXCLUSIVE),                                            df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, lit)});
        toReturn.add(new Object[] {(Prepare<OWLFunctionalDataPropertyAxiom>)            x->new BuilderFunctionalDataProperty(x,df),          new BuilderFunctionalDataProperty(df).withProperty(dp).withAnnotations(anns),                                           df.getOWLFunctionalDataPropertyAxiom(dp, anns)});
        toReturn.add(new Object[] {(Prepare<OWLFunctionalObjectPropertyAxiom>)          x->new BuilderFunctionalObjectProperty(x,df),        new BuilderFunctionalObjectProperty(df).withProperty(op).withAnnotations(anns),                                         df.getOWLFunctionalObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLHasKeyAxiom>)                            x->new BuilderHasKey(x,df),                          new BuilderHasKey(df).withAnnotations(anns).withClass(ce).withItems(ops),                                               df.getOWLHasKeyAxiom(ce, ops, anns)});
        toReturn.add(new Object[] {(Prepare<OWLImportsDeclaration>)                     x->new BuilderImportsDeclaration(x,df),              new BuilderImportsDeclaration(df).withImportedOntology(iri),                                                                   df.getOWLImportsDeclaration(iri)});
        toReturn.add(new Object[] {(Prepare<OWLInverseFunctionalObjectPropertyAxiom>)   x->new BuilderInverseFunctionalObjectProperty(x,df), new BuilderInverseFunctionalObjectProperty(df).withProperty(op).withAnnotations(anns),                                  df.getOWLInverseFunctionalObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLInverseObjectPropertiesAxiom>)           x->new BuilderInverseObjectProperties(x,df),         new BuilderInverseObjectProperties(df).withProperty(op).withInverseProperty(op).withAnnotations(anns),                  df.getOWLInverseObjectPropertiesAxiom(op, op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLIrreflexiveObjectPropertyAxiom>)         x->new BuilderIrreflexiveObjectProperty(x,df),       new BuilderIrreflexiveObjectProperty(df).withProperty(op).withAnnotations(anns),                                        df.getOWLIrreflexiveObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withValue(true).withAnnotations(anns),                                                           df.getOWLLiteral(true)});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withValue(1).withAnnotations(anns),                                                              df.getOWLLiteral(1)});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withValue(1.1D).withAnnotations(anns),                                                           df.getOWLLiteral(1.1D)});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withValue(1.2F).withAnnotations(anns),                                                           df.getOWLLiteral(1.2F)});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withLiteralForm("test").withAnnotations(anns),                                                   df.getOWLLiteral("test")});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withLiteralForm("test").withLanguage("en").withAnnotations(anns),                                df.getOWLLiteral("test", "en")});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withLiteralForm("test").withDatatype(OWL2Datatype.XSD_STRING).withAnnotations(anns),             df.getOWLLiteral("test", OWL2Datatype.XSD_STRING)});
        toReturn.add(new Object[] {(Prepare<OWLLiteral>)                                x->new BuilderLiteral(x,df),                         new BuilderLiteral(df).withLiteralForm("3.14").withDatatype(OWL2Datatype.OWL_REAL).withAnnotations(anns),               df.getOWLLiteral("3.14", OWL2Datatype.OWL_REAL)});
        toReturn.add(new Object[] {(Prepare<OWLNamedIndividual>)                        x->new BuilderNamedIndividual(x,df),                 new BuilderNamedIndividual(df).withIRI(iri),                                                                                   df.getOWLNamedIndividual(iri)});
        toReturn.add(new Object[] {(Prepare<OWLNegativeDataPropertyAssertionAxiom>)     x->new BuilderNegativeDataPropertyAssertion(x,df),   new BuilderNegativeDataPropertyAssertion(df).withAnnotations(anns).withProperty(dp).withValue(lit).withSubject(i),      df.getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit, anns)});
        toReturn.add(new Object[] {(Prepare<OWLNegativeObjectPropertyAssertionAxiom>)   x->new BuilderNegativeObjectPropertyAssertion(x,df), new BuilderNegativeObjectPropertyAssertion(df).withAnnotations(anns).withProperty(op).withValue(i).withSubject(i),      df.getOWLNegativeObjectPropertyAssertionAxiom(op, i, i, anns)});
        toReturn.add(new Object[] {(Prepare<OWLObjectAllValuesFrom>)                    x->new BuilderObjectAllValuesFrom(x,df),             new BuilderObjectAllValuesFrom(df).withProperty(op).withRange(ce),                                                             df.getOWLObjectAllValuesFrom(op, ce)});
        toReturn.add(new Object[] {(Prepare<OWLObjectExactCardinality>)                 x->new BuilderObjectExactCardinality(x,df),          new BuilderObjectExactCardinality(df).withCardinality(1).withProperty(op).withRange(ce),                                       df.getOWLObjectExactCardinality(1, op, ce)});
        toReturn.add(new Object[] {(Prepare<OWLObjectHasSelf>)                          x->new BuilderObjectHasSelf(x,df),                   new BuilderObjectHasSelf(df).withProperty(op),                                                                                 df.getOWLObjectHasSelf(op)});
        toReturn.add(new Object[] {(Prepare<OWLObjectHasValue>)                         x->new BuilderObjectHasValue(x,df),                  new BuilderObjectHasValue(df).withProperty(op).withValue(i),                                                                   df.getOWLObjectHasValue(op, i)});
        toReturn.add(new Object[] {(Prepare<OWLObjectIntersectionOf>)                   x->new BuilderObjectIntersectionOf(x,df),            new BuilderObjectIntersectionOf(df).withItems(classes),                                                                        df.getOWLObjectIntersectionOf(classes)});
        toReturn.add(new Object[] {(Prepare<OWLObjectInverseOf>)                        x->new BuilderObjectInverseOf(x,df),                 new BuilderObjectInverseOf(df).withProperty(op),                                                                               df.getOWLObjectInverseOf(op)});
        toReturn.add(new Object[] {(Prepare<OWLObjectMaxCardinality>)                   x->new BuilderObjectMaxCardinality(x,df),            new BuilderObjectMaxCardinality(df).withCardinality(1).withProperty(op).withRange(ce),                                         df.getOWLObjectMaxCardinality(1, op, ce)});
        toReturn.add(new Object[] {(Prepare<OWLObjectMinCardinality>)                   x->new BuilderObjectMinCardinality(x,df),            new BuilderObjectMinCardinality(df).withCardinality(1).withProperty(op).withRange(ce),                                         df.getOWLObjectMinCardinality(1, op, ce)});
        toReturn.add(new Object[] {(Prepare<OWLObjectProperty>)                         x->new BuilderObjectProperty(x,df),                  new BuilderObjectProperty(df).withIRI(iri),                                                                                    df.getOWLObjectProperty(iri)});
        toReturn.add(new Object[] {(Prepare<OWLObjectPropertyAssertionAxiom>)           x->new BuilderObjectPropertyAssertion(x,df),         new BuilderObjectPropertyAssertion(df).withProperty(op).withSubject(i).withValue(i).withAnnotations(anns),              df.getOWLObjectPropertyAssertionAxiom(op, i, i, anns)});
        toReturn.add(new Object[] {(Prepare<OWLObjectPropertyDomainAxiom>)              x->new BuilderObjectPropertyDomain(x,df),            new BuilderObjectPropertyDomain(df).withDomain(ce).withProperty(op).withAnnotations(anns),                              df.getOWLObjectPropertyDomainAxiom(op, ce, anns)});
        toReturn.add(new Object[] {(Prepare<OWLObjectPropertyRangeAxiom>)               x->new BuilderObjectPropertyRange(x,df),             new BuilderObjectPropertyRange(df).withProperty(op).withRange(ce).withAnnotations(anns),                                df.getOWLObjectPropertyRangeAxiom(op, ce, anns)});
        toReturn.add(new Object[] {(Prepare<OWLObjectSomeValuesFrom>)                   x->new BuilderObjectSomeValuesFrom(x,df),            new BuilderObjectSomeValuesFrom(df).withProperty(op).withRange(ce),                                                            df.getOWLObjectSomeValuesFrom(op, ce)});
        toReturn.add(new Object[] {(Prepare<OWLObjectOneOf>)                            x->new BuilderOneOf(x,df),                           new BuilderOneOf(df).withItem(i),                                                                                              df.getOWLObjectOneOf(i)});
        toReturn.add(new Object[] {(Prepare<OWLSubPropertyChainOfAxiom>)                x->new BuilderPropertyChain(x,df),                   new BuilderPropertyChain(df).withProperty(op).withAnnotations(anns).withPropertiesInChain(new ArrayList<OWLObjectPropertyExpression>(ops)), df.getOWLSubPropertyChainOfAxiom(new ArrayList<OWLObjectPropertyExpression>(ops), op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLReflexiveObjectPropertyAxiom>)           x->new BuilderReflexiveObjectProperty(x,df),         new BuilderReflexiveObjectProperty(df).withProperty(op).withAnnotations(anns),                                          df.getOWLReflexiveObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLSameIndividualAxiom>)                    x->new BuilderSameIndividual(x,df),                  new BuilderSameIndividual(df).withItems(inds).withAnnotations(anns),                                                    df.getOWLSameIndividualAxiom(inds, anns)});
        toReturn.add(new Object[] {(Prepare<OWLSubAnnotationPropertyOfAxiom>)           x->new BuilderSubAnnotationPropertyOf(x,df),         new BuilderSubAnnotationPropertyOf(df).withSub(ap).withSup(df.getRDFSLabel()).withAnnotations(anns),                    df.getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(), anns)});
        toReturn.add(new Object[] {(Prepare<OWLSubClassOfAxiom>)                        x->new BuilderSubClass(x,df),                        new BuilderSubClass(df).withAnnotations(anns).withSub(ce).withSup(df.getOWLThing()),                                    df.getOWLSubClassOfAxiom(ce, df.getOWLThing(), anns)});
        toReturn.add(new Object[] {(Prepare<OWLSubDataPropertyOfAxiom>)                 x->new BuilderSubDataProperty(x,df),                 new BuilderSubDataProperty(df).withSub(dp).withSup(df.getOWLTopDataProperty()),                                                df.getOWLSubDataPropertyOfAxiom(dp, df.getOWLTopDataProperty())});
        toReturn.add(new Object[] {(Prepare<OWLSubObjectPropertyOfAxiom>)               x->new BuilderSubObjectProperty(x,df),               new BuilderSubObjectProperty(df).withSub(op).withSup(df.getOWLTopObjectProperty()).withAnnotations(anns),               df.getOWLSubObjectPropertyOfAxiom(op, df.getOWLTopObjectProperty(), anns)});
        toReturn.add(new Object[] {(Prepare<SWRLBuiltInAtom>)                           x->new BuilderSWRLBuiltInAtom(x,df),                 new BuilderSWRLBuiltInAtom(df).with(iri).with(var1),                                                                           df.getSWRLBuiltInAtom(iri, Arrays.asList(var1))});
        toReturn.add(new Object[] {(Prepare<SWRLClassAtom>)                             x->new BuilderSWRLClassAtom(x,df),                   new BuilderSWRLClassAtom(df).with(ce).with(var2),                                                                              df.getSWRLClassAtom(ce, var2)});
        toReturn.add(new Object[] {(Prepare<SWRLDataPropertyAtom>)                      x->new BuilderSWRLDataPropertyAtom(x,df),            new BuilderSWRLDataPropertyAtom(df).withProperty(dp).with(var2).with(var1),                                                    df.getSWRLDataPropertyAtom(dp, var2, var1)});
        toReturn.add(new Object[] {(Prepare<SWRLDataRangeAtom>)                         x->new BuilderSWRLDataRangeAtom(x,df),               new BuilderSWRLDataRangeAtom(df).with(d).with(var1),                                                                           df.getSWRLDataRangeAtom(d, var1)});
        toReturn.add(new Object[] {(Prepare<SWRLDifferentIndividualsAtom>)              x->new BuilderSWRLDifferentIndividualsAtom(x,df),    new BuilderSWRLDifferentIndividualsAtom(df).withArg0(var2).withArg1(var2).withAnnotations(anns),                        df.getSWRLDifferentIndividualsAtom(var2, var2)});
        toReturn.add(new Object[] {(Prepare<SWRLIndividualArgument>)                    x->new BuilderSWRLIndividualArgument(x,df),          new BuilderSWRLIndividualArgument(df).with(i),                                                                                 df.getSWRLIndividualArgument(i)});
        toReturn.add(new Object[] {(Prepare<SWRLLiteralArgument>)                       x->new BuilderSWRLLiteralArgument(x,df),             new BuilderSWRLLiteralArgument(df).with(lit),                                                                                  df.getSWRLLiteralArgument(lit)});
        toReturn.add(new Object[] {(Prepare<SWRLObjectPropertyAtom>)                    x->new BuilderSWRLObjectPropertyAtom(x,df),          new BuilderSWRLObjectPropertyAtom(df).withProperty(op).withArg0(var2).withArg1(var2),                                          df.getSWRLObjectPropertyAtom(op, var2, var2)});
        toReturn.add(new Object[] {(Prepare<SWRLRule>)                                  x->new BuilderSWRLRule(x,df),                        new BuilderSWRLRule(df).withBody(v1).withHead(v2).withAnnotations(anns),                                                df.getSWRLRule(body, head, anns)});
        toReturn.add(new Object[] {(Prepare<SWRLSameIndividualAtom>)                    x->new BuilderSWRLSameIndividualAtom(x,df),          new BuilderSWRLSameIndividualAtom(df).withArg0(df.getSWRLIndividualArgument(i)).withArg1(df.getSWRLIndividualArgument(i)),     df.getSWRLSameIndividualAtom(df.getSWRLIndividualArgument(i), df.getSWRLIndividualArgument(i))});
        toReturn.add(new Object[] {(Prepare<SWRLVariable>)                              x->new BuilderSWRLVariable(x,df),                    new BuilderSWRLVariable(df).with(iri),                                                                                         df.getSWRLVariable(iri)});
        toReturn.add(new Object[] {(Prepare<OWLSymmetricObjectPropertyAxiom>)           x->new BuilderSymmetricObjectProperty(x,df),         new BuilderSymmetricObjectProperty(df).withProperty(op).withAnnotations(anns),                                          df.getOWLSymmetricObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLTransitiveObjectPropertyAxiom>)          x->new BuilderTransitiveObjectProperty(x,df),        new BuilderTransitiveObjectProperty(df).withProperty(op).withAnnotations(anns),                                         df.getOWLTransitiveObjectPropertyAxiom(op, anns)});
        toReturn.add(new Object[] {(Prepare<OWLObjectUnionOf>)                          x->new BuilderUnionOf(x,df),                         new BuilderUnionOf(df).withItems(classes),                                                                                     df.getOWLObjectUnionOf(classes)});
      //@formatter:on
        return toReturn;
    }

    private final Builder<Q> b;
    private final Q expected;
    private final @Nonnull OWLOntologyManager m = getManager();
    private Prepare<Q> p;

    // no parsers and storers injected
    private static OWLOntologyManager getManager() {
        OWLOntologyManager instance = new OWLOntologyManagerImpl(df, new ReentrantReadWriteLock());
        instance.getOntologyFactories().set(new OWLOntologyFactoryImpl((o, id) -> new OWLOntologyImpl(o, id)));
        return instance;
    }

    public BuildersTestCase(Prepare<Q> p, Builder<Q> b, Q o) {
        this.b = b;
        expected = o;
        this.p = p;
    }

    @Test
    public void shouldTest() throws OWLOntologyCreationException {
        assertEquals(expected, b.buildObject());
        if (expected instanceof OWLAxiom) {
            OWLOntology o = m.createOntology();
            b.applyChanges(o);
            assertTrue(expected.toString() + " but " + o.toString(), o.containsAxiom((OWLAxiom) expected));
        }
        assertEquals(expected, p.build(expected).buildObject());
    }
}
