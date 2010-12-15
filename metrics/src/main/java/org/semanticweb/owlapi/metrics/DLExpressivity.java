package org.semanticweb.owlapi.metrics;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DLExpressivityChecker;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class DLExpressivity extends AbstractOWLMetric<String> {


    public DLExpressivity(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	public String recomputeMetric() {
        DLExpressivityChecker checker = new DLExpressivityChecker(getOntologies());
        return checker.getDescriptionLogicName();
    }


    @Override
	protected void disposeMetric() {
    }


    public String getName() {
        return "DL expressivity";
    }


    @Override
	protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for(OWLOntologyChange change : changes) {
            if(change.isAxiomChange() && change.getAxiom().isLogicalAxiom()) {
                return true;
            }
        }
        return false;
    }
}
