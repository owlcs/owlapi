package org.semanticweb.owlapi.registries;

/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, University of Manchester
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2006<br>
 * <br>
 * The {@link OWLOntologyManagerFactory} provides a central point for the
 * registration of storer factories that create storers to store OWL ontologies.
 * The registry is typically used by at least one type of ontology factory for
 * storing ontologies whose concrete representations are to be contained in some
 * kind of document.
 */
public class OWLOntologyManagerFactoryRegistry extends
        AbstractServiceLoader<String, OWLOntologyManagerFactory> {

    private static final OWLOntologyManagerFactoryRegistry instance = new OWLOntologyManagerFactoryRegistry();

    public OWLOntologyManagerFactoryRegistry() {
        super(OWLOntologyManagerFactory.class);
    }

    /**
     * @return A static, pre-initialized, instance of this registry.
     */
    public static OWLOntologyManagerFactoryRegistry getInstance() {
        return instance;
    }

    /**
     * clear all registered manager factories
     */
    public void clearManagerFactories() {
        clear();
    }

    /**
     * @return the list of managers - changes will not be backed by the factory
     */
    public List<OWLOntologyManagerFactory> getOntologyManagerFactories() {
        return new ArrayList<OWLOntologyManagerFactory>(getAll());
    }

    /**
     * @param managerFactory
     *        the manager factory to register
     */
    public void registerOntologyManagerFactory(
            OWLOntologyManagerFactory managerFactory) {
        add(managerFactory);
    }

    /**
     * @param managerFactory
     *        the manager factory to remove
     */
    public void unregisterOntologyManagerFactory(
            OWLOntologyManagerFactory managerFactory) {
        remove(managerFactory);
    }

    /**
     * Returns the class name of the service as its unique key, to prevent
     * multiple instances of the one service being present in the registry.
     */
    @Override
    public String getKey(OWLOntologyManagerFactory service) {
        return service.getClass().getName();
    }

    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storers etc. NOTE: This uses the first registered
     * OWLOntologyManagerFactory.
     * 
     * @return The new manager.
     */
    public static OWLOntologyManager createOWLOntologyManager() {
        return createOWLOntologyManager(getOWLDataFactory());
    }

    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storers etc. NOTE: This uses the first registered
     * OWLOntologyManagerFactory.
     * 
     * @param dataFactory
     *        The data factory that the manager should have a reference to.
     * @return The manager.
     */
    public static OWLOntologyManager createOWLOntologyManager(
            OWLDataFactory dataFactory) {
        return createOWLOntologyManager(dataFactory,
                OWLOntologyStorerFactoryRegistry.getInstance(),
                OWLParserFactoryRegistry.getInstance());
    }

    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storers etc. NOTE: This uses the first registered
     * OWLOntologyManagerFactory.
     * 
     * @param dataFactory
     *        The data factory that the manager should have a reference to.
     * @param storerRegistry
     *        The OWLOntologyStorer registry to use to define valid ontology
     *        storing options for the returned OWLOntologyManager
     * @param parserRegistry
     *        The OWLParser registry to use to define valid ontology storing
     *        options for the returned OWLOntologyManager
     * @return The manager.
     */
    public static OWLOntologyManager createOWLOntologyManager(
            OWLDataFactory dataFactory,
            OWLOntologyStorerFactoryRegistry storerRegistry,
            OWLParserFactoryRegistry parserRegistry) {
        if (instance.getOntologyManagerFactories().isEmpty()) {
            throw new IllegalStateException(
                    "Could not find an OWLOntologyManagerFactory implementations");
        }
        return instance
                .getOntologyManagerFactories()
                .iterator()
                .next()
                .buildOWLOntologyManager(dataFactory, storerRegistry,
                        parserRegistry);
    }

    /**
     * Gets a global data factory that can be used to create OWL API objects.
     * NOTE: This uses the first registered OWLOntologyManagerFactory.
     * 
     * @return An OWLDataFactory that can be used for creating OWL API objects.
     */
    public static OWLDataFactory getOWLDataFactory() {
        if (instance.getOntologyManagerFactories().isEmpty()) {
            throw new IllegalStateException(
                    "Could not find any OWLOntologyManagerFactory implementations");
        }
        return instance.getOntologyManagerFactories().iterator().next()
                .getFactory();
    }
}
