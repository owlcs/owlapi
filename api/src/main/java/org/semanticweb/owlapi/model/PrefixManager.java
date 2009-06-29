package org.semanticweb.owlapi.model;

import java.util.Map;
import java.util.Set;
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
 * A prefix manager than can provide prefixes for prefix names.
 */
public interface PrefixManager {
    
    /**
     * Gets the default prefix.  The default prefix is denoted by the prefix name ":"
     *
     * @return The default prefix, or <code>null</code> if there is no default prefix.
     */
    String getDefaultPrefix();


    /**
     * Determines if this manager knows about a given prefix name and it contains a (non-null) mapping for the
     * prefix.
     *
     * @param prefixName The prefix name to be tested for.
     * @return <code>true</code> if the manager knows about this prefix and there is a non-null mapping for this
     *         prefix.
     */
    boolean containsPrefixMapping(String prefixName);


    /**
     * Gets the prefix that is bound to a particular prefix name.  Note that specifying ":" corresponds to
     * requesting the default prefix and will return the same result as a call to the <code>getDefaultPrefix()</code>
     * method.
     *
     * @param prefixName The prefix  name. A string that represents a prefix name of the prefix to be retrieved.
     *               Note that specifying ":" is the same as asking for the default prefix (see the
     *               getDefaultPrefix() method).
     * @return The prefix, or <code>null</code> if there is no prefix name bound to this prefix, or the prefix name doesn't
     *         exist.
     */
    String getPrefix(String prefixName);


    /**
     * Gets a map that maps prefix names to prefixes.
     *
     * @return The map of prefix names to prefixes.  Note that modifying the contents of this map will not change the
     *         prefix name - prefix mappings
     */
    Map<String, String> getPrefixName2PrefixMap();


    /**
     * Gets the URI for a given prefix IRI.  The prefix IRI must have a prefix name that is registered with this
     * manager, or a runtime exception will be thrown.
     *
     * @param prefixIRI The Prefix IRI
     * @return The full IRI.
     * @throws OWLRuntimeException if the prefix name of the prefix IRI doesn't have a corresponding prefix managed by this
     *                             manager.
     */
    IRI getIRI(String prefixIRI);

    /**
     * Gets the prefix IRI given a IRI (URI).
     * @param iri
     * @return The prefix IRI for this IRI, or <code>null</code> if a prefix IRI cannot be generated.
     */
    String getPrefixIRI(IRI iri);

    /**
     * Gets the prefix names that have a mapping in this prefix manager
     * @return
     */
    Set<String> getPrefixNames();
}
