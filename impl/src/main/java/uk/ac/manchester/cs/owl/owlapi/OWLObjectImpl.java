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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.compareStreams;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectImpl implements OWLObject, Serializable, HasIncrementalSignatureGenerationSupport {

    /** a convenience reference for an empty annotation set, saves on typing. */
    protected static final @Nonnull Set<OWLAnnotation> NO_ANNOTATIONS = Collections.emptySet();
    static final OWLObjectTypeIndexProvider OWLOBJECT_TYPEINDEX_PROVIDER = new OWLObjectTypeIndexProvider();
    protected int hashCode = 0;
    protected static CacheLoader<OWLObjectImpl, Set<OWLEntity>> builder = new CacheLoader<OWLObjectImpl, Set<OWLEntity>>() {

        @Override
        public Set<OWLEntity> load(OWLObjectImpl key) {
            return key.addSignatureEntitiesToSet(new HashSet<>());
        }
    };
    protected static LoadingCache<OWLObjectImpl, Set<OWLEntity>> signatures = CacheBuilder.newBuilder().weakKeys()
        .softValues().build(builder);
    protected static CacheLoader<OWLObjectImpl, Set<OWLAnonymousIndividual>> anonbuilder = new CacheLoader<OWLObjectImpl, Set<OWLAnonymousIndividual>>() {

        @Override
        public Set<OWLAnonymousIndividual> load(OWLObjectImpl key) {
            return key.addAnonymousIndividualsToSet(new HashSet<>());
        }
    };
    protected static LoadingCache<OWLObjectImpl, Set<OWLAnonymousIndividual>> anonCaches = CacheBuilder.newBuilder()
        .weakKeys().softValues().build(anonbuilder);

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        try {
            return anonCaches.get(this).stream();
        } catch (ExecutionException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public Stream<OWLEntity> signature() {
        try {
            return signatures.get(this).stream();
        } catch (ExecutionException e) {
            throw new OWLRuntimeException(e);
        }
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
        return signature().filter(e -> e.isOWLClass()).map(e -> e.asOWLClass());
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        return signature().filter(e -> e.isOWLDataProperty()).map(e -> e.asOWLDataProperty());
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return signature().filter(e -> e.isOWLObjectProperty()).map(e -> e.asOWLObjectProperty());
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        return signature().filter(e -> e.isOWLNamedIndividual()).map(e -> e.asOWLNamedIndividual());
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return signature().filter(e -> e.isOWLDatatype()).map(e -> e.asOWLDatatype());
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        return signature().filter(e -> e.isOWLAnnotationProperty()).map(e -> e.asOWLAnnotationProperty());
    }

    @Override
    public Stream<OWLClassExpression> nestedClassExpressions() {
        OWLClassExpressionCollector collector = new OWLClassExpressionCollector();
        return accept(collector).stream();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj == this || obj instanceof OWLObject;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = HashCode.hashCode(this);
        }
        return hashCode;
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        checkNotNull(o);
        assert o != null;
        int thisTypeIndex = index();
        int otherTypeIndex = 0;
        if (o instanceof OWLObjectImpl) {
            otherTypeIndex = ((OWLObjectImpl) o).index();
        } else {
            otherTypeIndex = OWLOBJECT_TYPEINDEX_PROVIDER.getTypeIndex(o);
        }
        int diff = thisTypeIndex - otherTypeIndex;
        if (diff != 0) {
            return diff;
        }
        // Objects are the same type
        diff = compareObjectOfSameType(o);
        if (diff != 0) {
            return diff;
        }
        if (this instanceof OWLAxiom) {
            diff = compareStreams(((OWLAxiom) this).annotations(), ((OWLAxiom) o).annotations());
        }
        return diff;
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

    protected abstract int index();

    protected abstract int compareObjectOfSameType(OWLObject object);

    @Override
    public String toString() {
        return ToStringRenderer.getInstance().getRendering(this);
    }
}
