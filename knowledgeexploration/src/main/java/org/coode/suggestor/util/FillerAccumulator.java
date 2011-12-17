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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * An abstract class that helps get all fillers for a given class/property
 */
abstract class FillerAccumulator<O extends OWLObject> extends RestrictionAccumulator {

    private final Set<O> fillers = new HashSet<O>();

    public FillerAccumulator(OWLReasoner r) {
        super(r);
    }

    public Set<O> getFillers(OWLClassExpression descr, OWLPropertyExpression prop) {
        fillers.clear();
        accummulateRestrictions(descr, prop, null);
        return Collections.unmodifiableSet(fillers);
    }

    protected void add(O filler) {
        fillers.add(filler);
    }
}