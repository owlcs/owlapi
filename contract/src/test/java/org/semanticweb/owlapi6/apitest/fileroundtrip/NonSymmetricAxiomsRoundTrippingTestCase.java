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
package org.semanticweb.owlapi6.apitest.fileroundtrip;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DatatypeDefinition;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.DP;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@RunWith(Parameterized.class)
public class NonSymmetricAxiomsRoundTrippingTestCase extends TestBase {

    private static final OWLDatatype dataD = Datatype(iri("D"));
    private static final OWLDatatype dataE = Datatype(iri("E"));
    private static final OWLObjectSomeValuesFrom d =
        ObjectSomeValuesFrom(P, ObjectIntersectionOf(B, C));
    private static final OWLDataSomeValuesFrom e =
        DataSomeValuesFrom(DP, DataIntersectionOf(dataD, dataE));
    private static final OWLClassExpression du = ObjectUnionOf(B, C);
    private static final OWLDataUnionOf eu = DataUnionOf(dataD, dataE);
    private final OWLAxiom in;
    private final OWLAxiom out;

    public NonSymmetricAxiomsRoundTrippingTestCase(OWLAxiom in, OWLAxiom out) {
        this.in = in;
        this.out = out;
    }

    @Parameters
    public static List<OWLAxiom[]> getData() {
        List<OWLAxiom[]> list = new ArrayList<>();
        list.add(new OWLAxiom[] {SubClassOf(A, ObjectIntersectionOf(d, d)), SubClassOf(A, d)});
        list.add(new OWLAxiom[] {SubClassOf(A, ObjectUnionOf(e, e)), SubClassOf(A, e)});
        list.add(new OWLAxiom[] {SubClassOf(A, ObjectIntersectionOf(du, du)), SubClassOf(A, du)});
        list.add(new OWLAxiom[] {DatatypeDefinition(dataD, DataUnionOf(eu, eu)),
            DatatypeDefinition(dataD, eu)});
        return list;
    }

    @Test
    public void shouldRoundTripAReadableVersion() throws OWLOntologyStorageException {
        OWLOntology output = getOWLOntology();
        output.add(in);
        OWLOntology o = roundTrip(output, new FunctionalSyntaxDocumentFormat());
        assertEquals(1, o.logicalAxioms().count());
        assertEquals(out, o.logicalAxioms().iterator().next());
    }
}
