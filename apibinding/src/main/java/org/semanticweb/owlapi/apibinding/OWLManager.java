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
package org.semanticweb.owlapi.apibinding;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import org.semanticweb.owlapi.OWLAPIParsersModule;
import org.semanticweb.owlapi.OWLAPIServiceLoaderModule;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactory;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

import com.google.inject.Guice;
import com.google.inject.Injector;

import uk.ac.manchester.cs.owl.owlapi.OWLAPIImplModule;
import uk.ac.manchester.cs.owl.owlapi.concurrent.Concurrency;

/**
 * Provides a point of convenience for creating an {@code OWLOntologyManager}
 * with commonly required features (such as an RDF parser for example).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLManager implements OWLOntologyManagerFactory {

    @Override
    public OWLOntologyManager get() {
        return createOWLOntologyManager();
    }

    /**
     * Creates an OWL ontology manager that is configured with standard parsers,
     * storeres etc.
     * 
     * @return The new manager.
     */
    public static OWLOntologyManager createOWLOntologyManager() {
        return instatiateOWLOntologyManager(Concurrency.NON_CONCURRENT);
    }

    /**
     * Creates an OWL ontology manager that is configured with the standard
     * parsers and storers and provides locking for concurrent access.
     * 
     * @return The new manager.
     */
    public static OWLOntologyManager createConcurrentOWLOntologyManager() {
        return instatiateOWLOntologyManager(Concurrency.CONCURRENT);
    }

    /**
     * Gets a global data factory that can be used to create OWL API objects.
     * 
     * @return An OWLDataFactory that can be used for creating OWL API objects.
     */
    public static OWLDataFactory getOWLDataFactory() {
        return verifyNotNull(createInjector(Concurrency.NON_CONCURRENT).getInstance(OWLDataFactory.class));
    }

    /**
     * @return an initialized manchester syntax parser for parsing strings
     */
    public static ManchesterOWLSyntaxParser createManchesterParser() {
        return createInjector(Concurrency.NON_CONCURRENT).getInstance(ManchesterOWLSyntaxParser.class);
    }

    private static Injector createInjector(Concurrency concurrency) {
        return Guice.createInjector(new OWLAPIImplModule(concurrency), new OWLAPIParsersModule(),
                new OWLAPIServiceLoaderModule());
    }

    private static OWLOntologyManager instatiateOWLOntologyManager(Concurrency concurrency) {
        Injector injector = createInjector(concurrency);
        OWLOntologyManager instance = injector.getInstance(OWLOntologyManager.class);
        injector.injectMembers(instance);
        return verifyNotNull(instance);
    }
}
