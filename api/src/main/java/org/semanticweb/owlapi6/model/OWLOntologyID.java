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

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.Nullable;

/**
 * An object that identifies an ontology. Since OWL 2, ontologies do not have to have an ontology
 * IRI, or if they have an ontology IRI then they can optionally also have a version IRI. Instances
 * of this OWLOntologyID class bundle identifying information of an ontology together. If an
 * ontology doesn't have an ontology IRI then we say that it is "anonymous".
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public interface OWLOntologyID extends Comparable<OWLOntologyID>, Serializable, IsAnonymous {

    /**
     * @param iri the iri to check
     * @return true if the input iri matches the ontology iri or the version iri
     */
    default boolean match(IRI iri) {
        return matchOntology(iri) || matchVersion(iri);
    }

    /**
     * @param iri the iri to check
     * @return true if the input iri matches the version iri
     */
    default boolean matchVersion(IRI iri) {
        return iri.equals(getVersionIRI().orElse(null));
    }

    /**
     * @param iri the iri to check
     * @return true if the input iri matches the default document iri
     */
    default boolean matchDocument(IRI iri) {
        return iri.equals(getDefaultDocumentIRI().orElse(null));
    }

    /**
     * @param iri the iri to check
     * @return true if the input iri matches the ontology iri
     */
    default boolean matchOntology(IRI iri) {
        return iri.equals(getOntologyIRI().orElse(null));
    }

    /**
     * @param id the id to check
     * @return true if the input id has the same ontology iri
     */
    default boolean match(OWLOntologyID id) {
        return getOntologyIRI().equals(id.getOntologyIRI());
    }

    /**
     * Determines if this is a valid OWL 2 DL ontology ID. To be a valid OWL 2 DL ID, the ontology
     * IRI and version IRI must not be reserved vocabulary.
     *
     * @return {@code true} if this is a valid OWL 2 DL ontology ID, otherwise {@code false}
     * @see org.semanticweb.owlapi6.model.IRI#isReservedVocabulary()
     */
    default boolean isOWL2DLOntologyID() {
        Boolean o = getOntologyIRI().map(IRI::isReservedVocabulary).orElse(Boolean.FALSE);
        Boolean v = getVersionIRI().map(IRI::isReservedVocabulary).orElse(Boolean.FALSE);
        return !(o.booleanValue() || v.booleanValue());
    }

    @Override
    default int compareTo(@Nullable OWLOntologyID o) {
        return toString().compareTo(verifyNotNull(o).toString());
    }

    /**
     * Gets the ontology IRI. If the ontology is anonymous, it will return an absent Optional (i.e.,
     * getOntologyIRI().isPresent() will return false.
     *
     * @return Optional of the ontology IRI, or Optional.absent if there is no ontology IRI.
     */
    Optional<IRI> getOntologyIRI();

    /**
     * Gets the version IRI.
     *
     * @return an optional of the version IRI, or Optional.absent if there is no version IRI.
     */
    Optional<IRI> getVersionIRI();

    /**
     * @return The internal id for anonymous ontologies. The value is not an IRI.
     */
    Optional<String> getInternalID();

    /**
     * Gets the IRI which is used as a default for the document that contain a representation of an
     * ontology with this ID. This will be the version IRI if there is an ontology IRI and version
     * IRI, else it will be the ontology IRI if there is an ontology IRI but no version IRI, else it
     * will be {@code null} if there is no ontology IRI. See
     * <a href="http://www.w3.org/TR/owl2-syntax/#Ontology_Documents">Ontology Documents</a> in the
     * OWL 2 Structural Specification.
     *
     * @return An Optional of the IRI that can be used as a default for an ontology document
     *         containing an ontology as identified by this ontology ID. Returns the default IRI or
     *         an Optional.absent.
     */
    default Optional<IRI> getDefaultDocumentIRI() {
        Optional<IRI> o = getOntologyIRI();
        Optional<IRI> v = getVersionIRI();
        if (o.isPresent()) {
            if (v.isPresent()) {
                return v;
            } else {
                return o;
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Determines if this ID names an ontology or whether it is an ID for an ontology without an
     * IRI. If the result of this method is true, getOntologyIRI() will return an Optional.absent.
     *
     * @return {@code true} if this ID is an ID for an ontology without an IRI, or {@code false} if
     *         this ID is an ID for an ontology with an IRI.
     */
    @Override
    default boolean isAnonymous() {
        return !getOntologyIRI().isPresent();
    }
}
