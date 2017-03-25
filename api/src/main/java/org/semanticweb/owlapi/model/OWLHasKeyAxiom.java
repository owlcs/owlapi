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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a <a href="http://www.w3.org/TR/owl2-syntax/#Keys">HasKey</a> axiom in the OWL 2
 * Specification.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public interface OWLHasKeyAxiom extends OWLLogicalAxiom {

    @Override
    @SuppressWarnings("unchecked")
    OWLHasKeyAxiom getAxiomWithoutAnnotations();

    @Override
    default Stream<?> components() {
        return Stream.of(getClassExpression(), propertyExpressions(), annotations());
    }

    @Override
    default Stream<?> componentsWithoutAnnotations() {
        return Stream.of(getClassExpression(), propertyExpressions());
    }

    @Override
    default Stream<?> componentsAnnotationsFirst() {
        return Stream.of(annotations(), getClassExpression(), propertyExpressions());
    }

    @Override
    default OWLObjectType type() {
        return OWLObjectType.HASKEY;
    }

    /**
     * Gets the class expression, instances of which, this axiom acts as the key for.
     *
     * @return The class expression
     */
    OWLClassExpression getClassExpression();

    /**
     * Gets the set of property expressions that form the key.
     *
     * @return The set of property expression that form the key
     * @deprecated use the stream method
     */
    @Deprecated
    default Set<OWLPropertyExpression> getPropertyExpressions() {
        return asSet(propertyExpressions());
    }

    /**
     * Gets the set of property expressions that form the key.
     *
     * @return The set of property expression that form the key
     */
    Stream<OWLPropertyExpression> propertyExpressions();

    /**
     * Gets the set of object property expressions that make up the key. This is simply a
     * convenience method that filteres out the object property expressions in the key. All of the
     * properties returned by this method are included in the return value of the
     * {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     *
     * @return The set of object property expressions in the key described by this axiom
     * @deprecated use the stream method
     */
    @Deprecated
    default Set<OWLObjectPropertyExpression> getObjectPropertyExpressions() {
        return asSet(objectPropertiesInSignature(), OWLObjectPropertyExpression.class);
    }

    /**
     * Gets the set of object property expressions that make up the key. This is simply a
     * convenience method that filteres out the object property expressions in the key. All of the
     * properties returned by this method are included in the return value of the
     * {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     *
     * @return The set of object property expressions in the key described by this axiom
     */
    default Stream<OWLObjectPropertyExpression> objectPropertyExpressions() {
        return propertyExpressions().filter(OWLPropertyExpression::isObjectPropertyExpression)
            .map(OWLPropertyExpression::asObjectPropertyExpression);
    }

    /**
     * Gets the set of data property expressions that make up the key. This is simply a convenience
     * method that filteres out the data property expressions in the key. All of the properties
     * returned by this method are included in the return value of the
     * {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     *
     * @return The set of object property expressions in the key described by this axiom
     * @deprecated use the stream method
     */
    @Deprecated
    default Set<OWLDataPropertyExpression> getDataPropertyExpressions() {
        return asSet(dataPropertyExpressions());
    }

    /**
     * Gets the set of data property expressions that make up the key. This is simply a convenience
     * method that filteres out the data property expressions in the key. All of the properties
     * returned by this method are included in the return value of the
     * {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     *
     * @return The set of object property expressions in the key described by this axiom
     */
    default Stream<OWLDataPropertyExpression> dataPropertyExpressions() {
        return propertyExpressions().filter(OWLPropertyExpression::isDataPropertyExpression)
            .map(OWLPropertyExpression::asDataPropertyExpression);
    }

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
        return AxiomType.HAS_KEY;
    }
}
