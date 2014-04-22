/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.reasoner.knowledgeexploration;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/** Extension of OWLReasoner to allow access to reasoner completion graph. */
public interface OWLKnowledgeExplorerReasoner extends OWLReasoner {

    /** Interface for a tableaux node. */
    interface RootNode {

        /**
         * @param <T>
         *        actual node type
         * @return inner node representation
         */
        @Nonnull
        <T> T getNode();
    }

    /**
     * @param expression
     *        the expression of which the completion tree is to be computed
     * @return compute and return the root node of the completion tree for the
     *         expression
     */
    RootNode getRoot(OWLClassExpression expression);

    /**
     * @param node
     *        a node, as returned by either getRoot() or getObjectNeighbours()
     * @param deterministicOnly
     *        true if only deterministic results should be returned
     * @return neighbors by object property
     */
    Node<? extends OWLObjectPropertyExpression> getObjectNeighbours(
            RootNode node, boolean deterministicOnly);

    /**
     * @param node
     *        a node, as returned by either getRoot() or getObjectNeighbours()
     * @param deterministicOnly
     *        true if only deterministic results should be returned
     * @return neighbors by data property
     */
    Node<OWLDataProperty> getDataNeighbours(RootNode node,
            boolean deterministicOnly);

    /**
     * @param node
     *        a node, as returned by either getRoot() or getObjectNeighbours()
     * @param property
     *        the property being followed
     * @return neighbors by object property
     */
    Collection<RootNode> getObjectNeighbours(RootNode node,
            OWLObjectProperty property);

    /**
     * @param node
     *        a node, as returned by either getRoot() or getObjectNeighbours()
     * @param property
     *        the property being followed
     * @return neighbors by data property
     */
    Collection<RootNode> getDataNeighbours(RootNode node,
            OWLDataProperty property);

    /**
     * @param node
     *        a node, as returned by either getRoot() or getObjectNeighbours()
     * @param deterministicOnly
     *        true if only deterministic results should be returned
     * @return neighbors label by object property
     */
    Node<? extends OWLClassExpression> getObjectLabel(RootNode node,
            boolean deterministicOnly);

    /**
     * @param node
     *        a node, as returned by getDataNeighbours()
     * @param deterministicOnly
     *        true if only deterministic results should be returned
     * @return neighbors label by data property
     */
    Node<? extends OWLDataRange> getDataLabel(RootNode node,
            boolean deterministicOnly);

    /**
     * @param node
     *        a node, as return by either getRoot() or getObjectNeighbours()
     * @return a node that blocks given node, or null if there is no such
     *         blocker
     */
    RootNode getBlocker(RootNode node);
}
