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
package org.semanticweb.owlapi6.model;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;

import java.net.URI;
import java.util.Optional;

import org.semanticweb.owlapi6.utilities.XMLUtils;
import org.semanticweb.owlapi6.vocab.Namespaces;
import org.semanticweb.owlapi6.vocab.OWLRDFVocabulary;

/**
 * Represents International Resource Identifiers.
 *
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public interface IRI extends OWLAnnotationSubject, OWLAnnotationValue, SWRLPredicate, CharSequence, OWLPrimitive,
    HasShortForm, org.apache.commons.rdf.api.IRI {

    @Override
    default boolean isIRI() {
        return true;
    }

    /**
     * @return the IRI scheme, e.g., http, urn, or the empty string if no scheme
     *         exists
     */
    default String getScheme() {
        return XMLUtils.schema(getNamespace());
    }

    /**
     * Obtains this IRI as a URI. Note that Java URIs handle unicode characters,
     * so there is no loss during this translation.
     *
     * @return The URI
     */
    default URI toURI() {
        return URI.create(getNamespace() + getFragment());
    }

    /**
     * Determines if this IRI is absolute.
     *
     * @return {@code true} if this IRI is absolute or {@code false} if this IRI
     *         is not absolute
     */
    default boolean isAbsolute() {
        return XMLUtils.isAbsolute(getNamespace());
    }

    @Override
    default CharSequence subSequence(int start, int end) {
        return (getNamespace() + getFragment()).subSequence(start, end);
    }

    @Override
    default char charAt(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        String n = getNamespace();
        if (index < n.length()) {
            return n.charAt(index);
        }
        return getFragment().charAt(index - n.length());
    }

    /**
     * @return the prefix
     */
    String getNamespace();

    /**
     * Determines if this IRI is in the reserved vocabulary. An IRI is in the
     * reserved vocabulary if it starts with
     * &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt; or
     * &lt;http://www.w3.org/2000/01/rdf-schema#&gt; or
     * &lt;http://www.w3.org/2001/XMLSchema#&gt; or
     * &lt;http://www.w3.org/2002/07/owl#&gt;
     *
     * @return {@code true} if the IRI is in the reserved vocabulary, otherwise
     *         {@code false}.
     */
    default boolean isReservedVocabulary() {
        String ns = getNamespace();
        return Namespaces.OWL.inNamespace(ns) || Namespaces.RDF.inNamespace(ns) || Namespaces.RDFS.inNamespace(ns)
            || Namespaces.XSD.inNamespace(ns);
    }

    /**
     * Built in annotation properties do not need declarations. Adding this
     * method to IRIs so during parsing any undeclared properties can easily be
     * disambiguated between builtin annotation properties and properties that
     * are guessed to be annotation properties because of missing declarations.
     * 
     * @return true if this IRI equals one of the vocabulary annotation
     *         properties
     */
    default boolean isBuiltinAnnotationProperty() {
        return OWLRDFVocabulary.BUILT_IN_AP_IRIS.contains(this);
    }

    /**
     * Determines if this IRI is equal to the IRI that {@code owl:Thing} is
     * named with.
     *
     * @return {@code true} if this IRI is equal to
     *         &lt;http://www.w3.org/2002/07/owl#Thing&gt; and otherwise
     *         {@code false}
     */
    default boolean isThing() {
        return equals(OWLRDFVocabulary.OWL_THING.getIRI());
    }

    /**
     * Determines if this IRI is equal to the IRI that {@code owl:Nothing} is
     * named with.
     *
     * @return {@code true} if this IRI is equal to
     *         &lt;http://www.w3.org/2002/07/owl#Nothing&gt; and otherwise
     *         {@code false}
     */
    default boolean isNothing() {
        return equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
    }

    /**
     * Determines if this IRI is equal to the IRI that is named
     * {@code rdf:PlainLiteral}.
     *
     * @return {@code true} if this IRI is equal to
     *         &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral&gt;,
     *         otherwise {@code false}
     */
    default boolean isPlainLiteral() {
        return "PlainLiteral".equals(getFragment()) && Namespaces.RDF.inNamespace(getNamespace());
    }

    /**
     * Gets the last part of the IRI that is a valid NCName; note that for some
     * IRIs this can be empty.
     *
     * @return The IRI fragment, or empty string if the IRI does not have a
     *         fragment
     */
    String getFragment();

    /**
     * @return the remainder (coincident with NCName usually) for this IRI.
     */
    default Optional<String> getRemainder() {
        String fragment = getFragment();
        if (fragment.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(fragment);
    }

    /**
     * @return true if this IRI has a file: protocol
     */
    default boolean isFileIRI() {
        return getNamespace().startsWith("file:");
    }

    /**
     * Obtained this IRI surrounded by angled brackets.
     *
     * @return This IRI surrounded by &lt; and &gt;
     */
    default String toQuotedString() {
        return ntriplesString();
    }

    /**
     * @param prefix
     *        prefix to use for replacing the IRI namespace
     * @return prefix plus IRI ncname
     */
    default String prefixedBy(String prefix) {
        checkNotNull(prefix, "prefix cannot be null");
        String r = getFragment();
        if (r.isEmpty()) {
            return prefix;
        }
        return prefix + r;
    }

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default int hashIndex() {
        return hashCode();
    }

    @Override
    default Optional<IRI> asIRI() {
        return Optional.of(this);
    }

    @Override
    default OWLObjectType type() {
        return OWLObjectType.IRI;
    }

    @Override
    default String getIRIString() {
        String f = getFragment();
        if (f.isEmpty()) {
            return getNamespace();
        }
        return getNamespace() + f;
    }

    @Override
    default String getShortForm() {
        String r = getFragment();
        if (!r.isEmpty()) {
            return r;
        }
        String n = getNamespace();
        int lastSlashIndex = n.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex != n.length() - 1) {
            return n.substring(lastSlashIndex + 1);
        }
        return toQuotedString();
    }

    @Override
    default String ntriplesString() {
        return '<' + getNamespace() + getFragment() + '>';
    }

    /**
     * @param iri
     *        iri to shorten
     * @return short form. If ther eis a remainder, that is returned. If there
     *         is no remainder, the last segment is returned. If there is no
     *         segment, the input bracketed between &lt; and &gt; will be
     *         returned.
     */
    static String getShortForm(String iri) {
        String r = XMLUtils.getNCNameSuffix(iri);
        if (r != null && !r.isEmpty()) {
            return r;
        }
        String n = XMLUtils.getNCNamePrefix(iri);
        int lastSlashIndex = n.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex != n.length() - 1) {
            return n.substring(lastSlashIndex + 1);
        }
        return '<' + iri + '>';
    }

    /**
     * @param iri
     *        iri to prefix
     * @param prefix
     *        prefix to use
     * @return prefixed iri
     */
    static String prefixedBy(String iri, String prefix) {
        checkNotNull(prefix, "prefix cannot be null");
        String r = XMLUtils.getNCNameSuffix(iri);
        if (r == null || r.isEmpty()) {
            return prefix;
        }
        return prefix + r;
    }
}
