package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLRestrictionWithFillerTestCase<P extends OWLPropertyExpression, F extends OWLObject> extends AbstractOWLRestrictionTestCase<P> {


    protected abstract OWLRestriction createRestriction(P prop, F filler) throws Exception;


    @Override
	protected OWLRestriction createRestriction(P prop) throws Exception {
        return createRestriction(prop, createFiller());
    }


    protected abstract F createFiller() throws Exception;

    @Override
	public void testCreation() throws Exception {
        assertNotNull(createRestriction(createProperty(), createFiller()));
    }


    @Override
	public void testEqualsPositive() throws Exception {
        P prop = createProperty();
        F filler = createFiller();
        OWLRestriction restA = createRestriction(prop, filler);
        OWLRestriction restB = createRestriction(prop, filler);
        assertEquals(restA, restB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        // Different filler
        P prop = createProperty();
        OWLRestriction restA = createRestriction(prop, createFiller());
        OWLRestriction restB = createRestriction(prop, createFiller());
        assertNotEquals(restA, restB);
        // Different property
        F filler = createFiller();
        OWLRestriction restC = createRestriction(createProperty(), filler);
        OWLRestriction restD = createRestriction(createProperty(), filler);
        assertNotEquals(restC, restD);
    }


    @Override
	public void testHashCode() throws Exception {
        P prop = createProperty();
        F filler = createFiller();
        OWLRestriction restA = createRestriction(prop, filler);
        OWLRestriction restB = createRestriction(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }
}
