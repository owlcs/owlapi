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
package org.semanticweb.owlapi6.impl;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.streamFromSorted;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.documents.ToStringRenderer;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectType;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.parameters.ConfigurationOptions;
import org.semanticweb.owlapi6.utility.OWLClassExpressionCollector;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectImpl implements OWLObject, Serializable, HasIncrementalSignatureGenerationSupport {

    /**
     * a convenience reference for an empty annotation set, saves on typing.
     */
    protected static final Set<OWLAnnotation> NO_ANNOTATIONS = Collections.emptySet();
    // @formatter:off
    protected static LoadingCache<OWLObjectImpl, Set<OWLEntity>>              signatures =                      build(key -> key.addSignatureEntitiesToSet(new TreeSet<>()));
    protected static LoadingCache<OWLObjectImpl, Set<OWLAnonymousIndividual>> anonCaches =                      build(key -> key.addAnonymousIndividualsToSet(new TreeSet<>()));
    protected static LoadingCache<OWLObjectImpl, Collection<OWLClassExpression>>expressionCaches =              build(key -> key.accept(new OWLClassExpressionCollector()));
    protected static LoadingCache<OWLObjectImpl, List<OWLClass>>              classesSignatures =               build(key -> cacheSig(key, OWLEntity::isOWLClass,               OWLEntity::asOWLClass));
    protected static LoadingCache<OWLObjectImpl, List<OWLDataProperty>>       dataPropertySignatures =          build(key -> cacheSig(key, OWLEntity::isOWLDataProperty,        OWLEntity::asOWLDataProperty));
    protected static LoadingCache<OWLObjectImpl, List<OWLObjectProperty>>     objectPropertySignatures =        build(key -> cacheSig(key, OWLEntity::isOWLObjectProperty,      OWLEntity::asOWLObjectProperty));
    protected static LoadingCache<OWLObjectImpl, List<OWLDatatype>>           datatypeSignatures =              build(key -> cacheSig(key, OWLEntity::isOWLDatatype,            OWLEntity::asOWLDatatype));
    protected static LoadingCache<OWLObjectImpl, List<OWLNamedIndividual>>    individualSignatures =            build(key -> cacheSig(key, OWLEntity::isOWLNamedIndividual,     OWLEntity::asOWLNamedIndividual));
    protected static LoadingCache<OWLObjectImpl, List<OWLAnnotationProperty>> annotationPropertiesSignatures =  build(key -> cacheSig(key, OWLEntity::isOWLAnnotationProperty,  OWLEntity::asOWLAnnotationProperty));
    // @formatter:on
    static <Q, T> LoadingCache<Q, T> build(CacheLoader<Q, T> c) {
        return Caffeine.newBuilder()
            .maximumSize(ConfigurationOptions.CACHE_SIZE.getValue(Integer.class, Collections.emptyMap()).longValue())
            .build(c);
    }

    static <T> List<T> cacheSig(OWLObject o, Predicate<OWLEntity> p, Function<OWLEntity, T> f) {
        return asList(o.unsortedSignature().filter(p).map(f).sorted());
    }

    protected int hashCode = 0;

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        return verifyNotNull(anonCaches.get(this)).stream();
    }

    @Override
    public Stream<OWLEntity> signature() {
        return verifyNotNull(signatures.get(this)).stream();
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return verifyNotNull(signatures.get(this)).contains(owlEntity);
    }

    @Override
    public Stream<OWLClass> classesInSignature() {
        return streamFromSorted(verifyNotNull(classesSignatures.get(this)));
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        return streamFromSorted(verifyNotNull(dataPropertySignatures.get(this)));
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return streamFromSorted(verifyNotNull(objectPropertySignatures.get(this)));
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        return streamFromSorted(verifyNotNull(individualSignatures.get(this)));
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return streamFromSorted(verifyNotNull(datatypeSignatures.get(this)));
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        return streamFromSorted(verifyNotNull(annotationPropertiesSignatures.get(this)));
    }

    @Override
    public Stream<OWLClassExpression> nestedClassExpressions() {
        return verifyNotNull(expressionCaches.get(this)).stream();
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
        return OWLObjectType.equals(this, (OWLObject) obj);
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = initHashCode();
        }
        return hashCode;
    }

    @Override
    public int compareTo(@Nullable OWLObject obj) {
        return OWLObjectType.compareTo(this, verifyNotNull(obj));
    }

    @Override
    public String toString() {
        return ToStringRenderer.getInstance().render(this);
    }

    @Override
    public String toFunctionalSyntax(PrefixManager pm) {
        return toSyntax(new FunctionalSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toManchesterSyntax(PrefixManager pm) {
        return toSyntax(new ManchesterSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toFunctionalSyntax() {
        return toSyntax(new FunctionalSyntaxDocumentFormat());
    }

    @Override
    public String toManchesterSyntax() {
        return toSyntax(new ManchesterSyntaxDocumentFormat());
    }

    @Override
    public String toSyntax(OWLDocumentFormat format) {
        return ToStringRenderer.getInstance(format).render(this);
    }

    @Override
    public String toSyntax(OWLDocumentFormat format, PrefixManager pm) {
        return ToStringRenderer.getInstance(format, pm).render(this);
    }
}
