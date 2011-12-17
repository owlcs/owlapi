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
package org.coode.suggestor.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Accumulates all restrictions made on a class and its ancestors along the given property (and its descendants).
 */
public class RestrictionAccumulator {

    protected final OWLReasoner r;

    public RestrictionAccumulator(OWLReasoner r) {
        this.r = r;
    }

    public Set<OWLRestriction> getRestrictions(OWLClassExpression cls, OWLPropertyExpression prop) {
        return accummulateRestrictions(cls, prop, null);
    }

    public <T extends OWLRestriction> Set<T> getRestrictions(OWLClassExpression cls, OWLPropertyExpression prop, Class<T> type) {
        Set<T> results = new HashSet<T>();
        for (OWLRestriction restr : accummulateRestrictions(cls, prop, type)){
            results.add((T)restr);
        }
        return results;
    }

    protected Set<OWLRestriction> accummulateRestrictions(OWLClassExpression cls,
                                                        OWLPropertyExpression prop,
                                                        Class<? extends OWLRestriction> type) {

        Set<OWLClass> relevantClasses = r.getSuperClasses(cls, false).getFlattened();

        RestrictionVisitor v = getVisitor(prop, type);

        if (!cls.isAnonymous()) {
            relevantClasses.add(cls.asOWLClass());
        }
        else {
            cls.accept(v);
        }

        final OWLOntology rootOnt = r.getRootOntology();
        final Set<OWLOntology> onts = rootOnt.getImportsClosure();

        for (OWLClass ancestor : relevantClasses) {
            for (OWLOntology ont : onts){
                for (OWLClassExpression restr : ancestor.getSuperClasses(ont)) {
                    restr.accept(v);
                }
                for (OWLClassExpression restr : ancestor.getEquivalentClasses(ont)) {
                    restr.accept(v);
                }
            }
        }

        return v.restrs;
    }

    protected RestrictionVisitor getVisitor(OWLPropertyExpression prop, Class<? extends OWLRestriction> type) {
        return new RestrictionVisitor(r, prop, type);
    }
}
