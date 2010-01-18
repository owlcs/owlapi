package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 15-Jan-2009
 */
public class OWLAnonymousIndividualImpl extends OWLIndividualImpl implements OWLAnonymousIndividual {

    private NodeID id;

    public OWLAnonymousIndividualImpl(OWLDataFactory dataFactory, NodeID nodeID) {
        super(dataFactory);
        this.id = nodeID;
    }

    public NodeID getID() {
        return id;
    }

    /**
     * Returns a string representation that can be used as the ID of this individual.  This is the toString
     * representation of the node ID of this individual
     * @return A string representing the toString of the node ID of this entity.
     */
    public String toStringID() {
        return id.getID();
    }

    public boolean isNamed() {
        return false;
    }

    public boolean isAnonymous() {
        return true;
    }

    public OWLAnonymousIndividual asOWLAnonymousIndividual() {
        return this;
    }

    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not a named individual! This method should only be called on named individuals");
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnonymousIndividual other = (OWLAnonymousIndividual) object;
        return id.compareTo(other.getID());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public void accept(OWLIndividualVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLIndividualVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationValueVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationSubjectVisitor visitor) {
        visitor.visit(this);
    }

    public <E> E accept(OWLAnnotationSubjectVisitorEx<E> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OWLAnonymousIndividual)) {
            return false;
        }
        return getID().equals(((OWLAnonymousIndividual) obj).getID());
    }
}
