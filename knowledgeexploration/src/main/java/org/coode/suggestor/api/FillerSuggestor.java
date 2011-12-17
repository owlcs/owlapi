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
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.Set;

/**
 * <p>The FillerSuggestor allows us to explore the relationships between the classes in the ontology.</p>
 *
 * <p>For more general discussion of the suggestor idea please see the <a href="package-summary.html">package summary</a></p>
 *
 * <p>To help with the notion of "property values" or "local ranges".</p>
 *
 * <p>Filler level questions - Given a class description and an object property:</p>
 * <ol>
 * <li>What are the named fillers on these properties? (ie on min cardi > 1, exact cardi or existentials)</li>
 * <li>What are the possible named fillers for a new existential restriction?</li>
 * <li>What are the sanctioned named fillers for a new existential restriction?</li>
 * </ol>
 *
 * <p>The following definitions are used in the API definition:</p>
 *
 * <h3>Direct</h3>
 * <p>The direct flag is used to control redundancy. If filler f holds for a query in the general case then
 * the direct case only holds if there is no g where StrictSubClassOf(g, f) and isCurrent(c, p, g) is entailed</p>
 *
 * <p>For the definition of StrictSubClassOf see the OWLAPI {@link org.semanticweb.owlapi.reasoner.OWLReasoner}.</p>
 */
public interface FillerSuggestor {

    /**
     * @param c a class expression
     * @param p an object property
     * @param f a filler class expression
     * @return true if SubClassOf(c, p some f) is entailed
     */
    boolean isCurrent(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f);

    /**
     * @param c a class expression
     * @param p an object property
     * @param f a filler class expression
     * @param direct (see definition above)
     * @return isCurrent(c, p, f). If direct then there is no g where StrictSubClassOf(g, f) and isCurrent(c, p, g) is true
     */
    boolean isCurrent(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f, boolean direct);

    /**
     * @param c a class expression
     * @param p a data property
     * @param f a filler data range
     * @return true if SubClassOf(c, p some f) is entailed
     */
    boolean isCurrent(OWLClassExpression c, OWLDataProperty p, OWLDataRange f);

    // TODO: how do we determine if there is a more specific range on c??
    boolean isCurrent(OWLClassExpression c, OWLDataProperty p, OWLDataRange f, boolean direct);

    /**
     * @param c a class expression
     * @param p an object property
     * @param f a filler class expression
     * @return true if isSatisfiable(c and p some f)
     */
    boolean isPossible(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f);

    /**
     * @param c a class expression
     * @param p a data property
     * @param f a filler data range
     * @return true if isSatisfiable(c and p some f)
     */
    boolean isPossible(OWLClassExpression c, OWLDataProperty p, OWLDataRange f);

    /**
     * @param c a class expression
     * @param p an object property
     * @param f a filler class expression
     * @return true if isPossible(c, p, f) and ANY filler sanction rule is met
     */
    boolean isSanctioned(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f);

    /**
     * @param c a class expression
     * @param p a data property
     * @param f a filler data range
     * @return true if isPossible(c, p, f) and ANY filler sanction rule is met
     */
    boolean isSanctioned(OWLClassExpression c, OWLDataProperty p, OWLDataRange f);

    /**
     * Roughly speaking, would adding SubClassOf(c, p some f) fail to usefully "specialise" c
     * @param c a class expression
     * @param p an object property
     * @param f a filler class expression
     * @return isCurrent(c, p, f) or there is a g such that StrictSubClassOf(g, f) and isCurrent(c, p, g) or SubClassOf(c, p only g)
     */
    boolean isRedundant(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f);

    /**
     * @param c a class expression
     * @param p an object property
     * @param direct (see definition above)
     * @return a set of named class fillers where every f satisfies isCurrent(c, p, f, direct)
     */
    NodeSet<OWLClass> getCurrentNamedFillers(OWLClassExpression c, OWLObjectPropertyExpression p, boolean direct);

    /**
     * Find subclasses (or descendants) of root for which isPossible() holds.
     * @param c a class expression
     * @param p an object property
     * @param root the class from which we start our search
     * @param direct controls whether subclasses or descendants of root are searched
     * @return a set of named class fillers where every f satisfies StrictSubClassOf(f, root) or StrictDescendantOf(f, root) and isPossible(c, p, f)
     */
    NodeSet<OWLClass> getPossibleNamedFillers(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression root, boolean direct);

    /**
     * Find subclasses (or descendants) of root for which isSanctioned() holds.
     * @param c a class expression
     * @param p an object property
     * @param root the class from which we start our search
     * @param direct controls whether subclasses or descendants of root are searched
     * @return a set of named class fillers where every f satisfies StrictSubClassOf(f, root) or StrictDescendantOf(f, root) and isSanctioned(c, p, f, direct)
     */
    Set<OWLClass> getSanctionedFillers(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression root, boolean direct);

    /**
     * Add a FillerSanctionRule to the end of the rules used for sanctioning
     * @param rule the rule to add
     */
    void addSanctionRule(FillerSanctionRule rule);

    /**
     * Remove this FillerSanctionRule from the rules used for sanctioning
     * @param rule the rule to remove
     */
    void removeSanctionRule(FillerSanctionRule rule);

    /**
     * @return get the reasoner used by this suggestor instance
     */
    OWLReasoner getReasoner();
}