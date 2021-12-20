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
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.equalStreams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
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

    static final OWLLiteral X_LITERAL = Literal("X");
    static final OWLObjectPropertyExpression PBI = OBJPROPS.Q.getInverseProperty();

    @Test
    void testRenameClass() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubClassOf(CLASSES.D, CLASSES.B));
        axioms1.add(EquivalentClasses(CLASSES.D, CLASSES.C));
        axioms1.add(DisjointClasses(CLASSES.D, CLASSES.C));
        axioms1.add(ObjectPropertyDomain(OBJPROPS.P, CLASSES.D));
        axioms1.add(ObjectPropertyRange(OBJPROPS.P, CLASSES.D));
        axioms1.add(DataPropertyDomain(DATAPROPS.DP, CLASSES.D));
        axioms1.add(ClassAssertion(CLASSES.D, INDIVIDUALS.indA));
        axioms1.add(AnnotationAssertion(ANNPROPS.AP, CLASSES.D.getIRI(), X_LITERAL));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubClassOf(CLASSES.E, CLASSES.B));
        axioms2.add(EquivalentClasses(CLASSES.E, CLASSES.C));
        axioms2.add(DisjointClasses(CLASSES.E, CLASSES.C));
        axioms2.add(ObjectPropertyDomain(OBJPROPS.P, CLASSES.E));
        axioms2.add(ObjectPropertyRange(OBJPROPS.P, CLASSES.E));
        axioms2.add(DataPropertyDomain(DATAPROPS.DP, CLASSES.E));
        axioms2.add(ClassAssertion(CLASSES.E, INDIVIDUALS.indA));
        axioms2.add(AnnotationAssertion(ANNPROPS.AP, CLASSES.E.getIRI(), X_LITERAL));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(CLASSES.D, CLASSES.E.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(CLASSES.E.getIRI(), CLASSES.D.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    void testRenameObjectProperty() {
        Set<OWLAxiom> axioms1 = axioms(OBJPROPS.P);
        Set<OWLAxiom> axioms2 = axioms(OBJPROPS.R);
        OWLOntology ont = createAnon();
        ont.add(axioms1);
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), set(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(OBJPROPS.P, OBJPROPS.R.getIRI());
        ont.applyChanges(changes);
        assertEquals(str(ont.axioms()), str(axioms2));
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(OBJPROPS.R.getIRI(), OBJPROPS.P.getIRI());
        ont.applyChanges(changes2);
        assertEquals(str(ont.axioms()), str(axioms1));
    }

    protected Set<OWLAxiom> axioms(OWLObjectProperty temp) {
        return set(SubObjectPropertyOf(temp, PBI), EquivalentObjectProperties(temp, PBI),
            DisjointObjectProperties(temp, PBI), ObjectPropertyDomain(temp, CLASSES.A),
            ObjectPropertyRange(temp, CLASSES.A), FunctionalObjectProperty(temp),
            InverseFunctionalObjectProperty(temp), SymmetricObjectProperty(temp),
            AsymmetricObjectProperty(temp), TransitiveObjectProperty(temp),
            ReflexiveObjectProperty(temp), IrreflexiveObjectProperty(temp),
            ObjectPropertyAssertion(temp, INDIVIDUALS.indA, INDIVIDUALS.indB),
            NegativeObjectPropertyAssertion(temp, INDIVIDUALS.indA, INDIVIDUALS.indB),
            AnnotationAssertion(ANNPROPS.areaTotal, temp.getIRI(), X_LITERAL));
    }

    @Test
    void testRenameDataProperty() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = dataAxioms(DATAPROPS.DP);
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = dataAxioms(DATAPROPS.DPP);
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), set(ont));
        List<OWLOntologyChange> changes =
            entityRenamer.changeIRI(DATAPROPS.DP, DATAPROPS.DPP.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(DATAPROPS.DPP.getIRI(), DATAPROPS.DP.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    protected Set<OWLAxiom> dataAxioms(OWLDataProperty dpa3) {
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubDataPropertyOf(dpa3, DATAPROPS.dp1));
        axioms1.add(EquivalentDataProperties(dpa3, DATAPROPS.dp1));
        axioms1.add(DisjointDataProperties(dpa3, DATAPROPS.dp1));
        axioms1.add(DataPropertyDomain(dpa3, CLASSES.C));
        axioms1.add(DataPropertyRange(dpa3, TopDatatype()));
        axioms1.add(FunctionalDataProperty(dpa3));
        axioms1.add(DataPropertyAssertion(dpa3, INDIVIDUALS.indA, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(dpa3, INDIVIDUALS.indA, Literal(44)));
        axioms1.add(AnnotationAssertion(ANNPROPS.areaTotal, dpa3.getIRI(), X_LITERAL));
        return axioms1;
    }

    @Test
    void testRenameIndividual() {
        OWLOntology ont = createAnon();
        OWLAnnotationAssertionAxiom aX =
            AnnotationAssertion(ANNPROPS.AP, DATAPROPS.DP.getIRI(), X_LITERAL);
        ont.add(l(ca(INDIVIDUALS.indA), dpa(INDIVIDUALS.indA), ndpa(INDIVIDUALS.indA), aX,
            opa(INDIVIDUALS.indA, INDIVIDUALS.indB), nopa(INDIVIDUALS.indA, INDIVIDUALS.indB)));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        ont.applyChanges(entityRenamer.changeIRI(INDIVIDUALS.indB, INDIVIDUALS.indA.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()),
            new HashSet<>(set(ca(INDIVIDUALS.indA), dpa(INDIVIDUALS.indA), ndpa(INDIVIDUALS.indA),
                aX, opa(INDIVIDUALS.indA, INDIVIDUALS.indA),
                nopa(INDIVIDUALS.indA, INDIVIDUALS.indA))));
        ont.applyChanges(entityRenamer.changeIRI(INDIVIDUALS.indA, INDIVIDUALS.indB.getIRI()));
        assertEquals(asUnorderedSet(ont.axioms()),
            new HashSet<>(set(ca(INDIVIDUALS.indB), dpa(INDIVIDUALS.indB), ndpa(INDIVIDUALS.indB),
                aX, opa(INDIVIDUALS.indB, INDIVIDUALS.indB),
                nopa(INDIVIDUALS.indB, INDIVIDUALS.indB))));
    }

    protected OWLNegativeObjectPropertyAssertionAxiom nopa(OWLIndividual a, OWLIndividual b) {
        return NegativeObjectPropertyAssertion(OBJPROPS.Q, a, b);
    }

    protected OWLObjectPropertyAssertionAxiom opa(OWLIndividual a, OWLIndividual b) {
        return ObjectPropertyAssertion(OBJPROPS.Q, a, b);
    }

    protected OWLNegativeDataPropertyAssertionAxiom ndpa(OWLIndividual a) {
        return NegativeDataPropertyAssertion(DATAPROPS.DP, a, Literal(44));
    }

    protected OWLDataPropertyAssertionAxiom dpa(OWLIndividual a) {
        return DataPropertyAssertion(DATAPROPS.DP, a, Literal(33));
    }

    protected OWLClassAssertionAxiom ca(OWLIndividual a) {
        return ClassAssertion(CLASSES.A, a);
    }

    @Test
    void testRenameDatatype() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new TreeSet<>();
        axioms1
            .add(DataPropertyRange(DATAPROPS.DP, DataIntersectionOf(DATATYPES.DTA, DATATYPES.DTB)));
        axioms1.add(DataPropertyRange(DATAPROPS.DP, DataUnionOf(DATATYPES.DTA, DATATYPES.DTB)));
        axioms1.add(DataPropertyRange(DATAPROPS.DP, DataComplementOf(DATATYPES.DTA)));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2
            .add(DataPropertyRange(DATAPROPS.DP, DataIntersectionOf(DATATYPES.DTC, DATATYPES.DTB)));
        axioms2.add(DataPropertyRange(DATAPROPS.DP, DataUnionOf(DATATYPES.DTC, DATATYPES.DTB)));
        axioms2.add(DataPropertyRange(DATAPROPS.DP, DataComplementOf(DATATYPES.DTC)));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes =
            entityRenamer.changeIRI(DATATYPES.DTA, DATATYPES.DTC.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(DATATYPES.DTC.getIRI(), DATATYPES.DTA.getIRI());
        ont.applyChanges(changes2);
        assertTrue(equalStreams(ont.axioms().sorted(), axioms1.stream()));
    }

    @Test
    void testRenameAnnotationProperty() {
        OWLOntology ont = createAnon();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(Declaration(ANNPROPS.AP));
        axioms1.add(
            AnnotationAssertion(ANNPROPS.AP, INDIVIDUALS.indA.getIRI(), INDIVIDUALS.indB.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(ANNPROPS.AP, ANNPROPS.propP));
        axioms1.add(AnnotationPropertyRange(ANNPROPS.AP, INDIVIDUALS.indA.getIRI()));
        axioms1.add(AnnotationPropertyDomain(ANNPROPS.AP, INDIVIDUALS.indA.getIRI()));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(Declaration(ANNPROPS.propQ));
        axioms2.add(AnnotationAssertion(ANNPROPS.propQ, INDIVIDUALS.indA.getIRI(),
            INDIVIDUALS.indB.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(ANNPROPS.propQ, ANNPROPS.propP));
        axioms2.add(AnnotationPropertyRange(ANNPROPS.propQ, INDIVIDUALS.indA.getIRI()));
        axioms2.add(AnnotationPropertyDomain(ANNPROPS.propQ, INDIVIDUALS.indA.getIRI()));
        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(ont.getOWLOntologyManager(), l(ont));
        List<OWLOntologyChange> changes =
            entityRenamer.changeIRI(ANNPROPS.AP, ANNPROPS.propQ.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(ANNPROPS.propQ.getIRI(), ANNPROPS.AP.getIRI());
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
