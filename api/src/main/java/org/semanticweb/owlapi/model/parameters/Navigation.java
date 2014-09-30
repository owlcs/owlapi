package org.semanticweb.owlapi.model.parameters;

/** Parameters for navigating class or property hierarchies. */
public enum Navigation {
    /**
     * search for entities in sub position (for subclasses, subproperties
     * axioms).
     */
    IN_SUB_POSITION,
    /**
     * search for entities in super position (for subclasses, subproperties
     * axioms).
     */
    IN_SUPER_POSITION
}
