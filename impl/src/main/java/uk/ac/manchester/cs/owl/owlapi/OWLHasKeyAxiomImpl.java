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
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLHasKeyAxiomImpl extends OWLLogicalAxiomImpl implements
        OWLHasKeyAxiom {

    private static final long serialVersionUID = 40000L;
    private final OWLClassExpression expression;
    private final Set<OWLPropertyExpression> propertyExpressions;

    /**
     * @param expression
     *        class expression
     * @param propertyExpressions
     *        properties
     * @param annotations
     *        annotations on the axiom
     */
    public OWLHasKeyAxiomImpl(@Nonnull OWLClassExpression expression,
            @Nonnull Set<? extends OWLPropertyExpression> propertyExpressions,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.expression = checkNotNull(expression, "expression cannot be null");
        this.propertyExpressions = new TreeSet<OWLPropertyExpression>(
                checkNotNull(propertyExpressions,
                        "propertyExpressions cannot be null"));
    }

    @Override
    public OWLHasKeyAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLHasKeyAxiomImpl(getClassExpression(),
                getPropertyExpressions(), NO_ANNOTATIONS);
    }

    @Override
    public OWLHasKeyAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return new OWLHasKeyAxiomImpl(getClassExpression(),
                getPropertyExpressions(), mergeAnnos(annotations));
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.HAS_KEY;
    }

    @Override
    public boolean isLogicalAxiom() {
        return true;
    }

    @Override
    public OWLClassExpression getClassExpression() {
        return expression;
    }

    @Override
    public Set<OWLPropertyExpression> getPropertyExpressions() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(propertyExpressions);
    }

    @Override
    public Set<OWLDataPropertyExpression> getDataPropertyExpressions() {
        Set<OWLDataPropertyExpression> props = new TreeSet<OWLDataPropertyExpression>();
        for (OWLPropertyExpression prop : propertyExpressions) {
            if (prop.isDataPropertyExpression()) {
                props.add((OWLDataPropertyExpression) prop);
            }
        }
        return props;
    }

    @Override
    public Set<OWLObjectPropertyExpression> getObjectPropertyExpressions() {
        Set<OWLObjectPropertyExpression> props = new TreeSet<OWLObjectPropertyExpression>();
        for (OWLPropertyExpression prop : propertyExpressions) {
            if (prop.isObjectPropertyExpression()) {
                props.add((OWLObjectPropertyExpression) prop);
            }
        }
        return props;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLHasKeyAxiom other = (OWLHasKeyAxiom) object;
        int diff = expression.compareTo(other.getClassExpression());
        if (diff != 0) {
            return diff;
        }
        return compareSets(propertyExpressions, other.getPropertyExpressions());
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
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
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            // superclass is responsible for null, identity, owlaxiom type and
            // annotations
            if (!(obj instanceof OWLHasKeyAxiom)) {
                return false;
            }
            OWLHasKeyAxiom other = (OWLHasKeyAxiom) obj;
            return expression.equals(other.getClassExpression())
                    && propertyExpressions.equals(other
                            .getPropertyExpressions());
        }
        return false;
    }
}
