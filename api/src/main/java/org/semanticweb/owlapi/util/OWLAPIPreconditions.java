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
package org.semanticweb.owlapi.util;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.google.common.base.Optional;

/** a set of personalized preconditions */
public class OWLAPIPreconditions {

    /**
     * check for null and throw NullPointerException if null
     * 
     * @param object
     *        reference to check
     * @param <T>
     *        reference type
     * @return the input reference if not null
     * @throws NullPointerException
     *         if object is null
     */
    @Nonnull
    public static <T> T checkNotNull(T object) {
        return checkNotNull(object, "this variable cannot be null");
    }

    /**
     * check for null and throw NullPointerException if null
     * 
     * @param object
     *        reference to check
     * @param message
     *        message for the illegal argument exception
     * @param <T>
     *        reference type
     * @return the input reference if not null
     * @throws NullPointerException
     *         if object is null
     */
    @Nonnull
    public static <T> T checkNotNull(T object, @Nonnull String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * check for negative value and throw IllegalArgumentException if negative
     * 
     * @param object
     *        value to check
     * @throws IllegalArgumentException
     *         if object is negative
     */
    public static void checkNotNegative(@Nonnegative long object) {
        checkNotNegative(object, "this variable cannot be negative: " + object);
    }

    /**
     * check for negative value and throw IllegalArgumentException if negative
     * 
     * @param object
     *        value to check
     * @param message
     *        message for the illegal argument exception
     * @throws IllegalArgumentException
     *         if object is negative
     */
    public static void checkNotNegative(@Nonnegative long object,
            @Nonnull String message) {
        if (object < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * check for absent and throw IllegalArgumentException if null or absent
     * 
     * @param object
     *        reference to check
     * @param message
     *        message for the illegal argument exception
     * @param <T>
     *        reference type
     * @return the input reference if not null
     * @throws IllegalArgumentException
     *         if object is null
     */
    @SuppressWarnings("null")
    @Nonnull
    public static <T> T
            checkNotNull(Optional<T> object, @Nonnull String message) {
        if (object == null || !object.isPresent()) {
            throw new IllegalArgumentException(message);
        }
        return object.get();
    }
}
