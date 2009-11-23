package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 14-Jan-2009
 * Represents International Resource Identifiers
 */
public abstract class IRI implements OWLAnnotationSubject, OWLAnnotationValue, SWRLPredicate {

    /**
     * Obtains this IRI as a URI.  Note that Java URIs handle unicode characters,
     * so there is no loss during this translation.
     * @return The URI
     */
    public abstract URI toURI();

    /**
     * Determines if this IRI is absolute
     * @return <code>true</code> if this IRI is absolute or <code>false</code> if this IRI is not absolute
     */
    public abstract boolean isAbsolute();

    public abstract IRI resolve(String s);


    /**
     * Determines if this IRI is in the reserved vocabulary.  An IRI is in the reserved vocabulary if it starts with
     * <http://www.w3.org/1999/02/22-rdf-syntax-ns#> or <http://www.w3.org/2000/01/rdf-schema#>
     * or <http://www.w3.org/2001/XMLSchema#> or <http://www.w3.org/2002/07/owl#>
     * @return <code>true</code> if the IRI is in the reserved vocabulary, otherwise <code>false</code>.
     */
    public abstract boolean isReservedVocabulary();


    public abstract boolean isThing();


    public abstract boolean isNothing();

    /**
     * Gets the fragment of the IRI.
     * @return The IRI fragment, or <code>null</code> if the IRI does not have a fragment
     */
    public abstract String getFragment();

    /**
     * Obtained this IRI surrounded by angled brackets
     * @return This IRI surrounded by &lt; and &gt;
     */
    public abstract String toQuotedString();

    /**
     * Creates an IRI from the specified String.
     * @param str The String that specifies the IRI
     * @return The IRI that has the specified string representation.
     */
    public static IRI create(String str) {
        if (str == null) {
            throw new NullPointerException("String must not be null");
        }
        return new IRIImpl(URI.create(str));
    }

    public static IRI create(URI uri) {
        if (uri == null) {
            throw new NullPointerException("URI must not be null");
        }
        return new IRIImpl(uri);
    }

    public static IRI create(URL url) throws URISyntaxException {
        if (url == null) {
            throw new NullPointerException("URL must not be null");
        }
        return new IRIImpl(url.toURI());
    }

    private static Map<String, String> prefixCache = new HashMap<String, String>();


    private static class IRIImpl extends IRI {

        private String fragment;

        private String prefix;

        private int hashCode = 0;

        private URI cachedURI = null;


        public IRIImpl(String s) {
            int fragmentSeparatorIndex = s.lastIndexOf('#');
            if(fragmentSeparatorIndex != -1 && fragmentSeparatorIndex < s.length()) {
                fragment = s.substring(fragmentSeparatorIndex + 1);
                prefix = s.substring(0, fragmentSeparatorIndex + 1);
            }
            else {
                int pathSeparatorIndex = s.lastIndexOf('/');
                if(pathSeparatorIndex != -1 && pathSeparatorIndex < s.length()) {
                    fragment = s.substring(pathSeparatorIndex + 1);
                    prefix = s.substring(0, pathSeparatorIndex + 1);
                }
                else {
                    fragment = null;
                    prefix = s;
                }
            }
            String cached = prefixCache.get(prefix);
            if(cached == null) {
                prefixCache.put(prefix, prefix);
            }
            else {
                prefix = cached;
            }
        }

        public IRIImpl(URI uri) {
            this(uri.toString());
        }

        public URI toURI() {
            if (cachedURI == null) {
                if (fragment != null) {
                    cachedURI = URI.create(prefix + fragment);
                }
                else {
                    cachedURI = URI.create(prefix);
                }
            }
            return cachedURI;
        }

        public IRI resolve(String s) {
            return IRI.create(toURI().resolve(s));
        }

        /**
         * Determines if this IRI is absolute
         * @return <code>true</code> if this IRI is absolute or <code>false</code> if this IRI is not absolute
         */
        public boolean isAbsolute() {
            int colonIndex = prefix.indexOf(':');
            if(colonIndex == -1) {
                return false;
            }
            for(int i = 0; i < colonIndex; i++) {
                char ch = prefix.charAt(i);
                if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '.' && ch != '+' && ch != '-') {
                    return false;
                }
            }
            return true;
        }

        /**
         * Gets the fragment of the IRI.
         * @return The IRI fragment, or <code>null</code> if the IRI does not have a fragment
         */
        public String getFragment() {
            if (prefix.charAt(prefix.length() - 1) == '#') {
                return fragment;
            }
            else {
                return null;
            }
        }
        
        public boolean isNothing() {
            return this.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        }

        public boolean isReservedVocabulary() {
            return prefix.startsWith(Namespaces.OWL.toString()) || prefix.startsWith(Namespaces.RDF.toString()) || prefix.startsWith(Namespaces.RDFS.toString()) || prefix.startsWith(Namespaces.XSD.toString());
        }

        public boolean isThing() {
            return fragment != null && fragment.equals("Thing") && prefix.equals(Namespaces.OWL.toString());
        }

        public void accept(OWLObjectVisitor visitor) {
            visitor.visit(this);
        }

        public <O> O accept(OWLObjectVisitorEx<O> visitor) {
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
        
        public Set<OWLDatatype> getDatatypesInSignature() {
            return Collections.emptySet();
        }

        public int compareTo(OWLObject o) {
            if(o == this) {
                return 0;
            }
            if (!(o instanceof IRIImpl)) {
                return -1;
            }
            IRIImpl other = (IRIImpl) o;
            int diff = prefix.compareTo(other.prefix);
            if(diff != 0) {
                return diff;
            }
            String otherFragment = other.fragment;
            if(fragment == null) {
                if(otherFragment == null) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
            else {
                if(otherFragment == null) {
                    return 1;
                }
                else {
                    return fragment.compareTo(otherFragment);
                }
            }
        }

        public String toString() {
            if(fragment != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(prefix);
                sb.append(fragment);
                return sb.toString();
            }
            else {
                return prefix;
            }
        }

        public String toQuotedString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<");
            sb.append(prefix);
            if(fragment != null) {
                sb.append(fragment);
            }
            sb.append(">");

            return sb.toString();
        }

        public int hashCode() {
            if(hashCode == 0) {
                hashCode = prefix.hashCode() + (fragment != null ? fragment.hashCode() : 0);
            }
            return hashCode;
        }

        public void accept(OWLAnnotationValueVisitor visitor) {
            visitor.visit(this);
        }

        public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
            return visitor.visit(this);
        }

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
            String otherFragment = other.fragment;
            if(fragment == null) {
                if(otherFragment == null) {
                    return prefix.equals(other.prefix);
                }
                else {
                    return false;
                }
            }
            else {
                if(otherFragment == null) {
                    return false;
                }
                else {
                    return fragment.equals(otherFragment) && other.prefix.equals(this.prefix);
                }
            }
        }
    }
}
