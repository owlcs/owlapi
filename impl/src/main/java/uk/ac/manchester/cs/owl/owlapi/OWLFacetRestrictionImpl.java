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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLFacetRestrictionImpl extends OWLObjectImpl implements OWLFacetRestriction {

    private final @Nonnull OWLFacet facet;
    private final @Nonnull OWLLiteral facetValue;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 7;
    }

    /**
     * @param facet
     *        facet
     * @param facetValue
     *        value
     */
    public OWLFacetRestrictionImpl(OWLFacet facet, OWLLiteral facetValue) {
        this.facet = checkNotNull(facet, "facet cannot be null");
        this.facetValue = checkNotNull(facetValue, "facetValue cannot be null");
    }

    @Override
    public OWLFacet getFacet() {
        return facet;
    }

    @Override
    public OWLLiteral getFacetValue() {
        return facetValue;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof OWLFacetRestriction) {
            OWLFacetRestriction object = (OWLFacetRestriction) obj;
            return facet.equals(object.getFacet()) && facetValue.equals(object.getFacetValue());
        }
        return false;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLFacetRestriction other = (OWLFacetRestriction) object;
        int diff = facet.compareTo(other.getFacet());
        if (diff != 0) {
            return diff;
        }
        return facetValue.compareTo(other.getFacetValue());
    }
}
