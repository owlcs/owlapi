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

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 */
public class DataUnionOfTestCase2 extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        OWLDataFactory factory = getFactory();
        OWLDatatype dt = factory.getOWLDatatype(IRI.create("file:/c/test.owlapi#SSN"));
        OWLFacetRestriction fr = factory.getOWLFacetRestriction(OWLFacet.PATTERN, factory.getOWLLiteral("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
        OWLDataRange dr = factory.getOWLDatatypeRestriction(factory.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#string")), fr);
        OWLDataIntersectionOf disj1 = factory.getOWLDataIntersectionOf(factory.getOWLDataComplementOf(dr), dt); // here I negate dr
        OWLDataIntersectionOf disj2 = factory.getOWLDataIntersectionOf(factory.getOWLDataComplementOf(dt), dr); // here I negate dt
        OWLDataUnionOf union = factory.getOWLDataUnionOf(disj1, disj2);
        System.out.println(union.toString());
        OWLDataProperty prop = getOWLDataProperty("prop");
        OWLDataPropertyRangeAxiom ax = factory.getOWLDataPropertyRangeAxiom(prop, union);
        return Collections.singleton(ax);
    }
}
