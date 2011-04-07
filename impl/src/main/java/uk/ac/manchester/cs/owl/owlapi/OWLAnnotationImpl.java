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

package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;


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
        this.annotations = CollectionFactory.getCopyOnRequestSet(new TreeSet<OWLAnnotation>(annotations));
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

    public OWLAnnotation getAnnotatedAnnotation(Set<OWLAnnotation> annotationsToAdd) {
        if(annotationsToAdd.isEmpty()) {
            return this;
        }
        Set<OWLAnnotation> merged = new HashSet<OWLAnnotation>(this.annotations);
        merged.addAll(annotationsToAdd);
        return new OWLAnnotationImpl(getOWLDataFactory(), property, value, merged);
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
     * @return <code>true</code> if this annotation is an annotation that can be used to deprecate an IRI, otherwise
     *         <code>false</code>.
     */
    public boolean isDeprecatedIRIAnnotation() {
        return property.isDeprecated() && value instanceof OWLLiteral && ((OWLLiteral) value).isBoolean() && ((OWLLiteral) value).parseBoolean();
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (obj instanceof OWLAnnotation) {
                OWLAnnotation other = (OWLAnnotation) obj;
                return other.getProperty().equals(property) && other.getValue().equals(value) && other.getAnnotations().equals(annotations);
            }
        }
        return false;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotation other = (OWLAnnotation) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        else {
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
