/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.reasoner;
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
