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
package org.semanticweb.owlapi.model.parameters;

import java.io.Serializable;

/**
 * Parameters for ontology copying across managers.
 *
 * @since 4.0.0
 */
public interface OntologyCopy extends Serializable {
    /**
     * Enumeration holding known instances.
     */
    enum KnownValues implements OntologyCopy {
        /**
         * the ontology copy will create a new OWLOntology instance with same ontology annotations,
         * same ID and same axioms. Format, document IRI will not be copied over.
         */
        SHALLOW_COPY_ONTOLOGY(false, true, false),
        /**
         * the ontology copy will create a new OWLOntology instance with same ontology annotations,
         * same ID and same axioms. Format, document IRI will be copied over.
         */
        DEEP_COPY_ONTOLOGY(false, false, false),
        /**
         * the ontology copy will remove the ontology from the previous manager. Format, document
         * IRI will be removed from the previous manager.
         */
        MOVE_ONTOLOGY(false, false, true),
        /**
         * the ontology copy will create a new OWLOntology instance for each ontology in the imports
         * closure, with same ontology annotations, same ID and same axioms. Format, document IRI
         * will not be copied over.
         */
        SHALLOW_COPY_ONTOLOGYCLOSURE(true, true, false),
        /**
         * the ontology copy will create a new OWLOntology instance for each ontology in the imports
         * closure, with same ontology annotations, same ID and same axioms. Format, document IRI
         * will be copied over.
         */
        DEEP_COPY_ONTOLOGYCLOSURE(true, false, false),
        /**
         * the ontology copy will remove the imports closure from the previous manager. Format,
         * document IRI will be removed from the previous manager.
         */
        MOVE_ONTOLOGYCLOSURE(true, false, true);

        private final boolean applyToImportsClosure;
        private final boolean applyToAxiomsAndAnnotationsOnly;
        private final boolean shouldMove;

        private KnownValues(boolean applyToImportsClosure, boolean applyToAxiomsAndAnnotationsOnly,
            boolean move) {
            this.applyToImportsClosure = applyToImportsClosure;
            this.applyToAxiomsAndAnnotationsOnly = applyToAxiomsAndAnnotationsOnly;
            shouldMove = move;
        }

        @Override
        public boolean applyToImportsClosure() {
            return applyToImportsClosure;
        }

        @Override
        public boolean applyToAxiomsAndAnnotationsOnly() {
            return applyToAxiomsAndAnnotationsOnly;
        }

        @Override
        public boolean shouldMove() {
            return shouldMove;
        }
    }

    /**
     * the ontology copy will create a new OWLOntology instance with same ontology annotations, same
     * ID and same axioms. Format, document IRI will not be copied over.
     */
    OntologyCopy SHALLOW_COPY = KnownValues.SHALLOW_COPY_ONTOLOGY;
    /**
     * the ontology copy will create a new OWLOntology instance with same ontology annotations, same
     * ID and same axioms. Format, document IRI will be copied over.
     */
    OntologyCopy DEEP_COPY = KnownValues.DEEP_COPY_ONTOLOGY;
    /**
     * the ontology copy will remove the ontology from the previous manager. Format, document IRI
     * will be removed from the previous manager.
     */
    OntologyCopy MOVE = KnownValues.MOVE_ONTOLOGY;
    /**
     * the ontology copy will create a new OWLOntology instance for each ontology in the imports
     * closure, with same ontology annotations, same ID and same axioms. Format, document IRI will
     * not be copied over.
     */
    OntologyCopy SHALLOW_COPY_ONTOLOGY_CLOSURE = KnownValues.SHALLOW_COPY_ONTOLOGYCLOSURE;
    /**
     * the ontology copy will create a new OWLOntology instance for each ontology in the imports
     * closure, with same ontology annotations, same ID and same axioms. Format, document IRI will
     * be copied over.
     */
    OntologyCopy DEEP_COPY_ONTOLOGY_CLOSURE = KnownValues.DEEP_COPY_ONTOLOGYCLOSURE;
    /**
     * the ontology copy will remove the imports closure from the previous manager. Format, document
     * IRI will be removed from the previous manager.
     */
    OntologyCopy MOVE_ONTOLOGY_CLOSURE = KnownValues.MOVE_ONTOLOGYCLOSURE;

    /** @return true if the imports closure of an ontology should also be copied/moved */
    boolean applyToImportsClosure();

    /** @return true if only axioms and annotations should be copied */
    boolean applyToAxiomsAndAnnotationsOnly();

    /** @return true if the ontology should be moved */
    boolean shouldMove();
}
