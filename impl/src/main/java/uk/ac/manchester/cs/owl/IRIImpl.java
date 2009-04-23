package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.Namespaces;

import java.net.URI;
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
 */
public class IRIImpl implements IRI {

    private URI uri;

    public IRIImpl(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI must not be null");
        }
        this.uri = uri;
    }

    public URI toURI() {
        return uri;
    }

    public boolean isBuiltIn() {
        return OWLRDFVocabulary.BUILT_IN_VOCABULARY.contains(uri);
    }


    public boolean isReservedVocabularyWithSpecialTreatment() {
        return false;
    }

    public boolean isNothing() {
        return uri.equals(OWLRDFVocabulary.OWL_NOTHING.getURI());
    }

    public boolean isReservedVocabulary() {
        return uri.toString().startsWith(Namespaces.OWL.toString()) ||
                uri.toString().startsWith(Namespaces.RDF.toString()) ||
                uri.toString().startsWith(Namespaces.RDFS.toString()) ||
                uri.toString().startsWith(Namespaces.XML.toString());
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
