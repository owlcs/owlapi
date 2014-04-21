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

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * A PrefixOWLOntologyFormat delegates all PrefixManager operations to a
 * PrefixManager implementation.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class PrefixOWLOntologyFormat extends OWLOntologyFormat
        implements PrefixManager {

    private static final long serialVersionUID = 40000L;
    private PrefixManager nsm;

    /** default constructor */
    public PrefixOWLOntologyFormat() {
        this(new DefaultPrefixManager());
    }

    /**
     * @param manager
     *        prefix manager to use
     */
    public PrefixOWLOntologyFormat(@Nonnull PrefixManager manager) {
        nsm = checkNotNull(manager, "manager cannot be null");
    }

    /**
     * @param m
     *        prefix manager to use
     */
    public void setPrefixManager(@Nonnull PrefixManager m) {
        nsm = checkNotNull(m, "m cannot be null");
    }

    @Override
    public void setPrefix(@Nonnull String prefixName, @Nonnull String prefix) {
        nsm.setPrefix(prefixName, prefix);
    }

    @Override
    public void clear() {
        nsm.clear();
    }

    @Nonnull
    @Override
    public Set<String> getPrefixNames() {
        return nsm.getPrefixNames();
    }

    @Override
    public void setDefaultPrefix(@Nonnull String namespace) {
        nsm.setDefaultPrefix(namespace);
    }

    @Override
    public boolean containsPrefixMapping(@Nonnull String prefix) {
        return nsm.containsPrefixMapping(prefix);
    }

    @Nonnull
    @Override
    public String getDefaultPrefix() {
        return nsm.getDefaultPrefix();
    }

    @Nonnull
    @Override
    public Map<String, String> getPrefixName2PrefixMap() {
        return nsm.getPrefixName2PrefixMap();
    }

    @Nonnull
    @Override
    public String getPrefix(@Nonnull String prefixName) {
        return nsm.getPrefix(prefixName);
    }

    @Nonnull
    @Override
    public IRI getIRI(@Nonnull String iri) {
        return nsm.getIRI(iri);
    }

    @Override
    public String getPrefixIRI(@Nonnull IRI iri) {
        return nsm.getPrefixIRI(iri);
    }

    @Override
    public void copyPrefixesFrom(@Nonnull PrefixManager prefixManager) {
        nsm.copyPrefixesFrom(prefixManager);
    }

    @Override
    public void unregisterNamespace(@Nonnull String namespace) {
        nsm.unregisterNamespace(namespace);
    }

    @Nonnull
    @Override
    public Comparator<String> getPrefixComparator() {
        return nsm.getPrefixComparator();
    }

    @Override
    public void setPrefixComparator(@Nonnull Comparator<String> comparator) {
        nsm.setPrefixComparator(comparator);
    }
}
