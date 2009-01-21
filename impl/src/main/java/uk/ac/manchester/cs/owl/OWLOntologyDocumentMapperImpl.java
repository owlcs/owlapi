package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.OWLOntologyDocumentMapper;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 */
public class OWLOntologyDocumentMapperImpl implements OWLOntologyDocumentMapper {

    private Map<URI, URI> uriMap;


    public OWLOntologyDocumentMapperImpl() {
        uriMap = new TreeMap<URI, URI>();
    }


    /**
     * Gets the physical URI of an ontology given an ontology URI.  If no mapping
     * is found, then the ontology URI is returned.
     *
     * @param ontologyURI The URI of the ontology to map
     */
    public URI getDocumentIRI(URI ontologyURI) {
        URI uri = uriMap.get(ontologyURI);
        if (uri != null) {
            return uri;
        } else {
            return ontologyURI;
        }
    }

    public void addMapping(URI ontologyURI, URI physicalURI) {
        uriMap.put(ontologyURI, physicalURI);
    }
}
