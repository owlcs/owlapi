package org.semanticweb.owl.model;

import java.net.URI;/*
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 14-Jan-2009
 * <p/>
 * Represents International Resource Identifiers
 */
public interface IRI extends OWLAnnotationSubject, OWLAnnotationValue {

    /**
     * Obtains this IRI as a URI.  Note that Java URIs handle unicode characters,
     * so there is no loss during this translation.
     * @return The URI
     */
    URI toURI();


    /**
     * Determines if this IRI is in the reserved vocabulary.  An IRI is in the reserved vocabulary if it starts with
     * <http://www.w3.org/1999/02/22-rdf-syntax-ns#> or <http://www.w3.org/2000/01/rdf-schema#>
     * or <http://www.w3.org/2001/XMLSchema#> or <http://www.w3.org/2002/07/owl#>
     * @return <code>true</code> if the IRI is in the reserved vocabulary, otherwise <code>false</code>.
     */
    boolean isReservedVocabulary();

    
    boolean isBuiltIn();


    boolean isThing();


    boolean isNothing();


    /**
     * Obtained this IRI surrounded by angled brackets
     * @return This IRI surrounded by &lt; and &gt;
     */
    String toQuotedString();
}
