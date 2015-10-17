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

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.WeakIndexCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author ignazio
 */
public class OWLDataFactoryInternalsImpl extends OWLDataFactoryInternalsImplNoCache {

    private static Logger logger = LoggerFactory.getLogger(OWLDataFactoryInternalsImpl.class);
    private final AtomicInteger annotationsCount = new AtomicInteger(0);

    protected class BuildableWeakIndexCache<V extends OWLEntity> extends WeakIndexCache<IRI, V> {

        public V cache(IRI s, Buildable v) {
            WeakReference<V> w = prefixCache.get(s);
            if (w != null) {
                V toReturn = w.get();
                if (toReturn != null) {
                    return toReturn;
                }
            }
            // need to add the new key and return it
            @SuppressWarnings("unchecked")
            V value = (V) v.build(s);
            prefixCache.put(s, new WeakReference<>(value));
            return value;
        }
    }

    private final @Nonnull BuildableWeakIndexCache<OWLClass> classesByURI;
    private final @Nonnull BuildableWeakIndexCache<OWLObjectProperty> objectPropertiesByURI;
    private final @Nonnull BuildableWeakIndexCache<OWLDataProperty> dataPropertiesByURI;
    private final @Nonnull BuildableWeakIndexCache<OWLDatatype> datatypesByURI;
    private final @Nonnull BuildableWeakIndexCache<OWLNamedIndividual> individualsByURI;
    private final @Nonnull BuildableWeakIndexCache<OWLAnnotationProperty> annotationPropertiesByURI;

    protected final <V extends OWLEntity> BuildableWeakIndexCache<V> buildCache() {
        return new BuildableWeakIndexCache<>();
    }

    /**
     * Annotations Cache uses a loading cache as a size limited Interner; the
     * value of the loader is simply the key. As with an interner, each access
     * constructs a new object that is discarded if the key is used. Most
     * annotations will only be used once; however some annotations may be
     * reused extremely frequently. for ontologies in the OBO family, a few
     * annotations will be reused extremely frequently.
     */
    private transient final LoadingCache<OWLAnnotation, OWLAnnotation> annotationsCache;

    /**
     * @param useCompression
     *        true if literals should be compressed
     */
    @Inject
    public OWLDataFactoryInternalsImpl(@CompressionEnabled boolean useCompression) {
        super(useCompression);
        classesByURI = buildCache();
        objectPropertiesByURI = buildCache();
        dataPropertiesByURI = buildCache();
        datatypesByURI = buildCache();
        individualsByURI = buildCache();
        annotationPropertiesByURI = buildCache();
        CacheBuilder<Object, Object> annotationsCacheBuilder = CacheBuilder.newBuilder().maximumSize(512)
                .expireAfterAccess(2, TimeUnit.MINUTES);
        if (logger.isDebugEnabled()) {
            annotationsCacheBuilder.recordStats();
        }
        annotationsCache = annotationsCacheBuilder.build(new CacheLoader<OWLAnnotation, OWLAnnotation>() {

            @Override
            public OWLAnnotation load(OWLAnnotation key) {
                return key;
            }
        });
    }

    @SuppressWarnings({ "unchecked" })
    protected enum Buildable {
        OWLCLASS {

            @Override
            OWLClass build(IRI iri) {
                return new OWLClassImpl(iri);
            }
        },
        OWLOBJECTPROPERTY {

            @Override
            OWLObjectProperty build(IRI iri) {
                return new OWLObjectPropertyImpl(iri);
            }
        },
        OWLDATAPROPERTY {

            @Override
            OWLDataProperty build(IRI iri) {
                return new OWLDataPropertyImpl(iri);
            }
        },
        OWLNAMEDINDIVIDUAL {

            @Override
            OWLNamedIndividual build(IRI iri) {
                return new OWLNamedIndividualImpl(iri);
            }
        },
        OWLDATATYPE {

            @Override
            OWLDatatype build(IRI iri) {
                return new OWLDatatypeImpl(iri);
            }
        },
        OWLANNOTATIONPROPERTY {

            @Override
            OWLAnnotationProperty build(IRI iri) {
                return new OWLAnnotationPropertyImpl(iri);
            }
        };

        abstract <K extends OWLEntity> K build(IRI iri);
    }

    @Override
    public OWLClass getOWLClass(IRI iri) {
        return classesByURI.cache(iri, Buildable.OWLCLASS);
    }

    @Override
    public void purge() {
        classesByURI.clear();
        objectPropertiesByURI.clear();
        dataPropertiesByURI.clear();
        datatypesByURI.clear();
        individualsByURI.clear();
        annotationPropertiesByURI.clear();
        annotationsCache.invalidateAll();
    }

    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return objectPropertiesByURI.cache(iri, Buildable.OWLOBJECTPROPERTY);
    }

    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return dataPropertiesByURI.cache(iri, Buildable.OWLDATAPROPERTY);
    }

    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return individualsByURI.cache(iri, Buildable.OWLNAMEDINDIVIDUAL);
    }

    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        return datatypesByURI.cache(iri, Buildable.OWLDATATYPE);
    }

    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return annotationPropertiesByURI.cache(iri, Buildable.OWLANNOTATIONPROPERTY);
    }

    @Override
    public OWLLiteral getOWLLiteral(String literal, @Nullable String lang) {
        return super.getOWLLiteral(literal, lang(lang));
    }

    private static String lang(@Nullable String l) {
        if (l == null || l.isEmpty()) {
            return "";
        }
        return l.trim().toLowerCase().intern();
    }

    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value,
            Stream<OWLAnnotation> annotations) {
        OWLAnnotation key = new OWLAnnotationImpl(property, value, annotations);
        try {
            OWLAnnotation annotation = annotationsCache.get(key);
            if (logger.isDebugEnabled()) {
                int n = annotationsCount.incrementAndGet();
                if (n % 1000 == 0) {
                    logger.debug("{}: Annotations Cache stats: {}", n, annotationsCache.stats());
                }
            }
            return annotation;
        } catch (@SuppressWarnings("unused") ExecutionException e) {
            // if any exception is raised, just return the input key
            return key;
        }
    }
}
