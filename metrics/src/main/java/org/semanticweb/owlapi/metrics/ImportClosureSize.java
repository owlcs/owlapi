package org.semanticweb.owlapi.metrics;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Jan-2008<br><br>
 */
public class ImportClosureSize extends IntegerValuedMetric {


    public ImportClosureSize(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	protected Integer recomputeMetric() {
        return getManager().getImportsClosure(getOntology()).size();
    }


    @Override
	protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for(OWLOntologyChange change : changes) {
            //XXX is this correct?
        }
        return false;
    }


    @Override
	protected void disposeMetric() {
    }


    public String getName() {
        return "Imports closure size";
    }
}
