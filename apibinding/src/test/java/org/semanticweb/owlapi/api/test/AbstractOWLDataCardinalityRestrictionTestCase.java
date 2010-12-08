package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLDataCardinalityRestrictionTestCase extends AbstractOWLRestrictionTestCase<OWLDataProperty> {

    protected abstract OWLDataCardinalityRestriction createRestriction(OWLDataProperty prop, int cardinality) throws Exception;

    protected abstract OWLDataCardinalityRestriction createRestriction(OWLDataProperty prop, int cardinality, OWLDataRange dataRange) throws Exception;


    protected OWLRestriction createRestriction(OWLDataProperty prop) throws Exception {
        return createRestriction(prop, 3);
    }


    protected OWLDataProperty createProperty() throws OWLException {
        return createOWLDataProperty();
    }


    public void testCreation() throws Exception {
        OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = createRestriction(prop, cardinality);
        assertNotNull(restA);
        OWLDataRange dataRange = getFactory().getOWLDatatype(createIRI());
        OWLDataCardinalityRestriction restB = createRestriction(prop, cardinality, dataRange);
        assertNotNull(restB);
    }


    public void testEqualsPositive() throws Exception {
        OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = createRestriction(prop, cardinality);
        OWLDataCardinalityRestriction restB = createRestriction(prop, cardinality);
        assertEquals(restA, restB);
        OWLDataRange dataRange = getFactory().getOWLDatatype(createIRI());
        OWLDataCardinalityRestriction restC = createRestriction(prop, cardinality, dataRange);
        OWLDataCardinalityRestriction restD = createRestriction(prop, cardinality, dataRange);
        assertEquals(restC, restD);
    }


    public void testEqualsNegative() throws Exception {
        OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
        // Different cardinality
        OWLDataCardinalityRestriction restA = createRestriction(prop, 3);
        OWLDataCardinalityRestriction restB = createRestriction(prop, 4);
        assertNotEquals(restA, restB);
        // Different property
        OWLDataCardinalityRestriction restC = createRestriction(getFactory().getOWLDataProperty(createIRI()), 3);
        OWLDataCardinalityRestriction restD = createRestriction(getFactory().getOWLDataProperty(createIRI()), 3);
        assertNotEquals(restC, restD);
        // Different filler
        OWLDataCardinalityRestriction restE = createRestriction(prop, 3, getFactory().getOWLDatatype(createIRI()));
        OWLDataCardinalityRestriction restF = createRestriction(prop, 3, getFactory().getOWLDatatype(createIRI()));
        assertNotEquals(restE, restF);
    }


    public void testHashCode() throws Exception {
        OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
        int cardinality = 3;
        OWLDataRange dataRange = getFactory().getOWLDatatype(createIRI());
        OWLDataCardinalityRestriction restA = createRestriction(prop, cardinality, dataRange);
        OWLDataCardinalityRestriction restB = createRestriction(prop, cardinality, dataRange);
        assertEquals(restA, restB);
    }
}
