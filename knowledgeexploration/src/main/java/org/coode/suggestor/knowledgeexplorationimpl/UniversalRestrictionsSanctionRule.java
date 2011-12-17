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
import org.coode.suggestor.util.RestrictionAccumulator;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner;


/**
 * Check the restrictions on the class for universals
 */
public class UniversalRestrictionsSanctionRule implements PropertySanctionRule {

    private OWLKnowledgeExplorerReasoner r;

    public void setSuggestor(PropertySuggestor ps) {
        this.r =(OWLKnowledgeExplorerReasoner) ps.getReasoner();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p) {
        RestrictionAccumulator acc = new RestrictionAccumulator(r);
        return !acc.getRestrictions(c, p, OWLObjectAllValuesFrom.class).isEmpty();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLDataProperty p) {
        RestrictionAccumulator acc = new RestrictionAccumulator(r);
        return !acc.getRestrictions(c, p, OWLDataAllValuesFrom.class).isEmpty();
    }
}
