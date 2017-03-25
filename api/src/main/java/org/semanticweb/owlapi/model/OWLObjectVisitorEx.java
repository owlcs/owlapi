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
 * @param <O> visitor type
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public interface OWLObjectVisitorEx<O> {
    /**
     * Gets the default return value for this visitor. By default, the default is {@code null}
     *
     * @param object The object that was visited.
     * @return The default return value
     */
    default O doDefault(@SuppressWarnings("unused") OWLObject object) {
        // no other way to provide a default implementation
        return null;
    }

    /**
     * Default operation for annotation axioms
     * 
     * @param axiom axiom to visit
     * @return visitor return value
     */
    default O doDefaultAnnotationAxiom(OWLAnnotationAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * @param axiom OWLAnnotationAssertionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLAnnotationAssertionAxiom axiom) {
        return doDefaultAnnotationAxiom(axiom);
    }

    /**
     * @param axiom OWLSubAnnotationPropertyOfAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return doDefaultAnnotationAxiom(axiom);
    }

    /**
     * @param axiom OWLAnnotationPropertyDomainAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return doDefaultAnnotationAxiom(axiom);
    }

    /**
     * @param axiom OWLAnnotationPropertyRangeAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return doDefaultAnnotationAxiom(axiom);
    }

    /**
     * Default operation for annotation objects
     * 
     * @param axiom axiom to visit
     * @return visitor return value
     */
    default O doDefaultAnnotationObject(OWLAnnotation axiom) {
        return doDefault(axiom);
    }

    /**
     * @param node OWLAnnotation to visit
     * @return visitor return value
     */
    default O visit(OWLAnnotation node) {
        return doDefaultAnnotationObject(node);
    }

    /**
     * Default operation for axioms
     * 
     * @param axiom axiom to visit
     * @return visitor return value
     */
    default O doDefaultAxiom(OWLAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * @param axiom axiom to visit
     * @return visitor return value
     */
    default O visit(OWLDeclarationAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom axiom to visit
     * @return visitor return value
     */
    default O visit(OWLDatatypeDefinitionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param iri IRI to visit
     * @return visitor return value
     */
    default O doDefaultAnnotationValue(IRI iri) {
        return doDefault(iri);
    }

    /**
     * @param iri IRI to visit
     * @return visitor return value
     */
    default O visit(IRI iri) {
        return doDefaultAnnotationValue(iri);
    }

    /**
     * @param ce OWLObjectIntersectionOf to visit
     * @return visitor return value
     */
    default O doDefaultClassExpression(OWLClassExpression ce) {
        return doDefault(ce);
    }

    /**
     * @param ce OWLObjectIntersectionOf to visit
     * @return visitor return value
     */
    default O visit(OWLObjectIntersectionOf ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectUnionOf to visit
     * @return visitor return value
     */
    default O visit(OWLObjectUnionOf ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectComplementOf to visit
     * @return visitor return value
     */
    default O visit(OWLObjectComplementOf ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectSomeValuesFrom to visit
     * @return visitor return value
     */
    default O visit(OWLObjectSomeValuesFrom ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectAllValuesFrom to visit
     * @return visitor return value
     */
    default O visit(OWLObjectAllValuesFrom ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectHasValue to visit
     * @return visitor return value
     */
    default O visit(OWLObjectHasValue ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectMinCardinality to visit
     * @return visitor return value
     */
    default O visit(OWLObjectMinCardinality ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectExactCardinality to visit
     * @return visitor return value
     */
    default O visit(OWLObjectExactCardinality ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectMaxCardinality to visit
     * @return visitor return value
     */
    default O visit(OWLObjectMaxCardinality ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectHasSelf to visit
     * @return visitor return value
     */
    default O visit(OWLObjectHasSelf ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLObjectOneOf to visit
     * @return visitor return value
     */
    default O visit(OWLObjectOneOf ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLDataSomeValuesFrom to visit
     * @return visitor return value
     */
    default O visit(OWLDataSomeValuesFrom ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLDataAllValuesFrom to visit
     * @return visitor return value
     */
    default O visit(OWLDataAllValuesFrom ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLDataHasValue to visit
     * @return visitor return value
     */
    default O visit(OWLDataHasValue ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLDataMinCardinality to visit
     * @return visitor return value
     */
    default O visit(OWLDataMinCardinality ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLDataExactCardinality to visit
     * @return visitor return value
     */
    default O visit(OWLDataExactCardinality ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param ce OWLDataMaxCardinality to visit
     * @return visitor return value
     */
    default O visit(OWLDataMaxCardinality ce) {
        return doDefaultClassExpression(ce);
    }

    /**
     * @param node OWLDataOneOf to visit
     * @return visitor return value
     */
    default O doDefaultDataRange(OWLDataRange node) {
        return doDefault(node);
    }

    /**
     * @param node OWLDataOneOf to visit
     * @return visitor return value
     */
    default O visit(OWLDataOneOf node) {
        return doDefaultDataRange(node);
    }

    /**
     * @param node OWLDataComplementOf to visit
     * @return visitor return value
     */
    default O visit(OWLDataComplementOf node) {
        return doDefaultDataRange(node);
    }

    /**
     * @param node OWLDataIntersectionOf to visit
     * @return visitor return value
     */
    default O visit(OWLDataIntersectionOf node) {
        return doDefaultDataRange(node);
    }

    /**
     * @param node OWLDataUnionOf to visit
     * @return visitor return value
     */
    default O visit(OWLDataUnionOf node) {
        return doDefaultDataRange(node);
    }

    /**
     * @param node OWLDatatypeRestriction to visit
     * @return visitor return value
     */
    default O visit(OWLDatatypeRestriction node) {
        return doDefaultDataRange(node);
    }

    /**
     * @param node OWLFacetRestriction to visit
     * @return visitor return value
     */
    default O visit(OWLFacetRestriction node) {
        return doDefault(node);
    }

    /**
     * @param node OWLLiteral to visit
     * @return visitor return value
     */
    default O visit(OWLLiteral node) {
        return doDefault(node);
    }

    /**
     * Default method for entities
     *
     * @param ce entity to visit
     * @return visitor return value
     */
    default O doDefaultEntity(OWLEntity ce) {
        return doDefault(ce);
    }

    /**
     * @param ce OWLClass to visit
     * @return visitor return value
     */
    default O visit(OWLClass ce) {
        return doDefaultEntity(ce);
    }

    /**
     * @param node OWLDatatype to visit
     * @return visitor return value
     */
    default O visit(OWLDatatype node) {
        return doDefaultEntity(node);
    }

    /**
     * @param individual OWLAnonymousIndividual to visit
     * @return visitor return value
     */
    default O visit(OWLAnonymousIndividual individual) {
        return doDefault(individual);
    }

    /**
     * @param individual OWLNamedIndividual to visit
     * @return visitor return value
     */
    default O visit(OWLNamedIndividual individual) {
        return doDefaultEntity(individual);
    }

    /**
     * @param property OWLObjectProperty to visit
     * @return visitor return value
     */
    default O visit(OWLObjectProperty property) {
        return doDefaultEntity(property);
    }

    /**
     * @param property OWLDataProperty to visit
     * @return visitor return value
     */
    default O visit(OWLDataProperty property) {
        return doDefaultEntity(property);
    }

    /**
     * @param property OWLAnnotationProperty to visit
     * @return visitor return value
     */
    default O visit(OWLAnnotationProperty property) {
        return doDefaultEntity(property);
    }

    /**
     * @param ontology OWLOntology to visit
     * @return visitor return value
     */
    default O visit(OWLOntology ontology) {
        return doDefault(ontology);
    }

    /**
     * @param axiom OWLSubClassOfAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSubClassOfAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLNegativeObjectPropertyAssertionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLAsymmetricObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLReflexiveObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDisjointClassesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDisjointClassesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDataPropertyDomainAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDataPropertyDomainAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLObjectPropertyDomainAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLObjectPropertyDomainAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLEquivalentObjectPropertiesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLNegativeDataPropertyAssertionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDifferentIndividualsAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDifferentIndividualsAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDisjointDataPropertiesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDisjointObjectPropertiesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLObjectPropertyRangeAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLObjectPropertyRangeAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLObjectPropertyAssertionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLFunctionalObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLSubObjectPropertyOfAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDisjointUnionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDisjointUnionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLSymmetricObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDataPropertyRangeAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDataPropertyRangeAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLFunctionalDataPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLEquivalentDataPropertiesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLClassAssertionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLClassAssertionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLEquivalentClassesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLEquivalentClassesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLDataPropertyAssertionAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLDataPropertyAssertionAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLTransitiveObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLIrreflexiveObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLSubDataPropertyOfAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSubDataPropertyOfAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLInverseFunctionalObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLSameIndividualAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSameIndividualAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLSubPropertyChainOfAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLSubPropertyChainOfAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLInverseObjectPropertiesAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param axiom OWLHasKeyAxiom to visit
     * @return visitor return value
     */
    default O visit(OWLHasKeyAxiom axiom) {
        return doDefaultAxiom(axiom);
    }

    /**
     * @param node SWRLRule to visit
     * @return visitor return value
     */
    default O visit(SWRLRule node) {
        return doDefaultAxiom(node);
    }

    /**
     * @param property OWLObjectInverseOf to visit
     * @return visitor return value
     */
    default O doDefaultPropertyExpression(OWLObjectInverseOf property) {
        return doDefault(property);
    }

    /**
     * @param property OWLObjectInverseOf to visit
     * @return visitor return value
     */
    default O visit(OWLObjectInverseOf property) {
        return doDefaultPropertyExpression(property);
    }

    /**
     * @param node SWRLClassAtom to visit
     * @return visitor return value
     */
    default O doDefaultSWRL(SWRLObject node) {
        return doDefault(node);
    }

    /**
     * @param node SWRLClassAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLClassAtom node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLDataRangeAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLDataRangeAtom node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLObjectPropertyAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLObjectPropertyAtom node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLDataPropertyAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLDataPropertyAtom node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLBuiltInAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLBuiltInAtom node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLVariable to visit
     * @return visitor return value
     */
    default O visit(SWRLVariable node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLIndividualArgument to visit
     * @return visitor return value
     */
    default O visit(SWRLIndividualArgument node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLLiteralArgument to visit
     * @return visitor return value
     */
    default O visit(SWRLLiteralArgument node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLSameIndividualAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLSameIndividualAtom node) {
        return doDefaultSWRL(node);
    }

    /**
     * @param node SWRLDifferentIndividualsAtom to visit
     * @return visitor return value
     */
    default O visit(SWRLDifferentIndividualsAtom node) {
        return doDefaultSWRL(node);
    }
}
