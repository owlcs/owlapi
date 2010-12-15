package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 *
 * An <code>OWLMetric</code> that returns an integer value
 */
public abstract class IntegerValuedMetric extends AbstractOWLMetric<Integer> {

    protected IntegerValuedMetric(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }
}
