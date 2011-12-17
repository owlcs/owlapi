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
package org.coode.suggestor.knowledgeexplorationimpl;

import org.coode.suggestor.api.PropertySanctionRule;
import org.coode.suggestor.api.PropertySuggestor;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner;


/**
 * A sanctioning rule that checks the domains of a property to try to determine
 * if a property is sanctioned for a class.
 */
public class DomainsSanctionRule implements PropertySanctionRule {

    private OWLKnowledgeExplorerReasoner r;
    private OWLDataFactory df;

    public void setSuggestor(PropertySuggestor ps) {
        this.r = (OWLKnowledgeExplorerReasoner)ps.getReasoner();
        this.df = r.getRootOntology().getOWLOntologyManager().getOWLDataFactory();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p) {
        return false;
//        return isInAssertedDomain(descr, p) && ReasonerHelper.getSubProperties(p, r).isEmpty();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLDataProperty p) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

//    private boolean isInAssertedDomain(OWLClassExpression descr, OWLPropertyExpression prop) {
//        OWLClassExpression assertedDomain = getGlobalAssertedDomain(prop);
//        return !assertedDomain.equals(df.getOWLThing()) &&
//               ReasonerHelper.isDescendantOf(descr, assertedDomain, r);
//    }
//
//    private OWLClassExpression getGlobalAssertedDomain(OWLPropertyExpression prop) {
//
//        final Set<OWLOntology> onts = ReasonerHelper.getOntologies(r);
//
//        Set<OWLClassExpression> assertedDomain = prop.getDomains(onts);
//        for (OWLPropertyExpression ancestorProp : ReasonerHelper.getAncestorProperties(prop, r)){
//            assertedDomain.addAll(ancestorProp.getDomains(onts));
//        }
//        // need to filter to remove redundant domains (supers of others) as getDomains() just returns all of them
//        assertedDomain = ReasonerHelper.filterClassExpressions(assertedDomain, r);
//
//        if (assertedDomain.isEmpty()) {
//            return df.getOWLThing();
//        }
//        else if (assertedDomain.size() == 1) {
//            return assertedDomain.iterator().next();
//        }
//        else {
//            // the set of domains on a property is interpreted as an intersection - MJH
//            return df.getOWLObjectIntersectionOf(assertedDomain);
//        }
//    }
}
