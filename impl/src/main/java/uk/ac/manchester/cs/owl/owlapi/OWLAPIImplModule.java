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

import javax.annotation.Nonnull;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.semanticweb.owlapi.annotations.OwlapiModule;
import org.semanticweb.owlapi.model.*;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import uk.ac.manchester.cs.owl.owlapi.concurrent.*;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@OwlapiModule
public class OWLAPIImplModule extends AbstractModule {

    private final Concurrency concurrency;

    public OWLAPIImplModule(Concurrency concurrency) {
        this.concurrency = concurrency;
    }

    @Override
    protected void configure() {

        if (concurrency == Concurrency.CONCURRENT) {
            bind(ReadWriteLock.class)
                    .to(ReentrantReadWriteLock.class)
                    .asEagerSingleton();
        }
        else {
            bind(ReadWriteLock.class)
                    .to(NoOpReadWriteLock.class)
                    .asEagerSingleton();
        }

        bind(boolean.class)
                .annotatedWith(CompressionEnabled.class)
                .toInstance(false);

        bind(OWLDataFactory.class)
                .to(OWLDataFactoryImpl.class)
                .asEagerSingleton();

        bind(OWLDataFactoryInternals.class)
                .to(OWLDataFactoryInternalsImpl.class);

        bind(OWLOntologyManager.class)
                .to(OWLOntologyManagerImpl.class)
                .asEagerSingleton();

        bind(OWLOntologyManager.class)
                .annotatedWith(NonConcurrentDelegate.class)
                .to(OWLOntologyManagerImpl.class)
                .asEagerSingleton();

        bind(OWLOntologyBuilder.class)
                .to(ConcurrentOWLOntologyBuilder.class);

        bind(OWLOntologyBuilder.class)
                .annotatedWith(NonConcurrentDelegate.class)
                .to(NonConcurrentOWLOntologyBuilder.class);

        install(new FactoryModuleBuilder()
                .implement(OWLOntology.class, OWLOntologyImpl.class)
                .build(OWLOntologyImplementationFactory.class));

        multibind(OWLOntologyFactory.class, OWLOntologyFactoryImpl.class);


    }

    @SafeVarargs
    private final <T> Multibinder<T> multibind(Class<T> type,
            @Nonnull Class<? extends T>... implementations) {
        Multibinder<T> binder = Multibinder.newSetBinder(binder(), type);
        for (Class<? extends T> i : implementations) {
            binder.addBinding().to(i);
        }
        return binder;
    }
}
