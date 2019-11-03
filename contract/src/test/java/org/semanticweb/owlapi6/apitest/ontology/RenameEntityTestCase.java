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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
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

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyChange;
import org.semanticweb.owlapi6.utility.OWLEntityRenamer;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class RenameEntityTestCase extends TestBase {

    private static final OWLClass CC = Class(iri("ClsC"));
    private static final OWLClass CB = Class(iri("ClsB"));
    private static final OWLClass CA2 = Class(iri("ClsA2"));
    private static final OWLClass CA1 = Class(iri("ClsA1"));
    private static final OWLNamedIndividual b = NamedIndividual(iri("indB"));
    private static final OWLAnnotationProperty AP2 = AnnotationProperty(iri("annoProp2"));
    private static final OWLAnnotationProperty APR = AnnotationProperty(iri("annoPropR"));
    private static final OWLDatatype DTC = Datatype(iri("DtC"));
    private static final OWLDatatype DTB = Datatype(iri("DtB"));
    private static final OWLDatatype DTA = Datatype(iri("DtA"));
    private static final OWLNamedIndividual a = NamedIndividual(iri("indA"));
    private static final OWLDataProperty DPB = DataProperty(iri("propB"));
    private static final OWLDataProperty DPA2 = DataProperty(iri("propA2"));
    private static final OWLDataProperty DPA = DataProperty(iri("propA"));
    private static final OWLAnnotationProperty AP = AnnotationProperty(iri("annoProp"));
    private static final OWLObjectProperty PB = ObjectProperty(iri("propB"));
    private static final OWLLiteral x = Literal("X");
    private static final OWLClass CA = Class(iri("ClsA"));
    private static final OWLObjectProperty PA2 = ObjectProperty(iri("propA2"));
    private static final OWLObjectProperty PA = ObjectProperty(iri("propA"));

    @Test
    public void testRenameClass() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubClassOf(CA1, CB));
        axioms1.add(EquivalentClasses(CA1, CC));
        axioms1.add(DisjointClasses(CA1, CC));
        axioms1.add(ObjectPropertyDomain(PA, CA1));
        axioms1.add(ObjectPropertyRange(PA, CA1));
        axioms1.add(DataPropertyDomain(DPA, CA1));
        axioms1.add(ClassAssertion(CA1, a));
        axioms1.add(AnnotationAssertion(AP, CA1.getIRI(), x));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubClassOf(CA2, CB));
        axioms2.add(EquivalentClasses(CA2, CC));
        axioms2.add(DisjointClasses(CA2, CC));
        axioms2.add(ObjectPropertyDomain(PA, CA2));
        axioms2.add(ObjectPropertyRange(PA, CA2));
        axioms2.add(DataPropertyDomain(DPA, CA2));
        axioms2.add(ClassAssertion(CA2, a));
        axioms2.add(AnnotationAssertion(AP, CA2.getIRI(), x));
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
    public void testRenameObjectProperty() {
        OWLOntology ont = getOWLOntology();
        OWLObjectPropertyExpression propB = PB.getInverseProperty();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubObjectPropertyOf(PA, propB));
        axioms1.add(EquivalentObjectProperties(PA, propB));
        axioms1.add(DisjointObjectProperties(PA, propB));
        axioms1.add(ObjectPropertyDomain(PA, CA));
        axioms1.add(ObjectPropertyRange(PA, CA));
        axioms1.add(FunctionalObjectProperty(PA));
        axioms1.add(InverseFunctionalObjectProperty(PA));
        axioms1.add(SymmetricObjectProperty(PA));
        axioms1.add(AsymmetricObjectProperty(PA));
        axioms1.add(TransitiveObjectProperty(PA));
        axioms1.add(ReflexiveObjectProperty(PA));
        axioms1.add(IrreflexiveObjectProperty(PA));
        axioms1.add(ObjectPropertyAssertion(PA, a, b));
        axioms1.add(NegativeObjectPropertyAssertion(PA, a, b));
        axioms1.add(AnnotationAssertion(AP, PA.getIRI(), x));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubObjectPropertyOf(PA2, propB));
        axioms2.add(EquivalentObjectProperties(PA2, propB));
        axioms2.add(DisjointObjectProperties(PA2, propB));
        axioms2.add(ObjectPropertyDomain(PA2, CA));
        axioms2.add(ObjectPropertyRange(PA2, CA));
        axioms2.add(FunctionalObjectProperty(PA2));
        axioms2.add(InverseFunctionalObjectProperty(PA2));
        axioms2.add(SymmetricObjectProperty(PA2));
        axioms2.add(AsymmetricObjectProperty(PA2));
        axioms2.add(TransitiveObjectProperty(PA2));
        axioms2.add(ReflexiveObjectProperty(PA2));
        axioms2.add(IrreflexiveObjectProperty(PA2));
        axioms2.add(ObjectPropertyAssertion(PA2, a, b));
        axioms2.add(NegativeObjectPropertyAssertion(PA2, a, b));
        axioms2.add(AnnotationAssertion(AP, PA2.getIRI(), x));
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
    public void testRenameDataProperty() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubDataPropertyOf(DPA, DPB));
        axioms1.add(EquivalentDataProperties(DPA, DPB));
        axioms1.add(DisjointDataProperties(DPA, DPB));
        axioms1.add(DataPropertyDomain(DPA, CA));
        axioms1.add(DataPropertyRange(DPA, TopDatatype()));
        axioms1.add(FunctionalDataProperty(DPA));
        axioms1.add(DataPropertyAssertion(DPA, a, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(DPA, a, Literal(44)));
        axioms1.add(AnnotationAssertion(AP, DPA.getIRI(), x));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubDataPropertyOf(DPA2, DPB));
        axioms2.add(EquivalentDataProperties(DPA2, DPB));
        axioms2.add(DisjointDataProperties(DPA2, DPB));
        axioms2.add(DataPropertyDomain(DPA2, CA));
        axioms2.add(DataPropertyRange(DPA2, TopDatatype()));
        axioms2.add(FunctionalDataProperty(DPA2));
        axioms2.add(DataPropertyAssertion(DPA2, a, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(DPA2, a, Literal(44)));
        axioms2.add(AnnotationAssertion(AP, DPA2.getIRI(), x));
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
    public void testRenameIndividual() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>(
            Arrays.asList(ClassAssertion(CA, a), DataPropertyAssertion(DPA, a, Literal(33)),
                NegativeDataPropertyAssertion(DPA, a, Literal(44)),
                AnnotationAssertion(AP, DPA.getIRI(), x), ObjectPropertyAssertion(PB, a, a),
                NegativeObjectPropertyAssertion(PB, a, a)));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>(
            Arrays.asList(ClassAssertion(CA, a), DataPropertyAssertion(DPA, a, Literal(33)),
                NegativeDataPropertyAssertion(DPA, a, Literal(44)),
                AnnotationAssertion(AP, DPA.getIRI(), x), ObjectPropertyAssertion(PB, a, a),
                NegativeObjectPropertyAssertion(PB, a, a)));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(a, a.getIRI());
        ont.applyChanges(changes);
        assertEquals(asUnorderedSet(ont.axioms()), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(a.getIRI(), a.getIRI());
        ont.applyChanges(changes2);
        assertEquals(asUnorderedSet(ont.axioms()), axioms1);
    }

    @Test
    public void testRenameDatatype() {
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
    public void testRenameAnnotationProperty() {
        OWLOntology ont = getOWLOntology();
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(Declaration(AP));
        axioms1.add(AnnotationAssertion(AP, a.getIRI(), b.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(AP, AP2));
        axioms1.add(AnnotationPropertyRange(AP, a.getIRI()));
        axioms1.add(AnnotationPropertyDomain(AP, a.getIRI()));
        ont.add(axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(Declaration(APR));
        axioms2.add(AnnotationAssertion(APR, a.getIRI(), b.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(APR, AP2));
        axioms2.add(AnnotationPropertyRange(APR, a.getIRI()));
        axioms2.add(AnnotationPropertyDomain(APR, a.getIRI()));
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
    public void shouldRenameAnnotationPropertyUsages() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.renameApUsages, new RDFXMLDocumentFormat());
        OWLEntityRenamer renamer =
            new OWLEntityRenamer(o1.getOWLOntologyManager(), Arrays.asList(o1));
        o1.annotationPropertiesInSignature()
            .map(e -> renamer.changeIRI(e.getIRI(), df.getIRI("urn:test:attempt")))
            .forEach(list -> assertFalse(list.isEmpty()));
    }
}
