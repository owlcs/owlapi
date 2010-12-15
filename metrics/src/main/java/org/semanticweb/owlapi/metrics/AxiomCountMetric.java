package org.semanticweb.owlapi.metrics;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Dec-2007<br><br>
 */
public abstract class AxiomCountMetric extends ObjectCountMetric<OWLAxiom> {


    public AxiomCountMetric(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    public Set<? extends OWLAxiom> getAxioms() {
        return getObjects();
    }
}
