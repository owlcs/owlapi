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
package org.semanticweb.owlapi.model;

/**
 * Represents an <a href= "http://www.w3.org/TR/owl2-syntax/#Positive_Object_Property_Assertions" >
 * ObjectPropertyAssertion</a> axiom in the OWL 2 Specification.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLObjectPropertyAssertionAxiom
    extends OWLPropertyAssertionAxiom<OWLObjectPropertyExpression, OWLIndividual>,
    OWLSubClassOfAxiomShortCut {

    @Override
    @SuppressWarnings("unchecked")
    OWLObjectPropertyAssertionAxiom getAxiomWithoutAnnotations();

    @Override
    default OWLObjectType type() {
        return OWLObjectType.OBJECT_ASSERTION;
    }

    /**
     * Gets a simplified version of this object property axiom. This is defined recursively as
     * follows:
     * <ul>
     * <li>ObjectPropertyAssertion(P S O) = ObjectPropertyAssertion(P S O)
     * <li>ObjectPropertyAssertion(ObjectInverseOf(P) S O) = ObjectPropertyAssertion(P O S)
     * </ul>
     *
     * @return the simplified version
     */
    OWLObjectPropertyAssertionAxiom getSimplified();

    /**
     * Determines if this axiom is in a simplified form, i.e. a form where the property is not a
     * property inverse. ObjectPropertyAssertion(P S O) is in a simplified form, where as
     * ObjectPropertyAssertion(ObjectInverseOf(P) S O) is not because it contains an inverse object
     * property.
     *
     * @return {@code true} if this axiom is in a simplified form, otherwise {@code false}
     */
    boolean isInSimplifiedForm();

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default AxiomType<?> getAxiomType() {
        return AxiomType.OBJECT_PROPERTY_ASSERTION;
    }
}
