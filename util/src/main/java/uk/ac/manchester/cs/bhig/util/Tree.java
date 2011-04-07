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

package uk.ac.manchester.cs.bhig.util;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2008<br><br>
 */
public interface Tree<N> {


    /**
     * Gets the "content" of this tree node.
     * @return The user content of this node.
     */
    N getUserObject();


    /**
     * Gets the parent of this tree node.
     * @return The parent tree node, or <code>null</code>
     * if this node doesn't have a parent.
     */
    Tree<N> getParent();


    /**
     * Gets the children of this tree node.
     * @return A list of children.  If this tree node
     * doesn't have any children then the list will be
     * empty.
     */
    List<Tree<N>> getChildren();

    Object getEdge(Tree<N> child);


    /**
     * Sorts the children using the specified comparator
     * @param comparator The comparator to be used for the sorting.
     */
    void sortChildren(Comparator<Tree<N>> comparator);

    /**
     * A convenience method that gets the number of child
     * nodes that this node has.
     * @return The count of the number of children of this node.
     */
    int getChildCount();


    /**
     * A convenience method that determines if this is
     * a root node (because it has no parent node)
     * @return <code>true</code> if this is a root node,
     * otherwise <code>false</code>.
     */
    boolean isRoot();


    /**
     * A convenience method that determines if this node is
     * a leaf node (because it has no children).
     * @return <code>true</code> if this node is a leaf
     * node otherwise <code>false</code>.
     */
    boolean isLeaf();

    /**
     * A convenience method that gets the root of this tree.
     * @return The root node, which could be this node.
     */
    Tree<N> getRoot();

    List<Tree<N>> getPathToRoot();

    List<N> getUserObjectPathToRoot();

    void dump(PrintWriter writer);

    void dump(PrintWriter writer, int indent);

    void setNodeRenderer(NodeRenderer<N> renderer);

    Set<N> getUserObjectClosure();

    List<N> fillDepthFirst();



}
