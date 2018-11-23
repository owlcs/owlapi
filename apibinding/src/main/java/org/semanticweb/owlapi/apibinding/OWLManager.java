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

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactory;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;
import org.semanticweb.owlapi.utilities.Injector;

import uk.ac.manchester.cs.owl.owlapi.CompressionEnabled;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.concurrent.ConcurrentOWLOntologyBuilder;
import uk.ac.manchester.cs.owl.owlapi.concurrent.NoOpReadWriteLock;
import uk.ac.manchester.cs.owl.owlapi.concurrent.NonConcurrentDelegate;
import uk.ac.manchester.cs.owl.owlapi.concurrent.NonConcurrentOWLOntologyBuilder;

/**
 * Provides a point of convenience for creating an {@code OWLOntologyManager} with commonly required
 * features (such as an RDF parser for example).
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLManager implements OWLOntologyManagerFactory {
    enum InjectorConstants {
        @CompressionEnabled
        COMPRESSION_ENABLED(Boolean.class, () -> Boolean.FALSE),
        //
        CONCURRENTBUILDER(OWLOntologyBuilder.class, ConcurrentOWLOntologyBuilder.class),
        //
        @NonConcurrentDelegate
        NONCONCURRENTBUILDER(OWLOntologyBuilder.class, NonConcurrentOWLOntologyBuilder.class),
        //
        CONFIG(OntologyConfigurator.class, OntologyConfigurator::new),
        // XXX ontology manager and ontology builder have independent locks in their constructors,
        // but they should actually share the one lock. Ontologies moving managers should rely on
        // the manager for locks. Now they rely on the fact that the locks are injector level
        // singletons.
        REENTRANT(ReadWriteLock.class, () -> new ReentrantReadWriteLock()),
        //
        NOOP(ReadWriteLock.class, new NoOpReadWriteLock());
        private Class<?> c;
        private Supplier<?> s;
        private Class<?> type;
        private Object instance;

        InjectorConstants(Class<?> c, Supplier<?> s) {
            this.c = c;
            this.s = s;
        }

        InjectorConstants(Class<?> c, Object s) {
            this.c = c;
            instance = s;
        }

        InjectorConstants(Class<?> c, Class<?> t) {
            this.c = c;
            type = t;
        }

        Annotation[] anns() {
            try {
                return InjectorConstants.class.getField(name()).getAnnotations();
            } catch (NoSuchFieldException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }

        Injector init(Injector i) {
            if (instance != null) {
                i.bindToOne(instance, c, anns());
            } else if (s != null) {
                i.bindToOne(s, c, anns());
            } else {
                i.bind(type, c, anns());
            }
            return i;
        }
    }

    private static final Injector concurrentInjector =
        InjectorConstants.REENTRANT.init(configure(new Injector()));
    private static final Injector normalInjector =
        InjectorConstants.NOOP.init(configure(new Injector()));

    private static Injector configure(Injector i) {
        Arrays.stream(InjectorConstants.values()).forEach(f -> f.init(i));
        return i;
    }

    /**
     * Creates an OWL ontology manager that is configured with standard parsers, storeres etc.
     *
     * @return The new manager.
     */
    public static OWLOntologyManager createOWLOntologyManager() {
        return normalInjector.inject(normalInjector.getImplementation(OWLOntologyManager.class));
    }

    /**
     * Creates an OWL ontology manager that is configured with the standard parsers and storers and
     * provides locking for concurrent access.
     *
     * @return The new manager.
     */
    public static OWLOntologyManager createConcurrentOWLOntologyManager() {
        return concurrentInjector
            .inject(concurrentInjector.getImplementation(OWLOntologyManager.class));
    }

    /**
     * Gets a global data factory that can be used to create OWL API objects.
     *
     * @return An OWLDataFactory that can be used for creating OWL API objects.
     */
    public static OWLDataFactory getOWLDataFactory() {
        return new OWLDataFactoryImpl();
    }

    /**
     * @return an initialized manchester syntax parser for parsing strings
     */
    public static ManchesterOWLSyntaxParser createManchesterParser() {
        return normalInjector.getImplementation(ManchesterOWLSyntaxParser.class);
    }

    @Override
    public OWLOntologyManager get() {
        return createOWLOntologyManager();
    }
}
