package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLIndividualRelationshipAxiomTestCase<P extends OWLPropertyExpression, O extends OWLObject> extends AbstractOWLDataFactoryTest {

    protected abstract P createProperty() throws Exception;

    protected abstract O createObject() throws Exception;

    protected abstract OWLIndividualAxiom createAxiom(OWLIndividual subject, P property, O object) throws OWLException;

    @Override
	public void testCreation() throws Exception {
        assertNotNull(createAxiom(createOWLIndividual(), createProperty(), createObject()));
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLIndividual subject = createOWLIndividual();
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(subject, property, object);
        OWLIndividualAxiom axiomB = createAxiom(subject, property, object);
        assertEquals(axiomA, axiomB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        // Different subject
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(createOWLIndividual(), property, object);
        OWLIndividualAxiom axiomB = createAxiom(createOWLIndividual(), property, object);
        assertNotEquals(axiomA, axiomB);
        // Different property
        OWLIndividual subject = createOWLIndividual();
        OWLIndividualAxiom axiomC = createAxiom(subject, createProperty(), object);
        OWLIndividualAxiom axiomD = createAxiom(subject, createProperty(), object);
        assertNotEquals(axiomC, axiomD);
        // Different object
        OWLIndividualAxiom axiomE = createAxiom(subject, property, createObject());
        OWLIndividualAxiom axiomF = createAxiom(subject, property, createObject());
        assertNotEquals(axiomE, axiomF);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLIndividual subject = createOWLIndividual();
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(subject, property, object);
        OWLIndividualAxiom axiomB = createAxiom(subject, property, object);
        assertEquals(axiomA.hashCode(), axiomB.hashCode());
    }
}
