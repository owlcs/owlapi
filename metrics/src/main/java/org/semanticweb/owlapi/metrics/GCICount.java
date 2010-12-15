package org.semanticweb.owlapi.metrics;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class GCICount extends AxiomCountMetric {


    public GCICount(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	protected String getObjectTypeName() {
        return "GCI";
    }


    @Override
	protected Set<? extends OWLAxiom> getObjects(OWLOntology ont) {
        return ont.getGeneralClassAxioms();
    }
}
