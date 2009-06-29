package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collection;
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
 * Date: 25-Mar-2009
 */
public class OWLSubAnnotationPropertyOfAxiomImpl extends OWLAxiomImpl implements OWLSubAnnotationPropertyOfAxiom {

    private OWLAnnotationProperty subProperty;

    private OWLAnnotationProperty superProperty;


    public OWLSubAnnotationPropertyOfAxiomImpl(OWLDataFactory dataFactory,
                                               OWLAnnotationProperty subProperty,
                                               OWLAnnotationProperty superProperty,
                                               Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subProperty = subProperty;
        this.superProperty = superProperty;
    }

    public OWLSubAnnotationPropertyOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubAnnotationPropertyOfAxiom(getSubProperty(), getSuperProperty(), mergeAnnos(annotations));
    }

    public OWLSubAnnotationPropertyOfAxiom getAxiomWithoutAnnotations() {
        if(!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubAnnotationPropertyOfAxiom(getSubProperty(), getSuperProperty());
    }

    public OWLAnnotationProperty getSubProperty() {
        return subProperty;
    }


    public OWLAnnotationProperty getSuperProperty() {
        return superProperty;
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isLogicalAxiom() {
        return false;
    }


    public AxiomType getAxiomType() {
        return AxiomType.SUB_ANNOTATION_PROPERTY_OF;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    protected int compareObjectOfSameType(OWLObject object) {
        OWLSubAnnotationPropertyOfAxiom other = (OWLSubAnnotationPropertyOfAxiom) object;
        int diff = subProperty.compareTo(other.getSubProperty());
        if(diff != 0) {
            return diff;
        }
        return superProperty.compareTo(other.getSuperProperty());
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OWLSubAnnotationPropertyOfAxiom)) {
            return false;
        }
        OWLSubAnnotationPropertyOfAxiom other = (OWLSubAnnotationPropertyOfAxiom) obj;
        return subProperty.equals(other.getSubProperty()) && superProperty.equals(other.getSuperProperty());
    }
}
