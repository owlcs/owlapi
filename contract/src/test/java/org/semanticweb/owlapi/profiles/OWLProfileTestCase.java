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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
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

class OWLProfileTestCase extends TestBase {

    private static final String TEST = "test";
    private static final String URN_TEST = "urn:test#";
    private static final String URN_DATATYPE = "urn:datatype#";
    protected static final Comparator<Class<?>> comp =
        (o1, o2) -> o1.getSimpleName().compareTo(o2.getSimpleName());
    private static final String START = OWLThing().getIRI().getNamespace();
    private static final OWLClass CL = Class(IRI(URN_TEST, "fakeclass"));
    private static final OWLDataProperty DATAP =
        DataProperty(IRI(URN_DATATYPE, "fakedatatypeproperty"));
    private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE2 = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LANG_RANGE, Literal(1))));
    private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, Literal(1))));
    private static final OWLObjectProperty OP =
        ObjectProperty(IRI(URN_DATATYPE, "fakeobjectproperty"));
    private static final OWLDatatype UNKNOWNFAKEDATATYPE =
        Datatype(IRI(START, "unknownfakedatatype"));
    private static final OWLDatatype FAKEUNDECLAREDDATATYPE =
        Datatype(IRI(URN_DATATYPE, "fakeundeclareddatatype"));
    private static final OWLDatatype FAKEDATATYPE = Datatype(IRI(URN_DATATYPE, "fakedatatype"));
    private static final IRI onto = iri(URN_TEST, "ontology");
    private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();
    private static final OWLObjectProperty P = ObjectProperty(IRI(URN_TEST, "objectproperty"));
    OWLOntology o;

    @BeforeEach
    public void setupManagersClean() {
        o = getOWLOntology(onto);
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
        assertEquals(list, list1, list1.toString());
    }

    public void runAssert(OWLOntology ontology, OWLProfile profile, int expected,
        Class<?>... expectedViolations) {
        List<OWLProfileViolation> violations = profile.checkOntology(ontology).getViolations();
        assertEquals(expected, violations.size(), violations.toString());
        checkInCollection(violations, expectedViolations);
        for (OWLProfileViolation violation : violations) {
            ontology.getOWLOntologyManager().applyChanges(violation.repair());
            violation.accept(new OWLProfileViolationVisitor() {});
            violation.accept(new OWLProfileViolationVisitorEx<String>() {

                @Override
                public Optional<String> doDefault(OWLProfileViolation object) {
                    return optional(object.toString());
                }
            });
        }
        violations = profile.checkOntology(ontology).getViolations();
        assertEquals(0, violations.size());
    }

    @Test
    @Tests(method = "Object visit(OWLDatatype datatype)")
    void shouldCreateViolationForOWLDatatypeInOWL2DLProfile() {
        declare(o, UNKNOWNFAKEDATATYPE, FAKEDATATYPE, Class(FAKEDATATYPE.getIRI()), DATAP);
        o.addAxiom(DataPropertyRange(DATAP, FAKEUNDECLAREDDATATYPE));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfUndeclaredDatatype.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeDefinitionAxiom axiom)")
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfile() {
        declare(o, Integer(), Boolean(), FAKEDATATYPE);
        o.add(DatatypeDefinition(Boolean(), Integer()), DatatypeDefinition(FAKEDATATYPE, Integer()),
            DatatypeDefinition(Integer(), FAKEDATATYPE));
        int expected = 4;
        runAssert(o, Profiles.OWL2_DL, expected, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeDefinitionAxiom axiom)")
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfileCycles() {
        OWLDatatype d = Datatype(IRI(START, TEST));
        declare(o, d, Integer(), Boolean(), FAKEDATATYPE);
        o.add(DatatypeDefinition(d, Boolean()), DatatypeDefinition(Boolean(), d),
            DatatypeDefinition(FAKEDATATYPE, Integer()),
            DatatypeDefinition(Integer(), FAKEDATATYPE));
        int expected = 9;
        runAssert(o, Profiles.OWL2_DL, expected, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfUnknownDatatype.class,
            UseOfUnknownDatatype.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectProperty property)")
    void shouldCreateViolationForOWLObjectPropertyInOWL2DLProfile() {
        IRI iri = IRI(START, TEST);
        declare(o, ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        o.addAxiom(SubObjectPropertyOf(OP, ObjectProperty(iri)));
        int expected = 4;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfReservedVocabularyForObjectPropertyIRI.class,
            UseOfUndeclaredObjectProperty.class, IllegalPunning.class, IllegalPunning.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataProperty property)")
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile1() {
        declare(o, DataProperty(IRI(START, "fail")));
        int expected = 0;
        runAssert(o, Profiles.OWL2_DL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLDataProperty property)")
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile2() {
        o.addAxiom(FunctionalDataProperty(DATAP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfUndeclaredDataProperty.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataProperty property)")
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile3() {
        declare(o, DATAP, AnnotationProperty(DATAP.getIRI()));
        int expected = 0;
        runAssert(o, Profiles.OWL2_DL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLDataProperty property)")
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile4() {
        declare(o, DATAP, ObjectProperty(DATAP.getIRI()));
        int expected = 0;
        runAssert(o, Profiles.OWL2_DL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLAnnotationProperty property)")
    void shouldCreateViolationForOWLAnnotationPropertyInOWL2DLProfile() {
        IRI iri = IRI(START, TEST);
        declare(o, ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        o.add(SubAnnotationPropertyOf(AnnotationProperty(IRI(URN_TEST, "t")),
            AnnotationProperty(iri)));
        int expected = 4;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfReservedVocabularyForAnnotationPropertyIRI.class,
            UseOfUndeclaredAnnotationProperty.class, IllegalPunning.class, IllegalPunning.class);
    }

    @Test
    @Tests(method = "Object visit(OWLOntology ontology)")
    void shouldCreateViolationForOWLOntologyInOWL2DLProfile() throws OWLOntologyCreationException {
        o = m.createOntology(
            new OWLOntologyID(optional(IRI(START, TEST)), optional(IRI(START, "test1"))));
        int expected = 2;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfReservedVocabularyForOntologyIRI.class,
            UseOfReservedVocabularyForVersionIRI.class);
    }

    @Test
    @Tests(method = "Object visit(OWLClass desc)")
    void shouldCreateViolationForOWLClassInOWL2DLProfile() {
        declare(o, Class(IRI(START, TEST)), FAKEDATATYPE);
        o.add(ClassAssertion(Class(FAKEDATATYPE.getIRI()), AnonymousIndividual()));
        int expected = 2;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfUndeclaredClass.class,
            DatatypeIRIAlsoUsedAsClassIRI.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataOneOf node)")
    void shouldCreateViolationForOWLDataOneOfInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, EmptyOneOfAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataUnionOf node)")
    void shouldCreateViolationForOWLDataUnionOfInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(
            DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, Literal(1))))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataIntersectionOf node)")
    void shouldCreateViolationForOWLDataIntersectionOfInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataIntersectionOf(
            DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, Literal(1))))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectIntersectionOf node)")
    void shouldCreateViolationForOWLObjectIntersectionOfInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(ObjectPropertyRange(OP, ObjectIntersectionOf(CL)));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectOneOf node)")
    void shouldCreateViolationForOWLObjectOneOfInOWL2DLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectOneOf()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, EmptyOneOfAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectUnionOf node)")
    void shouldCreateViolationForOWLObjectUnionOfInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(ObjectPropertyRange(OP, ObjectUnionOf(CL)));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLEquivalentClassesAxiom node)")
    void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(EquivalentClasses(CL));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointClassesAxiom node)")
    void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2DLProfile() {
        declare(o, OP, CL);
        OWLDataFactory df2 = OWLManager.getOWLDataFactory(
            new OWLOntologyLoaderConfiguration().withAllowDuplicatesInConstructSets(true));
        o.add(df2.getOWLDisjointClassesAxiom(CL));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointUnionAxiom node)")
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2DLProfile() {
        declare(o, OP);
        OWLClass otherfakeclass = Class(IRI(URN_TEST, "otherfakeclass"));
        declare(o, CL);
        declare(o, otherfakeclass);
        o.add(DisjointUnion(CL, otherfakeclass));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientOperands.class);
    }

    @Test
    @Tests(method = "Object visit(OWLEquivalentObjectPropertiesAxiom node)")
    void shouldCreateViolationForOWLEquivalentObjectPropertiesAxiomInOWL2DLProfile() {
        declare(o, P);
        o.add(EquivalentObjectProperties(P));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientPropertyExpressions.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointDataPropertiesAxiom node)")
    void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(DisjointDataProperties(DATAP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientPropertyExpressions.class);
    }

    @Test
    @Tests(method = "Object visit(OWLEquivalentDataPropertiesAxiom node)")
    void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2DLProfile() {
        declare(o, DATAP);
        o.add(EquivalentDataProperties(DATAP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientPropertyExpressions.class);
    }

    @Test
    @Tests(method = "Object visit(OWLHasKeyAxiom node)")
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2DLProfile() {
        declare(o, CL);
        o.add(HasKey(CL));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientPropertyExpressions.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSameIndividualAxiom node)")
    void shouldCreateViolationForOWLSameIndividualAxiomInOWL2DLProfile() {
        o.add(SameIndividual(NamedIndividual(IRI(URN_TEST, "i"))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientIndividuals.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDifferentIndividualsAxiom node)")
    void shouldCreateViolationForOWLDifferentIndividualsAxiomInOWL2DLProfile() {
        o.add(DifferentIndividuals(NamedIndividual(IRI(URN_TEST, "i"))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientIndividuals.class);
    }

    @Test
    @Tests(method = "Object visit(OWLNamedIndividual individual)")
    void shouldCreateViolationForOWLNamedIndividualInOWL2DLProfile() {
        o.add(ClassAssertion(OWLThing(), NamedIndividual(IRI(START, "i"))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfReservedVocabularyForIndividualIRI.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSubDataPropertyOfAxiom axiom)")
    void shouldCreateViolationForOWLSubDataPropertyOfAxiomInOWL2DLProfile() {
        o.add(SubDataPropertyOf(DF.getOWLTopDataProperty(), DF.getOWLTopDataProperty()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectMinCardinality desc)")
    void shouldCreateViolationForOWLObjectMinCardinalityInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(TransitiveObjectProperty(OP),
            SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectMaxCardinality desc)")
    void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(TransitiveObjectProperty(OP),
            SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectExactCardinality desc)")
    void shouldCreateViolationForOWLObjectExactCardinalityInOWL2DLProfile() {
        declare(o, OP, CL);
        o.add(TransitiveObjectProperty(OP),
            SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectHasSelf desc)")
    void shouldCreateViolationForOWLObjectHasSelfInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), ObjectPropertyRange(OP, ObjectHasSelf(OP)));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected, UseOfNonSimplePropertyInObjectHasSelf.class);
    }

    @Test
    @Tests(method = "Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), FunctionalObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInFunctionalPropertyAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), InverseFunctionalObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), IrreflexiveObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInIrreflexivePropertyAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLAsymmetricObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), AsymmetricObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_DL, expected,
            UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointObjectPropertiesAxiom axiom)")
    void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2DLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP), DisjointObjectProperties(OP));
        int expected = 2;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientPropertyExpressions.class,
            UseOfNonSimplePropertyInDisjointPropertiesAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSubPropertyChainOfAxiom axiom)")
    void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2DLProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI(URN_TEST, "op"));
        declare(o, OP, op1);
        o.add(SubPropertyChainOf(Arrays.asList(op1), OP),
            SubPropertyChainOf(Arrays.asList(OP, op1, OP), OP),
            SubPropertyChainOf(Arrays.asList(OP, op1), OP),
            SubPropertyChainOf(Arrays.asList(op1, OP, op1, OP), OP));
        int expected = 4;
        runAssert(o, Profiles.OWL2_DL, expected, InsufficientPropertyExpressions.class,
            UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class,
            UseOfPropertyInChainCausesCycle.class);
    }

    @Test
    @Tests(method = "Object visit(OWLOntology ont)")
    void shouldCreateViolationForOWLOntologyInOWL2Profile() throws OWLOntologyCreationException {
        o = m
            .createOntology(new OWLOntologyID(optional(IRI(TEST, "")), optional(IRI("test1", ""))));
        int expected = 2;
        runAssert(o, Profiles.OWL2_FULL, expected, OntologyIRINotAbsolute.class,
            OntologyVersionIRINotAbsolute.class);
    }

    @Test
    @Tests(method = "Object visit(IRI iri)")
    void shouldCreateViolationForIRIInOWL2Profile() {
        declare(o, Class(IRI(TEST, "")));
        int expected = 1;
        runAssert(o, Profiles.OWL2_FULL, expected, UseOfNonAbsoluteIRI.class);
    }

    @Test
    @Tests(method = "Object visit(OWLLiteral node)")
    void shouldCreateViolationForOWLLiteralInOWL2Profile() {
        declare(o, DATAP);
        o.add(DataPropertyAssertion(DATAP, AnonymousIndividual(),
            Literal("wrong", OWL2Datatype.XSD_INTEGER)));
        int expected = 1;
        runAssert(o, Profiles.OWL2_FULL, expected, LexicalNotInLexicalSpace.class);
    }

    @Test
    @Tests(method = "Object visit(OWLLiteral node)")
    void shouldCreateViolationForDefineDatatypeOWLLiteralInOWL2Profile() {
        declare(o, DATAP);
        o.add(DataPropertyAssertion(DATAP, AnonymousIndividual(),
            Literal("wrong", df.getOWLDatatype("urn:test:defineddatatype"))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_FULL, expected, UseOfDefinedDatatypeInLiteral.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeRestriction node)")
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2Profile() {
        declare(o, DATAP);
        o.add(DatatypeDefinition(Integer(), Boolean()),
            DatatypeDefinition(df.getOWLDatatype("urn:test:undeclaredDatatype"), Boolean()),
            DATA_PROPERTY_RANGE2);
        int expected = 3;
        runAssert(o, Profiles.OWL2_FULL, expected, UseOfDefinedDatatypeInDatatypeRestriction.class,
            UseOfIllegalFacetRestriction.class, UseOfUndeclaredDatatype.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeDefinitionAxiom axiom)")
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2Profile() {
        o.add(DatatypeDefinition(FAKEDATATYPE, Boolean()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_FULL, expected, UseOfUndeclaredDatatype.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatype node)")
    void shouldCreateViolationForOWLDatatypeInOWL2ELProfile() {
        declare(o, Boolean());
        int expected = 0;
        runAssert(o, Profiles.OWL2_EL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLAnonymousIndividual individual)")
    void shouldCreateViolationForOWLAnonymousIndividualInOWL2ELProfile() {
        o.add(ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfAnonymousIndividual.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectInverseOf property)")
    void shouldCreateViolationForOWLObjectInverseOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(SubObjectPropertyOf(OP, ObjectInverseOf(OP)));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfObjectPropertyInverse.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataAllValuesFrom desc)")
    void shouldCreateViolationForOWLDataAllValuesFromInOWL2ELProfile() {
        declare(o, DATAP, CL);
        o.add(SubClassOf(CL, DataAllValuesFrom(DATAP, Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataExactCardinality desc)")
    void shouldCreateViolationForOWLDataExactCardinalityInOWL2ELProfile() {
        declare(o, DATAP, CL, Integer());
        o.add(SubClassOf(CL, DataExactCardinality(1, DATAP, Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataMaxCardinality desc)")
    void shouldCreateViolationForOWLDataMaxCardinalityInOWL2ELProfile() {
        declare(o, DATAP, CL, Integer());
        o.add(SubClassOf(CL, DataMaxCardinality(1, DATAP, Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataMinCardinality desc)")
    void shouldCreateViolationForOWLDataMinCardinalityInOWL2ELProfile() {
        declare(o, DATAP, CL, Integer());
        o.add(SubClassOf(CL, DataMinCardinality(1, DATAP, Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectAllValuesFrom desc)")
    void shouldCreateViolationForOWLObjectAllValuesFromInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectAllValuesFrom(OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectComplementOf desc)")
    void shouldCreateViolationForOWLObjectComplementOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectComplementOf(OWLNothing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectExactCardinality desc)")
    void shouldCreateViolationForOWLObjectExactCardinalityInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectMaxCardinality desc)")
    void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectMinCardinality desc)")
    void shouldCreateViolationForOWLObjectMinCardinalityInOWL2ELProfile() {
        declare(o, OP, CL);
        o.add(SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectOneOf desc)")
    void shouldCreateViolationForOWLObjectOneOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectOneOf(NamedIndividual(IRI(URN_TEST, "i1")),
            NamedIndividual(IRI(URN_TEST, "i2")))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfObjectOneOfWithMultipleIndividuals.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectUnionOf desc)")
    void shouldCreateViolationForOWLObjectUnionOfInOWL2ELProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectUnionOf(OWLThing(), OWLNothing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataComplementOf node)")
    void shouldCreateViolationForOWLDataComplementOfInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataComplementOf(Double())));
        int expected = 2;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalDataRange.class,
            UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataOneOf node)")
    void shouldCreateViolationForOWLDataOneOfInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfDataOneOfWithMultipleLiterals.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeRestriction node)")
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DATA_PROPERTY_RANGE);
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataUnionOf node)")
    void shouldCreateViolationForOWLDataUnionOfInOWL2ELProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        int expected = 2;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalDataRange.class,
            UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLAsymmetricObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, OP);
        o.add(AsymmetricObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointDataPropertiesAxiom axiom)")
    void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2ELProfile() {
        OWLDataProperty dp = DataProperty(IRI(URN_TEST, "other"));
        declare(o, DATAP, dp);
        o.add(DisjointDataProperties(DATAP, dp));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointObjectPropertiesAxiom axiom)")
    void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2ELProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI(URN_TEST, TEST));
        declare(o, OP, op1);
        o.add(DisjointObjectProperties(op1, OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointUnionAxiom axiom)")
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2ELProfile() {
        declare(o, CL);
        o.add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, OP);
        o.add(FunctionalObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLHasKeyAxiom axiom)")
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2ELProfile() {
        declare(o, CL, OP);
        o.add(HasKey(CL, OP));
        int expected = 0;
        runAssert(o, Profiles.OWL2_EL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, P);
        o.add(InverseFunctionalObjectProperty(P));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLInverseObjectPropertiesAxiom axiom)")
    void shouldCreateViolationForOWLInverseObjectPropertiesAxiomInOWL2ELProfile() {
        declare(o, P);
        OWLObjectProperty p1 = ObjectProperty(IRI(URN_TEST, "objectproperty"));
        declare(o, p1);
        o.add(InverseObjectProperties(P, p1));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, P);
        o.add(IrreflexiveObjectProperty(P));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSymmetricObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLSymmetricObjectPropertyAxiomInOWL2ELProfile() {
        declare(o, P);
        o.add(SymmetricObjectProperty(P));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(SWRLRule rule)")
    void shouldCreateViolationForSWRLRuleInOWL2ELProfile() {
        o.add(DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSubPropertyChainOfAxiom axiom)")
    void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2ELProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI(URN_TEST, "op1"));
        OWLObjectProperty op2 = ObjectProperty(IRI(URN_TEST, "op"));
        declare(o, op1, OP, op2, CL);
        o.add(ObjectPropertyRange(OP, CL));
        List<OWLObjectProperty> asList = Arrays.asList(op2, op1);
        o.add(SubPropertyChainOf(asList, OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_EL, expected, LastPropertyInChainNotInImposedRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatype node)")
    void shouldCreateViolationForOWLDatatypeInOWL2QLProfile() {
        declare(o, FAKEDATATYPE);
        int expected = 0;
        runAssert(o, Profiles.OWL2_QL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLAnonymousIndividual individual)")
    void shouldCreateViolationForOWLAnonymousIndividualInOWL2QLProfile() {
        o.add(ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfAnonymousIndividual.class);
    }

    @Test
    @Tests(method = "Object visit(OWLHasKeyAxiom axiom)")
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2QLProfile() {
        declare(o, CL, OP);
        o.add(HasKey(CL, OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSubClassOfAxiom axiom)")
    void shouldCreateViolationForOWLSubClassOfAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(
            SubClassOf(ObjectComplementOf(OWLNothing()), ObjectUnionOf(OWLThing(), OWLNothing())));
        int expected = 2;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonSubClassExpression.class,
            UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLEquivalentClassesAxiom axiom)")
    void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2QLProfile() {
        o.add(EquivalentClasses(ObjectUnionOf(OWLNothing(), OWLThing()), OWLNothing()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointClassesAxiom axiom)")
    void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2QLProfile() {
        o.add(DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectPropertyDomainAxiom axiom)")
    void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyDomain(OP, ObjectUnionOf(OWLNothing(), OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectPropertyRangeAxiom axiom)")
    void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectUnionOf(OWLNothing(), OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSubPropertyChainOfAxiom axiom)")
    void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2QLProfile() {
        OWLObjectProperty op1 = ObjectProperty(IRI(URN_TEST, "op"));
        declare(o, OP, op1);
        o.add(SubPropertyChainOf(Arrays.asList(OP, op1), OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(FunctionalObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(InverseFunctionalObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLTransitiveObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLTransitiveObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(TransitiveObjectProperty(OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLFunctionalDataPropertyAxiom axiom)")
    void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(FunctionalDataProperty(DATAP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataPropertyDomainAxiom axiom)")
    void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2QLProfile() {
        declare(o, DATAP, OP);
        o.add(DataPropertyDomain(DATAP, ObjectMaxCardinality(1, OP, OWLNothing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLClassAssertionAxiom axiom)")
    void shouldCreateViolationForOWLClassAssertionAxiomInOWL2QLProfile() {
        OWLNamedIndividual i = NamedIndividual(IRI(URN_TEST, "i"));
        declare(o, OP, i);
        o.add(ClassAssertion(ObjectSomeValuesFrom(OP, OWLThing()), i));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfNonAtomicClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSameIndividualAxiom axiom)")
    void shouldCreateViolationForOWLSameIndividualAxiomInOWL2QLProfile() {
        o.add(SameIndividual(NamedIndividual(IRI(URN_TEST, "individual1")),
            NamedIndividual(IRI(URN_TEST, "individual2"))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom)")
    void shouldCreateViolationForOWLNegativeObjectPropertyAssertionAxiomInOWL2QLProfile() {
        declare(o, OP);
        OWLNamedIndividual i = NamedIndividual(IRI(URN_TEST, "i"));
        OWLNamedIndividual i1 = NamedIndividual(IRI(URN_TEST, "i"));
        declare(o, i, i1);
        o.add(NegativeObjectPropertyAssertion(OP, i, i1));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLNegativeDataPropertyAssertionAxiom axiom)")
    void shouldCreateViolationForOWLNegativeDataPropertyAssertionAxiomInOWL2QLProfile() {
        declare(o, DATAP);
        OWLNamedIndividual i = NamedIndividual(IRI(URN_TEST, "i"));
        declare(o, i);
        o.add(NegativeDataPropertyAssertion(DATAP, i, Literal(1)));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointUnionAxiom axiom)")
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2QLProfile() {
        declare(o, CL);
        o.add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2QLProfile() {
        declare(o, OP);
        o.add(IrreflexiveObjectProperty(OP));
        int expected = 0;
        runAssert(o, Profiles.OWL2_QL, expected);
    }

    @Test
    @Tests(method = "Object visit(SWRLRule rule)")
    void shouldCreateViolationForSWRLRuleInOWL2QLProfile() {
        o.add(DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataComplementOf node)")
    void shouldCreateViolationForOWLDataComplementOfInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataComplementOf(Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataOneOf node)")
    void shouldCreateViolationForOWLDataOneOfInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeRestriction node)")
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DATA_PROPERTY_RANGE);
        int expected = 1;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataUnionOf node)")
    void shouldCreateViolationForOWLDataUnionOfInOWL2QLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(Integer(), Boolean())));
        int expected = 2;
        runAssert(o, Profiles.OWL2_QL, expected, UseOfIllegalDataRange.class,
            UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLClassAssertionAxiom axiom)")
    void shouldCreateViolationForOWLClassAssertionAxiomInOWL2RLProfile() {
        declare(o, OP);
        o.add(ClassAssertion(ObjectMinCardinality(1, OP, OWLThing()),
            NamedIndividual(IRI(URN_TEST, "i"))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataPropertyDomainAxiom axiom)")
    void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2RLProfile() {
        declare(o, DATAP, OP);
        o.add(DataPropertyDomain(DATAP, ObjectMinCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointClassesAxiom axiom)")
    void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2RLProfile() {
        o.add(DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        int expected = 2;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSubClassExpression.class,
            UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointDataPropertiesAxiom axiom)")
    void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2RLProfile() {
        OWLDataProperty dp = DataProperty(IRI(URN_TEST, "dproperty"));
        declare(o, DATAP, dp);
        o.add(DisjointDataProperties(DATAP, dp));
        int expected = 0;
        runAssert(o, Profiles.OWL2_RL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLDisjointUnionAxiom axiom)")
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2RLProfile() {
        declare(o, CL);
        o.add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLEquivalentClassesAxiom axiom)")
    void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2RLProfile() {
        o.add(EquivalentClasses(ObjectComplementOf(OWLThing()), OWLNothing()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonEquivalentClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLEquivalentDataPropertiesAxiom axiom)")
    void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2RLProfile() {
        OWLDataProperty dp = DataProperty(IRI(URN_TEST, TEST));
        declare(o, DATAP, dp);
        o.add(EquivalentDataProperties(DATAP, dp));
        int expected = 0;
        runAssert(o, Profiles.OWL2_RL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLFunctionalDataPropertyAxiom axiom)")
    void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(FunctionalDataProperty(DATAP));
        int expected = 0;
        runAssert(o, Profiles.OWL2_RL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLHasKeyAxiom axiom)")
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2RLProfile() {
        declare(o, CL, OP);
        o.add(HasKey(ObjectComplementOf(CL), OP));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSubClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectPropertyDomainAxiom axiom)")
    void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2RLProfile() {
        declare(o, OP, OP);
        o.add(ObjectPropertyDomain(OP, ObjectMinCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLObjectPropertyRangeAxiom axiom)")
    void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2RLProfile() {
        declare(o, OP);
        o.add(ObjectPropertyRange(OP, ObjectMinCardinality(1, OP, OWLThing())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(OWLSubClassOfAxiom axiom)")
    void shouldCreateViolationForOWLSubClassOfAxiomInOWL2RLProfile() {
        o.add(SubClassOf(ObjectComplementOf(OWLThing()),
            ObjectOneOf(NamedIndividual(IRI(URN_TEST, TEST)))));
        int expected = 2;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfNonSubClassExpression.class,
            UseOfNonSuperClassExpression.class);
    }

    @Test
    @Tests(method = "Object visit(SWRLRule rule)")
    void shouldCreateViolationForSWRLRuleInOWL2RLProfile() {
        o.add(DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalAxiom.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataComplementOf node)")
    void shouldCreateViolationForOWLDataComplementOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataComplementOf(Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataIntersectionOf node)")
    void shouldCreateViolationForOWLDataIntersectionOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataIntersectionOf(Integer(), Boolean())));
        int expected = 0;
        runAssert(o, Profiles.OWL2_RL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLDataOneOf node)")
    void shouldCreateViolationForOWLDataOneOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatype node)")
    void shouldCreateViolationForOWLDatatypeInOWL2RLProfile() {
        declare(o, Datatype(IRI(URN_TEST, TEST)));
        int expected = 0;
        runAssert(o, Profiles.OWL2_RL, expected);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeRestriction node)")
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DATA_PROPERTY_RANGE);
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDataUnionOf node)")
    void shouldCreateViolationForOWLDataUnionOfInOWL2RLProfile() {
        declare(o, DATAP);
        o.add(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        int expected = 1;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalDataRange.class);
    }

    @Test
    @Tests(method = "Object visit(OWLDatatypeDefinitionAxiom axiom)")
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2RLProfile() {
        OWLDatatype datatype = Datatype(IRI(URN_TEST, "datatype"));
        declare(o, datatype);
        o.add(DatatypeDefinition(datatype, Boolean()));
        int expected = 2;
        runAssert(o, Profiles.OWL2_RL, expected, UseOfIllegalAxiom.class,
            UseOfIllegalDataRange.class);
    }
}
