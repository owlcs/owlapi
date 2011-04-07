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

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 18-Jan-2009
 * <p/>
 * An object that identifies an ontology.  Since OWL 2, ontologies do not have to have an ontology IRI, or if they
 * have an ontology IRI then they can optionally also have a version IRI.  Instances of this OWLOntologyID class bundle
 * identifying information of an ontology together.  If an ontology doesn't have an ontology IRI then we say that
 * it is "anonymous".
 */
public final class OWLOntologyID implements Comparable<OWLOntologyID> {

    private static int counter = 0;

    private static String ANON_PREFIX = "Anonymous-";

    private String internalID;

    private IRI ontologyIRI;

    private IRI versionIRI;

    private int hashCode;


    /**
     * Constructs an ontology identifier specifying the ontology IRI
     * @param ontologyIRI The ontology IRI used to indentify the ontology
     */
    public OWLOntologyID(IRI ontologyIRI) {
        this(ontologyIRI, null);
    }


    /**
     * Constructs an ontology identifier specifiying the ontology IRI and version IRI
     * @param ontologyIRI The ontology IRI (may be <code>null</code>)
     * @param versionIRI The version IRI (must be <code>null</code> if the ontologyIRI is null)
     */
    public OWLOntologyID(IRI ontologyIRI, IRI versionIRI) {
        this.ontologyIRI = ontologyIRI;
        hashCode = 17;
        if (ontologyIRI != null) {
            internalID = null;
            hashCode += 37 * ontologyIRI.hashCode();
        }
        if (versionIRI != null) {
            if (ontologyIRI == null) {
                throw new IllegalArgumentException("If the ontology IRI is null then it is not possible to specify a version IRI");
            }
            this.versionIRI = versionIRI;
            hashCode += 37 * versionIRI.hashCode();
        }
        else {
            this.versionIRI = null;
        }
        if (ontologyIRI == null) {
            internalID = ANON_PREFIX + getNextCounter();
            hashCode += 37 * internalID.hashCode();
        }
    }


    /**
     * Constructs an ontology identifier specifying that the ontology IRI (and hence the version IRI) is not present.
     */
    public OWLOntologyID() {
        this(null, null);
    }


    /**
     * Determines if this is a valid OWL 2 DL ontology ID.  To be a valid OWL 2 DL ID, the ontology IRI and version IRI
     * must not be reserved vocabulary.
     * @return <code>true</code> if this is a valid OWL 2 DL ontology ID, otherwise <code>false</code>
     * @see org.semanticweb.owlapi.model.IRI#isReservedVocabulary()
     */
    public boolean isOWL2DLOntologyID() {
        return ontologyIRI == null || (!ontologyIRI.isReservedVocabulary() && (versionIRI == null || !versionIRI.isReservedVocabulary()));
    }


    public int compareTo(OWLOntologyID o) {
        return toString().compareTo(o.toString());
    }


    public IRI getOntologyIRI() {
        return ontologyIRI;
    }


    public IRI getVersionIRI() {
        return versionIRI;
    }


    /**
     * Gets the IRI which is used as a default for the document that contain a representation of an ontology with this
     * ID. This will be the version IRI if there is an ontology IRI and version IRI, else it will be the ontology IRI
     * if there is an ontology IRI but no version IRI, else it will be <code>null</code> if there is no ontology IRI.
     * See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Ontology_Documents">Ontology Documents</a> in
     * the OWL 2 Structural Specification.
     * @return The IRI that can be used as a default for an ontology document containing an ontology as identified
     *         by this ontology ID.  Returns the default IRI or <code>null</code>.
     */
    public IRI getDefaultDocumentIRI() {
        if (ontologyIRI != null) {
            if (versionIRI != null) {
                return versionIRI;
            }
            else {
                return ontologyIRI;
            }
        }
        else {
            return null;
        }
    }

    /**
     * Determines if this ID names an ontology or whether it is an ID for an ontology without an IRI.
     * @return <code>true</code> if this ID is an ID for an ontology without an IRI, or <code>false</code>
     *         if this ID is an ID for an ontology with an IRI.
     */
    public boolean isAnonymous() {
        return ontologyIRI == null;
    }

    private static int getNextCounter() {
        counter++;
        return counter;
    }


    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        if (ontologyIRI != null) {
            sb.append(ontologyIRI.toQuotedString());
            if (versionIRI != null) {
                sb.append(versionIRI.toQuotedString());
            }
        }
        else {
            sb.append(internalID);
        }
        return sb.toString();
    }


    @Override
	public int hashCode() {
        return hashCode;
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntologyID)) {
            return false;
        }
        OWLOntologyID other = (OWLOntologyID) obj;
        if (ontologyIRI != null) {
            if (other.ontologyIRI == null) {
                return false;
            }
            if (!ontologyIRI.equals(other.ontologyIRI)) {
                return false;
            }
            if (versionIRI != null) {
                if (other.versionIRI == null) {
                    return false;
                }
                else {
                    return versionIRI.equals(other.versionIRI);
                }
            }
            else {
                return other.versionIRI == null;
            }
        }
        else {
            if (other.ontologyIRI != null) {
                return false;
            }
            else {
                return internalID.equals(other.internalID);
            }
        }
    }
}
