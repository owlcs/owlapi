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

package org.semanticweb.owlapi.apibinding;

import java.util.List;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactoryRegistry;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 * <p>
 * Provides a point of convenience for creating an <code>OWLOntologyManager</code>
 * with commonly required features (such as an RDF parser for example).
 */
@MetaInfServices(OWLOntologyManagerFactory.class)
public class OWLManager implements OWLOntologyManagerFactory {

    private static final Logger logger = LoggerFactory.getLogger(OWLManager.class);

    @Override
    public OWLOntologyManager buildOWLOntologyManager() {

    	return createOWLOntologyManager(getFactory());
    }

    @Override
    public OWLOntologyManager buildOWLOntologyManager(OWLDataFactory f) {

    	return createOWLOntologyManager(f);
    }

    @Override
    public OWLOntologyManager buildOWLOntologyManager(OWLDataFactory f,
            OWLOntologyStorerFactoryRegistry storerRegistry, OWLParserFactoryRegistry parserRegistry)
    {
        return createOWLOntologyManager(f, storerRegistry, parserRegistry);
    }
    
    @Override
    public OWLDataFactory getFactory() {

    	return getOWLDataFactory();
    }

    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storeres etc.
     *
     * @return The new manager.
     */
    public static OWLOntologyManager createOWLOntologyManager() {
        return createOWLOntologyManager(getOWLDataFactory());
    }


    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storers etc.
     *
     * @param dataFactory The data factory that the manager should have a reference to.
     * @return The manager.
     */
    public static OWLOntologyManager createOWLOntologyManager(OWLDataFactory dataFactory) {
        return createOWLOntologyManager(dataFactory, OWLOntologyStorerFactoryRegistry.getInstance(), OWLParserFactoryRegistry.getInstance());
    }
    
    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storers etc.
     *
     * @param dataFactory The data factory that the manager should have a reference to.
     * @param storerRegistry The OWLOntologyStorer registry to use to define valid ontology storing options for the returned OWLOntologyManager
     * @param parserRegistry The OWLParser registry to use to define valid ontology storing options for the returned OWLOntologyManager
     * @return The manager.
     */
    public static OWLOntologyManager createOWLOntologyManager(OWLDataFactory dataFactory, OWLOntologyStorerFactoryRegistry storerRegistry, OWLParserFactoryRegistry parserRegistry) {
        // Create the ontology manager and add ontology factories, mappers and storers
        OWLOntologyManager ontologyManager = new OWLOntologyManagerImpl(dataFactory);
        List<OWLOntologyStorerFactory> allFactories = storerRegistry.getStorerFactories();
        if(logger.isDebugEnabled()) {
            logger.debug("Found {} ontology storer factories", allFactories.size());
        }
        addOntologyStorers(ontologyManager, allFactories);

        ontologyManager.addIRIMapper(new NonMappingOntologyIRIMapper());

        ontologyManager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        ontologyManager.addOntologyFactory(new ParsableOWLOntologyFactory(parserRegistry));

        return ontologyManager;
    }
    
    private static void addOntologyStorers(OWLOntologyManager ontologyManager, List<OWLOntologyStorerFactory> storerFactories) {
        if(storerFactories != null) {
            for(OWLOntologyStorerFactory nextFactory : storerFactories) {
                OWLOntologyStorer createdStorer = nextFactory.createStorer();
                if(logger.isDebugEnabled()) {
                    logger.debug("Created a new ontology storer {}", createdStorer.toString());
                }
                ontologyManager.addOntologyStorer(createdStorer);
            }
        }
    }
    
    
    /**
     * Gets a global data factory that can be used to create OWL API objects.
     * @return An OWLDataFactory  that can be used for creating OWL API objects.
     */
    public static OWLDataFactory getOWLDataFactory() {
        return new OWLDataFactoryImpl();
    }

}
