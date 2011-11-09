package org.semanticweb.owlapi.reasoner.knowledgeexploration;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public interface OWLKnowledgeExplorerReasoner<O> extends OWLReasoner {
	/**
	 * @param expression
	 *            the expression of which the completion tree is to be computed
	 * @return compute and return the root node of the completion tree for the
	 *         expression
	 */
	O getRoot(OWLClassExpression expression);

	/**
	 * @param node
	 *            a node, as returned by either getRoot() or
	 *            getObjectNeighbours()
	 * @param deterministicOnly
	 *            true if only deterministic results should be returned
	 * */
	Node<? extends OWLObjectPropertyExpression> getObjectNeighbours(O node,
			boolean deterministicOnly);

	/**
	 * @param node
	 *            a node, as returned by either getRoot() or
	 *            getObjectNeighbours()
	 * @param deterministicOnly
	 *            true if only deterministic results should be returned
	 * */
	Node<OWLDataProperty> getDataNeighbours(O node, boolean deterministicOnly);

	/**
	 * @param node
	 *            a node, as returned by either getRoot() or
	 *            getObjectNeighbours()
	 * @param property
	 *            the property being followed
	 * */
	Collection<O> getObjectNeighbours(O node, OWLObjectProperty property);

	/**
	 * @param node
	 *            a node, as returned by either getRoot() or
	 *            getObjectNeighbours()
	 * @param property
	 *            the property being followed
	 * */
	Collection<O> getDataNeighbours(O node, OWLDataProperty property);

	/**
	 * @param node
	 *            a node, as returned by either getRoot() or
	 *            getObjectNeighbours()
	 * @param deterministicOnly
	 *            true if only deterministic results should be returned
	 * */
	Node<? extends OWLClassExpression> getObjectLabel(O node, boolean deterministicOnly);

	/**
	 * @param node
	 *            a node, as returned by getDataNeighbours()
	 * @param deterministicOnly
	 *            true if only deterministic results should be returned
	 * */
	Node<? extends OWLDataRange> getDataLabel(O node, boolean deterministicOnly);
}
