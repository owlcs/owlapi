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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.equalStreams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class RenameEntityTestCase extends TestBase {

    static final OWLLiteral X = Literal("X");
    static final OWLClass CA = Class(iri("ClsA"));
    static final OWLClass CB = Class(iri("ClsB"));
    static final OWLClass CC = Class(iri("ClsC"));
    static final OWLClass CA1 = Class(iri("ClsA1"));
    static final OWLClass CA2 = Class(iri("ClsA2"));
    static final OWLObjectProperty PA = ObjectProperty(iri("propA"));
    static final OWLDataProperty DPA = DataProperty(iri("propA"));
    static final OWLDataProperty DPA2 = DataProperty(iri("propA2"));
    static final OWLNamedIndividual A = NamedIndividual(iri("indA"));
    static final OWLNamedIndividual B = NamedIndividual(iri("indB"));
    static final OWLDataProperty DPB = DataProperty(iri("propB"));
    static final OWLObjectProperty PA2 = ObjectProperty(iri("propA2"));
    static final OWLAnnotationProperty AP = AnnotationProperty(iri("annoProp"));
    static final OWLObjectProperty PB = ObjectProperty(iri("propB"));
    static final OWLObjectPropertyExpression PBI = PB.getInverseProperty();

    static final OWLDatatype DTA = Datatype(iri("DtA"));
    static final OWLDatatype DTB = Datatype(iri("DtB"));
    static final OWLDatatype DTC = Datatype(iri("DtC"));

    static final OWLAnnotationProperty AP2 = AnnotationProperty(iri("annoProp2"));
    static final OWLAnnotationProperty APR = AnnotationProperty(iri("annoPropR"));

    @Test
    void testRenameClass() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubClassOf(CA1, CB));
        axioms1.add(EquivalentClasses(CA1, CC));
        axioms1.add(DisjointClasses(CA1, CC));
        axioms1.add(ObjectPropertyDomain(PA, CA1));
        axioms1.add(ObjectPropertyRange(PA, CA1));
        axioms1.add(DataPropertyDomain(DPA, CA1));
        axioms1.add(ClassAssertion(CA1, A));
        axioms1.add(AnnotationAssertion(AP, CA1.getIRI(), X));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubClassOf(CA2, CB));
        axioms2.add(EquivalentClasses(CA2, CC));
        axioms2.add(DisjointClasses(CA2, CC));
        axioms2.add(ObjectPropertyDomain(PA, CA2));
        axioms2.add(ObjectPropertyRange(PA, CA2));
        axioms2.add(DataPropertyDomain(DPA, CA2));
        axioms2.add(ClassAssertion(CA2, A));
        axioms2.add(AnnotationAssertion(AP, CA2.getIRI(), X));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(CA1, CA2.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(CA2.getIRI(), CA1.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameObjectProperty() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubObjectPropertyOf(PA, PBI));
        axioms1.add(EquivalentObjectProperties(PA, PBI));
        axioms1.add(DisjointObjectProperties(PA, PBI));
        axioms1.add(ObjectPropertyDomain(PA, CA));
        axioms1.add(ObjectPropertyRange(PA, CA));
        axioms1.add(FunctionalObjectProperty(PA));
        axioms1.add(InverseFunctionalObjectProperty(PA));
        axioms1.add(SymmetricObjectProperty(PA));
        axioms1.add(AsymmetricObjectProperty(PA));
        axioms1.add(TransitiveObjectProperty(PA));
        axioms1.add(ReflexiveObjectProperty(PA));
        axioms1.add(IrreflexiveObjectProperty(PA));
        axioms1.add(ObjectPropertyAssertion(PA, A, B));
        axioms1.add(NegativeObjectPropertyAssertion(PA, A, B));
        axioms1.add(AnnotationAssertion(AP, PA.getIRI(), X));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubObjectPropertyOf(PA2, PBI));
        axioms2.add(EquivalentObjectProperties(PA2, PBI));
        axioms2.add(DisjointObjectProperties(PA2, PBI));
        axioms2.add(ObjectPropertyDomain(PA2, CA));
        axioms2.add(ObjectPropertyRange(PA2, CA));
        axioms2.add(FunctionalObjectProperty(PA2));
        axioms2.add(InverseFunctionalObjectProperty(PA2));
        axioms2.add(SymmetricObjectProperty(PA2));
        axioms2.add(AsymmetricObjectProperty(PA2));
        axioms2.add(TransitiveObjectProperty(PA2));
        axioms2.add(ReflexiveObjectProperty(PA2));
        axioms2.add(IrreflexiveObjectProperty(PA2));
        axioms2.add(ObjectPropertyAssertion(PA2, A, B));
        axioms2.add(NegativeObjectPropertyAssertion(PA2, A, B));
        axioms2.add(AnnotationAssertion(AP, PA2.getIRI(), X));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(PA, PA2.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(PA2.getIRI(), PA.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameDataProperty() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubDataPropertyOf(DPA, DPB));
        axioms1.add(EquivalentDataProperties(DPA, DPB));
        axioms1.add(DisjointDataProperties(DPA, DPB));
        axioms1.add(DataPropertyDomain(DPA, CA));
        axioms1.add(DataPropertyRange(DPA, TopDatatype()));
        axioms1.add(FunctionalDataProperty(DPA));
        axioms1.add(dpa(A));
        axioms1.add(ndpa(A));
        axioms1.add(AnnotationAssertion(AP, DPA.getIRI(), X));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubDataPropertyOf(DPA2, DPB));
        axioms2.add(EquivalentDataProperties(DPA2, DPB));
        axioms2.add(DisjointDataProperties(DPA2, DPB));
        axioms2.add(DataPropertyDomain(DPA2, CA));
        axioms2.add(DataPropertyRange(DPA2, TopDatatype()));
        axioms2.add(FunctionalDataProperty(DPA2));
        axioms2.add(DataPropertyAssertion(DPA2, A, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(DPA2, A, Literal(44)));
        axioms2.add(AnnotationAssertion(AP, DPA2.getIRI(), X));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(DPA, DPA2.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(DPA2.getIRI(), DPA.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameIndividual() {
        OWLOntology ont = getOWLOntology();
        OWLAnnotationAssertionAxiom aX = AnnotationAssertion(AP, DPA.getIRI(), X);
        ont.add(set(ca(A), dpa(A), ndpa(A), aX, opa(A, B), nopa(A, B)));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        ont.applyChanges(entityRenamer.changeIRI(B, A.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()),
            new HashSet<>(set(ca(A), dpa(A), ndpa(A), aX, opa(A, A), nopa(A, A))));
        ont.applyChanges(entityRenamer.changeIRI(A, B.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()),
            new HashSet<>(set(ca(B), dpa(B), ndpa(B), aX, opa(B, B), nopa(B, B))));
    }

    protected OWLNegativeObjectPropertyAssertionAxiom nopa(OWLIndividual a, OWLIndividual b) {
        return NegativeObjectPropertyAssertion(PB, a, b);
    }

    protected OWLObjectPropertyAssertionAxiom opa(OWLIndividual a, OWLIndividual b) {
        return ObjectPropertyAssertion(PB, a, b);
    }

    protected OWLNegativeDataPropertyAssertionAxiom ndpa(OWLIndividual a) {
        return NegativeDataPropertyAssertion(DPA, a, Literal(44));
    }

    protected OWLDataPropertyAssertionAxiom dpa(OWLIndividual a) {
        return DataPropertyAssertion(DPA, a, Literal(33));
    }

    protected OWLClassAssertionAxiom ca(OWLIndividual a) {
        return ClassAssertion(CA, a);
    }

    @Test
    void testRenameDatatype() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new TreeSet<>();
        axioms1.add(DataPropertyRange(DPA, DataIntersectionOf(DTA, DTB)));
        axioms1.add(DataPropertyRange(DPA, DataUnionOf(DTA, DTB)));
        axioms1.add(DataPropertyRange(DPA, DataComplementOf(DTA)));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(DataPropertyRange(DPA, DataIntersectionOf(DTC, DTB)));
        axioms2.add(DataPropertyRange(DPA, DataUnionOf(DTC, DTB)));
        axioms2.add(DataPropertyRange(DPA, DataComplementOf(DTC)));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(DTA, DTC.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(DTC.getIRI(), DTA.getIRI());
        ont.applyChanges(changes2);
        assertTrue(equalStreams(ont.axioms().sorted(), axioms1.stream()));
    }

    @Test
    void testRenameAnnotationProperty() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(Declaration(AP));
        axioms1.add(AnnotationAssertion(AP, A.getIRI(), B.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(AP, AP2));
        axioms1.add(AnnotationPropertyRange(AP, A.getIRI()));
        axioms1.add(AnnotationPropertyDomain(AP, A.getIRI()));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(Declaration(APR));
        axioms2.add(AnnotationAssertion(APR, A.getIRI(), B.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(APR, AP2));
        axioms2.add(AnnotationPropertyRange(APR, A.getIRI()));
        axioms2.add(AnnotationPropertyDomain(APR, A.getIRI()));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(AP, APR.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(APR.getIRI(), AP.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void shouldRenameAnnotationPropertyUsages() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.renameApUsages, new RDFXMLDocumentFormat());
        OWLEntityRenamer renamer =
            new OWLEntityRenamer(o1.getOWLOntologyManager(), Arrays.asList(o1));
        o1.annotationPropertiesInSignature()
            .map(e -> renamer.changeIRI(e.getIRI(), iri("urn:test:", "attempt")))
            .forEach(list -> assertFalse(list.isEmpty()));
    }
}
