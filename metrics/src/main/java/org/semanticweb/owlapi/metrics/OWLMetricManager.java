package org.semanticweb.owlapi.metrics;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class OWLMetricManager {

    private List<OWLMetric<?>> metrics;


    public OWLMetricManager(List<OWLMetric<?>> metrics) {
        this.metrics = new ArrayList<OWLMetric<?>>(metrics);
    }

    public void setOntology(OWLOntology ontology) {
        for (OWLMetric<?> metric : metrics) {
            metric.setOntology(ontology);
        }
    }


    public List<OWLMetric<?>> getMetrics() {
        return new ArrayList<OWLMetric<?>>(metrics);
    }


    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OWLMetric<?> m : metrics) {
            sb.append(m);
            sb.append("\n");
        }
        return sb.toString();
    }
}
