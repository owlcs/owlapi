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

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * An enumeration for human readable values to include/exclude imports from
 * searches
 * 
 * @author ignazio
 * @since 4.0.0
 */
public enum Imports {
    /** imports are included */
    INCLUDED {

        @Override
        public Stream<OWLOntology> stream(OWLOntology o) {
            return o.getImportsClosure().stream();
        }

        @Override
        public boolean anyMatch(OWLOntology o, Predicate<OWLOntology> base,
                Predicate<OWLOntology> step) {
            return stream(o).anyMatch(step);
        }
    },
    /** imports are excluded */
    EXCLUDED {

        @Override
        public Stream<OWLOntology> stream(OWLOntology o) {
            return Stream.of(o);
        }

        @Override
        public boolean anyMatch(OWLOntology o, Predicate<OWLOntology> base,
                Predicate<OWLOntology> step) {
            return base.test(o);
        }
    };

    /**
     * @param o
     *        input ontology
     * @return if the import closure should be included, return a stream with
     *         all the ontologies in the imports closure. Otherwise, return a
     *         stream with a single ontology - the input ontology.
     */
    public abstract Stream<OWLOntology> stream(OWLOntology o);

    /**
     * @param o
     *        ontology to test
     * @param base
     *        function to use for imports excluded (i.e., base step of
     *        recursion)
     * @param step
     *        function to use for regular recursions tep
     * @return true if one of the predicates returns true at any point.
     */
    public abstract boolean anyMatch(OWLOntology o,
            Predicate<OWLOntology> base, Predicate<OWLOntology> step);

    public boolean anyMatch(OWLOntology o, Predicate<OWLOntology> step) {
        return stream(o).anyMatch(step);
    }

    public
            <T>
            Set<T>
            collect(OWLOntology o,
                    Function<? super OWLOntology, ? extends Stream<? extends T>> mapper) {
        return stream(o, mapper).collect(Collectors.toSet());
    }

    public
            <T>
            Stream<T>
            stream(OWLOntology o,
                    Function<? super OWLOntology, ? extends Stream<? extends T>> mapper) {
        return stream(o).flatMap(mapper);
    }

    public int count(OWLOntology o, ToIntFunction<? super OWLOntology> step) {
        return stream(o).collect(Collectors.summingInt(step)).intValue();
    }

    /**
     * Transform a boolean arg in an Imports arg. True means INCLUDED
     * 
     * @param b
     *        boolean argument
     * @return INCLUDED for true, EXCLUDED for false.
     */
    @Nonnull
    public static Imports fromBoolean(boolean b) {
        if (b) {
            return INCLUDED;
        }
        return EXCLUDED;
    }
}
