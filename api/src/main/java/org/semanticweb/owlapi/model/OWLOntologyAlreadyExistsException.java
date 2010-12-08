package org.semanticweb.owlapi.model;

/*
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
 * Indicates that an ontology with the given ontology IRI (and possible version IRI) exists.
 */
public class OWLOntologyAlreadyExistsException extends OWLOntologyCreationException {

    private OWLOntologyID ontologyID;

    private IRI documentIRI;

    /**
     * Constructs an <code>OWLOntologyAlreadyExistsException</code> to describe the situation where an attempt to
     * create an ontology failed because the manager already contained an ontology with specified ontology ID.
     *
     * @param id The ID of the ontology (not <code>null</code>) that was already contained in the manager.
     */
    public OWLOntologyAlreadyExistsException(OWLOntologyID id) {
        super("Ontology already exists. " + id);
        this.ontologyID = id;
    }

    /**
     * Constructs an <code>OWLOntologyAlreadyExistsException</code> to describe the situation where an attempt to
     * load an ontology failed because the manager already contained an ontology with the ID that was the same as
     * the ontology being loaded
     * @param ontologyID The ontology ID
     * @param documentIRI The IRI of the document where the load attempt occurred from
     */
    public OWLOntologyAlreadyExistsException(OWLOntologyID ontologyID, IRI documentIRI) {
        super("Ontology already exists. " + ontologyID + " (New ontology loaded from " + documentIRI.toQuotedString() + ")");
        this.ontologyID = ontologyID;
        this.documentIRI = documentIRI;
    }

    /**
     * Gets the ID of the ontology that already exists
     * @return The ontology ID.
     */
    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    /**
     * Gets the document IRI where the ontology was loaded from
     * @return The IRI of the document where the ontology was loaded from.  If the ontology was created without
     * loading it from an ontology document then the return value will be <code>null</code>.
     */
    public IRI getDocumentIRI() {
        return documentIRI;
    }
}
