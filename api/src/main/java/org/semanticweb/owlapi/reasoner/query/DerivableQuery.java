package org.semanticweb.owlapi.reasoner.query;

import org.semanticweb.owlapi.reasoner.Query;
import org.semanticweb.owlapi.reasoner.UnsupportedQueryTypeException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
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
 * Date: 18-Mar-2009
 *
 * <p>
 * Represents a (complex) query that is answered using a custom answering procedure.
 * </p>
 */
public interface DerivableQuery<R> extends Query<R> {

    /**
     * This method will be called by an OWLReasoner and in general should not be called directly since the reasoner
     * <i>may</i> understand the query at a high level and have internal optimisations that can process the query in
     * a smarter way than using individual well known queries to answer the compound query
     * Executes the custom query using the specified reasoner to answer any
     * base queries (e.g. satisfiability checking, consistency checking etc.)
     * @param reasoner The reasoner to be used
     * @return The query result
     * @throws UnsupportedQueryTypeException if the reasoner does not support answering one of
     * the base queries
     * @throws InterruptedException if the reasoner was interrupted when answering a base query.
     */
     R execute(OWLReasoner reasoner) throws UnsupportedQueryTypeException, InterruptedException;
}
