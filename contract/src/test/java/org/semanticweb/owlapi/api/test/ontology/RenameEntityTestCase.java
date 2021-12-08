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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.equalStreams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
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

    static final OWLLiteral X_LITERAL = Literal("X");
    static final OWLObjectPropertyExpression PBI = Q.getInverseProperty();

    @Test
    void testRenameClass() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubClassOf(D, B));
        axioms1.add(EquivalentClasses(D, C));
        axioms1.add(DisjointClasses(D, C));
        axioms1.add(ObjectPropertyDomain(P, D));
        axioms1.add(ObjectPropertyRange(P, D));
        axioms1.add(DataPropertyDomain(DP, D));
        axioms1.add(ClassAssertion(D, indA));
        axioms1.add(AnnotationAssertion(AP, D.getIRI(), X_LITERAL));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubClassOf(E, B));
        axioms2.add(EquivalentClasses(E, C));
        axioms2.add(DisjointClasses(E, C));
        axioms2.add(ObjectPropertyDomain(P, E));
        axioms2.add(ObjectPropertyRange(P, E));
        axioms2.add(DataPropertyDomain(DP, E));
        axioms2.add(ClassAssertion(E, indA));
        axioms2.add(AnnotationAssertion(AP, E.getIRI(), X_LITERAL));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(D, E.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(E.getIRI(), D.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameObjectProperty() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubObjectPropertyOf(P, PBI));
        axioms1.add(EquivalentObjectProperties(P, PBI));
        axioms1.add(DisjointObjectProperties(P, PBI));
        axioms1.add(ObjectPropertyDomain(P, A));
        axioms1.add(ObjectPropertyRange(P, A));
        axioms1.add(FunctionalObjectProperty(P));
        axioms1.add(InverseFunctionalObjectProperty(P));
        axioms1.add(SymmetricObjectProperty(P));
        axioms1.add(AsymmetricObjectProperty(P));
        axioms1.add(TransitiveObjectProperty(P));
        axioms1.add(ReflexiveObjectProperty(P));
        axioms1.add(IrreflexiveObjectProperty(P));
        axioms1.add(ObjectPropertyAssertion(P, indA, indB));
        axioms1.add(NegativeObjectPropertyAssertion(P, indA, indB));
        axioms1.add(AnnotationAssertion(AP, P.getIRI(), X_LITERAL));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubObjectPropertyOf(R, PBI));
        axioms2.add(EquivalentObjectProperties(R, PBI));
        axioms2.add(DisjointObjectProperties(R, PBI));
        axioms2.add(ObjectPropertyDomain(R, A));
        axioms2.add(ObjectPropertyRange(R, A));
        axioms2.add(FunctionalObjectProperty(R));
        axioms2.add(InverseFunctionalObjectProperty(R));
        axioms2.add(SymmetricObjectProperty(R));
        axioms2.add(AsymmetricObjectProperty(R));
        axioms2.add(TransitiveObjectProperty(R));
        axioms2.add(ReflexiveObjectProperty(R));
        axioms2.add(IrreflexiveObjectProperty(R));
        axioms2.add(ObjectPropertyAssertion(R, indA, indB));
        axioms2.add(NegativeObjectPropertyAssertion(R, indA, indB));
        axioms2.add(AnnotationAssertion(AP, R.getIRI(), X_LITERAL));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(P, R.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(R.getIRI(), P.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameDataProperty() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubDataPropertyOf(DP, DQ));
        axioms1.add(EquivalentDataProperties(DP, DQ));
        axioms1.add(DisjointDataProperties(DP, DQ));
        axioms1.add(DataPropertyDomain(DP, A));
        axioms1.add(DataPropertyRange(DP, TopDatatype()));
        axioms1.add(FunctionalDataProperty(DP));
        axioms1.add(dpa(indA));
        axioms1.add(ndpa(indA));
        axioms1.add(AnnotationAssertion(AP, DP.getIRI(), X_LITERAL));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubDataPropertyOf(DR, DQ));
        axioms2.add(EquivalentDataProperties(DR, DQ));
        axioms2.add(DisjointDataProperties(DR, DQ));
        axioms2.add(DataPropertyDomain(DR, A));
        axioms2.add(DataPropertyRange(DR, TopDatatype()));
        axioms2.add(FunctionalDataProperty(DR));
        axioms2.add(DataPropertyAssertion(DR, indA, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(DR, indA, Literal(44)));
        axioms2.add(AnnotationAssertion(AP, DR.getIRI(), X_LITERAL));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(DP, DR.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(DR.getIRI(), DP.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameIndividual() {
        OWLOntology ont = createAnon();
        OWLAnnotationAssertionAxiom aX = AnnotationAssertion(AP, DP.getIRI(), X_LITERAL);
        ont.add(l(ca(indA), dpa(indA), ndpa(indA), aX, opa(indA, indB), nopa(indA, indB)));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        ont.applyChanges(entityRenamer.changeIRI(indB, indA.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()), new HashSet<>(
            l(ca(indA), dpa(indA), ndpa(indA), aX, opa(indA, indA), nopa(indA, indA))));
        ont.applyChanges(entityRenamer.changeIRI(indA.getIRI(), indB.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()), new HashSet<>(
            l(ca(indB), dpa(indB), ndpa(indB), aX, opa(indB, indB), nopa(indB, indB))));
    }

    protected OWLNegativeObjectPropertyAssertionAxiom nopa(OWLIndividual a, OWLIndividual b) {
        return NegativeObjectPropertyAssertion(Q, a, b);
    }

    protected OWLObjectPropertyAssertionAxiom opa(OWLIndividual a, OWLIndividual b) {
        return ObjectPropertyAssertion(Q, a, b);
    }

    protected OWLNegativeDataPropertyAssertionAxiom ndpa(OWLIndividual a) {
        return NegativeDataPropertyAssertion(DP, a, Literal(44));
    }

    protected OWLDataPropertyAssertionAxiom dpa(OWLIndividual a) {
        return DataPropertyAssertion(DP, a, Literal(33));
    }

    protected OWLClassAssertionAxiom ca(OWLIndividual a) {
        return ClassAssertion(A, a);
    }

    @Test
    void testRenameDatatype() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new TreeSet<>();
        axioms1.add(DataPropertyRange(DP, DataIntersectionOf(DTA, DTB)));
        axioms1.add(DataPropertyRange(DP, DataUnionOf(DTA, DTB)));
        axioms1.add(DataPropertyRange(DP, DataComplementOf(DTA)));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(DataPropertyRange(DP, DataIntersectionOf(DTC, DTB)));
        axioms2.add(DataPropertyRange(DP, DataUnionOf(DTC, DTB)));
        axioms2.add(DataPropertyRange(DP, DataComplementOf(DTC)));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(DTA, DTC.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(DTC.getIRI(), DTA.getIRI());
        ont.applyChanges(changes2);
        assertTrue(equalStreams(ont.axioms().sorted(), axioms1.stream()));
    }

    @Test
    void testRenameAnnotationProperty() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(Declaration(AP));
        axioms1.add(AnnotationAssertion(AP, indA.getIRI(), indB.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(AP, propP));
        axioms1.add(AnnotationPropertyRange(AP, indA.getIRI()));
        axioms1.add(AnnotationPropertyDomain(AP, indA.getIRI()));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(Declaration(propQ));
        axioms2.add(AnnotationAssertion(propQ, indA.getIRI(), indB.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(propQ, propP));
        axioms2.add(AnnotationPropertyRange(propQ, indA.getIRI()));
        axioms2.add(AnnotationPropertyDomain(propQ, indA.getIRI()));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(AP, propQ.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(propQ.getIRI(), AP.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void shouldRenameAnnotationPropertyUsages() {
        OWLOntology o1 = loadFrom(TestFiles.renameApUsages, new RDFXMLDocumentFormat());
        OWLEntityRenamer renamer = new OWLEntityRenamer(o1.getOWLOntologyManager(), l(o1));
        o1.annotationPropertiesInSignature()
            .map(p -> renamer.changeIRI(p.getIRI(), iri("urn:test:", "attempt")))
            .forEach(list -> assertFalse(list.isEmpty()));
    }
}
