package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


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
