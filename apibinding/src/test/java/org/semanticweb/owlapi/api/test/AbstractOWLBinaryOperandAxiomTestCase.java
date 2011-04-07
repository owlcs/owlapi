/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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


    @Override
	public void testCreation() throws Exception {
        assertNotNull(createAxiom(createLeftOperand(), createRightOperand()));
    }


    @Override
	public void testEqualsPositive() throws Exception {
        L leftOp = createLeftOperand();
        R rightOp = createRightOperand();
        OWLAxiom axiomA = createAxiom(leftOp, rightOp);
        OWLAxiom axiomB = createAxiom(leftOp, rightOp);
        assertEquals(axiomA, axiomB);
    }


    @Override
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


    @Override
	public void testHashCode() throws Exception {
        L leftOperand = createLeftOperand();
        R rightOperand = createRightOperand();
        int hashCodeA = createAxiom(leftOperand, rightOperand).hashCode();
        int hashCodeB = createAxiom(leftOperand, rightOperand).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }
}
