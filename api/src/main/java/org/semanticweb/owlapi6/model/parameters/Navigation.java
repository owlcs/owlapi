package org.semanticweb.owlapi6.model.parameters;

import java.io.Serializable;

/**
 * Parameters for navigating class or property hierarchies.
 */
public interface Navigation extends Serializable {
    /**
     * Enumeration holding known instances.
     */
    enum KnownValues implements Navigation {
        /**
         * Search for entities in sub position (for subclasses, subproperties axioms).
         */
        SUB_POSITION(false),
        /**
         * Search for entities in super position (for subclasses, subproperties axioms).
         */
        SUPER_POSITION(true);

        private boolean superPosition;

        private KnownValues(boolean superPosition) {
            this.superPosition = superPosition;
        }

        @Override
        public boolean superPosition() {
            return superPosition;
        }
    }

    /**
     * Search for entities in sub position (for subclasses, subproperties axioms).
     */
    Navigation IN_SUB_POSITION = KnownValues.SUB_POSITION;
    /**
     * Search for entities in super position (for subclasses, subproperties axioms).
     */
    Navigation IN_SUPER_POSITION = KnownValues.SUPER_POSITION;

    /** @return true if entities in super position should be returned */
    boolean superPosition();
}
