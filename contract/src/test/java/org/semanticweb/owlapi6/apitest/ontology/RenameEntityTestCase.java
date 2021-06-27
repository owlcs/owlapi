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
package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.equalStreams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyChange;
import org.semanticweb.owlapi6.utility.OWLEntityRenamer;

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
    static final OWLDataProperty DPB = DataProperty(iri("propB"));
    static final OWLObjectProperty PA2 = ObjectProperty(iri("propA2"));
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
        axioms1.add(ClassAssertion(CA1, indA));
        axioms1.add(AnnotationAssertion(AP, CA1.getIRI(), X));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubClassOf(CA2, CB));
        axioms2.add(EquivalentClasses(CA2, CC));
        axioms2.add(DisjointClasses(CA2, CC));
        axioms2.add(ObjectPropertyDomain(PA, CA2));
        axioms2.add(ObjectPropertyRange(PA, CA2));
        axioms2.add(DataPropertyDomain(DPA, CA2));
        axioms2.add(ClassAssertion(CA2, indA));
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
        Set<OWLAxiom> axioms1 = axioms(PA);
        Set<OWLAxiom> axioms2 = axioms(PA2);
        OWLOntology ont = getOWLOntology();
        ont.add(axioms1);
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(PA, PA2.getIRI());
        ont.applyChanges(changes);
        assertEquals(new TreeSet<>(asUnorderedSet(ont.axioms().map(Object::toString))),
            new TreeSet<>(asUnorderedSet(axioms2.stream().map(Object::toString))));
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(PA2.getIRI(), PA.getIRI());
        ont.applyChanges(changes2);
        assertEquals(new HashSet<>(asUnorderedSet(ont.axioms())), axioms1);
    }

    protected Set<OWLAxiom> axioms(OWLObjectProperty temp) {
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubObjectPropertyOf(temp, PBI));
        axioms1.add(EquivalentObjectProperties(temp, PBI));
        axioms1.add(DisjointObjectProperties(temp, PBI));
        axioms1.add(ObjectPropertyDomain(temp, CA));
        axioms1.add(ObjectPropertyRange(temp, CA));
        axioms1.add(FunctionalObjectProperty(temp));
        axioms1.add(InverseFunctionalObjectProperty(temp));
        axioms1.add(SymmetricObjectProperty(temp));
        axioms1.add(AsymmetricObjectProperty(temp));
        axioms1.add(TransitiveObjectProperty(temp));
        axioms1.add(ReflexiveObjectProperty(temp));
        axioms1.add(IrreflexiveObjectProperty(temp));
        axioms1.add(ObjectPropertyAssertion(temp, indA, indB));
        axioms1.add(NegativeObjectPropertyAssertion(temp, indA, indB));
        axioms1.add(AnnotationAssertion(areaTotal, temp.getIRI(), X));
        return axioms1;
    }

    @Test
    void testRenameDataProperty() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = dataAxioms(DPA);
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = dataAxioms(DPA2);
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(DPA, DPA2.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(DPA2.getIRI(), DPA.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    protected Set<OWLAxiom> dataAxioms(OWLDataProperty dpa3) {
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubDataPropertyOf(dpa3, DPB));
        axioms1.add(EquivalentDataProperties(dpa3, DPB));
        axioms1.add(DisjointDataProperties(dpa3, DPB));
        axioms1.add(DataPropertyDomain(dpa3, CA));
        axioms1.add(DataPropertyRange(dpa3, TopDatatype()));
        axioms1.add(FunctionalDataProperty(dpa3));
        axioms1.add(DataPropertyAssertion(dpa3, indA, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(dpa3, indA, Literal(44)));
        axioms1.add(AnnotationAssertion(areaTotal, dpa3.getIRI(), X));
        return axioms1;
    }

    @Test
    void testRenameIndividual() {
        OWLOntology ont = getOWLOntology();
        org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom aX =
            AnnotationAssertion(AP, DPA.getIRI(), X);
        ont.add(set(ca(indA), dpa(indA), ndpa(indA), aX, opa(indA, indB), nopa(indA, indB)));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        ont.applyChanges(entityRenamer.changeIRI(indB, indA.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()), new HashSet<>(
            set(ca(indA), dpa(indA), ndpa(indA), aX, opa(indA, indA), nopa(indA, indA))));
        ont.applyChanges(entityRenamer.changeIRI(indA, indB.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()), new HashSet<>(
            set(ca(indB), dpa(indB), ndpa(indB), aX, opa(indB, indB), nopa(indB, indB))));
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
        axioms1.add(AnnotationAssertion(AP, indA.getIRI(), indB.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(AP, AP2));
        axioms1.add(AnnotationPropertyRange(AP, indA.getIRI()));
        axioms1.add(AnnotationPropertyDomain(AP, indA.getIRI()));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(Declaration(APR));
        axioms2.add(AnnotationAssertion(APR, indA.getIRI(), indB.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(APR, AP2));
        axioms2.add(AnnotationPropertyRange(APR, indA.getIRI()));
        axioms2.add(AnnotationPropertyDomain(APR, indA.getIRI()));
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
