package org.semanticweb.owlapi.reasoner;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Jul-2009
 * </p>
 * 
 * Represents a node (set) of entities.  The entities in a node are equivalent to each other.
 * </p>
 *
 * <h3>Nodes in hierarchies</h3>
 * In the OWL API, a reasoner treats a class hierarchy, an object property hierarchy or a data property hierarchy
 * as a hierarchy (directed acyclic graph - DAG) of <code>Nodes</code>.  Each node contains entities that are equivalent
 * to each other.  A hierarchy contains a <i>top node</i>, which is the ancestor of all nodes in the hierarchy, and
 * a <i>bottom node</i> which is the descendant of all nodes in the hierarchy.
 * </p>
 * In a class hierarchy, the nodes contain <code>OWLClass</code> objects.  The top node contains <code>owl:Thing</code> (and
 * any other named classes that are equivalent to <code>owl:Thing</code>).
 * The bottom node contains <code>owl:Nothing</code>
 * (and any other named classes that are equivalent to <code>owl:Nothing</code> - these classes are <i>unsatisfiable</i>
 * classes).
 * </p>
 * In an object property hierarchy, the nodes contain <code>OWLObjectProperty</code> objects.  The top node contains
 * <code>owl:topObjectProperty</code> (and any other named object properties that are equivalent to <code>owl:topObjectProperty</code>).
 * The bottom node contains <code>owl:bottomObjectProperty</code>
 * (and any other named object properties that are equivalent to <code>owl:bottomObjectProperty</code>).
 * </p>
 * In a data property hierarchy, the nodes contain <code>OWLDataProperty</code> objects.  The top node contains
 * <code>owl:topDataProperty</code> (and any other data properties that are equivalent to <code>owl:topDataProperty</code>).
 * The bottom node contains <code>owl:bottomDataProperty</code>
 * (and any other data properties that are equivalent to <code>owl:bottomDataProperty</code>).
 *
 * <h4>Class Hierarchy Example</h4>
 * The figure below shows an example class hierarchy.  Each box in the hierarchy represents a <code>Node</code>.  In
 * this case the top node contains <code>owl:Thing</code> and the bottom node contains <code>owl:Nothing</code>
 * because the nodes in the hierarchy are <code>OWLClass</code> nodes.  In this case, class <code>G</code>
 * is equivalent to <code>owl:Thing</code> so it appears as an entity in the top node. Similarly, class <code>K</code>
 * is unsatisfiable, so it is equivalent to <code>owl:Nothing</code> and therefore appears in the bottom node containing
 * <code>owl:Nothing</code>.
 * </p>
 *
 * <div align=center">
 *  <img src="../../../../doc-files/hierarchy.png"/>
 * </div>
 */
public interface Node<E extends OWLObject> extends Iterable<E> {

    /**
     * Determines if this node represents the top node (in a hierarchy).
     * For a named class node,
     * the top node is the node that contains <code>owl:Thing</code>. For an object property node, the top node
     * is the node that contains <code>owl:topObjectProperty</code>.  For a data property node, the top node is the
     * node that contains <code>owl:topDataProperty</code>
     * @return
     * <code>true</code> if this node is an <code>OWLClass</code> node and it contains <code>owl:Thing</code>.
     * </p>
     * <code>true</code> if this node is an <code>OWLObjectProperty</code> node and it contains <code>owl:topObjectProperty</code>.
     * </p>
     * <code>true</code> if this node is an <code>OWLDataProperty</code> node and it contains <code>owl:topDataProperty</code>.
     * </p>
     * <code>false</code> if none of the above.
     */
    boolean isTopNode();

    /**
     * Determines if this node represents the bottom node (in a hierarchy).
     * For a named class node,
     * the bottom node is the node that contains <code>owl:Nothing</code>. For an object property node, the bottom node
     * is the node that contains <code>owl:bottomObjectProperty</code>.  For a data property node, the bottom node is the
     * node that contains <code>owl:bottomDataProperty</code>
     * @return
     * <code>true</code> if this node is an <code>OWLClass</code> node and it contains <code>owl:Nothing</code>.
     * </p>
     * <code>true</code> if this node is an <code>OWLObjectProperty</code> node and it contains <code>owl:bottomObjectProperty</code>.
     * </p>
     * <code>true</code> if this node is an <code>OWLDataProperty</code> node and it contains <code>owl:bottomDataProperty</code>.
     * </p>
     * <code>false</code> if none of the above.
     */
    boolean isBottomNode();

    /**
     * Gets the entities contained in this node.  The entities are equivalent to each other.
     * @return The set of entities contained in this <code>Node</code>.
     */
    Set<E> getEntities();

    /**
     * Gets the number of entities contained in this <code>Node</code>
     * @return The number of entities contained in this node.
     */
    int getSize();

    /**
     * Determines if this node contains the specified entity.
     * @param entity The entity to check for
     * @return <code>true</code> if this node contains <code>entity</code>, or <code>false</code> if this node
     * does not contain <code>entity</code>
     */
    boolean contains(E entity);

    /**
     * Gets the entities contained in this node minus the specified entitie <code>e</code>.  This essentially
     * returns the entities that are returned by {@link #getEntities()} minus the specified entity <code>e</code>
     * @param e The entity that, is contained within this node, but should not be included in the return set.
     * @return The set of entities that are contained in this node minus the specified entity, <code>e</code>. If
     * <code>e</code> is not contained within this node then the full set of entities returned is the same as that
     * returned by {@link #getEntities()} 
     */
    Set<E> getEntitiesMinus(E e);

    /**
     * Gets the entities contained in this node minus the top entity.
     * For a node of named classes the top entity is <code>owl:Thing</code>.
     * For a node of object properties the top entity is <code>owl:topObjectProperty</code>.
     * For a node of data properties the top entity is <code>owl:topDataProperty</code>
     * @return The set of entities contained within this node minus the top entity. If this node does not contain
     * the top entity then the set of entities returned is the same as that returned by {@link #getEntities()}.
     */
    Set<E> getEntitiesMinusTop();

    /**
     * Gets the entities contained in this node minus the bottom entity.
     * For a node of named classes the bottom entity is <code>owl:Nothing</code>.
     * For a node of object properties the bottom entity is <code>owl:bottomObjectProperty</code>.
     * For a node of data properties the bottom entity is <code>owl:bottomDataProperty</code>
     * @return The set of entities contained within this node minus the bottom entity. If this node does not contain
     * the bottom entity then the set of entities returned is the same as that returned by {@link #getEntities()}.
     */
    Set<E> getEntitiesMinusBottom();

    /**
     * Determines if this <code>Node</code> contains just one entity.
     * @return <code>true</code> if this <code>Node</code> contains just one entity, otherwise <code>false</code>
     */
    boolean isSingleton();

    /**
     * Gets one of the entities contained in this entity set.  If this is a singleton set it will be the one and
     * only entity.
     * @return An entity from the set of entities contained within this node
     * @throws RuntimeException if this node is empty (it does not contain any entities).
     */
    E getRepresentativeElement();
}
