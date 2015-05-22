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
 * Represents a
 * <a href="http://www.w3.org/TR/owl2-syntax/#Data_Ranges">DataRange</a> in the
 * OWL 2 Specification.<br>
 * A high level interface which represents a data range. Example of data ranges
 * are datatypes (e.g. int, float, double, string, ...), complements of data
 * ranges (e.g. not(int)), data enumerations (data oneOfs), datatype
 * restrictions (e.g. int &gt; 3).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLDataRange extends OWLObject, OWLPropertyRange, SWRLPredicate, AsOWLDatatype {

    /**
     * Determines if this data range is the top data type.
     * 
     * @return {@code true} if this data range is the top datatype otherwise
     *         {@code false}
     */
    default boolean isTopDatatype() {
        return false;
    }

    /**
     * Gets the type of this data range.
     * 
     * @return The data range type
     */
    DataRangeType getDataRangeType();

    /**
     * @param visitor
     *        visitor
     */
    void accept(OWLDataVisitor visitor);

    /**
     * @param visitor
     *        visitor
     * @param <O>
     *        visitor return type
     * @return visitor return value
     */
    <O> O accept(OWLDataVisitorEx<O> visitor);

    /**
     * @param visitor
     *        visitor
     */
    void accept(OWLDataRangeVisitor visitor);

    /**
     * @param visitor
     *        visitor
     * @param <O>
     *        visitor return type
     * @return visitor return value
     */
    <O> O accept(OWLDataRangeVisitorEx<O> visitor);
}
