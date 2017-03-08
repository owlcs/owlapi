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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;

import java.util.Optional;

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
 * @param <T> return type
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 4.0.0
 */
public interface OWLProfileViolationVisitorEx<T> {

    /**
     * Gets the default return value for this visitor.
     *
     * @param object The object that was visited.
     * @return The default return value
     */
    default Optional<T> doDefault(@SuppressWarnings("unused") OWLProfileViolation object) {
        return emptyOptional();
    }

    /**
     * @param v IllegalPunning to visit
     * @return visitor return value
     */
    default Optional<T> visit(IllegalPunning v) {
        return doDefault(v);
    }

    /**
     * @param v CycleInDatatypeDefinition to visit
     * @return visitor return value
     */
    default Optional<T> visit(CycleInDatatypeDefinition v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfBuiltInDatatypeInDatatypeDefinition to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfBuiltInDatatypeInDatatypeDefinition v) {
        return doDefault(v);
    }

    /**
     * @param v DatatypeIRIAlsoUsedAsClassIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(DatatypeIRIAlsoUsedAsClassIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInCardinalityRestriction to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInCardinalityRestriction v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInDisjointPropertiesAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInFunctionalPropertyAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInIrreflexivePropertyAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSimplePropertyInObjectHasSelf to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSimplePropertyInObjectHasSelf v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfPropertyInChainCausesCycle to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfPropertyInChainCausesCycle v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForAnnotationPropertyIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForAnnotationPropertyIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForClassIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForClassIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForDataPropertyIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForDataPropertyIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForIndividualIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForIndividualIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForObjectPropertyIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForObjectPropertyIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForOntologyIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForOntologyIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfReservedVocabularyForVersionIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfReservedVocabularyForVersionIRI v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfUndeclaredAnnotationProperty to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfUndeclaredAnnotationProperty v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfUndeclaredClass to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfUndeclaredClass v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfUndeclaredDataProperty to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfUndeclaredDataProperty v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfUndeclaredDatatype to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfUndeclaredDatatype v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfUndeclaredObjectProperty to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfUndeclaredObjectProperty v) {
        return doDefault(v);
    }

    /**
     * @param v InsufficientPropertyExpressions to visit
     * @return visitor return value
     */
    default Optional<T> visit(InsufficientPropertyExpressions v) {
        return doDefault(v);
    }

    /**
     * @param v InsufficientIndividuals to visit
     * @return visitor return value
     */
    default Optional<T> visit(InsufficientIndividuals v) {
        return doDefault(v);
    }

    /**
     * @param v InsufficientOperands to visit
     * @return visitor return value
     */
    default Optional<T> visit(InsufficientOperands v) {
        return doDefault(v);
    }

    /**
     * @param v EmptyOneOfAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(EmptyOneOfAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v LastPropertyInChainNotInImposedRange to visit
     * @return visitor return value
     */
    default Optional<T> visit(LastPropertyInChainNotInImposedRange v) {
        return doDefault(v);
    }

    /**
     * @param v OntologyIRINotAbsolute to visit
     * @return visitor return value
     */
    default Optional<T> visit(OntologyIRINotAbsolute v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfDefinedDatatypeInDatatypeRestriction to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfDefinedDatatypeInDatatypeRestriction v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfIllegalClassExpression to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfIllegalClassExpression v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfIllegalDataRange to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfIllegalDataRange v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfUnknownDatatype to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfUnknownDatatype v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfObjectPropertyInverse to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfObjectPropertyInverse v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSuperClassExpression to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSuperClassExpression v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonSubClassExpression to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonSubClassExpression v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonEquivalentClassExpression to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonEquivalentClassExpression v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonAtomicClassExpression to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonAtomicClassExpression v) {
        return doDefault(v);
    }

    /**
     * @param v LexicalNotInLexicalSpace to visit
     * @return visitor return value
     */
    default Optional<T> visit(LexicalNotInLexicalSpace v) {
        return doDefault(v);
    }

    /**
     * @param v OntologyVersionIRINotAbsolute to visit
     * @return visitor return value
     */
    default Optional<T> visit(OntologyVersionIRINotAbsolute v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfAnonymousIndividual to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfAnonymousIndividual v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfIllegalAxiom to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfIllegalAxiom v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfIllegalFacetRestriction to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfIllegalFacetRestriction v) {
        return doDefault(v);
    }

    /**
     * @param v UseOfNonAbsoluteIRI to visit
     * @return visitor return value
     */
    default Optional<T> visit(UseOfNonAbsoluteIRI v) {
        return doDefault(v);
    }
}
