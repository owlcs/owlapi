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
package org.semanticweb.owlapi;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import javax.inject.Provider;

import org.semanticweb.owlapi.annotations.OwlapiModule;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * owlapi module for dynamic loading - uses ServiceLoader to add extra bindings
 * for OWLParser, OWLOntologyStorer, OWLOntologyFactory, OWLOntologyIRIMapper
 */
@OwlapiModule
public class OWLAPIServiceLoaderModule extends AbstractModule {

    @Override
    protected void configure() {
        loadInstancesFromServiceLoader(OWLParser.class);
        loadInstancesFromServiceLoader(OWLOntologyStorer.class);
        loadInstancesFromServiceLoader(OWLOntologyFactory.class);
        loadInstancesFromServiceLoader(OWLOntologyIRIMapper.class);
        loadFactories(OWLOntologyFormatFactory.class, OWLOntologyFormat.class);
        loadFactories(OWLParserFactory.class, OWLParser.class);
        loadFactories(OWLOntologyStorerFactory.class, OWLOntologyStorer.class);
    }

    protected <T> void loadInstancesFromServiceLoader(Class<T> type) {
        try {
            Multibinder<T> binder = Multibinder.newSetBinder(binder(), type);
            for (T o : ServiceLoader.load(type)) {
                binder.addBinding().toInstance(o);
            }
        } catch (ServiceConfigurationError e) {
            e.printStackTrace(System.out);
            throw new OWLRuntimeException("Injection failed for " + type, e);
        }
    }

    protected <T, F extends Provider<T>> void loadFactories(Class<F> factory,
            Class<T> type) {
        try {
            Multibinder<F> factoryBinder = Multibinder.newSetBinder(binder(),
                    factory);
            Multibinder<T> binder = Multibinder.newSetBinder(binder(), type);
            for (F o : ServiceLoader.load(factory)) {
                factoryBinder.addBinding().toInstance(o);
                binder.addBinding().toInstance(o.get());
            }
        } catch (ServiceConfigurationError e) {
            e.printStackTrace(System.out);
            throw new OWLRuntimeException("Injection failed for factory: "
                    + factory + " type: " + type, e);
        }
    }
}
