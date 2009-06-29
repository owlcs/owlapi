package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
