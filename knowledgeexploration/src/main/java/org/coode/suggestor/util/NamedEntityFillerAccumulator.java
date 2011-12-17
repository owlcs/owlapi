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


import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Includes both named class fillers and individuals
 */
public class NamedEntityFillerAccumulator extends FillerAccumulator<OWLEntity>{

    public NamedEntityFillerAccumulator(OWLReasoner r) {
        super(r);
    }

    @Override
    protected RestrictionVisitor getVisitor(OWLPropertyExpression prop, Class<? extends OWLRestriction> type) {
        return new RestrictionVisitor(r, prop, type){

            @Override
			public void visit(OWLObjectSomeValuesFrom desc) {
                super.visit(desc);
                if (props.contains(desc.getProperty())) {
                    final OWLClassExpression filler = desc.getFiller();
                    if (!filler.isAnonymous()){
                        add(filler.asOWLClass());
                    }
                }
            }

            @Override
			public void visit(OWLObjectMinCardinality desc) {
                super.visit(desc);
                if (desc.getCardinality() > 0 &&
                    props.contains(desc.getProperty())) {
                    OWLClassExpression filler = desc.getFiller();
                    if (filler != null && !filler.isAnonymous()) {
                        add(filler.asOWLClass());
                    }
                }
            }

            @Override
			public void visit(OWLObjectExactCardinality desc) {
                super.visit(desc);
                if (desc.getCardinality() > 0 &&
                    props.contains(desc.getProperty())) {
                    OWLClassExpression filler = desc.getFiller();
                    if (filler != null && !filler.isAnonymous()) {
                        add(filler.asOWLClass());
                    }
                }
            }

            @Override
			public void visit(OWLObjectHasValue desc) {
                super.visit(desc);
                if (props.contains(desc.getProperty())){
                    final OWLIndividual ind = desc.getValue();
                    if (!ind.isAnonymous()){
                        add(ind.asOWLNamedIndividual());
                    }
                }
            }
        };
    }
}
