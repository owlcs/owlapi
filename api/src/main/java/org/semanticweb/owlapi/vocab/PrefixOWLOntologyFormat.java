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

import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Feb-2007<br><br>
 * <p/>
 * An
 */
public class PrefixOWLOntologyFormat extends OWLOntologyFormat implements PrefixManager {

    private DefaultPrefixManager nsm;


    public PrefixOWLOntologyFormat() {
        nsm = new DefaultPrefixManager();
        nsm.clear();
    }


    /**
     * A convenience method to add a prefix name to prefix mapping
     * @param prefixName   The prefix name which maps to a prefix
     * @param prefix The prefix
     */
    public void setPrefix(String prefixName, String prefix) {
        if(!prefixName.endsWith(":")) {
            prefixName = prefixName + ":";
        }
        nsm.setPrefix(prefixName, prefix);
    }

    /**
     * Clears any previously set prefixes
     */
    public void clearPrefixes() {
        nsm.clear();
    }

    /**
     * Copies the prefix from another ontology format into this format
     * @param fromFormat The format that the prefixes should be copied from
     */
    public void copyPrefixesFrom(PrefixOWLOntologyFormat fromFormat) {
        Map<String, String> map = fromFormat.getPrefixName2PrefixMap();
        for(String pn : map.keySet()) {
            String prefix = map.get(pn);
            nsm.setPrefix(pn, prefix);
        }
    }

    public void copyPrefixesFrom(PrefixManager prefixManager) {
        for(String prefixName : prefixManager.getPrefixNames()) {
            String prefix = prefixManager.getPrefix(prefixName);
            nsm.setPrefix(prefixName, prefix);
        }
    }

    /**
     * Gets the prefix names that have a mapping in this prefix manager
     * @return A set of string representing the prefix names
     */
    public Set<String> getPrefixNames() {
        return nsm.getPrefixNames();
    }

    /**
     * Sets the default namespace. This is equivalent to adding mapping from the empty string prefix to a
     * namespace.
     * @param namespace The namespace to be set.
     */
    public void setDefaultPrefix(String namespace) {
        nsm.setDefaultPrefix(namespace);
    }


    public boolean containsPrefixMapping(String prefix) {
        return nsm.containsPrefixMapping(prefix);
    }


    public String getDefaultPrefix() {
        return nsm.getDefaultPrefix();
    }


    public Map<String, String> getPrefixName2PrefixMap() {
        return nsm.getPrefixName2PrefixMap();
    }


    public String getPrefix(String prefixName) {
        return nsm.getPrefix(prefixName);
    }


    public IRI getIRI(String iri) {
        return nsm.getIRI(iri);
    }

    public String getPrefixIRI(IRI iri) {
        return nsm.getPrefixIRI(iri);
    }
}
