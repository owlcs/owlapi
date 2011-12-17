/*
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * Author: Nick Drummond
 * http://www.cs.man.ac.uk/~drummond/
 * Bio Health Informatics Group
 * The University Of Manchester
 */
package org.coode.suggestor.api;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.Set;

/**
 * <p>The PropertySuggestor allows us to explore the relationships between the classes and properties in the ontology.</p>
 *
 * <p>For more general discussion of the suggestor idea please see the <a href="package-summary.html">package summary</a></p>
 *
 * <p>Using the suggestor, we can ask the following questions:</p>
 * <ul>
 * <li>Which properties does this class have? (What have we said about this class?)</li>
 * <li>Which properties could we add to this class?</li>
 * <li>Which properties might we be most interested in adding to this class? (Sanctioning)</li>
 * </ul>
 *
 * <p>The following definitions are used in the API definition:</p>
 *
 * <h3>Direct</h3>
 * <p>The direct flag is used to control redundancy. If property p holds for a query in the general case then
 * the direct case only holds if there is no q where StrictSubObjectPropertyOf(q, p) and isCurrent(q, c) is entailed</p>
 *
 * <h3>StrictSub[Object|Data]PropertyOf</h3>
 * <p>For the definition of StrictSubData/ObjectPropertyOf see the OWLAPI {@link org.semanticweb.owlapi.reasoner.OWLReasoner}.</p>
 */
public interface PropertySuggestor {

    /**
     * SubClassOf(c, p some Thing) is entailed
     * 
     * @param c a class expression
     * @param p an object property
     * @return true if SubClassOf(c, p some Thing) is entailed
     */
    boolean isCurrent(OWLClassExpression c, OWLObjectPropertyExpression p);

    /**
     * @param c a class expression
     * @param p an object property
     * @param direct (see definition above)
     * @return isCurrent(c, p). If direct then there is no q where StrictSubObjectPropertyOf(q, p) and isCurrent(c, q) is entailed
     */
    boolean isCurrent(OWLClassExpression c, OWLObjectPropertyExpression p, boolean direct);

    /**
     * @param c a class expression
     * @param p a data property
     * @return true if SubClassOf(c, p some Literal) is entailed
     */
    boolean isCurrent(OWLClassExpression c, OWLDataProperty p);

    /**
     * @param c a class expression
     * @param p a data property
     * @param direct (see definition above)
     * @return isCurrent(c, p). If direct then there is no q where StrictSubDataPropertyOf(q, p) and isCurrent(c, q) is entailed
     */
    boolean isCurrent(OWLClassExpression c, OWLDataProperty p, boolean direct);

    /**
     * @param c a class expression
     * @param p an object property
     * @return true if isSatisfiable(c and p some Thing)
     */
    boolean isPossible(OWLClassExpression c, OWLObjectPropertyExpression p);

    /**
     * @param c a class expression
     * @param p a data property
     * @return true if isSatisfiable(c and p some Literal)
     */
    boolean isPossible(OWLClassExpression c, OWLDataProperty p);

    /**
     * Determine if property p is sanctioned for class c by iterating through all of the registered property sanction rules
     * until one is successful or all fail. Only possible properties can be sanctioned.
     *
     * @param c a class expression
     * @param p an object property
     * @return true if isPossible(c, p) and ANY property sanction rule is met
     */
    boolean isSanctioned(OWLClassExpression c, OWLObjectPropertyExpression p);

    /**
     * Determine if data property p is sanctioned for class c by iterating through all of the registered property sanction rules
     * until one is successful or all fail. Only possible properties can be sanctioned.
     *
     * @param c a class expression
     * @param p a data property
     * @return true if isPossible(c, p) and ANY property sanction rule is met
     */
    boolean isSanctioned(OWLClassExpression c, OWLDataProperty p);
    
    /**
     * @param c a class expression
     * @param direct (see definition above)
     * @return a set of objectproperties where every p satisfies isCurrent(c, p, direct)
     */
    NodeSet<OWLObjectPropertyExpression> getCurrentObjectProperties(OWLClassExpression c, boolean direct);

    /**
     * @param c a class expression
     * @param direct (see definition above)
     * @return a set of dataproperties where every p satisfies isCurrent(c, p, direct)
     */
    NodeSet<OWLDataProperty> getCurrentDataProperties(OWLClassExpression c, boolean direct);

    /**
     * @param c a class expression
     * @param root an ObjectProperty from which we start our search
     * @param direct whether to search the subclasses or descendants of root
     * @return a set of objectproperties where every p satisfies StrictSubPropertyOf(p, root) or DescendantOf(p, root) and isPossible(c, p)
     */
    NodeSet<OWLObjectPropertyExpression> getPossibleObjectProperties(OWLClassExpression c, OWLObjectPropertyExpression root, boolean direct);

    /**
     * @param c a class expression
     * @param root a DataProperty from which we start our search
     * @param direct whether to search the subclasses or descendants of root
     * @return a set of dataproperties where every p satisfies StrictSubPropertyOf(p, root) or DescendantOf(p, root) and isPossible(c, p)
     */
    NodeSet<OWLDataProperty> getPossibleDataProperties(OWLClassExpression c, OWLDataProperty root, boolean direct);

    /**
     * @param c a class expression
     * @param root an ObjectProperty from which we start our search
     * @param direct whether to search the subclasses or descendants of root
     * @return a set of objectproperties where every p satisfies StrictSubPropertyOf(p, root) or DescendantOf(p, root) and isSanctioned(c, p)
     */
    Set<OWLObjectPropertyExpression> getSanctionedObjectProperties(OWLClassExpression c, OWLObjectPropertyExpression root, boolean direct);

    /**
     * @param c a class expression
     * @param root a DataProperty from which we start our search
     * @param direct whether to search the subclasses or descendants of root
     * @return a set of dataproperties where every p satisfies StrictSubPropertyOf(p, root) or DescendantOf(p, root) and isSanctioned(c, p)
     */
    Set<OWLDataProperty> getSanctionedDataProperties(OWLClassExpression c, OWLDataProperty root, boolean direct);

    /**
     * Add a PropertySanctionRule to the end of the rules used for sanctioning
     * @param rule the rule to add
     */
    void addSanctionRule(PropertySanctionRule rule);

    /**
     * Remove this PropertySanctionRule from the rules used for sanctioning
     * @param rule the rule to remove
     */
    void removeSanctionRule(PropertySanctionRule rule);

    /**
     * @return get the reasoner used by this suggestor instance
     */
    OWLReasoner getReasoner();
}