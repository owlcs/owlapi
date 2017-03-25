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
package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLObjectVisitor {
    /**
     * Default action for the visitor.
     *
     * @param object The object to visit
     */
    default void doDefault(@SuppressWarnings("unused") OWLObject object) {}

    /**
     * Default operation for annotation axioms
     * 
     * @param axiom axiom to visit
     */
    default void doDefaultAnnotationAxiom(OWLAnnotationAxiom axiom) {
        doDefault(axiom);
    }

    /** @param axiom OWLAnnotationAssertionAxiom to visit */
    default void visit(OWLAnnotationAssertionAxiom axiom) {
        doDefaultAnnotationAxiom(axiom);
    }

    /** @param axiom OWLSubAnnotationPropertyOfAxiom to visit */
    default void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        doDefaultAnnotationAxiom(axiom);
    }

    /** @param axiom OWLAnnotationPropertyDomainAxiom to visit */
    default void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        doDefaultAnnotationAxiom(axiom);
    }

    /** @param axiom OWLAnnotationPropertyRangeAxiom to visit */
    default void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        doDefaultAnnotationAxiom(axiom);
    }

    /**
     * Default operation for annotation objects
     * 
     * @param axiom axiom to visit
     */
    default void doDefaultAnnotationObject(OWLAnnotation axiom) {
        doDefault(axiom);
    }

    /** @param node OWLAnnotation to visit */
    default void visit(OWLAnnotation node) {
        doDefaultAnnotationObject(node);
    }

    /**
     * Default operation for axioms
     * 
     * @param axiom axiom to visit
     */
    default void doDefaultAxiom(OWLAxiom axiom) {
        doDefault(axiom);
    }

    /** @param axiom axiom to visit */
    default void visit(OWLDeclarationAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom axiom to visit */
    default void visit(OWLDatatypeDefinitionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param iri IRI to visit */
    default void doDefaultAnnotationValue(IRI iri) {
        doDefault(iri);
    }

    /** @param iri IRI to visit */
    default void visit(IRI iri) {
        doDefaultAnnotationValue(iri);
    }

    /** @param ce OWLObjectIntersectionOf to visit */
    default void doDefaultClassExpression(OWLClassExpression ce) {
        doDefault(ce);
    }

    /** @param ce OWLObjectIntersectionOf to visit */
    default void visit(OWLObjectIntersectionOf ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectUnionOf to visit */
    default void visit(OWLObjectUnionOf ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectComplementOf to visit */
    default void visit(OWLObjectComplementOf ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectSomeValuesFrom to visit */
    default void visit(OWLObjectSomeValuesFrom ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectAllValuesFrom to visit */
    default void visit(OWLObjectAllValuesFrom ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectHasValue to visit */
    default void visit(OWLObjectHasValue ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectMinCardinality to visit */
    default void visit(OWLObjectMinCardinality ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectExactCardinality to visit */
    default void visit(OWLObjectExactCardinality ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectMaxCardinality to visit */
    default void visit(OWLObjectMaxCardinality ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectHasSelf to visit */
    default void visit(OWLObjectHasSelf ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLObjectOneOf to visit */
    default void visit(OWLObjectOneOf ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLDataSomeValuesFrom to visit */
    default void visit(OWLDataSomeValuesFrom ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLDataAllValuesFrom to visit */
    default void visit(OWLDataAllValuesFrom ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLDataHasValue to visit */
    default void visit(OWLDataHasValue ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLDataMinCardinality to visit */
    default void visit(OWLDataMinCardinality ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLDataExactCardinality to visit */
    default void visit(OWLDataExactCardinality ce) {
        doDefaultClassExpression(ce);
    }

    /** @param ce OWLDataMaxCardinality to visit */
    default void visit(OWLDataMaxCardinality ce) {
        doDefaultClassExpression(ce);
    }

    /** @param node OWLDataOneOf to visit */
    default void doDefaultDataRange(OWLDataRange node) {
        doDefault(node);
    }

    /** @param node OWLDataOneOf to visit */
    default void visit(OWLDataOneOf node) {
        doDefaultDataRange(node);
    }

    /** @param node OWLDataComplementOf to visit */
    default void visit(OWLDataComplementOf node) {
        doDefaultDataRange(node);
    }

    /** @param node OWLDataIntersectionOf to visit */
    default void visit(OWLDataIntersectionOf node) {
        doDefaultDataRange(node);
    }

    /** @param node OWLDataUnionOf to visit */
    default void visit(OWLDataUnionOf node) {
        doDefaultDataRange(node);
    }

    /** @param node OWLDatatypeRestriction to visit */
    default void visit(OWLDatatypeRestriction node) {
        doDefaultDataRange(node);
    }

    /** @param node OWLFacetRestriction to visit */
    default void visit(OWLFacetRestriction node) {
        doDefault(node);
    }

    /** @param node OWLLiteral to visit */
    default void visit(OWLLiteral node) {
        doDefault(node);
    }

    /**
     * Default method for entities
     *
     * @param ce entity to visit
     */
    default void doDefaultEntity(OWLEntity ce) {
        doDefault(ce);
    }

    /** @param ce OWLClass to visit */
    default void visit(OWLClass ce) {
        doDefaultEntity(ce);
    }

    /** @param node OWLDatatype to visit */
    default void visit(OWLDatatype node) {
        doDefaultEntity(node);
    }

    /** @param individual OWLAnonymousIndividual to visit */
    default void visit(OWLAnonymousIndividual individual) {
        doDefault(individual);
    }

    /** @param individual OWLNamedIndividual to visit */
    default void visit(OWLNamedIndividual individual) {
        doDefaultEntity(individual);
    }

    /** @param property OWLObjectProperty to visit */
    default void visit(OWLObjectProperty property) {
        doDefaultEntity(property);
    }

    /** @param property OWLDataProperty to visit */
    default void visit(OWLDataProperty property) {
        doDefaultEntity(property);
    }

    /** @param property OWLAnnotationProperty to visit */
    default void visit(OWLAnnotationProperty property) {
        doDefaultEntity(property);
    }

    /** @param ontology OWLOntology to visit */
    default void visit(OWLOntology ontology) {
        doDefault(ontology);
    }

    /** @param axiom OWLSubClassOfAxiom to visit */
    default void visit(OWLSubClassOfAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLNegativeObjectPropertyAssertionAxiom to visit */
    default void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLAsymmetricObjectPropertyAxiom to visit */
    default void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLReflexiveObjectPropertyAxiom to visit */
    default void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDisjointClassesAxiom to visit */
    default void visit(OWLDisjointClassesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDataPropertyDomainAxiom to visit */
    default void visit(OWLDataPropertyDomainAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLObjectPropertyDomainAxiom to visit */
    default void visit(OWLObjectPropertyDomainAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLEquivalentObjectPropertiesAxiom to visit */
    default void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLNegativeDataPropertyAssertionAxiom to visit */
    default void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDifferentIndividualsAxiom to visit */
    default void visit(OWLDifferentIndividualsAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDisjointDataPropertiesAxiom to visit */
    default void visit(OWLDisjointDataPropertiesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDisjointObjectPropertiesAxiom to visit */
    default void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLObjectPropertyRangeAxiom to visit */
    default void visit(OWLObjectPropertyRangeAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLObjectPropertyAssertionAxiom to visit */
    default void visit(OWLObjectPropertyAssertionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLFunctionalObjectPropertyAxiom to visit */
    default void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLSubObjectPropertyOfAxiom to visit */
    default void visit(OWLSubObjectPropertyOfAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDisjointUnionAxiom to visit */
    default void visit(OWLDisjointUnionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLSymmetricObjectPropertyAxiom to visit */
    default void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDataPropertyRangeAxiom to visit */
    default void visit(OWLDataPropertyRangeAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLFunctionalDataPropertyAxiom to visit */
    default void visit(OWLFunctionalDataPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLEquivalentDataPropertiesAxiom to visit */
    default void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLClassAssertionAxiom to visit */
    default void visit(OWLClassAssertionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLEquivalentClassesAxiom to visit */
    default void visit(OWLEquivalentClassesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLDataPropertyAssertionAxiom to visit */
    default void visit(OWLDataPropertyAssertionAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLTransitiveObjectPropertyAxiom to visit */
    default void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLIrreflexiveObjectPropertyAxiom to visit */
    default void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLSubDataPropertyOfAxiom to visit */
    default void visit(OWLSubDataPropertyOfAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLInverseFunctionalObjectPropertyAxiom to visit */
    default void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLSameIndividualAxiom to visit */
    default void visit(OWLSameIndividualAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLSubPropertyChainOfAxiom to visit */
    default void visit(OWLSubPropertyChainOfAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLInverseObjectPropertiesAxiom to visit */
    default void visit(OWLInverseObjectPropertiesAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param axiom OWLHasKeyAxiom to visit */
    default void visit(OWLHasKeyAxiom axiom) {
        doDefaultAxiom(axiom);
    }

    /** @param node SWRLRule to visit */
    default void visit(SWRLRule node) {
        doDefaultAxiom(node);
    }

    /** @param property OWLObjectInverseOf to visit */
    default void doDefaultPropertyExpression(OWLObjectInverseOf property) {
        doDefault(property);
    }

    /** @param property OWLObjectInverseOf to visit */
    default void visit(OWLObjectInverseOf property) {
        doDefaultPropertyExpression(property);
    }

    /** @param node SWRLClassAtom to visit */
    default void doDefaultSWRL(SWRLObject node) {
        doDefault(node);
    }

    /** @param node SWRLClassAtom to visit */
    default void visit(SWRLClassAtom node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLDataRangeAtom to visit */
    default void visit(SWRLDataRangeAtom node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLObjectPropertyAtom to visit */
    default void visit(SWRLObjectPropertyAtom node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLDataPropertyAtom to visit */
    default void visit(SWRLDataPropertyAtom node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLBuiltInAtom to visit */
    default void visit(SWRLBuiltInAtom node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLVariable to visit */
    default void visit(SWRLVariable node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLIndividualArgument to visit */
    default void visit(SWRLIndividualArgument node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLLiteralArgument to visit */
    default void visit(SWRLLiteralArgument node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLSameIndividualAtom to visit */
    default void visit(SWRLSameIndividualAtom node) {
        doDefaultSWRL(node);
    }

    /** @param node SWRLDifferentIndividualsAtom to visit */
    default void visit(SWRLDifferentIndividualsAtom node) {
        doDefaultSWRL(node);
    }
}
