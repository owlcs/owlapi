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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
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
    private static final OWLClass CL = Class(iri(URN_TEST, "fakeclass"));
    private static final OWLDataProperty DATAP =
        DataProperty(iri(URN_DATATYPE, "fakedatatypeproperty"));
    private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE2 = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LANG_RANGE, LIT_ONE)));
    private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, LIT_ONE)));
    private static final OWLObjectProperty OPFAKE =
        ObjectProperty(iri(URN_DATATYPE, "fakeobjectproperty"));
    private static final OWLDatatype UNKNOWNFAKEDATATYPE =
        Datatype(iri(START, "unknownfakedatatype"));
    private static final OWLDatatype FAKEUNDECLAREDDATATYPE =
        Datatype(iri(URN_DATATYPE, "fakeundeclareddatatype"));
    private static final OWLDatatype FAKEDATATYPE = Datatype(iri(URN_DATATYPE, "fakedatatype"));
    private static final IRI onto = iriTest;
    OWLOntology o;

    @BeforeEach
    void setupManagersClean() {
        o = create(onto);
    }

    void add(OWLAxiom... ax) {
        o.add(ax);
    }

    protected void declare(OWLEntity... entities) {
        Stream.of(entities).forEach(entity -> o.addAxiom(Declaration(entity)));
    }

    protected void checkInCollection(List<OWLProfileViolation> violations, Class<?>[] inputList) {
        List<Class<?>> list = new ArrayList<>(Arrays.asList(inputList));
        List<Class<?>> list1 = new ArrayList<>();
        for (OWLProfileViolation violation : violations) {
            list1.add(violation.getClass());
        }
        Collections.sort(list, comp);
        Collections.sort(list1, comp);
        assertEquals(list, list1, list1.toString());
    }

    protected void runAssert(OWLProfile profile, Class<?>... expectedViolations) {
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertEquals(expectedViolations.length, violations.size(), violations.toString());
        checkInCollection(violations, expectedViolations);
        for (OWLProfileViolation violation : violations) {
            o.getOWLOntologyManager().applyChanges(violation.repair());
            violation.accept(new OWLProfileViolationVisitor() {});
            violation.accept(new OWLProfileViolationVisitorEx<String>() {

                @Override
                public Optional<String> doDefault(OWLProfileViolation viol) {
                    return optional(viol.toString());
                }
            });
        }
        violations = profile.checkOntology(o).getViolations();
        assertEquals(0, violations.size());
    }

    @Test
    void shouldCreateViolationForOWLDatatypeInOWL2DLProfile() {
        declare(UNKNOWNFAKEDATATYPE, FAKEDATATYPE, Class(FAKEDATATYPE.getIRI()), DATAP);
        add(DataPropertyRange(DATAP, FAKEUNDECLAREDDATATYPE));
        runAssert(Profiles.OWL2_DL, UseOfUndeclaredDatatype.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfile() {
        declare(Integer(), Boolean(), FAKEDATATYPE);
        o.add(DatatypeDefinition(Boolean(), Integer()), DatatypeDefinition(FAKEDATATYPE, Integer()),
            DatatypeDefinition(Integer(), FAKEDATATYPE));
        runAssert(Profiles.OWL2_DL, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfileCycles() {
        OWLDatatype dTest = Datatype(iri(START, TEST));
        declare(dTest, Integer(), Boolean(), FAKEDATATYPE);
        add(DatatypeDefinition(dTest, Boolean()), DatatypeDefinition(Boolean(), dTest),
            DatatypeDefinition(FAKEDATATYPE, Integer()),
            DatatypeDefinition(Integer(), FAKEDATATYPE));
        runAssert(Profiles.OWL2_DL, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfUnknownDatatype.class,
            UseOfUnknownDatatype.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectPropertyInOWL2DLProfile() {
        IRI iri = iri(START, TEST);
        declare(ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        add(SubObjectPropertyOf(OPFAKE, ObjectProperty(iri)));
        runAssert(Profiles.OWL2_DL, UseOfReservedVocabularyForObjectPropertyIRI.class,
            UseOfUndeclaredObjectProperty.class, IllegalPunning.class, IllegalPunning.class);
    }

    @Test
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile1() {
        declare(DataProperty(iri(START, "fail")));
        runAssert(Profiles.OWL2_DL);
    }

    @Test
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile2() {
        add(FunctionalDataProperty(DATAP));
        runAssert(Profiles.OWL2_DL, UseOfUndeclaredDataProperty.class);
    }

    @Test
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile3() {
        declare(DATAP, AnnotationProperty(DATAP.getIRI()));
        runAssert(Profiles.OWL2_DL);
    }

    @Test
    void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile4() {
        declare(DATAP, ObjectProperty(DATAP.getIRI()));
        runAssert(Profiles.OWL2_DL);
    }

    @Test
    void shouldCreateViolationForOWLAnnotationPropertyInOWL2DLProfile() {
        IRI iri = iri(START, TEST);
        declare(ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        add(SubAnnotationPropertyOf(AnnotationProperty(iri(URN_TEST, "t")),
            AnnotationProperty(iri)));
        runAssert(Profiles.OWL2_DL, UseOfReservedVocabularyForAnnotationPropertyIRI.class,
            UseOfUndeclaredAnnotationProperty.class, IllegalPunning.class, IllegalPunning.class);
    }

    @Test
    void shouldCreateViolationForOWLOntologyInOWL2DLProfile() {
        o = create(new OWLOntologyID(optional(iri(START, TEST)), optional(iri(START, "test1"))));
        runAssert(Profiles.OWL2_DL, UseOfReservedVocabularyForOntologyIRI.class,
            UseOfReservedVocabularyForVersionIRI.class);
    }

    @Test
    void shouldCreateViolationForOWLClassInOWL2DLProfile() {
        declare(Class(iri(START, TEST)), FAKEDATATYPE);
        add(ClassAssertion(Class(FAKEDATATYPE.getIRI()), AnonymousIndividual()));
        runAssert(Profiles.OWL2_DL, UseOfUndeclaredClass.class,
            DatatypeIRIAlsoUsedAsClassIRI.class);
    }

    @Test
    void shouldCreateViolationForOWLDataOneOfInOWL2DLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataOneOf()));
        runAssert(Profiles.OWL2_DL, EmptyOneOfAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDataUnionOfInOWL2DLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataUnionOf(
            DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, LIT_ONE)))));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLDataIntersectionOfInOWL2DLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataIntersectionOf(
            DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, LIT_ONE)))));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectIntersectionOfInOWL2DLProfile() {
        declare(OPFAKE, CL);
        add(ObjectPropertyRange(OPFAKE, ObjectIntersectionOf(CL)));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectOneOfInOWL2DLProfile() {
        declare(OPFAKE);
        add(ObjectPropertyRange(OPFAKE, ObjectOneOf()));
        runAssert(Profiles.OWL2_DL, EmptyOneOfAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectUnionOfInOWL2DLProfile() {
        declare(OPFAKE, CL);
        add(ObjectPropertyRange(OPFAKE, ObjectUnionOf(CL)));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2DLProfile() {
        declare(OPFAKE, CL);
        add(EquivalentClasses(CL));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2DLProfile() {
        declare(OPFAKE, CL);
        OWLDataFactory df2 = OWLManager.getOWLDataFactory(
            new OWLOntologyLoaderConfiguration().withAllowDuplicatesInConstructSets(true));
        add(df2.getOWLDisjointClassesAxiom(CL));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2DLProfile() {
        declare(OPFAKE);
        OWLClass otherfakeclass = Class(iri(URN_TEST, "otherfakeclass"));
        declare(CL);
        declare(otherfakeclass);
        add(DisjointUnion(CL, otherfakeclass));
        runAssert(Profiles.OWL2_DL, InsufficientOperands.class);
    }

    @Test
    void shouldCreateViolationForOWLEquivalentObjectPropertiesAxiomInOWL2DLProfile() {
        declare(P);
        add(EquivalentObjectProperties(P));
        runAssert(Profiles.OWL2_DL, InsufficientPropertyExpressions.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2DLProfile() {
        declare(DATAP);
        add(DisjointDataProperties(DATAP));
        runAssert(Profiles.OWL2_DL, InsufficientPropertyExpressions.class);
    }

    @Test
    void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2DLProfile() {
        declare(DATAP);
        add(EquivalentDataProperties(DATAP));
        runAssert(Profiles.OWL2_DL, InsufficientPropertyExpressions.class);
    }

    @Test
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2DLProfile() {
        declare(CL);
        add(HasKey(CL));
        runAssert(Profiles.OWL2_DL, InsufficientPropertyExpressions.class);
    }

    @Test
    void shouldCreateViolationForOWLSameIndividualAxiomInOWL2DLProfile() {
        add(SameIndividual(NamedIndividual(iri(URN_TEST, "i"))));
        runAssert(Profiles.OWL2_DL, InsufficientIndividuals.class);
    }

    @Test
    void shouldCreateViolationForOWLDifferentIndividualsAxiomInOWL2DLProfile() {
        add(DifferentIndividuals(NamedIndividual(iri(URN_TEST, "i"))));
        runAssert(Profiles.OWL2_DL, InsufficientIndividuals.class);
    }

    @Test
    void shouldCreateViolationForOWLNamedIndividualInOWL2DLProfile() {
        add(ClassAssertion(OWLThing(), NamedIndividual(iri(START, "i"))));
        runAssert(Profiles.OWL2_DL, UseOfReservedVocabularyForIndividualIRI.class);
    }

    @Test
    void shouldCreateViolationForOWLSubDataPropertyOfAxiomInOWL2DLProfile() {
        add(SubDataPropertyOf(TopDataProperty(), TopDataProperty()));
        runAssert(Profiles.OWL2_DL, UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectMinCardinalityInOWL2DLProfile() {
        declare(OPFAKE, CL);
        add(TransitiveObjectProperty(OPFAKE),
            SubClassOf(CL, ObjectMinCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2DLProfile() {
        declare(OPFAKE, CL);
        add(TransitiveObjectProperty(OPFAKE),
            SubClassOf(CL, ObjectMaxCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectExactCardinalityInOWL2DLProfile() {
        declare(OPFAKE, CL);
        add(TransitiveObjectProperty(OPFAKE),
            SubClassOf(CL, ObjectExactCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInCardinalityRestriction.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectHasSelfInOWL2DLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE), ObjectPropertyRange(OPFAKE, ObjectHasSelf(OPFAKE)));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInObjectHasSelf.class);
    }

    @Test
    void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2DLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE), FunctionalObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInFunctionalPropertyAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2DLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE), InverseFunctionalObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_DL,
            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2DLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE), IrreflexiveObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInIrreflexivePropertyAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2DLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE), AsymmetricObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_DL, UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2DLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE), DisjointObjectProperties(OPFAKE));
        runAssert(Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfNonSimplePropertyInDisjointPropertiesAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2DLProfile() {
        declare(OPFAKE, op1);
        add(SubPropertyChainOf(l(op1), OPFAKE),
            SubPropertyChainOf(Arrays.asList(OPFAKE, op1, OPFAKE), OPFAKE),
            SubPropertyChainOf(l(OPFAKE, op1), OPFAKE),
            SubPropertyChainOf(Arrays.asList(op1, OPFAKE, op1, OPFAKE), OPFAKE));
        runAssert(Profiles.OWL2_DL, InsufficientPropertyExpressions.class,
            UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class,
            UseOfPropertyInChainCausesCycle.class);
    }

    @Test
    void shouldCreateViolationForOWLOntologyInOWL2Profile() {
        o = create(new OWLOntologyID(optional(iri(TEST, "")), optional(iri("test1", ""))));
        runAssert(Profiles.OWL2_FULL, OntologyIRINotAbsolute.class,
            OntologyVersionIRINotAbsolute.class);
    }

    @Test
    void shouldCreateViolationForIRIInOWL2Profile() {
        declare(Class(iri(TEST, "")));
        runAssert(Profiles.OWL2_FULL, UseOfNonAbsoluteIRI.class);
    }

    @Test
    void shouldCreateViolationForOWLLiteralInOWL2Profile() {
        declare(DATAP);
        add(DataPropertyAssertion(DATAP, AnonymousIndividual(),
            Literal("wrong", OWL2Datatype.XSD_INTEGER)));
        runAssert(Profiles.OWL2_FULL, LexicalNotInLexicalSpace.class);
    }

    @Test
    void shouldCreateViolationForDefineDatatypeOWLLiteralInOWL2Profile() {
        declare(DATAP);
        add(DataPropertyAssertion(DATAP, AnonymousIndividual(),
            Literal("wrong", Datatype(iri("urn:test:", "defineddatatype")))));
        runAssert(Profiles.OWL2_FULL, UseOfDefinedDatatypeInLiteral.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2Profile() {
        declare(DATAP);
        add(DatatypeDefinition(Integer(), Boolean()),
            DatatypeDefinition(Datatype(iri("urn:test:", "undeclaredDatatype")), Boolean()),
            DATA_PROPERTY_RANGE2);
        runAssert(Profiles.OWL2_FULL, UseOfDefinedDatatypeInDatatypeRestriction.class,
            UseOfIllegalFacetRestriction.class, UseOfUndeclaredDatatype.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2Profile() {
        add(DatatypeDefinition(FAKEDATATYPE, Boolean()));
        runAssert(Profiles.OWL2_FULL, UseOfUndeclaredDatatype.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeInOWL2ELProfile() {
        declare(Boolean());
        runAssert(Profiles.OWL2_EL);
    }

    @Test
    void shouldCreateViolationForOWLAnonymousIndividualInOWL2ELProfile() {
        add(ClassAssertion(OWLThing(), AnonymousIndividual()));
        runAssert(Profiles.OWL2_EL, UseOfAnonymousIndividual.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectInverseOfInOWL2ELProfile() {
        declare(OPFAKE);
        add(SubObjectPropertyOf(OPFAKE, ObjectInverseOf(OPFAKE)));
        runAssert(Profiles.OWL2_EL, UseOfObjectPropertyInverse.class);
    }

    @Test
    void shouldCreateViolationForOWLDataAllValuesFromInOWL2ELProfile() {
        declare(DATAP, CL);
        add(SubClassOf(CL, DataAllValuesFrom(DATAP, Integer())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDataExactCardinalityInOWL2ELProfile() {
        declare(DATAP, CL, Integer());
        add(SubClassOf(CL, DataExactCardinality(1, DATAP, Integer())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDataMaxCardinalityInOWL2ELProfile() {
        declare(DATAP, CL, Integer());
        add(SubClassOf(CL, DataMaxCardinality(1, DATAP, Integer())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDataMinCardinalityInOWL2ELProfile() {
        declare(DATAP, CL, Integer());
        add(SubClassOf(CL, DataMinCardinality(1, DATAP, Integer())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectAllValuesFromInOWL2ELProfile() {
        declare(OPFAKE, CL);
        add(SubClassOf(CL, ObjectAllValuesFrom(OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectComplementOfInOWL2ELProfile() {
        declare(OPFAKE);
        add(ObjectPropertyRange(OPFAKE, ObjectComplementOf(OWLNothing())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectExactCardinalityInOWL2ELProfile() {
        declare(OPFAKE, CL);
        add(SubClassOf(CL, ObjectExactCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2ELProfile() {
        declare(OPFAKE, CL);
        add(SubClassOf(CL, ObjectMaxCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectMinCardinalityInOWL2ELProfile() {
        declare(OPFAKE, CL);
        add(SubClassOf(CL, ObjectMinCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectOneOfInOWL2ELProfile() {
        declare(OPFAKE);
        add(ObjectPropertyRange(OPFAKE, ObjectOneOf(NamedIndividual(iri(URN_TEST, "i1")),
            NamedIndividual(iri(URN_TEST, "i2")))));
        runAssert(Profiles.OWL2_EL, UseOfObjectOneOfWithMultipleIndividuals.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectUnionOfInOWL2ELProfile() {
        declare(OPFAKE);
        add(ObjectPropertyRange(OPFAKE, ObjectUnionOf(OWLThing(), OWLNothing())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDataComplementOfInOWL2ELProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataComplementOf(Double())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalDataRange.class, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDataOneOfInOWL2ELProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataOneOf(LIT_ONE, LIT_TWO)));
        runAssert(Profiles.OWL2_EL, UseOfDataOneOfWithMultipleLiterals.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2ELProfile() {
        declare(DATAP);
        add(DATA_PROPERTY_RANGE);
        runAssert(Profiles.OWL2_EL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDataUnionOfInOWL2ELProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        runAssert(Profiles.OWL2_EL, UseOfIllegalDataRange.class, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2ELProfile() {
        declare(OPFAKE);
        add(AsymmetricObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2ELProfile() {
        OWLDataProperty dp = DataProperty(iri(URN_TEST, "other"));
        declare(DATAP, dp);
        add(DisjointDataProperties(DATAP, dp));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2ELProfile() {
        declare(OPFAKE, op1);
        add(DisjointObjectProperties(op1, OPFAKE));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2ELProfile() {
        declare(CL);
        add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2ELProfile() {
        declare(OPFAKE);
        add(FunctionalObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2ELProfile() {
        declare(CL, OPFAKE);
        add(HasKey(CL, OPFAKE));
        runAssert(Profiles.OWL2_EL);
    }

    @Test
    void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2ELProfile() {
        declare(P);
        add(InverseFunctionalObjectProperty(P));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLInverseObjectPropertiesAxiomInOWL2ELProfile() {
        declare(P);
        OWLObjectProperty p1 = ObjectProperty(iri(URN_TEST, "objectproperty"));
        declare(p1);
        add(InverseObjectProperties(P, p1));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2ELProfile() {
        declare(P);
        add(IrreflexiveObjectProperty(P));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLSymmetricObjectPropertyAxiomInOWL2ELProfile() {
        declare(P);
        add(SymmetricObjectProperty(P));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForSWRLRuleInOWL2ELProfile() {
        add(SWRLRule(Collections.emptyList(), Collections.emptyList()));
        runAssert(Profiles.OWL2_EL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2ELProfile() {
        declare(op1, OPFAKE, op2, CL);
        add(ObjectPropertyRange(OPFAKE, CL));
        add(SubPropertyChainOf(l(op2, op1), OPFAKE));
        runAssert(Profiles.OWL2_EL, LastPropertyInChainNotInImposedRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeInOWL2QLProfile() {
        declare(FAKEDATATYPE);
        runAssert(Profiles.OWL2_QL);
    }

    @Test
    void shouldCreateViolationForOWLAnonymousIndividualInOWL2QLProfile() {
        add(ClassAssertion(OWLThing(), AnonymousIndividual()));
        runAssert(Profiles.OWL2_QL, UseOfAnonymousIndividual.class);
    }

    @Test
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2QLProfile() {
        declare(CL, OPFAKE);
        add(HasKey(CL, OPFAKE));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLSubClassOfAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(SubClassOf(ObjectComplementOf(OWLNothing()), ObjectUnionOf(OWLThing(), OWLNothing())));
        runAssert(Profiles.OWL2_QL, UseOfNonSubClassExpression.class,
            UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2QLProfile() {
        add(EquivalentClasses(ObjectUnionOf(OWLNothing(), OWLThing()), OWLNothing()));
        runAssert(Profiles.OWL2_QL, UseOfNonSubClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2QLProfile() {
        add(DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        runAssert(Profiles.OWL2_QL, UseOfNonSubClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(ObjectPropertyDomain(OPFAKE, ObjectUnionOf(OWLNothing(), OWLThing())));
        runAssert(Profiles.OWL2_QL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(ObjectPropertyRange(OPFAKE, ObjectUnionOf(OWLNothing(), OWLThing())));
        runAssert(Profiles.OWL2_QL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2QLProfile() {
        declare(OPFAKE, op1);
        add(SubPropertyChainOf(l(OPFAKE, op1), OPFAKE));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(FunctionalObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(InverseFunctionalObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLTransitiveObjectPropertyAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(TransitiveObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2QLProfile() {
        declare(DATAP);
        add(FunctionalDataProperty(DATAP));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2QLProfile() {
        declare(DATAP, OPFAKE);
        add(DataPropertyDomain(DATAP, ObjectMaxCardinality(1, OPFAKE, OWLNothing())));
        runAssert(Profiles.OWL2_QL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLClassAssertionAxiomInOWL2QLProfile() {

        declare(OPFAKE, i);
        add(ClassAssertion(ObjectSomeValuesFrom(OPFAKE, OWLThing()), i));
        runAssert(Profiles.OWL2_QL, UseOfNonAtomicClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLSameIndividualAxiomInOWL2QLProfile() {
        add(SameIndividual(NamedIndividual(iri(URN_TEST, "individual1")),
            NamedIndividual(iri(URN_TEST, "individual2"))));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLNegativeObjectPropertyAssertionAxiomInOWL2QLProfile() {
        declare(OPFAKE);

        OWLNamedIndividual i1 = NamedIndividual(iri(URN_TEST, "i"));
        declare(i, i1);
        add(NegativeObjectPropertyAssertion(OPFAKE, i, i1));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLNegativeDataPropertyAssertionAxiomInOWL2QLProfile() {
        declare(DATAP);

        declare(i);
        add(NegativeDataPropertyAssertion(DATAP, i, LIT_ONE));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2QLProfile() {
        declare(CL);
        add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2QLProfile() {
        declare(OPFAKE);
        add(IrreflexiveObjectProperty(OPFAKE));
        runAssert(Profiles.OWL2_QL);
    }

    @Test
    void shouldCreateViolationForSWRLRuleInOWL2QLProfile() {
        add(SWRLRule(Collections.emptyList(), Collections.emptyList()));
        runAssert(Profiles.OWL2_QL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDataComplementOfInOWL2QLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataComplementOf(Integer())));
        runAssert(Profiles.OWL2_QL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDataOneOfInOWL2QLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataOneOf(LIT_ONE, LIT_TWO)));
        runAssert(Profiles.OWL2_QL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2QLProfile() {
        declare(DATAP);
        add(DATA_PROPERTY_RANGE);
        runAssert(Profiles.OWL2_QL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDataUnionOfInOWL2QLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataUnionOf(Integer(), Boolean())));
        runAssert(Profiles.OWL2_QL, UseOfIllegalDataRange.class, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLClassAssertionAxiomInOWL2RLProfile() {
        declare(OPFAKE);
        add(ClassAssertion(ObjectMinCardinality(1, OPFAKE, OWLThing()),
            NamedIndividual(iri(URN_TEST, "i"))));
        runAssert(Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2RLProfile() {
        declare(DATAP, OPFAKE);
        add(DataPropertyDomain(DATAP, ObjectMinCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2RLProfile() {
        add(DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        runAssert(Profiles.OWL2_RL, UseOfNonSubClassExpression.class,
            UseOfNonSubClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2RLProfile() {
        OWLDataProperty dp = DataProperty(iri(URN_TEST, "dproperty"));
        declare(DATAP, dp);
        add(DisjointDataProperties(DATAP, dp));
        runAssert(Profiles.OWL2_RL);
    }

    @Test
    void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2RLProfile() {
        declare(CL);
        add(DisjointUnion(CL, OWLThing(), OWLNothing()));
        runAssert(Profiles.OWL2_RL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2RLProfile() {
        add(EquivalentClasses(ObjectComplementOf(OWLThing()), OWLNothing()));
        runAssert(Profiles.OWL2_RL, UseOfNonEquivalentClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2RLProfile() {
        OWLDataProperty dp = DataProperty(iri(URN_TEST, TEST));
        declare(DATAP, dp);
        add(EquivalentDataProperties(DATAP, dp));
        runAssert(Profiles.OWL2_RL);
    }

    @Test
    void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2RLProfile() {
        declare(DATAP);
        add(FunctionalDataProperty(DATAP));
        runAssert(Profiles.OWL2_RL);
    }

    @Test
    void shouldCreateViolationForOWLHasKeyAxiomInOWL2RLProfile() {
        declare(CL, OPFAKE);
        add(HasKey(ObjectComplementOf(CL), OPFAKE));
        runAssert(Profiles.OWL2_RL, UseOfNonSubClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2RLProfile() {
        declare(OPFAKE, OPFAKE);
        add(ObjectPropertyDomain(OPFAKE, ObjectMinCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2RLProfile() {
        declare(OPFAKE);
        add(ObjectPropertyRange(OPFAKE, ObjectMinCardinality(1, OPFAKE, OWLThing())));
        runAssert(Profiles.OWL2_RL, UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForOWLSubClassOfAxiomInOWL2RLProfile() {
        add(SubClassOf(ObjectComplementOf(OWLThing()),
            ObjectOneOf(NamedIndividual(iri(URN_TEST, TEST)))));
        runAssert(Profiles.OWL2_RL, UseOfNonSubClassExpression.class,
            UseOfNonSuperClassExpression.class);
    }

    @Test
    void shouldCreateViolationForSWRLRuleInOWL2RLProfile() {
        add(SWRLRule(Collections.emptyList(), Collections.emptyList()));
        runAssert(Profiles.OWL2_RL, UseOfIllegalAxiom.class);
    }

    @Test
    void shouldCreateViolationForOWLDataComplementOfInOWL2RLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataComplementOf(Integer())));
        runAssert(Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDataIntersectionOfInOWL2RLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataIntersectionOf(Integer(), Boolean())));
        runAssert(Profiles.OWL2_RL);
    }

    @Test
    void shouldCreateViolationForOWLDataOneOfInOWL2RLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataOneOf(LIT_ONE, LIT_TWO)));
        runAssert(Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeInOWL2RLProfile() {
        declare(Datatype(iri(URN_TEST, TEST)));
        runAssert(Profiles.OWL2_RL);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeRestrictionInOWL2RLProfile() {
        declare(DATAP);
        add(DATA_PROPERTY_RANGE);
        runAssert(Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDataUnionOfInOWL2RLProfile() {
        declare(DATAP);
        add(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        runAssert(Profiles.OWL2_RL, UseOfIllegalDataRange.class);
    }

    @Test
    void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2RLProfile() {
        OWLDatatype datatype = Datatype(iri(URN_TEST, "datatype"));
        declare(datatype);
        add(DatatypeDefinition(datatype, Boolean()));
        runAssert(Profiles.OWL2_RL, UseOfIllegalAxiom.class, UseOfIllegalDataRange.class);
    }
}
