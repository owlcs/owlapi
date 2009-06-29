package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.Map;
import java.util.Set;
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
     * A convenience method to add a prefix name to prefix mapping
     * @param prefixName   The prefix name which maps to a prefix
     * @param prefix The prefix
     */
    public void setPrefix(String prefixName, String prefix) {
        if(!prefixName.endsWith(":")) {
            prefixName = prefixName + ":";
        }
        nsm.setPrefix(prefixName, prefix);
    }

    /**
     * Copies the prefix from another ontology format into this format
     * @param fromFormat The format that the prefixes should be copied from
     */
    public void copyPrefixesFrom(PrefixOWLOntologyFormat fromFormat) {
        Map<String, String> map = fromFormat.getPrefixName2PrefixMap();
        for(String pn : map.keySet()) {
            String prefix = map.get(pn);
            nsm.setPrefix(pn, prefix);
        }
    }

    /**
     * Gets the prefix names that have a mapping in this prefix manager
     * @return
     */
    public Set<String> getPrefixNames() {
        return nsm.getPrefixNames();
    }

    /**
     * Sets the default namespace. This is equivalent to adding mapping from the empty string prefix to a
     * namespace.
     * @param namespace The namespace to be set.
     */
    public void setDefaultPrefix(String namespace) {
        nsm.setDefaultPrefix(namespace);
    }


    public boolean containsPrefixMapping(String prefix) {
        return nsm.containsPrefixMapping(prefix);
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


    public IRI getIRI(String curi) {       
        return nsm.getIRI(curi);
    }

    public String getPrefixIRI(IRI iri) {
        return nsm.getPrefixIRI(iri);
    }
}
