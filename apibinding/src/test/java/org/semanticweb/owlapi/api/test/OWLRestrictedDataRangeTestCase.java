package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.OWLDataUtil;
import org.semanticweb.owlapi.vocab.OWLFacet;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLRestrictedDataRangeTestCase extends AbstractOWLDataFactoryTest {


    @Override
	public void testCreation() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictions = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);
        OWLDatatypeRestriction restRng = getFactory().getOWLDatatypeRestriction(rng, restrictions);

        assertNotNull(restRng);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictions = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);

        OWLDatatypeRestriction restRngA = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        OWLDatatypeRestriction restRngB = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        assertEquals(restRngA, restRngB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictionsA = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);
        Set<OWLFacetRestriction> restrictionsB = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MIN_INCLUSIVE, facetValue);

        OWLDatatypeRestriction restRngA = getFactory().getOWLDatatypeRestriction(rng, restrictionsA);
        OWLDatatypeRestriction restRngB = getFactory().getOWLDatatypeRestriction(rng, restrictionsB);
        assertNotEquals(restRngA, restRngB);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLDatatype rng = getFactory().getOWLDatatype(createIRI());
        OWLLiteral facetValue = getFactory().getOWLLiteral("3", getFactory().getOWLDatatype(createIRI()));
        Set<OWLFacetRestriction> restrictions = OWLDataUtil.getFacetRestrictionSet(getFactory(), OWLFacet.MAX_EXCLUSIVE, facetValue);

        OWLDatatypeRestriction restRngA = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        OWLDatatypeRestriction restRngB = getFactory().getOWLDatatypeRestriction(rng, restrictions);
        assertEquals(restRngA.hashCode(), restRngB.hashCode());
    }
}
