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

package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class NodeFactory {


    
    public static DefaultNode<OWLClass> getOWLClassNode() {
        return new OWLClassNode();
    }
    
    public static DefaultNode<OWLClass> getOWLClassNode(OWLClass cls) {
        return new OWLClassNode(cls);
    }
    
    public static DefaultNode<OWLClass> getOWLClassNode(Set<OWLClass> clses) {
        return new OWLClassNode(clses);
    }
    
    public static DefaultNode<OWLClass> getOWLClassTopNode() {
        return OWLClassNode.getTopNode();
    }
    
    public static DefaultNode<OWLClass> getOWLClassBottomNode() {
        return OWLClassNode.getBottomNode();
    }
    
    
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyNode() {
        return new OWLObjectPropertyNode();
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyNode(OWLObjectPropertyExpression prop) {
        return new OWLObjectPropertyNode(prop);
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyNode(Set<OWLObjectPropertyExpression> properties) {
        return new OWLObjectPropertyNode(properties);
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyTopNode() {
        return OWLObjectPropertyNode.getTopNode();
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyBottomNode() {
        return OWLObjectPropertyNode.getBottomNode();
    }
    
    
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode() {
        return new OWLDataPropertyNode();
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode(OWLDataProperty prop) {
        return new OWLDataPropertyNode(prop);
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode(Set<OWLDataProperty> properties) {
        return new OWLDataPropertyNode(properties);
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyTopNode() {
        return OWLDataPropertyNode.getTopNode();
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyBottomNode() {
        return OWLDataPropertyNode.getBottomNode();
    }
    
    
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode() {
        return new OWLNamedIndividualNode();
    }
    
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode(OWLNamedIndividual ind) {
        return new OWLNamedIndividualNode(ind);
    }
    
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode(Set<OWLNamedIndividual> inds) {
        return new OWLNamedIndividualNode(inds);
    }

}
