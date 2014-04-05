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

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public interface OWLLogicalAxiomVisitor {

    /**
     * visit OWLSubClassOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLSubClassOfAxiom axiom);

    /**
     * visit OWLNegativeObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom);

    /**
     * visit OWLAsymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom);

    /**
     * visit OWLReflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom);

    /**
     * visit OWLDisjointClassesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDisjointClassesAxiom axiom);

    /**
     * visit OWLDataPropertyDomainAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDataPropertyDomainAxiom axiom);

    /**
     * visit OWLObjectPropertyDomainAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom);

    /**
     * visit OWLEquivalentObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom);

    /**
     * visit OWLNegativeDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom);

    /**
     * visit OWLDifferentIndividualsAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDifferentIndividualsAxiom axiom);

    /**
     * visit OWLDisjointDataPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom);

    /**
     * visit OWLDisjointObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom);

    /**
     * visit OWLObjectPropertyRangeAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom);

    /**
     * visit OWLObjectPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom);

    /**
     * visit OWLFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom);

    /**
     * visit OWLSubObjectPropertyOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom);

    /**
     * visit OWLDisjointUnionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDisjointUnionAxiom axiom);

    /**
     * visit OWLSymmetricObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom);

    /**
     * visit OWLDataPropertyRangeAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDataPropertyRangeAxiom axiom);

    /**
     * visit OWLFunctionalDataPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom);

    /**
     * visit OWLEquivalentDataPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom);

    /**
     * visit OWLClassAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLClassAssertionAxiom axiom);

    /**
     * visit OWLEquivalentClassesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLEquivalentClassesAxiom axiom);

    /**
     * visit OWLDataPropertyAssertionAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom);

    /**
     * visit OWLTransitiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom);

    /**
     * visit OWLIrreflexiveObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom);

    /**
     * visit OWLSubDataPropertyOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom);

    /**
     * visit OWLInverseFunctionalObjectPropertyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom);

    /**
     * visit OWLSameIndividualAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLSameIndividualAxiom axiom);

    /**
     * visit OWLSubPropertyChainOfAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom);

    /**
     * visit OWLInverseObjectPropertiesAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom);

    /**
     * visit OWLHasKeyAxiom type
     * 
     * @param axiom
     *        axiom to visit
     */
    void visit(@Nonnull OWLHasKeyAxiom axiom);

    /**
     * visit SWRLRule type
     * 
     * @param rule
     *        rule to visit
     */
    void visit(@Nonnull SWRLRule rule);
}
