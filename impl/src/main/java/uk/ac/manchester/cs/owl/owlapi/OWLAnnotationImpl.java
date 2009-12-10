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
 * Date: 19-Dec-2006<br><br>
 */
public class OWLAnnotationImpl extends OWLObjectImpl implements OWLAnnotation {

    private OWLAnnotationProperty property;

    private OWLAnnotationValue value;

    private Set<OWLAnnotation> annotations;

    public OWLAnnotationImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory);
        this.property = property;
        this.value = value;
        this.annotations = Collections.unmodifiableSortedSet(new TreeSet<OWLAnnotation>(annotations));
    }

    public Set<OWLAnnotation> getAnnotations() {
        return annotations;
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }


    public OWLAnnotationValue getValue() {
        return value;
    }

    public boolean isComment() {
        return property.isComment();
    }


    public boolean isLabel() {
        return property.isLabel();
    }

    /**
     * Determines if this annotation is an annotation used to deprecate an IRI.  This is the case if the annotation
     * property has an IRI of <code>owl:deprecated</code> and the value of the annotation is <code>"true"^^xsd:boolean</code>
     *
     * @return <code>true</code> if this annotation is an annotation that can be used to deprecate an IRI, otherwise
     *         <code>false</code>.
     */
    public boolean isDeprecatedIRIAnnotation() {
        return property.isDeprecated() && value instanceof OWLTypedLiteral && ((OWLTypedLiteral) value).getDatatype().isBoolean() &&((OWLTypedLiteral) value).getLiteral().equalsIgnoreCase("true");
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (obj instanceof OWLAnnotation) {
                OWLAnnotation other = (OWLAnnotation) obj;
                return other.getProperty().equals(property) && other.getValue().equals(value) && other.getAnnotations().equals(annotations);
            }
        }
        return false;
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotation other = (OWLAnnotation) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        } else {
            return getValue().compareTo(other.getValue());
        }
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAnnotationObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
