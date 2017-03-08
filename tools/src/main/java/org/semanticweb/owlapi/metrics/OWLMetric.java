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

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Represents a metric about some aspect of an ontology and possibly its imports closure.
 *
 * @param <M> the metric type
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public interface OWLMetric<M> {

    /**
     * Gets the human readable name of this metric.
     *
     * @return A label which represents the human readable name of this metric.
     */
    String getName();

    /**
     * Gets the value of this metric. This value is computed w.r.t. the current ontology and
     * possibly the imports closure (if specified).
     *
     * @return An object which represents the value of this metric - calling the {@code toString}
     * method of the object returned by this method will result in a human readable string that
     * displays the value of the metric.
     */
    M getValue();

    /**
     * Gets the ontology which the value of the metric should be based on.
     *
     * @return The ontology.
     */
    OWLOntology getOntology();

    /**
     * Sets the "root" ontology. The metric will be recomputed from this ontology (and potentially
     * its imports closure if selected).
     *
     * @param ontology The ontology for which the metric should be computed.
     */
    void setOntology(OWLOntology ontology);

    /**
     * Determines if the computation of the metric should take into account the imports closure of
     * the current ontology.
     *
     * @return {@code true} if the imports closure of the current ontology is taken into account
     * when computing the value of this metric, or {@code false} if the imports closure isn't taken
     * into account when computing this metric.
     */
    boolean isImportsClosureUsed();

    /**
     * Sets whether this metric uses the imports closure of the current ontology.
     *
     * @param b the new imports closure used {@code true} if this metric uses the imports closure of
     * the current ontology, otherwise false.
     */
    void setImportsClosureUsed(boolean b);

    /**
     * Gets the ontology manager which, amongst other things can be used to obtain the imports
     * closure of the current ontology.
     *
     * @return An {@code OWLOntologyManager}.
     */
    OWLOntologyManager getManager();

    /**
     * Diposes of the metric. If the metric attaches itself as a listener to an ontology manager
     * then this will cause the metric to detach itself and stop listening for ontology changes.
     */
    void dispose();
}
