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
package org.semanticweb.owlapi.mansyntax.renderer;

import java.util.Map;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * A special short form provider that delegates to a prefix manager to obtain
 * short forms. The only difference between this short form provider and a
 * prefix manager is that names with the default prefix do not have a colon with
 * this short form provider.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class ManchesterOWLSyntaxPrefixNameShortFormProvider implements
        ShortFormProvider {

    private DefaultPrefixManager prefixManager;

    /**
     * Constructs a short form provider that reuses any prefix name mappings
     * obtainable from the format of the specified ontology (the manager will be
     * asked for the ontology format of the specified ontology).
     * 
     * @param ont
     *        The ontology
     */
    public ManchesterOWLSyntaxPrefixNameShortFormProvider(
            @Nonnull OWLOntology ont) {
        this(ont.getOWLOntologyManager().getOntologyFormat(ont));
    }

    /**
     * Constructs a short form provider that reuses any prefix name mappings
     * from the specified ontology format.
     * 
     * @param format
     *        The format from which prefix name mappings will be reused
     */
    public ManchesterOWLSyntaxPrefixNameShortFormProvider(
            OWLOntologyFormat format) {
        prefixManager = new DefaultPrefixManager();
        if (format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat ontFormat = (PrefixOWLOntologyFormat) format;
            prefixManager.copyPrefixesFrom(ontFormat);
            prefixManager.setPrefixComparator(ontFormat.getPrefixComparator());
        }
    }

    /**
     * Constructs a short form provider that uses the specified prefix mappings.
     * 
     * @param prefixManager
     *        A prefix manager which will be used to obtain prefix mappings
     */
    public ManchesterOWLSyntaxPrefixNameShortFormProvider(
            DefaultPrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

    /** @return prefix manager map. The map is a copy. */
    public Map<String, String> getPrefixName2PrefixMap() {
        return prefixManager.getPrefixName2PrefixMap();
    }

    @SuppressWarnings("null")
    @Override
    public String getShortForm(OWLEntity entity) {
        String sf = prefixManager.getShortForm(entity);
        if (sf.startsWith(":")) {
            return sf.substring(1);
        } else {
            return sf;
        }
    }

    /**
     * Gets the short form for an IRI.
     * 
     * @param iri
     *        The IRI
     * @return The short form for the specified IRI
     */
    public String getShortForm(@Nonnull IRI iri) {
        return iri.toQuotedString();
    }

    @Override
    public void dispose() {
        prefixManager.dispose();
    }
}
