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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLPairwiseVisitor;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLInverseObjectPropertiesAxiomImpl extends
        OWLNaryPropertyAxiomImpl<OWLObjectPropertyExpression> implements
        OWLInverseObjectPropertiesAxiom {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLObjectPropertyExpression first;
    @Nonnull
    private final OWLObjectPropertyExpression second;

    /**
     * @param first
     *        first property
     * @param second
     *        second peoperty
     * @param annotations
     *        annotations
     */
    public OWLInverseObjectPropertiesAxiomImpl(
            @Nonnull OWLObjectPropertyExpression first,
            @Nonnull OWLObjectPropertyExpression second,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(new TreeSet<OWLObjectPropertyExpression>(Arrays.asList(
                checkNotNull(first, "first cannot be null"),
                checkNotNull(second, "second cannot be null"))), annotations);
        this.first = first;
        this.second = second;
    }

    @Override
    public Set<OWLInverseObjectPropertiesAxiom> asPairwiseAxioms() {
        return Collections.<OWLInverseObjectPropertiesAxiom> singleton(this);
    }

    @Override
    public OWLInverseObjectPropertiesAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLInverseObjectPropertiesAxiomImpl(getFirstProperty(),
                getSecondProperty(), NO_ANNOTATIONS);
    }

    @Override
    public OWLInverseObjectPropertiesAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLInverseObjectPropertiesAxiomImpl(getFirstProperty(),
                getSecondProperty(), mergeAnnos(annotations));
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
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
    public OWLObjectPropertyExpression getFirstProperty() {
        return first;
    }

    @Override
    public OWLObjectPropertyExpression getSecondProperty() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && obj instanceof OWLInverseObjectPropertiesAxiom;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.INVERSE_OBJECT_PROPERTIES;
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom> asSubObjectPropertyOfAxioms() {
        Set<OWLSubObjectPropertyOfAxiom> axs = new HashSet<OWLSubObjectPropertyOfAxiom>();
        axs.add(new OWLSubObjectPropertyOfAxiomImpl(first, second
                .getInverseProperty().getSimplified(), NO_ANNOTATIONS));
        axs.add(new OWLSubObjectPropertyOfAxiomImpl(second, first
                .getInverseProperty().getSimplified(), NO_ANNOTATIONS));
        return axs;
    }

    @Override
    public <T> Collection<T> walkPairwise(
            OWLPairwiseVisitor<T, OWLObjectPropertyExpression> visitor) {
        T t = visitor.visit(first, second);
        if (t != null) {
            return Collections.singleton(t);
        }
        return Collections.emptyList();
    }
}
