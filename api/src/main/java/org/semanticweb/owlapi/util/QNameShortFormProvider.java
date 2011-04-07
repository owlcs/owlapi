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

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 * A short form provider which creates QNames for entities
 */
public class QNameShortFormProvider implements ShortFormProvider {

    private NamespaceUtil namespaceUtil;

    /**
     * A place to store the result of splitting URIs into
     * namespaces and local names.
     */
    private static String[] result = new String[2];


    /**
     * Creates a QNameShortFormProvider where namespace prefix mappings
     * will automatically be generated.
     */
    public QNameShortFormProvider() {
        this(new HashMap<String, String>());
    }


    /**
     * Creates a QNameShortFormProvider where the specified map overrides
     * any auto-generated prefix namespace mappings.
     * @param prefix2NamespaceMap The map which contains a prefix -> namespace
     * mapping.
     */
    public QNameShortFormProvider(Map<String, String> prefix2NamespaceMap) {
        namespaceUtil = new NamespaceUtil();
        for (String prefix : prefix2NamespaceMap.keySet()) {
            namespaceUtil.setPrefix(prefix2NamespaceMap.get(prefix), prefix);
        }
    }


    public String getShortForm(OWLEntity entity) {
        String uriString = entity.getIRI().toString();
        namespaceUtil.split(uriString, result);
        String namespace = result[0];
        String localName = result[1];
        String prefix = namespaceUtil.getPrefix(namespace);
        return prefix + ":" + localName;
    }


    public void dispose() {
    }
}
