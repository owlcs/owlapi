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
package org.semanticweb.owlapi.profiles;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 03-Aug-2009
 */
public interface OWL2DLProfileViolationVisitor {

    /**
     * visit IllegalPunning type
     * 
     * @param violation
     *        object to visit
     */
    void visit(IllegalPunning violation);

    /**
     * visit CycleInDatatypeDefinition type
     * 
     * @param violation
     *        object to visit
     */
    void visit(CycleInDatatypeDefinition violation);

    /**
     * visit UseOfBuiltInDatatypeInDatatypeDefinition type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfBuiltInDatatypeInDatatypeDefinition violation);

    /**
     * visit DatatypeIRIAlsoUsedAsClassIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(DatatypeIRIAlsoUsedAsClassIRI violation);

    /**
     * visit UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom violation);

    /**
     * visit UseOfNonSimplePropertyInCardinalityRestriction type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfNonSimplePropertyInCardinalityRestriction violation);

    /**
     * visit UseOfNonSimplePropertyInDisjointPropertiesAxiom type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom violation);

    /**
     * visit UseOfNonSimplePropertyInFunctionalPropertyAxiom type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom violation);

    /**
     * visit UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom type
     * 
     * @param violation
     *        object to visit
     */
            void
            visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom violation);

    /**
     * visit UseOfNonSimplePropertyInIrreflexivePropertyAxiom type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom violation);

    /**
     * visit UseOfNonSimplePropertyInObjectHasSelf type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfNonSimplePropertyInObjectHasSelf violation);

    /**
     * visit UseOfPropertyInChainCausesCycle type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfPropertyInChainCausesCycle violation);

    /**
     * visit UseOfReservedVocabularyForAnnotationPropertyIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForAnnotationPropertyIRI violation);

    /**
     * visit UseOfReservedVocabularyForClassIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForClassIRI violation);

    /**
     * visit UseOfReservedVocabularyForDataPropertyIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForDataPropertyIRI violation);

    /**
     * visit UseOfReservedVocabularyForIndividualIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForIndividualIRI violation);

    /**
     * visit UseOfReservedVocabularyForObjectPropertyIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForObjectPropertyIRI violation);

    /**
     * visit UseOfReservedVocabularyForOntologyIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForOntologyIRI violation);

    /**
     * visit UseOfReservedVocabularyForVersionIRI type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfReservedVocabularyForVersionIRI violation);

    /**
     * visit UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom violation);

    /**
     * visit UseOfUndeclaredAnnotationProperty type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfUndeclaredAnnotationProperty violation);

    /**
     * visit UseOfUndeclaredClass type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfUndeclaredClass violation);

    /**
     * visit UseOfUndeclaredDataProperty type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfUndeclaredDataProperty violation);

    /**
     * visit UseOfUndeclaredDatatype type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfUndeclaredDatatype violation);

    /**
     * visit UseOfUndeclaredObjectProperty type
     * 
     * @param violation
     *        object to visit
     */
    void visit(UseOfUndeclaredObjectProperty violation);

    /**
     * visit InsufficientPropertyExpressions type
     * 
     * @param violation
     *        object to visit
     */
    void visit(InsufficientPropertyExpressions violation);

    /**
     * visit InsufficientIndividuals type
     * 
     * @param violation
     *        object to visit
     */
    void visit(InsufficientIndividuals violation);

    /**
     * visit InsufficientOperands type
     * 
     * @param violation
     *        object to visit
     */
    void visit(InsufficientOperands violation);

    /**
     * visit EmptyOneOfAxiom type
     * 
     * @param violation
     *        object to visit
     */
    void visit(EmptyOneOfAxiom violation);
}
