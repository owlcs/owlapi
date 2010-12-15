package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class DoubleValuedMetric extends AbstractOWLMetric<Double> {

    public DoubleValuedMetric(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }
}
