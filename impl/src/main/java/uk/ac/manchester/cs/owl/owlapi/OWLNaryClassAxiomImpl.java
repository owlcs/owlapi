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

import static org.semanticweb.owlapi.util.CollectionFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.compareStreams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLNaryClassAxiomImpl extends OWLClassAxiomImpl implements OWLNaryClassAxiom {

    protected final @Nonnull List<OWLClassExpression> classExpressions;

    /**
     * @param classExpressions
     *        classes
     * @param annotations
     *        annotations
     */
    @SuppressWarnings("unchecked")
    public OWLNaryClassAxiomImpl(Collection<? extends OWLClassExpression> classExpressions,
        Collection<OWLAnnotation> annotations) {
        super(annotations);
        checkNotNull(classExpressions, "classExpressions cannot be null");
        this.classExpressions = (List<OWLClassExpression>) sortOptionally(classExpressions.stream().distinct());
    }

    @Override
    public Set<OWLClassExpression> getClassExpressions() {
        return copy(classExpressions);
    }

    @Override
    public Stream<OWLClassExpression> classExpressions() {
        return classExpressions.stream();
    }

    @Override
    public boolean contains(OWLClassExpression ce) {
        return classExpressions.contains(ce);
    }

    @Override
    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc) {
        Set<OWLClassExpression> result = new HashSet<>(classExpressions);
        for (OWLClassExpression d : desc) {
            result.remove(d);
        }
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLNaryClassAxiom)) {
            return false;
        }
        if (obj instanceof OWLNaryClassAxiomImpl) {
            return classExpressions.equals(((OWLNaryClassAxiomImpl) obj).classExpressions);
        }
        return compareObjectOfSameType((OWLNaryClassAxiom) obj) == 0;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        return compareStreams(classExpressions(), ((OWLNaryClassAxiom) object).classExpressions());
    }

    @Override
    public <T> Collection<T> walkPairwise(OWLPairwiseVisitor<T, OWLClassExpression> visitor) {
        List<T> l = new ArrayList<>();
        for (int i = 0; i < classExpressions.size() - 1; i++) {
            for (int j = i + 1; j < classExpressions.size(); j++) {
                T t = visitor.visit(classExpressions.get(i), classExpressions.get(j));
                if (t != null) {
                    l.add(t);
                }
            }
        }
        return l;
    }

    @Override
    public void forEach(OWLPairwiseVoidVisitor<OWLClassExpression> visitor) {
        for (int i = 0; i < classExpressions.size() - 1; i++) {
            for (int j = i + 1; j < classExpressions.size(); j++) {
                visitor.visit(classExpressions.get(i), classExpressions.get(j));
            }
        }
    }

    @Override
    public boolean anyMatch(OWLPairwiseBooleanVisitor<OWLClassExpression> visitor) {
        for (int i = 0; i < classExpressions.size() - 1; i++) {
            for (int j = i + 1; j < classExpressions.size(); j++) {
                boolean b = visitor.visit(classExpressions.get(i), classExpressions.get(j));
                if (b) {
                    return b;
                }
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(OWLPairwiseBooleanVisitor<OWLClassExpression> visitor) {
        for (int i = 0; i < classExpressions.size() - 1; i++) {
            for (int j = i + 1; j < classExpressions.size(); j++) {
                boolean b = visitor.visit(classExpressions.get(i), classExpressions.get(j));
                if (!b) {
                    return b;
                }
            }
        }
        return true;
    }
}
