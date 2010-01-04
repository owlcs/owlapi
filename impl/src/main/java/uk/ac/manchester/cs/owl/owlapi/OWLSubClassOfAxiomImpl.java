package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.*;
/*
 * Copyright (C) 2006, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLSubClassOfAxiomImpl extends OWLClassAxiomImpl implements OWLSubClassOfAxiom {

    private OWLClassExpression subClass;

    private OWLClassExpression superClass;


    public OWLSubClassOfAxiomImpl(OWLDataFactory dataFactory, OWLClassExpression subClass, OWLClassExpression superClass, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subClass = subClass;
        this.superClass = superClass;
    }

    public Set<OWLClassExpression> getClassExpressions() {
        Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>(3);
        classExpressions.add(subClass);
        classExpressions.add(superClass);
        return classExpressions;
    }

    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc) {
        Set<OWLClassExpression> classExpressions = getClassExpressions();
        for(OWLClassExpression ce : desc) {
            classExpressions.remove(ce);
        }
        return classExpressions;
    }

    public OWLSubClassOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass, mergeAnnos(annotations));
    }

    public OWLSubClassOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
    }

    public boolean contains(OWLClassExpression ce) {
        return subClass.equals(ce) || superClass.equals(ce);
    }

    public OWLClassExpression getSubClass() {
        return subClass;
    }


    public OWLClassExpression getSuperClass() {
        return superClass;
    }


    public boolean isGCI() {
        return subClass.isAnonymous();
    }


    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLSubClassOfAxiom)) {
            return false;
        }
        OWLSubClassOfAxiom other = (OWLSubClassOfAxiom) obj;
        return other.getSubClass().equals(subClass) && other.getSuperClass().equals(superClass);
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public AxiomType getAxiomType() {
        return AxiomType.SUBCLASS_OF;
    }


    protected int compareObjectOfSameType(OWLObject object) {
        OWLSubClassOfAxiom other = (OWLSubClassOfAxiom) object;
        int diff = subClass.compareTo(other.getSubClass());
        if (diff != 0) {
            return diff;
        }
        return superClass.compareTo(other.getSuperClass());
    }
}
