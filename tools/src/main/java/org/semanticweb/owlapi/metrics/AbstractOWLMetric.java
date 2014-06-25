/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.metrics;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 * @param <M>
 *        the metric type
 */
public abstract class AbstractOWLMetric<M extends Serializable> implements
        OWLMetric<M>, OWLOntologyChangeListener {

    @Nonnull
    private OWLOntology ontology;
    private boolean dirty;
    private boolean importsClosureUsed;
    private M value;

    /**
     * Instantiates a new abstract owl metric.
     * 
     * @param o
     *        the ontology to use
     */
    public AbstractOWLMetric(@Nonnull OWLOntology o) {
        ontology = checkNotNull(o, "o cannot be null");
        ontology.getOWLOntologyManager().addOntologyChangeListener(this);
        dirty = true;
    }

    @Override
    public OWLOntology getOntology() {
        return ontology;
    }

    @Override
    public void setOntology(OWLOntology ontology) {
        this.ontology.getOWLOntologyManager()
                .removeOntologyChangeListener(this);
        this.ontology = ontology;
        this.ontology.getOWLOntologyManager().addOntologyChangeListener(this);
        setDirty(true);
    }

    /**
     * Recompute metric.
     * 
     * @return the m
     */
    @Nonnull
    protected abstract M recomputeMetric();

    @Override
    public M getValue() {
        if (dirty) {
            value = recomputeMetric();
        }
        return verifyNotNull(value);
    }

    private void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Gets the ontologies.
     * 
     * @return ontologies as a set
     */
    @Nonnull
    public Set<OWLOntology> getOntologies() {
        if (importsClosureUsed) {
            return ontology.getImportsClosure();
        } else {
            return CollectionFactory.createSet(ontology);
        }
    }

    @Override
    public void ontologiesChanged(List<? extends OWLOntologyChange> changes) {
        if (isMetricInvalidated(changes)) {
            setDirty(true);
        }
    }

    @Override
    public OWLOntologyManager getManager() {
        return ontology.getOWLOntologyManager();
    }

    @Override
    public void dispose() {
        ontology.getOWLOntologyManager().removeOntologyChangeListener(this);
        disposeMetric();
    }

    @Override
    public boolean isImportsClosureUsed() {
        return importsClosureUsed;
    }

    @Override
    public void setImportsClosureUsed(boolean b) {
        importsClosureUsed = b;
        recomputeMetric();
    }

    /**
     * Determines if the specified list of changes will cause the value of this
     * metric to be invalid.
     * 
     * @param changes
     *        The list of changes which will be examined to determine if the
     *        metric is now invalid.
     * @return {@code true} if the metric value is invalidated by the specified
     *         list of changes, or {@code false} if the list of changes do not
     *         cause the value of this metric to be invalidated.
     */
    protected abstract boolean isMetricInvalidated(
            @Nonnull List<? extends OWLOntologyChange> changes);

    /** Dispose metric. */
    protected abstract void disposeMetric();

    @Nonnull
    @Override
    public String toString() {
        return getName() + ": " + getValue();
    }
}
