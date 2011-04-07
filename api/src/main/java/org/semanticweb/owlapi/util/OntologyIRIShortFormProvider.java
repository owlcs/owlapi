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

package org.semanticweb.owlapi.util;

import java.net.URI;
import java.util.StringTokenizer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Jan-2008<br><br>
 */
public class OntologyIRIShortFormProvider implements IRIShortFormProvider {

    public String getShortForm(OWLOntology ont) {
        if (!ont.isAnonymous()) {
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
        if (path != null && path.length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(path, "/", false);
            String lastPathComponent = "";
            while (tokenizer.hasMoreTokens()) {
                String tok = tokenizer.nextToken();
                if (tok.length() > 0) {
                    lastPathComponent = tok;
                }
            }
            if (lastPathComponent.endsWith(".owl")) {
                shortForm = lastPathComponent.substring(0, lastPathComponent.length() - 4);
            }
            else {
                shortForm = lastPathComponent;
            }
        }
        else if (uri.getHost() != null) {
            shortForm = iri.toString();
        }
//        if(!Character.isUpperCase(shortForm.charAt(0))) {
//            shortForm = Character.toUpperCase(shortForm.charAt(0)) + shortForm.substring(1, shortForm.length());
//        }

        return shortForm;

    }
}
