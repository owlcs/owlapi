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

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 * @param <F>
 *        value
 */
public interface OWLCardinalityRestriction<F extends OWLPropertyRange> extends
        OWLQuantifiedRestriction<F>, HasCardinality {

    /**
     * Gets the cardinality of this restriction
     * 
     * @return The cardinality of this restriction
     */
    @Override
    int getCardinality();

    /**
     * Determines if this restriction is qualified. Qualified cardinality
     * restrictions are defined to be cardinality restrictions that have fillers
     * which aren't TOP (owl:Thing or rdfs:Literal). An object restriction is
     * unqualified if it has a filler that is owl:Thing. A data restriction is
     * unqualified if it has a filler which is the top data type (rdfs:Literal).
     * 
     * @return {@code true} if this restriction is qualified, or {@code false}
     *         if this restriction is unqualified.
     */
    boolean isQualified();
}
