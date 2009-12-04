package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collections;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

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
 * Date: 01-Aug-2009
 */
public class NodeImpl<E extends OWLLogicalEntity> implements Node<E> {

    private static Node<OWLClass> owlClassTopNode = createOWLClassNode(OWLDataFactoryImpl.getInstance().getOWLThing());

    private static Node<OWLClass> owlClassBottomNode = createOWLClassNode(OWLDataFactoryImpl.getInstance().getOWLThing());

    private static Node<OWLObjectProperty> owlObjectPropertyTopNode = createOWLObjectPropertyNode(OWLDataFactoryImpl.getInstance().getOWLTopObjectProperty());

    private static Node<OWLObjectProperty> owlObjectPropertyBottomNode = createOWLObjectPropertyNode(OWLDataFactoryImpl.getInstance().getOWLBottomObjectProperty());

    private static Node<OWLDataProperty> owlDataPropertyTopNode = createOWLDataPropertyNode(OWLDataFactoryImpl.getInstance().getOWLTopDataProperty());

    private static Node<OWLDataProperty> owlDataPropertyBottomNode = createOWLDataPropertyNode(OWLDataFactoryImpl.getInstance().getOWLBottomDataProperty());


    private E topEntity;

    private E bottomEntity;

    Set<E> entities;

    private NodeImpl(Set<E> entities, E topEntity, E bottomEntity) {
        this.entities = Collections.unmodifiableSet(new HashSet<E>(entities));
        this.topEntity = topEntity;
        this.bottomEntity = bottomEntity;
    }

    private NodeImpl(E entity, E topEntity, E bottomEntity) {
        this(Collections.singleton(entity), topEntity, bottomEntity);
    }

    public static <E extends OWLLogicalEntity> Node<E> createOWLNode(E entity) {
        if (entity.isOWLClass()) {
            return (Node<E>) createOWLClassNode(entity.asOWLClass());
        }
        else if (entity.isOWLObjectProperty()) {
            return (Node<E>) createOWLObjectPropertyNode(entity.asOWLObjectProperty());
        }
        else if (entity.isOWLDataProperty()) {
            return (Node<E>) createOWLDataPropertyNode(entity.asOWLDataProperty());
        }
        else if (entity.isOWLNamedIndividual()) {
            return (Node<E>) createOWLNamedIndividualNode(entity.asOWLNamedIndividual());
        }
        else if (entity.isOWLDatatype()) {
            return (Node<E>) createOWLDatatypeNode(entity.asOWLDatatype());
        }
        else {
            throw new IllegalStateException();
        }
    }

    public static Node<OWLClass> createOWLClassTopNode() {
        return owlClassTopNode;
    }


    public static Node<OWLClass> createOWLClassBottomNode() {
        return owlClassBottomNode;
    }


    public static Node<OWLObjectProperty> createOWLObjectPropertyTopNode() {
        return owlObjectPropertyTopNode;
    }


    public static Node<OWLObjectProperty> createOWLObjectPropertyBottomNode() {
        return owlObjectPropertyBottomNode;
    }


    public static Node<OWLDataProperty> createOWLDataPropertyTopNode() {
        return owlDataPropertyTopNode;
    }


    public static Node<OWLDataProperty> createOWLDataPropertyBottomNode() {
        return owlDataPropertyBottomNode;
    }


    public static Node<OWLClass> createOWLClassNode(OWLClass entity) {
        if (entity.isOWLThing()) {
            return new NodeImpl<OWLClass>(entity, OWLDataFactoryImpl.getInstance().getOWLThing(), null);
        }
        else if (entity.isOWLNothing()) {
            return new NodeImpl<OWLClass>(entity, null, OWLDataFactoryImpl.getInstance().getOWLNothing());
        }
        else {
            return new NodeImpl<OWLClass>(entity, null, null);
        }
    }

    public static Node<OWLClass> createOWLClassNode(Set<OWLClass> entities) {
        if (entities.contains(OWLDataFactoryImpl.getInstance().getOWLThing())) {
            return new NodeImpl<OWLClass>(entities, OWLDataFactoryImpl.getInstance().getOWLThing(), null);
        }
        else if (entities.contains(OWLDataFactoryImpl.getInstance().getOWLNothing())) {
            return new NodeImpl<OWLClass>(entities, null, OWLDataFactoryImpl.getInstance().getOWLNothing());
        }
        else {
            return new NodeImpl<OWLClass>(entities, null, null);
        }
    }

    public static Node<OWLObjectProperty> createOWLObjectPropertyNode(OWLObjectProperty entity) {
        if (entity.isOWLTopObjectProperty()) {
            return new NodeImpl<OWLObjectProperty>(entity, OWLDataFactoryImpl.getInstance().getOWLTopObjectProperty(), null);
        }
        else if (entity.isOWLBottomObjectProperty()) {
            return new NodeImpl<OWLObjectProperty>(entity, null, OWLDataFactoryImpl.getInstance().getOWLBottomObjectProperty());
        }
        else {
            return new NodeImpl<OWLObjectProperty>(entity, null, null);
        }
    }

    public static Node<OWLObjectProperty> createOWLObjectPropertyNode(Set<OWLObjectProperty> entities) {
        if (entities.contains(OWLDataFactoryImpl.getInstance().getOWLTopObjectProperty())) {
            return new NodeImpl<OWLObjectProperty>(entities, OWLDataFactoryImpl.getInstance().getOWLTopObjectProperty(), null);
        }
        else if (entities.contains(OWLDataFactoryImpl.getInstance().getOWLBottomObjectProperty())) {
            return new NodeImpl<OWLObjectProperty>(entities, null, OWLDataFactoryImpl.getInstance().getOWLBottomObjectProperty());
        }
        else {
            return new NodeImpl<OWLObjectProperty>(entities, null, null);
        }
    }


    public static Node<OWLDataProperty> createOWLDataPropertyNode(OWLDataProperty entity) {
        if (entity.isOWLTopObjectProperty()) {
            return new NodeImpl<OWLDataProperty>(entity, OWLDataFactoryImpl.getInstance().getOWLTopDataProperty(), null);
        }
        else if (entity.isOWLBottomObjectProperty()) {
            return new NodeImpl<OWLDataProperty>(entity, null, OWLDataFactoryImpl.getInstance().getOWLBottomDataProperty());
        }
        else {
            return new NodeImpl<OWLDataProperty>(entity, null, null);
        }
    }
    
    public static Node<OWLDataProperty> createOWLDataPropertyNode(Set<OWLDataProperty> entities) {
        if (entities.contains(OWLDataFactoryImpl.getInstance().getOWLTopDataProperty())) {
            return new NodeImpl<OWLDataProperty>(entities, OWLDataFactoryImpl.getInstance().getOWLTopDataProperty(), null);
        }
        else if (entities.contains(OWLDataFactoryImpl.getInstance().getOWLBottomDataProperty())) {
            return new NodeImpl<OWLDataProperty>(entities, null, OWLDataFactoryImpl.getInstance().getOWLBottomDataProperty());
        }
        else {
            return new NodeImpl<OWLDataProperty>(entities, null, null);
        }
    }

    public static Node<OWLDatatype> createOWLDatatypeNode(OWLDatatype entity) {
        if (entity.isTopDatatype()) {
            return new NodeImpl<OWLDatatype>(entity, OWLDataFactoryImpl.getInstance().getTopDatatype(), null);
        }
        else {
            return new NodeImpl<OWLDatatype>(entity, null, null);
        }
    }

    public static Node<OWLNamedIndividual> createOWLNamedIndividualNode(OWLNamedIndividual entity) {
        return new NodeImpl<OWLNamedIndividual>(entity, null, null);
    }


    public static Node<OWLNamedIndividual> createOWLNamedIndividualNode(Set<OWLNamedIndividual> entities) {
        return new NodeImpl<OWLNamedIndividual>(entities, null, null);
    }


    public Set<E> getEntities() {
        return entities;
    }

    public int getSize() {
        return entities.size();
    }

    public boolean isTopNode() {
        return topEntity != null;
    }

    public boolean isBottomNode() {
        return bottomEntity != null;
    }

    public Set<E> getEntitiesMinus(E e) {
        Set<E> result = new HashSet<E>(entities);
        result.remove(e);
        return result;
    }

    public Set<E> getEntitiesMinusTop() {
        return getEntitiesMinus(topEntity);
    }

    public Set<E> getEntitiesMinusBottom() {
        return getEntitiesMinus(bottomEntity);
    }

    public boolean isSingleton() {
        return entities.size() == 1;
    }

    public E getRepresentativeElement() {
        return entities.iterator().next();
    }

    public Iterator<E> iterator() {
        return entities.iterator();
    }

    public boolean contains(E entity) {
        return entities.contains(entity);
    }

}
