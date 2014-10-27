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
package org.semanticweb.owlapi.model.providers;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;

/** Datatype expression and facet expression provider. */
public interface DatatypeExpressionProvider extends LiteralProvider,
        OWLVocabularyProvider {

    /**
     * OWLDatatypeRestriction <a
     * href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">see
     * spec</a>
     * 
     * @param dataType
     *        datatype for the restriction
     * @param facetRestrictions
     *        facet restrictions. Cannot contain nulls.
     * @return an OWLDatatypeRestriction for the specified data type and
     *         restrictions
     */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeRestriction(
            @Nonnull OWLDatatype dataType,
            @Nonnull Set<OWLFacetRestriction> facetRestrictions);

    /**
     * OWLDatatypeRestriction <a
     * href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">see
     * spec</a>
     * 
     * @param dataType
     *        datatype for the restriction
     * @param facet
     *        facet for restriction
     * @param typedLiteral
     *        literal for facet.
     * @return an OWLDatatypeRestriction with given value for the specified
     *         facet
     */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeRestriction(
            @Nonnull OWLDatatype dataType, @Nonnull OWLFacet facet,
            @Nonnull OWLLiteral typedLiteral);

    /**
     * @param dataType
     *        datatype for the restriction
     * @param facetRestrictions
     *        facet restrictions. Cannot contain nulls.
     * @return an OWLDatatypeRestriction for the specified data type and
     *         restrictions
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeRestriction(
            @Nonnull OWLDatatype dataType,
            @Nonnull OWLFacetRestriction... facetRestrictions) {
        checkIterableNotNull(facetRestrictions,
                "facetRestrictions cannot be null", true);
        return getOWLDatatypeRestriction(dataType,
                CollectionFactory.createSet(facetRestrictions));
    }

    /**
     * Creates a datatype restriction on xsd:integer with a minInclusive facet
     * restriction
     * 
     * @param minInclusive
     *        The value of the min inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(
            int minInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    /**
     * Creates a datatype restriction on xsd:integer with a maxInclusive facet
     * restriction
     * 
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(
            double maxInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    /**
     * Creates a datatype restriction on xsd:integer with min and max inclusive
     * facet restrictions
     * 
     * @param minInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype.
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(
            int minInclusive, int maxInclusive) {
        return getOWLDatatypeRestriction(
                getIntegerOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE,
                        getOWLLiteral(minInclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    /**
     * Creates a datatype restriction on xsd:integer with a minExclusive facet
     * restriction
     * 
     * @param minExclusive
     *        The value of the min exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(
            int minExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    /**
     * Creates a datatype restriction on xsd:integer with a maxExclusive facet
     * restriction
     * 
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(
            int maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    /**
     * Creates a datatype restriction on xsd:integer with min and max exclusive
     * facet restrictions
     * 
     * @param minExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype.
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(
            int minExclusive, int maxExclusive) {
        return getOWLDatatypeRestriction(
                getIntegerOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE,
                        getOWLLiteral(minExclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    /**
     * Creates a datatype restriction on xsd:double with a minInclusive facet
     * restriction
     * 
     * @param minInclusive
     *        The value of the min inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(
            double minInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    /**
     * Creates a datatype restriction on xsd:double with min and max inclusive
     * facet restrictions
     * 
     * @param minInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype.
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(
            double minInclusive, double maxInclusive) {
        return getOWLDatatypeRestriction(
                getDoubleOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE,
                        getOWLLiteral(minInclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    /**
     * Creates a datatype restriction on xsd:double with a minExclusive facet
     * restriction
     * 
     * @param minExclusive
     *        The value of the min exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(
            double minExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    /**
     * Creates a datatype restriction on xsd:integer with a maxInclusive facet
     * restriction
     * 
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(
            int maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    /**
     * Creates a datatype restriction on xsd:double with a maxExclusive facet
     * restriction
     * 
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(
            double maxExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    /**
     * Creates a datatype restriction on xsd:double with min and max exclusive
     * facet restrictions
     * 
     * @param minExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype.
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    @Nonnull
    default OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(
            double minExclusive, double maxExclusive) {
        return getOWLDatatypeRestriction(
                getDoubleOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE,
                        getOWLLiteral(minExclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    /**
     * @param facet
     *        facet for restriction.
     * @param facetValue
     *        literal for restriction.
     * @return an OWLFacetRestriction on specified facet and value
     */
    @Nonnull
    OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            @Nonnull OWLLiteral facetValue);

    /**
     * @param facet
     *        facet for restriction.
     * @param facetValue
     *        facet value
     * @return an OWLFacetRestriction on specified facet and value
     */
    @Nonnull
    default OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            int facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    /**
     * @param facet
     *        facet for restriction
     * @param facetValue
     *        facet value.
     * @return an OWLFacetRestriction on specified facet and value
     */
    @Nonnull
    default OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            double facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    /**
     * @param facet
     *        facet for restriction
     * @param facetValue
     *        facet value.
     * @return an OWLFacetRestriction on specified facet and value
     */
    @Nonnull
    default OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            float facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }
}
