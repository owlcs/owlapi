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
 * Date: 09-Dec-2009
 * </p>
 * The policy of how a reasoner will return <code>NodeSet</code>s of individuals for queries that return node sets of
 * named individuals such as
 * {@link org.semanticweb.owlapi.reasoner.OWLReasoner#getTypes(org.semanticweb.owlapi.model.OWLNamedIndividual, boolean)} 
 * or {@link org.semanticweb.owlapi.reasoner.OWLReasoner#getInstances(org.semanticweb.owlapi.model.OWLClassExpression, boolean)}.
 */
public enum IndividualNodeSetPolicy {

    /**
     * Indicates that <code>NodeSet</code>s of named individuals returned by the reasoner will contain <code>Node</code>s
     * that group individuals which are entailed to be the same as each other.  For example, if <code>i</code>,
     * <code>j</code> and <code>k</code> are individuals, and they are instances of <code>C</code>, and <code>i</code>
     * is entailed to be the same as <code>j</code> then asking for the instances of <code>A</code> will return a
     * <code>NodeSet</code> containing two <code>Node</code>s, one containing <code>i</code> and <code>j</code> and the
     * other containing <code>k</code>.
     */
    BY_SAME_AS,

    /**
     * Indicates that <code>NodeSet</code>s of named individuals will always contain singleton <code>Node</code>s and
     * individuals that are the same as each other will not be grouped together in one <code>Node</code> within the
     * <code>NodeSet</code>.For example, if <code>i</code>,
     * <code>j</code> and <code>k</code> are individuals, and they are instances of <code>C</code>, and <code>i</code>
     * is entailed to be the same as <code>j</code> then asking for the instances of <code>A</code> will return a
     * <code>NodeSet</code> containing <i>three</i> <code>Node</code>s, one containing <code>i</code>, one containing
     * <code>j</code> and the third containing <code>k</code>.
     */
    BY_NAME,

}
