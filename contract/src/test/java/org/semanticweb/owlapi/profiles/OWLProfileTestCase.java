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
package org.semanticweb.owlapi.profiles;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Boolean;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataAllValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataExactCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataOneOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DatatypeDefinition;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DatatypeRestriction;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointUnion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Double;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FacetRestriction;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.HasKey;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Integer;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectAllValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectExactCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectHasSelf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectInverseOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectMaxCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectMinCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectOneOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectUnionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubPropertyChainOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapi.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapi.profiles.violations.IllegalPunning;
import org.semanticweb.owlapi.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapi.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapi.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapi.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapi.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapi.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapi.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.UseOfDataOneOfWithMultipleLiterals;
import org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInLiteral;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectOneOfWithMultipleIndividuals;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapi.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUnknownDatatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class OWLProfileTestCase extends TestBase {

    protected static final Comparator<Class<?>> comp =
        (o1, o2) -> o1.getSimpleName().compareTo(o2.getSimpleName());
    private static final String START = OWLThing().getIRI().getNamespace();
    private static final OWLClass CL = Class(IRI("urn:test#", "fakeclass"));
    private static final OWLDataProperty DATAP =
        DataProperty(IRI("urn:datatype#", "fakedatatypeproperty"));
    private static final OWLObjectProperty OP =
        ObjectProperty(IRI("urn:datatype#", "fakeobjectproperty"));
    private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE2 = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LANG_RANGE, Literal(1))));
    private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, Literal(1))));
    private static final OWLDatatype UNKNOWNFAKEDATATYPE =
        Datatype(IRI(START, "unknownfakedatatype"));
    private static final OWLDatatype FAKEUNDECLAREDDATATYPE =
        Datatype(IRI("urn:datatype#", "fakeundeclareddatatype"));
    private static final OWLDatatype FAKEDATATYPE = Datatype(IRI("urn:datatype#", "fakedatatype"));
    private static final IRI onto = df.getIRI("urn:test#", "ontology");
    private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();
    private static final OWLObjectProperty P = ObjectProperty(IRI("urn:test#", "objectproperty"));
    private static final OWLIndividual I = NamedIndividual(IRI("urn:test#", "ind"));
    OWLOntology o;

    // XXX refactor and remove @Tests
    @Before
    public void setupManagersClean() {
        try {
            o = getOWLOntology(onto);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    public void declare(OWLOntology ont, OWLEntity... entities) {
        Stream.of(entities).map(e -> Declaration(e)).forEach(ax -> ont.add(ax));
    }

    public void checkInCollection(List<OWLProfileViolation> violations, Class<?>[] inputList) {
        List<Class<?>> list = new ArrayList<>(Arrays.asList(inputList));
        List<Class<?>> list1 = new ArrayList<>();
        for (OWLProfileViolation v : violations) {
            list1.add(v.getClass());
        }
        Collections.sort(list, comp);
        Collections.sort(list1, comp);
        assertEquals(list1.toString(), list, list1);
    }

    public void runAssert(OWLOntology ontology, OWLProfile profile,
        Class<?>... expectedViolations) {
        List<OWLProfileViolation> violations = profile.checkOntology(ontology).getViolations();
        assertEquals(violations.toString(), expectedViolations.length, violations.size());
        checkInCollection(violations, expectedViolations);
        for (OWLProfileViolation violation : violations) {
            ontology.applyChanges(violation.repair());
            violation.accept(new OWLProfileViolationVisitor() {});
            violation.accept(object -> Optional.ofNullable(object.toString()));
        }
        violations = profile.checkOntology(ontology).getViolations();
        assertEquals(0, violations.size());
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype datatype)")
    public void shouldCreateViolationForOWLDatatypeInOWL2DLProfile() {
        declare(o, UNKNOWNFAKEDATATYPE, FAKEDATATYPE, Class(FAKEDATATYPE.getIRI()), DATAP);
        o.addAxiom(DataPropertyRange(DATAP, FAKEUNDECLAREDDATATYPE));
        runAssert(o, Profiles.OWL2_DL, UseOfUndeclaredDatatype.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfile() {
        declare(o, Integer(), Boolean(), FAKEDATATYPE);
        o.add(DatatypeDefinition(Boolean(), Integer()), DatatypeDefinition(FAKEDATATYPE, Integer()),
            DatatypeDefinition(Integer(), FAKEDATATYPE));
        runAssert(o, Profiles.OWL2_DL, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfileCycles() {
        OWLDatatype d = Datatype(IRI(START, "test"));
        declare(o, d, Integer(), Boolean(), FAKEDATATYPE);
        o.add(DatatypeDefinition(d, Boolean()), DatatypeDefinition(Boolean(), d),
            DatatypeDefinition(FAKEDATATYPE, Integer()),
            DatatypeDefinition(Integer(), FAKEDATATYPE));
        runAssert(o, Profiles.OWL2_DL, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfUnknownDatatype.class,
            UseOfUnknownDatatype.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectProperty property)")
    public void shouldCreateViolationForOWLObjectPropertyInOWL2DLProfile() {
        IRI iri = IRI(START, "test");
        declare(o, ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        o.addAxiom(SubObjectPropertyOf(OP, ObjectProperty(iri)));
        runAssert(o, Profiles.OWL2_DL, UseOfReservedVocabularyForObjectPropertyIRI.class,
            UseOfUndeclaredObjectProperty.class, IllegalPunning.class, IllegalPunning.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile1() {
        declare(o, DataProperty(IRI(START, "fail")));
        runAssert(o, Profiles.OWL2_DL);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile2() {
        o.addAxiom(FunctionalDataProperty(DATAP));
        runAssert(o, Profiles.OWL2_DL, UseOfUndeclaredDataProperty.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile3() {
        declare(o, DATAP, AnnotationProperty(DATAP.getIRI()));
        runAssert(o, Profiles.OWL2_DL);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile4() {
        declare(o, DATAP, ObjectProperty(DATAP.getIRI()));
        runAssert(o, Profiles.OWL2_DL);
    }

    @Test
    @Tests(method = "public Object visit(OWLAnnotationProperty property)")
    public void shouldCreateViolationForOWLAnnotationPropertyInOWL2DLProfile() {
        IRI iri = IRI(START, "test");
        declare(o, ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        o.add(SubAnnotationPropertyOf(AnnotationProperty(IRI("urn:test#", "t")),
            AnnotationProperty(iri)));
        runAssert(o, Profiles.OWL2_DL, UseOfReservedVocabularyForAnnotationPropertyIRI.class,
            UseOfUndeclaredAnnotationProperty.class, IllegalPunning.class, IllegalPunning.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLOntology ontology)")
    public void shouldCreateViolationForOWLOntologyInOWL2DLProfile()
        throws OWLOntologyCreationException {
        o = m.createOntology(df.getOWLOntologyID(IRI(START, "test"), IRI(START, "test1")));
        runAssert(o, Profiles.OWL2_DL, UseOfReservedVocabularyForOntologyIRI.class,
            UseOfReservedVocabularyForVersionIRI.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLClass desc)")
    public void shouldCreateViolationForOWLClassInOWL2DLProfile() {
        declare(o, Class(IRI(START, "test")), FAKEDATATYPE);
        o.add(ClassAssertion(Class(FAKEDATATYPE.getIRI()), AnonymousIndividual()));
        runAssert(o, Profiles.OWL2_DL, UseOfUndeclaredClass.class,
            DatatypeIRIAlsoUsedAsClassIRI.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf()));
        runAssert(o, Profiles.OWL2_DL, EmptyOneOfAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf()));
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataIntersectionOf node)")
    public void shouldCreateViolationForOWLDataIntersectionOfInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataIntersectionOf()));
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectIntersectionOf node)")
    public void shouldCreateViolationForOWLObjectIntersectionOfInOWL2DLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectIntersectionOf()));
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectOneOf node)")
    public void shouldCreateViolationForOWLObjectOneOfInOWL2DLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectOneOf()));
        runAssert(o, Profiles.OWL2_DL, EmptyOneOfAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectUnionOf node)")
    public void shouldCreateViolationForOWLObjectUnionOfInOWL2DLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectUnionOf()));
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentClassesAxiom node)")
    public void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(EquivalentClasses());
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointClassesAxiom node)")
    public void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(DisjointClasses());
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom node)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2DLProfile() {
        declare(o, OP);
        OWLClass otherfakeclass = Class(IRI("urn:test#", "otherfakeclass"));
        declare(o, CL);
        declare(o, otherfakeclass);
        o.add(DisjointUnion(CL, otherfakeclass));
        runAssert(o, Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentObjectPropertiesAxiom node)")
    public void shouldCreateViolationForOWLEquivalentObjectPropertiesAxiomInOWL2DLProfile() {
        o.add(EquivalentObjectProperties(OP));
        runAssert(o, Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfUndeclaredObjectProperty.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointDataPropertiesAxiom node)")
    public void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2DLProfile() {
        o.add(DisjointDataProperties(DATAP));
        runAssert(o, Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfUndeclaredDataProperty.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentDataPropertiesAxiom node)")
    public void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2DLProfile() {
        o.add(EquivalentDataProperties(DATAP));
        runAssert(o, Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfUndeclaredDataProperty.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom node)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2DLProfile() {
        declare(o, CL);
        o.add(HasKey(CL));
        runAssert(o, Profiles.OWL2_DL, InsufficientPropertyExpressions.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSameIndividualAxiom node)")
    public void shouldCreateViolationForOWLSameIndividualAxiomInOWL2DLProfile() {
        o.add(SameIndividual(I));
        runAssert(o, Profiles.OWL2_DL, InsufficientIndividuals.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDifferentIndividualsAxiom node)")
    public void shouldCreateViolationForOWLDifferentIndividualsAxiomInOWL2DLProfile() {
        o.add(DifferentIndividuals(I));
        runAssert(o, Profiles.OWL2_DL, InsufficientIndividuals.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLNamedIndividual individual)")
    public void shouldCreateViolationForOWLNamedIndividualInOWL2DLProfile() {
        o.add(ClassAssertion(OWLThing(), NamedIndividual(IRI(START, "i"))));
        runAssert(o, Profiles.OWL2_DL, UseOfReservedVocabularyForIndividualIRI.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubDataPropertyOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubDataPropertyOfAxiomInOWL2DLProfile() {
        o.add(SubDataPropertyOf(DF.getOWLTopDataProperty(), DF.getOWLTopDataProperty()));
        runAssert(o, Profiles.OWL2_DL, UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMinCardinality desc)")
    public void shouldCreateViolationForOWLObjectMinCardinalityInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(TransitiveObjectProperty(OP),
            SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMaxCardinality desc)")
    public void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(TransitiveObjectProperty(OP),
            SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectExactCardinality desc)")
    public void shouldCreateViolationForOWLObjectExactCardinalityInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(TransitiveObjectProperty(OP),
            SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectHasSelf desc)")
    public void shouldCreateViolationForOWLObjectHasSelfInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), ObjectPropertyRange(OP, ObjectHasSelf(OP)));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInObjectHasSelf.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), FunctionalObjectProperty(OP));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInFunctionalPropertyAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), InverseFunctionalObjectProperty(OP));
        runAssert(o, Profiles.OWL2_DL,
            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), IrreflexiveObjectProperty(OP));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInIrreflexivePropertyAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLAsymmetricObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), AsymmetricObjectProperty(OP));
        runAssert(o, Profiles.OWL2_DL, UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointObjectPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), DisjointObjectProperties(OP));
        runAssert(o, Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfNonSimplePropertyInDisjointPropertiesAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubPropertyChainOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2DLProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#", "op"));
        declare(o, OP, op1);
        o.add(SubPropertyChainOf(Arrays.asList(op1), OP),
            SubPropertyChainOf(Arrays.asList(OP, op1, OP), OP),
            SubPropertyChainOf(Arrays.asList(OP, op1), OP),
            SubPropertyChainOf(Arrays.asList(op1, OP, op1, OP), OP));
        runAssert(o, Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class,
            UseOfPropertyInChainCausesCycle.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLOntology ont)")
    public void shouldCreateViolationForOWLOntologyInOWL2Profile()
        throws OWLOntologyCreationException {
        o = m.createOntology(
            df.getOWLOntologyID(Optional.of(IRI("test", "")), Optional.of(IRI("test1", ""))));
        runAssert(o, Profiles.OWL2_FULL, OntologyIRINotAbsolute.class,
            OntologyVersionIRINotAbsolute.class);
    }

    @Test
    @Tests(method = "public Object visit(IRI iri)")
    public void shouldCreateViolationForIRIInOWL2Profile() {
        declare(o, Class(IRI("test", "")));
        runAssert(o, Profiles.OWL2_FULL, UseOfNonAbsoluteIRI.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLLiteral node)")
    public void shouldCreateViolationForOWLLiteralInOWL2Profile() {
        declare(o, DATAP);
        o.add(DataPropertyAssertion(DATAP, AnonymousIndividual(),
            Literal("wrong", OWL2Datatype.XSD_INTEGER)));
        runAssert(o, Profiles.OWL2_FULL, LexicalNotInLexicalSpace.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLLiteral node)")
    public void shouldCreateViolationForDefineDatatypeOWLLiteralInOWL2Profile() {
        declare(o, DATAP);
        o.add(DataPropertyAssertion(DATAP, AnonymousIndividual(),
            Literal("wrong", df.getOWLDatatype("urn:test:defineddatatype"))));
        runAssert(o, Profiles.OWL2_FULL, UseOfDefinedDatatypeInLiteral.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2Profile() {
        declare(o, DATAP);
        o.add(DatatypeDefinition(Integer(), Boolean()),
            DatatypeDefinition(df.getOWLDatatype("urn:test:undeclaredDatatype"), Boolean()),
            DATA_PROPERTY_RANGE2);
        runAssert(o, Profiles.OWL2_FULL, UseOfDefinedDatatypeInDatatypeRestriction.class,
            UseOfIllegalFacetRestriction.class, UseOfUndeclaredDatatype.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2Profile() {
        o.add(DatatypeDefinition(FAKEDATATYPE, Boolean()));
        runAssert(o, Profiles.OWL2_FULL, UseOfUndeclaredDatatype.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype node)")
    public void shouldCreateViolationForOWLDatatypeInOWL2ELProfile() {
        declare(o, Boolean());
        runAssert(o, Profiles.OWL2_EL);
    }

    @Test
    @Tests(method = "public Object visit(OWLAnonymousIndividual individual)")
    public void shouldCreateViolationForOWLAnonymousIndividualInOWL2ELProfile() {
        o.add(ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual()));
        runAssert(o, Profiles.OWL2_EL, UseOfAnonymousIndividual.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectInverseOf property)")
    public void shouldCreateViolationForOWLObjectInverseOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(SubObjectPropertyOf(OP, ObjectInverseOf(OP)));
        runAssert(o, Profiles.OWL2_EL, UseOfObjectPropertyInverse.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataAllValuesFrom desc)")
    public void shouldCreateViolationForOWLDataAllValuesFromInOWL2ELProfile() {
        declare(o, DATAP, CL);
        o.add(SubClassOf(CL, DataAllValuesFrom(DATAP, Integer())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataExactCardinality desc)")
    public void shouldCreateViolationForOWLDataExactCardinalityInOWL2ELProfile() {
        declare(o, DATAP, CL, Integer());
        o.add(SubClassOf(CL, DataExactCardinality(1, DATAP, Integer())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataMaxCardinality desc)")
    public void shouldCreateViolationForOWLDataMaxCardinalityInOWL2ELProfile() {
        declare(o, DATAP, CL, Integer());
        o.add(SubClassOf(CL, DataMaxCardinality(1, DATAP, Integer())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataMinCardinality desc)")
    public void shouldCreateViolationForOWLDataMinCardinalityInOWL2ELProfile() {
        declare(o, DATAP, CL, Integer());
        o.add(SubClassOf(CL, DataMinCardinality(1, DATAP, Integer())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectAllValuesFrom desc)")
    public void shouldCreateViolationForOWLObjectAllValuesFromInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectAllValuesFrom(OP, OWLThing())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectComplementOf desc)")
    public void shouldCreateViolationForOWLObjectComplementOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectComplementOf(OWLNothing())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectExactCardinality desc)")
    public void shouldCreateViolationForOWLObjectExactCardinalityInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMaxCardinality desc)")
    public void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMinCardinality desc)")
    public void shouldCreateViolationForOWLObjectMinCardinalityInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectOneOf desc)")
    public void shouldCreateViolationForOWLObjectOneOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectOneOf(NamedIndividual(IRI("urn:test#", "i1")),
            NamedIndividual(IRI("urn:test#", "i2")))));
        runAssert(o, Profiles.OWL2_EL, UseOfObjectOneOfWithMultipleIndividuals.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectUnionOf desc)")
    public void shouldCreateViolationForOWLObjectUnionOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectUnionOf(OWLThing(), OWLNothing())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataComplementOf node)")
    public void shouldCreateViolationForOWLDataComplementOfInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataComplementOf(Double())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalDataRange.class, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        runAssert(o, Profiles.OWL2_EL, UseOfDataOneOfWithMultipleLiterals.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DATA_PROPERTY_RANGE);
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalDataRange.class, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLAsymmetricObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, OP);
        o.add(AsymmetricObjectProperty(OP));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointDataPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2ELProfile() {
        OWLDataProperty dp = DataProperty(IRI("urn:test#", "other"));
        declare(o, DATAP, dp);
        o.add(DisjointDataProperties(DATAP, dp));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointObjectPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2ELProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#", "test"));
        declare(o, OP, op1);
        o.add(DisjointObjectProperties(op1, OP));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2ELProfile() {
        declare(o, CL);
        o.add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, OP);
        o.add(FunctionalObjectProperty(OP));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom axiom)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2ELProfile() {
        declare(o, CL, OP);
        o.add(HasKey(CL, OP));
        runAssert(o, Profiles.OWL2_EL);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, P);
        o.add(InverseFunctionalObjectProperty(P));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseObjectPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLInverseObjectPropertiesAxiomInOWL2ELProfile() {
        declare(o, P);
        OWLObjectProperty p1 = ObjectProperty(IRI("urn:test#", "objectproperty"));
        declare(o, p1);
        o.add(InverseObjectProperties(P, p1));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, P);
        o.add(IrreflexiveObjectProperty(P));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSymmetricObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLSymmetricObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, P);
        o.add(SymmetricObjectProperty(P));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(SWRLRule rule)")
    public void shouldCreateViolationForSWRLRuleInOWL2ELProfile() {
        o.add(DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        runAssert(o, Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubPropertyChainOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2ELProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#", "op1"));
        OWLObjectProperty op2 = ObjectProperty(IRI("urn:test#", "op"));
        declare(o, op1, OP, op2, CL);
        o.add(ObjectPropertyRange(OP, CL));
        List<OWLObjectProperty> asList = Arrays.asList(op2, op1);
        o.add(SubPropertyChainOf(asList, OP));
        runAssert(o, Profiles.OWL2_EL, LastPropertyInChainNotInImposedRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype node)")
    public void shouldCreateViolationForOWLDatatypeInOWL2QLProfile() {
        declare(o, FAKEDATATYPE);
        runAssert(o, Profiles.OWL2_QL);
    }

    @Test
    @Tests(method = "public Object visit(OWLAnonymousIndividual individual)")
    public void shouldCreateViolationForOWLAnonymousIndividualInOWL2QLProfile() {
        o.add(ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual()));
        runAssert(o, Profiles.OWL2_QL, UseOfAnonymousIndividual.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom axiom)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2QLProfile() {
        declare(o, CL, OP);
        o.add(HasKey(CL, OP));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubClassOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubClassOfAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(
            SubClassOf(ObjectComplementOf(OWLNothing()), ObjectUnionOf(OWLThing(), OWLNothing())));
        runAssert(o, Profiles.OWL2_QL, UseOfNonSubClassExpression.class,
            UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentClassesAxiom axiom)")
    public void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2QLProfile() {
        o.add(EquivalentClasses(ObjectUnionOf(OWLNothing(), OWLThing()), OWLNothing()));
        runAssert(o, Profiles.OWL2_QL, UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointClassesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2QLProfile() {
        o.add(DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        runAssert(o, Profiles.OWL2_QL, UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyDomain(OP, ObjectUnionOf(OWLNothing(), OWLThing())));
        runAssert(o, Profiles.OWL2_QL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyRangeAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectUnionOf(OWLNothing(), OWLThing())));
        runAssert(o, Profiles.OWL2_QL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubPropertyChainOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2QLProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#", "op"));
        declare(o, OP, op1);
        o.add(SubPropertyChainOf(Arrays.asList(OP, op1), OP));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(FunctionalObjectProperty(OP));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(InverseFunctionalObjectProperty(OP));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLTransitiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLTransitiveObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalDataPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(FunctionalDataProperty(DATAP));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2QLProfile() {
        declare(o, DATAP, OP);
        o.add(DataPropertyDomain(DATAP, ObjectMaxCardinality(1, OP, OWLNothing())));
        runAssert(o, Profiles.OWL2_QL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLClassAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLClassAssertionAxiomInOWL2QLProfile() {
        OWLNamedIndividual i = NamedIndividual(IRI("urn:test#", "i"));
        declare(o, OP, i);
        o.add(ClassAssertion(ObjectSomeValuesFrom(OP, OWLThing()), i));
        runAssert(o, Profiles.OWL2_QL, UseOfNonAtomicClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSameIndividualAxiom axiom)")
    public void shouldCreateViolationForOWLSameIndividualAxiomInOWL2QLProfile() {
        o.add(SameIndividual(NamedIndividual(IRI("urn:test#", "individual1")),
            NamedIndividual(IRI("urn:test#", "individual2"))));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLNegativeObjectPropertyAssertionAxiomInOWL2QLProfile() {
        declare(o, OP);
        OWLNamedIndividual i = NamedIndividual(IRI("urn:test#", "i"));
        OWLNamedIndividual i1 = NamedIndividual(IRI("urn:test#", "i"));
        declare(o, i, i1);
        o.add(NegativeObjectPropertyAssertion(OP, i, i1));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLNegativeDataPropertyAssertionAxiomInOWL2QLProfile() {
        declare(o, DATAP);
        OWLNamedIndividual i = NamedIndividual(IRI("urn:test#", "i"));
        declare(o, i);
        o.add(NegativeDataPropertyAssertion(DATAP, i, Literal(1)));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2QLProfile() {
        declare(o, CL);
        o.add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(IrreflexiveObjectProperty(OP));
        runAssert(o, Profiles.OWL2_QL);
    }

    @Test
    @Tests(method = "public Object visit(SWRLRule rule)")
    public void shouldCreateViolationForSWRLRuleInOWL2QLProfile() {
        o.add(DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataComplementOf node)")
    public void shouldCreateViolationForOWLDataComplementOfInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataComplementOf(Integer())));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DATA_PROPERTY_RANGE);
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(Integer(), Boolean())));
        runAssert(o, Profiles.OWL2_QL, UseOfIllegalDataRange.class, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLClassAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLClassAssertionAxiomInOWL2RLProfile() {
        declare(o, OP);
        o.add(ClassAssertion(ObjectMinCardinality(1, OP, OWLThing()),
            NamedIndividual(IRI("urn:test#", "i"))));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2RLProfile() {
        declare(o, DATAP, OP);
        o.add(DataPropertyDomain(DATAP, ObjectMinCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointClassesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2RLProfile() {
        o.add(DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSubClassExpression.class,
            UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointDataPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2RLProfile() {
        OWLDataProperty dp = DataProperty(IRI("urn:test#", "dproperty"));
        declare(o, DATAP, dp);
        o.add(DisjointDataProperties(DATAP, dp));
        runAssert(o, Profiles.OWL2_RL);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2RLProfile() {
        declare(o, CL);
        o.add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentClassesAxiom axiom)")
    public void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2RLProfile() {
        o.add(EquivalentClasses(ObjectComplementOf(OWLThing()), OWLNothing()));
        runAssert(o, Profiles.OWL2_RL, UseOfNonEquivalentClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentDataPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2RLProfile() {
        OWLDataProperty dp = DataProperty(IRI("urn:test#", "test"));
        declare(o, DATAP, dp);
        o.add(EquivalentDataProperties(DATAP, dp));
        runAssert(o, Profiles.OWL2_RL);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalDataPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(FunctionalDataProperty(DATAP));
        runAssert(o, Profiles.OWL2_RL);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom axiom)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2RLProfile() {
        declare(o, CL, OP);
        o.add(HasKey(ObjectComplementOf(CL), OP));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2RLProfile() {
        declare(o, OP, OP);
        o.add(ObjectPropertyDomain(OP, ObjectMinCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyRangeAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2RLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectMinCardinality(1, OP, OWLThing())));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubClassOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubClassOfAxiomInOWL2RLProfile() {
        o.add(SubClassOf(ObjectComplementOf(OWLThing()),
            ObjectOneOf(NamedIndividual(IRI("urn:test#", "test")))));
        runAssert(o, Profiles.OWL2_RL, UseOfNonSubClassExpression.class,
            UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "public Object visit(SWRLRule rule)")
    public void shouldCreateViolationForSWRLRuleInOWL2RLProfile() {
        o.add(DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataComplementOf node)")
    public void shouldCreateViolationForOWLDataComplementOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataComplementOf(Integer())));
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataIntersectionOf node)")
    public void shouldCreateViolationForOWLDataIntersectionOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataIntersectionOf(Integer(), Boolean())));
        runAssert(o, Profiles.OWL2_RL);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype node)")
    public void shouldCreateViolationForOWLDatatypeInOWL2RLProfile() {
        declare(o, Datatype(IRI("urn:test#", "test")));
        runAssert(o, Profiles.OWL2_RL);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DATA_PROPERTY_RANGE);
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2RLProfile() {
        OWLDatatype datatype = Datatype(IRI("urn:test#", "datatype"));
        declare(o, datatype);
        o.add(DatatypeDefinition(datatype, Boolean()));
        runAssert(o, Profiles.OWL2_RL, UseOfIllegalAxiom.class, UseOfIllegalDataRange.class);
    }
}
