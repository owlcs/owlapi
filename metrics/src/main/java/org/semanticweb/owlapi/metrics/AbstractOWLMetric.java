package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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

    public String toString() {
        return getName() + ": " + getValue();
    }
}
