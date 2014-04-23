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
 * @param <T>
 *        return type
 * @since 4.0.0
 */
public interface OWLProfileViolationVisitorEx<T> {

    /**
     * @param v
     *        IllegalPunning to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull IllegalPunning v);

    /**
     * @param v
     *        CycleInDatatypeDefinition to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull CycleInDatatypeDefinition v);

    /**
     * @param v
     *        UseOfBuiltInDatatypeInDatatypeDefinition to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfBuiltInDatatypeInDatatypeDefinition v);

    /**
     * @param v
     *        DatatypeIRIAlsoUsedAsClassIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull DatatypeIRIAlsoUsedAsClassIRI v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInCardinalityRestriction to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSimplePropertyInCardinalityRestriction v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInDisjointPropertiesAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSimplePropertyInDisjointPropertiesAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInFunctionalPropertyAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSimplePropertyInFunctionalPropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom to
     *        visit
     * @return visitor return value
     */
    @Nonnull
            T
            visit(@Nonnull UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInIrreflexivePropertyAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSimplePropertyInIrreflexivePropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInObjectHasSelf to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSimplePropertyInObjectHasSelf v);

    /**
     * @param v
     *        UseOfPropertyInChainCausesCycle to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfPropertyInChainCausesCycle v);

    /**
     * @param v
     *        UseOfReservedVocabularyForAnnotationPropertyIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForAnnotationPropertyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForClassIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForClassIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForDataPropertyIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForDataPropertyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForIndividualIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForIndividualIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForObjectPropertyIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForObjectPropertyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForOntologyIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForOntologyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForVersionIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfReservedVocabularyForVersionIRI v);

    /**
     * @param v
     *        UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v);

    /**
     * @param v
     *        UseOfUndeclaredAnnotationProperty to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfUndeclaredAnnotationProperty v);

    /**
     * @param v
     *        UseOfUndeclaredClass to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfUndeclaredClass v);

    /**
     * @param v
     *        UseOfUndeclaredDataProperty to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfUndeclaredDataProperty v);

    /**
     * @param v
     *        UseOfUndeclaredDatatype to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfUndeclaredDatatype v);

    /**
     * @param v
     *        UseOfUndeclaredObjectProperty to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfUndeclaredObjectProperty v);

    /**
     * @param v
     *        InsufficientPropertyExpressions to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull InsufficientPropertyExpressions v);

    /**
     * @param v
     *        InsufficientIndividuals to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull InsufficientIndividuals v);

    /**
     * @param v
     *        InsufficientOperands to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull InsufficientOperands v);

    /**
     * @param v
     *        EmptyOneOfAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull EmptyOneOfAxiom v);

    /**
     * @param v
     *        LastPropertyInChainNotInImposedRange to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull LastPropertyInChainNotInImposedRange v);

    /**
     * @param v
     *        OntologyIRINotAbsolute to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull OntologyIRINotAbsolute v);

    /**
     * @param v
     *        UseOfDefinedDatatypeInDatatypeRestriction to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfDefinedDatatypeInDatatypeRestriction v);

    /**
     * @param v
     *        UseOfIllegalClassExpression to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfIllegalClassExpression v);

    /**
     * @param v
     *        UseOfIllegalDataRange to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfIllegalDataRange v);

    /**
     * @param v
     *        UseOfUnknownDatatype to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfUnknownDatatype v);

    /**
     * @param v
     *        UseOfObjectPropertyInverse to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfObjectPropertyInverse v);

    /**
     * @param v
     *        UseOfNonSuperClassExpression to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSuperClassExpression v);

    /**
     * @param v
     *        UseOfNonSubClassExpression to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonSubClassExpression v);

    /**
     * @param v
     *        UseOfNonEquivalentClassExpression to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonEquivalentClassExpression v);

    /**
     * @param v
     *        UseOfNonAtomicClassExpression to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonAtomicClassExpression v);

    /**
     * @param v
     *        LexicalNotInLexicalSpace to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull LexicalNotInLexicalSpace v);

    /**
     * @param v
     *        OntologyVersionIRINotAbsolute to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull OntologyVersionIRINotAbsolute v);

    /**
     * @param v
     *        UseOfAnonymousIndividual to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfAnonymousIndividual v);

    /**
     * @param v
     *        UseOfIllegalAxiom to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfIllegalAxiom v);

    /**
     * @param v
     *        UseOfIllegalFacetRestriction to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfIllegalFacetRestriction v);

    /**
     * @param v
     *        UseOfNonAbsoluteIRI to visit
     * @return visitor return value
     */
    @Nonnull
    T visit(@Nonnull UseOfNonAbsoluteIRI v);
}
