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
package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;

/**
 * A generic factory class for OWLOntologyFormats. This class can act as a
 * factory for any OWLOntologyFormat type that has a no argument constructor
 * (the default type of OWLOntologyFormat).
 * 
 * @author ignazio
 */
public abstract class OWLDocumentFormatFactoryImpl implements OWLDocumentFormatFactory {

    private final List<String> mimeTypes;
    private final String key;
    private final boolean isTextualFormat;

    /**
     * Default, for a textual format with no mime types.
     */
    protected OWLDocumentFormatFactoryImpl() {
        this(new ArrayList<String>(0), true);
    }

    protected OWLDocumentFormatFactoryImpl(List<String> mimeTypes) {
        this(mimeTypes, true);
    }

    protected OWLDocumentFormatFactoryImpl(List<String> mimeTypes, boolean isTextualFormat) {
        this.mimeTypes = new ArrayList<>(mimeTypes);
        this.isTextualFormat = isTextualFormat;
        key = this.getClass().getName();
    }

    protected OWLDocumentFormatFactoryImpl(List<String> mimeTypes, boolean isTextualFormat, String key) {
        this.mimeTypes = new ArrayList<>(mimeTypes);
        this.isTextualFormat = isTextualFormat;
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public @Nullable String getDefaultMIMEType() {
        if (mimeTypes.isEmpty()) {
            return null;
        } else {
            return mimeTypes.get(0);
        }
    }

    @Override
    public List<String> getMIMETypes() {
        if (mimeTypes.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(mimeTypes);
        }
    }

    @Override
    public boolean handlesMimeType(String mimeType) {
        String type = mimeType;
        if (mimeType.indexOf(';') > 0) {
            type = mimeType.substring(0, mimeType.indexOf(';'));
        }
        for (String nextMimeType : getMIMETypes()) {
            if (mimeType.equalsIgnoreCase(nextMimeType)) {
                return true;
            }
            if (mimeType != type && type.equalsIgnoreCase(nextMimeType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTextual() {
        return isTextualFormat;
    }

    @Override
    public OWLDocumentFormat get() {
        return createFormat();
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLDocumentFormatFactory)) {
            return false;
        }
        OWLDocumentFormatFactory otherFactory = (OWLDocumentFormatFactory) obj;
        return getKey().equals(otherFactory.getKey());
    }
}
