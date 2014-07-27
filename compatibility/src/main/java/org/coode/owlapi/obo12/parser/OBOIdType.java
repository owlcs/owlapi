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
 * Copyright 2011, The University of Manchester
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
package org.coode.owlapi.obo12.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 19/04/2012
 * <p>
 * Describes the types of OBO IDs that can be found in OBO Files. Taken from
 * Section 2.5 of the OBO Syntax and Semantics Specification.
 * </p>
 */
@SuppressWarnings("all")
enum OBOIdType {
    /**
     * Any string with an http: or https: prefix.
     */
    URL_AS_ID(Pattern.compile("(http:|https:)[^\\s]*"), new OBOIIdTranslator() {

        @Override
        public IRI getIRIFromOBOId(OWLOntologyID ontologyID,
                IDSpaceManager idSpaceManager, String id) {
            return IRI.create(id);
        }
    }),
    /**
     * Any unprefixed ID. Does not contain a colon character. The spec implies
     * the empty string matches this ID.
     */
    UNPREFIXED_ID(Pattern.compile("[^\\s:]*"), new OBOIIdTranslator() {

        @Override
        public IRI getIRIFromOBOId(OWLOntologyID ontologyID,
                IDSpaceManager idSpaceManager, String id) {
            StringBuilder sb = new StringBuilder();
            if (!ontologyID.isAnonymous()) {
                sb.append(ontologyID.getOntologyIRI());
            } else {
                sb.append("anonymous");
            }
            sb.append("#");
            sb.append(id);
            return IRI.create(sb.toString());
        }
    }),
    /**
     * Must contain a colon character in the ID. The idspace must only consist
     * of Alpha-Chars and possibly an underscore. The local id must only consist
     * of digits (possibly none).
     */
    CANONICAL_PREFIXED_ID(
            Pattern.compile("([A-Za-z][A-Za-z_]*):([0-9]*)"),
            new OBOIIdTranslator() {

                @Override
                public IRI getIRIFromOBOId(OWLOntologyID ontologyID,
                        IDSpaceManager idSpaceManager, String id) {
                    Matcher matcher = CANONICAL_PREFIXED_ID.getPattern()
                            .matcher(id);
                    matcher.matches();
                    String idspace = matcher.group(1);
                    String localid = matcher.group(2);
                    String iriPrefix = idSpaceManager.getIRIPrefix(idspace);
                    StringBuilder sb = new StringBuilder();
                    sb.append(iriPrefix);
                    sb.append(idspace);
                    sb.append("_");
                    sb.append(localid);
                    return IRI.create(sb.toString());
                }
            }),
    /**
     * Must contain a colon character somewhere in the ID. Any kind of prefix
     * plus a local Id. The prefix doesn't contain a colon character.
     */
    NON_CANONICAL_PREFIXED_ID(
            Pattern.compile("([^\\s:]*):([^\\s]*)"),
            new OBOIIdTranslator() {

                @Override
                public IRI getIRIFromOBOId(OWLOntologyID ontologyID,
                        IDSpaceManager idSpaceManager, String id) {
                    Matcher matcher = NON_CANONICAL_PREFIXED_ID.getPattern()
                            .matcher(id);
                    matcher.matches();
                    String idspace = matcher.group(1);
                    String localid = matcher.group(2);
                    String iriPrefix = idSpaceManager.getIRIPrefix(idspace);
                    StringBuilder sb = new StringBuilder();
                    sb.append(iriPrefix);
                    sb.append(idspace);
                    sb.append("#_");
                    sb.append(localid);
                    return IRI.create(sb.toString());
                }
            });

    private Pattern pattern;
    private OBOIIdTranslator translator;

    private OBOIdType(Pattern pattern, OBOIIdTranslator translator) {
        this.pattern = pattern;
        this.translator = translator;
    }

    /** @return the pattern */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * @param ontologyID
     * @param idSpaceManager
     * @param oboId
     * @return the translated iri
     */
    @Nonnull
    public IRI getIRIFromOBOId(OWLOntologyID ontologyID,
            IDSpaceManager idSpaceManager, String oboId) {
        return translator.getIRIFromOBOId(ontologyID, idSpaceManager, oboId);
    }

    /**
     * Gets the OBOIdType for a given OBO ID.
     * 
     * @param oboId
     *        The OBO ID. Must not be null.
     * @return The OBOIdType for the specified oboId, or <code>null</code> if
     *         the specified oboId does not conform to any OBO Id type.
     */
    public static OBOIdType getIdType(String oboId) {
        if (oboId == null) {
            throw new NullPointerException("oboId must not be null");
        }
        for (OBOIdType idType : values()) {
            Pattern pattern = idType.getPattern();
            Matcher matcher = pattern.matcher(oboId);
            if (matcher.matches()) {
                return idType;
            }
        }
        return null;
    }

    private static interface OBOIIdTranslator {

        IRI getIRIFromOBOId(OWLOntologyID ontologyID,
                IDSpaceManager idSpaceManager, String id);
    }
}
