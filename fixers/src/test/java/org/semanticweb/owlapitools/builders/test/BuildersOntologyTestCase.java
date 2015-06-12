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

import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapitools.builders.*;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImplNoCache;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

@SuppressWarnings({ "javadoc", })
public class BuildersOntologyTestCase {

    @Nonnull
    private final OWLDataFactory df = new OWLDataFactoryImpl(new OWLDataFactoryInternalsImplNoCache(false));
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
    private final OWLDatatype d = df.getOWLDatatype(IRI.create("urn:test#datatype"));
    @Nonnull
    private final Set<OWLDataProperty> dps = new HashSet<>(Arrays.asList(df.getOWLDataProperty(iri), dp));
    @Nonnull
    private final Set<OWLObjectProperty> ops = new HashSet<>(Arrays.asList(df.getOWLObjectProperty(iri), op));
    @Nonnull
    private final Set<OWLClass> classes = new HashSet<>(Arrays.asList(df.getOWLClass(iri), ce));
    @Nonnull
    private final Set<OWLNamedIndividual> inds = new HashSet<>(Arrays.asList(i, df.getOWLNamedIndividual(iri)));
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
    @Nonnull
    private final OWLOntologyManager m = getManager();

    // no parsers and storers injected
    @Nonnull
    private OWLOntologyManager getManager() {
        OWLOntologyManager instance = new OWLOntologyManagerImpl(df, new ReentrantReadWriteLock());
        instance.setOntologyFactories(Collections.singleton((OWLOntologyFactory) new OWLOntologyFactoryImpl(
            new OWLOntologyBuilder() {

                @Nonnull
                @Override
                public OWLOntology createOWLOntology(@Nonnull OWLOntologyManager manager,
                    @Nonnull OWLOntologyID ontologyID) {
                    return new OWLOntologyImpl(manager, ontologyID);
                }
            })));
        return instance;
    }

    @Test
    public void shouldBuildAnnotationAssertion() throws OWLOntologyCreationException {
        // given
        BuilderAnnotationAssertion builder = new BuilderAnnotationAssertion(df).withAnnotations(annotations)
            .withProperty(ap).withSubject(iri).withValue(lit);
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(ap, iri, lit, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildAnnotationPropertyDomain() throws OWLOntologyCreationException {
        // given
        BuilderAnnotationPropertyDomain builder = new BuilderAnnotationPropertyDomain(df).withProperty(ap).withDomain(
            iri).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLAnnotationPropertyDomainAxiom(ap, iri, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildAnnotationPropertyRange() throws OWLOntologyCreationException {
        // given
        BuilderAnnotationPropertyRange builder = new BuilderAnnotationPropertyRange(df).withProperty(ap).withRange(iri)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLAnnotationPropertyRangeAxiom(ap, iri, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildAsymmetricObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderAsymmetricObjectProperty builder = new BuilderAsymmetricObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLAsymmetricObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildClassAssertion() throws OWLOntologyCreationException {
        // given
        BuilderClassAssertion builder = new BuilderClassAssertion(df).withClass(ce).withIndividual(i).withAnnotations(
            annotations);
        OWLAxiom expected = df.getOWLClassAssertionAxiom(ce, i, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDataPropertyAssertion() throws OWLOntologyCreationException {
        // given
        BuilderDataPropertyAssertion builder = new BuilderDataPropertyAssertion(df).withProperty(dp).withSubject(i)
            .withValue(lit).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLDataPropertyAssertionAxiom(dp, i, lit, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDataPropertyDomain() throws OWLOntologyCreationException {
        // given
        BuilderDataPropertyDomain builder = new BuilderDataPropertyDomain(df).withProperty(dp).withDomain(ce)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLDataPropertyDomainAxiom(dp, ce, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDataPropertyRange() throws OWLOntologyCreationException {
        // given
        BuilderDataPropertyRange builder = new BuilderDataPropertyRange(df).withProperty(dp).withRange(d)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(dp, d, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDatatypeDefinition() throws OWLOntologyCreationException {
        // given
        BuilderDatatypeDefinition builder = new BuilderDatatypeDefinition(df).with(d).withType(df
            .getDoubleOWLDatatype()).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLDatatypeDefinitionAxiom(d, df.getDoubleOWLDatatype(), annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDeclaration() throws OWLOntologyCreationException {
        // given
        BuilderDeclaration builder = new BuilderDeclaration(df).withEntity(ce).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLDeclarationAxiom(ce, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDifferentIndividuals() throws OWLOntologyCreationException {
        // given
        BuilderDifferentIndividuals builder = new BuilderDifferentIndividuals(df).withItem(i).withItem(df
            .getOWLNamedIndividual(iri));
        OWLAxiom expected = df.getOWLDifferentIndividualsAxiom(i, df.getOWLNamedIndividual(iri));
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDisjointClasses() throws OWLOntologyCreationException {
        // given
        BuilderDisjointClasses builder = new BuilderDisjointClasses(df).withItem(ce).withItem(df.getOWLClass(iri));
        OWLAxiom expected = df.getOWLDisjointClassesAxiom(ce, df.getOWLClass(iri));
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDisjointDataProperties() throws OWLOntologyCreationException {
        // given
        BuilderDisjointDataProperties builder = new BuilderDisjointDataProperties(df).withItems(dps).withAnnotations(
            annotations);
        OWLAxiom expected = df.getOWLDisjointDataPropertiesAxiom(dps, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDisjointObjectProperties() throws OWLOntologyCreationException {
        // given
        BuilderDisjointObjectProperties builder = new BuilderDisjointObjectProperties(df).withItems(ops)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLDisjointObjectPropertiesAxiom(ops, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildDisjointUnion() throws OWLOntologyCreationException {
        // given
        BuilderDisjointUnion builder = new BuilderDisjointUnion(df).withClass(ce).withItems(classes).withAnnotations(
            annotations);
        OWLAxiom expected = df.getOWLDisjointUnionAxiom(ce, classes, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildEquivalentClasses() throws OWLOntologyCreationException {
        // given
        BuilderEquivalentClasses builder = new BuilderEquivalentClasses(df).withItems(classes).withAnnotations(
            annotations);
        OWLAxiom expected = df.getOWLEquivalentClassesAxiom(classes, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildEquivalentDataProperties() throws OWLOntologyCreationException {
        // given
        BuilderEquivalentDataProperties builder = new BuilderEquivalentDataProperties(df).withItems(dps)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLEquivalentDataPropertiesAxiom(dps, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildEquivalentObjectProperties() throws OWLOntologyCreationException {
        // given
        BuilderEquivalentObjectProperties builder = new BuilderEquivalentObjectProperties(df).withItems(ops)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLEquivalentObjectPropertiesAxiom(ops, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildFunctionalDataProperty() throws OWLOntologyCreationException {
        // given
        BuilderFunctionalDataProperty builder = new BuilderFunctionalDataProperty(df).withProperty(dp).withAnnotations(
            annotations);
        OWLAxiom expected = df.getOWLFunctionalDataPropertyAxiom(dp, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildFunctionalObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderFunctionalObjectProperty builder = new BuilderFunctionalObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLFunctionalObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildHasKey() throws OWLOntologyCreationException {
        // given
        BuilderHasKey builder = new BuilderHasKey(df).withAnnotations(annotations).withClass(ce).withItems(ops);
        OWLAxiom expected = df.getOWLHasKeyAxiom(ce, ops, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildInverseFunctionalObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderInverseFunctionalObjectProperty builder = new BuilderInverseFunctionalObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLInverseFunctionalObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildInverseObjectProperties() throws OWLOntologyCreationException {
        // given
        BuilderInverseObjectProperties builder = new BuilderInverseObjectProperties(df).withProperty(op)
            .withInverseProperty(op).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLInverseObjectPropertiesAxiom(op, op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildIrreflexiveObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderIrreflexiveObjectProperty builder = new BuilderIrreflexiveObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLIrreflexiveObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildNegativeDataPropertyAssertion() throws OWLOntologyCreationException {
        // given
        BuilderNegativeDataPropertyAssertion builder = new BuilderNegativeDataPropertyAssertion(df).withAnnotations(
            annotations).withProperty(dp).withValue(lit).withSubject(i);
        OWLAxiom expected = df.getOWLNegativeDataPropertyAssertionAxiom(dp, i, lit, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildNegativeObjectPropertyAssertion() throws OWLOntologyCreationException {
        // given
        BuilderNegativeObjectPropertyAssertion builder = new BuilderNegativeObjectPropertyAssertion(df).withAnnotations(
            annotations).withProperty(op).withValue(i).withSubject(i);
        OWLAxiom expected = df.getOWLNegativeObjectPropertyAssertionAxiom(op, i, i, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildObjectPropertyAssertion() throws OWLOntologyCreationException {
        // given
        BuilderObjectPropertyAssertion builder = new BuilderObjectPropertyAssertion(df).withProperty(op).withSubject(i)
            .withValue(i).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(op, i, i, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildObjectPropertyDomain() throws OWLOntologyCreationException {
        // given
        BuilderObjectPropertyDomain builder = new BuilderObjectPropertyDomain(df).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLObjectPropertyDomainAxiom(op, ce, annotations);
        builder.withDomain(ce).withProperty(op).withAnnotations(annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildObjectPropertyRange() throws OWLOntologyCreationException {
        // given
        BuilderObjectPropertyRange builder = new BuilderObjectPropertyRange(df).withProperty(op).withRange(ce)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLObjectPropertyRangeAxiom(op, ce, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildPropertyChain() throws OWLOntologyCreationException {
        // given
        List<OWLObjectProperty> chain = new ArrayList<>(ops);
        BuilderPropertyChain builder = new BuilderPropertyChain(df).withProperty(op).withAnnotations(annotations);
        for (OWLObjectPropertyExpression p : chain) {
            builder.withPropertyInChain(p);
        }
        OWLAxiom expected = df.getOWLSubPropertyChainOfAxiom(chain, op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildReflexiveObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderReflexiveObjectProperty builder = new BuilderReflexiveObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLReflexiveObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSameIndividual() throws OWLOntologyCreationException {
        // given
        BuilderSameIndividual builder = new BuilderSameIndividual(df).withItems(inds).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLSameIndividualAxiom(inds, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSubAnnotationPropertyOf() throws OWLOntologyCreationException {
        // given
        BuilderSubAnnotationPropertyOf builder = new BuilderSubAnnotationPropertyOf(df).withSub(ap).withSup(df
            .getRDFSLabel()).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLSubAnnotationPropertyOfAxiom(ap, df.getRDFSLabel(), annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSubClass() throws OWLOntologyCreationException {
        // given
        BuilderSubClass builder = new BuilderSubClass(df).withAnnotations(annotations).withSub(ce).withSup(df
            .getOWLThing());
        OWLAxiom expected = df.getOWLSubClassOfAxiom(ce, df.getOWLThing(), annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSubDataProperty() throws OWLOntologyCreationException {
        // given
        BuilderSubDataProperty builder = new BuilderSubDataProperty(df).withSub(dp).withSup(df.getOWLTopDataProperty());
        OWLAxiom expected = df.getOWLSubDataPropertyOfAxiom(dp, df.getOWLTopDataProperty());
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSubObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderSubObjectProperty builder = new BuilderSubObjectProperty(df).withSub(op).withSup(df
            .getOWLTopObjectProperty()).withAnnotations(annotations);
        OWLAxiom expected = df.getOWLSubObjectPropertyOfAxiom(op, df.getOWLTopObjectProperty(), annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSWRLRule() throws OWLOntologyCreationException {
        // given
        BuilderSWRLRule builder = new BuilderSWRLRule(df).withBody(v1).withHead(v2);
        OWLAxiom expected = df.getSWRLRule(body, head);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildSymmetricObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderSymmetricObjectProperty builder = new BuilderSymmetricObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLSymmetricObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldBuildTransitiveObjectProperty() throws OWLOntologyCreationException {
        // given
        BuilderTransitiveObjectProperty builder = new BuilderTransitiveObjectProperty(df).withProperty(op)
            .withAnnotations(annotations);
        OWLAxiom expected = df.getOWLTransitiveObjectPropertyAxiom(op, annotations);
        OWLOntology o = m.createOntology();
        // when
        builder.applyChanges(o);
        // then
        assertTrue(o.containsAxiom(expected));
    }
}
