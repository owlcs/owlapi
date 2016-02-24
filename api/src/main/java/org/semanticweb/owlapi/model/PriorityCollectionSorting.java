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

import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi.util.HasPriorityComparator;

/**
 * Specifies how PriorityCollection should sort its entries.
 * 
 * @author Ignazio
 * @since 4.0.2
 */
public enum PriorityCollectionSorting implements ByName<PriorityCollectionSorting> {
    /** Always keep the collection sorted by HasPriority annotation values. */
    ALWAYS {

        @Override
        public <O> List<O> sort(List<O> list) {
            Collections.sort(list, new HasPriorityComparator<>());
            return list;
        }

        @Override
        public <O> List<O> sortInputSet(List<O> list) {
            return sort(list);
        }
    },
    /**
     * Only sort according to HasPriority annotation when a Set is passed in
     * (this happens on Guice injection of entries), do not sort otherwise. This
     * allows for fine tuning of the order in which entries appear, but does not
     * lose the default prioritisation. This is important for parsers. When this
     * sorting is used, addition of new elements causes the elements to be added
     * at the front of the collection (First In, First Out)
     */
    ON_SET_INJECTION_ONLY {

        @Override
        public <O> List<O> sort(List<O> list) {
            return list;
        }

        @Override
        public <O> List<O> sortInputSet(List<O> list) {
            Collections.sort(list, new HasPriorityComparator<>());
            return list;
        }
    },
    /**
     * Sorting of entries is disabled. When this sorting is used, addition of
     * new elements causes the elements to be added at the front of the
     * collection (First In, First Out)
     */
    NEVER {

        @Override
        public <O> List<O> sort(List<O> list) {
            return list;
        }

        @Override
        public <O> List<O> sortInputSet(List<O> list) {
            return list;
        }
    };

    /**
     * @param list
     *        list to sort
     * @param <O>
     *        type of elements
     * @return sorted list
     */
    public abstract <O> List<O> sort(List<O> list);

    /**
     * @param list
     *        list to sort
     * @param <O>
     *        type of elements
     * @return sorted list
     */
    public abstract <O> List<O> sortInputSet(List<O> list);

    @Override
    public PriorityCollectionSorting byName(CharSequence name) {
        return valueOf(name.toString());
    }
}
