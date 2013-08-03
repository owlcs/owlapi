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
package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br>
 * <br>
 * </p> An interface for objects that can accept visits from axioms. (See the <a
 * href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>) */
@SuppressWarnings("javadoc")
public interface OWLAxiomVisitor extends OWLAnnotationAxiomVisitor {
    void visit(@Nonnull OWLDeclarationAxiom axiom);

    void visit(@Nonnull OWLSubClassOfAxiom axiom);

    void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom);

    void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLDisjointClassesAxiom axiom);

    void visit(@Nonnull OWLDataPropertyDomainAxiom axiom);

    void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom);

    void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom);

    void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom);

    void visit(@Nonnull OWLDifferentIndividualsAxiom axiom);

    void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom);

    void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom);

    void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom);

    void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom);

    void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom);

    void visit(@Nonnull OWLDisjointUnionAxiom axiom);

    void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLDataPropertyRangeAxiom axiom);

    void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom);

    void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom);

    void visit(@Nonnull OWLClassAssertionAxiom axiom);

    void visit(@Nonnull OWLEquivalentClassesAxiom axiom);

    void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom);

    void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom);

    void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom);

    void visit(@Nonnull OWLSameIndividualAxiom axiom);

    void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom);

    void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom);

    void visit(@Nonnull OWLHasKeyAxiom axiom);

    void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom);

    void visit(@Nonnull SWRLRule rule);
}
