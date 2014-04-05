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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLSymmetricObjectPropertyAxiomImpl extends
        OWLObjectPropertyCharacteristicAxiomImpl implements
        OWLSymmetricObjectPropertyAxiom {

    private static final long serialVersionUID = 40000L;

    /**
     * @param property
     *        property
     * @param annotations
     *        annotations
     */
    public OWLSymmetricObjectPropertyAxiomImpl(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(property, annotations);
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom> asSubPropertyAxioms() {
        Set<OWLSubObjectPropertyOfAxiom> result = new HashSet<OWLSubObjectPropertyOfAxiom>(
                5);
        result.add(new OWLSubObjectPropertyOfAxiomImpl(getProperty(),
                getProperty().getInverseProperty().getSimplified(),
                NO_ANNOTATIONS));
        result.add(new OWLSubObjectPropertyOfAxiomImpl(getProperty()
                .getInverseProperty().getSimplified(), getProperty(),
                NO_ANNOTATIONS));
        return result;
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLSymmetricObjectPropertyAxiomImpl(getProperty(),
                mergeAnnos(annotations));
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLSymmetricObjectPropertyAxiomImpl(getProperty(),
                NO_ANNOTATIONS);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && obj instanceof OWLSymmetricObjectPropertyAxiom;
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
        return AxiomType.SYMMETRIC_OBJECT_PROPERTY;
    }
}
