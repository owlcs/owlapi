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

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 *
 * Converts the entity URI fragment or last path element if the
 * fragment is not present to Camel Case.  For example, if
 * the URI is http://another.com/pathA/pathB#has_part then this will
 * be converted to http://another.com/pathA/pathB#hasPart
 */
public class OWLEntityURIUnderscores2CamelBackConverterStrategy implements OWLEntityURIConverterStrategy {

    private Map<IRI, IRI> iriMap;


    public OWLEntityURIUnderscores2CamelBackConverterStrategy() {
        iriMap = new HashMap<IRI, IRI>();
    }


    public IRI getConvertedIRI(OWLEntity entity) {
        IRI convIRI = iriMap.get(entity.getIRI());
        if(convIRI == null) {
            convIRI = convert(entity.getIRI());
            iriMap.put(entity.getIRI(), convIRI);
        }
        return convIRI;
    }

    private static IRI convert(IRI iri) {
        String iriString = iri.toString();
        String fragment = iri.toURI().getFragment();
        if(fragment != null) {
            String base = iriString.substring(0, iriString.length() - fragment.length());
            String camelCaseFragment = toCamelCase(fragment);
            return IRI.create(base + camelCaseFragment);
        }
        String path = iri.toURI().getPath();
        if(path.length() > 0) {
            int index = path.lastIndexOf('/');
            String lastPathElement = path.substring(index + 1, path.length());
            String camelCaseElement = toCamelCase(lastPathElement);
            String base = iriString.substring(0, iriString.lastIndexOf('/') + 1);
            return IRI.create(base + camelCaseElement);
        }
        return iri;
    }



    private static String toCamelCase(String s) {
        StringBuilder sb = new StringBuilder();
        boolean nextIsUpperCase = false;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '_') {
                nextIsUpperCase = true;
            }
            else {
                if(nextIsUpperCase) {
                    sb.append(Character.toUpperCase(ch));
                    nextIsUpperCase = false;
                }
                else {
                    sb.append(ch);
                }

            }
        }
        return sb.toString();
    }


}
