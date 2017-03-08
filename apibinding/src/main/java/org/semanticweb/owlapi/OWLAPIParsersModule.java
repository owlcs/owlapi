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

import javax.annotation.ParametersAreNonnullByDefault;

import org.semanticweb.owlapi.annotations.OwlapiModule;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * OWLAPI module. Bindings can be overridden by subclassing this class, to allow to replace part of
 * the configuration without having to rewrite all of it.
 */
@ParametersAreNonnullByDefault
@OwlapiModule
public class OWLAPIParsersModule extends AbstractModule {

    @Override
    protected void configure() {
        // nothing to configure here
    }

    /**
     * @param df data factory for parser
     * @param provider config provider for parser
     * @return implementation of manchester parser for parsing strings
     */
    @Provides
    public ManchesterOWLSyntaxParser provideManchesterSyntaxParser(OWLDataFactory df,
        OWLOntologyManager provider) {
        return new ManchesterOWLSyntaxParserImpl(provider.getOntologyConfigurator(), df);
    }
}
