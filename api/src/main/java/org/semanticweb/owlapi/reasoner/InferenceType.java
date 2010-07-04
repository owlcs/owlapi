package org.semanticweb.owlapi.reasoner;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 * <p/>
 * An enumeration that denotes various types of inference task.
 */
public enum InferenceType {

    /**
     * Denotes the computation of the class hierarchy.
     */
    CLASS_HIERARCHY,


    /**
     * Denotes the computation of the object property hierarchy.
     */
    OBJECT_PROPERTY_HIERARCHY,

    /**
     * Denotes the computation of the data property hierarchy.
     */
    DATA_PROPERTY_HIERARCHY,

    /**
     * Denotes the computation of the direct types of individuals.
     */
    CLASS_ASSERTIONS,

    /**
     * Denotes the computation of relationships between individuals
     */
    OBJECT_PROPERTY_ASSERTIONS,

    /**
     * Denotes the computation of relationships between individuals and data property values.
     */
    DATA_PROPERTY_ASSERTIONS

    
}
