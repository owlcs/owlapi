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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a <a href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions" >
 * DatatypeRestriction</a> data range in the OWL 2 Specification.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLDatatypeRestriction extends OWLDataRange {

    @Override
    default Stream<?> components() {
        return Stream.of(getDatatype(), facetRestrictionsAsList());
    }

    @Override
    default int initHashCode() {
        int hash = hashIndex();
        hash = OWLObject.hashIteration(hash, getDatatype().hashCode());
        return OWLObject.hashIteration(hash, facetRestrictionsAsList().hashCode());
    }

    @Override
    default int hashIndex() {
        return 271;
    }

    @Override
    default int typeIndex() {
        return 4006;
    }

    @Override
    default DataRangeType getDataRangeType() {
        return DataRangeType.DATATYPE_RESTRICTION;
    }

    /**
     * Gets the data range that this data range restricts.
     *
     * @return The datatype that is restricted
     */
    OWLDatatype getDatatype();

    /**
     * Gets the facet restrictions on this data range.
     *
     * @return A {@code Set} of facet restrictions that apply to this data range
     * @deprecated use {@link #facetRestrictions()}
     */
    @Deprecated
    default Set<OWLFacetRestriction> getFacetRestrictions() {
        return asSet(facetRestrictions());
    }

    /**
     * Gets the sorted facet restrictions on this data range.
     *
     * @return A {@code Stream} of facet restrictions that apply to this data range
     */
    Stream<OWLFacetRestriction> facetRestrictions();

    /**
     * Gets the sorted facet restrictions on this data range.
     *
     * @return A {@code List} of facet restrictions that apply to this data range
     */
    default List<OWLFacetRestriction> facetRestrictionsAsList() {
        return asList(facetRestrictions());
    }

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default void accept(OWLDataRangeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
