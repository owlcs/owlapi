package org.semanticweb.owl.util;

import org.semanticweb.owl.model.OWLOntologyIRIMapper;
import org.semanticweb.owl.model.IRI;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 07-Feb-2007<br><br>
 * <p/>
 * An ontology URI mapper that can be used to map ontology URIs
 * to physical URIs which share the same base.
 */
public class CommonBaseIRIMapper implements OWLOntologyIRIMapper {

    private URI base;

    private Map<IRI, URI> uriMap;


    /**
     * Creates a mapper, which maps ontology URIs to URIs which share
     * the specified base
     */
    public CommonBaseIRIMapper(URI base) {
        this.base = base;
        uriMap = new HashMap<IRI, URI>();
    }


    /**
     * Adds a mapping from an ontology URI to a physical URI which
     * has a base of this mapper and a specified local name - in
     * other words the physical URI will be determined by resolving
     * the local name against the URI base of this mapper.
     */
    public void addMapping(IRI ontologyIRI, String localName) {
        URI physicalIRI = base.resolve(localName);
        uriMap.put(ontologyIRI, physicalIRI);
    }


    /**
     * Given an ontology IRI, this method maps the ontology IRI
     * to a physical IRI that points to some concrete representation
     * of the ontology.
     * @param ontologyIRI The ontology IRI to be mapped.
     * @return The physical IRI of the ontology, or <code>null</code>
     *         if the mapper doesn't have mapping for the specified ontology IRI.
     */
    public URI getPhysicalURI(IRI ontologyIRI) {
        return uriMap.get(ontologyIRI);
    }
}
