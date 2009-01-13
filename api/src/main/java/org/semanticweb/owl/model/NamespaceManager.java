package org.semanticweb.owl.model;

import java.net.URI;
import java.util.Map;
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
 * 10-Sep-2008<br><br>
 * <p/>
 * A namespace manager than can provide namespaces for prefixes.  Note that the empty string always points to the
 * default namespace if there is a default namespace.
 */
public interface NamespaceManager {
    
    /**
     * Gets the default namespace.
     *
     * @return The default namespace, or null if there is no default namespace.
     */
    String getDefaultNamespace();


    /**
     * Determines if this manager knows about a given namespace prefix and it contains a (non-null) mapping for the
     * prefix.
     *
     * @param prefix The prefix to be tested for.
     * @return <code>true</code> if the manager knows about this prefix and there is a non-null mapping for this
     *         prefix.
     */
    boolean containsPrefixMapping(String prefix);


    /**
     * Gets the namespace that is bound to a particular prefix.  Note that specifying the empty string corresponds to
     * requesting the default namespace and will return the same result as a call to the <code>getDefaultNamespace()</code>
     * method.
     *
     * @param prefix The namespace prefix. A string that represents a namespace prefix of the namespace to be retrieved.
     *               Note that specifying the empty string is the same as asking for the default namespace (see the
     *               getDefaultNamespace() method).
     * @return The namespace, or <code>null</code> if there is no namespace bound to this prefix, or the prefix doesn't
     *         exist.
     */
    String getNamespace(String prefix);


    /**
     * Gets a map that maps prefixes to namespaces.
     *
     * @return The map of prefixes to namespaces.  Note that modifying the contents of this map will not change the
     *         prefix - namespace mappings
     */
    Map<String, String> getNamespaceMap();


    /**
     * Gets the URI for a given CURI.  The CURI must have a namespace prefix that is registered with this namespace
     * manager, or a runtime exception will be thrown.
     *
     * @param curie The CURIE
     * @return The full URI.
     * @throws OWLRuntimeException if the prefix of the CURIE doesn't have a corresponding namespace managed by this
     *                             manager.
     */
    URI getURI(String curie);
}
