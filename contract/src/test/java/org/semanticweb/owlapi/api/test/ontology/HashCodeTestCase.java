/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplBoolean;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplDouble;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplFloat;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplInteger;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplNoCompression;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.2.0
 */
@SuppressWarnings("javadoc")
public class HashCodeTestCase {

    @Test
    public void testSetContainsInt() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(OWL2Datatype.XSD_INTEGER.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("3", null, datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("3", null, datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplInteger(3);
        assertEquals(litNoComp.getLiteral(), litIntImpl.getLiteral());
        Set<OWLLiteral> lncset = new HashSet<>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }

    @Test
    public void testSetContainsDouble() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(OWL2Datatype.XSD_DOUBLE.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("3.0", null, datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("3.0", null, datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplDouble(3.0D);
        assertEquals(litNoComp.getLiteral(), litIntImpl.getLiteral());
        Set<OWLLiteral> lncset = new HashSet<>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }

    @Test
    public void testSetContainsFloat() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(OWL2Datatype.XSD_FLOAT.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("3.0", null, datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("3.0", null, datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplFloat(3.0F);
        assertEquals(litNoComp.getLiteral(), litIntImpl.getLiteral());
        Set<OWLLiteral> lncset = new HashSet<>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }

    @Test
    public void testSetContainsBoolean() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(OWL2Datatype.XSD_BOOLEAN.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("true", null, datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("true", null, datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplBoolean(true);
        assertEquals(litNoComp.getLiteral(), litIntImpl.getLiteral());
        Set<OWLLiteral> lncset = new HashSet<>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }
}
