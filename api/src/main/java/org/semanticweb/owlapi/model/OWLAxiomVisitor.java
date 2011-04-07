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
 * Date: 26-Oct-2006<br><br>
 * </p>
 * An interface for objects that can accept visits from axioms.  (See the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 */
public interface OWLAxiomVisitor extends OWLAnnotationAxiomVisitor {

    void visit(OWLDeclarationAxiom axiom);


    void visit(OWLSubClassOfAxiom axiom);


    void visit(OWLNegativeObjectPropertyAssertionAxiom axiom);


    void visit(OWLAsymmetricObjectPropertyAxiom axiom);


    void visit(OWLReflexiveObjectPropertyAxiom axiom);


    void visit(OWLDisjointClassesAxiom axiom);


    void visit(OWLDataPropertyDomainAxiom axiom);


    void visit(OWLObjectPropertyDomainAxiom axiom);


    void visit(OWLEquivalentObjectPropertiesAxiom axiom);


    void visit(OWLNegativeDataPropertyAssertionAxiom axiom);


    void visit(OWLDifferentIndividualsAxiom axiom);


    void visit(OWLDisjointDataPropertiesAxiom axiom);


    void visit(OWLDisjointObjectPropertiesAxiom axiom);


    void visit(OWLObjectPropertyRangeAxiom axiom);


    void visit(OWLObjectPropertyAssertionAxiom axiom);


    void visit(OWLFunctionalObjectPropertyAxiom axiom);


    void visit(OWLSubObjectPropertyOfAxiom axiom);


    void visit(OWLDisjointUnionAxiom axiom);


    void visit(OWLSymmetricObjectPropertyAxiom axiom);


    void visit(OWLDataPropertyRangeAxiom axiom);


    void visit(OWLFunctionalDataPropertyAxiom axiom);


    void visit(OWLEquivalentDataPropertiesAxiom axiom);


    void visit(OWLClassAssertionAxiom axiom);


    void visit(OWLEquivalentClassesAxiom axiom);


    void visit(OWLDataPropertyAssertionAxiom axiom);


    void visit(OWLTransitiveObjectPropertyAxiom axiom);


    void visit(OWLIrreflexiveObjectPropertyAxiom axiom);


    void visit(OWLSubDataPropertyOfAxiom axiom);


    void visit(OWLInverseFunctionalObjectPropertyAxiom axiom);


    void visit(OWLSameIndividualAxiom axiom);


    void visit(OWLSubPropertyChainOfAxiom axiom);


    void visit(OWLInverseObjectPropertiesAxiom axiom);


    void visit(OWLHasKeyAxiom axiom);


    void visit(OWLDatatypeDefinitionAxiom axiom);

    
    void visit(SWRLRule rule);




}
