package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
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
public class OWLDisjointUnionAxiomImpl extends OWLClassAxiomImpl implements OWLDisjointUnionAxiom {

    private OWLClass owlClass;

    private Set<OWLClassExpression> classExpressions;

    public OWLDisjointUnionAxiomImpl(OWLDataFactory dataFactory, OWLClass owlClass,
                                Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.owlClass = owlClass;
        this.classExpressions = Collections.unmodifiableSortedSet(new TreeSet<OWLClassExpression>(classExpressions));
    }

    public Set<OWLClassExpression> getClassExpressions() {
        return classExpressions;
    }

    public OWLDisjointUnionAxiom getAxiomWithoutAnnotations() {
        if(!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDisjointUnionAxiom(getOWLClass(), getClassExpressions());
    }

    public OWLDisjointUnionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDisjointUnionAxiom(getOWLClass(), getClassExpressions(), mergeAnnos(annotations));
    }

    public OWLClass getOWLClass() {
        return owlClass;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDisjointUnionAxiom)) {
                return false;
            }
            return ((OWLDisjointUnionAxiom) obj).getOWLClass().equals(owlClass);
        }
        return false;
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
        return AxiomType.DISJOINT_UNION;
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom() {
        return getOWLDataFactory().getOWLEquivalentClassesAxiom(owlClass, getOWLDataFactory().getOWLObjectUnionOf(getClassExpressions()));
    }

    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom() {
        return getOWLDataFactory().getOWLDisjointClassesAxiom(getClassExpressions());
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLDisjointUnionAxiom other = (OWLDisjointUnionAxiom) object;
        int diff = owlClass.compareTo(other.getOWLClass());
        if (diff != 0) {
            return diff;
        }
        return compareSets(classExpressions, other.getClassExpressions());
    }
}
