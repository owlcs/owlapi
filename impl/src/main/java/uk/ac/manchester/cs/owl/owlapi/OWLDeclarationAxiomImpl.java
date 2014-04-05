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

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLDeclarationAxiomImpl extends OWLAxiomImpl implements
        OWLDeclarationAxiom {

    private static final long serialVersionUID = 40000L;
    private final OWLEntity entity;

    /**
     * @param entity
     *        entity to declare
     * @param annotations
     *        annotations on the axiom
     */
    public OWLDeclarationAxiomImpl(@Nonnull OWLEntity entity,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.entity = checkNotNull(entity, "entity cannot be null");
    }

    @Override
    public boolean isLogicalAxiom() {
        return false;
    }

    @Override
    public boolean isAnnotationAxiom() {
        return false;
    }

    @Override
    public OWLDeclarationAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLDeclarationAxiomImpl(getEntity(), NO_ANNOTATIONS);
    }

    @Override
    public OWLDeclarationAxiom
            getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return new OWLDeclarationAxiomImpl(getEntity(), mergeAnnos(annotations));
    }

    @Override
    public OWLEntity getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            // superclass is responsible for null, identity, owlaxiom type and
            // annotations
            if (obj instanceof OWLDeclarationAxiom) {
                return ((OWLDeclarationAxiom) obj).getEntity().equals(entity);
            }
        }
        return false;
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
        return AxiomType.DECLARATION;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        return entity.compareTo(((OWLDeclarationAxiom) object).getEntity());
    }
}
