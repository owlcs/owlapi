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
package org.semanticweb.owlapi.profiles;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapi.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapi.profiles.violations.IllegalPunning;
import org.semanticweb.owlapi.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapi.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapi.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapi.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapi.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapi.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapi.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapi.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForClassIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForDataPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUnknownDatatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 */
public interface OWLProfileViolationVisitor {

    /**
     * @param object
     *        object to visit @deprecated use doDefault() instead
     */
    @Deprecated
    default void getDefaultReturnValue(@Nonnull OWLProfileViolation object) {
        doDefault(object);
    }

    /**
     * Default action for the visitor.
     * 
     * @param object
     *        The object that was visited.
     */
    default void doDefault(
            @SuppressWarnings("unused") @Nonnull OWLProfileViolation object) {}

    /**
     * @param v
     *        IllegalPunning to visit
     */
    default void visit(IllegalPunning v) {
        doDefault(v);
    }

    /**
     * @param v
     *        CycleInDatatypeDefinition to visit
     */
    default void visit(CycleInDatatypeDefinition v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfBuiltInDatatypeInDatatypeDefinition to visit
     */
    default void visit(UseOfBuiltInDatatypeInDatatypeDefinition v) {
        doDefault(v);
    }

    /**
     * @param v
     *        DatatypeIRIAlsoUsedAsClassIRI to visit
     */
    default void visit(DatatypeIRIAlsoUsedAsClassIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom to visit
     */
    default void visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInCardinalityRestriction to visit
     */
    default void visit(UseOfNonSimplePropertyInCardinalityRestriction v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInDisjointPropertiesAxiom to visit
     */
    default void visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInFunctionalPropertyAxiom to visit
     */
    default void visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom to
     *        visit
     */
    default void visit(
            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInIrreflexivePropertyAxiom to visit
     */
    default void visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSimplePropertyInObjectHasSelf to visit
     */
    default void visit(UseOfNonSimplePropertyInObjectHasSelf v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfPropertyInChainCausesCycle to visit
     */
    default void visit(UseOfPropertyInChainCausesCycle v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForAnnotationPropertyIRI to visit
     */
    default void visit(UseOfReservedVocabularyForAnnotationPropertyIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForClassIRI to visit
     */
    default void visit(UseOfReservedVocabularyForClassIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForDataPropertyIRI to visit
     */
    default void visit(UseOfReservedVocabularyForDataPropertyIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForIndividualIRI to visit
     */
    default void visit(UseOfReservedVocabularyForIndividualIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForObjectPropertyIRI to visit
     */
    default void visit(UseOfReservedVocabularyForObjectPropertyIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForOntologyIRI to visit
     */
    default void visit(UseOfReservedVocabularyForOntologyIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfReservedVocabularyForVersionIRI to visit
     */
    default void visit(UseOfReservedVocabularyForVersionIRI v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom to visit
     */
    default void visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfUndeclaredAnnotationProperty to visit
     */
    default void visit(UseOfUndeclaredAnnotationProperty v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfUndeclaredClass to visit
     */
    default void visit(UseOfUndeclaredClass v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfUndeclaredDataProperty to visit
     */
    default void visit(UseOfUndeclaredDataProperty v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfUndeclaredDatatype to visit
     */
    default void visit(UseOfUndeclaredDatatype v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfUndeclaredObjectProperty to visit
     */
    default void visit(UseOfUndeclaredObjectProperty v) {
        doDefault(v);
    }

    /**
     * @param v
     *        InsufficientPropertyExpressions to visit
     */
    default void visit(InsufficientPropertyExpressions v) {
        doDefault(v);
    }

    /**
     * @param v
     *        InsufficientIndividuals to visit
     */
    default void visit(InsufficientIndividuals v) {
        doDefault(v);
    }

    /**
     * @param v
     *        InsufficientOperands to visit
     */
    default void visit(InsufficientOperands v) {
        doDefault(v);
    }

    /**
     * @param v
     *        EmptyOneOfAxiom to visit
     */
    default void visit(EmptyOneOfAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        LastPropertyInChainNotInImposedRange to visit
     */
    default void visit(LastPropertyInChainNotInImposedRange v) {
        doDefault(v);
    }

    /**
     * @param v
     *        OntologyIRINotAbsolute to visit
     */
    default void visit(OntologyIRINotAbsolute v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfDefinedDatatypeInDatatypeRestriction to visit
     */
    default void visit(UseOfDefinedDatatypeInDatatypeRestriction v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfIllegalClassExpression to visit
     */
    default void visit(UseOfIllegalClassExpression v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfIllegalDataRange to visit
     */
    default void visit(UseOfIllegalDataRange v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfUnknownDatatype to visit
     */
    default void visit(UseOfUnknownDatatype v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfObjectPropertyInverse to visit
     */
    default void visit(UseOfObjectPropertyInverse v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSuperClassExpression to visit
     */
    default void visit(UseOfNonSuperClassExpression v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonSubClassExpression to visit
     */
    default void visit(UseOfNonSubClassExpression v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonEquivalentClassExpression to visit
     */
    default void visit(UseOfNonEquivalentClassExpression v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonAtomicClassExpression to visit
     */
    default void visit(UseOfNonAtomicClassExpression v) {
        doDefault(v);
    }

    /**
     * @param v
     *        LexicalNotInLexicalSpace to visit
     */
    default void visit(LexicalNotInLexicalSpace v) {
        doDefault(v);
    }

    /**
     * @param v
     *        OntologyVersionIRINotAbsolute to visit
     */
    default void visit(OntologyVersionIRINotAbsolute v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfAnonymousIndividual to visit
     */
    default void visit(UseOfAnonymousIndividual v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfIllegalAxiom to visit
     */
    default void visit(UseOfIllegalAxiom v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfIllegalFacetRestriction to visit
     */
    default void visit(UseOfIllegalFacetRestriction v) {
        doDefault(v);
    }

    /**
     * @param v
     *        UseOfNonAbsoluteIRI to visit
     */
    default void visit(UseOfNonAbsoluteIRI v) {
        doDefault(v);
    }
}
