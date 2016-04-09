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
package org.semanticweb.owlapi.rdf.rdfxml.renderer;

import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.*;

import java.util.EnumMap;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

/**
 * Developed as part of the CO-ODE project http://www.co-ode.org .
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public final class XMLWriterPreferences {

    private static final @Nonnull XMLWriterPreferences INSTANCE = new XMLWriterPreferences();
    /** Local override map. */
    private EnumMap<ConfigurationOptions, Object> overrides = new EnumMap<>(ConfigurationOptions.class);

    private XMLWriterPreferences() {}

    /**
     * @return copy of the current XMLWriterPreferences instance. Passing this
     *         to an XML writer breaks the link between multiple threads
     *         changing the settings. The class should be rewritten to be
     *         immutable but this would break the current interface.
     */
    @Nonnull
    public synchronized XMLWriterPreferences copy() {
        XMLWriterPreferences p = new XMLWriterPreferences();
        p.overrides.putAll(overrides);
        return p;
    }

    /**
     * @return the only instance
     */
    public static XMLWriterPreferences getInstance() {
        return INSTANCE;
    }

    /**
     * @return use namespace entities
     */
    public boolean isUseNamespaceEntities() {
        return USE_NAMESPACE_ENTITIES.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param useNamespaceEntities
     *        useNamespaceEntities
     */
    public void setUseNamespaceEntities(boolean useNamespaceEntities) {
        overrides.put(USE_NAMESPACE_ENTITIES, useNamespaceEntities);
    }

    /**
     * @return indenting
     */
    public boolean isIndenting() {
        return INDENTING.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param indenting
     *        indenting
     */
    public void setIndenting(boolean indenting) {
        overrides.put(INDENTING, indenting);
    }

    /**
     * @return indent size
     */
    public int getIndentSize() {
        return INDENT_SIZE.getValue(Integer.class, overrides).intValue();
    }

    /**
     * @param indentSize
     *        indentSize
     */
    public void setIndentSize(int indentSize) {
        overrides.put(INDENT_SIZE, indentSize);
    }

    public void setLabelsAsBanner(boolean labelsAsBanner) {
        overrides.put(LABELS_AS_BANNER, labelsAsBanner);
    }

    public boolean isLabelsAsBanner() {
        return LABELS_AS_BANNER.getValue(Boolean.class, overrides).booleanValue();
    }

    public void setBannersEnabled(boolean bannersEnabled) {
        overrides.put(BANNERS_ENABLED, bannersEnabled);
    }

    public boolean isBannersEnabled() {
        return BANNERS_ENABLED.getValue(Boolean.class, overrides).booleanValue();
    }
}
