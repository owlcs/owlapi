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
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.util.SimpleRenderer;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyBuilderImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

@SuppressWarnings({ "javadoc" })
public class VisitorsTestCase {

    @Nonnull
    private final OWLDataFactory df = new OWLDataFactoryImpl(true, false);
    @Nonnull
    private final OWLAnnotationProperty ap = df.getOWLAnnotationProperty(IRI
            .create("urn:test#ann"));
    @Nonnull
    private final OWLObjectProperty op = df.getOWLObjectProperty(IRI
            .create("urn:test#op"));
    @Nonnull
    private final OWLDataProperty dp = df.getOWLDataProperty(IRI
            .create("urn:test#dp"));
    @Nonnull
    private final OWLLiteral lit = df.getOWLLiteral(false);
    @Nonnull
    private final OWLLiteral plainlit = df.getOWLLiteral("string", "en");
    @Nonnull
    private final IRI iri = IRI.create("urn:test#iri");
    @Nonnull
    private final Set<OWLAnnotation> annotations = new HashSet<>(
            Arrays.asList(df.getOWLAnnotation(ap, df.getOWLLiteral("test"))));
    @Nonnull
    private final OWLClass ce = df.getOWLClass(IRI.create("urn:test#c"));
    @Nonnull
    private final OWLNamedIndividual i = df.getOWLNamedIndividual(IRI
            .create("urn:test#i"));
    @Nonnull
    private final OWLDatatype d = df.getOWLDatatype(IRI
            .create("urn:test#datatype"));
    @Nonnull
    private final Set<OWLDataProperty> dps = new HashSet<>(Arrays.asList(
            df.getOWLDataProperty(iri), dp));
    @Nonnull
    private final Set<OWLObjectProperty> ops = new HashSet<>(Arrays.asList(
            df.getOWLObjectProperty(iri), op));
    @Nonnull
    private final Set<OWLClass> classes = new HashSet<>(Arrays.asList(
            df.getOWLClass(iri), ce));
    @Nonnull
    private final Set<OWLNamedIndividual> inds = new HashSet<>(Arrays.asList(i,
            df.getOWLNamedIndividual(iri)));
    @Nonnull
    private final SWRLAtom v1 = df.getSWRLBuiltInAtom(
            IRI.create("v1"),
            Arrays.asList(
                    (SWRLDArgument) df.getSWRLVariable(IRI.create("var3")),
                    df.getSWRLVariable(IRI.create("var4"))));
    @Nonnull
    private final SWRLAtom v2 = df.getSWRLBuiltInAtom(
            IRI.create("v2"),
            Arrays.asList(
                    (SWRLDArgument) df.getSWRLVariable(IRI.create("var5")),
                    df.getSWRLVariable(IRI.create("var6"))));
    @Nonnull
    private final Set<SWRLAtom> body = new HashSet<>(Arrays.asList(v1));
    @Nonnull
    private final Set<SWRLAtom> head = new HashSet<>(Arrays.asList(v2));
    @Nonnull
    private final SWRLDArgument var1 = df.getSWRLVariable(IRI.create("var1"));
    @Nonnull
    private final SWRLIArgument var2 = df.getSWRLVariable(IRI.create("var2"));
    @Nonnull
    private final OWLOntologyManager m = getManager();

    // no parsers and storers injected
    @Nonnull
    private OWLOntologyManager getManager() {
        OWLOntologyManager instance = new OWLOntologyManagerImpl(df);
        instance.setOntologyFactories(Collections
                .singleton((OWLOntologyFactory) new EmptyInMemOWLOntologyFactory(
                        new OWLOntologyBuilderImpl())));
        return instance;
    }

    @Test
    public void testOntology() throws OWLOntologyCreationException {
        // given
        OWLOntology expected = m.createOntology(IRI.create("urn:test"));
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Ontology(OntologyID(OntologyIRI(<urn:test>) VersionIRI(<null>)) [Axioms: 0] [Logical axioms: 0])",
                render);
    }

    @Test
    public void testAnnotationAssertion() {
        // given
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(ap, iri, lit,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "AnnotationAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#ann> <urn:test#iri> \"false\"^^xsd:boolean)",
                render);
    }

    @Test
    public void testAnnotationPropertyDomain() {
        OWLAxiom expected = df.getOWLAnnotationPropertyDomainAxiom(ap, iri,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals("AnnotationPropertyDomain(<urn:test#ann> <urn:test#iri>)",
                render);
    }

    @Test
    public void testAnnotationPropertyRange() {
        OWLAxiom expected = df.getOWLAnnotationPropertyRangeAxiom(ap, iri,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals("AnnotationPropertyRange(<urn:test#ann> <urn:test#iri>)",
                render);
    }

    @Test
    public void testAsymmetricObjectProperty() {
        OWLAxiom expected = df.getOWLAsymmetricObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "AsymmetricObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testClassAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(ce, i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#i>)",
                render);
    }

    @Test
    public void testClassAndAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectIntersectionOf(classes), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectIntersectionOf(<urn:test#c> <urn:test#iri>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassOrAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectUnionOf(classes), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectUnionOf(<urn:test#c> <urn:test#iri>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataAndAssertion() {
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(dp,
                df.getOWLDataIntersectionOf(d, df.getOWLDataOneOf(lit)),
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataIntersectionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ))",
                render);
    }

    @Test
    public void testClassDataOrAssertion() {
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(dp,
                df.getOWLDataUnionOf(d, df.getOWLDataOneOf(lit)), annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataUnionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ))",
                render);
    }

    @Test
    public void testClassNotAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectComplementOf(ce), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test#c>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassNotAnonAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectComplementOf(ce),
                df.getOWLAnonymousIndividual("id"), annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test#c>) _:id)",
                render);
    }

    @Test
    public void testClassSomeAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectSomeValuesFrom(op, ce), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectSomeValuesFrom(<urn:test#op> <urn:test#c>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassAllAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectAllValuesFrom(op, ce), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectAllValuesFrom(<urn:test#op> <urn:test#c>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassHasAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectHasValue(op, i), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectHasValue(<urn:test#op> <urn:test#i>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassMinAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectMinCardinality(1, op, ce), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectMinCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassMaxAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectMaxCardinality(1, op, ce), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectMaxCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassEqAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectExactCardinality(1, op, ce), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectExactCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassHasSelfAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectHasSelf(op), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectHasSelf(<urn:test#op>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassOneOfAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLObjectOneOf(i), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectOneOf(<urn:test#i>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataSomeAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLDataSomeValuesFrom(dp, d), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataSomeValuesFrom(<urn:test#dp> <urn:test#datatype>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataAllAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLDataAllValuesFrom(dp, d), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataAllValuesFrom(<urn:test#dp> <urn:test#datatype>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataHasAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLDataHasValue(dp, lit), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataHasValue(<urn:test#dp> \"false\"^^xsd:boolean) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataMinAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLDataMinCardinality(1, dp, d), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataMinCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataMaxAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLDataMaxCardinality(1, dp, d), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataMaxCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataEqAssertion() {
        OWLAxiom expected = df.getOWLClassAssertionAxiom(
                df.getOWLDataExactCardinality(1, dp, d), i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataExactCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>)",
                render);
    }

    @Test
    public void testClassDataOneOfAssertion() {
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(dp,
                df.getOWLDataOneOf(lit), annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataOneOf(\"false\"^^xsd:boolean ))",
                render);
    }

    @Test
    public void testClassDataNotAssertion() {
        OWLAxiom expected = df
                .getOWLDataPropertyRangeAxiom(dp,
                        df.getOWLDataComplementOf(df.getOWLDataOneOf(lit)),
                        annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataComplementOf(DataOneOf(\"false\"^^xsd:boolean )))",
                render);
    }

    @Test
    public void testClassDatatypeRestrictionAssertion() {
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(dp,
                df.getOWLDatatypeMinMaxExclusiveRestriction(5.0D, 6.0D),
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataRangeRestriction(xsd:double facetRestriction(minExclusive \"5.0\"^^xsd:double) facetRestriction(maxExclusive \"6.0\"^^xsd:double)))",
                render);
    }

    @Test
    public void testDataPropertyAssertion() {
        OWLAxiom expected = df.getOWLDataPropertyAssertionAxiom(dp, i, lit,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"false\"^^xsd:boolean)",
                render);
    }

    @Test
    public void testDataPropertyPlainAssertion() {
        OWLAxiom expected = df.getOWLDataPropertyAssertionAxiom(dp, i,
                plainlit, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"string\"@en)",
                render);
    }

    @Test
    public void testDataPropertyDomain() {
        OWLAxiom expected = df.getOWLDataPropertyDomainAxiom(dp, ce,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyDomain(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#c>)",
                render);
    }

    @Test
    public void testDataPropertyRange() {
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(dp, d, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#datatype>)",
                render);
    }

    @Test
    public void testDatatypeDefinition() {
        OWLAxiom expected = df.getOWLDatatypeDefinitionAxiom(d,
                df.getDoubleOWLDatatype(), annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DatatypeDefinition(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#datatype> xsd:double)",
                render);
    }

    @Test
    public void testClassDeclaration() {
        OWLAxiom expected = df.getOWLDeclarationAxiom(ce, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) Class(<urn:test#c>))",
                render);
    }

    @Test
    public void testOPDeclaration() {
        OWLAxiom expected = df.getOWLDeclarationAxiom(op, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectProperty(<urn:test#op>))",
                render);
    }

    @Test
    public void testDPDeclaration() {
        OWLAxiom expected = df.getOWLDeclarationAxiom(dp, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataProperty(<urn:test#dp>))",
                render);
    }

    @Test
    public void testDatatypeDeclaration() {
        OWLAxiom expected = df.getOWLDeclarationAxiom(d, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) Datatype(<urn:test#datatype>))",
                render);
    }

    @Test
    public void testAPDeclaration() {
        OWLAxiom expected = df.getOWLDeclarationAxiom(ap, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) AnnotationProperty(<urn:test#ann>))",
                render);
    }

    @Test
    public void testIndividualDeclaration() {
        OWLAxiom expected = df.getOWLDeclarationAxiom(i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) NamedIndividual(<urn:test#i>))",
                render);
    }

    @Test
    public void testDifferentIndividuals() {
        OWLAxiom expected = df.getOWLDifferentIndividualsAxiom(i,
                df.getOWLNamedIndividual(iri));
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals("DifferentIndividuals(<urn:test#i> <urn:test#iri> )",
                render);
    }

    @Test
    public void testDisjointClasses() {
        OWLAxiom expected = df.getOWLDisjointClassesAxiom(ce,
                df.getOWLClass(iri));
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals("DisjointClasses(<urn:test#c> <urn:test#iri>)", render);
    }

    @Test
    public void testDisjointDataProperties() {
        OWLAxiom expected = df.getOWLDisjointDataPropertiesAxiom(dps,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DisjointDataProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#iri> )",
                render);
    }

    @Test
    public void testDisjointObjectProperties() {
        OWLAxiom expected = df.getOWLDisjointObjectPropertiesAxiom(ops,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DisjointObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#iri> <urn:test#op> )",
                render);
    }

    @Test
    public void testDisjointUnion() {
        OWLAxiom expected = df.getOWLDisjointUnionAxiom(ce, classes,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DisjointUnion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#c> <urn:test#iri> )",
                render);
    }

    @Test
    public void testEquivalentClasses() {
        OWLAxiom expected = df.getOWLEquivalentClassesAxiom(classes,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "EquivalentClasses(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#iri> )",
                render);
    }

    @Test
    public void testEquivalentDataProperties() {
        OWLAxiom expected = df.getOWLEquivalentDataPropertiesAxiom(dps,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "EquivalentDataProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#iri> )",
                render);
    }

    @Test
    public void testEquivalentObjectProperties() {
        OWLAxiom expected = df.getOWLEquivalentObjectPropertiesAxiom(ops,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "EquivalentObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#iri> <urn:test#op> )",
                render);
    }

    @Test
    public void testFunctionalDataProperty() {
        OWLAxiom expected = df.getOWLFunctionalDataPropertyAxiom(dp,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "FunctionalDataProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp>)",
                render);
    }

    @Test
    public void testFunctionalObjectProperty() {
        OWLAxiom expected = df.getOWLFunctionalObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "FunctionalObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testHasKey() {
        Set<OWLPropertyExpression> props = new HashSet<>(ops);
        props.add(dp);
        OWLAxiom expected = df.getOWLHasKeyAxiom(ce, props, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "HasKey(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> (<urn:test#iri> <urn:test#op> ) (<urn:test#dp> ))",
                render);
    }

    @Test
    public void testInverseFunctionalObjectProperty() {
        OWLAxiom expected = df.getOWLInverseFunctionalObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "InverseFunctionalObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testInverseObjectProperties() {
        OWLAxiom expected = df.getOWLInverseObjectPropertiesAxiom(op, op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "InverseObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#op>)",
                render);
    }

    @Test
    public void testIrreflexiveObjectProperty() {
        OWLAxiom expected = df.getOWLIrreflexiveObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "IrreflexiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testNegativeDataPropertyAssertion() {
        // given
        OWLAxiom expected = df.getOWLNegativeDataPropertyAssertionAxiom(dp, i,
                lit, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "NegativeDataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"false\"^^xsd:boolean)",
                render);
    }

    @Test
    public void testNegativeObjectPropertyAssertion() {
        // given
        OWLAxiom expected = df.getOWLNegativeObjectPropertyAssertionAxiom(op,
                i, i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "NegativeObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#i> <urn:test#i>)",
                render);
    }

    @Test
    public void testObjectPropertyAssertion() {
        // given
        OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(op, i, i,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#i> <urn:test#i>)",
                render);
    }

    @Test
    public void testObjectInversePropertyAssertion() {
        // given
        OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(
                df.getOWLObjectInverseOf(op), i, i, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) InverseOf(<urn:test#op>) <urn:test#i> <urn:test#i>)",
                render);
    }

    @Test
    public void testObjectPropertyDomain() {
        // given
        OWLAxiom expected = df.getOWLObjectPropertyDomainAxiom(op, ce,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ObjectPropertyDomain(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#c>)",
                render);
    }

    @Test
    public void testObjectPropertyRange() {
        // given
        OWLAxiom expected = df.getOWLObjectPropertyRangeAxiom(op, ce,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ObjectPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#c>)",
                render);
    }

    @Test
    public void testPropertyChain() {
        // given
        List<OWLObjectProperty> chain = new ArrayList<>(ops);
        OWLAxiom expected = df.getOWLSubPropertyChainOfAxiom(chain, op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "SubObjectPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectPropertyChain( <urn:test#iri> <urn:test#op> ) <urn:test#op>)",
                render);
    }

    @Test
    public void testReflexiveObjectProperty() {
        // given
        OWLAxiom expected = df.getOWLReflexiveObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "ReflexiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testSameIndividual() {
        // given
        OWLAxiom expected = df.getOWLSameIndividualAxiom(inds, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "SameIndividual(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#i> <urn:test#iri> )",
                render);
    }

    @Test
    public void testSubAnnotationPropertyOf() {
        // given
        OWLAxiom expected = df.getOWLSubAnnotationPropertyOfAxiom(ap,
                df.getRDFSLabel(), annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "SubAnnotationPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#ann> rdfs:label)",
                render);
    }

    @Test
    public void testSubClass() {
        // given
        OWLAxiom expected = df.getOWLSubClassOfAxiom(ce, df.getOWLThing(),
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "SubClassOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> owl:Thing)",
                render);
    }

    @Test
    public void testSubDataProperty() {
        // given
        OWLAxiom expected = df.getOWLSubDataPropertyOfAxiom(dp,
                df.getOWLTopDataProperty());
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals("SubDataPropertyOf(<urn:test#dp> owl:topDataProperty)",
                render);
    }

    @Test
    public void testSubObjectProperty() {
        // given
        OWLAxiom expected = df.getOWLSubObjectPropertyOfAxiom(op,
                df.getOWLTopObjectProperty(), annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "SubObjectPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> owl:topObjectProperty)",
                render);
    }

    @Test
    public void testSWRLRule() {
        // given
        OWLAxiom expected = df.getSWRLRule(body, head);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DLSafeRule( Body(BuiltInAtom(<v1> Variable(<var3>) Variable(<var4>) )) Head(BuiltInAtom(<v2> Variable(<var5>) Variable(<var6>) )) )",
                render);
    }

    @Test
    public void testSymmetricObjectProperty() {
        // given
        OWLAxiom expected = df.getOWLSymmetricObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "SymmetricObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testTransitiveObjectProperty() {
        // given
        OWLAxiom expected = df.getOWLTransitiveObjectPropertyAxiom(op,
                annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "TransitiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)",
                render);
    }

    @Test
    public void testRule() {
        Set<SWRLAtom> body = new HashSet<>();
        body.add(v1);
        body.add(df.getSWRLClassAtom(ce, var2));
        body.add(df.getSWRLDataRangeAtom(d, var1));
        body.add(df.getSWRLBuiltInAtom(iri, Arrays.asList(var1)));
        body.add(df.getSWRLDifferentIndividualsAtom(var2,
                df.getSWRLIndividualArgument(i)));
        body.add(df.getSWRLSameIndividualAtom(var2,
                df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri))));
        body.add(df.getSWRLBuiltInAtom(iri, Arrays.asList(var1)));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(v2);
        head.add(df.getSWRLDataPropertyAtom(dp, var2,
                df.getSWRLLiteralArgument(lit)));
        head.add(df.getSWRLObjectPropertyAtom(op, var2, var2));
        // given
        OWLAxiom expected = df.getSWRLRule(body, head, annotations);
        // when
        String render = new SimpleRenderer().render(expected);
        // then
        assertEquals(
                "DLSafeRule( Body(ClassAtom(<urn:test#c> Variable(<var2>)) DataRangeAtom(<urn:test#datatype> Variable(<var1>)) BuiltInAtom(<v1> Variable(<var3>) Variable(<var4>) ) BuiltInAtom(<urn:test#iri> Variable(<var1>) ) SameAsAtom(Variable(<var2>) <urn:test#iri>) DifferentFromAtom(Variable(<var2>) <urn:test#i>)) Head(ObjectPropertyAtom(<urn:test#op> Variable(<var2>) Variable(<var2>)) DataPropertyAtom(<urn:test#dp> Variable(<var2>) \"false\"^^xsd:boolean) BuiltInAtom(<v2> Variable(<var5>) Variable(<var6>) )) )",
                render);
    }
}
