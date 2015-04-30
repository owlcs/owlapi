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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

/**
 * An object that identifies an ontology. Since OWL 2, ontologies do not have to
 * have an ontology IRI, or if they have an ontology IRI then they can
 * optionally also have a version IRI. Instances of this OWLOntologyID class
 * bundle identifying information of an ontology together. If an ontology
 * doesn't have an ontology IRI then we say that it is "anonymous".
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLOntologyID
    implements Comparable<OWLOntologyID>, Serializable, IsAnonymous {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private static final AtomicInteger COUNTER = new AtomicInteger();
    @Nonnull
    private static final String ANON_PREFIX = "Anonymous-";
    @Nonnull
    private transient Optional<String> internalID = emptyOptional();
    @Nonnull
    private transient Optional<IRI> ontologyIRI;
    @Nonnull
    private transient Optional<IRI> versionIRI;
    private int hashCode;

    private void readObject(ObjectInputStream stream)
        throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
        ontologyIRI = optional((IRI) stream.readObject());
        versionIRI = optional((IRI) stream.readObject());
        internalID = optional((String) stream.readObject());
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(ontologyIRI.orElse(null));
        stream.writeObject(versionIRI.orElse(null));
        stream.writeObject(internalID.orElse(null));
    }

    /**
     * Constructs an ontology identifier specifiying the ontology IRI and
     * version IRI. Equivalent to OWLOntologyID(Optional
     * 
     * @param iri
     *        The ontology IRI (may be {@code null})
     */
    public OWLOntologyID(IRI iri) {
        this(opt(iri), emptyOptional(IRI.class));
    }

    /**
     * Constructs an ontology identifier specifiying the ontology IRI and
     * version IRI.
     * 
     * @param iri
     *        The ontology IRI (may be {@code null})
     * @param versionIRI
     *        The version IRI (must be {@code null} if the ontologyIRI is null)
     */
    public OWLOntologyID(IRI iri, IRI versionIRI) {
        this(opt(iri), opt(versionIRI));
    }

    @Nonnull
    private static Optional<IRI> opt(IRI i) {
        if (NodeID.isAnonymousNodeIRI(i)) {
            return emptyOptional();
        }
        return optional(i);
    }

    /**
     * Replace an optional with a blank node iri with an absent optional.
     * 
     * @param i
     *        Optional to check
     * @return input optional if its iri is not a blank node iri, absent
     *         otherwise
     */
    @Nonnull
    private static Optional<IRI> opt(Optional<IRI> i) {
        if (NodeID.isAnonymousNodeIRI(i.orElse(null))) {
            return emptyOptional();
        }
        return i;
    }

    /**
     * @return true if the input iri matches the ontology iri or the version iri
     * @param iri
     *        the iri to check
     */
    public boolean match(@Nonnull IRI iri) {
        return matchOntology(iri) || matchVersion(iri);
    }

    /**
     * @return true if the input iri matches the version iri
     * @param iri
     *        the iri to check
     */
    public boolean matchVersion(@Nonnull IRI iri) {
        return iri.equals(versionIRI.orElse(null));
    }

    /**
     * @return true if the input iri matches the default document iri
     * @param iri
     *        the iri to check
     */
    public boolean matchDocument(@Nonnull IRI iri) {
        return iri.equals(getDefaultDocumentIRI().orElse(null));
    }

    /**
     * @return true if the input iri matches the ontology iri
     * @param iri
     *        the iri to check
     */
    public boolean matchOntology(@Nonnull IRI iri) {
        return iri.equals(ontologyIRI.orElse(null));
    }

    /**
     * @return true if the input id has the same ontology iri
     * @param id
     *        the id to check
     */
    public boolean match(@Nonnull OWLOntologyID id) {
        return ontologyIRI.equals(id.getOntologyIRI());
    }

    /**
     * Constructs an ontology identifier specifiying the ontology IRI and
     * version IRI.
     * 
     * @param iri
     *        The ontology IRI (may be absent)
     * @param version
     *        The version IRI (must be absent if the ontologyIRI is absent)
     */
    public OWLOntologyID(@Nonnull Optional<IRI> iri,
        @Nonnull Optional<IRI> version) {
        ontologyIRI = opt(iri);
        hashCode = 17;
        if (ontologyIRI.isPresent()) {
            hashCode += 37 * ontologyIRI.hashCode();
        } else {
            internalID = optional(ANON_PREFIX + COUNTER.getAndIncrement());
            hashCode += 37 * internalID.hashCode();
        }
        versionIRI = opt(version);
        if (versionIRI.isPresent()) {
            if (!ontologyIRI.isPresent()) {
                throw new IllegalArgumentException(
                    "If the ontology IRI is null then it is not possible to specify a version IRI");
            }
            hashCode += 37 * versionIRI.hashCode();
        }
    }

    /**
     * Constructs an ontology identifier specifying that the ontology IRI (and
     * hence the version IRI) is not present.
     */
    public OWLOntologyID() {
        this(emptyOptional(IRI.class), emptyOptional(IRI.class));
    }

    /**
     * Determines if this is a valid OWL 2 DL ontology ID. To be a valid OWL 2
     * DL ID, the ontology IRI and version IRI must not be reserved vocabulary.
     * 
     * @return {@code true} if this is a valid OWL 2 DL ontology ID, otherwise
     *         {@code false}
     * @see org.semanticweb.owlapi.model.IRI#isReservedVocabulary()
     */
    public boolean isOWL2DLOntologyID() {
        return !ontologyIRI.isPresent()
            || !ontologyIRI.get().isReservedVocabulary()
                && (!versionIRI.isPresent()
                    || !versionIRI.get().isReservedVocabulary());
    }

    @Override
    public int compareTo(OWLOntologyID o) {
        return toString().compareTo(o.toString());
    }

    /**
     * Gets the ontology IRI. If the ontology is anonymous, it will return an
     * absent Optional (i.e., getOntologyIRI().isPresent() will return false.
     * 
     * @return Optional of the ontology IRI, or Optional.absent if there is no
     *         ontology IRI.
     */
    @Nonnull
    public Optional<IRI> getOntologyIRI() {
        return ontologyIRI;
    }

    /**
     * Gets the version IRI.
     * 
     * @return an optional of the version IRI, or Optional.absent if there is no
     *         version IRI.
     */
    @Nonnull
    public Optional<IRI> getVersionIRI() {
        return versionIRI;
    }

    /**
     * Gets the IRI which is used as a default for the document that contain a
     * representation of an ontology with this ID. This will be the version IRI
     * if there is an ontology IRI and version IRI, else it will be the ontology
     * IRI if there is an ontology IRI but no version IRI, else it will be
     * {@code null} if there is no ontology IRI. See
     * <a href="http://www.w3.org/TR/owl2-syntax/#Ontology_Documents">Ontology
     * Documents</a> in the OWL 2 Structural Specification.
     * 
     * @return An Optional of the IRI that can be used as a default for an
     *         ontology document containing an ontology as identified by this
     *         ontology ID. Returns the default IRI or an Optional.absent.
     */
    @Nonnull
    public Optional<IRI> getDefaultDocumentIRI() {
        if (ontologyIRI.isPresent()) {
            if (versionIRI.isPresent()) {
                return versionIRI;
            } else {
                return ontologyIRI;
            }
        } else {
            return emptyOptional();
        }
    }

    /**
     * Determines if this ID names an ontology or whether it is an ID for an
     * ontology without an IRI. If the result of this method is true,
     * getOntologyIRI() will return an Optional.absent.
     * 
     * @return {@code true} if this ID is an ID for an ontology without an IRI,
     *         or {@code false} if this ID is an ID for an ontology with an IRI.
     */
    @Override
    public boolean isAnonymous() {
        return !ontologyIRI.isPresent();
    }

    @Nonnull
    @Override
    public String toString() {
        if (ontologyIRI.isPresent()) {
            String template = "OntologyID(OntologyIRI(<%s>) VersionIRI(<%s>))";
            return String.format(template, ontologyIRI.get(),
                versionIRI.orElse(null));
        }
        return "OntologyID(" + internalID.orElse(null) + ')';
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntologyID)) {
            return false;
        }
        OWLOntologyID other = (OWLOntologyID) obj;
        if (isAnonymous() && other.isAnonymous()) {
            // both anonymous: check the anon version
            return internalID.equals(other.internalID);
        }
        if (isAnonymous() != other.isAnonymous()) {
            // one anonymous, one not: equals is false
            return false;
        }
        if (!isAnonymous()) {
            boolean toReturn = ontologyIRI.equals(other.ontologyIRI);
            if (!toReturn) {
                return toReturn;
            }
            // if toReturn is true, compare the version iris
            toReturn = versionIRI.equals(other.versionIRI);
            return toReturn;
        }
        // else this is anonymous and the other cannot be anonymous, so return
        // false
        return false;
    }
}
