package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLNaryOperandsObjectTestCase<O extends OWLObject> extends AbstractOWLDataFactoryTest {

    protected abstract OWLObject createObject(Set<O> operands) throws Exception;

    protected abstract O createOperand() throws Exception;


    @Override
	public void testCreation() throws Exception {
        Set<O> operands = new HashSet<O>();
        operands.add(createOperand());
        operands.add(createOperand());
        operands.add(createOperand());
        OWLObject obj = createObject(operands);
        assertNotNull(obj);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        Set<O> operands = new HashSet<O>();
        operands.add(createOperand());
        operands.add(createOperand());
        operands.add(createOperand());
        OWLObject objA = createObject(operands);
        OWLObject objB = createObject(operands);
        assertEquals(objA, objB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        Set<O> operands = new HashSet<O>();
        operands.add(createOperand());
        operands.add(createOperand());
        OWLObject objA = createObject(operands);
        operands.add(createOperand());
        OWLObject objB = createObject(operands);
        assertNotEquals(objA, objB);
    }


    @Override
	public void testHashCode() throws Exception {
        Set<O> operands = new HashSet<O>();
        operands.add(createOperand());
        operands.add(createOperand());
        operands.add(createOperand());
        OWLObject objA = createObject(operands);
        OWLObject objB = createObject(operands);
        assertEquals(objA.hashCode(), objB.hashCode());
    }
}
