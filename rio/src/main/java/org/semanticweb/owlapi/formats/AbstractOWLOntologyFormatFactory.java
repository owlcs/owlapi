/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2014, Commonwealth Scientific and Industrial Research Organisation
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
 * Copyright 2014, Commonwealth Scientific and Industrial Research Organisation
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
package org.semanticweb.owlapi.formats;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

/**
 * Abstract implementation of OWLOntologyFormatFactory to define the use of
 * getKey for equals and hashCode.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public abstract class AbstractOWLOntologyFormatFactory implements
        OWLOntologyFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntologyFormatFactory)) {
            return false;
        }
        OWLOntologyFormatFactory otherFactory = (OWLOntologyFormatFactory) obj;
        return getKey().equals(otherFactory.getKey());
    }

    @Override
    public String getDefaultMIMEType() {
        if (getMIMETypes().isEmpty()) {
            return null;
        } else {
            return getMIMETypes().get(0);
        }
    }

    @Override
    public boolean handlesMimeType(String mimeType) {
        if (mimeType == null) {
            return false;
        }
        String type = mimeType;
        if (mimeType.indexOf(';') > 0) {
            type = mimeType.substring(0, mimeType.indexOf(';'));
        }
        List<String> mimeTypes = getMIMETypes();
        for (String nextMimeType : mimeTypes) {
            if (mimeType.equalsIgnoreCase(nextMimeType)) {
                return true;
            }
            if (mimeType != type && type.equalsIgnoreCase(nextMimeType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Default implementation for isTextual as the vast majority of formats will
     * be textual. Override to return false if this format cannot be processed
     * textually using java.io.Reader.
     */
    @Override
    public boolean isTextual() {
        return true;
    }

    @Override
    public OWLOntologyFormat get() {
        return createFormat();
    }
}
