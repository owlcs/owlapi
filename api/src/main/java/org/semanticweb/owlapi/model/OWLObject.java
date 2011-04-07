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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 */
public interface OWLObject extends Comparable<OWLObject> {


    /**
     * Gets the signature of this object
     * @return A set of entities that correspond to the
     *         signature of this object. The set is an 
     *         unmodifiable collection.
     */
    Set<OWLEntity> getSignature();


    /**
     * A convenience method that obtains the classes
     * that are in the signature of this object
     * @return A set containing the classes that are in the signature
     *         of this object. The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection 
     *         and changes are not reflected by the signature.
     */
    Set<OWLClass> getClassesInSignature();

    /**
     * A convenience method that obtains the data properties
     * that are in the signature of this object
     * @return A set containing the data properties that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection 
     *         and changes are not reflected by the signature.
     */
    Set<OWLDataProperty> getDataPropertiesInSignature();

    /**
     * A convenience method that obtains the object properties
     * that are in the signature of this object
     * @return A set containing the object properties that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection 
     *         and changes are not reflected by the signature.
     */
    Set<OWLObjectProperty> getObjectPropertiesInSignature();


    /**
     * A convenience method that obtains the individuals
     * that are in the signature of this object
     * @return A set containing the individuals that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection 
     *         and changes are not reflected by the signature.
     */
    Set<OWLNamedIndividual> getIndividualsInSignature();

    /**
     * A convenience method that obtains the datatypes
     * that are in the signature of this object
     * @return A set containing the datatypes that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection 
     *         and changes are not reflected by the signature.
     */
    Set<OWLDatatype> getDatatypesInSignature();

    /**
     * Gets all of the nested (includes top level) class expressions that are used in this object
     * @return A set of {@link org.semanticweb.owlapi.model.OWLClassExpression}s that represent the nested class
     * expressions used in this object.
     */
    Set<OWLClassExpression> getNestedClassExpressions();

    public void accept(OWLObjectVisitor visitor);

    <O> O accept(OWLObjectVisitorEx<O> visitor);


    /**
     * Determines if this object is either, owl:Thing (the top class), owl:topObjectProperty (the top object property)
     * , owl:topDataProperty (the top data property) or rdfs:Literal (the top datatype).
     * @return <code>true</code> if this object corresponds to one of the above entities.
     */
    boolean isTopEntity();

    
    /**
     * Determines if this object is either, owl:Nothing (the bottom class), owl:bottomObjectProperty (the bottom object property)
     * , owl:bottomDataProperty (the bottom data property).
     * @return <code>true</code> if this object corresponds to one of the above entities.
     */
    boolean isBottomEntity();


    
}
