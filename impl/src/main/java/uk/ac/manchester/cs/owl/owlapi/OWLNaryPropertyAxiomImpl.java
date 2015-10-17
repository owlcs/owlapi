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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.compareStreams;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 * @param
 *        <P>
 *        the property expression
 */
public abstract class OWLNaryPropertyAxiomImpl<P extends OWLPropertyExpression> extends OWLPropertyAxiomImpl
    implements OWLNaryPropertyAxiom<P> {

    protected final @Nonnull List<P> properties;

    /**
     * @param properties
     *        properties
     * @param annotations
     *        annotations
     */
    @SuppressWarnings("unchecked")
    public OWLNaryPropertyAxiomImpl(Collection<? extends P> properties, Collection<OWLAnnotation> annotations) {
        super(annotations);
        checkNotNull(properties, "properties cannot be null");
        this.properties = (List<P>) CollectionFactory.sortOptionally(properties.stream().distinct());
    }

    @SafeVarargs
    OWLNaryPropertyAxiomImpl(Collection<OWLAnnotation> annotations, P... properties) {
        super(annotations);
        checkNotNull(properties, "properties cannot be null");
        Arrays.sort(properties);
        this.properties = Arrays.asList(properties);
    }

    @Override
    public Stream<P> properties() {
        return properties.stream();
    }

    @Override
    public Set<P> getPropertiesMinus(P property) {
        Set<P> props = new TreeSet<>(properties);
        props.remove(property);
        return props;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLNaryPropertyAxiom)) {
            return false;
        }
        if (obj instanceof OWLNaryPropertyAxiomImpl) {
            return properties.equals(((OWLNaryPropertyAxiomImpl<?>) obj).properties);
        }
        return compareObjectOfSameType((OWLNaryPropertyAxiom<?>) obj) == 0;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        return compareStreams(properties(), ((OWLNaryPropertyAxiom<?>) object).properties());
    }

    @Override
    public <T> Collection<T> walkPairwise(OWLPairwiseVisitor<T, P> visitor) {
        List<T> l = new ArrayList<>();
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                T t = visitor.visit(properties.get(i), properties.get(j));
                if (t != null) {
                    l.add(t);
                }
            }
        }
        return l;
    }

    @Override
    public void forEach(OWLPairwiseVoidVisitor<P> visitor) {
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                visitor.visit(properties.get(i), properties.get(j));
            }
        }
    }

    @Override
    public boolean anyMatch(OWLPairwiseBooleanVisitor<P> visitor) {
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                boolean b = visitor.visit(properties.get(i), properties.get(j));
                if (b) {
                    return b;
                }
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(OWLPairwiseBooleanVisitor<P> visitor) {
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                boolean b = visitor.visit(properties.get(i), properties.get(j));
                if (!b) {
                    return b;
                }
            }
        }
        return true;
    }
}
