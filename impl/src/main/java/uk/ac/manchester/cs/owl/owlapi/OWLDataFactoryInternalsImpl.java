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

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.WeakIndexCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

/** @author ignazio */
public class OWLDataFactoryInternalsImpl extends InternalsNoCache {

    private static final long serialVersionUID = 40000L;

    protected class BuildableWeakIndexCache<V extends OWLEntity> extends
            WeakIndexCache<IRI, V> {

        private static final long serialVersionUID = 40000L;

        @Nonnull
        public V cache(@Nonnull IRI s, @Nonnull Buildable v) {
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

    @Nonnull
    private final BuildableWeakIndexCache<OWLClass> classesByURI;
    @Nonnull
    private final BuildableWeakIndexCache<OWLObjectProperty> objectPropertiesByURI;
    @Nonnull
    private final BuildableWeakIndexCache<OWLDataProperty> dataPropertiesByURI;
    @Nonnull
    private final BuildableWeakIndexCache<OWLDatatype> datatypesByURI;
    @Nonnull
    private final BuildableWeakIndexCache<OWLNamedIndividual> individualsByURI;
    @Nonnull
    private final BuildableWeakIndexCache<OWLAnnotationProperty> annotationPropertiesByURI;
    @Nonnull
    transient final private Interner<String> languageTagInterner;

    @Nonnull
    protected final <V extends OWLEntity> BuildableWeakIndexCache<V>
            buildCache() {
        return new BuildableWeakIndexCache<>();
    }

    /**
     * @param useCompression
     *        true if literals should be compressed
     */
    public OWLDataFactoryInternalsImpl(boolean useCompression) {
        super(useCompression);
        classesByURI = buildCache();
        objectPropertiesByURI = buildCache();
        dataPropertiesByURI = buildCache();
        datatypesByURI = buildCache();
        individualsByURI = buildCache();
        annotationPropertiesByURI = buildCache();
        languageTagInterner = Interners.newWeakInterner();
    }

    @SuppressWarnings("unchecked")
    protected enum Buildable {
        OWLCLASS {

            @Nonnull
            @Override
            OWLClass build(@Nonnull IRI iri) {
                return new OWLClassImpl(iri);
            }
        },
        OWLOBJECTPROPERTY {

            @Nonnull
            @Override
            OWLObjectProperty build(@Nonnull IRI iri) {
                return new OWLObjectPropertyImpl(iri);
            }
        },
        OWLDATAPROPERTY {

            @Nonnull
            @Override
            OWLDataProperty build(@Nonnull IRI iri) {
                return new OWLDataPropertyImpl(iri);
            }
        },
        OWLNAMEDINDIVIDUAL {

            @Nonnull
            @Override
            OWLNamedIndividual build(@Nonnull IRI iri) {
                return new OWLNamedIndividualImpl(iri);
            }
        },
        OWLDATATYPE {

            @Nonnull
            @Override
            OWLDatatype build(@Nonnull IRI iri) {
                return new OWLDatatypeImpl(iri);
            }
        },
        OWLANNOTATIONPROPERTY {

            @Nonnull
            @Override
            OWLAnnotationProperty build(@Nonnull IRI iri) {
                return new OWLAnnotationPropertyImpl(iri);
            }
        };

        @Nonnull
        abstract <K extends OWLEntity> K build(@Nonnull IRI iri);
    }

    @Nonnull
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
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return objectPropertiesByURI.cache(iri, Buildable.OWLOBJECTPROPERTY);
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return dataPropertiesByURI.cache(iri, Buildable.OWLDATAPROPERTY);
    }

    @Nonnull
    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return individualsByURI.cache(iri, Buildable.OWLNAMEDINDIVIDUAL);
    }

    @Nonnull
    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        return datatypesByURI.cache(iri, Buildable.OWLDATATYPE);
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return annotationPropertiesByURI.cache(iri,
                Buildable.OWLANNOTATIONPROPERTY);
    }

    /*
       Use a guava weak String interner for language tags.
     */
    @Override
    public OWLLiteral getOWLLiteral(String literal, @Nullable String lang) {
        if (lang == null) {
            lang = "";
        }
        lang = languageTagInterner.intern(lang.trim().toLowerCase());
        return super.getOWLLiteral(literal, lang);
    }
}
