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
package org.semanticweb.owlapi.reasoner.impl;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class NodeFactory {

    /** @return a class node */
    public static DefaultNode<OWLClass> getOWLClassNode() {
        return new OWLClassNode();
    }

    /**
     * @param cls
     *        a class to be included in the node
     * @return a class node with one element
     */
    public static DefaultNode<OWLClass> getOWLClassNode(@Nonnull OWLClass cls) {
        return new OWLClassNode(checkNotNull(cls, "cls cannot be null"));
    }

    /**
     * @param clses
     *        set of classes to be included
     * @return a class node with some elements
     */
    public static DefaultNode<OWLClass> getOWLClassNode(
            @Nonnull Set<OWLClass> clses) {
        return new OWLClassNode(checkNotNull(clses, "clses cannot be null"));
    }

    /** @return the top class node */
    public static DefaultNode<OWLClass> getOWLClassTopNode() {
        return OWLClassNode.getTopNode();
    }

    /** @return the bottom class node */
    public static DefaultNode<OWLClass> getOWLClassBottomNode() {
        return OWLClassNode.getBottomNode();
    }

    /** @return an object property node */
    public static DefaultNode<OWLObjectPropertyExpression>
            getOWLObjectPropertyNode() {
        return new OWLObjectPropertyNode();
    }

    /**
     * @param prop
     *        a property to be added
     * @return an object property node with one element
     */
    public static DefaultNode<OWLObjectPropertyExpression>
            getOWLObjectPropertyNode(OWLObjectPropertyExpression prop) {
        return new OWLObjectPropertyNode(prop);
    }

    /**
     * @param properties
     *        some properties to be added
     * @return an object property node with some elements
     */
    public static
            DefaultNode<OWLObjectPropertyExpression>
            getOWLObjectPropertyNode(Set<OWLObjectPropertyExpression> properties) {
        return new OWLObjectPropertyNode(properties);
    }

    /** @return the top object property node */
    public static DefaultNode<OWLObjectPropertyExpression>
            getOWLObjectPropertyTopNode() {
        return OWLObjectPropertyNode.getTopNode();
    }

    /** @return the bottom object property node */
    public static DefaultNode<OWLObjectPropertyExpression>
            getOWLObjectPropertyBottomNode() {
        return OWLObjectPropertyNode.getBottomNode();
    }

    /** @return a data property node */
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode() {
        return new OWLDataPropertyNode();
    }

    /**
     * @param prop
     *        a property to be added
     * @return a data property node with one element
     */
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode(
            OWLDataProperty prop) {
        return new OWLDataPropertyNode(prop);
    }

    /**
     * @param properties
     *        some properties to be added
     * @return a data property node with some elements
     */
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode(
            Set<OWLDataProperty> properties) {
        return new OWLDataPropertyNode(properties);
    }

    /** @return the top data property node */
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyTopNode() {
        return OWLDataPropertyNode.getTopNode();
    }

    /** @return the bottom data property node */
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyBottomNode() {
        return OWLDataPropertyNode.getBottomNode();
    }

    /** @return an individual node */
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode() {
        return new OWLNamedIndividualNode();
    }

    /**
     * @param ind
     *        an individual to be added
     * @return an individual node with one element
     */
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode(
            OWLNamedIndividual ind) {
        return new OWLNamedIndividualNode(ind);
    }

    /**
     * @param inds
     *        some individuals to be added
     * @return an individual node containing some individuals
     */
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode(
            Set<OWLNamedIndividual> inds) {
        return new OWLNamedIndividualNode(inds);
    }
}
