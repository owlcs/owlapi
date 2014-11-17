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
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public enum DublinCoreVocabulary
        implements
        HasShortForm,
        HasIRI,
        HasPrefixedName {
//@formatter:off
    /** http://purl.org/dc/elements/1.1/contributor. */ CONTRIBUTOR("contributor"),
    /** http://purl.org/dc/elements/1.1/coverage. */    COVERAGE   ("coverage"   ),
    /** http://purl.org/dc/elements/1.1/creator. */     CREATOR    ("creator"    ),
    /** http://purl.org/dc/elements/1.1/date. */        DATE       ("date"       ),
    /** http://purl.org/dc/elements/1.1/description. */ DESCRIPTION("description"),
    /** http://purl.org/dc/elements/1.1/format. */      FORMAT     ("format"     ),
    /** http://purl.org/dc/elements/1.1/identifier. */  IDENTIFIER ("identifier" ),
    /** http://purl.org/dc/elements/1.1/language. */    LANGUAGE   ("language"   ),
    /** http://purl.org/dc/elements/1.1/publisher. */   PUBLISHER  ("publisher"  ),
    /** http://purl.org/dc/elements/1.1/relation. */    RELATION   ("relation"   ),
    /** http://purl.org/dc/elements/1.1/rights. */      RIGHTS     ("rights"     ),
    /** http://purl.org/dc/elements/1.1/source. */      SOURCE     ("source"     ),
    /** http://purl.org/dc/elements/1.1/subject. */     SUBJECT    ("subject"    ),
    /** http://purl.org/dc/elements/1.1/title. */       TITLE      ("title"      ),
    /** http://purl.org/dc/elements/1.1/type. */        TYPE       ("type"       );
//@formatter:on
    @Nonnull
    private final String shortName;
    @Nonnull
    private final String qname;
    @Nonnull
    private final IRI iri;
    /** Dublin Core name space. */
    @Nonnull
    public static final String NAME_SPACE = "http://purl.org/dc/elements/1.1/";

    DublinCoreVocabulary(@Nonnull String name) {
        shortName = name;
        qname = Namespaces.DC.getPrefixName() + ':' + name;
        iri = IRI.create(NAME_SPACE, name);
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    @Override
    public String getPrefixedName() {
        return qname;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /** All IRIs. */
    public static final Set<IRI> ALL_URIS = asSet(Stream.of(values()).map(
            x -> x.getIRI()));

    @Override
    public String toString() {
        return iri.toString();
    }
}
