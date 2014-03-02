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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * A short form provider which creates QNames for entities.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class QNameShortFormProvider implements ShortFormProvider {

    private final NamespaceUtil namespaceUtil = new NamespaceUtil();

    /**
     * Creates a QNameShortFormProvider where namespace prefix mappings will
     * automatically be generated.
     */
    public QNameShortFormProvider() {
        this(new HashMap<String, String>());
    }

    /**
     * Creates a QNameShortFormProvider where the specified map overrides any
     * auto-generated prefix namespace mappings.
     * 
     * @param prefix2NamespaceMap
     *        The map which contains a prefix to namespace mapping. The prefix
     *        must not have a trailing ":"; if one is there, it will be removed
     */
    public QNameShortFormProvider(
            @Nonnull Map<String, String> prefix2NamespaceMap) {
        checkNotNull(prefix2NamespaceMap, "prefix2NamespaceMap cannot be null");
        for (Map.Entry<String, String> e : prefix2NamespaceMap.entrySet()) {
            String key = e.getKey();
            int lastChar = key.length() - 1;
            if (key.charAt(lastChar) == ':') {
                key = key.substring(0, lastChar);
            }
            namespaceUtil.setPrefix(e.getValue(), key);
        }
    }

    @Override
    public String getShortForm(OWLEntity entity) {
        checkNotNull(entity, "entity cannot be null");
        String namespace = entity.getIRI().getNamespace();
        String localName = entity.getIRI().getFragment();
        String prefix = namespaceUtil.getPrefix(namespace);
        return prefix + ":" + (localName != null ? localName : "");
    }

    @Override
    public void dispose() {}
}
