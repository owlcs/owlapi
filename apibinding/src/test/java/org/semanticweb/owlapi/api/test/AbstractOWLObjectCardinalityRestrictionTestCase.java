package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLObjectCardinalityRestrictionTestCase extends AbstractOWLDataFactoryTest {


    protected abstract OWLObjectCardinalityRestriction createRestriction(OWLObjectProperty prop, int cardinality) throws Exception;

    protected abstract OWLObjectCardinalityRestriction createRestriction(OWLObjectProperty prop, int cardinality, OWLClassExpression classExpression) throws Exception;

    public void testCreation() throws Exception {
        OWLObjectProperty prop = getFactory().getOWLObjectProperty(createIRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = createRestriction(prop, cardinality);
        assertNotNull(restA);
        OWLClassExpression cls = getFactory().getOWLClass(createIRI());
        OWLObjectCardinalityRestriction restB = createRestriction(prop, cardinality, cls);
        assertNotNull(restB);
    }


    public void testEqualsPositive() throws Exception {
        OWLObjectProperty prop = getFactory().getOWLObjectProperty(createIRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = createRestriction(prop, cardinality);
        OWLObjectCardinalityRestriction restB = createRestriction(prop, cardinality);
        assertEquals(restA, restB);
        OWLClassExpression cls = getFactory().getOWLClass(createIRI());
        OWLObjectCardinalityRestriction restC = createRestriction(prop, cardinality, cls);
        OWLObjectCardinalityRestriction restD = createRestriction(prop, cardinality, cls);
        assertEquals(restC, restD);
    }


    public void testEqualsNegative() throws Exception {
        OWLObjectProperty prop = getFactory().getOWLObjectProperty(createIRI());
        // Different cardinality
        OWLObjectCardinalityRestriction restA = createRestriction(prop, 3);
        OWLObjectCardinalityRestriction restB = createRestriction(prop, 4);
        assertNotEquals(restA, restB);
        // Different property
        OWLObjectCardinalityRestriction restC = createRestriction(getFactory().getOWLObjectProperty(createIRI()), 3);
        OWLObjectCardinalityRestriction restD = createRestriction(getFactory().getOWLObjectProperty(createIRI()), 3);
        assertNotEquals(restC, restD);
        // Different filler
        OWLObjectCardinalityRestriction restE = createRestriction(prop, 3, getFactory().getOWLClass(createIRI()));
        OWLObjectCardinalityRestriction restF = createRestriction(prop, 3, getFactory().getOWLClass(createIRI()));
        assertNotEquals(restE, restF);
    }


    public void testHashCode() throws Exception {
        OWLObjectProperty prop = getFactory().getOWLObjectProperty(createIRI());
        int cardinality = 3;
        OWLClassExpression cls = getFactory().getOWLClass(createIRI());
        OWLObjectCardinalityRestriction restA = createRestriction(prop, cardinality, cls);
        OWLObjectCardinalityRestriction restB = createRestriction(prop, cardinality, cls);
        assertEquals(restA.hashCode(), restB.hashCode());
    }
}
