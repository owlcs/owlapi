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
package org.semanticweb.owlapi6.model;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.annotations.HasPriority;

/**
 * Specifies how PriorityCollection should sort its entries.
 *
 * @author Ignazio
 * @since 4.0.2
 */
public interface PriorityCollectionSorting extends ByName<PriorityCollectionSorting>, Serializable {
    /**
     * Enumeration holding known instances.
     */
    enum KnownValues implements PriorityCollectionSorting {
        /**
         * Always keep the collection sorted by HasPriority annotation values.
         */
        ALWAYS_SORT(KnownValues::sorting, KnownValues::sorting),
        /**
         * Only sort according to HasPriority annotation when a Set is passed in (this happens on
         * constructor injection of entries), do not sort otherwise. This allows for fine tuning of
         * the order in which entries appear, but does not lose the default prioritisation. This is
         * important for parsers. When this sorting is used, addition of new elements causes the
         * elements to be added at the front of the collection (First In, First Out)
         */
        SORT_ON_SET_INJECTION_ONLY(Function.identity()::apply, KnownValues::sorting),
        /**
         * Sorting of entries is disabled. When this sorting is used, addition of new elements
         * causes the elements to be added at the front of the collection (First In, First Out)
         */
        NEVER_SORT(Function.identity()::apply, Function.identity()::apply);

        private final Consumer<List<? extends Object>> regular;
        private final Consumer<List<? extends Object>> forInput;

        KnownValues(Consumer<List<? extends Object>> regular,
            Consumer<List<? extends Object>> forInput) {
            this.regular = regular;
            this.forInput = forInput;
        }

        @Override
        public <O> List<O> sort(List<O> list) {
            regular.accept(list);
            return list;
        }

        @Override
        public <O> List<O> sortInputSet(List<O> list) {
            forInput.accept(list);
            return list;
        }

        /**
         * @param p object to inspect
         * @return value of {@code HasPriority} annotation, or Double.MAX_VALUE if no such
         *         annotation is present
         */
        public static double getPriority(@Nullable Object p) {
            HasPriority priority = checkNotNull(p).getClass().getAnnotation(HasPriority.class);
            if (priority != null) {
                return priority.value();
            }
            // if the object does not have a priority annotation, only use
            // it last
            return Double.MAX_VALUE;
        }

        /**
         * @param a first object to compare
         * @param b second object to compare
         * @return compareTo result using {@code getPriority()} for key extraction and
         *         {@code Double.compare()} for comparison.
         */
        static int compare(Object a, Object b) {
            return Double.compare(getPriority(a), getPriority(b));
        }

        /**
         * @param <Q> type of the elements
         * @param l list to sort
         */
        static <Q> void sorting(List<Q> l) {
            Collections.sort(l, KnownValues::compare);
        }
    }

    /**
     * Always keep the collection sorted by HasPriority annotation values.
     */
    PriorityCollectionSorting ALWAYS = KnownValues.ALWAYS_SORT;
    /**
     * Only sort according to HasPriority annotation when a Set is passed in (this happens on
     * constructor injection of entries), do not sort otherwise. This allows for fine tuning of the
     * order in which entries appear, but does not lose the default prioritisation. This is
     * important for parsers. When this sorting is used, addition of new elements causes the
     * elements to be added at the front of the collection (First In, First Out)
     */
    PriorityCollectionSorting ON_SET_INJECTION_ONLY = KnownValues.SORT_ON_SET_INJECTION_ONLY;
    /**
     * Sorting of entries is disabled. When this sorting is used, addition of new elements causes
     * the elements to be added at the front of the collection (First In, First Out)
     */
    PriorityCollectionSorting NEVER = KnownValues.NEVER_SORT;

    /**
     * @param list list to sort
     * @param <O> type of elements
     * @return sorted list
     */
    <O> List<O> sort(List<O> list);

    /**
     * @param list list to sort
     * @param <O> type of elements
     * @return sorted list
     */
    <O> List<O> sortInputSet(List<O> list);

    @Override
    public default PriorityCollectionSorting byName(CharSequence name) {
        if ("ALWAYS".equals(name)) {
            return ALWAYS;
        }
        if ("NEVER".equals(name)) {
            return NEVER;
        }
        if ("ON_SET_INJECTION_ONLY".equals(name)) {
            return ON_SET_INJECTION_ONLY;
        }
        throw new IllegalArgumentException(name + " is not a known instance name");
    }
}
