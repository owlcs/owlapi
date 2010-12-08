package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.IRI;

/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 06-Dec-2006<br><br>
 */
public abstract class RDFNode {

    public abstract boolean isLiteral();


    /**
     * Gets the URI of the resource.
     * @return The URI or <code>null</code> if this is an anonymous resource.
     */
    public abstract IRI getIRI();


    /**
     * Determines if this node is a resource and is anonymous.
     * @return <code>true</code> if this is a resource node (i.e.
     * <code>isLiteral</code> returns <code>false</code>) and the
     * node is anonymous, or <code>false</code> if this is a
     * resource node and is not anonymous.
     */
    public abstract boolean isAnonymous();
}
