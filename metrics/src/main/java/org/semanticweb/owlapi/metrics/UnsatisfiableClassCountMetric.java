package org.semanticweb.owlapi.metrics;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Jan-2008<br><br>
 */
public class UnsatisfiableClassCountMetric extends IntegerValuedMetric {

    private OWLReasoner reasoner;

    public UnsatisfiableClassCountMetric(OWLReasoner reasoner, OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
        this.reasoner = reasoner;
    }


    public String getName() {
        return "Unsatisfiable class count";
    }


    @Override
	protected Integer recomputeMetric() {
        return reasoner.getUnsatisfiableClasses().getSize();
    }


    @Override
    @SuppressWarnings("unused")
	protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        return false;
    }


    @Override
	protected void disposeMetric() {
    }
}
