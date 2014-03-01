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

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 09-Mar-2007
 */
public enum DublinCoreVocabulary
        implements
        HasShortForm,
        HasIRI,
        HasPrefixedName {
    //@formatter:off
    /** http://purl.org/dc/elements/1.1/contributor */ CONTRIBUTOR("contributor"),
    /** http://purl.org/dc/elements/1.1/coverage */    COVERAGE   ("coverage"   ),
    /** http://purl.org/dc/elements/1.1/creator */     CREATOR    ("creator"    ),
    /** http://purl.org/dc/elements/1.1/date */        DATE       ("date"       ),
    /** http://purl.org/dc/elements/1.1/description */ DESCRIPTION("description"),
    /** http://purl.org/dc/elements/1.1/format */      FORMAT     ("format"     ),
    /** http://purl.org/dc/elements/1.1/identifier */  IDENTIFIER ("identifier" ),
    /** http://purl.org/dc/elements/1.1/language */    LANGUAGE   ("language"   ),
    /** http://purl.org/dc/elements/1.1/publisher */   PUBLISHER  ("publisher"  ),
    /** http://purl.org/dc/elements/1.1/relation */    RELATION   ("relation"   ),
    /** http://purl.org/dc/elements/1.1/rights */      RIGHTS     ("rights"     ),
    /** http://purl.org/dc/elements/1.1/source */      SOURCE     ("source"     ),
    /** http://purl.org/dc/elements/1.1/subject */     SUBJECT    ("subject"    ),
    /** http://purl.org/dc/elements/1.1/title */       TITLE      ("title"      ),
    /** http://purl.org/dc/elements/1.1/type */        TYPE       ("type"       );
    //@formatter:on
    private final String shortName;
    private final String qname;
    private final IRI iri;
    /**
     * Dublin Core name space
     * 
     * @deprecated Use {@link org.semanticweb.owlapi.vocab.Namespaces#DC}
     */
    @Deprecated
    public static final String NAME_SPACE = "http://purl.org/dc/elements/1.1/";

    DublinCoreVocabulary(String name) {
        shortName = name;
        qname = Namespaces.DC.getPrefixName() + ":" + name;
        iri = IRI.create(NAME_SPACE, name);
    }

    /**
     * @return IRI fragment
     * @deprecated Use {@link #getShortForm()}
     */
    @Deprecated
    public String getShortName() {
        return shortName;
    }

    /**
     * Gets the short form for this vocabulary element. Short forms are the
     * local name e.g. "contributor" for {@link #CONTRIBUTOR} etc.
     * 
     * @return The short form. Not {@code null}.
     */
    @Override
    public String getShortForm() {
        return shortName;
    }

    /**
     * @return qname
     * @deprecated Use {@link #getPrefixedName()}.
     */
    @Deprecated
    public String getQName() {
        return qname;
    }

    /**
     * @return IRI as URI.
     * @deprecated use getIRI()
     */
    @Deprecated
    public URI getURI() {
        return iri.toURI();
    }

    /** @return the iri for the entity */
    @Override
    public IRI getIRI() {
        return iri;
    }

    /** all IRIs */
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

    @Override
    public String getPrefixedName() {
        return qname;
    }
}
