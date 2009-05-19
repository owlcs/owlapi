package org.semanticweb.owl.util;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.vocab.Namespaces;

import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.net.URI;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 19-May-2009
 */
public class PrefixShortFormProvider implements ShortFormProvider, URIShortFormProvider {

    private Map<String, String> prefix2PrefixNameMap = new TreeMap<String, String>(
            new Comparator<String>() {
                // Sorts longest strings first then alpha
                public int compare(String o1, String o2) {
                    int diff = o1.length() - o2.length();
                    if(diff != 0) {
                        return diff;
                    }
                    return o1.compareTo(o2);
                }
            }
    );


    /**
     * Registers well known prefix name and prefix pairs:
     * owl <OWLNS>, rdfs <RDFSNS>, rdf <RDFNS>, xsd <XSDNS>, xml <XMLNS>, skos <SKOSNS>, dc <DUBLINCORENS>.
     *
     */
    public void registerWellKnownPrefixes() {
        setPrefix("owl", Namespaces.OWL.toString());
        setPrefix("rdfs", Namespaces.RDFS.toString());
        setPrefix("rdf", Namespaces.RDF.toString());
        setPrefix("xsd", Namespaces.XSD.toString());
        setPrefix("xml", Namespaces.XML.toString());
        setPrefix("skos", Namespaces.SKOS.toString());
        setPrefix("dc", Namespaces.SKOS.toString());
    }

    /**
     * Sets the prefix name for a prefix.  For example,  'owl:' is the prefix name for the OWL namespace.
     * The default prefix name is ':'
     * @param prefixName The prefix name. Not <code>null</code>.  Use ':' for the default prefix name.
     * @param prefixIRI The prefix IRI. Not <code>null</code>
     */
    public void setPrefix(String prefixName, String prefixIRI) {
        prefix2PrefixNameMap.put(prefixIRI, prefixName);
    }

    public String getShortForm(OWLEntity entity) {
        return getShortForm(entity.getURI());
    }

    public String getShortForm(URI uri) {
        String uriString = uri.toString();
        for(String prefix : prefix2PrefixNameMap.keySet()) {
            if(uriString.startsWith(prefix)) {
                StringBuilder sb = new StringBuilder();
                String prefixName = prefix2PrefixNameMap.get(prefix);
                sb.append(prefixName);
                sb.append(uriString.substring(prefix.length() + 1));
                return sb.toString();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(uri);
        sb.append(">");
        return sb.toString();
    }

    public void dispose() {
    }
}
