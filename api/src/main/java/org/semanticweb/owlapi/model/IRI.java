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
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Represents International Resource Identifiers.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class IRI
    implements OWLAnnotationSubject, OWLAnnotationValue, SWRLPredicate, CharSequence, OWLPrimitive, HasShortForm,
    org.apache.commons.rdf.api.IRI {

    /**
     * Obtains this IRI as a URI. Note that Java URIs handle unicode characters,
     * so there is no loss during this translation.
     * 
     * @return The URI
     */
    public URI toURI() {
        return URI.create(namespace + remainder);
    }

    /**
     * Determines if this IRI is absolute.
     * 
     * @return {@code true} if this IRI is absolute or {@code false} if this IRI
     *         is not absolute
     */
    public boolean isAbsolute() {
        int colonIndex = namespace.indexOf(':');
        if (colonIndex == -1) {
            return false;
        }
        for (int i = 0; i < colonIndex; i++) {
            char ch = namespace.charAt(i);
            if (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '.' && ch != '+' && ch != '-') {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the IRI scheme, e.g., http, urn
     */
    public @Nullable String getScheme() {
        int colonIndex = namespace.indexOf(':');
        if (colonIndex == -1) {
            return null;
        }
        return namespace.substring(0, colonIndex);
    }

    /**
     * @return the prefix
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param s
     *        the IRI stirng to be resolved
     * @return s resolved against this IRI (with the URI::resolve() method,
     *         unless this IRI is opaque)
     */
    public IRI resolve(String s) {
        // shortcut: checking absolute and opaque here saves the creation of an
        // extra URI object
        URI uri = URI.create(s);
        if (uri.isAbsolute() || uri.isOpaque()) {
            return create(uri);
        }
        return create(toURI().resolve(uri));
    }

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
    public boolean isReservedVocabulary() {
        return Namespaces.OWL.inNamespace(namespace) || Namespaces.RDF.inNamespace(namespace)
            || Namespaces.RDFS.inNamespace(namespace) || Namespaces.XSD.inNamespace(namespace);
    }

    /**
     * Determines if this IRI is equal to the IRI that {@code owl:Thing} is
     * named with.
     * 
     * @return {@code true} if this IRI is equal to
     *         &lt;http://www.w3.org/2002/07/owl#Thing&gt; and otherwise
     *         {@code false}
     */
    public boolean isThing() {
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
    public boolean isNothing() {
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
    public boolean isPlainLiteral() {
        return remainder.equals("PlainLiteral") && Namespaces.RDF.inNamespace(namespace);
    }

    /**
     * Gets the fragment of the IRI.
     * 
     * @return The IRI fragment, or empty string if the IRI does not have a
     *         fragment
     * @deprecated use getNCName() - getFragment() does not return a real
     *             fragment. e.g., it does not allow / and () on it
     */
    @Deprecated
    public String getFragment() {
        return remainder;
    }

    /**
     * @return the remainder (coincident with NCName usually) for this IRI.
     */
    public Optional<String> getRemainder() {
        if (remainder.isEmpty()) {
            return emptyOptional();
        }
        return optional(remainder);
    }

    /**
     * Obtained this IRI surrounded by angled brackets.
     * 
     * @return This IRI surrounded by &lt; and &gt;
     */
    public String toQuotedString() {
        return ntriplesString();
    }

    /**
     * Creates an IRI from the specified String.
     * 
     * @param str
     *        The String that specifies the IRI
     * @return The IRI that has the specified string representation.
     */
    public static IRI create(String str) {
        checkNotNull(str, "str cannot be null");
        int index = XMLUtils.getNCNameSuffixIndex(str);
        if (index < 0) {
            // no ncname
            return new IRI(str, "");
        }
        return new IRI(str.substring(0, index), str.substring(index));
    }

    /**
     * Creates an IRI by concatenating two strings. The full IRI is an IRI that
     * contains the characters in prefix + suffix.
     * 
     * @param prefix
     *        The first string
     * @param suffix
     *        The second string
     * @return An IRI whose characters consist of prefix + suffix.
     * @since 3.3
     */
    public static IRI create(@Nullable String prefix, @Nullable String suffix) {
        if (prefix == null && suffix == null) {
            throw new IllegalArgumentException("prefix and suffix cannot both be null");
        }
        if (prefix == null) {
            return create(verifyNotNull(suffix));
        } else if (suffix == null) {
            // suffix set deliberately to null is used only in blank node
            // management
            // this is not great but blank nodes should be changed to not refer
            // to IRIs at all
            // XXX address blank node issues with iris
            return create(prefix);
        } else {
            int index = XMLUtils.getNCNameSuffixIndex(prefix);
            int test = XMLUtils.getNCNameSuffixIndex(suffix);
            if (index == -1 && test == 0) {
                // the prefix does not contain an ncname character and there is
                // no illegal character in the suffix
                // the split is therefore correct
                return new IRI(prefix, suffix);
            }
            // otherwise the split is wrong; we could obtain the right split by
            // using index and test, but it's just as easy to use the other
            // constructor
            return create(prefix + suffix);
        }
    }

    /**
     * @param file
     *        the file to create the IRI from
     * @return file.toURI() IRI
     */
    public static IRI create(File file) {
        checkNotNull(file, "file cannot be null");
        return new IRI(file.toURI());
    }

    /**
     * @param uri
     *        the uri to create the IRI from
     * @return the IRI wrapping the uri
     */
    public static IRI create(URI uri) {
        checkNotNull(uri, "uri cannot be null");
        return new IRI(uri);
    }

    /**
     * @param url
     *        the url to create the IRI from
     * @return an IRI wrapping url.toURI()
     * @throws OWLRuntimeException
     *         if the URL is ill formed
     */
    public static IRI create(URL url) {
        checkNotNull(url, "url cannot be null");
        try {
            return new IRI(url.toURI());
        } catch (URISyntaxException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * Gets an auto-generated ontology document IRI.
     * 
     * @return An auto-generated ontology document IRI. The IRI has the form
     *         {@code owlapi:ontologyNNNNNNNNNNN}
     */
    public static IRI generateDocumentIRI() {
        return getNextDocumentIRI("owlapi:ontology");
    }

    /**
     * @param prefix
     *        prefix for result
     * @return a fresh IRI
     */
    public static IRI getNextDocumentIRI(String prefix) {
        return IRI.create(prefix + COUNTER.incrementAndGet());
    }

    private static final AtomicLong COUNTER = new AtomicLong(System.nanoTime());
    // Impl - All constructors are private - factory methods are used for
    // public creation
    private final @Nonnull String remainder;
    private final @Nonnull String namespace;

    /**
     * Constructs an IRI which is built from the concatenation of the specified
     * prefix and suffix.
     * 
     * @param prefix
     *        The prefix.
     * @param suffix
     *        The suffix.
     */
    protected IRI(String prefix, @Nullable String suffix) {
        namespace = prefix.intern();
        remainder = suffix == null ? "" : suffix;
    }

    /**
     * @param suffix
     *        suffix to turn to optional. Empty string is the same as null
     * @return optional value for remainder
     */
    protected Optional<String> asOptional(@Nullable String suffix) {
        if (suffix == null) {
            return emptyOptional();
        } else if (suffix.isEmpty()) {
            return emptyOptional();
        }
        return optional(suffix);
    }

    protected IRI(String s) {
        this(XMLUtils.getNCNamePrefix(s), XMLUtils.getNCNameSuffix(s));
    }

    protected IRI(URI uri) {
        this(checkNotNull(uri, "uri cannot be null").toString());
    }

    @Override
    public int length() {
        return namespace.length() + remainder.length();
    }

    @Override
    public char charAt(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index < namespace.length()) {
            return namespace.charAt(index);
        }
        return remainder.charAt(index - namespace.length());
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(remainder);
        return sb.subSequence(start, end);
    }

    /**
     * @param prefix
     *        prefix to use for replacing the IRI namespace
     * @return prefix plus IRI ncname
     */
    public String prefixedBy(String prefix) {
        checkNotNull(prefix, "prefix cannot be null");
        if (remainder.isEmpty()) {
            return prefix;
        }
        return prefix + remainder;
    }

    @Override
    public String getShortForm() {
        if (!remainder.isEmpty()) {
            return remainder;
        }
        int lastSlashIndex = namespace.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex != namespace.length() - 1) {
            return namespace.substring(lastSlashIndex + 1);
        }
        return toQuotedString();
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(OWLAnnotationSubjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <E> E accept(OWLAnnotationSubjectVisitorEx<E> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return false;
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        checkNotNull(o);
        assert o != null;
        if (o == this || equals(o)) {
            return 0;
        }
        if (!(o instanceof IRI)) {
            return -1;
        }
        IRI other = (IRI) o;
        int diff = namespace.compareTo(other.namespace);
        if (diff != 0) {
            return diff;
        }
        return remainder.compareTo(other.remainder);
    }

    @Override
    public String toString() {
        return getIRIString();
    }

    @Override
    public int hashCode() {
        return namespace.hashCode() + remainder.hashCode();
    }

    @Override
    public void accept(OWLAnnotationValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<IRI> asIRI() {
        return optional(this);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof IRI) {
            IRI other = (IRI) obj;
            return remainder.equals(other.remainder) && other.namespace.equals(namespace);
        }
        // Commons RDF IRI equals() contract
        if (obj instanceof org.apache.commons.rdf.api.IRI) {
            org.apache.commons.rdf.api.IRI iri = (org.apache.commons.rdf.api.IRI) obj;
            return ntriplesString().equals(iri.ntriplesString());
        }
        return false;
    }

    @Override
    public String ntriplesString() {
        return '<' + namespace + remainder + '>';
    }

    @Override
    public String getIRIString() {
        if (remainder.isEmpty()) {
            return namespace;
        }
        return namespace + remainder;
    }
}
