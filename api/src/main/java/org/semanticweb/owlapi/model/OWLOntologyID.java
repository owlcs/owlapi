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

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

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
public class OWLOntologyID implements Comparable<OWLOntologyID>, Serializable {

    private static final long serialVersionUID = 40000L;
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final String ANON_PREFIX = "Anonymous-";
    private String internalID;
    private final IRI ontologyIRI;
    private final IRI versionIRI;
    private int hashCode;

    /**
     * Constructs an ontology identifier specifying the ontology IRI.
     * 
     * @param ontologyIRI
     *        The ontology IRI used to indentify the ontology
     */
    public OWLOntologyID(IRI ontologyIRI) {
        this(ontologyIRI, null);
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
        ontologyIRI = NodeID.isAnonymousNodeIRI(iri) ? null : iri;
        hashCode = 17;
        if (ontologyIRI != null) {
            internalID = null;
            hashCode += 37 * ontologyIRI.hashCode();
        }
        if (versionIRI != null) {
            if (ontologyIRI == null) {
                throw new IllegalArgumentException(
                        "If the ontology IRI is null then it is not possible to specify a version IRI");
            }
            this.versionIRI = versionIRI;
            hashCode += 37 * versionIRI.hashCode();
        } else {
            this.versionIRI = null;
        }
        if (ontologyIRI == null) {
            internalID = ANON_PREFIX + COUNTER.getAndIncrement();
            hashCode += 37 * internalID.hashCode();
        }
    }

    /**
     * Constructs an ontology identifier specifying that the ontology IRI (and
     * hence the version IRI) is not present.
     */
    public OWLOntologyID() {
        this(null, null);
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
        return ontologyIRI == null || !ontologyIRI.isReservedVocabulary()
                && (versionIRI == null || !versionIRI.isReservedVocabulary());
    }

    @Override
    public int compareTo(OWLOntologyID o) {
        return toString().compareTo(o.toString());
    }

    /**
     * Gets the ontology IRI.
     * 
     * @return the ontology IRI, or {@code null} if there is no ontology IRI.
     */
    public IRI getOntologyIRI() {
        return ontologyIRI;
    }

    /**
     * Gets the version IRI.
     * 
     * @return the version IRI, or {@code null} if there is no version IRI.
     */
    public IRI getVersionIRI() {
        return versionIRI;
    }

    /**
     * Gets the IRI which is used as a default for the document that contain a
     * representation of an ontology with this ID. This will be the version IRI
     * if there is an ontology IRI and version IRI, else it will be the ontology
     * IRI if there is an ontology IRI but no version IRI, else it will be
     * {@code null} if there is no ontology IRI. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#Ontology_Documents">Ontology
     * Documents</a> in the OWL 2 Structural Specification.
     * 
     * @return The IRI that can be used as a default for an ontology document
     *         containing an ontology as identified by this ontology ID. Returns
     *         the default IRI or {@code null}.
     */
    public IRI getDefaultDocumentIRI() {
        if (ontologyIRI != null) {
            if (versionIRI != null) {
                return versionIRI;
            } else {
                return ontologyIRI;
            }
        } else {
            return null;
        }
    }

    /**
     * Determines if this ID names an ontology or whether it is an ID for an
     * ontology without an IRI.
     * 
     * @return {@code true} if this ID is an ID for an ontology without an IRI,
     *         or {@code false} if this ID is an ID for an ontology with an IRI.
     */
    public boolean isAnonymous() {
        return ontologyIRI == null || NodeID.isAnonymousNodeIRI(ontologyIRI);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OntologyID(");
        if (ontologyIRI != null) {
            sb.append("OntologyIRI(");
            sb.append(ontologyIRI.toQuotedString());
            sb.append(")");
            if (versionIRI != null) {
                sb.append(" VersionIRI(");
                sb.append(versionIRI.toQuotedString());
                sb.append(")");
            }
        } else {
            sb.append(internalID);
        }
        sb.append(")");
        return sb.toString();
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
            if (versionIRI != null) {
                toReturn = versionIRI.equals(other.versionIRI);
            } else {
                toReturn = other.versionIRI == null;
            }
            return toReturn;
        }
        // else this is anonymous and the other cannot be anonymous, so return
        // false
        return false;
    }
}
