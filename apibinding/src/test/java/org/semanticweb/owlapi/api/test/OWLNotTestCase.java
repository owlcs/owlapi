package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLNotTestCase extends AbstractOWLDataFactoryTest {

    @Override
	public void testCreation() throws Exception {
        OWLClassExpression operand = getFactory().getOWLClass(createIRI());
        OWLObjectComplementOf not = getFactory().getOWLObjectComplementOf(operand);
        assertNotNull(not);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLClassExpression operand = getFactory().getOWLClass(createIRI());
        OWLObjectComplementOf notA = getFactory().getOWLObjectComplementOf(operand);
        OWLObjectComplementOf notB = getFactory().getOWLObjectComplementOf(operand);
        assertEquals(notA, notB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        OWLClassExpression operandA = getFactory().getOWLClass(createIRI());
        OWLObjectComplementOf notA = getFactory().getOWLObjectComplementOf(operandA);
        OWLClassExpression operandB = getFactory().getOWLClass(createIRI());
        OWLObjectComplementOf notB = getFactory().getOWLObjectComplementOf(operandB);
        assertNotEquals(notA, notB);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLClassExpression operand = getFactory().getOWLClass(createIRI());
        OWLObjectComplementOf notA = getFactory().getOWLObjectComplementOf(operand);
        OWLObjectComplementOf notB = getFactory().getOWLObjectComplementOf(operand);
        assertEquals(notA.hashCode(), notB.hashCode());
    }
}
