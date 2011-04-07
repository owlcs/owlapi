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

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 17-Jan-2009
 */
public class OWLAnnotationPropertyRangeAxiomImpl extends OWLAxiomImpl implements OWLAnnotationPropertyRangeAxiom {

    private OWLAnnotationProperty property;

    protected IRI range;

    public OWLAnnotationPropertyRangeAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, IRI range, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.property = property;
        this.range = range;
    }

    public OWLAnnotationPropertyRangeAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLAnnotationPropertyRangeAxiom(getProperty(), getRange());
    }

    public OWLAnnotationPropertyRangeAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLAnnotationPropertyRangeAxiom(getProperty(), getRange(), mergeAnnos(annotations));
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }

    public IRI getRange() {
        return range;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_RANGE;
    }

    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) object;
        int diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return range.compareTo(other.getRange());
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


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLAnnotationPropertyRangeAxiom)) {
            return false;
        }
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) obj;
        return property.equals(other.getProperty()) && range.equals(other.getRange()) && getAnnotations().equals(other.getAnnotations());
    }
}
