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

package org.semanticweb.owlapi.util;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Feb-2007<br><br>
 * <p/>
 * An ontology IRI mapper that can be used to map ontology IRIs
 * to ontology document IRIs which share the same base.
 */
public class CommonBaseIRIMapper implements OWLOntologyIRIMapper {

    private IRI base;

    private Map<IRI, IRI> iriMap;


    /**
     * Creates a mapper, which maps ontology URIs to URIs which share
     * the specified base
     */
    public CommonBaseIRIMapper(IRI base) {
        this.base = base;
        iriMap = new HashMap<IRI, IRI>();
    }


    /**
     * Adds a mapping from an ontology IRI to an ontology document IRI which
     * has a base of this mapper and a specified local name - in
     * other words the document IRI will be determined by resolving
     * the local name against the URI base of this mapper.
     */
    public void addMapping(IRI ontologyIRI, String localName) {
        IRI documentIRI = base.resolve(localName);
        iriMap.put(ontologyIRI, documentIRI);
    }


    /**
     * Given an ontology IRI, this method maps the ontology IRI
     * to a document IRI that points to some concrete representation
     * of the ontology.
     * @param ontologyIRI The ontology IRI to be mapped.
     * @return The document IRI of the ontology, or <code>null</code>
     *         if the mapper doesn't have mapping for the specified ontology IRI.
     */
    public IRI getDocumentIRI(IRI ontologyIRI) {
        return iriMap.get(ontologyIRI);
    }
}
