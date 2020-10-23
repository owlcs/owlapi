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
package org.semanticweb.owlapi6.model.parameters;

import java.io.Serializable;

import javax.annotation.Nullable;

/**
 * An enum for change application success.
 *
 * @author ignazio
 * @since 4.0.0
 */
public interface ChangeApplied extends Serializable {
    /**
     * Enumeration holding known instances.
     */
    enum KnownValues implements ChangeApplied {
        /**
         * Change applied successfully.
         */
        SUCCESSFUL(Boolean.TRUE),
        /**
         * Change not applied.
         */
        UNSUCCESSFUL(Boolean.FALSE),
        /**
         * No operation carried out (change had no effect)
         */
        NO_CHANGE(null);

        @Nullable
        private Boolean toReturn;

        private KnownValues(@Nullable Boolean toReturn) {
            this.toReturn = toReturn;
        }

        @Override
        public Boolean change() {
            return toReturn;
        }
    }

    /**
     * Change applied successfully.
     */
    ChangeApplied SUCCESSFULLY = KnownValues.SUCCESSFUL;
    /**
     * Change not applied.
     */
    ChangeApplied UNSUCCESSFULLY = KnownValues.UNSUCCESSFUL;
    /**
     * No operation carried out (change had no effect)
     */
    ChangeApplied NO_OPERATION = KnownValues.NO_CHANGE;

    /**
     * @return Boolean.TRUE if the change was successful, Boolean.FALSE if the change was
     *         unsuccessful, null if no change happened.
     */
    @Nullable
    Boolean change();

    /** @return true if the change was successful */
    default boolean successful() {
        Boolean change = change();
        return change != null && change.booleanValue();
    }

    /** @return true if the change was unsuccessful */
    default boolean unsuccessful() {
        Boolean change = change();
        return change != null && !change.booleanValue();
    }

    /** @return true if no change happened */
    default boolean noChange() {
        return change() == null;
    }
}
