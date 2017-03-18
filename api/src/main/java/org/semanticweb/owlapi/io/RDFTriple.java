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
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_SOURCE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_TARGET;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DEPRECATED;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_WITH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_COMMENT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_IS_DEFINED_BY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUBCLASS_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.rdf.api.Triple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.2
 */
public class RDFTriple
    implements Serializable, Comparable<RDFTriple>, org.apache.commons.rdf.api.Triple {

    static final TObjectIntHashMap<IRI> specialPredicateRanks = initMap();
    private final RDFResource subject;
    private final RDFResourceIRI predicate;
    private final RDFNode object;

    /**
     * @param subject the subject
     * @param predicate the predicate
     * @param object the object
     */
    public RDFTriple(RDFResource subject, RDFResourceIRI predicate, RDFNode object) {
        this.subject = checkNotNull(subject, "subject cannot be null");
        this.predicate = checkNotNull(predicate, "predicate cannot be null");
        this.object = checkNotNull(object, "object cannot be null");
    }

    /**
     * @param subject the subject
     * @param subjectAnon whether the subject is anonymous
     * @param subjectAxiom true if the subject is an axiom
     * @param predicate the predicate
     * @param object the object
     * @param objectAnon whether the object is anonymous
     * @param objectAxiom true if the object is an axiom
     */
    public RDFTriple(IRI subject, boolean subjectAnon, boolean subjectAxiom, IRI predicate,
        IRI object, boolean objectAnon, boolean objectAxiom) {
        this(getResource(subject, subjectAnon, subjectAxiom),
            // Predicate is not allowed to be anonymous
            new RDFResourceIRI(predicate), getResource(object, objectAnon, objectAxiom));
    }

    /**
     * @param subject the subject
     * @param subjectAnon whether the subject is anonymous
     * @param subjectAxiom true if the subject is an axiom
     * @param predicate the predicate
     * @param object the object
     */
    public RDFTriple(IRI subject, boolean subjectAnon, boolean subjectAxiom, IRI predicate,
        OWLLiteral object) {
        this(getResource(subject, subjectAnon, subjectAxiom), new RDFResourceIRI(predicate),
            new RDFLiteral(object));
    }

    private static RDFResource getResource(IRI iri, boolean anon, boolean axiom) {
        if (anon) {
            return new RDFResourceBlankNode(iri, true, true, axiom);
        }
        return new RDFResourceIRI(iri);
    }

    static TObjectIntHashMap<IRI> initMap() {
        TObjectIntHashMap<IRI> predicates = new TObjectIntHashMap<>();
        AtomicInteger nextId = new AtomicInteger(1);
        List<OWLRDFVocabulary> ORDERED_URIS = Arrays.asList(RDF_TYPE, RDFS_LABEL, OWL_DEPRECATED,
            RDFS_COMMENT, RDFS_IS_DEFINED_BY, RDF_FIRST, RDF_REST, OWL_EQUIVALENT_CLASS,
            OWL_EQUIVALENT_PROPERTY, RDFS_SUBCLASS_OF, RDFS_SUB_PROPERTY_OF, RDFS_DOMAIN,
            RDFS_RANGE, OWL_DISJOINT_WITH, OWL_ON_PROPERTY, OWL_DATA_RANGE, OWL_ON_CLASS,
            OWL_ANNOTATED_SOURCE, OWL_ANNOTATED_PROPERTY, OWL_ANNOTATED_TARGET);
        ORDERED_URIS.forEach(iri -> predicates.put(iri.getIRI(), nextId.getAndIncrement()));
        Stream.of(OWLRDFVocabulary.values())
            .forEach(iri -> predicates.putIfAbsent(iri.getIRI(), nextId.getAndIncrement()));
        return predicates;
    }

    private static int comparePredicates(RDFResourceIRI predicate, RDFResourceIRI otherPredicate) {
        IRI predicateIRI = predicate.getIRI();
        int specialPredicateRank = specialPredicateRanks.get(predicateIRI);
        IRI otherPredicateIRI = otherPredicate.getIRI();
        int otherSpecialPredicateRank = specialPredicateRanks.get(otherPredicateIRI);
        if (specialPredicateRank != specialPredicateRanks.getNoEntryValue()) {
            if (otherSpecialPredicateRank != specialPredicateRanks.getNoEntryValue()) {
                return Integer.compare(specialPredicateRank, otherSpecialPredicateRank);
            } else {
                return -1;
            }
        } else {
            if (otherSpecialPredicateRank != specialPredicateRanks.getNoEntryValue()) {
                return +1;
            } else {
                return predicateIRI.compareTo(otherPredicateIRI);
            }
        }
    }

    /**
     * @return true if subject and object are the same
     */
    public boolean isSubjectSameAsObject() {
        return subject.equals(object);
    }

    @Override
    public RDFResource getSubject() {
        return subject;
    }

    @Override
    public RDFResourceIRI getPredicate() {
        return predicate;
    }

    @Override
    public RDFNode getObject() {
        return object;
    }

    @Override
    public int hashCode() {
        return subject.hashCode() * 37 + predicate.hashCode() * 17 + object.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RDFTriple) {
            RDFTriple other = (RDFTriple) obj;
            return subject.equals(other.subject) && predicate.equals(other.predicate)
                && object.equals(other.object);
        }
        // Commons RDF Triple.equals() contract
        if (obj instanceof Triple) {
            // Note: This also works on RDFLiteral
            // but is slightly more expensive as it must call the
            // getter methods when accessing obj.
            //
            // To ensure future compatibility, the Commons RDF getter
            // methods are also called on this rather than using the fields.
            Triple triple = (Triple) obj;
            return getSubject().equals(triple.getSubject())
                && getPredicate().equals(triple.getPredicate())
                && getObject().equals(triple.getObject());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s.", subject, predicate, object);
    }

    @Override
    public int compareTo(@Nullable RDFTriple o) {
        checkNotNull(o);
        assert o != null;
        // compare by predicate, then subject, then object
        int diff = comparePredicates(predicate, o.predicate);
        if (diff == 0) {
            diff = subject.compareTo(o.subject);
        }
        if (diff == 0) {
            diff = object.compareTo(o.object);
        }
        return diff;
    }
}
