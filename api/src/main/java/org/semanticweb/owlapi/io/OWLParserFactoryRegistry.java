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
package org.semanticweb.owlapi.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code OWLParserFactoryRegistry} provides a central point for the
 * registration of parser factories that create parsers to parse OWL ontologies.
 * The registry is typically used by at least one type of ontology factory for
 * loading ontologies whose concrete representations are contained in some kind
 * of document.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 15-Nov-2006
 */
public class OWLParserFactoryRegistry {

    private static final OWLParserFactoryRegistry instance = new OWLParserFactoryRegistry();
    private final List<OWLParserFactory> parserFactories = new ArrayList<OWLParserFactory>(
            10);

    private OWLParserFactoryRegistry() {}

    /** @return the parser factory registry */
    public static OWLParserFactoryRegistry getInstance() {
        return instance;
    }

    /** clear all registered parser factories. */
    public void clearParserFactories() {
        parserFactories.clear();
    }

    /** @return the list of parsers - changes will not be backed by the factory */
    public List<OWLParserFactory> getParserFactories() {
        return Collections.unmodifiableList(parserFactories);
    }

    /**
     * @param parserFactory
     *        the parser factory to register
     */
    public void registerParserFactory(OWLParserFactory parserFactory) {
        parserFactories.add(0, parserFactory);
    }

    /**
     * @param parserFactory
     *        the parser factory to remove
     */
    public void unregisterParserFactory(OWLParserFactory parserFactory) {
        parserFactories.remove(parserFactory);
    }
}
