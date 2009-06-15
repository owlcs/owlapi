package org.semanticweb.owl.model;

import org.semanticweb.owl.vocab.Namespaces;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;/*
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
public abstract class IRI implements OWLAnnotationSubject, OWLAnnotationValue {

    /**
     * Obtains this IRI as a URI.  Note that Java URIs handle unicode characters,
     * so there is no loss during this translation.
     * @return The URI
     */
    public abstract URI toURI();


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


    private static class IRIImpl extends IRI {

        private URI uri;

        public IRIImpl(URI uri) {
            this.uri = uri;
        }

        public URI toURI() {
            return uri;
        }

        /**
         * Gets the fragment of the IRI.
         * @return The IRI fragment, or <code>null</code> if the IRI does not have a fragment
         */
        public String getFragment() {
            return uri.getFragment();
        }

        public boolean isReservedVocabularyWithSpecialTreatment() {
            return false;
        }

        public boolean isNothing() {
            return uri.equals(OWLRDFVocabulary.OWL_NOTHING.getURI());
        }

        public boolean isReservedVocabulary() {
            return uri.toString().startsWith(Namespaces.OWL.toString()) || uri.toString().startsWith(Namespaces.RDF.toString()) || uri.toString().startsWith(Namespaces.RDFS.toString()) || uri.toString().startsWith(Namespaces.XML.toString());
        }

        public boolean isThing() {
            return uri.equals(OWLRDFVocabulary.OWL_THING.getURI());
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

        public int compareTo(OWLObject o) {
            if (!(o instanceof IRI)) {
                return -1;
            }
            IRI other = (IRI) o;
            return uri.compareTo(other.toURI());
        }

        public String toString() {
            return uri.toString();
        }

        public String toQuotedString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<");
            sb.append(uri);
            sb.append(">");

            return sb.toString();
        }

        public int hashCode() {
            return uri.hashCode();
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
            if (!(obj instanceof IRI)) {
                return false;
            }
            IRI other = (IRI) obj;
            return uri.equals(other.toURI());
        }
    }
}
