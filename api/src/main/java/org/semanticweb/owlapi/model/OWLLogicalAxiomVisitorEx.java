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
 * @param <O>
 *        visitor type
 */
public interface OWLLogicalAxiomVisitorEx<O> extends SWRLRuleVisitorExBase<O> {

    /**
     * visit OWLSubClassOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLSubClassOfAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLNegativeObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLAsymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLReflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDisjointClassesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDisjointClassesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDataPropertyDomainAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDataPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLObjectPropertyDomainAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLObjectPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLEquivalentObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLNegativeDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDifferentIndividualsAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDifferentIndividualsAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDisjointDataPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDisjointObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLObjectPropertyRangeAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLObjectPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLSubObjectPropertyOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDisjointUnionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDisjointUnionAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLSymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDataPropertyRangeAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDataPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLFunctionalDataPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLEquivalentDataPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLClassAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLClassAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLEquivalentClassesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLEquivalentClassesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLTransitiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLIrreflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLSubDataPropertyOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLSubDataPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLInverseFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLSameIndividualAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLSameIndividualAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLSubPropertyChainOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLSubPropertyChainOfAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLInverseObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    /**
     * visit OWLHasKeyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     * @return visitor value
     */
    default O visit(OWLHasKeyAxiom axiom) {
        return doDefault(axiom);
    }
}
