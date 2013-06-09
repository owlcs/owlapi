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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRestriction;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectRestriction;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group Date: 25-Oct-2006 */
@SuppressWarnings("javadoc")
public abstract class AbstractOWLRestrictionWithFillerTestCase<P extends OWLPropertyExpression, F extends OWLObject>
        extends AbstractOWLRestrictionTestCase<P> {
    protected abstract OWLObjectRestriction createObjectRestriction(
            OWLObjectProperty prop, OWLClassExpression filler);

    protected abstract OWLDataRestriction createDataRestriction(OWLDataProperty prop,
            OWLDataRange filler);

    @Override
    protected OWLObjectRestriction createObjectRestriction(OWLObjectProperty prop) {
        return createObjectRestriction(prop, createObjectFiller());
    }

    @Override
    protected OWLDataRestriction createDataRestriction(OWLDataProperty prop) {
        return createDataRestriction(prop, createDataFiller());
    }

    @Test
    public void testDataCreation() {
        assertNotNull("restriction should not be null",
                createDataRestriction(createDataProperty(), createDataFiller()));
    }

    @Test
    public void testDataEqualsPositive() {
        OWLDataProperty prop = createDataProperty();
        OWLDataRange filler = createDataFiller();
        OWLDataRestriction restA = createDataRestriction(prop, filler);
        OWLDataRestriction restB = createDataRestriction(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testDataEqualsNegative() {
        // Different filler
        OWLDataProperty prop = createDataProperty();
        OWLDataRestriction restA = createDataRestriction(prop, createDataFiller());
        OWLDataRestriction restB = createDataRestriction(prop, createDataFiller());
        assertFalse(restA.equals(restB));
        // Different property
        OWLDataRange filler = createDataFiller();
        OWLDataRestriction restC = createDataRestriction(createDataProperty(), filler);
        OWLDataRestriction restD = createDataRestriction(createDataProperty(), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testDataHashCode() {
        OWLDataProperty prop = createDataProperty();
        OWLDataRange filler = createDataFiller();
        OWLDataRestriction restA = createDataRestriction(prop, filler);
        OWLDataRestriction restB = createDataRestriction(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Override
    @Test
    public void testCreation() throws Exception {
        assertNotNull("restriction should not be null",
                createObjectRestriction(createObjectProperty(), createObjectFiller()));
    }

    @Override
    @Test
    public void testEqualsPositive() throws Exception {
        OWLObjectProperty prop = createObjectProperty();
        OWLClassExpression filler = createObjectFiller();
        OWLObjectRestriction restA = createObjectRestriction(prop, filler);
        OWLObjectRestriction restB = createObjectRestriction(prop, filler);
        assertEquals(restA, restB);
    }

    @Override
    @Test
    public void testEqualsNegative() throws Exception {
        // Different filler
        OWLObjectProperty prop = createObjectProperty();
        OWLObjectRestriction restA = createObjectRestriction(prop, createObjectFiller());
        OWLObjectRestriction restB = createObjectRestriction(prop, createObjectFiller());
        assertFalse(restA.equals(restB));
        // Different property
        OWLClassExpression filler = createObjectFiller();
        OWLObjectRestriction restC = createObjectRestriction(createObjectProperty(),
                filler);
        OWLObjectRestriction restD = createObjectRestriction(createObjectProperty(),
                filler);
        assertFalse(restC.equals(restD));
    }

    @Override
    @Test
    public void testHashCode() throws Exception {
        OWLObjectProperty prop = createObjectProperty();
        OWLClassExpression filler = createObjectFiller();
        OWLObjectRestriction restA = createObjectRestriction(prop, filler);
        OWLObjectRestriction restB = createObjectRestriction(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }
}
