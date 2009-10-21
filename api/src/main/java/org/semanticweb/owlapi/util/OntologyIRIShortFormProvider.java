package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
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
 * Date: 23-Jan-2008<br><br>
 */
public class OntologyIRIShortFormProvider implements IRIShortFormProvider {

    public String getShortForm(OWLOntology ont) {
        if(!ont.isAnonymous()) {
            return getShortForm(ont.getOntologyID().getOntologyIRI());
        }
        else {
            return ont.getOntologyID().toString();
        }
    }

    public String getShortForm(IRI iri) {
        String shortForm = iri.toString();
        URI uri = iri.toURI();
        String path = uri.getPath();
        if(path != null && path.length() > 0) {
            int lastSepIndex = path.lastIndexOf('/');
            String lastPathComponent = path.substring(lastSepIndex + 1, path.length());
            if(lastPathComponent.endsWith(".owlapi")) {
                shortForm = lastPathComponent.substring(0, lastPathComponent.length() - 4);
            }
            else {
                shortForm = lastPathComponent;
            }
        }
        else if(uri.getHost() != null) {
            shortForm = iri.toString();
        }
//        if(!Character.isUpperCase(shortForm.charAt(0))) {
//            shortForm = Character.toUpperCase(shortForm.charAt(0)) + shortForm.substring(1, shortForm.length());
//        }

        return shortForm;

    }
}
