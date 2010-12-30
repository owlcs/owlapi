package org.semanticweb.owlapi.metrics;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class ReferencedObjectPropertyCount extends ObjectCountMetric<OWLObjectProperty> {


    public ReferencedObjectPropertyCount(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	protected String getObjectTypeName() {
        return "Object property";
    }


    @Override
	protected Set<OWLObjectProperty> getObjects(OWLOntology ont) {
        return ont.getObjectPropertiesInSignature();
    }
}
