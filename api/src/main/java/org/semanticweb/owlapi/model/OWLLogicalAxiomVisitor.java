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
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public interface OWLLogicalAxiomVisitor extends SWRLRuleVisitorBase {

    /**
     * visit OWLSubClassOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLSubClassOfAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLNegativeObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLAsymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLReflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDisjointClassesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDisjointClassesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDataPropertyDomainAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDataPropertyDomainAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLObjectPropertyDomainAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLObjectPropertyDomainAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLEquivalentObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLNegativeDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDifferentIndividualsAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDifferentIndividualsAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDisjointDataPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDisjointDataPropertiesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDisjointObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLObjectPropertyRangeAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLObjectPropertyRangeAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLObjectPropertyAssertionAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLSubObjectPropertyOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLSubObjectPropertyOfAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDisjointUnionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDisjointUnionAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLSymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDataPropertyRangeAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDataPropertyRangeAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLFunctionalDataPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLFunctionalDataPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLEquivalentDataPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLClassAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLClassAssertionAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLEquivalentClassesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLEquivalentClassesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLDataPropertyAssertionAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLTransitiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLIrreflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLSubDataPropertyOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLSubDataPropertyOfAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLInverseFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLSameIndividualAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLSameIndividualAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLSubPropertyChainOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLSubPropertyChainOfAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLInverseObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLInverseObjectPropertiesAxiom axiom) {
        doDefault(axiom);
    }

    /**
     * visit OWLHasKeyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    default void visit(OWLHasKeyAxiom axiom) {
        doDefault(axiom);
    }
}
