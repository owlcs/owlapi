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

import static java.util.Collections.emptyList;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.compareIterators;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.equalStreams;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectImpl
                implements OWLObject, Serializable, HasIncrementalSignatureGenerationSupport {

    /** a convenience reference for an empty annotation set, saves on typing. */
    protected static final Set<OWLAnnotation> NO_ANNOTATIONS = Collections.emptySet();
    protected int hashCode = 0;
    protected static LoadingCache<OWLObjectImpl, Set<OWLEntity>> signatures =
                    Caffeine.newBuilder().weakKeys().softValues()
                                    .build(key -> key.addSignatureEntitiesToSet(new HashSet<>()));
    protected static LoadingCache<OWLObjectImpl, Set<OWLAnonymousIndividual>> anonCaches =
                    Caffeine.newBuilder().weakKeys().softValues().build(
                                    key -> key.addAnonymousIndividualsToSet(new HashSet<>()));
    protected static final IntBinaryOperator hashIteration = (a, b) -> a * 37 + b;

    /**
     * Override point to change hashing strategy if needed - only used in OWLLiteral implementations
     * at the moment.
     * 
     * @param object the object to compute the hashcode for
     * @return the hashcode
     */
    protected int hashCode(OWLObject object) {
        return hash(object.hashIndex(), object.components());
    }

    /**
     * Streams from components need a start point and the order of the components is important.
     * 
     * @param start start index
     * @param s stream to hash
     * @return hash for the stream
     */
    static int hash(int start, Stream<?> s) {
        return s.sequential().mapToInt(OWLObjectImpl::hash).reduce(start, hashIteration);
    }

    private static int hash(Object o) {
        if (o instanceof Stream) {
            return ((Stream<?>) o).mapToInt(Object::hashCode).sum();
        }
        return o.hashCode();
    }

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        return verifyNotNull(anonCaches.get(this)).stream();
    }

    @Override
    public Stream<OWLEntity> signature() {
        return verifyNotNull(signatures.get(this)).stream();
    }

    protected static List<OWLAnnotation> asAnnotations(Collection<OWLAnnotation> anns) {
        if (anns.isEmpty()) {
            return emptyList();
        }
        if (anns.size() == 1) {
            return Collections.singletonList(anns.iterator().next());
        }
        return sortOptionally(anns.stream().distinct());
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return signature().anyMatch(e -> e.equals(owlEntity));
    }

    @Override
    public Stream<OWLClass> classesInSignature() {
        return signature().filter(OWLEntity::isOWLClass).map(OWLEntity::asOWLClass);
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        return signature().filter(OWLEntity::isOWLDataProperty).map(OWLEntity::asOWLDataProperty);
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return signature().filter(OWLEntity::isOWLObjectProperty)
                        .map(OWLEntity::asOWLObjectProperty);
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        return signature().filter(OWLEntity::isOWLNamedIndividual)
                        .map(OWLEntity::asOWLNamedIndividual);
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return signature().filter(OWLEntity::isOWLDatatype).map(OWLEntity::asOWLDatatype);
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        return signature().filter(OWLEntity::isOWLAnnotationProperty)
                        .map(OWLEntity::asOWLAnnotationProperty);
    }

    @Override
    public Stream<OWLClassExpression> nestedClassExpressions() {
        return accept(new OWLClassExpressionCollector()).stream();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OWLObject)) {
            return false;
        }
        OWLObject other = (OWLObject) obj;
        if (typeIndex() != other.typeIndex()) {
            return false;
        }
        return equalStreams(components(), other.components());
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = hashCode(this);
        }
        return hashCode;
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        checkNotNull(o);
        assert o != null;
        int diff = Integer.compare(typeIndex(), o.typeIndex());
        if (diff != 0) {
            return diff;
        }
        return compareIterators(components().iterator(), o.components().iterator());
    }

    protected int compareAnnotations(List<OWLAnnotation> l1, List<OWLAnnotation> l2) {
        int i = 0;
        for (; i < l1.size() && i < l2.size(); i++) {
            int diff = l1.get(i).compareTo(l2.get(i));
            if (diff != 0) {
                return diff;
            }
        }
        if (i < l2.size()) {
            // l1 is shorter and a sublist of l2
            return -1;
        }
        if (i < l1.size()) {
            // l2 is shorter and a sublist of l1
            return 1;
        }
        // lists are identical
        return 0;
    }

    @Override
    public String toString() {
        return ToStringRenderer.getRendering(this);
    }
}
