package org.semanticweb.owlapi.reasoner;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 * <p/>
 * An enumeration that denotes various types of inference task.  Each inference task has a name
 * that is associated with it - reasoners may use these names when reporting progress.
 * 
 */
public enum InferenceType {

    /**
     * Denotes the computation of the class hierarchy.
     */
    CLASS_HIERARCHY("class hierarchy"),


    /**
     * Denotes the computation of the object property hierarchy.
     */
    OBJECT_PROPERTY_HIERARCHY("object property hierarchy"),

    /**
     * Denotes the computation of the data property hierarchy.
     */
    DATA_PROPERTY_HIERARCHY("data property hierarchy"),

    /**
     * Denotes the computation of the direct types of individuals for each individual in the
     * signature of the imports closure of the root ontology.
     */
    CLASS_ASSERTIONS("class assertions"),

    /**
     * Denotes the computation of relationships between individuals in the signature of the
     * imports closure of the root ontology.
     */
    OBJECT_PROPERTY_ASSERTIONS("object property assertions"),

    /**
     * Denotes the computation of relationships between individuals and data property values for
     * each individual in the signature of the imports closure of the root ontology.
     */
    DATA_PROPERTY_ASSERTIONS("data property assertions"),

    /**
     * Denotes the computation of individuals that are interpreted as the same object for each
     * individual in the imports closure of the root ontology.
     */
    SAME_INDIVIDUAL("same individuals"),

    /**
     * Denotes the computation of sets of individuals that are different from each individual
     * in the signature of the imports closure of the root ontology.
     */
    DIFFERENT_INDIVIDUALS("different individuals"),

    /**
     * Denotes the computation of sets of classes that are disjoint for each class in the
     * signature of the imports closure of the root ontology.
     */
    DISJOINT_CLASSES("disjoint classes");


    private String name;

    InferenceType(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
