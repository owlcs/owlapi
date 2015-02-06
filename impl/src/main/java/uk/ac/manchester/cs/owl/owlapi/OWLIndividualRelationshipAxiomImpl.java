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

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * @param <P>
 *        the property expression
 * @param <O>
 *        the object
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLIndividualRelationshipAxiomImpl<P extends OWLPropertyExpression, O extends OWLPropertyAssertionObject>
        extends OWLLogicalAxiomImplWithoutEntityAndAnonCaching implements
        OWLPropertyAssertionAxiom<P, O> {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLIndividual subject;
    @Nonnull
    private final P property;
    @Nonnull
    private final O o;

    /**
     * @param subject
     *        the subject
     * @param property
     *        the property
     * @param object
     *        the object
     * @param annotations
     *        the annotations
     */
    public OWLIndividualRelationshipAxiomImpl(@Nonnull OWLIndividual subject,
            @Nonnull P property, @Nonnull O object,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.subject = checkNotNull(subject, "subject cannot be null");
        this.property = checkNotNull(property, "property cannot be null");
        this.o = checkNotNull(object, "object cannot be null");
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        if (getSubject().isNamed()) {
            entities.add(getSubject().asOWLNamedIndividual());
        }
        addSignatureEntitiesToSetForValue(entities, getProperty());
        addSignatureEntitiesToSetForValue(entities, getObject());
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {
        if (getSubject().isAnonymous()) {
            anons.add(getSubject().asOWLAnonymousIndividual());
        }
        addAnonymousIndividualsToSetForValue(anons, getProperty());
        addAnonymousIndividualsToSetForValue(anons, getObject());
    }

    @Override
    public OWLIndividual getSubject() {
        return subject;
    }

    @Override
    public P getProperty() {
        return property;
    }

    @Override
    public O getObject() {
        return o;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLPropertyAssertionAxiom)) {
            return false;
        }
        OWLPropertyAssertionAxiom<?, ?> other = (OWLPropertyAssertionAxiom<?, ?>) obj;
        return other.getSubject().equals(subject)
                && other.getProperty().equals(property)
                && other.getObject().equals(o);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLPropertyAssertionAxiom<?, ?> other = (OWLPropertyAssertionAxiom<?, ?>) object;
        int diff = subject.compareTo(other.getSubject());
        if (diff != 0) {
            return diff;
        }
        diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return o.compareTo(other.getObject());
    }
}
