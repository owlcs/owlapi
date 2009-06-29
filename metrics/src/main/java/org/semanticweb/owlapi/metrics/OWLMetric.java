package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
 * <p/>
 * Represents a metric about some aspect of an ontology and possibly its
 * imports closure.
 */
public interface OWLMetric<M> {

    /**
     * Gets the human readable name of this metic
     * @return A label which represents the human readable name of
     *         this metric.
     */
    String getName();


    /**
     * Gets the value of this metric.  This value is computed w.r.t. the
     * current ontology and possibly the imports closure (if specified).
     * @return An object which represents the value of this
     *         metric - calling the <code>toString</code> method of
     *         the object returned by this method will result in a human
     *         readable string that displays the value of the metric.
     */
    M getValue();

    /**
     * Sets the "root" ontology.  The metric will be recomputed
     * from this ontology (and potentially its imports closure
     * if selected).
     * @param ontology The ontology for which the metric should
     *                 be computed.
     */
    void setOntology(OWLOntology ontology);


    /**
     * Gets the ontology which the value of the metric
     * should be based on.
     * @return The ontology.
     */
    OWLOntology getOntology();

    /**
     * Determines if the computation of the metric should take
     * into account the imports closure of the current ontology.
     * @return <code>true</code> if the imports closure of the
     *         current ontology is taken into account when computing the
     *         value of this metric, or <code>false</code> if the imports
     *         closure isn't taken into account when computing this metric.
     */
    boolean isImportsClosureUsed();


    /**
     * Sets whether this metric uses the imports closure of the
     * current ontology
     * @param b <code>true</code> if this metric uses the imports
     * closure of the current ontology, otherwise false.
     */
    void setImportsClosureUsed(boolean b);


    /**
     * Gets the ontology manager which, amongst other things can
     * be used to obtain the imports closure of the current ontology.
     * @return An <code>OWLOntologyManager</code>.
     */
    OWLOntologyManager getManager();


    /**
     * Diposes of the metric.  If the metric attaches itself
     * as a listener to an ontology manager then this will cause
     * the metric to detach itself and stop listening for ontology
     * changes.
     */
    void dispose();
}
