package org.semanticweb.owlapi.profiles;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public interface OWL2DLProfileViolationVisitor {

    void accept(CycleInDatatypeDefinition violation);

    void accept(UseOfBuiltInDatatypeInDatatypeDefinition violation);

    void accept(UseOfDatatypeIRIForClassIRI violation);

    void accept(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom violation);

    void accept(UseOfNonSimplePropertyInCardinalityRestriction violation);

    void accept(UseOfNonSimplePropertyInDisjointPropertiesAxiom violation);

    void accept(UseOfNonSimplePropertyInFunctionalPropertyAxiom violation);

    void accept(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom violation);

    void accept(UseOfNonSimplePropertyInIrreflexivePropertyAxiom violation);

    void accept(UseOfNonSimplePropertyInObjectHasSelf violation);

    void accept(UseOfPropertyInChainCausesCycle violation);

    void accept(UseOfReservedVocabularyForAnnotationPropertyIRI violation);

    void accept(UseOfReservedVocabularyForClassIRI violation);

    void accept(UseOfReservedVocabularyForDataPropertyIRI violation);

    void accept(UseOfReservedVocabularyForIndividualIRI violation);

    void accept(UseOfReservedVocabularyForObjectPropertyIRI violation);

    void accept(UseOfReservedVocabularyForOntologyIRI violation);

    void accept(UseOfReservedVocabularyForVersionIRI violation);

    void accept(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom violation);

    void accept(UseOfUndeclaredAnnotationProperty violation);

    void accept(UseOfUndeclaredClass violation);

    void accept(UseOfUndeclaredDataProperty violation);

    void accept(UseOfUndeclaredDatatype violation);

    void accept(UseOfUndeclaredObjectProperty violation);
}
