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

package org.semanticweb.owlapi.vocab;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Mar-2007<br><br>
 */
public enum DublinCoreVocabulary {

    CONTRIBUTOR("contributor"),
    COVERAGE("coverage"),
    CREATOR("creator"),
    DATE("date"),
    DESCRIPTION("description"),
    FORMAT("format"),
    IDENTIFIER("identifier"),
    LANGUAGE("language"),
    PUBLISHER("publisher"),
    RELATION("relation"),
    RIGHTS("rights"),
    SOURCE("source"),
    SUBJECT("subject"),
    TITLE("title"),
    TYPE("type");

    private String shortName;

    private String qname;

    private IRI iri;

    public static final String NAME_SPACE = "http://purl.org/dc/elements/1.1/";


    DublinCoreVocabulary(String name) {
        shortName = name;
        qname = "dc:" + name;
        iri = IRI.create(NAME_SPACE + name);
    }

    public String getShortName() {
        return shortName;
    }

    public String getQName() {
        return qname;
    }

    public URI getURI() {
        return iri.toURI();
    }

    public IRI getIRI() {
        return iri;
    }

    public static final Set<IRI> ALL_URIS;


    static {
        ALL_URIS = new HashSet<IRI>();
        for (DublinCoreVocabulary v : DublinCoreVocabulary.values()) {
            ALL_URIS.add(v.getIRI());
        }
    }


    @Override
	public String toString() {
        return iri.toString();
    }
}
