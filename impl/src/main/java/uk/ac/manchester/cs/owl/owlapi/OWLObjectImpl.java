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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.compareIterators;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.equalStreams;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.streamFromSorted;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
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
import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectImpl
    implements OWLObject, Serializable, HasIncrementalSignatureGenerationSupport {

    /**
     * a convenience reference for an empty annotation set, saves on typing.
     */
    protected static final Set<OWLAnnotation> NO_ANNOTATIONS = Collections.emptySet();

    // @formatter:off
    protected static LoadingCache<OWLObjectImpl, Set<OWLEntity>>              signatures =                      build(key -> key.addSignatureEntitiesToSet(new TreeSet<>()));
    protected static LoadingCache<OWLObjectImpl, Set<OWLAnonymousIndividual>> anonCaches =                      build(key -> key.addAnonymousIndividualsToSet(new TreeSet<>()));
    protected static LoadingCache<OWLObjectImpl, List<OWLClass>>              classesSignatures =               build(key -> cacheSig(key, OWLEntity::isOWLClass,               OWLEntity::asOWLClass));
    protected static LoadingCache<OWLObjectImpl, List<OWLDataProperty>>       dataPropertySignatures =          build(key -> cacheSig(key, OWLEntity::isOWLDataProperty,        OWLEntity::asOWLDataProperty));
    protected static LoadingCache<OWLObjectImpl, List<OWLObjectProperty>>     objectPropertySignatures =        build(key -> cacheSig(key, OWLEntity::isOWLObjectProperty,      OWLEntity::asOWLObjectProperty));
    protected static LoadingCache<OWLObjectImpl, List<OWLDatatype>>           datatypeSignatures =              build(key -> cacheSig(key, OWLEntity::isOWLDatatype,            OWLEntity::asOWLDatatype));
    protected static LoadingCache<OWLObjectImpl, List<OWLNamedIndividual>>    individualSignatures =            build(key -> cacheSig(key, OWLEntity::isOWLNamedIndividual,     OWLEntity::asOWLNamedIndividual));
    protected static LoadingCache<OWLObjectImpl, List<OWLAnnotationProperty>> annotationPropertiesSignatures =  build(key -> cacheSig(key, OWLEntity::isOWLAnnotationProperty,  OWLEntity::asOWLAnnotationProperty));
    // @formatter:on
    static <Q, T> LoadingCache<Q, T> build(CacheLoader<Q, T> c) {
        return Caffeine.newBuilder().weakKeys().maximumSize(size()).build(c);
    }

    protected static long size() {
        return ConfigurationOptions.CACHE_SIZE.getValue(Integer.class, Collections.emptyMap())
            .longValue();
    }

    static <T> List<T> cacheSig(OWLObject o, Predicate<OWLEntity> p, Function<OWLEntity, T> f) {
        return asList(o.signature().filter(p).map(f));
    }

    protected int hashCode = 0;

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        return anonCaches.get(this).stream();
    }

    @Override
    public Stream<OWLEntity> signature() {
        return signatures.get(this).stream();
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return signatures.get(this).contains(owlEntity);
    }

    @Override
    public Stream<OWLClass> classesInSignature() {
        return streamFromSorted(classesSignatures.get(this));
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        return streamFromSorted(dataPropertySignatures.get(this));
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return streamFromSorted(objectPropertySignatures.get(this));
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        return streamFromSorted(individualSignatures.get(this));
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return streamFromSorted(datatypeSignatures.get(this));
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        return streamFromSorted(annotationPropertiesSignatures.get(this));
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
        if (typeIndex() != other.typeIndex() || hashCode() != other.hashCode()) {
            return false;
        }
        return equalStreams(components(), other.components());
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = initHashCode();
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
