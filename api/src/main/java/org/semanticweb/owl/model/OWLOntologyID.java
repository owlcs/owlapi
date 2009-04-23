package org.semanticweb.owl.model;/*
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
 * Date: 18-Jan-2009
 * <p/>
 * An object that identifies an ontology.  Since OWL 2, ontologies do not have to have an ontology IRI, or if they
 * have an ontology IRI then they can optionally also have a version IRI.  Instances of this OWLOntologyID class bundle
 * identifying information of an ontology together.
 */
public final class OWLOntologyID implements Comparable<OWLOntologyID> {

    private static int counter = 0;

    private static String ANON_PREFIX = "Unnamed-";

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
    public OWLOntologyID(IRI ontologyIRI,
                         IRI versionIRI) {
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
     * @see org.semanticweb.owl.model.IRI#isReservedVocabulary()
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
     * (See section 3.2 of the OWL 2 specification)
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


    private static int getNextCounter() {
        counter++;
        return counter;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ontology: ");
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


    public int hashCode() {
        return hashCode;
    }


    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntologyID)) {
            return false;
        }
        OWLOntologyID other = (OWLOntologyID) obj;
        if (ontologyIRI != null) {
            return other.ontologyIRI != null && ontologyIRI.equals(other.ontologyIRI) && (versionIRI == null || other.versionIRI != null && versionIRI.equals(other.versionIRI));
        }
        else {
            return other.ontologyIRI == null && internalID.equals(other.internalID);
        }
    }
}
