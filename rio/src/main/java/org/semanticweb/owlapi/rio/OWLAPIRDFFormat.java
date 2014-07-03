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
package org.semanticweb.owlapi.rio;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;

/**
 * Extended {@link RDFFormat} constants for OWL formats that can be translated
 * into RDF.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class OWLAPIRDFFormat extends RDFFormat {

    /**
     * The <a href="http://www.w3.org/TR/owl2-manchester-syntax/">Manchester OWL
     * Syntax</a> file format.
     * <p>
     * The file extension {@code .omn} is recommended for Manchester OWL Syntax
     * documents. The media type is {@code text/owl-manchester} and encoding is
     * in UTF-8.
     * </p>
     * 
     * @see <a href="http://www.w3.org/TR/owl2-manchester-syntax/">OWL 2 Web
     *      Ontology Language Manchester Syntax (Second Edition)</a>
     */
    public static final OWLAPIRDFFormat MANCHESTER_OWL = new OWLAPIRDFFormat(
            "Manchester OWL Syntax", Arrays.asList("text/owl-manchester"),
            Charset.forName("UTF-8"), Arrays.asList("omn"),
            SUPPORTS_NAMESPACES, NO_CONTEXTS,
            new ManchesterSyntaxDocumentFormat());
    /**
     * The <a href="http://www.w3.org/TR/owl2-xml-serialization/">OWL/XML</a>
     * file format.
     * <p>
     * The file extension {@code .owx} is recommended for OWL/XML documents. The
     * media type is {@code application/owl+xml} and encoding is in UTF-8.
     * </p>
     * 
     * @see <a href="http://www.w3.org/TR/owl2-xml-serialization/">OWL 2 Web
     *      Ontology Language XML Serialization (Second Edition)</a>
     */
    public static final OWLAPIRDFFormat OWL_XML = new OWLAPIRDFFormat(
            "OWL/XML Syntax", Arrays.asList("application/owl+xml"),
            Charset.forName("UTF-8"), Arrays.asList("owx"),
            SUPPORTS_NAMESPACES, NO_CONTEXTS, new OWLXMLDocumentFormat());
    /**
     * The <a href="http://www.w3.org/TR/owl2-syntax/">OWL Functional Syntax</a>
     * file format.
     * <p>
     * The file extension {@code .ofn} is recommended for OWL Functional Syntax
     * documents. The media type is {@code text/owl-functional} and encoding is
     * in UTF-8.
     * </p>
     * 
     * @see <a href="http://www.w3.org/TR/owl2-syntax/">OWL 2 Web Ontology
     *      Language Structural Specification and Functional-Style Syntax
     *      (Second Edition)</a>
     */
    public static final OWLAPIRDFFormat OWL_FUNCTIONAL = new OWLAPIRDFFormat(
            "OWL Functional Syntax", Arrays.asList("text/owl-functional"),
            Charset.forName("UTF-8"), Arrays.asList("ofn"),
            SUPPORTS_NAMESPACES, NO_CONTEXTS,
            new FunctionalSyntaxDocumentFormat());
    private OWLDocumentFormat owlFormat;
    private OWLDocumentFormatFactory owlFormatFactory;

    /**
     * @param name
     *        name
     * @param mimeType
     *        mimeType
     * @param charset
     *        charset
     * @param fileExtension
     *        fileExtension
     * @param supportsNamespaces
     *        supportsNamespaces
     * @param supportsContexts
     *        supportsContexts
     * @param owlFormat
     *        owlFormat
     */
    public OWLAPIRDFFormat(String name, String mimeType, Charset charset,
            String fileExtension, boolean supportsNamespaces,
            boolean supportsContexts, OWLDocumentFormatFactory owlFormat) {
        super(name, mimeType, charset, fileExtension, supportsNamespaces,
                supportsContexts);
        owlFormatFactory = owlFormat;
    }

    /**
     * @param name
     *        name
     * @param mimeType
     *        mimeType
     * @param charset
     *        charset
     * @param fileExtensions
     *        fileExtensions
     * @param supportsNamespaces
     *        supportsNamespaces
     * @param supportsContexts
     *        supportsContexts
     * @param owlFormat
     *        owlFormat
     */
    public OWLAPIRDFFormat(String name, String mimeType, Charset charset,
            Collection<String> fileExtensions, boolean supportsNamespaces,
            boolean supportsContexts, OWLDocumentFormatFactory owlFormat) {
        super(name, mimeType, charset, fileExtensions, supportsNamespaces,
                supportsContexts);
        owlFormatFactory = owlFormat;
    }

    /**
     * @param name
     *        name
     * @param mimeTypes
     *        mimeTypes
     * @param charset
     *        charset
     * @param fileExtensions
     *        fileExtensions
     * @param supportsNamespaces
     *        supportsNamespaces
     * @param supportsContexts
     *        supportsContexts
     * @param owlFormat
     *        owlFormat
     */
    public OWLAPIRDFFormat(String name, Collection<String> mimeTypes,
            Charset charset, Collection<String> fileExtensions,
            boolean supportsNamespaces, boolean supportsContexts,
            OWLDocumentFormatFactory owlFormat) {
        super(name, mimeTypes, charset, fileExtensions, supportsNamespaces,
                supportsContexts);
        owlFormatFactory = owlFormat;
    }

    /**
     * @param name
     *        name
     * @param mimeType
     *        mimeType
     * @param charset
     *        charset
     * @param fileExtension
     *        fileExtension
     * @param supportsNamespaces
     *        supportsNamespaces
     * @param supportsContexts
     *        supportsContexts
     * @param owlFormat
     *        owlFormat
     */
    public OWLAPIRDFFormat(String name, String mimeType, Charset charset,
            String fileExtension, boolean supportsNamespaces,
            boolean supportsContexts, OWLDocumentFormat owlFormat) {
        super(name, mimeType, charset, fileExtension, supportsNamespaces,
                supportsContexts);
        this.owlFormat = owlFormat;
    }

    /**
     * @param name
     *        name
     * @param mimeType
     *        mimeType
     * @param charset
     *        charset
     * @param fileExtensions
     *        fileExtensions
     * @param supportsNamespaces
     *        supportsNamespaces
     * @param supportsContexts
     *        supportsContexts
     * @param owlFormat
     *        owlFormat
     */
    public OWLAPIRDFFormat(String name, String mimeType, Charset charset,
            Collection<String> fileExtensions, boolean supportsNamespaces,
            boolean supportsContexts, OWLDocumentFormat owlFormat) {
        super(name, mimeType, charset, fileExtensions, supportsNamespaces,
                supportsContexts);
        this.owlFormat = owlFormat;
    }

    /**
     * @param name
     *        name
     * @param mimeTypes
     *        mimeTypes
     * @param charset
     *        charset
     * @param fileExtensions
     *        fileExtensions
     * @param supportsNamespaces
     *        supportsNamespaces
     * @param supportsContexts
     *        supportsContexts
     * @param owlFormat
     *        owlFormat
     */
    public OWLAPIRDFFormat(String name, Collection<String> mimeTypes,
            Charset charset, Collection<String> fileExtensions,
            boolean supportsNamespaces, boolean supportsContexts,
            OWLDocumentFormat owlFormat) {
        super(name, mimeTypes, charset, fileExtensions, supportsNamespaces,
                supportsContexts);
        this.owlFormat = owlFormat;
    }

    /**
     * @return A fresh instance of the matching {@link OWLDocumentFormat} for
     *         this OWLAPIRDFFormat.
     */
    public OWLDocumentFormat getOWLFormat() {
        if (owlFormatFactory != null) {
            return owlFormatFactory.createFormat();
        } else {
            try {
                return owlFormat.getClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(
                        "Format did not have a factory or a public default constructor",
                        e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                        "Format did not have a factory or a public default constructor",
                        e);
            }
        }
    }
}
