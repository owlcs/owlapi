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

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLObjectPropertyAssertionAxiomImpl
        extends
        OWLIndividualRelationshipAxiomImpl<OWLObjectPropertyExpression, OWLIndividual>
        implements OWLObjectPropertyAssertionAxiom {

    private static final long serialVersionUID = 40000L;

    /**
     * @param subject
     *        subject
     * @param property
     *        property
     * @param object
     *        object
     * @param annotations
     *        annotations
     */
    public OWLObjectPropertyAssertionAxiomImpl(@Nonnull OWLIndividual subject,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual object,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        super(subject, property, object, annotations);
    }

    @Override
    public OWLObjectPropertyAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLObjectPropertyAssertionAxiomImpl(getSubject(),
                getProperty(), getObject(), NO_ANNOTATIONS);
    }

    @Override
    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        return new OWLSubClassOfAxiomImpl(new OWLObjectOneOfImpl(
                Collections.singleton(getSubject())),
                new OWLObjectHasValueImpl(getProperty(), getObject()),
                NO_ANNOTATIONS);
    }

    @Override
    public OWLObjectPropertyAssertionAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLObjectPropertyAssertionAxiomImpl(getSubject(),
                getProperty(), getObject(), mergeAnnos(annotations));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && obj instanceof OWLObjectPropertyAssertionAxiom;
    }

    @Override
    public OWLObjectPropertyAssertionAxiom getSimplified() {
        if (!getProperty().isAnonymous()) {
            return this;
        } else {
            OWLObjectInverseOf property = (OWLObjectInverseOf) getProperty();
            OWLObjectPropertyExpression invProp = property.getInverse();
            return new OWLObjectPropertyAssertionAxiomImpl(getSubject(),
                    invProp, getObject(), NO_ANNOTATIONS);
        }
    }

    @Override
    public boolean isInSimplifiedForm() {
        return !getProperty().isAnonymous();
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.OBJECT_PROPERTY_ASSERTION;
    }
}
