package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLBinaryOperandAxiomTestCase<L extends OWLObject, R extends OWLObject> extends AbstractOWLDataFactoryTest {

    protected abstract L createLeftOperand() throws Exception;


    protected abstract R createRightOperand() throws Exception;


    protected abstract OWLAxiom createAxiom(L leftOperand, R rightOperand) throws Exception;


    public void testCreation() throws Exception {
        assertNotNull(createAxiom(createLeftOperand(), createRightOperand()));
    }


    public void testEqualsPositive() throws Exception {
        L leftOp = createLeftOperand();
        R rightOp = createRightOperand();
        OWLAxiom axiomA = createAxiom(leftOp, rightOp);
        OWLAxiom axiomB = createAxiom(leftOp, rightOp);
        assertEquals(axiomA, axiomB);
    }


    public void testEqualsNegative() throws Exception {
        L leftOp = createLeftOperand();
        R rightOp = createRightOperand();
        // Different left operand
        OWLAxiom axiomA = createAxiom(createLeftOperand(), rightOp);
        OWLAxiom axiomB = createAxiom(createLeftOperand(), rightOp);
        assertNotEquals(axiomA, axiomB);
        // Different right operand
        OWLAxiom axiomC = createAxiom(leftOp, createRightOperand());
        OWLAxiom axiomD = createAxiom(leftOp, createRightOperand());
        assertNotEquals(axiomC, axiomD);
    }


    public void testHashCode() throws Exception {
        L leftOperand = createLeftOperand();
        R rightOperand = createRightOperand();
        int hashCodeA = createAxiom(leftOperand, rightOperand).hashCode();
        int hashCodeB = createAxiom(leftOperand, rightOperand).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }
}
