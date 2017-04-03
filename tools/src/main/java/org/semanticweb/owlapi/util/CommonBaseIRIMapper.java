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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;

/**
 * An ontology IRI mapper that can be used to map ontology IRIs to ontology document IRIs which
 * share the same base.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class CommonBaseIRIMapper implements OWLOntologyIRIMapper {

    private final IRI base;
    private final Map<IRI, IRI> iriMap = new HashMap<>();

    /**
     * Creates a mapper, which maps ontology URIs to URIs which share the specified base.
     *
     * @param base the base IRI
     */
    public CommonBaseIRIMapper(IRI base) {
        this.base = checkNotNull(base, "base cannot be null");
    }

    /**
     * Adds a mapping from an ontology IRI to an ontology document IRI which has a base of this
     * mapper and a specified local name - in other words the document IRI will be determined by
     * resolving the local name against the URI base of this mapper.
     *
     * @param ontologyIRI the ontology IRI
     * @param localName the document IRI
     */
    public void addMapping(IRI ontologyIRI, String localName) {
        checkNotNull(localName, "localName cannot be null");
        checkNotNull(ontologyIRI, "ontologyIRI cannot be null");
        IRI documentIRI = base.resolve(localName);
        iriMap.put(ontologyIRI, documentIRI);
    }

    @Override
    @Nullable
    public IRI getDocumentIRI(IRI ontologyIRI) {
        checkNotNull(ontologyIRI, "ontologyIRI cannot be null");
        return iriMap.get(ontologyIRI);
    }
}
