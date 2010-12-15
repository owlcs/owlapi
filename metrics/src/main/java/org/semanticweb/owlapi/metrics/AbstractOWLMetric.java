package org.semanticweb.owlapi.metrics;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class AbstractOWLMetric<M> implements OWLMetric<M>, OWLOntologyChangeListener {

    private OWLOntologyManager owlOntologyManager;

    private OWLOntology ontology;

    private boolean dirty;

    private boolean importsClosureUsed;

    private M value;

    public AbstractOWLMetric(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
        owlOntologyManager.addOntologyChangeListener(this);
        dirty=true;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    final public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
        setDirty(true);
    }

    protected abstract M recomputeMetric();

    final public M getValue() {
        if(dirty) {
            value = recomputeMetric();
        }
        return value;
    }


    private void setDirty(boolean dirty) {
        this.dirty = dirty;
    }


    public Set<OWLOntology> getOntologies() {
        if (importsClosureUsed) {
            return owlOntologyManager.getImportsClosure(ontology);
        }
        else {
            return Collections.singleton(ontology);
        }
    }

    public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        if(isMetricInvalidated(changes)) {
            setDirty(true);
        }
    }

    public OWLOntologyManager getManager() {
        return owlOntologyManager;
    }


    public void dispose() {
        owlOntologyManager.removeOntologyChangeListener(this);
        disposeMetric();
    }




    final public boolean isImportsClosureUsed() {
        return importsClosureUsed;
    }


    public void setImportsClosureUsed(boolean b) {
        importsClosureUsed = b;
        if(ontology!= null) {
            recomputeMetric();
        }
    }

    /**
     * Determines if the specified list of changes will cause the value of this metric
     * to be invalid.
     * @param changes The list of changes which will be examined to determine if the
     * metric is now invalid.
     * @return <code>true</code> if the metric value is invalidated by the specified
     * list of changes, or <code>false</code> if the list of changes do not cause
     * the value of this metric to be invalidated.
     */
    protected abstract boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes);

    protected abstract void disposeMetric();

    @Override
	public String toString() {
        return getName() + ": " + getValue();
    }
}
