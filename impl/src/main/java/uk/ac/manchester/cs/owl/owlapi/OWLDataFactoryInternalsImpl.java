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

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

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
import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author ignazio
 */
public class OWLDataFactoryInternalsImpl extends OWLDataFactoryInternalsImplNoCache {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    transient private LoadingCache<IRI, OWLClass> classesByURI;
    @Nonnull
    transient private LoadingCache<IRI, OWLObjectProperty> objectPropertiesByURI;
    @Nonnull
    transient private LoadingCache<IRI, OWLDataProperty> dataPropertiesByURI;
    @Nonnull
    transient private LoadingCache<IRI, OWLDatatype> datatypesByURI;
    @Nonnull
    transient private LoadingCache<IRI, OWLNamedIndividual> individualsByURI;
    @Nonnull
    transient private LoadingCache<IRI, OWLAnnotationProperty> annotationPropertiesByURI;
    @Nonnull
    transient private LoadingCache<String, String> languageTagInterner;

    /**
     * Annotations Cache uses a loading cache as a size limited Interner; the value of the loader is
     * simply the key. As with an interner, each access constructs a new object that is discarded if
     * the key is used. Most annotations will only be used once; however some annotations may be
     * reused extremely frequently. for ontologies in the OBO family, a few annotations will be
     * reused extremely frequently.
     */
    transient private LoadingCache<OWLAnnotation, OWLAnnotation> annotationsCache;

    /**
     * @param useCompression true if literals should be compressed
     */
    @Inject
    public OWLDataFactoryInternalsImpl(@CompressionEnabled boolean useCompression) {
        super(useCompression);
        initCaches();
    }

    private void readObject(java.io.ObjectInputStream stream) {
        initCaches();
    }

    protected void initCaches() {
        Caffeine<Object, Object> builder = Caffeine.newBuilder().weakKeys().maximumSize(size());
        classesByURI = builder.build(OWLClassImpl::new);
        objectPropertiesByURI = builder.build(OWLObjectPropertyImpl::new);
        dataPropertiesByURI = builder.build(OWLDataPropertyImpl::new);
        datatypesByURI = builder.build(OWLDatatypeImpl::new);
        individualsByURI = builder.build(OWLNamedIndividualImpl::new);
        annotationPropertiesByURI = builder.build(OWLAnnotationPropertyImpl::new);
        annotationsCache = builder.build(k -> k);
        languageTagInterner = builder.build(k -> k);
    }

    protected long size() {
        return ConfigurationOptions.CACHE_SIZE.getValue(Integer.class, Collections.emptyMap())
            .longValue();
    }

    @Nonnull
    @Override
    public OWLClass getOWLClass(IRI iri) {
        return classesByURI.get(iri);
    }

    @Override
    public void purge() {
        classesByURI.invalidateAll();
        objectPropertiesByURI.invalidateAll();
        dataPropertiesByURI.invalidateAll();
        datatypesByURI.invalidateAll();
        individualsByURI.invalidateAll();
        annotationPropertiesByURI.invalidateAll();
        annotationsCache.invalidateAll();
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return objectPropertiesByURI.get(iri);
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return dataPropertiesByURI.get(iri);
    }

    @Nonnull
    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return individualsByURI.get(iri);
    }

    @Nonnull
    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        return datatypesByURI.get(iri);
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return annotationPropertiesByURI.get(iri);
    }

    @Override
    public OWLLiteral getOWLLiteral(String literal, @Nullable String lang) {
        return super.getOWLLiteral(literal, intern(lang));
    }

    protected String intern(String lang) {
        if (lang == null) {
            return "";
        }
        return languageTagInterner.get(lang.trim().toLowerCase());
    }

    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        OWLAnnotation key = new OWLAnnotationImpl(property, value, annotations);
        return annotationsCache.get(key);
    }
}
