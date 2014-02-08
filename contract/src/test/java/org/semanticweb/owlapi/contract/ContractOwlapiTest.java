/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2014, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.change.AbstractCompositeOntologyChange;
import org.semanticweb.owlapi.change.AddClassExpressionClosureAxiom;
import org.semanticweb.owlapi.change.AmalgamateSubClassAxioms;
import org.semanticweb.owlapi.change.CoerceConstantsIntoDataPropertyRange;
import org.semanticweb.owlapi.change.ConvertEquivalentClassesToSuperClasses;
import org.semanticweb.owlapi.change.ConvertPropertyAssertionsToAnnotations;
import org.semanticweb.owlapi.change.ConvertSuperClassesToEquivalentClass;
import org.semanticweb.owlapi.change.CreateValuePartition;
import org.semanticweb.owlapi.change.MakeClassesMutuallyDisjoint;
import org.semanticweb.owlapi.change.MakePrimitiveSubClassesMutuallyDisjoint;
import org.semanticweb.owlapi.change.OWLCompositeOntologyChange;
import org.semanticweb.owlapi.change.RemoveAllDisjointAxioms;
import org.semanticweb.owlapi.change.ShortForm2AnnotationGenerator;
import org.semanticweb.owlapi.change.SplitSubClassAxioms;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.ShortFormProvider;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiTest {
    @Test
    public void shouldTestAbstractCompositeOntologyChange() throws OWLException {
        AbstractCompositeOntologyChange testSubject0 = new AbstractCompositeOntologyChange(
                mock(OWLDataFactory.class)) {};
        List<OWLOntologyChange<?>> result1 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestAddClassExpressionClosureAxiom() throws OWLException {
        AddClassExpressionClosureAxiom testSubject0 = new AddClassExpressionClosureAxiom(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockObjectProperty(), Utils.mockSet(Utils
                        .getMockOntology()), Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestAmalgamateSubClassAxioms() throws OWLException {
        AmalgamateSubClassAxioms testSubject0 = new AmalgamateSubClassAxioms(
                mock(OWLDataFactory.class), Utils.mockSet(Utils
                        .getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestCoerceConstantsIntoDataPropertyRange()
            throws OWLException {
        CoerceConstantsIntoDataPropertyRange testSubject0 = new CoerceConstantsIntoDataPropertyRange(
                mock(OWLDataFactory.class), Utils.mockSet(Utils
                        .getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestConvertEquivalentClassesToSuperClasses()
            throws OWLException {
        ConvertEquivalentClassesToSuperClasses testSubject0 = new ConvertEquivalentClassesToSuperClasses(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockSet(Utils.getMockOntology()),
                Utils.getMockOntology(), false);
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestConvertPropertyAssertionsToAnnotations()
            throws OWLException {
        ConvertPropertyAssertionsToAnnotations testSubject0 = new ConvertPropertyAssertionsToAnnotations(
                Factory.getFactory(), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestConvertSuperClassesToEquivalentClass()
            throws OWLException {
        ConvertSuperClassesToEquivalentClass testSubject0 = new ConvertSuperClassesToEquivalentClass(
                Factory.getFactory(), Factory.getFactory().getOWLClass(
                        IRI.create("urn:test:class")), Utils.mockSet(Utils
                        .getMockOntology()), Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestCreateValuePartition() throws OWLException {
        CreateValuePartition testSubject0 = new CreateValuePartition(
                Factory.getFactory(), mock(OWLClass.class),
                Utils.mockSet(mock(OWLClass.class)),
                mock(OWLObjectProperty.class), Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestMakeClassesMutuallyDisjoint() throws OWLException {
        Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>(
                Arrays.asList(
                        Factory.getFactory().getOWLClass(
                                IRI.create("urn:test:c1")),
                        Factory.getFactory().getOWLClass(
                                IRI.create("urn:test:c2"))));
        MakeClassesMutuallyDisjoint testSubject0 = new MakeClassesMutuallyDisjoint(
                Factory.getFactory(), classes, false, Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestMakePrimitiveSubClassesMutuallyDisjoint()
            throws OWLException {
        MakePrimitiveSubClassesMutuallyDisjoint testSubject0 = new MakePrimitiveSubClassesMutuallyDisjoint(
                Factory.getFactory(), Factory.getFactory().getOWLClass(
                        IRI.create("urn:test:c")), Utils.getMockOntology());
        MakePrimitiveSubClassesMutuallyDisjoint testSubject1 = new MakePrimitiveSubClassesMutuallyDisjoint(
                Factory.getFactory(), Factory.getFactory().getOWLClass(
                        IRI.create("urn:test:c")), Utils.getMockOntology(),
                false);
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestInterfaceOWLCompositeOntologyChange()
            throws OWLException {
        OWLCompositeOntologyChange testSubject0 = mock(OWLCompositeOntologyChange.class);
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestRemoveAllDisjointAxioms() throws OWLException {
        RemoveAllDisjointAxioms testSubject0 = new RemoveAllDisjointAxioms(
                mock(OWLDataFactory.class), Utils.mockSet(Utils
                        .getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    public void shouldTestShortForm2AnnotationGenerator() throws OWLException {
        ShortForm2AnnotationGenerator testSubject0 = new ShortForm2AnnotationGenerator(
                mock(OWLDataFactory.class), Utils.getMockManager(),
                Utils.getMockOntology(), mock(ShortFormProvider.class),
                IRI("urn:aFake"), "");
        ShortForm2AnnotationGenerator testSubject1 = new ShortForm2AnnotationGenerator(
                mock(OWLDataFactory.class), Utils.getMockManager(),
                Utils.getMockOntology(), mock(ShortFormProvider.class),
                IRI("urn:aFake"));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestSplitSubClassAxioms() throws OWLException {
        SplitSubClassAxioms testSubject0 = new SplitSubClassAxioms(
                Utils.mockSet(Utils.getMockOntology()),
                mock(OWLDataFactory.class));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }
}
