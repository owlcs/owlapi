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
package org.semanticweb.owlapi6.impl;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.documents.ToStringRenderer;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.PrefixManager;

/**
 * Represents International Resource Identifiers.
 *
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 6.0.0
 */
public class IRIImpl implements IRI {

    private final String remainder;
    private final String namespace;
    private final int hash;

    /**
     * Constructs an IRI which is built from the concatenation of the specified
     * prefix and suffix.
     *
     * @param prefix
     *        The prefix.
     * @param suffix
     *        The suffix.
     */
    protected IRIImpl(String prefix, @Nullable String suffix) {
        namespace = prefix;
        remainder = suffix == null ? "" : suffix;
        hash = namespace.hashCode() + remainder.hashCode();
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getFragment() {
        return remainder;
    }

    @Override
    public int length() {
        return namespace.length() + remainder.length();
    }

    @Override
    public int compareTo(@Nullable OWLObject obj) {
        OWLObject o = verifyNotNull(obj);
        if (o == this) {
            return 0;
        }
        if (!o.isIRI()) {
            return -1;
        }
        if (o instanceof IRIImpl) {
            IRIImpl other = (IRIImpl) o;
            int diff = namespace.compareTo(other.namespace);
            if (diff != 0) {
                return diff;
            }
            return remainder.compareTo(other.remainder);
        }
        IRI other = (IRI) o;
        int diff = getNamespace().compareTo(other.getNamespace());
        if (diff != 0) {
            return diff;
        }
        return getFragment().compareTo(other.getFragment());
    }

    @Override
    public String toString() {
        return getIRIString();
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof IRIImpl) {
            IRIImpl other = (IRIImpl) obj;
            return remainder.equals(other.remainder) && other.namespace.equals(namespace);
        }
        // Commons RDF IRI equals() contract
        if (obj instanceof org.apache.commons.rdf.api.IRI) {
            org.apache.commons.rdf.api.IRI iri = (org.apache.commons.rdf.api.IRI) obj;
            return ntriplesString().equals(iri.ntriplesString());
        }
        if (obj instanceof IRI) {
            IRI other = (IRI) obj;
            return getFragment().equals(other.getFragment()) && other.getNamespace().equals(getNamespace());
        }
        return false;
    }

    @Override
    public String toSyntax(OWLDocumentFormat format) {
        return ToStringRenderer.getInstance(format).render(this);
    }

    @Override
    public String toSyntax(OWLDocumentFormat format, PrefixManager pm) {
        return ToStringRenderer.getInstance(format, pm).render(this);
    }

    @Override
    public String toFunctionalSyntax(PrefixManager pm) {
        return toSyntax(new FunctionalSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toManchesterSyntax(PrefixManager pm) {
        return toSyntax(new ManchesterSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toFunctionalSyntax() {
        return toSyntax(new FunctionalSyntaxDocumentFormat());
    }

    @Override
    public String toManchesterSyntax() {
        return toSyntax(new ManchesterSyntaxDocumentFormat());
    }
}
