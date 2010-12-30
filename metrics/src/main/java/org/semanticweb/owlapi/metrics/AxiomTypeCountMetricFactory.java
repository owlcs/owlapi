package org.semanticweb.owlapi.metrics;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class AxiomTypeCountMetricFactory {

    public static Set<OWLMetric<?>> createMetrics(OWLOntologyManager manager) {
        Set<OWLMetric<?>> metrics = new HashSet<OWLMetric<?>>();
        for(AxiomType<?> axiomType : AxiomType.AXIOM_TYPES) {
            metrics.add(new AxiomTypeMetric(manager, axiomType));
        }
        return metrics;
    }

    
}
