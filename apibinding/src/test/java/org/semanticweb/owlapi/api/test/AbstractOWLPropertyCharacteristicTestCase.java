package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLPropertyCharacteristicTestCase<P extends OWLPropertyExpression> extends AbstractOWLDataFactoryTest {


    @Override
	public void testCreation() throws Exception {
        OWLPropertyAxiom axiom = createOWLPropertyAxiom(createProperty());
        assertNotNull(axiom);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        P prop = createProperty();
        OWLPropertyAxiom axiomA = createOWLPropertyAxiom(prop);
        OWLPropertyAxiom axiomB = createOWLPropertyAxiom(prop);
        assertEquals(axiomA, axiomB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        OWLPropertyAxiom axiomA = createOWLPropertyAxiom(createProperty());
        OWLPropertyAxiom axiomB = createOWLPropertyAxiom(createProperty());
        assertNotEquals(axiomA, axiomB);
    }

    protected abstract P createProperty() throws Exception;

    protected abstract OWLPropertyAxiom createOWLPropertyAxiom(P property) throws OWLException;


    @Override
	public void testHashCode() throws Exception {
        P prop = createProperty();
        OWLPropertyAxiom axiomA = createOWLPropertyAxiom(prop);
        OWLPropertyAxiom axiomB = createOWLPropertyAxiom(prop);
        assertEquals(axiomA.hashCode(), axiomB.hashCode());
    }
}
