/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLObjectPropertyImpl extends OWLObjectPropertyExpressionImpl
        implements OWLObjectProperty {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final IRI iri;
    private final boolean builtin;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.OBJECT_PROPERTY;
    }

    /**
     * @param iri
     *        property iri
     */
    public OWLObjectPropertyImpl(@Nonnull IRI iri) {
        this.iri = checkNotNull(iri, "iri cannot be null");
        builtin = iri.equals(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI())
                || iri.equals(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY
                        .getIRI());
    }

    @Override
    public boolean isTopEntity() {
        return isOWLTopObjectProperty();
    }

    @Override
    public boolean isBottomEntity() {
        return isOWLBottomObjectProperty();
    }

    @Override
    public EntityType<?> getEntityType() {
        return EntityType.OBJECT_PROPERTY;
    }

    @Override
    public boolean isType(EntityType<?> entityType) {
        return getEntityType().equals(entityType);
    }

    @Override
    public String toStringID() {
        return iri.toString();
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public boolean isBuiltIn() {
        return builtin;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLObjectProperty)) {
                return false;
            }
            IRI otherIRI = ((OWLObjectProperty) obj).getIRI();
            return otherIRI.equals(iri);
        }
        return false;
    }

    @Override
    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLPropertyExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLNamedObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public OWLObjectProperty asOWLObjectProperty() {
        return this;
    }

    @Override
    public OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not a data property!");
    }

    @Override
    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
    }

    @Override
    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not an individual!");
    }

    @Override
    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass!");
    }

    @Override
    public boolean isOWLClass() {
        return false;
    }

    @Override
    public boolean isOWLDataProperty() {
        return false;
    }

    @Override
    public boolean isOWLDatatype() {
        return false;
    }

    @Override
    public boolean isOWLNamedIndividual() {
        return false;
    }

    @Override
    public boolean isOWLObjectProperty() {
        return true;
    }

    @Override
    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new OWLRuntimeException("Not an annotation property");
    }

    @Override
    public boolean isOWLAnnotationProperty() {
        return false;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        return iri.compareTo(((OWLObjectProperty) object).getIRI());
    }

    @Override
    public boolean isOWLTopObjectProperty() {
        return iri.equals(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    }

    @Override
    public boolean isOWLBottomObjectProperty() {
        return iri.equals(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    }

    @Override
    public boolean isOWLTopDataProperty() {
        return false;
    }

    @Override
    public boolean isOWLBottomDataProperty() {
        return false;
    }
}
