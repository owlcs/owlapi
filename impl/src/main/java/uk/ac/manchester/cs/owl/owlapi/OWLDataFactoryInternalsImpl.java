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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author ignazio
 */
public class OWLDataFactoryInternalsImpl extends OWLDataFactoryInternalsImplNoCache {

    private static Logger logger = LoggerFactory.getLogger(OWLDataFactoryInternalsImpl.class);
    private final AtomicInteger annotationsCount = new AtomicInteger(0);
    /**
     * Annotations Cache uses a loading cache as a size limited Interner; the
     * value of the loader is simply the key. As with an interner, each access
     * constructs a new object that is discarded if the key is used. Most
     * annotations will only be used once; however some annotations may be
     * reused extremely frequently. for ontologies in the OBO family, a few
     * annotations will be reused extremely frequently.
     */
    private final transient LoadingCache<OWLAnnotation, OWLAnnotation> annotations = builder(key -> key);
    private final LoadingCache<IRI, OWLClass> classes = builder(key -> new OWLClassImpl(key));
    private final LoadingCache<IRI, OWLObjectProperty> objectProperties = builder(key -> new OWLObjectPropertyImpl(
        key));
    private final LoadingCache<IRI, OWLDataProperty> dataProperties = builder(key -> new OWLDataPropertyImpl(key));
    private final LoadingCache<IRI, OWLDatatype> datatypes = builder(key -> new OWLDatatypeImpl(key));
    private final LoadingCache<IRI, OWLNamedIndividual> individuals = builder(key -> new OWLNamedIndividualImpl(key));
    private final LoadingCache<IRI, OWLAnnotationProperty> annotationProperties = builder(
        key -> new OWLAnnotationPropertyImpl(key));

    /**
     * @param useCompression
     *        true if literals should be compressed
     */
    public OWLDataFactoryInternalsImpl(boolean useCompression) {
        super(useCompression);
    }

    private static <F, T> LoadingCache<F, T> builder(CacheLoader<F, T> f) {
        Caffeine<Object, Object> builder = Caffeine.newBuilder().maximumSize(1024).expireAfterAccess(5,
            TimeUnit.MINUTES);
        if (logger.isDebugEnabled()) {
            builder.recordStats();
        }
        return builder.build(f);
    }

    @Override
    public OWLClass getOWLClass(IRI iri) {
        return classes.get(iri);
    }

    @Override
    public void purge() {
        classes.invalidateAll();
        objectProperties.invalidateAll();
        dataProperties.invalidateAll();
        datatypes.invalidateAll();
        individuals.invalidateAll();
        annotationProperties.invalidateAll();
        annotations.invalidateAll();
    }

    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return objectProperties.get(iri);
    }

    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return dataProperties.get(iri);
    }

    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return individuals.get(iri);
    }

    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        return datatypes.get(iri);
    }

    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return annotationProperties.get(iri);
    }

    @Override
    public OWLLiteral getOWLLiteral(String literal, @Nullable String lang) {
        return super.getOWLLiteral(literal, lang);
    }

    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        Stream<OWLAnnotation> anns) {
        OWLAnnotation annotation = annotations.get(new OWLAnnotationImpl(property, value, anns));
        if (logger.isDebugEnabled()) {
            int n = annotationsCount.incrementAndGet();
            if (n % 1000 == 0) {
                logger.debug("{}: Annotations Cache stats: {}", Integer.valueOf(n), annotations.stats());
            }
        }
        return annotation;
    }
}
