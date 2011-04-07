/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.model;
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Data_Ranges">DataRange</a> in the OWL 2 Specification.
 * </p>
 * A high level interface which represents a data range.  Example of
 * data ranges are datatypes (e.g. int, float, double, string, ...),
 * complements of data ranges (e.g. not(int)), data enumerations (data oneOfs),
 * datatype restrictions (e.g. int > 3).
 */
public interface OWLDataRange extends OWLObject, OWLPropertyRange, SWRLPredicate {

    /**
     * Determines if this data range is a datatype (int, float, ...)
     *
     * @return <code>true</code> if this datarange is a datatype, or
     *         <code>false</code> if it is not a datatype and is some other
     *         data range such as a data range restriction, data oneOf or
     *         data complementOf.
     */
    boolean isDatatype();


    /**
     * Determines if this data range is the top data type.
     *
     * @return <code>true</code> if this data range is the top datatype otherwise
     *         <code>false</code>
     */
    boolean isTopDatatype();

    /**
     * If this data range is a datatype then this method may be used to obtain it as a datatype (rather than
     * using an explicit cast).
     * @return This data range as an {@link org.semanticweb.owlapi.model.OWLDatatype}
     */
    OWLDatatype asOWLDatatype();

    /**
     * Gets the type of this data range
     * @return The data range type
     */
    DataRangeType getDataRangeType();

    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);

    void accept(OWLDataRangeVisitor visitor);

    <O> O accept(OWLDataRangeVisitorEx<O> visitor);
}
