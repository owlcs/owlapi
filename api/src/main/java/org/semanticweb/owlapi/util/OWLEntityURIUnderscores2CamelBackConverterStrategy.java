package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.IRI;

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
