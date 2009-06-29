package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.IRI;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 01-Sep-2008<br><br>
 */
public class DefaultNamespaceTagValueHandler extends AbstractTagValueHandler {


    public DefaultNamespaceTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.DEFAULT_NAMESPACE.getName(), consumer);
    }


    public void handle(String id, String value) {
        // Set the base to be the default base + default prefix
        String uri = OBOVocabulary.ONTOLOGY_URI_BASE + "/" + value.toLowerCase();
        getConsumer().setDefaultNamespace(uri + "#");
        applyChange(new SetOntologyID(getOntology(), new OWLOntologyID(IRI.create(uri))));
    }
}
