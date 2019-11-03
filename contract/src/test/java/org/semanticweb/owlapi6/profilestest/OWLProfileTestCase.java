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
package org.semanticweb.owlapi6.profilestest;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Boolean;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataAllValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataExactCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataOneOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DatatypeDefinition;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DatatypeRestriction;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointUnion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Double;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FacetRestriction;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.HasKey;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Integer;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectAllValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectExactCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectHasSelf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectInverseOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectMaxCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectMinCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectOneOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubPropertyChainOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_DL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_EL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_FULL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_QL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_RL;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.profiles.OWLProfile;
import org.semanticweb.owlapi6.profiles.OWLProfileViolation;
import org.semanticweb.owlapi6.profiles.Profiles;
import org.semanticweb.owlapi6.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapi6.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapi6.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapi6.profiles.violations.IllegalPunning;
import org.semanticweb.owlapi6.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapi6.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapi6.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapi6.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapi6.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapi6.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapi6.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapi6.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapi6.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapi6.profiles.violations.UseOfDataOneOfWithMultipleLiterals;
import org.semanticweb.owlapi6.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapi6.profiles.violations.UseOfDefinedDatatypeInLiteral;
import org.semanticweb.owlapi6.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapi6.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapi6.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapi6.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi6.profiles.violations.UseOfObjectOneOfWithMultipleIndividuals;
import org.semanticweb.owlapi6.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapi6.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapi6.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi6.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapi6.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapi6.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapi6.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapi6.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapi6.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapi6.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapi6.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapi6.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi6.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapi6.profiles.violations.UseOfUnknownDatatype;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLFacet;

@RunWith(Parameterized.class)
public class OWLProfileTestCase extends TestBase {

    private static final String NEGO = "OWLNegativeObjectPropertyAssertionAxiom";
    private static final String NEGD = "OWLNegativeDataPropertyAssertionAxiom";
    private static final String INVP = "OWLInverseObjectPropertiesAxiom";
    private static final String OEX = "OWLObjectExactCardinality";
    private static final String ALL = "OWLObjectAllValuesFrom";
    private static final String TRAN = "OWLTransitiveObjectPropertyAxiom";
    private static final String INV = "OWLObjectInverseOf";
    private static final String AND = "OWLObjectIntersectionOf";
    private static final String SELF = "OWLObjectHasSelf";
    private static final String NOT = "OWLObjectComplementOf";
    private static final String IRR = "OWLIrreflexiveObjectPropertyAxiom";
    private static final String IFP = "OWLInverseFunctionalObjectPropertyAxiom";
    private static final String DISJO = "OWLDisjointObjectPropertiesAxiom";
    private static final String CLASS = "OWLClassAssertionAxiom";
    private static final String CLA = "OWLClass";
    private static final String DUN = "OWLDisjointUnionAxiom";
    private static final String ANNP = "OWLAnnotationProperty";
    private static final String IRI = "IRI";
    private static final String DDOM = "OWLDataPropertyDomainAxiom";
    private static final String DMIN = "OWLDataMinCardinality";
    private static final String DMAX = "OWLDataMaxCardinality";
    private static final String DEX = "OWLDataExactCardinality";
    private static final String DALL = "OWLDataAllValuesFrom";
    private static final String LIT = "OWLLiteral";
    private static final String FDATA = "OWLFunctionalDataPropertyAxiom";
    private static final String SUBD = "OWLSubDataPropertyOfAxiom";
    private static final String SUB = "OWLSubClassOfAxiom";
    private static final String IND = "OWLNamedIndividual";
    private static final String DDISJ = "OWLDisjointDataPropertiesAxiom";
    private static final String DIFF = "OWLDifferentIndividualsAxiom";
    private static final String DATADEF = "OWLDatatypeDefinitionAxiom";
    private static final String EQOBJ = "OWLEquivalentObjectPropertiesAxiom";
    private static final String EQDATA = "OWLEquivalentDataPropertiesAxiom";
    private static final String SAME = "OWLSameIndividualAxiom";
    private static final String RULE = "SWRLRule";
    private static final String DATAAND = "OWLDataIntersectionOf";
    private static final String ONE = "OWLObjectOneOf";
    private static final String OR = "OWLObjectUnionOf";
    private static final String OBJP = "OWLObjectProperty";
    private static final String SYMM = "OWLSymmetricObjectPropertyAxiom";
    private static final String OFUNC = "OWLFunctionalObjectPropertyAxiom";
    private static final String ORANGE = "OWLObjectPropertyRangeAxiom";
    private static final String ODOM = "OWLObjectPropertyDomainAxiom";
    private static final String ASYMM = "OWLAsymmetricObjectPropertyAxiom";
    private static final String DAT = "OWLDatatype";
    private static final String DATAPR = "OWLDataProperty";
    private static final String DATAOR = "OWLDataUnionOf";
    private static final String DATAREST = "OWLDatatypeRestriction";
    private static final String DATAONE = "OWLDataOneOf";
    private static final String DATANOT = "OWLDataComplementOf";
    private static final String EQUIV = "OWLEquivalentClassesAxiom";
    private static final String DISJ = "OWLDisjointClassesAxiom";
    private static final String ANON = "OWLAnonymousIndividual";
    private static final String ONT = "OWLOntology";
    private static final String OBJMIN = "OWLObjectMinCardinality";
    private static final String OBJMAX = "OWLObjectMaxCardinality";
    private static final String HASKEY = "OWLHasKeyAxiom";
    private static final String CHAIN = "OWLSubPropertyChainOfAxiom";
    private static final OWLDisjointClassesAxiom DISJOINT_TOP =
        DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing());
    private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();
    private static final OWLClassAssertionAxiom ANON_INDIVIDUAL =
        ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual());
    private static final OWLLiteral LW2 =
        Literal("wrong", DF.getOWLDatatype("urn:test:defineddatatype"));
    private static final OWLLiteral LW1 = Literal("wrong", OWL2Datatype.XSD_INTEGER);
    private static final SWRLRule SWRL_RULE =
        DF.getSWRLRule(Collections.emptyList(), Collections.emptyList());
    private static final String t = "urn:test#";
    private static final OWLAnnotationProperty APT = AnnotationProperty(IRI(t, "t"));
    private static final OWLDatatype FAKEDT2 = Datatype(IRI(t, "fakeundeclareddatatype"));
    private static final OWLLiteral _1 = Literal(1);
    private static final OWLDatatype FAKEDT = Datatype(IRI("urn:datatype#", "fakedatatype"));
    private static final OWLDatatypeDefinitionAxiom DT_BOOL_DEF =
        DatatypeDefinition(FAKEDT, Boolean());
    private static final OWLDataOneOf DATA_ONE_OF = DataOneOf(_1, Literal(2));
    private static final OWLNamedIndividual I = NamedIndividual(IRI(t, "ind"));
    private static final OWLNamedIndividual I1 = NamedIndividual(IRI(t, "i1"));
    private static final OWLNamedIndividual I2 = NamedIndividual(IRI(t, "i2"));
    protected static final Comparator<Class<?>> comp = Comparator.comparing(Class::getSimpleName);
    private static final OWLDataProperty DATA_PROPERTY = DataProperty(IRI(t, "dproperty"));
    private static final OWLDatatype DT = Datatype(IRI(t, "datatype"));
    private static final OWLObjectProperty OPX = ObjectProperty(IRI(t, "op1"));
    private static final OWLDataProperty OTHER_DP = DataProperty(IRI(t, "other"));
    private static final OWLClass OTHERFAKECLASS = Class(IRI(t, "otherfakeclass"));
    private static final IRI IRI_TEST = IRI(t, "test");
    private static final OWLDatatype DTT = Datatype(IRI_TEST);
    private static final OWLNamedIndividual I4 = NamedIndividual(IRI_TEST);
    private static final OWLDataProperty TEST_DP = DataProperty(IRI_TEST);
    private static final IRI RELATIVE_TEST = IRI("test", "");
    private static final String START = OWLThing().getIRI().getNamespace();
    private static final OWLDatatype DT3 = Datatype(IRI(START, "unknownfakedatatype"));
    private static final OWLDataProperty DT_FAIL = DataProperty(IRI(START, "fail"));
    private static final OWLNamedIndividual I3 = NamedIndividual(IRI(START, "i"));
    private static final IRI OWL_TEST = IRI(START, "test");
    private static final OWLDataProperty OWLTEST_DP = DataProperty(OWL_TEST);
    private static final OWLAnnotationProperty OWLTEST_AP = AnnotationProperty(OWL_TEST);
    private static final OWLObjectProperty OWLTEST_OP = ObjectProperty(OWL_TEST);
    private static final OWLDatatype OWLTEST_DT = Datatype(OWL_TEST);
    private static final OWLClass CL = Class(IRI(t, "fakeclass"));
    private static final OWLDataProperty DATAP = DataProperty(IRI(t, "fakedatatypeproperty"));
    private static final OWLDataPropertyRangeAxiom ILLEGAL_DATAP_RANGE_2 =
        DataPropertyRange(DATAP, DataComplementOf(Integer()));
    private static final OWLDataPropertyRangeAxiom ONEOF_DATAP_RANGE =
        DataPropertyRange(DATAP, DATA_ONE_OF);
    private static final OWLDataPropertyRangeAxiom ILLEGAL_DATAP_RANGE = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, _1)));
    private static final OWLFunctionalDataPropertyAxiom DATAP_FUNCTIONAL =
        FunctionalDataProperty(DATAP);
    private static final OWLObjectProperty OPP = ObjectProperty(DATAP.getIRI());
    private static final OWLAnnotationProperty APP = AnnotationProperty(DATAP.getIRI());
    private static final OWLObjectProperty OP = ObjectProperty(IRI(t, "fakeobjectproperty"));
    private static final OWLObjectProperty op1 = ObjectProperty(IRI(t, "op"));
    private static final IRI ONTO = DF.getIRI(t, "ontology");
    private static final OWLObjectProperty P = ObjectProperty(IRI(t, "objectproperty"));

    @SafeVarargs
    private static <T> List<T> l(T... e) {
        return Arrays.asList(e);
    }

    private static void checkInCollection(List<OWLProfileViolation> violations,
        List<Class<?>> inputList) {
        inputList.sort(comp);
        assertEquals(inputList, asList(violations.stream().map(Object::getClass).sorted(comp)));
    }

    public void runAssert(OWLOntology ontology, OWLProfile profile,
        List<Class<?>> expectedViolations) {
        List<OWLProfileViolation> violations = profile.checkOntology(ontology).getViolations();
        assertEquals(violations.toString(), expectedViolations.size(), violations.size());
        checkInCollection(violations, expectedViolations);
        ontology.applyChanges(violations.stream().flatMap(v -> v.repair().stream()));
        assertEquals(0, profile.checkOntology(ontology).getViolations().size());
    }

    Profiles p;
    List<OWLEntity> entities;
    List<OWLAxiom> axioms;
    Supplier<OWLOntology> onts;
    List<Class<?>> exceptions;

    public OWLProfileTestCase(Supplier<OWLOntology> os, String name, List<OWLEntity> entities,
        List<OWLAxiom> axioms, Profiles p, List<Class<?>> exceptions) {
        this.p = p;
        this.entities = entities;
        this.axioms = axioms;
        onts = os;
        this.exceptions = exceptions;
    }

    static OWLOntology os0() {
        try {
            return OWLManager.createOWLOntologyManager().createOntology(ONTO);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    static OWLOntology os1() {
        try {
            return OWLManager.createOWLOntologyManager()
                .createOntology(DF.getOWLOntologyID(OWL_TEST, IRI(START, "test1")));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    static OWLOntology os2() {
        try {
            return OWLManager.createOWLOntologyManager().createOntology(
                DF.getOWLOntologyID(Optional.of(RELATIVE_TEST), Optional.of(IRI("test1", ""))));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Test
    public void should() {
        OWLOntology o = onts.get();
        entities.stream().forEach(e -> o.add(Declaration(e)));
        o.add(axioms);
        runAssert(o, p, exceptions);
    }

    static Object[] arg(Supplier<OWLOntology> supplier, String name, List<OWLEntity> e,
        List<OWLAxiom> a, Profiles p, List<Class<?>> exceptions) {
        return new Object[] {supplier, name, e, a, p, exceptions};
    }

    static Supplier<OWLOntology> os = OWLProfileTestCase::os0;
    static Supplier<OWLOntology> o1 = OWLProfileTestCase::os1;
    static Supplier<OWLOntology> o2 = OWLProfileTestCase::os2;

    @Parameters
    public static Collection<Object[]> getData() {
        List<Object[]> toReturn = new ArrayList<>();
//@formatter:off
        toReturn.add(arg(o1, ONT,      l(), l(), OWL2_DL,      l(UseOfReservedVocabularyForOntologyIRI.class, UseOfReservedVocabularyForVersionIRI.class)));
        toReturn.add(arg(o2, ONT,      l(), l(), OWL2_FULL,    l(OntologyIRINotAbsolute.class, OntologyVersionIRINotAbsolute.class)));
        toReturn.add(arg(os, ANON,     l(), l(ANON_INDIVIDUAL), OWL2_EL, l(UseOfAnonymousIndividual.class)));
        toReturn.add(arg(os, ANON,     l(), l(ANON_INDIVIDUAL), OWL2_QL, l(UseOfAnonymousIndividual.class)));
        toReturn.add(arg(os, DATAPR,   l(), l(DATAP_FUNCTIONAL), OWL2_DL, l(UseOfUndeclaredDataProperty.class)));
        toReturn.add(arg(os, DATADEF,  l(), l(DT_BOOL_DEF), OWL2_FULL, l(UseOfUndeclaredDatatype.class)));
        toReturn.add(arg(os, DIFF,     l(), l(DifferentIndividuals(I)), OWL2_DL, l(InsufficientIndividuals.class)));
        toReturn.add(arg(os, DISJ,     l(), l(DISJOINT_TOP), OWL2_QL, l(UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, DISJ,     l(), l(DISJOINT_TOP), OWL2_RL, l(UseOfNonSubClassExpression.class, UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, DDISJ,    l(), l(DisjointDataProperties(DATAP)), OWL2_DL, l(InsufficientPropertyExpressions.class, UseOfUndeclaredDataProperty.class)));
        toReturn.add(arg(os, EQUIV,    l(), l(EquivalentClasses(ObjectComplementOf(OWLThing()), OWLNothing())), OWL2_RL, l(UseOfNonEquivalentClassExpression.class)));
        toReturn.add(arg(os, EQUIV,    l(), l(EquivalentClasses(ObjectUnionOf(OWLNothing(), OWLThing()), OWLNothing())), OWL2_QL, l(UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, EQDATA,   l(), l(EquivalentDataProperties(DATAP)), OWL2_DL, l(InsufficientPropertyExpressions.class, UseOfUndeclaredDataProperty.class)));
        toReturn.add(arg(os, EQOBJ,    l(), l(EquivalentObjectProperties(OP)), OWL2_DL, l(InsufficientPropertyExpressions.class, UseOfUndeclaredObjectProperty.class)));
        toReturn.add(arg(os, IND,      l(), l(ClassAssertion(OWLThing(), I3)), OWL2_DL, l(UseOfReservedVocabularyForIndividualIRI.class)));
        toReturn.add(arg(os, SAME,     l(), l(SameIndividual(I)), OWL2_DL, l(InsufficientIndividuals.class)));
        toReturn.add(arg(os, SAME,     l(), l(SameIndividual(I1, I2)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, SUB,      l(), l(SubClassOf(ObjectComplementOf(OWLThing()), ObjectOneOf(I4))), OWL2_RL, l(UseOfNonSubClassExpression.class, UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, SUBD,     l(), l(SubDataPropertyOf(DF.getOWLTopDataProperty(), DF.getOWLTopDataProperty())), OWL2_DL, l(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class)));
        toReturn.add(arg(os, RULE,     l(), l(SWRL_RULE), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, RULE,     l(), l(SWRL_RULE), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, RULE,     l(), l(SWRL_RULE), OWL2_RL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DATANOT,  l(DATAP), l(DataPropertyRange(DATAP, DataComplementOf(Double()))), OWL2_EL, l(UseOfIllegalDataRange.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATANOT,  l(DATAP), l(ILLEGAL_DATAP_RANGE_2), OWL2_QL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATANOT,  l(DATAP), l(ILLEGAL_DATAP_RANGE_2), OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAAND,  l(DATAP), l(DataPropertyRange(DATAP, DataIntersectionOf())), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DATAAND,  l(DATAP), l(DataPropertyRange(DATAP, DataIntersectionOf(Integer(), Boolean()))), OWL2_RL, l()));
        toReturn.add(arg(os, DATAONE,  l(DATAP), l(ONEOF_DATAP_RANGE), OWL2_EL, l(UseOfDataOneOfWithMultipleLiterals.class)));
        toReturn.add(arg(os, DATAONE,  l(DATAP), l(ONEOF_DATAP_RANGE), OWL2_QL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAONE,  l(DATAP), l(ONEOF_DATAP_RANGE), OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAONE,  l(DATAP), l(DataPropertyRange(DATAP, DataOneOf())), OWL2_DL, l(EmptyOneOfAxiom.class)));
        toReturn.add(arg(os, DATAREST, l(DATAP), l(ILLEGAL_DATAP_RANGE), OWL2_EL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAREST, l(DATAP), l(ILLEGAL_DATAP_RANGE), OWL2_QL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAREST, l(DATAP), l(ILLEGAL_DATAP_RANGE), OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAREST, l(DATAP), l(DatatypeDefinition(Integer(), Boolean()), DT_BOOL_DEF, DataPropertyRange(DATAP, DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LANG_RANGE, _1)))), OWL2_FULL, l(UseOfDefinedDatatypeInDatatypeRestriction.class, UseOfIllegalFacetRestriction.class, UseOfUndeclaredDatatype.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAP), l(DataPropertyRange(DATAP, DataUnionOf())), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAP), l(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer()))), OWL2_EL, l(UseOfIllegalDataRange.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAP), l(DataPropertyRange(DATAP, DataUnionOf(Double(), Integer()))), OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAP), l(DataPropertyRange(DATAP, DataUnionOf(Integer(), Boolean()))), OWL2_QL, l(UseOfIllegalDataRange.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, FDATA,    l(DATAP), l(DATAP_FUNCTIONAL), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, FDATA,    l(DATAP), l(DATAP_FUNCTIONAL), OWL2_RL, l()));
        toReturn.add(arg(os, LIT,      l(DATAP), l(DataPropertyAssertion(DATAP, AnonymousIndividual(), LW1)), OWL2_FULL, l(LexicalNotInLexicalSpace.class)));
        toReturn.add(arg(os, LIT,      l(DATAP), l(DataPropertyAssertion(DATAP, AnonymousIndividual(), LW2)), OWL2_FULL, l(UseOfDefinedDatatypeInLiteral.class)));
        toReturn.add(arg(os, DALL,   l(DATAP, CL),               l(SubClassOf(CL, DataAllValuesFrom(DATAP, Integer()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DEX,    l(DATAP, CL, Integer()),    l(SubClassOf(CL, DataExactCardinality(1, DATAP, Integer()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DMAX,   l(DATAP, CL, Integer()),    l(SubClassOf(CL, DataMaxCardinality(1, DATAP, Integer()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DMIN,   l(DATAP, CL, Integer()),    l(SubClassOf(CL, DataMinCardinality(1, DATAP, Integer()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DATAPR, l(DATAP, APP),  l(), OWL2_DL, l()));
        toReturn.add(arg(os, DATAPR, l(DATAP, OPP),  l(), OWL2_DL, l()));
        toReturn.add(arg(os, DATAPR, l(DT_FAIL),     l(), OWL2_DL, l()));
        toReturn.add(arg(os, DDOM,   l(DATAP, OP),   l(DataPropertyDomain(DATAP, ObjectMaxCardinality(1, OP, OWLNothing()))), OWL2_QL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, DDOM,   l(DATAP, OP),   l(DataPropertyDomain(DATAP, ObjectMinCardinality(1, OP, OWLThing()))), OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, DAT,    l(Boolean()),   l(), OWL2_EL, l()));
        toReturn.add(arg(os, DAT,    l(DT3, FAKEDT, Class(FAKEDT.getIRI()), DATAP), l(DataPropertyRange(DATAP, FAKEDT2)), OWL2_DL, l(UseOfUndeclaredDatatype.class)));
        toReturn.add(arg(os, DAT,    l(DTT),     l(), OWL2_RL, l()));
        toReturn.add(arg(os, DAT,    l(FAKEDT),  l(), OWL2_QL, l()));
        toReturn.add(arg(os, DATADEF,l(DT), l(DatatypeDefinition(DT, Boolean())), OWL2_RL, l(UseOfIllegalAxiom.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATADEF,l(Integer(), Boolean(), FAKEDT), l(DatatypeDefinition(Boolean(), Integer()), DatatypeDefinition(FAKEDT, Integer()), DatatypeDefinition(Integer(), FAKEDT)), OWL2_DL, l(CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class)));
        toReturn.add(arg(os, IRI,    l(Class(RELATIVE_TEST)),                l(),   OWL2_FULL, l(UseOfNonAbsoluteIRI.class)));
        toReturn.add(arg(os, ANNP,   l(OWLTEST_OP, OWLTEST_DP, OWLTEST_AP),  l(SubAnnotationPropertyOf(APT, OWLTEST_AP)), OWL2_DL, l(UseOfReservedVocabularyForAnnotationPropertyIRI.class, UseOfUndeclaredAnnotationProperty.class, IllegalPunning.class, IllegalPunning.class)));
        toReturn.add(arg(os, CLA,    l(Class(OWL_TEST), FAKEDT),             l(ClassAssertion(Class(FAKEDT.getIRI()), AnonymousIndividual())), OWL2_DL, l(UseOfUndeclaredClass.class, DatatypeIRIAlsoUsedAsClassIRI.class)));
        toReturn.add(arg(os, DATADEF,l(OWLTEST_DT, Integer(), Boolean(), FAKEDT), l(DatatypeDefinition(OWLTEST_DT, Boolean()), DatatypeDefinition(Boolean(), OWLTEST_DT), DatatypeDefinition(FAKEDT, Integer()), DatatypeDefinition(Integer(), FAKEDT)), OWL2_DL, l(CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfUnknownDatatype.class, UseOfUnknownDatatype.class)));
        toReturn.add(arg(os, DISJ,  l(OP), l(DisjointClasses()), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DDISJ, l(DATAP, DATA_PROPERTY), l(DisjointDataProperties(DATAP, DATA_PROPERTY)), OWL2_RL, l()));
        toReturn.add(arg(os, DDISJ, l(DATAP, OTHER_DP), l(DisjointDataProperties(DATAP, OTHER_DP)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DUN,   l(CL), l(DisjointUnion(CL, OWLThing(), OWLNothing())), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DUN,   l(CL), l(DisjointUnion(CL, OWLThing(), OWLNothing())), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DUN,   l(CL), l(DisjointUnion(CL, OWLThing(), OWLNothing())), OWL2_RL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, EQDATA,l(DATAP, TEST_DP), l(EquivalentDataProperties(DATAP, TEST_DP)), OWL2_RL, l()));
        toReturn.add(arg(os, ASYMM, l(OP), l(AsymmetricObjectProperty(OP)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, ASYMM, l(OP), l(TransitiveObjectProperty(OP), AsymmetricObjectProperty(OP)), OWL2_DL, l(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class)));
        toReturn.add(arg(os, CLASS, l(OP), l(ClassAssertion(ObjectMinCardinality(1, OP, OWLThing()), NamedIndividual(IRI(t, "i")))), OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, EQUIV, l(OP), l(EquivalentClasses()), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, OFUNC, l(OP), l(FunctionalObjectProperty(OP)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, OFUNC, l(OP), l(FunctionalObjectProperty(OP)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, OFUNC, l(OP), l(TransitiveObjectProperty(OP), FunctionalObjectProperty(OP)), OWL2_DL, l(UseOfNonSimplePropertyInFunctionalPropertyAxiom.class)));
        toReturn.add(arg(os, DISJO, l(OP), l(TransitiveObjectProperty(OP), DisjointObjectProperties(OP)), OWL2_DL, l(InsufficientPropertyExpressions.class, UseOfNonSimplePropertyInDisjointPropertiesAxiom.class)));
        toReturn.add(arg(os, IFP,   l(OP), l(InverseFunctionalObjectProperty(OP)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, IFP,   l(OP), l(TransitiveObjectProperty(OP), InverseFunctionalObjectProperty(OP)), OWL2_DL, l(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class)));
        toReturn.add(arg(os, IRR,   l(OP), l(IrreflexiveObjectProperty(OP)), OWL2_QL, l()));
        toReturn.add(arg(os, IRR,   l(OP), l(TransitiveObjectProperty(OP), IrreflexiveObjectProperty(OP)), OWL2_DL, l(UseOfNonSimplePropertyInIrreflexivePropertyAxiom.class)));
        toReturn.add(arg(os, NOT,   l(OP), l(ObjectPropertyRange(OP, ObjectComplementOf(OWLNothing()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, SELF,  l(OP), l(TransitiveObjectProperty(OP), ObjectPropertyRange(OP, ObjectHasSelf(OP))), OWL2_DL, l(UseOfNonSimplePropertyInObjectHasSelf.class)));
        toReturn.add(arg(os, AND,   l(OP), l(ObjectPropertyRange(OP, ObjectIntersectionOf())), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, INV,   l(OP), l(SubObjectPropertyOf(OP, ObjectInverseOf(OP))), OWL2_EL, l(UseOfObjectPropertyInverse.class)));
        toReturn.add(arg(os, ONE,   l(OP), l(ObjectPropertyRange(OP, ObjectOneOf())), OWL2_DL, l(EmptyOneOfAxiom.class)));
        toReturn.add(arg(os, ONE,   l(OP), l(ObjectPropertyRange(OP, ObjectOneOf(I1, I2))), OWL2_EL, l(UseOfObjectOneOfWithMultipleIndividuals.class)));
        toReturn.add(arg(os, ODOM,  l(OP), l(ObjectPropertyDomain(OP, ObjectMinCardinality(1, OP, OWLThing()))), OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ODOM,  l(OP), l(ObjectPropertyDomain(OP, ObjectUnionOf(OWLNothing(), OWLThing()))), OWL2_QL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ORANGE,l(OP), l(ObjectPropertyRange(OP, ObjectMinCardinality(1, OP, OWLThing()))), OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ORANGE,l(OP), l(ObjectPropertyRange(OP, ObjectUnionOf(OWLNothing(), OWLThing()))), OWL2_QL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, OR,    l(OP), l(ObjectPropertyRange(OP, ObjectUnionOf())), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, OR,    l(OP), l(ObjectPropertyRange(OP, ObjectUnionOf(OWLThing(), OWLNothing()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, SUB,   l(OP), l(SubClassOf(ObjectComplementOf(OWLNothing()), ObjectUnionOf(OWLThing(), OWLNothing()))), OWL2_QL, l(UseOfNonSubClassExpression.class, UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, TRAN,  l(OP), l(TransitiveObjectProperty(OP)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DISJO, l(OP, op1), l(DisjointObjectProperties(op1, OP)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, HASKEY,l(CL), l(HasKey(CL)), OWL2_DL, l(InsufficientPropertyExpressions.class)));
        toReturn.add(arg(os, HASKEY,l(OP, CL), l(HasKey(CL, OP)), OWL2_EL, l()));
        toReturn.add(arg(os, HASKEY,l(OP, CL), l(HasKey(CL, OP)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, HASKEY,l(OP, CL), l(HasKey(ObjectComplementOf(CL), OP)), OWL2_RL, l(UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, ALL,   l(OP, CL), l(SubClassOf(CL, ObjectAllValuesFrom(OP, OWLThing()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OEX,   l(OP, CL), l(SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OEX,   l(OP, CL), l(TransitiveObjectProperty(OP), SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing()))), OWL2_DL, l(UseOfNonSimplePropertyInCardinalityRestriction.class)));
        toReturn.add(arg(os, OBJMAX,l(OP, CL), l(SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OBJMAX,l(OP, CL), l(TransitiveObjectProperty(OP), SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing()))), OWL2_DL, l(UseOfNonSimplePropertyInCardinalityRestriction.class)));
        toReturn.add(arg(os, OBJMIN,l(OP, CL), l(SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing()))), OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OBJMIN,l(OP, CL), l(TransitiveObjectProperty(OP), SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing()))), OWL2_DL, l(UseOfNonSimplePropertyInCardinalityRestriction.class)));
        toReturn.add(arg(os, DUN,   l(OP, CL, OTHERFAKECLASS), l(DisjointUnion(CL, OTHERFAKECLASS)), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, IFP,   l(P), l(InverseFunctionalObjectProperty(P)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, INVP,  l(P, op1), l(InverseObjectProperties(P, op1)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, IRR,   l(P), l(IrreflexiveObjectProperty(P)), OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, NEGD,  l(DATAP, I), l(NegativeDataPropertyAssertion(DATAP, I, _1)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, CLASS, l(OP, I), l(ClassAssertion(ObjectSomeValuesFrom(OP, OWLThing()), I)), OWL2_QL, l(UseOfNonAtomicClassExpression.class)));
        toReturn.add(arg(os, NEGO,  l(OP, I), l(NegativeObjectPropertyAssertion(OP, I, I)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, OBJP,  l(OWLTEST_OP, OWLTEST_DP, OWLTEST_AP), l(SubObjectPropertyOf(OP, OWLTEST_OP)), OWL2_DL, l(UseOfReservedVocabularyForObjectPropertyIRI.class, UseOfUndeclaredObjectProperty.class, IllegalPunning.class, IllegalPunning.class)));
        toReturn.add(arg(os, CHAIN, l(OP, op1), l(SubPropertyChainOf(l(OP, op1), OP)), OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, CHAIN, l(OP, op1), l(SubPropertyChainOf(l(op1), OP), SubPropertyChainOf(l(OP, op1, OP), OP), SubPropertyChainOf(l(OP, op1), OP), SubPropertyChainOf(l(op1, OP, op1, OP), OP)), OWL2_DL, l(InsufficientPropertyExpressions.class, UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class)));
        toReturn.add(arg(os, CHAIN, l(OP, op1, OPX, CL), l(ObjectPropertyRange(OP, CL), SubPropertyChainOf(l(op1, OPX), OP)), OWL2_EL, l(LastPropertyInChainNotInImposedRange.class)));
        toReturn.add(arg(os, SYMM,  l(P), l(SymmetricObjectProperty(P)), OWL2_EL, l(UseOfIllegalAxiom.class)));
//@formatter:on
        return toReturn;
    }
}
