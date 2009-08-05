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

    void visit(CycleInDatatypeDefinition violation);

    void visit(UseOfBuiltInDatatypeInDatatypeDefinition violation);

    void visit(DatatypeIRIAlsoUsedAsClassIRI violation);

    void visit(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom violation);

    void visit(UseOfNonSimplePropertyInCardinalityRestriction violation);

    void visit(UseOfNonSimplePropertyInDisjointPropertiesAxiom violation);

    void visit(UseOfNonSimplePropertyInFunctionalPropertyAxiom violation);

    void visit(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom violation);

    void visit(UseOfNonSimplePropertyInIrreflexivePropertyAxiom violation);

    void visit(UseOfNonSimplePropertyInObjectHasSelf violation);

    void visit(UseOfPropertyInChainCausesCycle violation);

    void visit(UseOfReservedVocabularyForAnnotationPropertyIRI violation);

    void visit(UseOfReservedVocabularyForClassIRI violation);

    void visit(UseOfReservedVocabularyForDataPropertyIRI violation);

    void visit(UseOfReservedVocabularyForIndividualIRI violation);

    void visit(UseOfReservedVocabularyForObjectPropertyIRI violation);

    void visit(UseOfReservedVocabularyForOntologyIRI violation);

    void visit(UseOfReservedVocabularyForVersionIRI violation);

    void visit(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom violation);

    void visit(UseOfUndeclaredAnnotationProperty violation);

    void visit(UseOfUndeclaredClass violation);

    void visit(UseOfUndeclaredDataProperty violation);

    void visit(UseOfUndeclaredDatatype violation);

    void visit(UseOfUndeclaredObjectProperty violation);
}
