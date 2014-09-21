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
package uk.ac.manchester.cs.owl.explanation.ordering;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 * @param <N>
 *        the kind of elements in the tree
 */
public interface Tree<N> {

    /**
     * Gets the "content" of this tree node.
     * 
     * @return The user content of this node.
     */
    @Nonnull
    N getUserObject();

    /**
     * Gets the parent of this tree node.
     * 
     * @return The parent tree node, or {@code null} if this node doesn't have a
     *         parent.
     */
    @Nonnull
    Tree<N> getParent();

    /**
     * Gets the children of this tree node.
     * 
     * @return A list of children. If this tree node doesn't have any children
     *         then the list will be empty.
     */
    @Nonnull
    List<Tree<N>> getChildren();

    /**
     * @param child
     *        the child to use
     * @return the object stored as edge
     */
    Object getEdge(Tree<N> child);

    /**
     * Sorts the children using the specified comparator.
     * 
     * @param comparator
     *        The comparator to be used for the sorting.
     */
    void sortChildren(@Nonnull Comparator<Tree<N>> comparator);

    /**
     * A convenience method that gets the number of child nodes that this node
     * has.
     * 
     * @return The count of the number of children of this node.
     */
    int getChildCount();

    /**
     * A convenience method that determines if this is a root node (because it
     * has no parent node).
     * 
     * @return {@code true} if this is a root node, otherwise {@code false}.
     */
    boolean isRoot();

    /**
     * A convenience method that determines if this node is a leaf node (because
     * it has no children).
     * 
     * @return {@code true} if this node is a leaf node otherwise {@code false}.
     */
    boolean isLeaf();

    /**
     * A convenience method that gets the root of this tree.
     * 
     * @return The root node, which could be this node.
     */
    @Nonnull
    Tree<N> getRoot();

    /**
     * Gets the path to root.
     * 
     * @return the path to the root
     */
    @Nonnull
    List<Tree<N>> getPathToRoot();

    /**
     * Gets the user object path to root.
     * 
     * @return the elements in the path to root
     */
    @Nonnull
    List<N> getUserObjectPathToRoot();

    /**
     * Dump.
     * 
     * @param writer
     *        the writer to print to
     */
    void dump(@Nonnull PrintWriter writer);

    /**
     * Dump.
     * 
     * @param writer
     *        the writer to print to
     * @param indent
     *        how much to indent
     */
    void dump(@Nonnull PrintWriter writer, int indent);

    /**
     * Sets the node renderer.
     * 
     * @param renderer
     *        the renderer to use
     */
    void setNodeRenderer(@Nonnull NodeRenderer<N> renderer);

    /**
     * Gets the user object closure.
     * 
     * @return the set of user objects
     */
    @Nonnull
    Set<N> getUserObjectClosure();

    /**
     * Fill depth first.
     * 
     * @return the list of elements
     */
    @Nonnull
    List<N> fillDepthFirst();
}
