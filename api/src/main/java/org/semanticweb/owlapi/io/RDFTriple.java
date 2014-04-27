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
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2
 */
public class RDFTriple implements Serializable, Comparable<RDFTriple> {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final RDFResource subject;
    @Nonnull
    private final RDFResourceIRI predicate;
    @Nonnull
    private final RDFNode object;

    /**
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    public RDFTriple(@Nonnull RDFResource subject,
            @Nonnull RDFResourceIRI predicate, @Nonnull RDFNode object) {
        this.subject = checkNotNull(subject, "subject cannot be null");
        this.predicate = checkNotNull(predicate, "predicate cannot be null");
        this.object = checkNotNull(object, "object cannot be null");
    }

    /**
     * @param subject
     *        the subject
     * @param subjectAnon
     *        whether the subject is anonymous
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     * @param objectAnon
     *        whether the object is anonymous
     */
    public RDFTriple(@Nonnull IRI subject, boolean subjectAnon,
            @Nonnull IRI predicate, @Nonnull IRI object, boolean objectAnon) {
        this(getResource(subject, subjectAnon),
        // Predicate is not allowed to be anonymous
                new RDFResourceIRI(predicate), getResource(object, objectAnon));
    }

    @Nonnull
    private static RDFResource getResource(@Nonnull IRI iri, boolean anon) {
        if (anon) {
            return new RDFResourceBlankNode(iri);
        }
        return new RDFResourceIRI(iri);
    }

    /**
     * @param subject
     *        the subject
     * @param subjectAnon
     *        whether the subject is anonymous
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    public RDFTriple(@Nonnull IRI subject, boolean subjectAnon,
            @Nonnull IRI predicate, @Nonnull OWLLiteral object) {
        this(getResource(subject, subjectAnon), new RDFResourceIRI(predicate),
                new RDFLiteral(object));
    }

    /** @return the subject */
    @Nonnull
    public RDFResource getSubject() {
        return subject;
    }

    /** @return the predicate */
    @Nonnull
    public RDFResourceIRI getPredicate() {
        return predicate;
    }

    /** @return the object */
    @Nonnull
    public RDFNode getObject() {
        return object;
    }

    @Override
    public int hashCode() {
        return subject.hashCode() * 37 + predicate.hashCode() * 17
                + object.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RDFTriple)) {
            return false;
        }
        RDFTriple other = (RDFTriple) o;
        return subject.equals(other.subject)
                && predicate.equals(other.predicate)
                && object.equals(other.object);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject.toString());
        sb.append(" ");
        sb.append(predicate.toString());
        sb.append(" ");
        sb.append(object.toString());
        sb.append(".");
        return sb.toString();
    }

    private static final List<IRI> orderedURIs = Arrays.asList(
            RDF_TYPE.getIRI(), RDFS_LABEL.getIRI(),
            OWL_EQUIVALENT_CLASS.getIRI(), RDFS_SUBCLASS_OF.getIRI(),
            OWL_DISJOINT_WITH.getIRI(), OWL_ON_PROPERTY.getIRI(),
            OWL_DATA_RANGE.getIRI(), OWL_ON_CLASS.getIRI());

    private static int getIndex(IRI iri) {
        int index = orderedURIs.indexOf(iri);
        if (index == -1) {
            index = orderedURIs.size();
        }
        return index;
    }

    @Override
    public int compareTo(RDFTriple b) {
        // compare by predicate, then subject, then object
        int diff = getIndex(predicate.getIRI())
                - getIndex(b.predicate.getIRI());
        if (diff == 0) {
            diff = subject.compareTo(b.subject);
        }
        if (diff == 0) {
            diff = object.compareTo(b.object);
        }
        return diff;
    }
}
