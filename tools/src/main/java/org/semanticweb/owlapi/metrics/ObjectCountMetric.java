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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 * @param <E>
 *        the entity type
 */
public abstract class ObjectCountMetric<E> extends IntegerValuedMetric {

    /**
     * Instantiates a new object count metric.
     * 
     * @param o
     *        ontology to use
     */
    public ObjectCountMetric(@Nonnull OWLOntology o) {
        super(o);
    }

    /**
     * Gets the object type name.
     * 
     * @return the object type name
     */
    @Nonnull
    protected abstract String getObjectTypeName();

    @Nonnull
    @Override
    public String getName() {
        return getObjectTypeName() + " count";
    }

    /**
     * Gets the objects.
     * 
     * @param ont
     *        the ont
     * @return the objects
     */
    @Nonnull
    protected abstract Stream<? extends E> getObjects(@Nonnull OWLOntology ont);

    @Override
    public Integer recomputeMetric() {
        return getObjects().size();
    }

    /**
     * Gets the objects.
     * 
     * @return the objects
     */
    @Nonnull
    protected Set<? extends E> getObjects() {
        return asSet(getOntologies().flatMap(o -> getObjects(o)));
    }

    @Override
    protected boolean isMetricInvalidated(
            List<? extends OWLOntologyChange> changes) {
        return true;
    }

    @Override
    protected void disposeMetric() {}
}
