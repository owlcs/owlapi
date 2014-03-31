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
 * adapter class
 * 
 * @param <O>
 *        return type
 */
public class OWLProfileViolationVisitorExAdapter<O> implements
        OWLProfileViolationVisitorEx<O> {

    /**
     * override this method in subclasses to change default behaviour
     * 
     * @param v
     *        violation
     * @return default return value
     */
    protected O doDefault(@SuppressWarnings("unused") OWLProfileViolation<?> v) {
        return defaultValue;
    }

    private O defaultValue;

    /** default returned value is null */
    public OWLProfileViolationVisitorExAdapter() {
        this(null);
    }

    /**
     * @param o
     *        default return value
     */
    public OWLProfileViolationVisitorExAdapter(O o) {
        defaultValue = o;
    }

    @Override
    public O visit(IllegalPunning v) {
        return doDefault(v);
    }

    @Override
    public O visit(CycleInDatatypeDefinition v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfBuiltInDatatypeInDatatypeDefinition v) {
        return doDefault(v);
    }

    @Override
    public O visit(DatatypeIRIAlsoUsedAsClassIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInCardinalityRestriction v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(
            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSimplePropertyInObjectHasSelf v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfPropertyInChainCausesCycle v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForAnnotationPropertyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForClassIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForDataPropertyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForIndividualIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForObjectPropertyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForOntologyIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfReservedVocabularyForVersionIRI v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredAnnotationProperty v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredClass v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredDataProperty v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredDatatype v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUndeclaredObjectProperty v) {
        return doDefault(v);
    }

    @Override
    public O visit(InsufficientPropertyExpressions v) {
        return doDefault(v);
    }

    @Override
    public O visit(InsufficientIndividuals v) {
        return doDefault(v);
    }

    @Override
    public O visit(InsufficientOperands v) {
        return doDefault(v);
    }

    @Override
    public O visit(EmptyOneOfAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(LastPropertyInChainNotInImposedRange v) {
        return doDefault(v);
    }

    @Override
    public O visit(OntologyIRINotAbsolute v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfDefinedDatatypeInDatatypeRestriction v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalDataRange v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfUnknownDatatype v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfObjectPropertyInverse v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSuperClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonSubClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonEquivalentClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonAtomicClassExpression v) {
        return doDefault(v);
    }

    @Override
    public O visit(LexicalNotInLexicalSpace v) {
        return doDefault(v);
    }

    @Override
    public O visit(OntologyVersionIRINotAbsolute v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfAnonymousIndividual v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalAxiom v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfIllegalFacetRestriction v) {
        return doDefault(v);
    }

    @Override
    public O visit(UseOfNonAbsoluteIRI v) {
        return doDefault(v);
    }
}
