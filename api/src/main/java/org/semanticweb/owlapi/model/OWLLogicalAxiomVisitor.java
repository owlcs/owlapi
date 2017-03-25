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
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public interface OWLLogicalAxiomVisitor extends OWLObjectVisitor {

    /** @param axiom object to visit */
    default void visitSubClassOfAxiom(OWLSubClassOfAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitNegativeObjectPropertyAssertionAxiom(
        OWLNegativeObjectPropertyAssertionAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitAsymmetricObjectPropertyAxiom(OWLAsymmetricObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitReflexiveObjectPropertyAxiom(OWLReflexiveObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDisjointClassesAxiom(OWLDisjointClassesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDataPropertyDomainAxiom(OWLDataPropertyDomainAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitObjectPropertyDomainAxiom(OWLObjectPropertyDomainAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitEquivalentObjectPropertiesAxiom(OWLEquivalentObjectPropertiesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitNegativeDataPropertyAssertionAxiom(
        OWLNegativeDataPropertyAssertionAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDifferentIndividualsAxiom(OWLDifferentIndividualsAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDisjointDataPropertiesAxiom(OWLDisjointDataPropertiesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDisjointObjectPropertiesAxiom(OWLDisjointObjectPropertiesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitObjectPropertyRangeAxiom(OWLObjectPropertyRangeAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitObjectPropertyAssertionAxiom(OWLObjectPropertyAssertionAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitFunctionalObjectPropertyAxiom(OWLFunctionalObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitSubObjectPropertyOfAxiom(OWLSubObjectPropertyOfAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDisjointUnionAxiom(OWLDisjointUnionAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitSymmetricObjectPropertyAxiom(OWLSymmetricObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDataPropertyRangeAxiom(OWLDataPropertyRangeAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitFunctionalDataPropertyAxiom(OWLFunctionalDataPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitEquivalentDataPropertiesAxiom(OWLEquivalentDataPropertiesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitClassAssertionAxiom(OWLClassAssertionAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitEquivalentClassesAxiom(OWLEquivalentClassesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitDataPropertyAssertionAxiom(OWLDataPropertyAssertionAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitTransitiveObjectPropertyAxiom(OWLTransitiveObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitIrreflexiveObjectPropertyAxiom(OWLIrreflexiveObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitSubDataPropertyOfAxiom(OWLSubDataPropertyOfAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitInverseFunctionalObjectPropertyAxiom(
        OWLInverseFunctionalObjectPropertyAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitSameIndividualAxiom(OWLSameIndividualAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitSubPropertyChainOfAxiom(OWLSubPropertyChainOfAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitInverseObjectPropertiesAxiom(OWLInverseObjectPropertiesAxiom axiom) {
        visit(axiom);
    }

    /** @param axiom object to visit */
    default void visitHasKeyAxiom(OWLHasKeyAxiom axiom) {
        visit(axiom);
    }

    /** @param node rule to visit */
    default void visitSWRLRule(SWRLRule node) {
        visit(node);
    }
}
