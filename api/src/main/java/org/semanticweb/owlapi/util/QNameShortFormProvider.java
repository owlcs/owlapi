package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLEntity;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 *
 * A short form provider which creates QNames for entities
 */
public class QNameShortFormProvider implements ShortFormProvider {

    private NamespaceUtil namespaceUtil;

    /**
     * A place to store the result of splitting URIs into
     * namespaces and local names.
     */
    private static String [] result = new String [2];


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
        for(String prefix : prefix2NamespaceMap.keySet()) {
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
