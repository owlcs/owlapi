package org.semanticweb.owlapi.metrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class ObjectCountMetric<E extends Object> extends IntegerValuedMetric {


    public ObjectCountMetric(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }

    protected abstract String getObjectTypeName();


    public String getName() {
        return getObjectTypeName() + " count";
    }

    protected abstract Set<? extends E> getObjects(OWLOntology ont);


    @Override
	public Integer recomputeMetric() {
        return getObjects().size();
    }

    protected Set<? extends E> getObjects() {
        Set<E> objects = new HashSet<E>();
        for(OWLOntology ont : getOntologies()) {
            objects.addAll(getObjects(ont));
        }
        return objects;
    }


    @Override
    @SuppressWarnings("unused")
	protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        return true;
    }

    @Override
	protected void disposeMetric() {
    }
}
