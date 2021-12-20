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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
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

class OWLProfileTestCase extends TestBase {
    private static final List<OWLEntity> OP_OP1 = l(OBJPROPS.OP, OBJPROPS.op1);
    private static final List<OWLEntity> CL_L = l(CLASSES.CL);
    private static final List<OWLEntity> OP_L = l(OBJPROPS.OP);
    private static final List<OWLEntity> OP_CL = l(OBJPROPS.OP, CLASSES.CL);
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
    private static final OWLClassAssertionAxiom ANON_INDIVIDUAL =
        ClassAssertion(OWLThing(), AnonymousIndividual());
    private static final OWLLiteral LW2 = Literal("wrong", Datatype("urn:test:defineddatatype"));
    private static final OWLLiteral LW1 = Literal("wrong", OWL2Datatype.XSD_INTEGER);
    private static final SWRLRule SWRL_RULE =
        SWRLRule(Collections.emptyList(), Collections.emptyList());
    private static final OWLDataOneOf DATA_ONE_OF = DataOneOf(LITERALS.LIT_ONE, Literal(2));
    protected static final Comparator<Class<?>> comp = Comparator.comparing(Class::getSimpleName);
    private static final IRI RELATIVE_TEST = iri("test", "");
    private static final OWLDataPropertyRangeAxiom ILLEGAL_DATAP_RANGE_2 =
        DataPropertyRange(DATAPROPS.DATAP, DataComplementOf(Integer()));
    private static final OWLDataPropertyRangeAxiom ONEOF_DATAP_RANGE =
        DataPropertyRange(DATAPROPS.DATAP, DATA_ONE_OF);
    private static final OWLDataPropertyRangeAxiom ILLEGAL_DATAP_RANGE = DataPropertyRange(
        DATAPROPS.DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, LITERALS.LIT_ONE)));
    private static final OWLFunctionalDataPropertyAxiom DATAP_FUNCTIONAL =
        FunctionalDataProperty(DATAPROPS.DATAP);
    private static final IRI ONTO = iri("ontology");

    private static void checkInCollection(List<OWLProfileViolation> violations,
        List<Class<?>> inputList) {
        inputList.sort(comp);
        assertEquals(inputList, asList(violations.stream().map(Object::getClass).sorted(comp)));
    }

    void runAssert(OWLOntology ontology, OWLProfile profile, List<Class<?>> expectedViolations,
        String name) {
        List<OWLProfileViolation> violations = profile.checkOntology(ontology).getViolations();
        // assertEquals(expectedViolations.size(), violations.size(),
        // expectedViolations.toString());
        checkInCollection(violations, expectedViolations);
        ontology
            .applyChanges(violations.stream().flatMap(violation -> violation.repair().stream()));
        assertEquals(0, profile.checkOntology(ontology).getViolations().size(), name);
    }

    static OWLOntology os0() {
        try {
            return OWLManager.createOWLOntologyManager().createOntology(ONTO);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    static OWLOntology os1() {
        try {
            return OWLManager.createOWLOntologyManager()
                .createOntology(OntologyID(IRIS.OWL_TEST, iri(START, "test1")));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    static OWLOntology os2() {
        try {
            return OWLManager.createOWLOntologyManager().createOntology(
                df.getOWLOntologyID(Optional.of(RELATIVE_TEST), Optional.of(iri("test1", ""))));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @ParameterizedTest
    @MethodSource("getData")
    void should(Supplier<OWLOntology> onts, String name, List<OWLEntity> entities,
        List<OWLAxiom> axioms, Profiles p, List<Class<?>> exceptions) {
        OWLOntology o = onts.get();
        entities.stream().forEach(entity -> o.add(Declaration(entity)));
        o.add(axioms);
        runAssert(o, p, exceptions, name);
    }

    static Object[] arg(Supplier<OWLOntology> supplier, String name, List<OWLEntity> entities,
        List<OWLAxiom> a, Profiles p, List<Class<?>> exceptions) {
        return new Object[] {supplier, name, entities, a, p, exceptions};
    }

    static Supplier<OWLOntology> os = OWLProfileTestCase::os0;
    static Supplier<OWLOntology> o1 = OWLProfileTestCase::os1;
    static Supplier<OWLOntology> o2 = OWLProfileTestCase::os2;


    private static OWLDataFactory DFLIST = OWLManager
        .getOWLDataFactory(new OntologyConfigurator().withAllowDuplicatesInConstructSets(true));

    static Collection<Object[]> getData() {
        List<Object[]> toReturn = new ArrayList<>();
        //@formatter:off
        toReturn.add(arg(o1, ONT,      l(),                   l(),                                                                              OWL2_DL,      Arrays.asList(UseOfReservedVocabularyForOntologyIRI.class, UseOfReservedVocabularyForVersionIRI.class)));
        toReturn.add(arg(o2, ONT,      l(),                   l(),                                                                              OWL2_FULL,    Arrays.asList(OntologyIRINotAbsolute.class, OntologyVersionIRINotAbsolute.class)));
        toReturn.add(arg(os, ANON,     l(),                   l(ANON_INDIVIDUAL),                                                               OWL2_EL,      l(UseOfAnonymousIndividual.class)));
        toReturn.add(arg(os, ANON,     l(),                   l(ANON_INDIVIDUAL),                                                               OWL2_QL,      l(UseOfAnonymousIndividual.class)));
        toReturn.add(arg(os, DATAPR,   l(),                   l(DATAP_FUNCTIONAL),                                                              OWL2_DL,      l(UseOfUndeclaredDataProperty.class)));
        toReturn.add(arg(os, DATADEF,  l(),                   l(DatatypeDefinition(DATATYPES.FAKEDT, Boolean())),                               OWL2_FULL,    l(UseOfUndeclaredDatatype.class)));
        toReturn.add(arg(os, DIFF,     l(),                   l(DifferentIndividuals(INDIVIDUALS.I0)),                                          OWL2_DL,      l(InsufficientIndividuals.class)));
        toReturn.add(arg(os, DISJ,     l(),                   l(DISJOINT_TOP),                                                                  OWL2_QL,      l(UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, DISJ,     l(),                   l(DISJOINT_TOP),                                                                  OWL2_RL,      Arrays.asList(UseOfNonSubClassExpression.class, UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, DDISJ,    l(),                   l(DisjointDataProperties(DATAPROPS.DATAP)),                                       OWL2_DL,      l(InsufficientPropertyExpressions.class, UseOfUndeclaredDataProperty.class)));
        toReturn.add(arg(os, EQUIV,    l(),                   l(EquivalentClasses(ObjectComplementOf(OWLThing()), OWLNothing())),               OWL2_RL,    l(UseOfNonEquivalentClassExpression.class)));
        toReturn.add(arg(os, EQUIV,    l(),                   l(EquivalentClasses(ObjectUnionOf(OWLNothing(), OWLThing()), OWLNothing())),      OWL2_QL,    l(UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, EQDATA,   l(),                   l(EquivalentDataProperties(DATAPROPS.DATAP)),                                     OWL2_DL,    l(InsufficientPropertyExpressions.class, UseOfUndeclaredDataProperty.class)));
        toReturn.add(arg(os, EQOBJ,    l(),                   l(EquivalentObjectProperties(OBJPROPS.OP)),                                       OWL2_DL,    l(InsufficientPropertyExpressions.class, UseOfUndeclaredObjectProperty.class)));
        toReturn.add(arg(os, IND,      l(),                   l(ClassAssertion(OWLThing(), INDIVIDUALS.I3)),                                    OWL2_DL,    l(UseOfReservedVocabularyForIndividualIRI.class)));
        toReturn.add(arg(os, SAME,     l(),                   l(SameIndividual(INDIVIDUALS.I0)),                                                OWL2_DL, l(InsufficientIndividuals.class)));
        toReturn.add(arg(os, SAME,     l(),                   l(SameIndividual(INDIVIDUALS.I1, INDIVIDUALS.I2)),                                OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, SUB,      l(),                   l(SubClassOf(ObjectComplementOf(OWLThing()), ObjectOneOf(INDIVIDUALS.I4))),       OWL2_RL, l(UseOfNonSubClassExpression.class, UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, SUBD,     l(),                   l(SubDataPropertyOf(TopDataProperty(), TopDataProperty())),                       OWL2_DL, l(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class)));
        toReturn.add(arg(os, RULE,     l(),                   l(SWRL_RULE),                                                                     OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, RULE,     l(),                   l(SWRL_RULE),                                                                     OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, RULE,     l(),                   l(SWRL_RULE),                                                                     OWL2_RL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DATANOT,  l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataComplementOf(Double()))),                OWL2_EL, Arrays.asList(UseOfIllegalDataRange.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATANOT,  l(DATAPROPS.DATAP),    l(ILLEGAL_DATAP_RANGE_2),                                                         OWL2_QL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATANOT,  l(DATAPROPS.DATAP),    l(ILLEGAL_DATAP_RANGE_2),                                                         OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAAND,  l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataIntersectionOf(DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, Literal(1)))))), OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DATAAND,  l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataIntersectionOf(Integer(), Boolean()))),  OWL2_RL, l()));
        toReturn.add(arg(os, DATAONE,  l(DATAPROPS.DATAP),    l(ONEOF_DATAP_RANGE),                                                             OWL2_EL, l(UseOfDataOneOfWithMultipleLiterals.class)));
        toReturn.add(arg(os, DATAONE,  l(DATAPROPS.DATAP),    l(ONEOF_DATAP_RANGE),                                                             OWL2_QL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAONE,  l(DATAPROPS.DATAP),    l(ONEOF_DATAP_RANGE),                                                             OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAONE,  l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataOneOf())),                               OWL2_DL, l(EmptyOneOfAxiom.class)));
        toReturn.add(arg(os, DATAREST, l(DATAPROPS.DATAP),    l(ILLEGAL_DATAP_RANGE),                                                           OWL2_EL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAREST, l(DATAPROPS.DATAP),    l(ILLEGAL_DATAP_RANGE),                                                           OWL2_QL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAREST, l(DATAPROPS.DATAP),    l(ILLEGAL_DATAP_RANGE),                                                           OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAREST, l(DATAPROPS.DATAP),    l(DatatypeDefinition(Integer(), Boolean()), DatatypeDefinition(DATATYPES.FAKEDT, Boolean()), DataPropertyRange(DATAPROPS.DATAP, DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LANG_RANGE, LITERALS.LIT_ONE)))), OWL2_FULL, l(UseOfDefinedDatatypeInDatatypeRestriction.class, UseOfIllegalFacetRestriction.class, UseOfUndeclaredDatatype.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataUnionOf(Double()))),                     OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataUnionOf(Double(), Integer()))),          OWL2_EL, Arrays.asList(UseOfIllegalDataRange.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataUnionOf(Double(), Integer()))),          OWL2_RL, l(UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, DATAOR,   l(DATAPROPS.DATAP),    l(DataPropertyRange(DATAPROPS.DATAP, DataUnionOf(Integer(), Boolean()))),         OWL2_QL, Arrays.asList(UseOfIllegalDataRange.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, FDATA,    l(DATAPROPS.DATAP),    l(DATAP_FUNCTIONAL),                                                              OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, FDATA,    l(DATAPROPS.DATAP),    l(DATAP_FUNCTIONAL),                                                              OWL2_RL, l()));
        toReturn.add(arg(os, LIT,      l(DATAPROPS.DATAP),    l(DataPropertyAssertion(DATAPROPS.DATAP, AnonymousIndividual(), LW1)),            OWL2_FULL, l(LexicalNotInLexicalSpace.class)));
        toReturn.add(arg(os, LIT,      l(DATAPROPS.DATAP),    l(DataPropertyAssertion(DATAPROPS.DATAP, AnonymousIndividual(), LW2)),            OWL2_FULL, l(UseOfDefinedDatatypeInLiteral.class)));
        toReturn.add(arg(os, DATAPR,   l(DATAPROPS.DT_FAIL),    l(),                                                                            OWL2_DL, l()));
        toReturn.add(arg(os, DAT,      l(Boolean()),            l(),                                                                            OWL2_EL, l()));
        toReturn.add(arg(os, DAT,      l(DATATYPES.DTT),        l(),                                                                            OWL2_RL, l()));
        toReturn.add(arg(os, DAT,      l(DATATYPES.FAKEDT),     l(),                                                                            OWL2_QL, l()));
        toReturn.add(arg(os, DATADEF,  l(DATATYPES.DType),      l(DatatypeDefinition(DATATYPES.DType, Boolean())),                              OWL2_RL, l(UseOfIllegalAxiom.class, UseOfIllegalDataRange.class)));
        toReturn.add(arg(os, IRI,      l(Class(RELATIVE_TEST)), l(),                                                                            OWL2_FULL, l(UseOfNonAbsoluteIRI.class)));
        toReturn.add(arg(os, DUN,      CL_L,  l(DisjointUnion(CLASSES.CL, OWLThing(), OWLNothing())),                                           OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DUN,      CL_L,  l(DisjointUnion(CLASSES.CL, OWLThing(), OWLNothing())),                                           OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DUN,      CL_L,  l(DisjointUnion(CLASSES.CL, OWLThing(), OWLNothing())),                                           OWL2_RL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, ASYMM,    OP_L,  l(AsymmetricObjectProperty(OBJPROPS.OP)),                                                         OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, ASYMM,    OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP), AsymmetricObjectProperty(OBJPROPS.OP)),                  OWL2_DL, l(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class)));
        toReturn.add(arg(os, CLASS,    OP_L,  l(ClassAssertion(ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()), INDIVIDUALS.I)),               OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, EQUIV,    OP_CL, l(DFLIST.getOWLEquivalentClassesAxiom(CLASSES.CL)),                                               OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, OFUNC,    OP_L,  l(FunctionalObjectProperty(OBJPROPS.OP)),                                                         OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, OFUNC,    OP_L,  l(FunctionalObjectProperty(OBJPROPS.OP)),                                                         OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, OFUNC,    OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP), FunctionalObjectProperty(OBJPROPS.OP)),                  OWL2_DL, l(UseOfNonSimplePropertyInFunctionalPropertyAxiom.class)));
        toReturn.add(arg(os, DISJO,    OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP), DisjointObjectProperties(OBJPROPS.OP)),                  OWL2_DL, l(InsufficientPropertyExpressions.class, UseOfNonSimplePropertyInDisjointPropertiesAxiom.class)));
        toReturn.add(arg(os, IFP,      OP_L,  l(InverseFunctionalObjectProperty(OBJPROPS.OP)),                                                  OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, IFP,      OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP), InverseFunctionalObjectProperty(OBJPROPS.OP)),           OWL2_DL, l(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class)));
        toReturn.add(arg(os, IRR,      OP_L,  l(IrreflexiveObjectProperty(OBJPROPS.OP)),                                                        OWL2_QL, l()));
        toReturn.add(arg(os, IRR,      OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP), IrreflexiveObjectProperty(OBJPROPS.OP)),                 OWL2_DL, l(UseOfNonSimplePropertyInIrreflexivePropertyAxiom.class)));
        toReturn.add(arg(os, NOT,      OP_L,  l(ObjectPropertyRange(OBJPROPS.OP, ObjectComplementOf(OWLNothing()))),                            OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, AND,      OP_CL, l(ObjectPropertyRange(OBJPROPS.OP, ObjectIntersectionOf(CLASSES.CL))),                            OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, INV,      OP_L,  l(SubObjectPropertyOf(OBJPROPS.OP, ObjectInverseOf(OBJPROPS.OP))),                                OWL2_EL, l(UseOfObjectPropertyInverse.class)));
        toReturn.add(arg(os, ONE,      OP_L,  l(ObjectPropertyRange(OBJPROPS.OP, ObjectOneOf())),                                               OWL2_DL, l(EmptyOneOfAxiom.class)));
        toReturn.add(arg(os, ONE,      OP_L,  l(ObjectPropertyRange(OBJPROPS.OP, ObjectOneOf(INDIVIDUALS.I1, INDIVIDUALS.I2))),                 OWL2_EL, l(UseOfObjectOneOfWithMultipleIndividuals.class)));
        toReturn.add(arg(os, ODOM,     OP_L,  l(ObjectPropertyDomain(OBJPROPS.OP, ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()))),           OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ODOM,     OP_L,  l(ObjectPropertyDomain(OBJPROPS.OP, ObjectUnionOf(OWLNothing(), OWLThing()))),                    OWL2_QL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ORANGE,   OP_L,  l(ObjectPropertyRange(OBJPROPS.OP, ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()))),            OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ORANGE,   OP_L,  l(ObjectPropertyRange(OBJPROPS.OP, ObjectUnionOf(OWLNothing(), OWLThing()))),                     OWL2_QL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, OR,       OP_CL, l(ObjectPropertyRange(OBJPROPS.OP, ObjectUnionOf(CLASSES.CL))),                                   OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, OR,       OP_L,  l(ObjectPropertyRange(OBJPROPS.OP, ObjectUnionOf(OWLThing(), OWLNothing()))),                     OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, SUB,      OP_L,  l(SubClassOf(ObjectComplementOf(OWLNothing()), ObjectUnionOf(OWLThing(), OWLNothing()))),         OWL2_QL, l(UseOfNonSubClassExpression.class, UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, TRAN,     OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP)),                                                         OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, HASKEY,   CL_L,  l(HasKey(CLASSES.CL)),                                                                            OWL2_DL, l(InsufficientPropertyExpressions.class)));
        toReturn.add(arg(os, DISJO,    OP_OP1,l(DisjointObjectProperties(OBJPROPS.op1, OBJPROPS.OP)),                                           OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, HASKEY,   OP_CL, l(HasKey(CLASSES.CL, OBJPROPS.OP)),                                                               OWL2_EL, l()));
        toReturn.add(arg(os, HASKEY,   OP_CL, l(HasKey(CLASSES.CL, OBJPROPS.OP)),                                                               OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, HASKEY,   OP_CL, l(HasKey(ObjectComplementOf(CLASSES.CL), OBJPROPS.OP)),                                           OWL2_RL, l(UseOfNonSubClassExpression.class)));
        toReturn.add(arg(os, ALL,      OP_CL, l(SubClassOf(CLASSES.CL, ObjectAllValuesFrom(OBJPROPS.OP, OWLThing()))),                          OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OEX,      OP_CL, l(SubClassOf(CLASSES.CL, ObjectExactCardinality(1, OBJPROPS.OP, OWLThing()))),                    OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OBJMAX,   OP_CL, l(SubClassOf(CLASSES.CL, ObjectMaxCardinality(1, OBJPROPS.OP, OWLThing()))),                      OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, OBJMIN,   OP_CL, l(SubClassOf(CLASSES.CL, ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()))),                      OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, IFP,      l(OBJPROPS.P),   l(InverseFunctionalObjectProperty(OBJPROPS.P)),                                         OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, IRR,      l(OBJPROPS.P),   l(IrreflexiveObjectProperty(OBJPROPS.P)),                                               OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, SYMM,     l(OBJPROPS.P),   l(SymmetricObjectProperty(OBJPROPS.P)),                                                 OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, INVP,     l(OBJPROPS.P, OBJPROPS.op1),               l(InverseObjectProperties(OBJPROPS.P, OBJPROPS.op1)),         OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DATAPR,   l(DATAPROPS.DATAP, ANNPROPS.APP),          l(),                                                          OWL2_DL, l()));
        toReturn.add(arg(os, DATAPR,   l(DATAPROPS.DATAP, OBJPROPS.OPP),          l(),                                                          OWL2_DL, l()));
        toReturn.add(arg(os, SELF,     OP_L,  l(TransitiveObjectProperty(OBJPROPS.OP), ObjectPropertyRange(OBJPROPS.OP, ObjectHasSelf(OBJPROPS.OP))),               OWL2_DL, l(UseOfNonSimplePropertyInObjectHasSelf.class)));
        toReturn.add(arg(os, OEX,      OP_CL, l(TransitiveObjectProperty(OBJPROPS.OP), SubClassOf(CLASSES.CL, ObjectExactCardinality(1, OBJPROPS.OP, OWLThing()))), OWL2_DL, l(UseOfNonSimplePropertyInCardinalityRestriction.class)));
        toReturn.add(arg(os, OBJMAX,   OP_CL, l(TransitiveObjectProperty(OBJPROPS.OP), SubClassOf(CLASSES.CL, ObjectMaxCardinality(1, OBJPROPS.OP, OWLThing()))),   OWL2_DL, l(UseOfNonSimplePropertyInCardinalityRestriction.class)));
        toReturn.add(arg(os, OBJMIN,   OP_CL, l(TransitiveObjectProperty(OBJPROPS.OP), SubClassOf(CLASSES.CL, ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()))),   OWL2_DL, l(UseOfNonSimplePropertyInCardinalityRestriction.class)));
        toReturn.add(arg(os, DALL,     l(DATAPROPS.DATAP, CLASSES.CL),            l(SubClassOf(CLASSES.CL, DataAllValuesFrom(DATAPROPS.DATAP, Integer()))),         OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DEX,      l(DATAPROPS.DATAP, CLASSES.CL, Integer()), l(SubClassOf(CLASSES.CL, DataExactCardinality(1, DATAPROPS.DATAP, Integer()))),   OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DMAX,     l(DATAPROPS.DATAP, CLASSES.CL, Integer()), l(SubClassOf(CLASSES.CL, DataMaxCardinality(1, DATAPROPS.DATAP, Integer()))),     OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, DMIN,     l(DATAPROPS.DATAP, CLASSES.CL, Integer()), l(SubClassOf(CLASSES.CL, DataMinCardinality(1, DATAPROPS.DATAP, Integer()))),     OWL2_EL, l(UseOfIllegalClassExpression.class)));
        toReturn.add(arg(os, EQDATA,   l(DATAPROPS.DATAP, DATAPROPS.TEST_DTP),  l(EquivalentDataProperties(DATAPROPS.DATAP, DATAPROPS.TEST_DTP)),                   OWL2_RL, l()));
        toReturn.add(arg(os, NEGD,     l(DATAPROPS.DATAP, INDIVIDUALS.I0),      l(NegativeDataPropertyAssertion(DATAPROPS.DATAP, INDIVIDUALS.I0, LITERALS.LIT_ONE)),OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, CLASS,    l(OBJPROPS.OP, INDIVIDUALS.I0),          l(ClassAssertion(ObjectSomeValuesFrom(OBJPROPS.OP, OWLThing()), INDIVIDUALS.I0)),   OWL2_QL, l(UseOfNonAtomicClassExpression.class)));
        toReturn.add(arg(os, NEGO,     l(OBJPROPS.OP, INDIVIDUALS.I0),          l(NegativeObjectPropertyAssertion(OBJPROPS.OP, INDIVIDUALS.I0, INDIVIDUALS.I0)),    OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, CHAIN,    OP_OP1,          l(SubPropertyChainOf(l(OBJPROPS.OP, OBJPROPS.op1), OBJPROPS.OP)),                                           OWL2_QL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, CLA,      l(Class(IRIS.OWL_TEST), DATATYPES.FAKEDT), l(ClassAssertion(CLASSES.FAKECLASS, AnonymousIndividual())),                              OWL2_DL, l(UseOfUndeclaredClass.class, DatatypeIRIAlsoUsedAsClassIRI.class)));
        toReturn.add(arg(os, DISJ,     OP_CL, l(DFLIST.getOWLDisjointClassesAxiom(CLASSES.CL)),                                                                             OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DDISJ,    l(DATAPROPS.DATAP, DATAPROPS.DPP), l(DisjointDataProperties(DATAPROPS.DATAP, DATAPROPS.DPP)),                                        OWL2_RL, l()));
        toReturn.add(arg(os, DDISJ,    l(DATAPROPS.DATAP, DATAPROPS.OTHER_DP), l(DisjointDataProperties(DATAPROPS.DATAP, DATAPROPS.OTHER_DP)),                              OWL2_EL, l(UseOfIllegalAxiom.class)));
        toReturn.add(arg(os, DUN,      l(OBJPROPS.OP, CLASSES.CL, CLASSES.OTHERFAKECLASS), l(DisjointUnion(CLASSES.CL, CLASSES.OTHERFAKECLASS)),                            OWL2_DL, l(InsufficientOperands.class)));
        toReturn.add(arg(os, DAT,      l(DATATYPES.DT3, DATATYPES.FAKEDT, CLASSES.FAKECLASS, DATAPROPS.DATAP), l(DataPropertyRange(DATAPROPS.DATAP, DATATYPES.FAKEDT2)),    OWL2_DL, l(UseOfUndeclaredDatatype.class)));
        toReturn.add(arg(os, OBJP,     l(OBJPROPS.OWLTEST_OP, DATAPROPS.OWLTEST_DP, ANNPROPS.OWLTEST_AP), l(SubObjectPropertyOf(OBJPROPS.OP, OBJPROPS.OWLTEST_OP)),         OWL2_DL, Arrays.asList(UseOfReservedVocabularyForObjectPropertyIRI.class, UseOfUndeclaredObjectProperty.class, IllegalPunning.class, IllegalPunning.class)));
        toReturn.add(arg(os, DDOM,     l(DATAPROPS.DATAP, OBJPROPS.OP),           l(DataPropertyDomain(DATAPROPS.DATAP, ObjectMaxCardinality(1, OBJPROPS.OP, OWLNothing()))),   OWL2_QL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, DDOM,     l(DATAPROPS.DATAP, OBJPROPS.OP),           l(DataPropertyDomain(DATAPROPS.DATAP, ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()))),     OWL2_RL, l(UseOfNonSuperClassExpression.class)));
        toReturn.add(arg(os, ANNP,     l(OBJPROPS.OWLTEST_OP, DATAPROPS.OWLTEST_DP, ANNPROPS.OWLTEST_AP),  l(SubAnnotationPropertyOf(ANNPROPS.APT, ANNPROPS.OWLTEST_AP)),       OWL2_DL, Arrays.asList(UseOfReservedVocabularyForAnnotationPropertyIRI.class, UseOfUndeclaredAnnotationProperty.class, IllegalPunning.class, IllegalPunning.class)));
        toReturn.add(arg(os, CHAIN,    l(OBJPROPS.OP, OBJPROPS.op1, OBJPROPS.op2, CLASSES.CL), l(ObjectPropertyRange(OBJPROPS.OP, CLASSES.CL), SubPropertyChainOf(l(OBJPROPS.op1, OBJPROPS.op2), OBJPROPS.OP)), OWL2_EL, l(LastPropertyInChainNotInImposedRange.class)));
        toReturn.add(arg(os, DATADEF,  l(Integer(), Boolean(), DATATYPES.FAKEDT), l(DatatypeDefinition(Boolean(), Integer()), DatatypeDefinition(DATATYPES.FAKEDT, Integer()), DatatypeDefinition(Integer(), DATATYPES.FAKEDT)), OWL2_DL, Arrays.asList(CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class)));
        toReturn.add(arg(os, DATADEF,  l(DATATYPES.OWLTEST_DT, Integer(), Boolean(), DATATYPES.FAKEDT), l(DatatypeDefinition(DATATYPES.OWLTEST_DT, Boolean()), DatatypeDefinition(Boolean(), DATATYPES.OWLTEST_DT), DatatypeDefinition(DATATYPES.FAKEDT, Integer()), DatatypeDefinition(Integer(), DATATYPES.FAKEDT)), OWL2_DL, Arrays.asList(CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfUnknownDatatype.class, UseOfUnknownDatatype.class)));
        toReturn.add(arg(os, CHAIN,    OP_OP1,          l(SubPropertyChainOf(l(OBJPROPS.op1), OBJPROPS.OP), SubPropertyChainOf(Arrays.asList(OBJPROPS.OP, OBJPROPS.op1, OBJPROPS.OP), OBJPROPS.OP), SubPropertyChainOf(l(OBJPROPS.OP, OBJPROPS.op1), OBJPROPS.OP), SubPropertyChainOf(Arrays.asList(OBJPROPS.op1, OBJPROPS.OP, OBJPROPS.op1, OBJPROPS.OP), OBJPROPS.OP)), OWL2_DL, Arrays.asList(InsufficientPropertyExpressions.class, UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class)));
        //@formatter:on
        return toReturn;
    }
}
