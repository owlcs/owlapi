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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/04/2012
 * <p>
 * Maps between id prefixes and IRI prefixes. By default an IRI prefix is the value defined by
 * {@link OBOPrefix#getPrefix()}, but this can be overridden using an idspace tag in the ontology
 * header.
 * </p>
 * <p>
 * Note that the terminology used here, i.e. "id prefix" and "IRI prefix" is OBO terminology and is
 * not related to the OWL notion of prefix name and prefix. "id prefix" is the substring before the
 * colon in an OBO id. IRI prefix is the prefix of an IRI that is the result of translating an OBO
 * id to an IRI.
 * </p>
 */
class IDSpaceManager {

    private final Map<String, String> idPrefix2IRIPrefixMap = new HashMap<>();

    /**
     * 
     */
    public IDSpaceManager() {}

    /**
     * Creates an IDSpaceManager and copying the id prefix to IRI prefix mappings contained in some
     * other IDSpaceManager.
     * 
     * @param idSpaceManager The other IDSpaceManager from where id prefix to IRI prefix mappings
     *        will be copied. Not null.
     * @throws NullPointerException if idSpaceManager is null.
     */
    public IDSpaceManager(IDSpaceManager idSpaceManager) {
        checkNotNull(idSpaceManager, "idSpaceManager must not be null");
        idPrefix2IRIPrefixMap.putAll(idSpaceManager.idPrefix2IRIPrefixMap);
    }

    /**
     * Gets the default IRI prefix (which is returned by the {@link #getIRIPrefix(String)} for
     * unregistered id prefixes.
     * 
     * @return The default prefix. This is actually defined by {@link OBOPrefix#OBO}.
     */
    public String getDefaultIRIPrefix() {
        return OBOPrefix.OBO.getPrefix();
    }

    /**
     * Gets an IRI prefix from an id prefix.
     * 
     * @param idPrefix The id prefix. May be null.
     * @return The IRI prefix for the given id prefix. Not null. If the specified id prefix is not
     *         registered/set with this manager, or it is null, then the default prefix will be
     *         returned, which is defined by {@link OBOPrefix#OBO}.
     */
    public String getIRIPrefix(String idPrefix) {
        String iriPrefix = idPrefix2IRIPrefixMap.get(idPrefix);
        if (iriPrefix != null) {
            return iriPrefix;
        } else {
            return getDefaultIRIPrefix();
        }
    }

    /**
     * Sets the IRI prefix for a given id prefix. This clears any previously set IRI prefix for the
     * given id prefix.
     * 
     * @param idPrefix The id prefix to set.
     * @param iriPrefix The IRI prefix that the id prefix maps to.
     */
    public void setIRIPrefix(String idPrefix, String iriPrefix) {
        checkNotNull(idPrefix, "idPrefix must not be null");
        checkNotNull(iriPrefix, "iriPrefix must not be null");
        idPrefix2IRIPrefixMap.put(idPrefix, iriPrefix);
    }
}
