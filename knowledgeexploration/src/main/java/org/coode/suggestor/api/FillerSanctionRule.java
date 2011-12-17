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

/**
 * <p>A pluggable way of determining if a certain filler is "interesting" for a given (class, property) pair</p>
 */
public interface FillerSanctionRule {

    /**
     * Called by the suggestor when the rule has been registered (in case the rule requires suggestor methods)
     * @param fs the FillerSuggestor
     */
    void setSuggestor(FillerSuggestor fs);

    /**
     * @param c a class expression
     * @param p an object property
     * @param f a filler class expression
     * @return true if f is an "interesting" filler to use in the axiom SubClassOf(c, p some f)
     */
    boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f);

    /**
     * @param c a class expression
     * @param p a data property
     * @param f a filler data range
     * @return true if f is an "interesting" filler to use in the axiom SubClassOf(c, p some f)
     */
    boolean meetsSanction(OWLClassExpression c, OWLDataProperty p, OWLDataRange f);

}
