/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapitools.profiles;

import org.semanticweb.owlapitools.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapitools.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapitools.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapitools.profiles.violations.IllegalPunning;
import org.semanticweb.owlapitools.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapitools.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapitools.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapitools.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapitools.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapitools.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapitools.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapitools.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapitools.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapitools.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapitools.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapitools.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapitools.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForClassIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForDataPropertyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapitools.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapitools.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapitools.profiles.violations.UseOfUnknownDatatype;

/** @author Matthew Horridge, The University of Manchester, Information Management
 *         Group
 * @param <T>
 *            return type */
public interface OWLProfileViolationVisitorEx<T> {
    /** @param v
     *            IllegalPunning to visit
     * @return visitor retur value */
    T visit(IllegalPunning v);

    /** @param v
     *            CycleInDatatypeDefinition to visit
     * @return visitor retur value */
    T visit(CycleInDatatypeDefinition v);

    /** @param v
     *            UseOfBuiltInDatatypeInDatatypeDefinition to visit
     * @return visitor retur value */
    T visit(UseOfBuiltInDatatypeInDatatypeDefinition v);

    /** @param v
     *            DatatypeIRIAlsoUsedAsClassIRI to visit
     * @return visitor retur value */
    T visit(DatatypeIRIAlsoUsedAsClassIRI v);

    /** @param v
     *            UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v);

    /** @param v
     *            UseOfNonSimplePropertyInCardinalityRestriction to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInCardinalityRestriction v);

    /** @param v
     *            UseOfNonSimplePropertyInDisjointPropertiesAxiom to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v);

    /** @param v
     *            UseOfNonSimplePropertyInFunctionalPropertyAxiom to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v);

    /** @param v
     *            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom
     *            to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v);

    /** @param v
     *            UseOfNonSimplePropertyInIrreflexivePropertyAxiom to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v);

    /** @param v
     *            UseOfNonSimplePropertyInObjectHasSelf to visit
     * @return visitor retur value */
    T visit(UseOfNonSimplePropertyInObjectHasSelf v);

    /** @param v
     *            UseOfPropertyInChainCausesCycle to visit
     * @return visitor retur value */
    T visit(UseOfPropertyInChainCausesCycle v);

    /** @param v
     *            UseOfReservedVocabularyForAnnotationPropertyIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForAnnotationPropertyIRI v);

    /** @param v
     *            UseOfReservedVocabularyForClassIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForClassIRI v);

    /** @param v
     *            UseOfReservedVocabularyForDataPropertyIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForDataPropertyIRI v);

    /** @param v
     *            UseOfReservedVocabularyForIndividualIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForIndividualIRI v);

    /** @param v
     *            UseOfReservedVocabularyForObjectPropertyIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForObjectPropertyIRI v);

    /** @param v
     *            UseOfReservedVocabularyForOntologyIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForOntologyIRI v);

    /** @param v
     *            UseOfReservedVocabularyForVersionIRI to visit
     * @return visitor retur value */
    T visit(UseOfReservedVocabularyForVersionIRI v);

    /** @param v
     *            UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom to visit
     * @return visitor retur value */
    T visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v);

    /** @param v
     *            UseOfUndeclaredAnnotationProperty to visit
     * @return visitor retur value */
    T visit(UseOfUndeclaredAnnotationProperty v);

    /** @param v
     *            UseOfUndeclaredClass to visit
     * @return visitor retur value */
    T visit(UseOfUndeclaredClass v);

    /** @param v
     *            UseOfUndeclaredDataProperty to visit
     * @return visitor retur value */
    T visit(UseOfUndeclaredDataProperty v);

    /** @param v
     *            UseOfUndeclaredDatatype to visit
     * @return visitor retur value */
    T visit(UseOfUndeclaredDatatype v);

    /** @param v
     *            UseOfUndeclaredObjectProperty to visit
     * @return visitor retur value */
    T visit(UseOfUndeclaredObjectProperty v);

    /** @param v
     *            InsufficientPropertyExpressions to visit
     * @return visitor retur value */
    T visit(InsufficientPropertyExpressions v);

    /** @param v
     *            InsufficientIndividuals to visit
     * @return visitor retur value */
    T visit(InsufficientIndividuals v);

    /** @param v
     *            InsufficientOperands to visit
     * @return visitor retur value */
    T visit(InsufficientOperands v);

    /** @param v
     *            EmptyOneOfAxiom to visit
     * @return visitor retur value */
    T visit(EmptyOneOfAxiom v);

    /** @param v
     *            LastPropertyInChainNotInImposedRange to visit
     * @return visitor retur value */
    T visit(LastPropertyInChainNotInImposedRange v);

    /** @param v
     *            OntologyIRINotAbsolute to visit
     * @return visitor retur value */
    T visit(OntologyIRINotAbsolute v);

    /** @param v
     *            UseOfDefinedDatatypeInDatatypeRestriction to visit
     * @return visitor retur value */
    T visit(UseOfDefinedDatatypeInDatatypeRestriction v);

    /** @param v
     *            UseOfIllegalClassExpression to visit
     * @return visitor retur value */
    T visit(UseOfIllegalClassExpression v);

    /** @param v
     *            UseOfIllegalDataRange to visit
     * @return visitor retur value */
    T visit(UseOfIllegalDataRange v);

    /** @param v
     *            UseOfUnknownDatatype to visit
     * @return visitor retur value */
    T visit(UseOfUnknownDatatype v);

    /** @param v
     *            UseOfObjectPropertyInverse to visit
     * @return visitor retur value */
    T visit(UseOfObjectPropertyInverse v);

    /** @param v
     *            UseOfNonSuperClassExpression to visit
     * @return visitor retur value */
    T visit(UseOfNonSuperClassExpression v);

    /** @param v
     *            UseOfNonSubClassExpression to visit
     * @return visitor retur value */
    T visit(UseOfNonSubClassExpression v);

    /** @param v
     *            UseOfNonEquivalentClassExpression to visit
     * @return visitor retur value */
    T visit(UseOfNonEquivalentClassExpression v);

    /** @param v
     *            UseOfNonAtomicClassExpression to visit
     * @return visitor retur value */
    T visit(UseOfNonAtomicClassExpression v);

    /** @param v
     *            LexicalNotInLexicalSpace to visit
     * @return visitor retur value */
    T visit(LexicalNotInLexicalSpace v);

    /** @param v
     *            OntologyVersionIRINotAbsolute to visit
     * @return visitor retur value */
    T visit(OntologyVersionIRINotAbsolute v);

    /** @param v
     *            UseOfAnonymousIndividual to visit
     * @return visitor retur value */
    T visit(UseOfAnonymousIndividual v);

    /** @param v
     *            UseOfIllegalAxiom to visit
     * @return visitor retur value */
    T visit(UseOfIllegalAxiom v);

    /** @param v
     *            UseOfIllegalFacetRestriction to visit
     * @return visitor retur value */
    T visit(UseOfIllegalFacetRestriction v);

    /** @param v
     *            UseOfNonAbsoluteIRI to visit
     * @return visitor retur value */
    T visit(UseOfNonAbsoluteIRI v);
}
