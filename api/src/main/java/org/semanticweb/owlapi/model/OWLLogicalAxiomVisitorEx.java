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
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public interface OWLLogicalAxiomVisitorEx<O> extends OWLObjectVisitorEx<O> {

    /**
     * @param axiom OWLSubClassOfAxiom to visit
     * @return visitor value
     */
    default O visitSubClassOfAxiom(OWLSubClassOfAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLNegativeObjectPropertyAssertionAxiom to visit
     * @return visitor value
     */
    default O visitNegativeObjectPropertyAssertionAxiom(
        OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLAsymmetricObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitAsymmetricObjectPropertyAxiom(OWLAsymmetricObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLReflexiveObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitReflexiveObjectPropertyAxiom(OWLReflexiveObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDisjointClassesAxiom to visit
     * @return visitor value
     */
    default O visitDisjointClassesAxiom(OWLDisjointClassesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDataPropertyDomainAxiom to visit
     * @return visitor value
     */
    default O visitDataPropertyDomainAxiom(OWLDataPropertyDomainAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLObjectPropertyDomainAxiom to visit
     * @return visitor value
     */
    default O visitObjectPropertyDomainAxiom(OWLObjectPropertyDomainAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLEquivalentObjectPropertiesAxiom to visit
     * @return visitor value
     */
    default O visitEquivalentObjectPropertiesAxiom(OWLEquivalentObjectPropertiesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLNegativeDataPropertyAssertionAxiom to visit
     * @return visitor value
     */
    default O visitNegativeDataPropertyAssertionAxiom(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDifferentIndividualsAxiom to visit
     * @return visitor value
     */
    default O visitDifferentIndividualsAxiom(OWLDifferentIndividualsAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDisjointDataPropertiesAxiom to visit
     * @return visitor value
     */
    default O visitDisjointDataPropertiesAxiom(OWLDisjointDataPropertiesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDisjointObjectPropertiesAxiom to visit
     * @return visitor value
     */
    default O visitDisjointObjectPropertiesAxiom(OWLDisjointObjectPropertiesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLObjectPropertyRangeAxiom to visit
     * @return visitor value
     */
    default O visitObjectPropertyRangeAxiom(OWLObjectPropertyRangeAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLObjectPropertyAssertionAxiom to visit
     * @return visitor value
     */
    default O visitObjectPropertyAssertionAxiom(OWLObjectPropertyAssertionAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLFunctionalObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitFunctionalObjectPropertyAxiom(OWLFunctionalObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLSubObjectPropertyOfAxiom to visit
     * @return visitor value
     */
    default O visitSubObjectPropertyOfAxiom(OWLSubObjectPropertyOfAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDisjointUnionAxiom to visit
     * @return visitor value
     */
    default O visitDisjointUnionAxiom(OWLDisjointUnionAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLSymmetricObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitSymmetricObjectPropertyAxiom(OWLSymmetricObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDataPropertyRangeAxiom to visit
     * @return visitor value
     */
    default O visitDataPropertyRangeAxiom(OWLDataPropertyRangeAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLFunctionalDataPropertyAxiom to visit
     * @return visitor value
     */
    default O visitFunctionalDataPropertyAxiom(OWLFunctionalDataPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLEquivalentDataPropertiesAxiom to visit
     * @return visitor value
     */
    default O visitEquivalentDataPropertiesAxiom(OWLEquivalentDataPropertiesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLClassAssertionAxiom to visit
     * @return visitor value
     */
    default O visitClassAssertionAxiom(OWLClassAssertionAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLEquivalentClassesAxiom to visit
     * @return visitor value
     */
    default O visitEquivalentClassesAxiom(OWLEquivalentClassesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLDataPropertyAssertionAxiom to visit
     * @return visitor value
     */
    default O visitDataPropertyAssertionAxiom(OWLDataPropertyAssertionAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLTransitiveObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitTransitiveObjectPropertyAxiom(OWLTransitiveObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLIrreflexiveObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitIrreflexiveObjectPropertyAxiom(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLSubDataPropertyOfAxiom to visit
     * @return visitor value
     */
    default O visitSubDataPropertyOfAxiom(OWLSubDataPropertyOfAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLInverseFunctionalObjectPropertyAxiom to visit
     * @return visitor value
     */
    default O visitInverseFunctionalObjectPropertyAxiom(
        OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLSameIndividualAxiom to visit
     * @return visitor value
     */
    default O visitSameIndividualAxiom(OWLSameIndividualAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLSubPropertyChainOfAxiom to visit
     * @return visitor value
     */
    default O visitSubPropertyChainOfAxiom(OWLSubPropertyChainOfAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLInverseObjectPropertiesAxiom to visit
     * @return visitor value
     */
    default O visitInverseObjectPropertiesAxiom(OWLInverseObjectPropertiesAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom OWLHasKeyAxiom to visit
     * @return visitor value
     */
    default O visitHasKeyAxiom(OWLHasKeyAxiom axiom) {
        return visit(axiom);
    }

    /**
     * @param axiom SWRLRule to visit
     * @return visitor value
     */
    default O visitSWRLRule(SWRLRule axiom) {
        return visit(axiom);
    }
}
