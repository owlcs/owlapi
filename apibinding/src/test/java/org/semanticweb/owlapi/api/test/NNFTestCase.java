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

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class NNFTestCase extends AbstractOWLAPITestCase {

    public void testPosOWLClass() {
        OWLClass cls = getOWLClass("A");
        assertEquals(cls.getNNF(), cls);
    }


    public void testNegOWLClass() {
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(getOWLClass("A"));
        assertEquals(cls.getNNF(), cls);
    }

    public void testPosAllValuesFrom() {
        OWLClassExpression cls = getFactory().getOWLObjectAllValuesFrom(getOWLObjectProperty("p"), getOWLClass("A"));
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegAllValuesFrom() {
        OWLObjectProperty property = getOWLObjectProperty("p");
        OWLClass filler = getOWLClass("A");
        OWLObjectAllValuesFrom allValuesFrom = getFactory().getOWLObjectAllValuesFrom(property, filler);
        OWLClassExpression cls = allValuesFrom.getObjectComplementOf();
        OWLClassExpression nnf = getFactory().getOWLObjectSomeValuesFrom(property, filler.getObjectComplementOf());
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosSomeValuesFrom() {
        OWLClassExpression cls = getFactory().getOWLObjectSomeValuesFrom(getOWLObjectProperty("p"), getOWLClass("A"));
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegSomeValuesFrom() {
        OWLObjectProperty property = getOWLObjectProperty("p");
        OWLClass filler = getOWLClass("A");
        OWLObjectSomeValuesFrom someValuesFrom = getFactory().getOWLObjectSomeValuesFrom(property, filler);
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(someValuesFrom);
        OWLClassExpression nnf = getFactory().getOWLObjectAllValuesFrom(property, getFactory().getOWLObjectComplementOf(filler));
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosObjectIntersectionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectIntersectionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C"));
        assertEquals(cls.getNNF(), cls);
    }


    public void testNegObjectIntersectionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(getFactory().getOWLObjectIntersectionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C")));
        OWLClassExpression nnf = getFactory().getOWLObjectUnionOf(getFactory().getOWLObjectComplementOf(getOWLClass("A")), getFactory().getOWLObjectComplementOf(getOWLClass("B")), getFactory().getOWLObjectComplementOf(getOWLClass("C")));
        assertEquals(cls.getNNF(), nnf);
    }


    public void testPosObjectUnionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectUnionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C"));
        assertEquals(cls.getNNF(), cls);
    }


    public void testNegObjectUnionOf() {
        OWLClassExpression cls = getFactory().getOWLObjectComplementOf(getFactory().getOWLObjectUnionOf(getOWLClass("A"), getOWLClass("B"), getOWLClass("C")));
        OWLClassExpression nnf = getFactory().getOWLObjectIntersectionOf(getFactory().getOWLObjectComplementOf(getOWLClass("A")), getFactory().getOWLObjectComplementOf(getOWLClass("B")), getFactory().getOWLObjectComplementOf(getOWLClass("C")));
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosObjectMinCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMinCardinality(3, prop, filler);
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegObjectMinCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMinCardinality(3, prop, filler).getObjectComplementOf();
        OWLClassExpression nnf = getFactory().getOWLObjectMaxCardinality(2, prop, filler);
        assertEquals(cls.getNNF(), nnf);
    }

    public void testPosObjectMaxCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMaxCardinality(3, prop, filler);
        assertEquals(cls.getNNF(), cls);
    }

    public void testNegObjectMaxCardinality() {
        OWLObjectProperty prop = getOWLObjectProperty("p");
        OWLClassExpression filler = getOWLClass("A");
        OWLClassExpression cls = getFactory().getOWLObjectMaxCardinality(3, prop, filler).getObjectComplementOf();
        OWLClassExpression nnf = getFactory().getOWLObjectMinCardinality(4, prop, filler);
        assertEquals(cls.getNNF(), nnf);
    }
}
