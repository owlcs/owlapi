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
package org.coode.suggestor.impl;

import org.coode.suggestor.api.PropertySanctionRule;
import org.coode.suggestor.api.PropertySuggestor;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * <p>Looks at the direct subclasses to determine which properties are used as they may be considered distinguishing features.</p>
 *
 * <p>Sanction is met if for all d where DirectStrictSubClassOf(c, d) if isCurrent(d, p, true) is entailed.</p>
 *
 * <p>See {@link OWLReasoner} for definition of DirectStrictSubClassOf</p>
 */
public class CheckSubsSanctionRule implements PropertySanctionRule {

    private OWLReasoner r;
    private PropertySuggestor ps;

    public void setSuggestor(PropertySuggestor ps) {
        this.ps = ps;
        this.r = ps.getReasoner();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p) {
        for (Node<OWLClass> sub : r.getSubClasses(c, true)) {
            if (ps.isCurrent(sub.getRepresentativeElement(), p, true)) {
                return true;
            }
        }
        return false;
    }

    public boolean meetsSanction(OWLClassExpression c, OWLDataProperty p) {
        for (Node<OWLClass> sub : r.getSubClasses(c, true)) {
            if (ps.isCurrent(sub.getRepresentativeElement(), p, true)) {
                return true;
            }
        }
        return false;
    }
}
