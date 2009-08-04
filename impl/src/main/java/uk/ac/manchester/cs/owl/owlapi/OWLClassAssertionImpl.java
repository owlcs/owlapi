package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collection;
import java.util.Set;
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
public class OWLClassAssertionImpl extends OWLIndividualAxiomImpl implements OWLClassAssertionAxiom {

    private OWLIndividual individual;

    private OWLClassExpression classExpression;


    public OWLClassAssertionImpl(OWLDataFactory dataFactory, OWLIndividual individual, OWLClassExpression classExpression, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.individual = individual;
        this.classExpression = classExpression;
    }

    public OWLClassAssertionAxiom getAxiomWithoutAnnotations() {
        if(!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLClassAssertionAxiom(getClassExpression(), getIndividual());
    }

    public OWLClassAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLClassAssertionAxiom(getClassExpression(), getIndividual(), mergeAnnos(annotations));
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }


    public OWLIndividual getIndividual() {
        return individual;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLClassAssertionAxiom)) {
                return false;
            }
            OWLClassAssertionAxiom other = (OWLClassAssertionAxiom) obj;
            return other.getIndividual().equals(individual) &&
                    other.getClassExpression().equals(classExpression);
        }
        return false;
    }

    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        return getOWLDataFactory().getOWLSubClassOfAxiom(
                getOWLDataFactory().getOWLObjectOneOf(getIndividual()),
                getClassExpression()
        );
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
        return AxiomType.CLASS_ASSERTION;
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLClassAssertionAxiom otherAx = (OWLClassAssertionAxiom) object;
        int diff = getIndividual().compareTo(otherAx.getIndividual());
        if (diff != 0) {
            return diff;
        } else {
            return getClassExpression().compareTo(otherAx.getClassExpression());
        }
    }

}
