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

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 14-Jan-2009
 */
public class OWLAnnotationPropertyImpl extends OWLObjectImpl implements OWLAnnotationProperty {

    private IRI iri;

    public OWLAnnotationPropertyImpl(OWLDataFactory dataFactory, IRI iri) {
        super(dataFactory);
        this.iri = iri;
    }

    public IRI getIRI() {
        return iri;
    }

    /**
     * Gets the entity type for this entity
     * @return The entity type
     */
    public EntityType<?> getEntityType() {
        return EntityType.ANNOTATION_PROPERTY;
    }

    /**
     * Gets an entity that has the same IRI as this entity but is of the specified type.
     * @param entityType The type of the entity to obtain.  This entity is not affected in any way.
     * @return An entity that has the same IRI as this entity and is of the specified type
     */
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType) {
        return getOWLDataFactory().getOWLEntity(entityType, getIRI());
    }

    /**
     * Tests to see if this entity is of the specified type
     * @param entityType The entity type
     * @return <code>true</code> if this entity is of the specified type, otherwise <code>false</code>.
     */
    public boolean isType(EntityType<?> entityType) {
        return getEntityType().equals(entityType);
    }

    /**
     * Returns a string representation that can be used as the ID of this entity.  This is the toString
     * representation of the IRI
     * @return A string representing the toString of the IRI of this entity.
     */
    public String toStringID() {
        return iri.toString();
    }

    public boolean isDeprecated() {
        return iri.equals(OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return iri.compareTo(((OWLAnnotationProperty) object).getIRI());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public boolean isComment() {
        return iri.equals(OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    }

    public boolean isLabel() {
        return iri.equals(OWLRDFVocabulary.RDFS_LABEL.getIRI());
    }


    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not OWLClass");
    }

    public OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not OWLDataProperty");
    }

    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not OWLDatatype");
    }

    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not OWLIndividual");
    }

    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not OWLObjectProperty");
    }

    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }

    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }

    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty, Collections.singleton(ontology));
    }

    public boolean isBuiltIn() {
        return OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS.contains(getIRI());
    }

    public boolean isOWLClass() {
        return false;
    }

    public boolean isOWLDataProperty() {
        return false;
    }

    public boolean isOWLDatatype() {
        return false;
    }

    public boolean isOWLNamedIndividual() {
        return false;
    }

    public boolean isOWLObjectProperty() {
        return false;
    }

    public OWLAnnotationProperty asOWLAnnotationProperty() {
        return this;
    }

    public boolean isOWLAnnotationProperty() {
        return true;
    }

    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    public URI getURI() {
        return iri.toURI();
    }


    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
        return ontology.getReferencingAxioms(this);
    }

    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
        return ontology.getReferencingAxioms(this, includeImports);
    }

    public Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology) {
        return getSubProperties(Collections.singleton(ontology));
    }

    public Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology, boolean includeImportsClosure) {
        if (includeImportsClosure) {
            return getSubProperties(ontology.getImportsClosure());
        }
        else {
            return getSubProperties(Collections.singleton(ontology));
        }
    }

    public Set<OWLAnnotationProperty> getSubProperties(Set<OWLOntology> ontologies) {
        Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
        for(OWLOntology ont : ontologies) {
            for(OWLSubAnnotationPropertyOfAxiom ax : ont.getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
                if (ax.getSuperProperty().equals(this)) {
                    result.add(ax.getSubProperty());
                }
            }
        }
        return result;
    }
    
    
    public Set<OWLAnnotationProperty> getSuperProperties(OWLOntology ontology) {
        return getSuperProperties(Collections.singleton(ontology));
    }

    public Set<OWLAnnotationProperty> getSuperProperties(OWLOntology ontology, boolean includeImportsClosure) {
        if (includeImportsClosure) {
            return getSuperProperties(ontology.getImportsClosure());
        }
        else {
            return getSuperProperties(Collections.singleton(ontology));
        }
    }

    public Set<OWLAnnotationProperty> getSuperProperties(Set<OWLOntology> ontologies) {
        Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
        for(OWLOntology ont : ontologies) {
            for(OWLSubAnnotationPropertyOfAxiom ax : ont.getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
                if (ax.getSubProperty().equals(this)) {
                    result.add(ax.getSuperProperty());
                }
            }
        }
        return result;
    }

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLAnnotationProperty)) {
            return false;
        }
        OWLAnnotationProperty other = (OWLAnnotationProperty) obj;
        return iri.equals(other.getIRI());
    }

}
