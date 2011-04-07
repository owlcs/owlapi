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
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
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
public class OWLAnnotationPropertyDomainAxiomImpl extends OWLAxiomImpl implements OWLAnnotationPropertyDomainAxiom {

    private OWLAnnotationProperty property;

    private IRI domain;

    public OWLAnnotationPropertyDomainAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, IRI domain, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.domain = domain;
        this.property = property;
    }

    public OWLAnnotationPropertyDomainAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLAnnotationPropertyDomainAxiom(getProperty(), getDomain());
    }

    public OWLAnnotationPropertyDomainAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLAnnotationPropertyDomainAxiom(getProperty(), getDomain(), mergeAnnos(annotations));
    }

    public IRI getDomain() {
        return domain;
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_DOMAIN;
    }

    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationPropertyDomainAxiom other = (OWLAnnotationPropertyDomainAxiom) object;
        int diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return domain.compareTo(other.getDomain());
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
        if (!(obj instanceof OWLAnnotationPropertyDomainAxiom)) {
            return false;
        }
        OWLAnnotationPropertyDomainAxiom other = (OWLAnnotationPropertyDomainAxiom) obj;
        return property.equals(other.getProperty()) && domain.equals(other.getDomain()) && getAnnotations().equals(other.getAnnotations());
    }
}
