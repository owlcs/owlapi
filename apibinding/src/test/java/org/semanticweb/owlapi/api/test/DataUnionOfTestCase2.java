package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;

import java.util.Set;
import java.util.Collections;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 */
public class DataUnionOfTestCase2 extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        OWLDataFactory factory = getFactory();
        OWLDatatype dt = factory.getOWLDatatype(IRI.create("file:/c/test.owlapi#SSN"));
        OWLFacetRestriction fr = factory.getOWLFacetRestriction(OWLFacet.PATTERN, factory.getOWLTypedLiteral("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
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
