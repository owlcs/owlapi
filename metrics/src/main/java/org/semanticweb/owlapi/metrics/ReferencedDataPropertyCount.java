package org.semanticweb.owlapi.metrics;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class ReferencedDataPropertyCount extends ObjectCountMetric<OWLDataProperty> {


    public ReferencedDataPropertyCount(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	protected String getObjectTypeName() {
        return "Data property";
    }


    @Override
	protected Set<OWLDataProperty> getObjects(OWLOntology ont) {
        return ont.getDataPropertiesInSignature();
    }
}
