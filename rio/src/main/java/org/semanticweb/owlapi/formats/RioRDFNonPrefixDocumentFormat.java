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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.annotation.Nonnull;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParserRegistry;
import org.semanticweb.owlapi.model.MIMETypeAware;

/**
 * This format is designed to encapsulate any Sesame Rio RDFFormat within
 * RDFOntologyFormat, and more generally OWLOntologyFormat. <br>
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioRDFNonPrefixDocumentFormat extends
        AbstractRDFNonPrefixDocumentFormat implements MIMETypeAware,
        RioRDFDocumentFormat {

    private static final long serialVersionUID = 40000L;
    private transient RDFFormat format;
    private final String formatName;

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        for (RDFFormat f : RDFParserRegistry.getInstance().getKeys()) {
            if (f.getName().equals(formatName)) {
                format = f;
                return;
            }
        }
    }

    /**
     * Constructor for super-classes to specify which
     * {@link org.openrdf.rio.RDFFormat} they support.
     *
     * @param format
     *        The {@link org.openrdf.rio.RDFFormat} that this instance supports.
     */
    public RioRDFNonPrefixDocumentFormat(RDFFormat format) {
        this.format = format;
        formatName = this.format.getName();
    }

    /**
     * Determines if this format is an instance of a format that uses prefixes
     * to shorted IRIs.
     *
     * @return {@code true} if this format is an instance of
     *         {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat} other
     *         wise {@code false}.
     */
    @Override
    public boolean isPrefixOWLOntologyFormat() {
        return false;
    }

    /**
     * If this format is an instance of
     * {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat} then this
     * method will obtain it as a
     * {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat}.
     *
     * @return This format as a more specific
     *         {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat}.
     * @throws ClassCastException
     *         if this format is not an instance of
     *         {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat}
     */
    @Nonnull
    @Override
    public PrefixDocumentFormat asPrefixOWLOntologyFormat() {
        throw new ClassCastException("Not a Prefix Document Format");
    }

    @Override
    public String getKey() {
        String name = format.getName();
        assert name != null;
        return name;
    }

    @Override
    public RDFFormat getRioFormat() {
        return format;
    }

    @Override
    public String getDefaultMIMEType() {
        return format.getDefaultMIMEType();
    }

    @Override
    public List<String> getMIMETypes() {
        List<String> mimeTypes = format.getMIMETypes();
        assert mimeTypes != null;
        return mimeTypes;
    }

    @Override
    public boolean handlesMimeType(String mimeType) {
        return format.hasMIMEType(mimeType);
    }
}
