package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;/*
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
 * Date: 17-Jan-2009
 */
public class OWLHasKeyAxiomImpl extends OWLAxiomImpl implements OWLHasKeyAxiom {

    private OWLClassExpression expression;

    private Set<OWLPropertyExpression> propertyExpressions;

    public OWLHasKeyAxiomImpl(OWLDataFactory dataFactory, OWLClassExpression expression, Set<? extends OWLPropertyExpression> propertyExpressions, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.expression = expression;
        this.propertyExpressions = Collections.unmodifiableSortedSet(new TreeSet<OWLPropertyExpression>(propertyExpressions));
    }

    public OWLHasKeyAxiom getAxiomWithoutAnnotations() {
        if(!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLHasKeyAxiom(getClassExpression(), getPropertyExpressions());
    }

    public OWLHasKeyAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLHasKeyAxiom(getClassExpression(), getPropertyExpressions(), mergeAnnos(annotations));
    }

    public AxiomType getAxiomType() {
        return AxiomType.HAS_KEY;
    }

    public boolean isLogicalAxiom() {
        return true;
    }

    public OWLClassExpression getClassExpression() {
        return expression;
    }

    public Set<OWLPropertyExpression> getPropertyExpressions() {
        return propertyExpressions;
    }

    public Set<OWLDataPropertyExpression> getDataPropertyExpressions() {
        Set<OWLDataPropertyExpression> props = new TreeSet<OWLDataPropertyExpression>();
        for(OWLPropertyExpression prop : propertyExpressions) {
            if(prop.isDataPropertyExpression()) {
                props.add((OWLDataPropertyExpression) prop);
            }
        }
        return props;
    }

    public Set<OWLObjectPropertyExpression> getObjectPropertyExpressions() {
        Set<OWLObjectPropertyExpression> props = new TreeSet<OWLObjectPropertyExpression>();
        for(OWLPropertyExpression prop : propertyExpressions) {
            if(prop.isObjectPropertyExpression()) {
                props.add((OWLObjectPropertyExpression) prop);
            }
        }
        return props;
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLHasKeyAxiom other = (OWLHasKeyAxiom) object;
        int diff = expression.compareTo(other.getClassExpression());
        if (diff != 0) {
            return diff;
        }
        return compareSets(propertyExpressions, other.getPropertyExpressions());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OWLHasKeyAxiom)) {
            return false;
        }
        OWLHasKeyAxiom other = (OWLHasKeyAxiom) obj;
        return expression.equals(other.getClassExpression()) && propertyExpressions.equals(other.getPropertyExpressions()) && other.getAnnotations().equals(getAnnotations());
    }
}
