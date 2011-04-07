/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
