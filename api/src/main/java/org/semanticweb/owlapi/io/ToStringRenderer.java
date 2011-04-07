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

package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleRenderer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Jan-2008<br><br>
 * A utility class which can be used by implementations to provide
 * a toString rendering of OWL API objects.  The idea is that this
 * is pluggable.
 */
public class ToStringRenderer {

	private static ToStringRenderer instance;

    private OWLObjectRenderer renderer;

    private ToStringRenderer() {
        renderer = new SimpleRenderer();
    }

    public static synchronized ToStringRenderer getInstance() {
        if (instance == null) {
            instance = new ToStringRenderer();
        }
        return instance;
    }

    /**Deprecated: this method returns internal mutable state, it is not safe to use in a multithreaded environment. Use ToStringRenderer::getInstance().setShortFormProvider() to set the ShortFormProvider instead.
     * @return the current OWLObjectRenderer */
    @Deprecated
    public synchronized OWLObjectRenderer getRenderer() {
        return renderer;
    }
    
    public synchronized void setShortFormProvider(ShortFormProvider provider) {
    	renderer.setShortFormProvider(provider);
    }

    public synchronized void setRenderer(OWLObjectRenderer renderer) {
        this.renderer = renderer;
    }

    public synchronized String getRendering(OWLObject object) {
        return renderer.render(object);
    }
}
