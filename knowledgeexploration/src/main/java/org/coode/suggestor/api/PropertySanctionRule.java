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

/**
 * <p>A pluggable way of determining if a property is "interesting" for a given class.</p>
 */
public interface PropertySanctionRule {

    /**
     * Called by the suggestor when the rule has been registered (in case the rule requires suggestor methods)
     * @param ps The PropertySuggestor
     */
    void setSuggestor(PropertySuggestor ps);

    /**
     * @param c a class expression
     * @param p an object property
     * @return true if p is an "interesting" property to use in the axiom SubClassOf(c, p some x)
     */
    boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p);

    /**
     * @param c a class expression
     * @param p a data property
     * @return true if p is an "interesting" property to use in the axiom SubClassOf(c, p some x)
     */
    boolean meetsSanction(OWLClassExpression c, OWLDataProperty p);

}
