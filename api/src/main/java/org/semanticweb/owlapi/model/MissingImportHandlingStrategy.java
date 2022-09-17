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
package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * Specifies how missing imports should be treated during loading.
 *
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.3
 */
public interface MissingImportHandlingStrategy
    extends ByName<MissingImportHandlingStrategy>, Serializable {
    /**
     * Enumeration holding known instances.
     */
    enum KnownValues implements MissingImportHandlingStrategy {
        /**
         * Specifies that an {@link org.semanticweb.owlapi.model.UnloadableImportException} will
         * NOT be thrown during ontology loading if an import cannot be loaded (for what ever
         * reason). Instead, any registered
         * {@link org.semanticweb.owlapi.model.MissingImportListener}s will be informed of the
         * problem via their
         * {@link org.semanticweb.owlapi.model.MissingImportListener#importMissing(org.semanticweb.owlapi.model.MissingImportEvent)}
         * method.
         */
        NO_EXCEPTION(false),
        /**
         * Specifies that an {@link org.semanticweb.owlapi.model.UnloadableImportException} WILL be
         * thrown during ontology loading if an import cannot be loaded.
         */
        RAISE_EXCEPTION(true);

        private final boolean raiseException;

        private KnownValues(boolean raiseException) {
            this.raiseException = raiseException;
        }

        @Override
        public boolean throwException() {
            return raiseException;
        }
    }

    /**
     * Specifies that an {@link org.semanticweb.owlapi.model.UnloadableImportException} will NOT be
     * thrown during ontology loading if an import cannot be loaded (for what ever reason). Instead,
     * any registered {@link org.semanticweb.owlapi.model.MissingImportListener}s will be informed
     * of the problem via their
     * {@link org.semanticweb.owlapi.model.MissingImportListener#importMissing(org.semanticweb.owlapi.model.MissingImportEvent)}
     * method.
     */
    MissingImportHandlingStrategy SILENT = KnownValues.NO_EXCEPTION;
    /**
     * Specifies that an {@link org.semanticweb.owlapi.model.UnloadableImportException} WILL be
     * thrown during ontology loading if an import cannot be loaded.
     */
    MissingImportHandlingStrategy THROW_EXCEPTION = KnownValues.RAISE_EXCEPTION;

    @Override
    public default MissingImportHandlingStrategy byName(CharSequence name) {
        if ("SILENT".equals(name)) {
            return SILENT;
        }
        if ("THROW_EXCEPTION".equals(name)) {
            return THROW_EXCEPTION;
        }
        throw new IllegalArgumentException(name + " is not a known instance name");
    }

    /** @return true if an exception should be thrown */
    boolean throwException();
}
