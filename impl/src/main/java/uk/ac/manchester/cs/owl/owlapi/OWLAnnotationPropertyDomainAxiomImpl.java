package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collection;
import java.util.Set;/*
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
public class OWLAnnotationPropertyDomainAxiomImpl extends OWLAxiomImpl implements OWLAnnotationPropertyDomainAxiom {

    private OWLAnnotationProperty property;

    private IRI domain;

    public OWLAnnotationPropertyDomainAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, IRI domain, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.domain = domain;
        this.property = property;
    }

    public OWLAnnotationPropertyDomainAxiom getAxiomWithoutAnnotations() {
        if(!isAnnotated()) {
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

    public AxiomType getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_DOMAIN;
    }

    public boolean isLogicalAxiom() {
        return false;
    }

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


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OWLAnnotationPropertyDomainAxiom)) {
            return false;
        }
        OWLAnnotationPropertyDomainAxiom other = (OWLAnnotationPropertyDomainAxiom) obj;
        return property.equals(other.getProperty()) && domain.equals(other.getDomain())  && getAnnotations().equals(other.getAnnotations());
    }
}
