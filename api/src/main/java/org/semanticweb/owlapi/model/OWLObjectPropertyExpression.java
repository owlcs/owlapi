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

import java.util.Set;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group Date: 24-Oct-2006
 */
public interface OWLObjectPropertyExpression extends OWLPropertyExpression<OWLClassExpression, OWLObjectPropertyExpression>, SWRLPredicate {

    /**
     * Determines if the specified ontology specifies that this property is inverse functional.
     *
     * @param ontology The ontology to be tested for an inverse functional property axiom.
     * @return <code>true</code> if the property is inverse functional, or <code>false</code> if the property is not
     *         inverse functional.
     */
    boolean isInverseFunctional(OWLOntology ontology);


    boolean isInverseFunctional(Set<OWLOntology> ontologies);


    boolean isSymmetric(OWLOntology ontology);


    boolean isSymmetric(Set<OWLOntology> ontologies);


    boolean isAsymmetric(OWLOntology ontology);


    boolean isAsymmetric(Set<OWLOntology> ontologies);


    boolean isReflexive(OWLOntology ontology);


    boolean isReflexive(Set<OWLOntology> ontologies);


    boolean isIrreflexive(OWLOntology ontology);


    boolean isIrreflexive(Set<OWLOntology> ontologies);


    boolean isTransitive(OWLOntology ontology);


    boolean isTransitive(Set<OWLOntology> ontologies);


    Set<OWLObjectPropertyExpression> getInverses(OWLOntology ontology);


    Set<OWLObjectPropertyExpression> getInverses(Set<OWLOntology> ontologies);


    /**
     * If the property is a named object property then this method will obtain the property as such.  The general
     * pattern of use is that the <code>isAnonymous</code> method should first be used to determine if the property is
     * named (i.e. not an object property expression such as inv(p)).  If the property is named then this method may be
     * used to obtain the property as a named property without casting.
     *
     * @return The property as an <code>OWLObjectProperty</code> if possible.
     * @throws OWLRuntimeException if the property is not a named property.
     */
    OWLObjectProperty asOWLObjectProperty();


    /**
     * Obtains the property that corresponds to the inverse of this property.
     *
     * @return The inverse of this property.  Note that this property will not necessarily be in the simplest form.
     */
    OWLObjectPropertyExpression getInverseProperty();


    /**
     * Returns this property in its simplified form.
     *
     * @return Let p be a property name and PE an object property expression. The simplification, 'simp', is defined as
     *         follows: simp(p) = p simp(inv(p)) = inv(p) simp(inv(inv(PE)) = simp(PE)
     */
    OWLObjectPropertyExpression getSimplified();


    /**
     * Get the named object property used in this property expression.
     *
     * @return P if simp(PE) = inv(P) or P if simp(PE) = P.
     */
    OWLObjectProperty getNamedProperty();
}
