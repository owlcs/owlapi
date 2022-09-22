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
package org.semanticweb.owlapi.apitest.fileroundtrip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.TestFilenames;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.search.Searcher;
import org.semanticweb.owlapi.vocab.OWLFacet;

class FileRoundTripCorrectAxiomsTestCase extends TestBase {

    static final OWLDeclarationAxiom dpd = Declaration(DATAPROPS.DP);
    static final OWLDeclarationAxiom dbb = Declaration(DATATYPES.DATAB);
    static final OWLDeclarationAxiom pd = Declaration(OBJPROPS.P);
    static final OWLDeclarationAxiom cd = Declaration(Class(iri(DECLARATIONS, "Cls")));
    static final OWLDeclarationAxiom opd = Declaration(ObjectProperty(iri(DECLARATIONS, "op")));
    static final OWLDeclarationAxiom bd = Declaration(CLASSES.B);

    protected void assertEqualsSet(String ontology, Set<OWLAxiom> axioms) {
        assertEquals(str(ontologyFromClasspathFile(ontology).axioms()), str(axioms));
    }

    static Stream<Arguments> axioms() {
        return Stream.of(
        //@formatter:off
            of(TestFilenames.DATA_COMPLEMENT_OF_RDF,              DataPropertyRange(DATAPROPS.DP, DataComplementOf(Integer())), dpd),
            of(TestFilenames.DATA_INTERSECTION_OF_RDF,            DataPropertyRange(DATAPROPS.DP, DataIntersectionOf(Integer(), Float())), dpd),
            of(TestFilenames.DATA_ONE_OF_RDF,                     DataPropertyRange(DATAPROPS.DP, DataOneOf(Literal(30), Literal(31f))), dpd),
            of(TestFilenames.DATA_UNION_OF_RDF,                   DataPropertyRange(DATAPROPS.DP, DataUnionOf(Integer(), Float())), dpd),
            of(TestFilenames.DATATYPE_RESTRICTION_RDF,            DataPropertyRange(DATAPROPS.DP, DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MIN_INCLUSIVE, Literal(18)), FacetRestriction(OWLFacet.MAX_INCLUSIVE, Literal(30)))), dpd),
            of(TestFilenames.COMPLEX_SUB_PROPERTY_RDF,            SubPropertyChainOf(l(OBJPROPS.P, OBJPROPS.Q), OBJPROPS.R)),
            of(TestFilenames.TEST_DECLARATIONS_RDF,               Declaration(DataProperty(iri(DECLARATIONS, "dp"))), Declaration(INDIVIDUALS.I), Declaration(AnnotationProperty(iri(DECLARATIONS, "ap"))), Declaration(Datatype(iri(DECLARATIONS, "dt"))), cd, opd),
            of(TestFilenames.DISJOINT_CLASSES_RDF,                DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C)),
            of(TestFilenames.HAS_KEY_RDF,                         HasKey(CLASSES.PERSON, OBJPROPS.OP, DATAPROPS.DAP), Declaration(CLASSES.PERSON), Declaration(DATAPROPS.DAP), Declaration(OBJPROPS.OP)),
            of(TestFilenames.INVERSE_OF_RDF,                      InverseObjectProperties(OBJPROPS.P, OBJPROPS.Q)),
            of(TestFilenames.DATA_ALL_VALUES_FROM_RDF,            SubClassOf(CLASSES.A, DataAllValuesFrom(DATAPROPS.DP, DATATYPES.DATAB)), dbb, dpd),
            of(TestFilenames.DATA_HAS_VALUE_RDF,                  SubClassOf(CLASSES.A, DataHasValue(DATAPROPS.DP, LITERALS.LIT_THREE)), dpd, SubClassOf(CLASSES.A, DataHasValue(DATAPROPS.DP, Literal("A", "")))),
            of(TestFilenames.DATA_MAX_CARDINALITY_RDF,            SubClassOf(CLASSES.A, DataMaxCardinality(3, DATAPROPS.DP, TopDatatype())), dpd),
            of(TestFilenames.DATA_MIN_CARDINALITY_RDF,            SubClassOf(CLASSES.A, DataMinCardinality(3, DATAPROPS.DP, TopDatatype())), dpd),
            of(TestFilenames.DATA_SOME_VALUES_FROM_RDF,           SubClassOf(CLASSES.A, DataSomeValuesFrom(DATAPROPS.DP, DATATYPES.DATAB)), dbb, dpd),
            of(TestFilenames.OBJECT_ALL_VALUES_FROM_RDF,          SubClassOf(CLASSES.A, ObjectAllValuesFrom(OBJPROPS.P, CLASSES.B)), bd, pd),
            of(TestFilenames.OBJECT_CARDINALITY_RDF,              SubClassOf(CLASSES.A, ObjectExactCardinality(3, OBJPROPS.P, OWLThing())), pd),
            of(TestFilenames.OBJECT_COMPLEMENT_OF_RDF,            SubClassOf(CLASSES.A, notB)),
            of(TestFilenames.OBJECT_HAS_SELF_RDF,                 SubClassOf(CLASSES.A, ObjectHasSelf(OBJPROPS.P)), pd),
            of(TestFilenames.OBJECT_HAS_VALUE_RDF,                SubClassOf(CLASSES.A, ObjectHasValue(OBJPROPS.P, INDIVIDUALS.indA)), pd),
            of(TestFilenames.OBJECT_INTERSECTION_OF_RDF,          SubClassOf(CLASSES.A, ObjectIntersectionOf(CLASSES.B, CLASSES.C))),
            of(TestFilenames.OBJECT_MAX_CARDINALITY_RDF,          SubClassOf(CLASSES.A, ObjectMaxCardinality(3, OBJPROPS.P, OWLThing())), pd),
            of(TestFilenames.OBJECT_MAX_QUALIFIED_CARDINALITY_RDF,SubClassOf(CLASSES.A, ObjectMaxCardinality(3, OBJPROPS.P, CLASSES.B)), pd),
            of(TestFilenames.OBJECT_MIN_CARDINALITY_RDF,          SubClassOf(CLASSES.A, ObjectMinCardinality(3, OBJPROPS.P, OWLThing())), pd),
            of(TestFilenames.OBJECT_MIN_QUALIFIED_CARDINALITY_RDF,SubClassOf(CLASSES.A, ObjectMinCardinality(3, OBJPROPS.P, CLASSES.B)), pd),
            of(TestFilenames.OBJECT_ONE_OF_RDF,                   SubClassOf(CLASSES.A, ObjectOneOf(INDIVIDUALS.indA, INDIVIDUALS.indB))),
            of(TestFilenames.OBJECT_QUALIFIED_CARDINALITY_RDF,    SubClassOf(CLASSES.A, ObjectExactCardinality(3, OBJPROPS.P, CLASSES.B)), pd),
            of(TestFilenames.OBJECT_SOME_VALUES_FROM_RDF,         SubClassOf(CLASSES.A, ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.B)), bd, pd),
            of(TestFilenames.OBJECT_UNION_OF_RDF,                 SubClassOf(CLASSES.A, ObjectUnionOf(CLASSES.B, CLASSES.C))),
            of(TestFilenames.SUB_CLASS_OF_RDF,                    SubClassOf(CLASSES.A, CLASSES.B)),
            of(TestFilenames.UNTYPED_SUB_CLASS_OF_RDF,            SubClassOf(CLASSES.A, CLASSES.B))
            //@formatter:on
        );
    }

    static Arguments of(String rdfFile, OWLAxiom... axioms) {
        return Arguments.of(rdfFile, set(axioms));
    }

    @ParameterizedTest
    @MethodSource("axioms")
    void testContainsComplexSubPropertyAxiom(String name, Set<OWLAxiom> axioms) {
        assertEqualsSet(name, axioms);
    }

    @Test
    void testCorrectAxiomAnnotatedPropertyAssertions() {
        OWLOntology ontology =
            ontologyFromClasspathFile(TestFilenames.ANNOTATED_PROPERTY_ASSERTIONS_RDF);
        OWLAxiom ax =
            ObjectPropertyAssertion(OBJPROPS.predicate, INDIVIDUALS.subject, INDIVIDUALS.object);
        assertTrue(ontology.containsAxiom(ax, EXCLUDED, AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS));
        Set<OWLAxiom> axioms = asUnorderedSet(ontology.axiomsIgnoreAnnotations(ax, EXCLUDED));
        assertEquals(1, axioms.size());
        OWLAxiom theAxiom = axioms.iterator().next();
        assertTrue(theAxiom.isAnnotated());
    }

    @Test
    void testDeprecatedAnnotationAssertionsPresent() {
        OWLOntology ont = ontologyFromClasspathFile(TestFilenames.DEPRECATED_RDF);
        Searcher.annotationObjects(ont.annotationAssertionAxioms(IRIS.clsA, INCLUDED))
            .forEach(a -> a.isDeprecatedIRIAnnotation());
        Searcher.annotationObjects(ont.annotationAssertionAxioms(IRIS.prop, INCLUDED))
            .forEach(a -> assertTrue(a.isDeprecatedIRIAnnotation()));
    }

    @Test
    void testCorrectAxiomsRDFSClass() {
        OWLOntology ont = ontologyFromClasspathFile(TestFilenames.RDFS_CLASS_RDF);
        assertTrue(ont.containsAxiom(Declaration(CLASSES.A)));
    }

    @Test
    void testStructuralReasonerRecusion() {
        OWLOntology ontology = ontologyFromClasspathFile(TestFilenames.KOALA_OWL);
        String ontName = ontology.getOntologyID().getOntologyIRI().get().toString();
        StructuralReasoner reasoner =
            new StructuralReasoner(ontology, new SimpleConfiguration(), BufferingMode.BUFFERING);
        OWLClass cls = Class(iri(ontName + "#", "Koala"));
        reasoner.getSubClasses(cls, false);
        reasoner.getSuperClasses(cls, false);
    }

    @Test
    void testIsGCIMethodSubClassAxiom() {
        assertFalse(SubClassOf(CLASSES.A, CLASSES.B).isGCI());
        assertTrue(SubClassOf(ObjectIntersectionOf(CLASSES.A, CLASSES.C), CLASSES.B).isGCI());
    }

    @Test
    void testParsedAxiomsSubClassOfUntypedOWLClass() {
        OWLOntology ontology =
            ontologyFromClasspathFile(TestFilenames.SUB_CLASS_OF_UNTYPED_OWL_CLASS_RDF);
        List<OWLSubClassOfAxiom> axioms = asList(ontology.axioms(AxiomType.SUBCLASS_OF));
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.get(0);
        assertEquals(CLASSES.A, ax.getSubClass());
        assertEquals(CLASSES.B, ax.getSuperClass());
    }

    @Test
    void testParsedAxiomsSubClassOfUntypedSomeValuesFrom() {
        OWLOntology ontology =
            ontologyFromClasspathFile(TestFilenames.SUB_CLASS_OF_UNTYPED_SOME_VALUES_FROM_RDF);
        List<OWLSubClassOfAxiom> axioms = asList(ontology.axioms(AxiomType.SUBCLASS_OF));
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.get(0);
        assertEquals(CLASSES.A, ax.getSubClass());
        assertTrue(ax.getSuperClass() instanceof OWLObjectSomeValuesFrom);
        OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) ax.getSuperClass();
        assertEquals(OBJPROPS.P, someValuesFrom.getProperty());
        assertEquals(CLASSES.C, someValuesFrom.getFiller());
    }
}
