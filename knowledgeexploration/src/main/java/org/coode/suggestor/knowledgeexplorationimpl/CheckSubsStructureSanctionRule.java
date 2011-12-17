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
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner;


/**
 * <p>Looks at the direct subclasses to determine which properties are restricted.</p>
 *
 * <p>Sanction is met if for all d where DirectStrictSubClassOf(c, d) if SubClassOf(d, p only x) is asserted (where x is any class expression).</p>
 *
 * <p>NNF is used when evaluating candidate restrictions.</p>
 */
public class CheckSubsStructureSanctionRule implements PropertySanctionRule {

    private OWLKnowledgeExplorerReasoner r;

    public void setSuggestor(PropertySuggestor ps) {
        this.r = (OWLKnowledgeExplorerReasoner)ps.getReasoner();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p) {
        for (Node<OWLClass> sub : r.getSubClasses(c, true)) {
            RestrictionAccumulator acc = new RestrictionAccumulator(r);
            for (OWLClass s : sub.getEntities()){
                for (OWLClassExpression restr : acc.getRestrictions(s, p)) {
                    restr = restr.getNNF();
                    if (restr instanceof OWLObjectAllValuesFrom) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean meetsSanction(OWLClassExpression c, OWLDataProperty p) {
        for (Node<OWLClass> sub : r.getSubClasses(c, true)) {
            RestrictionAccumulator acc = new RestrictionAccumulator(r);
            for (OWLClass s : sub.getEntities()){
                for (OWLClassExpression restr : acc.getRestrictions(s, p)) {
                    restr = restr.getNNF();
                    if (restr instanceof OWLDataAllValuesFrom) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
