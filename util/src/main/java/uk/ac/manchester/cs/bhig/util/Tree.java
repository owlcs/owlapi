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
