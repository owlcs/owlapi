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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;


import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLObjectOneOfImpl extends OWLAnonymousClassExpressionImpl
        implements OWLObjectOneOf {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final Set<OWLIndividual> values;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.CLASS_EXPRESSION_TYPE_INDEX_BASE + 4;
    }

    /**
     * @param values
     *        values for oneof
     */
    public OWLObjectOneOfImpl(@Nonnull Set<? extends OWLIndividual> values) {
        this.values = new HashSet<OWLIndividual>(checkNotNull(values,
                "values cannot be null"));
    }

    @Override
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_ONE_OF;
    }

    @Override
    public Set<OWLIndividual> getIndividuals() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(values);
    }

    @Override
    public boolean isClassExpressionLiteral() {
        return false;
    }

    @Override
    public OWLClassExpression asObjectUnionOf() {
        if (values.size() == 1) {
            return this;
        } else {
            Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
            for (OWLIndividual ind : values) {
                ops.add(new OWLObjectOneOfImpl(Collections.singleton(ind)));
            }
            return new OWLObjectUnionOfImpl(ops);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLObjectOneOf)) {
                return false;
            }
            return ((OWLObjectOneOf) obj).getIndividuals().equals(values);
        }
        return false;
    }

    @Override
    public void accept(OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(values, ((OWLObjectOneOf) object).getIndividuals());
    }
}
