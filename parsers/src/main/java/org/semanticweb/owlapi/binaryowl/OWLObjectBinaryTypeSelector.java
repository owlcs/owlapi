/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.binaryowl;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.binaryowl.OWLObjectBinaryType.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 25/04/2012
 */
public class OWLObjectBinaryTypeSelector implements OWLObjectVisitorEx<OWLObjectBinaryType<?>> {

    public OWLObjectBinaryType<OWLAnnotation> visit(OWLAnnotation node) {
        return OWL_ANNOTATION;
    }

    public OWLObjectBinaryType<IRI> visit(IRI iri) {
        return IRI;
    }

    public OWLObjectBinaryType<OWLAnonymousIndividual> visit(OWLAnonymousIndividual individual) {
        return OWL_ANONYMOUS_INDIVIDUAL;
    }

    public OWLObjectBinaryType<OWLSubClassOfAxiom> visit(OWLSubClassOfAxiom axiom) {
        return OWL_SUBCLASS_OF;
    }

    public OWLObjectBinaryType<OWLNegativeObjectPropertyAssertionAxiom> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION;
    }

    public OWLObjectBinaryType<OWLAsymmetricObjectPropertyAxiom> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return OWL_ASYMMETRIC_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLReflexiveObjectPropertyAxiom> visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return OWL_REFLEXIVE_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLDisjointClassesAxiom> visit(OWLDisjointClassesAxiom axiom) {
        return OWL_DISJOINT_CLASSES;
    }

    public OWLObjectBinaryType<OWLDataPropertyDomainAxiom> visit(OWLDataPropertyDomainAxiom axiom) {
        return OWL_DATA_PROPERTY_DOMAIN;
    }

    public OWLObjectBinaryType<OWLObjectPropertyDomainAxiom> visit(OWLObjectPropertyDomainAxiom axiom) {
        return OWL_OBJECT_PROPERTY_DOMAIN;
    }

    public OWLObjectBinaryType<OWLEquivalentObjectPropertiesAxiom> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return OWL_EQUIVALENT_OBJECT_PROPERTIES;
    }

    public OWLObjectBinaryType<OWLNegativeDataPropertyAssertionAxiom> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return OWL_NEGATIVE_DATA_PROPERTY_ASSERTION;
    }

    public OWLObjectBinaryType<OWLDifferentIndividualsAxiom> visit(OWLDifferentIndividualsAxiom axiom) {
        return OWL_DIFFERENT_INDIVIDUALS;
    }

    public OWLObjectBinaryType<OWLDisjointDataPropertiesAxiom> visit(OWLDisjointDataPropertiesAxiom axiom) {
        return OWL_DISJOINT_DATA_PROPERTIES;
    }

    public OWLObjectBinaryType<OWLDisjointObjectPropertiesAxiom> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return OWL_DISJOINT_OBJECT_PROPERTIES;
    }

    public OWLObjectBinaryType<OWLObjectPropertyRangeAxiom> visit(OWLObjectPropertyRangeAxiom axiom) {
        return OWL_OBJECT_PROPERTY_RANGE;
    }

    public OWLObjectBinaryType<OWLObjectPropertyAssertionAxiom> visit(OWLObjectPropertyAssertionAxiom axiom) {
        return OWL_OBJECT_PROPERTY_ASSERTION;
    }

    public OWLObjectBinaryType<OWLFunctionalObjectPropertyAxiom> visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return OWL_FUNCTIONAL_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLSubObjectPropertyOfAxiom> visit(OWLSubObjectPropertyOfAxiom axiom) {
        return OWL_SUB_OBJECT_PROPERTY_OF;
    }

    public OWLObjectBinaryType<OWLDisjointUnionAxiom> visit(OWLDisjointUnionAxiom axiom) {
        return OWL_DISJOINT_UNION;
    }

    public OWLObjectBinaryType<OWLDeclarationAxiom> visit(OWLDeclarationAxiom axiom) {
        return OWL_DECLARATION;
    }

    public OWLObjectBinaryType<OWLAnnotationAssertionAxiom> visit(OWLAnnotationAssertionAxiom axiom) {
        return OWL_ANNOTATION_ASSERTION;
    }

    public OWLObjectBinaryType<OWLSymmetricObjectPropertyAxiom> visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return OWL_SYMMETRIC_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLDataPropertyRangeAxiom> visit(OWLDataPropertyRangeAxiom axiom) {
        return OWL_DATA_PROPERTY_RANGE;
    }

    public OWLObjectBinaryType<OWLFunctionalDataPropertyAxiom> visit(OWLFunctionalDataPropertyAxiom axiom) {
        return OWL_FUNCTIONAL_DATA_PROPERTY;
    }

    public OWLObjectBinaryType<OWLEquivalentDataPropertiesAxiom> visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return OWL_EQUIVALENT_DATA_PROPERTIES;
    }

    public OWLObjectBinaryType<OWLClassAssertionAxiom> visit(OWLClassAssertionAxiom axiom) {
        return OWL_CLASS_ASSERTION;
    }

    public OWLObjectBinaryType<OWLEquivalentClassesAxiom> visit(OWLEquivalentClassesAxiom axiom) {
        return OWL_EQUIVALENT_CLASSES;
    }

    public OWLObjectBinaryType<OWLDataPropertyAssertionAxiom> visit(OWLDataPropertyAssertionAxiom axiom) {
        return OWL_DATA_PROPERTY_ASSERTION;
    }

    public OWLObjectBinaryType<OWLTransitiveObjectPropertyAxiom> visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return OWL_TRANSITIVE_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLIrreflexiveObjectPropertyAxiom> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return OWL_IRREFLEXIVE_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLSubDataPropertyOfAxiom> visit(OWLSubDataPropertyOfAxiom axiom) {
        return OWL_SUB_DATA_PROPERTY_OF;
    }

    public OWLObjectBinaryType<OWLInverseFunctionalObjectPropertyAxiom> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return OWL_INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLSameIndividualAxiom> visit(OWLSameIndividualAxiom axiom) {
        return OWL_SAME_INDIVIDUAL;
    }

    public OWLObjectBinaryType<OWLSubPropertyChainOfAxiom> visit(OWLSubPropertyChainOfAxiom axiom) {
        return OWL_SUB_OBJECT_PROPERTY_CHAIN_OF;
    }

    public OWLObjectBinaryType<OWLInverseObjectPropertiesAxiom> visit(OWLInverseObjectPropertiesAxiom axiom) {
        return OWL_INVERSE_OBJECT_PROPERTIES;
    }

    public OWLObjectBinaryType<OWLHasKeyAxiom> visit(OWLHasKeyAxiom axiom) {
        return OWL_HAS_KEY;
    }

    public OWLObjectBinaryType<OWLDatatypeDefinitionAxiom> visit(OWLDatatypeDefinitionAxiom axiom) {
        return OWL_DATATYPE_DEFINITION;
    }

    public OWLObjectBinaryType<OWLSubAnnotationPropertyOfAxiom> visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return OWL_SUB_ANNOTATION_PROPERTY_OF;
    }

    public OWLObjectBinaryType<OWLAnnotationPropertyDomainAxiom> visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return OWL_ANNOTATION_PROPERTY_DOMAIN;
    }

    public OWLObjectBinaryType<OWLAnnotationPropertyRangeAxiom> visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return OWL_ANNOTATION_PROPERTY_RANGE;
    }

    public OWLObjectBinaryType<OWLClass> visit(OWLClass ce) {
        return OWL_CLASS;
    }

    public OWLObjectBinaryType<OWLObjectIntersectionOf> visit(OWLObjectIntersectionOf ce) {
        return OWL_OBJECT_INTERSECTION_OF;
    }

    public OWLObjectBinaryType<OWLObjectUnionOf> visit(OWLObjectUnionOf ce) {
        return OWL_OBJECT_UNION_OF;
    }

    public OWLObjectBinaryType<OWLObjectComplementOf> visit(OWLObjectComplementOf ce) {
        return OWL_OBJECT_COMPLEMENT_OF;
    }

    public OWLObjectBinaryType<OWLObjectSomeValuesFrom> visit(OWLObjectSomeValuesFrom ce) {
        return OWL_OBJECT_SOME_VALUES_FROM;
    }

    public OWLObjectBinaryType<OWLObjectAllValuesFrom> visit(OWLObjectAllValuesFrom ce) {
        return OWL_OBJECT_ALL_VALUES_FROM;
    }

    public OWLObjectBinaryType<OWLObjectHasValue> visit(OWLObjectHasValue ce) {
        return OWL_OBJECT_HAS_VALUE;
    }

    public OWLObjectBinaryType<OWLObjectMinCardinality> visit(OWLObjectMinCardinality ce) {
        return OWL_OBJECT_MIN_CARDINALITY;
    }

    public OWLObjectBinaryType<OWLObjectExactCardinality> visit(OWLObjectExactCardinality ce) {
        return OWL_OBJECT_EXACT_CARDINALITY;
    }

    public OWLObjectBinaryType<OWLObjectMaxCardinality> visit(OWLObjectMaxCardinality ce) {
        return OWL_OBJECT_MAX_CARDINALITY;
    }

    public OWLObjectBinaryType<OWLObjectHasSelf> visit(OWLObjectHasSelf ce) {
        return OWL_OBJECT_HAS_SELF;
    }

    public OWLObjectBinaryType<OWLObjectOneOf> visit(OWLObjectOneOf ce) {
        return OWL_OBJECT_ONE_OF;
    }

    public OWLObjectBinaryType<OWLDataSomeValuesFrom> visit(OWLDataSomeValuesFrom ce) {
        return OWL_DATA_SOME_VALUES_FROM;
    }

    public OWLObjectBinaryType<OWLDataAllValuesFrom> visit(OWLDataAllValuesFrom ce) {
        return OWL_DATA_ALL_VALUES_FROM;
    }

    public OWLObjectBinaryType<OWLDataHasValue> visit(OWLDataHasValue ce) {
        return OWL_DATA_HAS_VALUE;
    }

    public OWLObjectBinaryType<OWLDataMinCardinality> visit(OWLDataMinCardinality ce) {
        return OWL_DATA_MIN_CARDINALITY;
    }

    public OWLObjectBinaryType<OWLDataExactCardinality> visit(OWLDataExactCardinality ce) {
        return OWL_DATA_EXACT_CARDINALITY;
    }

    public OWLObjectBinaryType<OWLDataMaxCardinality> visit(OWLDataMaxCardinality ce) {
        return OWL_DATA_MAX_CARDINALITY;
    }

    public OWLObjectBinaryType<OWLDatatype> visit(OWLDatatype node) {
        return OWL_DATATYPE;
    }

    public OWLObjectBinaryType<OWLDataComplementOf> visit(OWLDataComplementOf node) {
        return OWL_DATA_COMPLEMENT_OF;
    }

    public OWLObjectBinaryType<OWLDataOneOf> visit(OWLDataOneOf node) {
        return OWL_DATA_ONE_OF;
    }

    public OWLObjectBinaryType<OWLDataIntersectionOf> visit(OWLDataIntersectionOf node) {
        return OWL_DATA_INTERSECTION_OF;
    }

    public OWLObjectBinaryType<OWLDataUnionOf> visit(OWLDataUnionOf node) {
        return OWL_DATA_UNION_OF;
    }

    public OWLObjectBinaryType<OWLDatatypeRestriction> visit(OWLDatatypeRestriction node) {
        return OWL_DATATYPE_RESTRICTION;
    }

    public OWLObjectBinaryType<OWLLiteral> visit(OWLLiteral node) {
        return OWL_LITERAL;
    }

    public OWLObjectBinaryType<OWLFacetRestriction> visit(OWLFacetRestriction node) {
        return OWL_FACET_RESTRICTION;
    }

    public OWLObjectBinaryType<OWLNamedIndividual> visit(OWLNamedIndividual individual) {
        return OWL_NAMED_INDIVIDUAL;
    }

    public OWLObjectBinaryType<OWLAnnotationProperty> visit(OWLAnnotationProperty property) {
        return OWL_ANNOTATION_PROPERTY;
    }

    public OWLObjectBinaryType<OWLOntology> visit(OWLOntology ontology) {
        return OWL_ONTOLOGY;
    }

    public OWLObjectBinaryType<OWLObjectProperty> visit(OWLObjectProperty property) {
        return OWL_OBJECT_PROPERTY;
    }

    public OWLObjectBinaryType<OWLObjectInverseOf> visit(OWLObjectInverseOf property) {
        return OWL_OBJECT_INVERSE_OF;
    }

    public OWLObjectBinaryType<OWLDataProperty> visit(OWLDataProperty property) {
        return OWL_DATA_PROPERTY;
    }


    public OWLObjectBinaryType<SWRLRule> visit(SWRLRule rule) {
        return SWRL_RULE;
    }

    public OWLObjectBinaryType<SWRLClassAtom> visit(SWRLClassAtom node) {
        return OWLObjectBinaryType.SWRL_CLASS_ATOM;
    }

    public OWLObjectBinaryType<SWRLDataRangeAtom> visit(SWRLDataRangeAtom node) {
        return OWLObjectBinaryType.SWRL_DATA_RANGE_ATOM;
    }

    public OWLObjectBinaryType<SWRLObjectPropertyAtom> visit(SWRLObjectPropertyAtom node) {
        return OWLObjectBinaryType.SWRL_OBJECT_PROPERTY_ATOM;
    }

    public OWLObjectBinaryType<SWRLDataPropertyAtom> visit(SWRLDataPropertyAtom node) {
        return OWLObjectBinaryType.SWRL_DATA_PROPERTY_ATOM;
    }

    public OWLObjectBinaryType<SWRLBuiltInAtom> visit(SWRLBuiltInAtom node) {
        return SWRL_BUILT_IN_ATOM;
    }

    public OWLObjectBinaryType<SWRLVariable> visit(SWRLVariable node) {
        return SWRL_VARIABLE;
    }

    public OWLObjectBinaryType<SWRLIndividualArgument> visit(SWRLIndividualArgument node) {
        return OWLObjectBinaryType.SWRL_INDIVIDUAL_ARGUMENT;
    }

    public OWLObjectBinaryType<SWRLLiteralArgument> visit(SWRLLiteralArgument node) {
        return SWRL_LITERAL_ARGUMENT;
    }

    public OWLObjectBinaryType<SWRLSameIndividualAtom> visit(SWRLSameIndividualAtom node) {
        return SWRL_SAME_INDIVIDUAL_ATOM;
    }

    public OWLObjectBinaryType<SWRLDifferentIndividualsAtom> visit(SWRLDifferentIndividualsAtom node) {
        return SWRL_DIFFERENT_INDIVIDUALS_ATOM;
    }
}
