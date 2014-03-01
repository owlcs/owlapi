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

import org.semanticweb.owlapi.annotations.HasIdentifierKey;
import org.semanticweb.owlapi.annotations.IsBinaryFormat;
import org.semanticweb.owlapi.annotations.SupportsMIMEType;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

/**
 * A generic factory class for OWLOntologyFormats. This class can act as a
 * factory for any OWLOntologyFormat type that has a no argument constructor
 * (the default type of OWLOntologyFormat).
 * 
 * @author ignazio
 * @param <T>
 *        built type
 */
public class OWLOntologyFormatFactoryImpl<T extends OWLOntologyFormat>
        implements OWLOntologyFormatFactory {

    private static final long serialVersionUID = 40000L;
    private Class<T> type;

    /**
     * @param type
     *        type of format that this factory provides
     */
    public OWLOntologyFormatFactoryImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public OWLOntologyFormat createFormat() {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(
                    "Cannot instantiate an OWLOntologyFormat of type " + type);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Cannot instantiate an OWLOntologyFormat of type " + type);
        }
    }

    @Override
    public String getKey() {
        HasIdentifierKey id = type.getAnnotation(HasIdentifierKey.class);
        if (id != null) {
            return id.value();
        }
        // if there is no annotation, it is necessary to create a throwaway
        // instance
        return createFormat().getKey();
    }

    @Override
    public String getDefaultMIMEType() {
        SupportsMIMEType types = this.type
                .getAnnotation(SupportsMIMEType.class);
        if (types == null) {
            return null;
        }
        return types.defaultMIMEType();
    }

    @Override
    public List<String> getMIMETypes() {
        SupportsMIMEType types = this.type
                .getAnnotation(SupportsMIMEType.class);
        if (types == null) {
            return Collections.emptyList();
        }
        List<String> mimeTypes = new ArrayList<String>();
        mimeTypes.add(types.defaultMIMEType());
        for (String s : types.supportedMIMEtypes()) {
            mimeTypes.add(s);
        }
        return mimeTypes;
    }

    @Override
    public boolean handlesMimeType(String mimeType) {
        return getMIMETypes().contains(mimeType);
    }

    @Override
    public boolean isTextual() {
        IsBinaryFormat binary = type.getAnnotation(IsBinaryFormat.class);
        if (binary == null) {
            return true;
        }
        return !binary.value();
    }

    @Override
    public OWLOntologyFormat get() {
        return createFormat();
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (null == other) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof OWLOntologyFormatFactory)) {
            return false;
        }
        OWLOntologyFormatFactory otherFactory = (OWLOntologyFormatFactory) other;
        return getKey().equals(otherFactory.getKey());
    }
}
