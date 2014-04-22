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
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleRenderer;

/**
 * A utility class which can be used by implementations to provide a toString
 * rendering of OWL API objects. The idea is that this is pluggable. TODO this
 * does not allow for independent rendering; in a multithreaded situation, the
 * rendere may change mid execution because of the static singleton instance
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class ToStringRenderer {

    @Nonnull
    private static ToStringRenderer instance = new ToStringRenderer();
    private OWLObjectRenderer renderer;

    private ToStringRenderer() {
        renderer = new SimpleRenderer();
    }

    /** @return the singleton instance */
    public static ToStringRenderer getInstance() {
        return instance;
    }

    /**
     * @param provider
     *        the new short form provider
     */
    public synchronized void setShortFormProvider(
            @Nonnull ShortFormProvider provider) {
        renderer.setShortFormProvider(provider);
    }

    /**
     * @param renderer
     *        the new renderer to use
     */
    public synchronized void setRenderer(@Nonnull OWLObjectRenderer renderer) {
        this.renderer = checkNotNull(renderer, "renderer cannot be null");
    }

    /**
     * @param object
     *        the object to render
     * @return the rendering for the object
     */
    public synchronized String getRendering(@Nonnull OWLObject object) {
        return renderer.render(checkNotNull(object, "object cannot be null"));
    }
}
