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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

/**
 * Developed as part of the CO-ODE project http://www.co-ode.org .
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public final class XMLWriterPreferences {

    private static final @Nonnull XMLWriterPreferences INSTANCE = new XMLWriterPreferences();
    private AtomicBoolean useNamespaceEntities = new AtomicBoolean(false);
    private AtomicBoolean indenting = new AtomicBoolean(true);
    private AtomicBoolean labelsAsBanner = new AtomicBoolean(false);
    private AtomicInteger indentSize = new AtomicInteger(4);

    private XMLWriterPreferences() {}

    /**
     * @return copy of the current XMLWriterPreferences instance. Passing this
     *         to an XML writer breaks the link between multiple threads
     *         changing the settings. The class should be rewritten to be
     *         immutable but this would break the current interface.
     */
    public synchronized XMLWriterPreferences copy() {
        XMLWriterPreferences p = new XMLWriterPreferences();
        p.indenting.set(indenting.get());
        p.indentSize.set(indentSize.get());
        p.useNamespaceEntities.set(useNamespaceEntities.get());
        p.labelsAsBanner.set(labelsAsBanner.get());
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
        return useNamespaceEntities.get();
    }

    /**
     * @param useNamespaceEntities
     *        useNamespaceEntities
     */
    public void setUseNamespaceEntities(boolean useNamespaceEntities) {
        this.useNamespaceEntities.set(useNamespaceEntities);
    }

    /**
     * @return indenting
     */
    public boolean isIndenting() {
        return indenting.get();
    }

    /**
     * @param indenting
     *        indenting
     */
    public void setIndenting(boolean indenting) {
        this.indenting.set(indenting);
    }

    /**
     * @return indent size
     */
    public int getIndentSize() {
        return indentSize.get();
    }

    /**
     * @param indentSize
     *        indentSize
     */
    public void setIndentSize(int indentSize) {
        this.indentSize.set(indentSize);
    }

    /**
     * @param labelsAsBanner
     *        true if labels should be used for banner comments
     */
    public void setLabelsAsBanner(boolean labelsAsBanner) {
        this.labelsAsBanner.set(labelsAsBanner);
    }

    /**
     * @return true if labels should be used for banner comments
     */
    public boolean isLabelsAsBanner() {
        return labelsAsBanner.get();
    }
}
