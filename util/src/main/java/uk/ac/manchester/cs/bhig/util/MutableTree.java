package uk.ac.manchester.cs.bhig.util;

import java.io.PrintWriter;
import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 22-Jan-2008<br><br>
 */
public class MutableTree<N> implements Tree<N> {

    private N userObject;

    private MutableTree<N> parent;

    private List<MutableTree<N>> children;

    private Map<Tree<N>, Object> child2EdgeMap;

    private NodeRenderer<N> toStringRenderer;

    public MutableTree(N userObject) {
        this.userObject = userObject;
        children = new ArrayList<MutableTree<N>>();
        child2EdgeMap = new HashMap<Tree<N>, Object>();
        toStringRenderer = new NodeRenderer<N>() {
            public String render(Tree<N> object) {
                return object.toString();
            }
        };
    }

    public N getUserObject() {
        return userObject;
    }


    public void setParent(MutableTree<N> parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        this.parent.children.add(this);
    }


    public void addChild(MutableTree<N> child) {
        children.add(child);
        child.parent = this;
    }

    public void addChild(MutableTree<N> child, Object edge) {
        addChild(child);
        child2EdgeMap.put(child, edge);
    }


    public void removeChild(MutableTree<N> child) {
        children.remove(child);
        child.parent = null;
    }


    public Object getEdge(Tree<N> child) {
        return child2EdgeMap.get(child);
    }


    public void sortChildren(Comparator<Tree<N>> comparator) {
        Collections.sort(children, comparator);
    }


    public void clearChildren() {
        for (MutableTree<N> child : new ArrayList<MutableTree<N>>(children)) {
            removeChild(child);
        }
    }


    public Tree<N> getParent() {
        return parent;
    }


    public List<Tree<N>> getChildren() {
        return new ArrayList<Tree<N>>(children);
    }


    public int getChildCount() {
        return children.size();
    }


    public boolean isRoot() {
        return parent == null;
    }


    public boolean isLeaf() {
        return children.isEmpty();
    }


    public Tree<N> getRoot() {
        if (parent == null) {
            return this;
        }
        return parent.getRoot();
    }


    public List<Tree<N>> getPathToRoot() {
        List<Tree<N>> path = new ArrayList<Tree<N>>();
        path.add(0, this);
        Tree<N> par = parent;
        while (par != null) {
            path.add(0, par);
            par = par.getParent();
        }
        return path;
    }


    public List<N> getUserObjectPathToRoot() {
        List<N> path = new ArrayList<N>();
        path.add(0, this.getUserObject());
        Tree<N> par = parent;
        while (par != null) {
            path.add(0, par.getUserObject());
            par = par.getParent();
        }
        return path;
    }


    public Set<N> getUserObjectClosure() {
        Set<N> objects = new HashSet<N>();
        getUserObjectClosure(this, objects);
        return objects;
    }

    private void getUserObjectClosure(Tree<N> tree, Set<N> bin) {
        bin.add(tree.getUserObject());
        for (Tree<N> child : tree.getChildren()) {
            getUserObjectClosure(child, bin);
        }
    }


    public void dump(PrintWriter writer) {
        dump(writer, 0);
    }

    public void dump(PrintWriter writer, int indent) {
        int depth = getPathToRoot().size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth + indent; i++) {
            sb.append("\t");
        }
        writer.print(sb.toString());
        String ren = toStringRenderer.render(this);
        ren = ren.replace("\n", "\n" + sb);
        writer.println(ren);
        for (Tree<N> child : getChildren()) {
            Object edge = getEdge(child);
            if (edge != null) {
                writer.print(sb.toString());
                writer.print("--- ");
                writer.print(edge);
                writer.print(" ---\n\n");
            }
            child.dump(writer, indent);
        }
        writer.flush();
    }

    public void setNodeRenderer(NodeRenderer<N> renderer) {
        this.toStringRenderer = renderer;
        for (Tree<N> child : children) {
            child.setNodeRenderer(toStringRenderer);
        }
    }

    public List<N> fillDepthFirst() {
        List<N> results = new ArrayList<N>();
        fillDepthFirst(this, results);
        return results;
    }

    private void fillDepthFirst(Tree<N> tree, List<N> bin) {
        bin.add(tree.getUserObject());
        for (Tree<N> child : tree.getChildren()) {
            fillDepthFirst(child, bin);
        }
    }

    public void replace(MutableTree<N> tree) {
        parent.children.remove(this);
        parent.children.add(tree);
        parent = null;
        tree.children.clear();
        tree.children.addAll(children);
        children.clear();
    }


    public String toString() {
        if (userObject != null) {
            return userObject.toString();
        } else {
            return "";
        }
    }


    public int getSize() {
        return getUserObjectClosure().size();
    }



    public int getMaxDepth() {
        return getMaxDepth(this);
    }

    private int getMaxDepth(Tree<N> tree) {
        int maxChildDepth = tree.getPathToRoot().size();
        for (Tree<N> child : tree.getChildren()) {
            int childDepth = getMaxDepth(child);
            if(childDepth > maxChildDepth) {
                maxChildDepth = childDepth;
            }
        }
        return maxChildDepth;
    }

    public static void main(String[] args) {
        MutableTree tree = new MutableTree("1");
        for(int i = 0; i < 3; i++) {
            MutableTree tree1 = new MutableTree("1." + i);
            tree.addChild(tree1);
            for(int j = 0; j < 2; j++) {
                tree1.addChild(new MutableTree("2." + i + "." + j ));
            }
        }
        System.out.println(tree.getSize());
        System.out.println(tree.getMaxDepth());
    }
}
