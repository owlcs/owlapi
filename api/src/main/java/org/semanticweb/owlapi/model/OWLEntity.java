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
 * Represents <a href=
 * "http://www.w3.org/TR/owl2-syntax/#Entities.2C_Literals.2C_and_Anonymous_Individuals"
 * >Entities</a> in the OWL 2 Specification.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLEntity extends OWLObject, OWLNamedObject, OWLPrimitive, AsOWLClass, AsOWLDataProperty,
        AsOWLDatatype, AsOWLAnnotationProperty, AsOWLNamedIndividual, AsOWLObjectProperty {

    /**
     * Gets the entity type for this entity.
     * 
     * @return The entity type
     */
    EntityType<?> getEntityType();

    /**
     * Tests to see if this entity is of the specified type.
     * 
     * @param entityType
     *        The entity type
     * @return {@code true} if this entity is of the specified type, otherwise
     *         {@code false}.
     */
    boolean isType(EntityType<?> entityType);

    /**
     * Determines if this entity is a built in entity. The entity is a built in
     * entity if it is:
     * <ul>
     * <li>a class and the URI corresponds to owl:Thing or owl:Nothing</li>
     * <li>an object property and the URI corresponds to owl:topObjectProperty
     * or owl:bottomObjectProperty</li>
     * <li>a data property and the URI corresponds to owl:topDataProperty or
     * owl:bottomDataProperty</li>
     * <li>a datatype and the IRI is rdfs:Literal or is in the OWL 2 datatype
     * map or is rdf:PlainLiteral</li>
     * <li>an annotation property and the URI is in the set of built in
     * annotation property URIs, i.e. one of:
     * <ul>
     * <li>rdfs:label</li>
     * <li>rdfs:comment</li>
     * <li>rdfs:seeAlso</li>
     * <li>rdfs:isDefinedBy</li>
     * <li>owl:deprecated</li>
     * <li>owl:priorVersion</li>
     * <li>owl:backwardCompatibleWith</li>
     * <li>owl:incompatibleWith</li>
     * </ul>
     * </li>
     * </ul>
     * 
     * @return {@code true} if this entity is a built in entity, or
     *         {@code false} if this entity is not a builtin entity.
     */
    boolean isBuiltIn();

    /**
     * Returns a string representation that can be used as the ID of this
     * entity. This is the toString representation of the IRI
     * 
     * @return A string representing the toString of the IRI of this entity.
     */
    String toStringID();

    /**
     * @param visitor
     *        visitor
     */
    void accept(OWLEntityVisitor visitor);

    /**
     * @param visitor
     *        visitor
     * @param <O>
     *        visitor return type
     * @return visitor return value
     */
    <O> O accept(OWLEntityVisitorEx<O> visitor);
}
