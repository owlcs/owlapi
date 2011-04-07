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
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 * </p>
 * An interface for objects that can accept visits from axioms.  (See the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 */
public interface OWLAxiomVisitorEx<O> extends OWLAnnotationAxiomVisitorEx<O> {


    O visit(OWLSubClassOfAxiom axiom);


    O visit(OWLNegativeObjectPropertyAssertionAxiom axiom);


    O visit(OWLAsymmetricObjectPropertyAxiom axiom);


    O visit(OWLReflexiveObjectPropertyAxiom axiom);


    O visit(OWLDisjointClassesAxiom axiom);


    O visit(OWLDataPropertyDomainAxiom axiom);


    O visit(OWLObjectPropertyDomainAxiom axiom);


    O visit(OWLEquivalentObjectPropertiesAxiom axiom);


    O visit(OWLNegativeDataPropertyAssertionAxiom axiom);


    O visit(OWLDifferentIndividualsAxiom axiom);


    O visit(OWLDisjointDataPropertiesAxiom axiom);


    O visit(OWLDisjointObjectPropertiesAxiom axiom);


    O visit(OWLObjectPropertyRangeAxiom axiom);


    O visit(OWLObjectPropertyAssertionAxiom axiom);


    O visit(OWLFunctionalObjectPropertyAxiom axiom);


    O visit(OWLSubObjectPropertyOfAxiom axiom);


    O visit(OWLDisjointUnionAxiom axiom);


    O visit(OWLDeclarationAxiom axiom);


    O visit(OWLAnnotationAssertionAxiom axiom);


    O visit(OWLSymmetricObjectPropertyAxiom axiom);


    O visit(OWLDataPropertyRangeAxiom axiom);


    O visit(OWLFunctionalDataPropertyAxiom axiom);


    O visit(OWLEquivalentDataPropertiesAxiom axiom);


    O visit(OWLClassAssertionAxiom axiom);


    O visit(OWLEquivalentClassesAxiom axiom);


    O visit(OWLDataPropertyAssertionAxiom axiom);


    O visit(OWLTransitiveObjectPropertyAxiom axiom);


    O visit(OWLIrreflexiveObjectPropertyAxiom axiom);


    O visit(OWLSubDataPropertyOfAxiom axiom);


    O visit(OWLInverseFunctionalObjectPropertyAxiom axiom);


    O visit(OWLSameIndividualAxiom axiom);


    O visit(OWLSubPropertyChainOfAxiom axiom);


    O visit(OWLInverseObjectPropertiesAxiom axiom);


    O visit(OWLHasKeyAxiom axiom);


    O visit(OWLDatatypeDefinitionAxiom axiom);


    O visit(SWRLRule rule);
}
