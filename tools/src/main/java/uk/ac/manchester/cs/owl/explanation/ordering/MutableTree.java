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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 * @param <N> type of elements
 */
public class MutableTree<N> implements Tree<N> {

    @Nullable
    private final N userObject;
    @Nullable
    private MutableTree<N> parent;
    private final List<MutableTree<N>> children;
    private final Map<Tree<N>, Object> child2EdgeMap;
    private NodeRenderer<N> toStringRenderer;

    /**
     * @param userObject the user object
     */
    public MutableTree(N userObject) {
        this.userObject = userObject;
        children = new ArrayList<>();
        child2EdgeMap = new HashMap<>();
        toStringRenderer = Object::toString;
    }

    @Override
    @Nullable
    public N getUserObject() {
        return userObject;
    }

    /**
     * @param parent the new parent
     */
    public void setParent(MutableTree<N> parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        this.parent.children.add(this);
    }

    /**
     * @param child child to add
     */
    public void addChild(MutableTree<N> child) {
        children.add(child);
        child.parent = this;
    }

    /**
     * @param child child to add
     * @param edge the edge
     */
    public void addChild(MutableTree<N> child, Object edge) {
        addChild(child);
        child2EdgeMap.put(child, edge);
    }

    /**
     * @param child child to remove
     */
    public void removeChild(MutableTree<N> child) {
        children.remove(child);
        child.parent = null;
    }

    @Override
    @Nullable
    public Object getEdge(Tree<N> child) {
        return child2EdgeMap.get(child);
    }

    @Override
    public void sortChildren(Comparator<Tree<N>> comparator) {
        Collections.sort(children, comparator);
    }

    /** remove all children. */
    public void clearChildren() {
        children.forEach(c -> c.parent = null);
        children.clear();
    }

    @Override
    @Nullable
    public Tree<N> getParent() {
        return parent;
    }

    @Override
    public List<Tree<N>> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Tree<N> getRoot() {
        if (parent == null) {
            return this;
        }
        return verifyNotNull(parent).getRoot();
    }

    @Override
    public List<Tree<N>> getPathToRoot() {
        List<Tree<N>> path = new ArrayList<>();
        path.add(0, this);
        Tree<N> par = parent;
        while (par != null) {
            path.add(0, par);
            par = par.getParent();
        }
        return path;
    }

    @Override
    public List<N> getUserObjectPathToRoot() {
        List<N> path = new ArrayList<>();
        path.add(0, this.getUserObject());
        Tree<N> par = parent;
        while (par != null) {
            path.add(0, par.getUserObject());
            par = par.getParent();
        }
        return path;
    }

    @Override
    public Set<N> getUserObjectClosure() {
        Set<N> objects = new HashSet<>();
        getUserObjectClosure(this, objects);
        return objects;
    }

    private void getUserObjectClosure(Tree<N> tree, Set<N> bin) {
        bin.add(tree.getUserObject());
        tree.getChildren().forEach(c -> getUserObjectClosure(c, bin));
    }

    @Override
    public void dump(PrintWriter writer) {
        dump(writer, 0);
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        int depth = getPathToRoot().size();
        char[] chars = new char[depth + indent];
        Arrays.fill(chars, '\t');
        String tabs = new String(chars);
        writer.print(tabs);
        String ren = toStringRenderer.render(this);
        ren = ren.replace("\n", "\n" + tabs);
        writer.println(ren);
        for (Tree<N> child : getChildren()) {
            Object edge = getEdge(child);
            if (edge != null) {
                writer.print("\t--- ");
                writer.print(edge);
                writer.print(" ---\n\n");
            }
            child.dump(writer, indent);
        }
        writer.flush();
    }

    @Override
    public void setNodeRenderer(NodeRenderer<N> renderer) {
        this.toStringRenderer = renderer;
        children.forEach(c -> c.setNodeRenderer(toStringRenderer));
    }

    @Override
    public List<N> fillDepthFirst() {
        List<N> results = new ArrayList<>();
        fillDepthFirst(this, results);
        return results;
    }

    private void fillDepthFirst(Tree<N> tree, List<N> bin) {
        bin.add(tree.getUserObject());
        tree.getChildren().forEach(c -> fillDepthFirst(c, bin));
    }

    /**
     * @param tree the node to put in place of this one
     */
    public void replace(MutableTree<N> tree) {
        verifyNotNull(parent, "parent cannot be null here").children.remove(this);
        verifyNotNull(parent, "parent cannot be null here").children.add(tree);
        parent = null;
        tree.children.clear();
        tree.children.addAll(children);
        children.clear();
    }

    @Override
    public String toString() {
        if (userObject != null) {
            return userObject.toString();
        }
        return "";
    }

    /**
     * @return the size
     */
    public int getSize() {
        return getUserObjectClosure().size();
    }

    /**
     * @return the max depth
     */
    public int getMaxDepth() {
        return getMaxDepth(this);
    }

    private int getMaxDepth(Tree<N> tree) {
        int maxChildDepth = tree.getPathToRoot().size();
        return tree.getChildren().stream().mapToInt(this::getMaxDepth).max().orElse(maxChildDepth);
    }
}
