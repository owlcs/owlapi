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
     * @param v
     *        IllegalPunning to visit
     */
    void visit(IllegalPunning v);

    /**
     * @param v
     *        CycleInDatatypeDefinition to visit
     */
    void visit(CycleInDatatypeDefinition v);

    /**
     * @param v
     *        UseOfBuiltInDatatypeInDatatypeDefinition to visit
     */
    void visit(UseOfBuiltInDatatypeInDatatypeDefinition v);

    /**
     * @param v
     *        DatatypeIRIAlsoUsedAsClassIRI to visit
     */
    void visit(DatatypeIRIAlsoUsedAsClassIRI v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom to visit
     */
    void visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInCardinalityRestriction to visit
     */
    void visit(UseOfNonSimplePropertyInCardinalityRestriction v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInDisjointPropertiesAxiom to visit
     */
    void visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInFunctionalPropertyAxiom to visit
     */
    void visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom to
     *        visit
     */
    void visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInIrreflexivePropertyAxiom to visit
     */
    void visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v);

    /**
     * @param v
     *        UseOfNonSimplePropertyInObjectHasSelf to visit
     */
    void visit(UseOfNonSimplePropertyInObjectHasSelf v);

    /**
     * @param v
     *        UseOfPropertyInChainCausesCycle to visit
     */
    void visit(UseOfPropertyInChainCausesCycle v);

    /**
     * @param v
     *        UseOfReservedVocabularyForAnnotationPropertyIRI to visit
     */
    void visit(UseOfReservedVocabularyForAnnotationPropertyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForClassIRI to visit
     */
    void visit(UseOfReservedVocabularyForClassIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForDataPropertyIRI to visit
     */
    void visit(UseOfReservedVocabularyForDataPropertyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForIndividualIRI to visit
     */
    void visit(UseOfReservedVocabularyForIndividualIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForObjectPropertyIRI to visit
     */
    void visit(UseOfReservedVocabularyForObjectPropertyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForOntologyIRI to visit
     */
    void visit(UseOfReservedVocabularyForOntologyIRI v);

    /**
     * @param v
     *        UseOfReservedVocabularyForVersionIRI to visit
     */
    void visit(UseOfReservedVocabularyForVersionIRI v);

    /**
     * @param v
     *        UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom to visit
     */
    void visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v);

    /**
     * @param v
     *        UseOfUndeclaredAnnotationProperty to visit
     */
    void visit(UseOfUndeclaredAnnotationProperty v);

    /**
     * @param v
     *        UseOfUndeclaredClass to visit
     */
    void visit(UseOfUndeclaredClass v);

    /**
     * @param v
     *        UseOfUndeclaredDataProperty to visit
     */
    void visit(UseOfUndeclaredDataProperty v);

    /**
     * @param v
     *        UseOfUndeclaredDatatype to visit
     */
    void visit(UseOfUndeclaredDatatype v);

    /**
     * @param v
     *        UseOfUndeclaredObjectProperty to visit
     */
    void visit(UseOfUndeclaredObjectProperty v);

    /**
     * @param v
     *        InsufficientPropertyExpressions to visit
     */
    void visit(InsufficientPropertyExpressions v);

    /**
     * @param v
     *        InsufficientIndividuals to visit
     */
    void visit(InsufficientIndividuals v);

    /**
     * @param v
     *        InsufficientOperands to visit
     */
    void visit(InsufficientOperands v);

    /**
     * @param v
     *        EmptyOneOfAxiom to visit
     */
    void visit(EmptyOneOfAxiom v);

    /**
     * @param v
     *        LastPropertyInChainNotInImposedRange to visit
     */
    void visit(LastPropertyInChainNotInImposedRange v);

    /**
     * @param v
     *        OntologyIRINotAbsolute to visit
     */
    void visit(OntologyIRINotAbsolute v);

    /**
     * @param v
     *        UseOfDefinedDatatypeInDatatypeRestriction to visit
     */
    void visit(UseOfDefinedDatatypeInDatatypeRestriction v);

    /**
     * @param v
     *        UseOfIllegalClassExpression to visit
     */
    void visit(UseOfIllegalClassExpression v);

    /**
     * @param v
     *        UseOfIllegalDataRange to visit
     */
    void visit(UseOfIllegalDataRange v);

    /**
     * @param v
     *        UseOfUnknownDatatype to visit
     */
    void visit(UseOfUnknownDatatype v);

    /**
     * @param v
     *        UseOfObjectPropertyInverse to visit
     */
    void visit(UseOfObjectPropertyInverse v);

    /**
     * @param v
     *        UseOfNonSuperClassExpression to visit
     */
    void visit(UseOfNonSuperClassExpression v);

    /**
     * @param v
     *        UseOfNonSubClassExpression to visit
     */
    void visit(UseOfNonSubClassExpression v);

    /**
     * @param v
     *        UseOfNonEquivalentClassExpression to visit
     */
    void visit(UseOfNonEquivalentClassExpression v);

    /**
     * @param v
     *        UseOfNonAtomicClassExpression to visit
     */
    void visit(UseOfNonAtomicClassExpression v);

    /**
     * @param v
     *        LexicalNotInLexicalSpace to visit
     */
    void visit(LexicalNotInLexicalSpace v);

    /**
     * @param v
     *        OntologyVersionIRINotAbsolute to visit
     */
    void visit(OntologyVersionIRINotAbsolute v);

    /**
     * @param v
     *        UseOfAnonymousIndividual to visit
     */
    void visit(UseOfAnonymousIndividual v);

    /**
     * @param v
     *        UseOfIllegalAxiom to visit
     */
    void visit(UseOfIllegalAxiom v);

    /**
     * @param v
     *        UseOfIllegalFacetRestriction to visit
     */
    void visit(UseOfIllegalFacetRestriction v);

    /**
     * @param v
     *        UseOfNonAbsoluteIRI to visit
     */
    void visit(UseOfNonAbsoluteIRI v);
}
