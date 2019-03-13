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
package org.semanticweb.owlapi6.documents;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.annotations.Renders;
import org.semanticweb.owlapi6.io.OWLObjectRenderer;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.parameters.ConfigurationOptions;
import org.semanticweb.owlapi6.utilities.Injector;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * A utility class which can be used by implementations to provide a toString
 * rendering of OWL API objects. The renderer can be set through the
 * ConfigurtionOptions class, with property file or system property. No local
 * override is possible, because ToStringRenderer has no access to ontology or
 * ontology manager objects. Be careful of changing the value of the options in
 * a multithreaded application, as this will cause the renderer to change
 * behaviour. For a more precise rendering use the syntax specific methods in
 * OWLObject, where the desired forma is specified as input.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public final class ToStringRenderer {

    private static Injector injector = new Injector();

    private ToStringRenderer() {}

    static <Q, T> LoadingCache<Q, T> build(CacheLoader<Q, T> c) {
        return Caffeine.newBuilder()
            .maximumSize(ConfigurationOptions.CACHE_SIZE.getValue(Integer.class, Collections.emptyMap()).longValue()).build(c);
    }

    static Supplier<OWLObjectRenderer> renderer(Class<OWLObjectRenderer> className) {
        return () -> supply(className);
    }

    static OWLObjectRenderer supply(Class<OWLObjectRenderer> className) {
        try {
            return className.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
            throw new OWLRuntimeException("Custom renderer unavailable: " + className, e);
        }
    }

    @SuppressWarnings("unchecked")
    static Class<OWLObjectRenderer> value() {
        String value = ConfigurationOptions.TO_STRING_RENDERER.getValue(String.class, null);
        try {
            return (Class<OWLObjectRenderer>) Class.forName(value);
        } catch (ClassNotFoundException e) {
            throw new OWLRuntimeException("Custom renderer unavailable: " + value, e);
        }
    }

    private static final LoadingCache<Class<OWLObjectRenderer>, Supplier<OWLObjectRenderer>> renderers = build(
        ToStringRenderer::renderer);

    /**
     * @return the singleton instance
     */
    public static OWLObjectRenderer getInstance() {
        return verifyNotNull(renderers.get(value())).get();
    }

    private static final Map<Class<? extends OWLDocumentFormat>, Class<? extends OWLObjectRenderer>> formatToRenderer = initMap();

    /**
     * @param object
     *        the object to render
     * @return the rendering for the object
     */
    public static String getRendering(OWLObject object) {
        return getInstance().render(checkNotNull(object, "object cannot be null"));
    }

    private static Map<Class<? extends OWLDocumentFormat>, Class<? extends OWLObjectRenderer>> initMap() {
        Map<Class<? extends OWLDocumentFormat>, Class<? extends OWLObjectRenderer>> map = new ConcurrentHashMap<>();
        Consumer<OWLObjectRenderer> r = c -> {
            Renders annotation = c.getClass().getAnnotation(Renders.class);
            if (annotation != null) {
                map.put(annotation.value(), c.getClass());
            }
        };
        injector.getImplementations(OWLObjectRenderer.class).forEach(r);
        return map;
    }

    /**
     * @param format
     *        format for output
     * @param pm
     *        prefix manager
     * @return renderer prepared for output
     */
    public static OWLObjectRenderer getInstance(OWLDocumentFormat format, @Nullable PrefixManager pm) {
        @SuppressWarnings("unchecked") Class<OWLObjectRenderer> class1 = (Class<OWLObjectRenderer>) formatToRenderer
            .get(format.getClass());
        if (class1 == null) {
            throw new OWLRuntimeException(
                "Format " + format + " does not have an OWLObjectRenderer implementation available.");
        }
        Supplier<OWLObjectRenderer> supplier = renderers.get(class1);
        if (supplier == null) {
            throw new OWLRuntimeException(
                "Format " + format + " does not have an OWLObjectRenderer supplier implementation available.");
        }
        OWLObjectRenderer r = supplier.get();
        if (pm != null) {
            r.setPrefixManager(pm);
        }
        return r;
    }

    /**
     * @param format
     *        format for output
     * @return renderer prepared for output
     */
    public static OWLObjectRenderer getInstance(OWLDocumentFormat format) {
        return getInstance(format, null);
    }
}
