package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDatatype;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDataRangeNotTestCase extends AbstractOWLDataFactoryTest {


    public void testCreation() throws Exception {
        OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
        OWLDataComplementOf rng = getFactory().getOWLDataComplementOf(dt);
        assertNotNull(rng);
    }


    public void testEqualsPositive() throws Exception {
        OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
        OWLDataComplementOf rngA = getFactory().getOWLDataComplementOf(dt);
        OWLDataComplementOf rngB = getFactory().getOWLDataComplementOf(dt);
        assertEquals(rngA, rngB);
    }


    public void testEqualsNegative() throws Exception {
        OWLDatatype dtA = getFactory().getOWLDatatype(createIRI());
        OWLDataComplementOf rngA = getFactory().getOWLDataComplementOf(dtA);
        OWLDatatype dtB = getFactory().getOWLDatatype(createIRI());
        OWLDataComplementOf rngB = getFactory().getOWLDataComplementOf(dtB);
        assertNotEquals(rngA, rngB);
    }


    public void testHashCode() throws Exception {
        OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
        OWLDataComplementOf rngA = getFactory().getOWLDataComplementOf(dt);
        OWLDataComplementOf rngB = getFactory().getOWLDataComplementOf(dt);
        assertEquals(rngA.hashCode(), rngB.hashCode());
    }
}
