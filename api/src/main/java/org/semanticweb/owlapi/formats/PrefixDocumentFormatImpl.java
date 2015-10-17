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
package org.semanticweb.owlapi.formats;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormatImpl;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.StringComparator;

/**
 * A PrefixOWLOntologyFormat delegates all PrefixManager operations to a
 * PrefixManager implementation.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class PrefixDocumentFormatImpl extends OWLDocumentFormatImpl implements PrefixDocumentFormat {

    private @Nonnull PrefixManager nsm;

    /** Default constructor. */
    public PrefixDocumentFormatImpl() {
        this(new DefaultPrefixManager());
    }

    /**
     * @param manager
     *        prefix manager to use
     */
    public PrefixDocumentFormatImpl(PrefixManager manager) {
        nsm = checkNotNull(manager, "manager cannot be null");
    }

    @Override
    public void setPrefixManager(PrefixManager m) {
        nsm = checkNotNull(m, "m cannot be null");
    }

    @Override
    public void setPrefix(String prefixName, String prefix) {
        nsm.setPrefix(prefixName, prefix);
    }

    @Override
    public void clear() {
        nsm.clear();
    }

    @Override
    public Stream<String> prefixNames() {
        return nsm.prefixNames();
    }

    @Override
    public void setDefaultPrefix(String defaultPrefix) {
        nsm.setDefaultPrefix(defaultPrefix);
    }

    @Override
    public boolean containsPrefixMapping(String prefixName) {
        return nsm.containsPrefixMapping(prefixName);
    }

    @Override
    public @Nullable String getDefaultPrefix() {
        return nsm.getDefaultPrefix();
    }

    @Override
    public Map<String, String> getPrefixName2PrefixMap() {
        return nsm.getPrefixName2PrefixMap();
    }

    @Override
    public @Nullable String getPrefix(String prefixName) {
        return nsm.getPrefix(prefixName);
    }

    @Override
    public IRI getIRI(String prefixIRI) {
        return nsm.getIRI(prefixIRI);
    }

    @Override
    public @Nullable String getPrefixIRI(IRI iri) {
        return nsm.getPrefixIRI(iri);
    }

    @Override
    public void copyPrefixesFrom(PrefixManager from) {
        nsm.copyPrefixesFrom(from);
    }

    @Override
    public void copyPrefixesFrom(Map<String, String> from) {
        nsm.copyPrefixesFrom(from);
    }

    @Override
    public void unregisterNamespace(String namespace) {
        nsm.unregisterNamespace(namespace);
    }

    @Override
    public StringComparator getPrefixComparator() {
        return nsm.getPrefixComparator();
    }

    @Override
    public void setPrefixComparator(StringComparator comparator) {
        nsm.setPrefixComparator(comparator);
    }

    @Override
    public String getKey() {
        return "Generic prefix ontology format";
    }

    @Override
    public boolean isPrefixOWLOntologyFormat() {
        return true;
    }
}
