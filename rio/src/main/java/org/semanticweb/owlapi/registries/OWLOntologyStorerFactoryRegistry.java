package org.semanticweb.owlapi.registries;

/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2006<br>
 * <br>
 * The {@link OWLOntologyStorerFactoryRegistry} provides a central point for the
 * registration of storer factories that create storers to store OWL ontologies.
 * The registry is typically used by at least one type of ontology factory for
 * storing ontologies whose concrete representations are to be contained in some
 * kind of document.
 */
public class OWLOntologyStorerFactoryRegistry
        extends
        AbstractServiceLoader<OWLOntologyFormatFactory, OWLOntologyStorerFactory> {

    private static final OWLOntologyStorerFactoryRegistry instance = new OWLOntologyStorerFactoryRegistry();

    public OWLOntologyStorerFactoryRegistry() {
        super(OWLOntologyStorerFactory.class);
    }

    /** @return the storer factory registry */
    public static OWLOntologyStorerFactoryRegistry getInstance() {
        return instance;
    }

    /**
     * clear all registered storer factories
     */
    public void clearStorerFactories() {
        clear();
    }

    /**
     * @return The list of storers - changes will not be backed by the factory
     */
    public List<OWLOntologyStorerFactory> getStorerFactories() {
        return new ArrayList<OWLOntologyStorerFactory>(getAll());
    }

    /**
     * Return an OWLOntologyStorerFactory matching the given OWLOntologyFormat.
     * 
     * @param key
     *        The format to find a storer for.
     * @return The first storer found matching this format, or null if none were
     *         found.
     */
    public OWLOntologyStorerFactory getStorerFactory(
            OWLOntologyFormatFactory key) {
        Collection<OWLOntologyStorerFactory> collection = get(key);
        if (collection.isEmpty()) {
            return null;
        } else {
            return collection.iterator().next();
        }
    }

    /**
     * @param storerFactory
     *        The ontology storer factory to register
     */
    public void registerStorerFactory(OWLOntologyStorerFactory storerFactory) {
        add(storerFactory);
    }

    /**
     * @param storerFactory
     *        The ontology storer factory to remove
     */
    public void unregisterStorerFactory(OWLOntologyStorerFactory storerFactory) {
        remove(storerFactory);
    }

    @Override
    public OWLOntologyFormatFactory getKey(OWLOntologyStorerFactory service) {
        return service.getFormatFactory();
    }
}
