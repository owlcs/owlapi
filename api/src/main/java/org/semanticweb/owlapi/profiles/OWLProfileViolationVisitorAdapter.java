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

/** @author ignazio */
public class OWLProfileViolationVisitorAdapter implements
        OWLProfileViolationVisitor {

    /**
     * override this method in subclasses to change default behaviour
     * 
     * @param v
     *        violation
     */
    protected void doDefault(
            @SuppressWarnings("unused") OWLProfileViolation<?> v) {}

    @Override
    public void visit(IllegalPunning v) {
        doDefault(v);
    }

    @Override
    public void visit(CycleInDatatypeDefinition v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfBuiltInDatatypeInDatatypeDefinition v) {
        doDefault(v);
    }

    @Override
    public void visit(DatatypeIRIAlsoUsedAsClassIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInCardinalityRestriction v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(
            UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSimplePropertyInObjectHasSelf v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfPropertyInChainCausesCycle v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForAnnotationPropertyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForClassIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForDataPropertyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForIndividualIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForObjectPropertyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForOntologyIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfReservedVocabularyForVersionIRI v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredAnnotationProperty v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredClass v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredDataProperty v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredDatatype v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUndeclaredObjectProperty v) {
        doDefault(v);
    }

    @Override
    public void visit(InsufficientPropertyExpressions v) {
        doDefault(v);
    }

    @Override
    public void visit(InsufficientIndividuals v) {
        doDefault(v);
    }

    @Override
    public void visit(InsufficientOperands v) {
        doDefault(v);
    }

    @Override
    public void visit(EmptyOneOfAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(LastPropertyInChainNotInImposedRange v) {
        doDefault(v);
    }

    @Override
    public void visit(OntologyIRINotAbsolute v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfDefinedDatatypeInDatatypeRestriction v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalDataRange v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfUnknownDatatype v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfObjectPropertyInverse v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSuperClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonSubClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonEquivalentClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonAtomicClassExpression v) {
        doDefault(v);
    }

    @Override
    public void visit(LexicalNotInLexicalSpace v) {
        doDefault(v);
    }

    @Override
    public void visit(OntologyVersionIRINotAbsolute v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfAnonymousIndividual v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalAxiom v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfIllegalFacetRestriction v) {
        doDefault(v);
    }

    @Override
    public void visit(UseOfNonAbsoluteIRI v) {
        doDefault(v);
    }
}
