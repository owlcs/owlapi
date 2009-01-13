package org.semanticweb.owl.util;

import org.semanticweb.owl.model.OWLEntity;

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
 * Date: 25-Nov-2007<br><br>
 *
 * Converts the entity URI fragment or last path element if the
 * fragment is not present to Camel Case.  For example, if
 * the URI is http://another.com/pathA/pathB#has_part then this will
 * be converted to http://another.com/pathA/pathB#hasPart
 */
public class OWLEntityURIUnderscores2CamelBackConverterStrategy implements OWLEntityURIConverterStrategy {

    private Map<URI, URI> uriMap;


    public OWLEntityURIUnderscores2CamelBackConverterStrategy() {
        uriMap = new HashMap<URI, URI>();
    }


    public URI getConvertedURI(OWLEntity entity) {
        URI convURI = uriMap.get(entity.getURI());
        if(convURI == null) {
            convURI = convertURI(entity.getURI());
            uriMap.put(entity.getURI(), convURI);
        }
        return convURI;
    }

    private static URI convertURI(URI uri) {
        String uriString = uri.toString();
        String fragment = uri.getFragment();
        if(fragment != null) {
            String base = uriString.substring(0, uriString.length() - fragment.length());
            String camelCaseFragment = toCamelCase(fragment);
            return URI.create(base + camelCaseFragment);
        }
        String path = uri.getPath();
        if(path.length() > 0) {
            int index = path.lastIndexOf('/');
            String lastPathElement = path.substring(index + 1, path.length());
            String camelCaseElement = toCamelCase(lastPathElement);
            String base = uriString.substring(0, uriString.lastIndexOf('/') + 1); 
            return URI.create(base + camelCaseElement);
        }
        return uri;
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


    public static void main(String[] args) {
        URI uri = URI.create("http://www.another.co");
        System.out.println(uri);
        System.out.println(OWLEntityURIUnderscores2CamelBackConverterStrategy.convertURI(uri));

    }
}
