package org.semanticweb.owlapi.reasoner;
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
 * Date: 29-Nov-2009
 * </p>
 * An undeclared entity is an entity that is not a built in entity (see {@link org.semanticweb.owlapi.model.OWLEntity#isBuiltIn()}
 * and is not contained within the signature of the root ontology imports closure.
 * </p>
 * See also: {@link org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration}, {@link org.semanticweb.owlapi.reasoner.UndeclaredEntitiesException}
 */
public enum UndeclaredEntityPolicy {

    /**
     * Specifies that undeclared entities are allowed and should be treated as "fresh" entities when they are encountered
     * in the signature of OWLObjects in queries to a reasoner.
     */
    ALLOW,

    /**
     * Specifies that undeclared entities are not disallowed.  When an undeclared entity is encountered in the signature of
     * an OWLObject that is used in a query to a reasoner an {@link UndeclaredEntitiesException} will be thrown in the
     * calling thread.
     */
    DISALLOW
}
