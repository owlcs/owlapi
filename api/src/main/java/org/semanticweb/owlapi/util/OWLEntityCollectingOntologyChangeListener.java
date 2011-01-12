package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiomChange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Feb-2007<br><br>
 * <p/>
 * A convenience class which is an ontology change listener which collects the
 * entities which are referenced in a set of ontology changes.
 */
public abstract class OWLEntityCollectingOntologyChangeListener implements OWLOntologyChangeListener {

    private Set<OWLEntity> entities;


    public OWLEntityCollectingOntologyChangeListener() {
        entities = new HashSet<OWLEntity>();
    }


    public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        entities.clear();
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange()) {
                OWLAxiomChange axiomChange = (OWLAxiomChange) change;
                entities.addAll(axiomChange.getEntities());
            }
        }
        ontologiesChanged();
    }


    /**
     * Called when a set of changes have been applied.
     */
    public abstract void ontologiesChanged() throws OWLException;


    /**
     * Gets the entities which were referenced in the last change set.
     */
    public Set<OWLEntity> getEntities() {
        return CollectionFactory.getCopyOnRequestSet(entities);
    }
}
