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

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Nov-2006<br><br>
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Object_Subproperties">SubObjectPropertyOf</a> axiom in the OWL 2 Specification where the subproperty is
 * a chain of properties.  Note that this axiom type is not explicit in the OWL 2 specification, but it is included
 * in the OWL API as a convenience to the programmer.
 */
public interface OWLSubPropertyChainOfAxiom extends OWLObjectPropertyAxiom {

    /**
     * Gets the chain of properties that represents the subproperty in this axiom.
     * @return A list of object property expressions that represents the chain of properties that represent the subproperty
     * in this axiom.
     */
    List<OWLObjectPropertyExpression> getPropertyChain();

    /**
     * Gets the super property of this axiom
     * @return The property expression that represents the superproperty in this expression.
     */
    OWLObjectPropertyExpression getSuperProperty();


    /**
     * Determines if this axiom is of the form:  P o P -> P, which
     * is an encoding of Transitive(P)
     *
     * @return <code>true</code> if this encodes that the super property
     *         is transitive, otherwise <code>false</code>.
     */
    boolean isEncodingOfTransitiveProperty();

    OWLSubPropertyChainOfAxiom getAxiomWithoutAnnotations();
}
