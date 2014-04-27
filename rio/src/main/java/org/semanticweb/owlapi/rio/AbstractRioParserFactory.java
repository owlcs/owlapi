/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Queensland
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, The University of Queensland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.semanticweb.owlapi.rio;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.RioRDFOntologyFormatFactory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public abstract class AbstractRioParserFactory implements OWLParserFactory {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public OWLParser createParser() {
        return new RioParserImpl(getRioFormatFactory());
    }

    @Override
    public final Set<OWLOntologyFormatFactory> getSupportedFormats() {
        return Collections
                .<OWLOntologyFormatFactory> singleton(getRioFormatFactory());
    }

    /**
     * @return Rio format factory
     */
    public abstract RioRDFOntologyFormatFactory getRioFormatFactory();

    @Override
    public OWLParser get() {
        return createParser();
    }

    @Override
    public String getDefaultMIMEType() {
        return getRioFormatFactory().createFormat().getRioFormat()
                .getDefaultMIMEType();
    }

    @SuppressWarnings("null")
    @Override
    public List<String> getMIMETypes() {
        return getRioFormatFactory().createFormat().getRioFormat()
                .getMIMETypes();
    }

    @Override
    public boolean handlesMimeType(String mimeType) {
        return getRioFormatFactory().createFormat().getRioFormat()
                .hasMIMEType(mimeType);
    }
}
