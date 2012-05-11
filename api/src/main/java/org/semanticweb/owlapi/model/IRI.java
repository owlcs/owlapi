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
package org.semanticweb.owlapi.model;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.util.WeakCache;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 14-Jan-2009 Represents International Resource Identifiers
 */
public abstract class IRI implements OWLAnnotationSubject, OWLAnnotationValue, SWRLPredicate, CharSequence {

    /**
     * Obtains this IRI as a URI. Note that Java URIs handle unicode characters,
     * so there is no loss during this translation.
     * @return The URI
     */
    public abstract URI toURI();

    /**
     * Determines if this IRI is absolute
     * @return <code>true</code> if this IRI is absolute or <code>false</code>
     *         if this IRI is not absolute
     */
    public abstract boolean isAbsolute();

    /**
     * @return the IRI scheme, e.g., http, urn... can be null
     */
    public abstract String getScheme();

    /**
     * @return the prefix. Can be null.
     */
    public abstract String getStart();

    /**
     * @param s the IRI stirng to be resolved
     * @return s resolved against this IRI (with the URI::resolve() method,
     *         unless this IRI is opaque)
     */
    public abstract IRI resolve(String s);

    /**
     * Determines if this IRI is in the reserved vocabulary. An IRI is in the
     * reserved vocabulary if it starts with
     * &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt; or
     * &lt;http://www.w3.org/2000/01/rdf-schema#&gt; or
     * &lt;http://www.w3.org/2001/XMLSchema#&gt; or
     * &lt;http://www.w3.org/2002/07/owl#&gt;
     * @return <code>true</code> if the IRI is in the reserved vocabulary,
     *         otherwise <code>false</code>.
     */
    public abstract boolean isReservedVocabulary();

    /**
     * Determines if this IRI is equal to the IRI that <code>owl:Thing</code> is
     * named with
     * @return <code>true</code> if this IRI is equal to
     *         &lt;http://www.w3.org/2002/07/owl#Thing&gt; and otherwise
     *         <code>false</code>
     */
    public abstract boolean isThing();

    /**
     * Determines if this IRI is equal to the IRI that <code>owl:Nothing</code>
     * is named with
     * @return <code>true</code> if this IRI is equal to
     *         &lt;http://www.w3.org/2002/07/owl#Nothing&gt; and otherwise
     *         <code>false</code>
     */
    public abstract boolean isNothing();

    /**
     * Determines if this IRI is equal to the IRI that is named
     * <code>rdf:PlainLiteral</code>
     * @return <code>true</code> if this IRI is equal to
     *         &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral&gt;,
     *         otherwise <code>false</code>
     */
    public abstract boolean isPlainLiteral();

    /**
     * Gets the fragment of the IRI.
     * @return The IRI fragment, or <code>null</code> if the IRI does not have a
     *         fragment
     */
    public abstract String getFragment();

    /**
     * Obtained this IRI surrounded by angled brackets
     * @return This IRI surrounded by &lt; and &gt;
     */
    public abstract String toQuotedString();
    
    /**
     * Creates an IRI from the specified String.
     * @param str The String that specifies the IRI. Cannot be null.
     * @return The IRI that has the specified string representation.
     */
    public static IRI create(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String must not be null");
        }
        return new IRIImpl(str);
    }

    /**
     * Creates an IRI by concatenating two strings.  The full IRI is an IRI that contains the characters in
     * prefix + suffix.
     * @param prefix The first string.  May be <code>null</code>.
     * @param suffix The second string. May be <code>null</code>.
     * @return An IRI whose characters consist of prefix + suffix.
     * @since 3.3
     */
    public static IRI create(String prefix, String suffix) {
        return new IRIImpl(prefix, suffix);
    }

    /**
     * @param file the file to create the IRI from. Cannot be null.
     * @return file.toURI() IRI
     */
    public static IRI create(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }
        return new IRIImpl(file.toURI());
    }

    /**
     * @param uri the uri to create the IRI from. Cannot be null
     * @return the IRI wrapping the uri
     */
    public static IRI create(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }
        return new IRIImpl(uri);
    }

    /**
     * @param url the url to create the IRI from. Cannot be null.
     * @return an IRI wraopping url.toURI()
     * @throws URISyntaxException if the URL is ill formed
     */
    public static IRI create(URL url) throws URISyntaxException {
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        return new IRIImpl(url.toURI());
    }

    /**
     * Gets an auto-generated ontology document IRI.
     * @return An auto-generated ontology document IRI. The IRI has the form
     *         <code>owlapi:ontologyTIMESTAMP</code>
     */
    public static IRI generateDocumentIRI() {
        return create("owlapi:ontology" + System.nanoTime());
    }

    private static class IRIImpl extends IRI implements Serializable {

        /**
		 *
		 */
		private static final long serialVersionUID = -6496701874123212758L;

		private static WeakCache<String> prefixCache = new WeakCache<String>();

        private final String remainder;

        private final String prefix;

        private int hashCode = 0;

        /**
         * Constructs an IRI which is built from the concatenation of the specified prefix and suffix.
         * @param prefix The prefix.
         * @param fragment The suffix.
         */
        public IRIImpl(String prefix, String fragment) {
            this.prefix = prefixCache.cache(prefix);
            this.remainder = fragment;
        }
        
        public IRIImpl(String s) {
            int fragmentSeparatorIndex = s.lastIndexOf('#');
            if (fragmentSeparatorIndex != -1 && fragmentSeparatorIndex < s.length()) {
                remainder = s.substring(fragmentSeparatorIndex + 1);
                prefix = prefixCache.cache(s.substring(0, fragmentSeparatorIndex + 1));
            }
            else {
                int pathSeparatorIndex = s.lastIndexOf('/');
                if (pathSeparatorIndex != -1 && pathSeparatorIndex < s.length()) {
                    remainder = s.substring(pathSeparatorIndex + 1);
                    prefix = prefixCache.cache(s.substring(0, pathSeparatorIndex + 1));
                }
                else {
                    remainder = null;
                    prefix = prefixCache.cache(s);
                }
            }
        }

        public IRIImpl(URI uri) {
            this(uri.toString());
        }

        /**
         * Returns the length of this character sequence.  The length is the number
         * of 16-bit <code>char</code>s in the sequence.</p>
         * @return the number of <code>char</code>s in this sequence
         */
        public int length() {
            return prefix.length() + (remainder==null?0:remainder.length());
        }

        /**
         * Returns the <code>char</code> value at the specified index.  An index ranges from zero
         * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
         * index zero, the next at index one, and so on, as for array
         * indexing. </p>
         * <p>If the <code>char</code> value specified by the index is a
         * <a href="Character.html#unicode">surrogate</a>, the surrogate
         * value is returned.
         * @param index the index of the <code>char</code> value to be returned
         * @return the specified <code>char</code> value
         * @throws IndexOutOfBoundsException if the <tt>index</tt> argument is negative or not less than
         *                                   <tt>length()</tt>
         */
        public char charAt(int index) {
            if(index < 0) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            if(index >= length()) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            if(index < prefix.length()) {
                return prefix.charAt(index);
            }
            return remainder.charAt(index - prefix.length());
        }

        /**
         * Returns a new <code>CharSequence</code> that is a subsequence of this sequence.
         * The subsequence starts with the <code>char</code> value at the specified index and
         * ends with the <code>char</code> value at index <tt>end - 1</tt>.  The length
         * (in <code>char</code>s) of the
         * returned sequence is <tt>end - start</tt>, so if <tt>start == end</tt>
         * then an empty sequence is returned. </p>
         * @param start the start index, inclusive
         * @param end the end index, exclusive
         * @return the specified subsequence
         * @throws IndexOutOfBoundsException if <tt>start</tt> or <tt>end</tt> are negative,
         *                                   if <tt>end</tt> is greater than <tt>length()</tt>,
         *                                   or if <tt>start</tt> is greater than <tt>end</tt>
         */
        public CharSequence subSequence(int start, int end) {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            sb.append(remainder);
            return sb.subSequence(start, end);
        }

        @Override
        public URI toURI() {
            if (remainder != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(prefix);
                sb.append(remainder);
                return URI.create(sb.toString());
            }
            else {
                return URI.create(prefix);
            }
        }

        @Override
        public IRI resolve(String s) {
            // shortcut: checking absolute and opaque here saves the creation of an extra URI object
            URI uri = URI.create(s);
            if (uri.isAbsolute() || uri.isOpaque()) {
                return IRI.create(uri.toString());
            }
            return IRI.create(toURI().resolve(uri).toString());
        }

        @Override
        public String getScheme() {
            int colonIndex = prefix.indexOf(':');
            if (colonIndex == -1) {
                return null;
            }
            return prefix.substring(0, colonIndex);
        }

        /**
         * Determines if this IRI is absolute
         * @return <code>true</code> if this IRI is absolute or
         *         <code>false</code> if this IRI is not absolute
         */
        @Override
        public boolean isAbsolute() {
            int colonIndex = prefix.indexOf(':');
            if (colonIndex == -1) {
                return false;
            }
            for (int i = 0; i < colonIndex; i++) {
                char ch = prefix.charAt(i);
                if (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '.' && ch != '+' && ch != '-') {
                    return false;
                }
            }
            return true;
        }

        /**
         * Gets the fragment of the IRI.
         * @return The IRI fragment, or <code>null</code> if the IRI does not
         *         have a fragment
         */
        @Override
        public String getFragment() {
            return remainder;
        }

        @Override
        public String getStart() {
            return prefix;
        }

        @Override
        public boolean isNothing() {
            return this.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        }

        @Override
        public boolean isReservedVocabulary() {
            return prefix.startsWith(Namespaces.OWL.toString()) || prefix.startsWith(Namespaces.RDF.toString()) || prefix.startsWith(Namespaces.RDFS.toString()) || prefix.startsWith(Namespaces.XSD.toString());
        }

        @Override
        public boolean isThing() {
            return remainder != null && remainder.equals("Thing") && prefix.equals(Namespaces.OWL.toString());
        }

        @Override
        public boolean isPlainLiteral() {
            return remainder != null && remainder.equals("PlainLiteral") && prefix.equals(Namespaces.RDF.toString());
        }

        public void accept(OWLObjectVisitor visitor) {
            visitor.visit(this);
        }

        public <O> O accept(OWLObjectVisitorEx<O> visitor) {
            return visitor.visit(this);
        }

        public void accept(OWLAnnotationSubjectVisitor visitor) {
            visitor.visit(this);
        }

        public <E> E accept(OWLAnnotationSubjectVisitorEx<E> visitor) {
            return visitor.visit(this);
        }

        public Set<OWLClass> getClassesInSignature() {
            return Collections.emptySet();
        }

        public Set<OWLDataProperty> getDataPropertiesInSignature() {
            return Collections.emptySet();
        }

        public Set<OWLNamedIndividual> getIndividualsInSignature() {
            return Collections.emptySet();
        }

        public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
            return Collections.emptySet();
        }

        public Set<OWLEntity> getSignature() {
            return Collections.emptySet();
        }

        public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
            return Collections.emptySet();
        }

        public Set<OWLDatatype> getDatatypesInSignature() {
            return Collections.emptySet();
        }

        public Set<OWLClassExpression> getNestedClassExpressions() {
            return Collections.emptySet();
        }

        public int compareTo(OWLObject o) {
            if (o == this) {
                return 0;
            }
            if (!(o instanceof IRIImpl)) {
                return -1;
            }
            IRIImpl other = (IRIImpl) o;
            int diff = prefix.compareTo(other.prefix);
            if (diff != 0) {
                return diff;
            }
            String otherRemainder = other.remainder;
            if (remainder == null) {
                if (otherRemainder == null) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
            else {
                if (otherRemainder == null) {
                    return 1;
                }
                else {
                    return remainder.compareTo(otherRemainder);
                }
            }
        }

        @Override
        public String toString() {
            if (remainder != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(prefix);
                sb.append(remainder);
                return sb.toString();
            }
            else {
                return prefix;
            }
        }

        @Override
        public String toQuotedString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<");
            sb.append(prefix);
            if (remainder != null) {
                sb.append(remainder);
            }
            sb.append(">");
            return sb.toString();
        }

        @Override
        public int hashCode() {
            if (hashCode == 0) {
                hashCode = prefix.hashCode() + (remainder != null ? remainder.hashCode() : 0);
            }
            return hashCode;
        }

        public void accept(OWLAnnotationValueVisitor visitor) {
            visitor.visit(this);
        }

        public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
            return visitor.visit(this);
        }

        public boolean isTopEntity() {
            return false;
        }

        public boolean isBottomEntity() {
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof IRIImpl)) {
                return false;
            }
            IRIImpl other = (IRIImpl) obj;
            String otherRemainder = other.remainder;
            if (remainder == null) {
                return otherRemainder == null && prefix.equals(other.prefix);
            }
            else {
                return otherRemainder != null && remainder.equals(otherRemainder) && other.prefix.equals(this.prefix);
            }
        }
    }
}
