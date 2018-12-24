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
package org.semanticweb.owlapi6.model.providers;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLRuntimeException;

/**
 * IRI provider
 * 
 * @since 6.0.0
 */
public interface IRIProvider extends Serializable {

    /**
     * @param s the IRI string to be resolved
     * @param iri the IRI to resolve against
     * @return s resolved against this IRI (with the URI::resolve() method, unless this IRI is
     *         opaque)
     */
    default IRI resolve(String s, IRI iri) {
        // shortcut: checking absolute and opaque here saves the creation of an
        // extra URI object
        URI uri = URI.create(s);
        if (uri.isAbsolute() || uri.isOpaque()) {
            return getIRI(uri);
        }
        return getIRI(iri.toURI().resolve(uri));
    }

    /**
     * Creates an IRI from the specified String.
     *
     * @param str The String that specifies the IRI
     * @return The IRI that has the specified string representation.
     */
    IRI getIRI(String str);

    /**
     * Creates an IRI by concatenating two strings. The full IRI is an IRI that contains the
     * characters in prefix + suffix.
     *
     * @param prefix The first string
     * @param suffix The second string
     * @return An IRI whose characters consist of prefix + suffix.
     * @since 3.3
     */
    IRI getIRI(@Nullable String prefix, @Nullable String suffix);

    /**
     * @param file the file to create the IRI from
     * @return file.toURI() IRI
     */
    default IRI getIRI(File file) {
        checkNotNull(file, "file cannot be null");
        return getIRI(file.toURI());
    }

    /**
     * @param uri the uri to create the IRI from
     * @return the IRI wrapping the uri
     */
    default IRI getIRI(URI uri) {
        return getIRI(checkNotNull(uri, "uri cannot be null").toString());
    }

    /**
     * @param url the url to create the IRI from
     * @return an IRI wrapping url.toURI()
     * @throws OWLRuntimeException if the URL is ill formed
     */
    default IRI getIRI(URL url) {
        checkNotNull(url, "url cannot be null");
        try {
            return getIRI(url.toURI());
        } catch (URISyntaxException e) {
            throw new OWLRuntimeException(e);
        }
    }

}
