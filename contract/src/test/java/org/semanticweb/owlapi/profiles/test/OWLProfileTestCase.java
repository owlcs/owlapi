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
package org.semanticweb.owlapi.profiles.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

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
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2Profile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.OWLProfileViolationVisitorAdapter;
import org.semanticweb.owlapi.profiles.OWLProfileViolationVisitorExAdapter;
import org.semanticweb.owlapi.profiles.violations.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

import com.google.common.base.Optional;

@SuppressWarnings({ "javadoc", "rawtypes", })
public class OWLProfileTestCase extends TestBase {

    @Nonnull private static final String START = OWLThing().getIRI().getNamespace();
    @Nonnull private static final OWLClass CL = Class(IRI("urn:test#fakeclass"));
    @Nonnull private static final OWLDataProperty DATAP = DataProperty(IRI("urn:datatype#fakedatatypeproperty"));
    @Nonnull private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE2 = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LANG_RANGE, Literal(1))));
    @Nonnull private static final OWLDataPropertyRangeAxiom DATA_PROPERTY_RANGE = DataPropertyRange(DATAP,
        DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MAX_EXCLUSIVE, Literal(1))));
    @Nonnull private static final OWLObjectProperty OP = ObjectProperty(IRI("urn:datatype#fakeobjectproperty"));
    @Nonnull private static final OWLDatatype UNKNOWNFAKEDATATYPE = Datatype(IRI(START + "unknownfakedatatype"));
    @Nonnull private static final OWLDatatype FAKEUNDECLAREDDATATYPE = Datatype(IRI(
        "urn:datatype#fakeundeclareddatatype"));
    @Nonnull private static final OWLDatatype FAKEDATATYPE = Datatype(IRI("urn:datatype#fakedatatype"));
    @Nonnull private static final IRI onto = IRI.create("urn:test#ontology");
    @Nonnull private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();
    @Nonnull private static final OWLObjectProperty P = ObjectProperty(IRI("urn:test#objectproperty"));

    public void declare(@Nonnull OWLOntology o, @Nonnull OWLEntity... entities) {
        for (OWLEntity e : entities) {
            assert e != null;
            m.addAxiom(o, Declaration(e));
        }
    }

    @Nonnull Comparator<Class> comp = new Comparator<Class>() {

        @Override
        public int compare(Class o1, Class o2) {
            return o1.getSimpleName().compareTo(o2.getSimpleName());
        }
    };

    public void checkInCollection(@Nonnull List<OWLProfileViolation> violations, Class[] inputList) {
        List<Class> list = new ArrayList<>(Arrays.asList(inputList));
        List<Class> list1 = new ArrayList<>();
        for (OWLProfileViolation v : violations) {
            list1.add(v.getClass());
        }
        Collections.sort(list, comp);
        Collections.sort(list1, comp);
        assertEquals(list1.toString(), list, list1);
    }

    public void runAssert(@Nonnull OWLOntology o, @Nonnull OWLProfile profile, int expected,
        Class[] expectedViolations) {
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertEquals(expected, violations.size());
        checkInCollection(violations, expectedViolations);
        for (OWLProfileViolation violation : violations) {
            o.getOWLOntologyManager().applyChanges(violation.repair());
            violation.accept(new OWLProfileViolationVisitorAdapter());
            violation.accept(new OWLProfileViolationVisitorExAdapter<String>() {

                @Override
                protected String doDefault(@Nonnull OWLProfileViolation object) {
                    return object.toString();
                }
            });
        }
        violations = profile.checkOntology(o).getViolations();
        assertEquals(0, violations.size());
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype datatype)")
    public void shouldCreateViolationForOWLDatatypeInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, UNKNOWNFAKEDATATYPE, FAKEDATATYPE, Class(FAKEDATATYPE.getIRI()), DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, FAKEUNDECLAREDDATATYPE));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfUndeclaredDatatype.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, Integer(), Boolean(), FAKEDATATYPE);
        m.addAxiom(o, DatatypeDefinition(Boolean(), Integer()));
        m.addAxiom(o, DatatypeDefinition(FAKEDATATYPE, Integer()));
        m.addAxiom(o, DatatypeDefinition(Integer(), FAKEDATATYPE));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 4;
        Class[] expectedViolations = { CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2DLProfileCycles() throws Exception {
        OWLOntology o = createOnto();
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLDatatype d = Datatype(IRI(START + "test"));
        declare(o, d, Integer(), Boolean(), FAKEDATATYPE);
        m.addAxiom(o, DatatypeDefinition(d, Boolean()));
        m.addAxiom(o, DatatypeDefinition(Boolean(), d));
        m.addAxiom(o, DatatypeDefinition(FAKEDATATYPE, Integer()));
        m.addAxiom(o, DatatypeDefinition(Integer(), FAKEDATATYPE));
        int expected = 9;
        Class[] expectedViolations = { CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class,
            CycleInDatatypeDefinition.class, CycleInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfBuiltInDatatypeInDatatypeDefinition.class,
            UseOfBuiltInDatatypeInDatatypeDefinition.class, UseOfUnknownDatatype.class, UseOfUnknownDatatype.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectProperty property)")
    public void shouldCreateViolationForOWLObjectPropertyInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        IRI iri = IRI(START + "test");
        declare(o, ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        m.addAxiom(o, SubObjectPropertyOf(OP, ObjectProperty(iri)));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 4;
        Class[] expectedViolations = { UseOfReservedVocabularyForObjectPropertyIRI.class,
            UseOfUndeclaredObjectProperty.class, IllegalPunning.class, IllegalPunning.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile1() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DataProperty(IRI(START + "fail")));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile2() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, FunctionalDataProperty(DATAP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfUndeclaredDataProperty.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile3() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, AnnotationProperty(DATAP.getIRI()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataProperty property)")
    public void shouldCreateViolationForOWLDataPropertyInOWL2DLProfile4() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, ObjectProperty(DATAP.getIRI()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLAnnotationProperty property)")
    public void shouldCreateViolationForOWLAnnotationPropertyInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        IRI iri = IRI(START + "test");
        declare(o, ObjectProperty(iri), DataProperty(iri), AnnotationProperty(iri));
        m.addAxiom(o, SubAnnotationPropertyOf(AnnotationProperty(IRI("urn:test#t")), AnnotationProperty(iri)));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 4;
        Class[] expectedViolations = { UseOfReservedVocabularyForAnnotationPropertyIRI.class,
            UseOfUndeclaredAnnotationProperty.class, IllegalPunning.class, IllegalPunning.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLOntology ontology)")
    public void shouldCreateViolationForOWLOntologyInOWL2DLProfile() throws Exception {
        OWLOntology o = m.createOntology(new OWLOntologyID(Optional.of(IRI(START + "test")), Optional.of(IRI(START
            + "test1"))));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfReservedVocabularyForOntologyIRI.class,
            UseOfReservedVocabularyForVersionIRI.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLClass desc)")
    public void shouldCreateViolationForOWLClassInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, Class(IRI(START + "test")), FAKEDATATYPE);
        m.addAxiom(o, ClassAssertion(Class(FAKEDATATYPE.getIRI()), AnonymousIndividual()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfUndeclaredClass.class, DatatypeIRIAlsoUsedAsClassIRI.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataOneOf()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { EmptyOneOfAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataUnionOf()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataIntersectionOf node)")
    public void shouldCreateViolationForOWLDataIntersectionOfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataIntersectionOf()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectIntersectionOf node)")
    public void shouldCreateViolationForOWLObjectIntersectionOfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectIntersectionOf()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectOneOf node)")
    public void shouldCreateViolationForOWLObjectOneOfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectOneOf()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { EmptyOneOfAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectUnionOf node)")
    public void shouldCreateViolationForOWLObjectUnionOfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectUnionOf()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentClassesAxiom node)")
    public void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, EquivalentClasses());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointClassesAxiom node)")
    public void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, DisjointClasses());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom node)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        OWLClass otherfakeclass = Class(IRI("urn:test#otherfakeclass"));
        declare(o, CL);
        declare(o, otherfakeclass);
        m.addAxiom(o, DisjointUnion(CL, otherfakeclass));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientOperands.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentObjectPropertiesAxiom node)")
    public void shouldCreateViolationForOWLEquivalentObjectPropertiesAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, EquivalentObjectProperties());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientPropertyExpressions.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointDataPropertiesAxiom node)")
    public void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DisjointDataProperties());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientPropertyExpressions.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentDataPropertiesAxiom node)")
    public void shouldCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, EquivalentDataProperties());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientPropertyExpressions.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom node)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL);
        m.addAxiom(o, HasKey(CL));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientPropertyExpressions.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSameIndividualAxiom node)")
    public void shouldCreateViolationForOWLSameIndividualAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, SameIndividual());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientIndividuals.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDifferentIndividualsAxiom node)")
    public void shouldCreateViolationForOWLDifferentIndividualsAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DifferentIndividuals());
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { InsufficientIndividuals.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLNamedIndividual individual)")
    public void shouldCreateViolationForOWLNamedIndividualInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, ClassAssertion(OWLThing(), NamedIndividual(IRI(START + 'i'))));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfReservedVocabularyForIndividualIRI.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubDataPropertyOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubDataPropertyOfAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, SubDataPropertyOf(DF.getOWLTopDataProperty(), DF.getOWLTopDataProperty()));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMinCardinality desc)")
    public void shouldCreateViolationForOWLObjectMinCardinalityInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing())));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInCardinalityRestriction.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMaxCardinality desc)")
    public void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing())));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInCardinalityRestriction.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectExactCardinality desc)")
    public void shouldCreateViolationForOWLObjectExactCardinalityInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing())));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInCardinalityRestriction.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectHasSelf desc)")
    public void shouldCreateViolationForOWLObjectHasSelfInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectHasSelf(OP)));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInObjectHasSelf.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, FunctionalObjectProperty(OP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInFunctionalPropertyAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, InverseFunctionalObjectProperty(OP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, IrreflexiveObjectProperty(OP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInIrreflexivePropertyAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLAsymmetricObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, AsymmetricObjectProperty(OP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointObjectPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        m.addAxiom(o, DisjointObjectProperties(OP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 2;
        Class[] expectedViolations = { InsufficientPropertyExpressions.class,
            UseOfNonSimplePropertyInDisjointPropertiesAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubPropertyChainOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2DLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#op"));
        declare(o, OP, op1);
        m.addAxiom(o, SubPropertyChainOf(Arrays.asList(op1), OP));
        m.addAxiom(o, SubPropertyChainOf(Arrays.asList(OP, op1, OP), OP));
        m.addAxiom(o, SubPropertyChainOf(Arrays.asList(OP, op1), OP));
        m.addAxiom(o, SubPropertyChainOf(Arrays.asList(op1, OP, op1, OP), OP));
        OWL2DLProfile profile = new OWL2DLProfile();
        int expected = 4;
        Class[] expectedViolations = { InsufficientPropertyExpressions.class, UseOfPropertyInChainCausesCycle.class,
            UseOfPropertyInChainCausesCycle.class, UseOfPropertyInChainCausesCycle.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    // With the change in OWLOntologyID to stop use of relative IRIs, this test is no longer applicable. A warning will be logged.
    // @Test
    @Tests(method = "public Object visit(OWLOntology ont)")
    public void shouldCreateViolationForOWLOntologyInOWL2Profile() throws Exception {
        OWLOntology o = m.createOntology(new OWLOntologyID(Optional.of(IRI("test")), Optional.of(IRI("test1"))));
        OWL2Profile profile = new OWL2Profile();
        int expected = 2;
        Class[] expectedViolations = { OntologyIRINotAbsolute.class, OntologyVersionIRINotAbsolute.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(IRI iri)")
    public void shouldCreateViolationForIRIInOWL2Profile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, Class(IRI("test")));
        OWL2Profile profile = new OWL2Profile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonAbsoluteIRI.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLLiteral node)")
    public void shouldCreateViolationForOWLLiteralInOWL2Profile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyAssertion(DATAP, AnonymousIndividual(), Literal("wrong", OWL2Datatype.XSD_INTEGER)));
        OWL2Profile profile = new OWL2Profile();
        int expected = 1;
        Class[] expectedViolations = { LexicalNotInLexicalSpace.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2Profile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DatatypeDefinition(Integer(), Boolean()));
        m.addAxiom(o, DATA_PROPERTY_RANGE2);
        OWL2Profile profile = new OWL2Profile();
        int expected = 3;
        Class[] expectedViolations = { UseOfDefinedDatatypeInDatatypeRestriction.class,
            UseOfIllegalFacetRestriction.class, UseOfUndeclaredDatatype.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2Profile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DatatypeDefinition(FAKEDATATYPE, Boolean()));
        OWL2Profile profile = new OWL2Profile();
        int expected = 1;
        Class[] expectedViolations = { UseOfUndeclaredDatatype.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype node)")
    public void shouldCreateViolationForOWLDatatypeInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, Boolean());
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLAnonymousIndividual individual)")
    public void shouldCreateViolationForOWLAnonymousIndividualInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual()));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfAnonymousIndividual.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectInverseOf property)")
    public void shouldCreateViolationForOWLObjectInverseOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, SubObjectPropertyOf(OP, ObjectInverseOf(OP)));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfObjectPropertyInverse.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataAllValuesFrom desc)")
    public void shouldCreateViolationForOWLDataAllValuesFromInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, CL);
        m.addAxiom(o, SubClassOf(CL, DataAllValuesFrom(DATAP, Integer())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataExactCardinality desc)")
    public void shouldCreateViolationForOWLDataExactCardinalityInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, CL, Integer());
        m.addAxiom(o, SubClassOf(CL, DataExactCardinality(1, DATAP, Integer())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataMaxCardinality desc)")
    public void shouldCreateViolationForOWLDataMaxCardinalityInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, CL, Integer());
        m.addAxiom(o, SubClassOf(CL, DataMaxCardinality(1, DATAP, Integer())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataMinCardinality desc)")
    public void shouldCreateViolationForOWLDataMinCardinalityInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, CL, Integer());
        m.addAxiom(o, SubClassOf(CL, DataMinCardinality(1, DATAP, Integer())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectAllValuesFrom desc)")
    public void shouldCreateViolationForOWLObjectAllValuesFromInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, SubClassOf(CL, ObjectAllValuesFrom(OP, OWLThing())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectComplementOf desc)")
    public void shouldCreateViolationForOWLObjectComplementOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectComplementOf(OWLNothing())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectExactCardinality desc)")
    public void shouldCreateViolationForOWLObjectExactCardinalityInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, SubClassOf(CL, ObjectExactCardinality(1, OP, OWLThing())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMaxCardinality desc)")
    public void shouldCreateViolationForOWLObjectMaxCardinalityInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, SubClassOf(CL, ObjectMaxCardinality(1, OP, OWLThing())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectMinCardinality desc)")
    public void shouldCreateViolationForOWLObjectMinCardinalityInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, CL);
        m.addAxiom(o, SubClassOf(CL, ObjectMinCardinality(1, OP, OWLThing())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectOneOf desc)")
    public void shouldCreateViolationForOWLObjectOneOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectOneOf(NamedIndividual(IRI("urn:test#i1")), NamedIndividual(IRI(
            "urn:test#i2")))));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfObjectOneOfWithMultipleIndividuals.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectUnionOf desc)")
    public void shouldCreateViolationForOWLObjectUnionOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectUnionOf(OWLThing(), OWLNothing())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataComplementOf node)")
    public void shouldCreateViolationForOWLDataComplementOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataComplementOf(Double())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfIllegalDataRange.class, UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfDataOneOfWithMultipleLiterals.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DATA_PROPERTY_RANGE);
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfIllegalDataRange.class, UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLAsymmetricObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLAsymmetricObjectPropertyAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, AsymmetricObjectProperty(OP));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointDataPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLDataProperty dp = DataProperty(IRI("urn:test#other"));
        declare(o, DATAP, dp);
        m.addAxiom(o, DisjointDataProperties(DATAP, dp));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointObjectPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointObjectPropertiesAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#test"));
        declare(o, OP, op1);
        m.addAxiom(o, DisjointObjectProperties(op1, OP));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL);
        m.addAxiom(o, DisjointUnion(CL, OWLThing(), OWLNothing()));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, FunctionalObjectProperty(OP));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom axiom)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL, OP);
        m.addAxiom(o, HasKey(CL, OP));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, P);
        m.addAxiom(o, InverseFunctionalObjectProperty(P));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseObjectPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLInverseObjectPropertiesAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, P);
        OWLObjectProperty p1 = ObjectProperty(IRI("urn:test#objectproperty"));
        declare(o, p1);
        m.addAxiom(o, InverseObjectProperties(P, p1));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, P);
        m.addAxiom(o, IrreflexiveObjectProperty(P));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSymmetricObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLSymmetricObjectPropertyAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, P);
        m.addAxiom(o, SymmetricObjectProperty(P));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(SWRLRule rule)")
    public void shouldCreateViolationForSWRLRuleInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        OWL2ELProfile profile = new OWL2ELProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubPropertyChainOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2ELProfile() throws Exception {
        OWLOntology o = createOnto();
        OWL2ELProfile profile = new OWL2ELProfile();
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#op1"));
        OWLObjectProperty op2 = ObjectProperty(IRI("urn:test#op"));
        declare(o, op1, OP, op2, CL);
        m.addAxiom(o, ObjectPropertyRange(OP, CL));
        List<OWLObjectProperty> asList = Arrays.asList(op2, op1);
        assert asList != null;
        m.addAxiom(o, SubPropertyChainOf(asList, OP));
        int expected = 1;
        Class[] expectedViolations = { LastPropertyInChainNotInImposedRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype node)")
    public void shouldCreateViolationForOWLDatatypeInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, FAKEDATATYPE);
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLAnonymousIndividual individual)")
    public void shouldCreateViolationForOWLAnonymousIndividualInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWL2QLProfile profile = new OWL2QLProfile();
        m.addAxiom(o, ClassAssertion(OWLThing(), DF.getOWLAnonymousIndividual()));
        int expected = 1;
        Class[] expectedViolations = { UseOfAnonymousIndividual.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom axiom)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL, OP);
        m.addAxiom(o, HasKey(CL, OP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubClassOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubClassOfAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, SubClassOf(ObjectComplementOf(OWLNothing()), ObjectUnionOf(OWLThing(), OWLNothing())));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfNonSubClassExpression.class, UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentClassesAxiom axiom)")
    public void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, EquivalentClasses(ObjectUnionOf(OWLNothing(), OWLThing()), OWLNothing()));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSubClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointClassesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWL2QLProfile profile = new OWL2QLProfile();
        m.addAxiom(o, DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSubClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyDomain(OP, ObjectUnionOf(OWLNothing(), OWLThing())));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyRangeAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectUnionOf(OWLNothing(), OWLThing())));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubPropertyChainOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubPropertyChainOfAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLObjectProperty op1 = ObjectProperty(IRI("urn:test#op"));
        declare(o, OP, op1);
        m.addAxiom(o, SubPropertyChainOf(Arrays.asList(OP, op1), OP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalObjectPropertyAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, FunctionalObjectProperty(OP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLInverseFunctionalObjectPropertyAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, InverseFunctionalObjectProperty(OP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLTransitiveObjectPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLTransitiveObjectPropertyAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, TransitiveObjectProperty(OP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalDataPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, FunctionalDataProperty(DATAP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, OP);
        m.addAxiom(o, DataPropertyDomain(DATAP, ObjectMaxCardinality(1, OP, OWLNothing())));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLClassAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLClassAssertionAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLNamedIndividual i = NamedIndividual(IRI("urn:test#i"));
        declare(o, OP, i);
        m.addAxiom(o, ClassAssertion(ObjectSomeValuesFrom(OP, OWLThing()), i));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonAtomicClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSameIndividualAxiom axiom)")
    public void shouldCreateViolationForOWLSameIndividualAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, SameIndividual(NamedIndividual(IRI("urn:test#individual1")), NamedIndividual(IRI(
            "urn:test#individual2"))));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLNegativeObjectPropertyAssertionAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        OWLNamedIndividual i = NamedIndividual(IRI("urn:test#i"));
        OWLNamedIndividual i1 = NamedIndividual(IRI("urn:test#i"));
        declare(o, i, i1);
        m.addAxiom(o, NegativeObjectPropertyAssertion(OP, i, i1));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLNegativeDataPropertyAssertionAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        OWLNamedIndividual i = NamedIndividual(IRI("urn:test#i"));
        declare(o, i);
        m.addAxiom(o, NegativeDataPropertyAssertion(DATAP, i, Literal(1)));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL);
        m.addAxiom(o, DisjointUnion(CL, OWLThing(), OWLNothing()));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom)")
    public void shouldNotCreateViolationForOWLIrreflexiveObjectPropertyAxiomInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, IrreflexiveObjectProperty(OP));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(SWRLRule rule)")
    public void shouldCreateViolationForSWRLRuleInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataComplementOf node)")
    public void shouldCreateViolationForOWLDataComplementOfInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataComplementOf(Integer())));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DATA_PROPERTY_RANGE);
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2QLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataUnionOf(Integer(), Boolean())));
        OWL2QLProfile profile = new OWL2QLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfIllegalDataRange.class, UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLClassAssertionAxiom axiom)")
    public void shouldCreateViolationForOWLClassAssertionAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ClassAssertion(ObjectMinCardinality(1, OP, OWLThing()), NamedIndividual(IRI("urn:test#i"))));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLDataPropertyDomainAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP, OP);
        m.addAxiom(o, DataPropertyDomain(DATAP, ObjectMinCardinality(1, OP, OWLThing())));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointClassesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointClassesAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DisjointClasses(ObjectComplementOf(OWLThing()), OWLThing()));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfNonSubClassExpression.class, UseOfNonSubClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointDataPropertiesAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointDataPropertiesAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLDataProperty dp = DataProperty(IRI("urn:test#dproperty"));
        declare(o, DATAP, dp);
        m.addAxiom(o, DisjointDataProperties(DATAP, dp));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDisjointUnionAxiom axiom)")
    public void shouldCreateViolationForOWLDisjointUnionAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL);
        m.addAxiom(o, DisjointUnion(CL, OWLThing(), OWLNothing()));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentClassesAxiom axiom)")
    public void shouldCreateViolationForOWLEquivalentClassesAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, EquivalentClasses(ObjectComplementOf(OWLThing()), OWLNothing()));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonEquivalentClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLEquivalentDataPropertiesAxiom axiom)")
    public void shouldNotCreateViolationForOWLEquivalentDataPropertiesAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLDataProperty dp = DataProperty(IRI("urn:test#test"));
        declare(o, DATAP, dp);
        m.addAxiom(o, EquivalentDataProperties(DATAP, dp));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLFunctionalDataPropertyAxiom axiom)")
    public void shouldCreateViolationForOWLFunctionalDataPropertyAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, FunctionalDataProperty(DATAP));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLHasKeyAxiom axiom)")
    public void shouldCreateViolationForOWLHasKeyAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, CL, OP);
        m.addAxiom(o, HasKey(ObjectComplementOf(CL), OP));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSubClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyDomainAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyDomainAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP, OP);
        m.addAxiom(o, ObjectPropertyDomain(OP, ObjectMinCardinality(1, OP, OWLThing())));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLObjectPropertyRangeAxiom axiom)")
    public void shouldCreateViolationForOWLObjectPropertyRangeAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, OP);
        m.addAxiom(o, ObjectPropertyRange(OP, ObjectMinCardinality(1, OP, OWLThing())));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLSubClassOfAxiom axiom)")
    public void shouldCreateViolationForOWLSubClassOfAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, SubClassOf(ObjectComplementOf(OWLThing()), ObjectOneOf(NamedIndividual(IRI("urn:test#test")))));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfNonSubClassExpression.class, UseOfNonSuperClassExpression.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(SWRLRule rule)")
    public void shouldCreateViolationForSWRLRuleInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        m.addAxiom(o, DF.getSWRLRule(new HashSet<SWRLAtom>(), new HashSet<SWRLAtom>()));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalAxiom.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataComplementOf node)")
    public void shouldCreateViolationForOWLDataComplementOfInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataComplementOf(Integer())));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataIntersectionOf node)")
    public void shouldNotCreateViolationForOWLDataIntersectionOfInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataIntersectionOf(Integer(), Boolean())));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataOneOf node)")
    public void shouldCreateViolationForOWLDataOneOfInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataOneOf(Literal(1), Literal(2))));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatype node)")
    public void shouldCreateViolationForOWLDatatypeInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, Datatype(IRI("urn:test#test")));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 0;
        Class[] expectedViolations = {};
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeRestriction node)")
    public void shouldCreateViolationForOWLDatatypeRestrictionInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DATA_PROPERTY_RANGE);
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDataUnionOf node)")
    public void shouldCreateViolationForOWLDataUnionOfInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        declare(o, DATAP);
        m.addAxiom(o, DataPropertyRange(DATAP, DataUnionOf(Double(), Integer())));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 1;
        Class[] expectedViolations = { UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Test
    @Tests(method = "public Object visit(OWLDatatypeDefinitionAxiom axiom)")
    public void shouldCreateViolationForOWLDatatypeDefinitionAxiomInOWL2RLProfile() throws Exception {
        OWLOntology o = createOnto();
        OWLDatatype datatype = Datatype(IRI("urn:test#datatype"));
        declare(o, datatype);
        m.addAxiom(o, DatatypeDefinition(datatype, Boolean()));
        OWL2RLProfile profile = new OWL2RLProfile();
        int expected = 2;
        Class[] expectedViolations = { UseOfIllegalAxiom.class, UseOfIllegalDataRange.class };
        runAssert(o, profile, expected, expectedViolations);
    }

    @Nonnull
    private OWLOntology createOnto() throws OWLOntologyCreationException {
        return m.createOntology(onto);
    }
}
