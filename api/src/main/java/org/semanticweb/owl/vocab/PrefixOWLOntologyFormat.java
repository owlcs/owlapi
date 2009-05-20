package org.semanticweb.owl.vocab;

import org.semanticweb.owl.model.PrefixManager;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.semanticweb.owl.util.DefaultPrefixManager;

import java.net.URI;
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
 * Date: 20-Feb-2007<br><br>
 * <p/>
 * An
 */
public class PrefixOWLOntologyFormat extends OWLOntologyFormat implements PrefixManager {

    private DefaultPrefixManager nsm;


    public PrefixOWLOntologyFormat() {
        nsm = new DefaultPrefixManager();
        nsm.clear();
    }


    /**
     * A convenience method to add a namespace mapping
     * @param prefix    The prefix which maps to a namespace
     * @param namespace The namespace
     */
    public void addPrefixNamespaceMapping(String prefix, String namespace) {
        if(!prefix.endsWith(":")) {
            prefix = prefix + ":";
        }
        nsm.setPrefix(prefix, namespace);
    }


    /**
     * Sets the default namespace. This is equivalent to adding mapping from the empty string prefix to a
     * namespace.
     * @param namespace The namespace to be set.
     */
    public void setDefaultNamespace(String namespace) {
        nsm.setDefaultPrefix(namespace);
    }


    public boolean containsPrefixMapping(String prefix) {
        return nsm.containsPrefixMapping(prefix);
    }


    /**
     * Gets a map that maps namespace prefixes to namespaces
     * @return Gets a map that maps namespace prefixes to namespaces.
     */
    public Map<String, String> getNamespacesByPrefixMap() {
        return nsm.getPrefixName2PrefixMap();
    }


    public String getDefaultPrefix() {
        return nsm.getDefaultPrefix();
    }


    public Map<String, String> getPrefixName2PrefixMap() {
        return nsm.getPrefixName2PrefixMap();
    }


    public String getPrefix(String prefix) {
        return nsm.getPrefix(prefix);
    }


    public URI getURI(String curi) {       
        return nsm.getURI(curi);
    }

    public String getPrefixIRI(URI uri) {
        return nsm.getPrefixIRI(uri);
    }
}
