package uk.ac.manchester.cs.bhig.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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


    @Override
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

}
