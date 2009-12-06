package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;

import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
    
    
    
    public static DefaultNode<OWLObjectProperty> getOWLObjectPropertyNode() {
        return new OWLObjectPropertyNode();
    }
    
    public static DefaultNode<OWLObjectProperty> getOWLObjectPropertyNode(OWLObjectProperty prop) {
        return new OWLObjectPropertyNode(prop);
    }
    
    public static DefaultNode<OWLObjectProperty> getOWLObjectPropertyNode(Set<OWLObjectProperty> properties) {
        return new OWLObjectPropertyNode(properties);
    }
    
    public static DefaultNode<OWLObjectProperty> getOWLObjectPropertyTopNode() {
        return OWLObjectPropertyNode.getTopNode();
    }
    
    public static DefaultNode<OWLObjectProperty> getOWLObjectPropertyBottomNode() {
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
