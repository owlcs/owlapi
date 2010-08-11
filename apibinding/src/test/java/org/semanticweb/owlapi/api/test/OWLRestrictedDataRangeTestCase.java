package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.OWLDataUtil;
import org.semanticweb.owlapi.vocab.OWLFacet;

import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLRestrictedDataRangeTestCase extends AbstractOWLDataFactoryTest {


    public void testCreation() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictions = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);
        OWLDatatypeRestriction restRng = getFactory().getOWLDatatypeRestriction(rng, restrictions);

        assertNotNull(restRng);
    }


    public void testEqualsPositive() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictions = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);

        OWLDatatypeRestriction restRngA = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        OWLDatatypeRestriction restRngB = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        assertEquals(restRngA, restRngB);
    }


    public void testEqualsNegative() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictionsA = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);
        Set<OWLFacetRestriction> restrictionsB = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MIN_INCLUSIVE, facetValue);

        OWLDatatypeRestriction restRngA = getFactory().getOWLDatatypeRestriction(rng, restrictionsA);
        OWLDatatypeRestriction restRngB = getFactory().getOWLDatatypeRestriction(rng, restrictionsB);
        assertNotEquals(restRngA, restRngB);
    }


    public void testHashCode() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictions = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);

        OWLDatatypeRestriction restRngA = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        OWLDatatypeRestriction restRngB = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        assertEquals(restRngA.hashCode(), restRngB.hashCode());
    }
}
