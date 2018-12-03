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
package org.semanticweb.owlapi.manchestersyntax.renderer;

import javax.annotation.Nullable;

/**
 * The listener interface for receiving renderer events. The class that is interested in processing
 * a renderer event implements this interface, and the object created with that class is registered
 * with a component using the component's {@code addRendererListener} method. When the renderer
 * event occurs, that object's appropriate method is invoked.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public interface RendererListener {

    /**
     * Frame rendering prepared.
     *
     * @param frameName the frame name
     * @param event the event
     */
    void frameRenderingPrepared(String frameName, @Nullable RendererEvent event);

    /**
     * Frame rendering started.
     *
     * @param frameName the frame name
     * @param event the event
     */
    void frameRenderingStarted(String frameName, @Nullable RendererEvent event);

    /**
     * Frame rendering finished.
     *
     * @param frameName the frame name
     * @param event the event
     */
    void frameRenderingFinished(String frameName, @Nullable RendererEvent event);

    /**
     * Section rendering prepared.
     *
     * @param sectionName the section name
     * @param event the event
     */
    void sectionRenderingPrepared(String sectionName, @Nullable RendererEvent event);

    /**
     * Section rendering started.
     *
     * @param sectionName the section name
     * @param event the event
     */
    void sectionRenderingStarted(String sectionName, @Nullable RendererEvent event);

    /**
     * Section item prepared.
     *
     * @param sectionName the section name
     * @param event the event
     */
    void sectionItemPrepared(String sectionName, @Nullable RendererEvent event);

    /**
     * Section item finished.
     *
     * @param sectionName the section name
     * @param event the event
     */
    void sectionItemFinished(String sectionName, @Nullable RendererEvent event);

    /**
     * Section rendering finished.
     *
     * @param sectionName the section name
     * @param event the event
     */
    void sectionRenderingFinished(String sectionName, @Nullable RendererEvent event);
}
