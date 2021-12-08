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
package org.semanticweb.owlapi.api.test.fileroundtrip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class NonSymmetricAxiomsRoundTrippingTestCase extends TestBase {

    static Stream<Arguments> nonSymmetricAxiomsRoundTrippingTestCase() {
        OWLObjectSomeValuesFrom objectSome = ObjectSomeValuesFrom(P, ObjectIntersectionOf(B, C));
        OWLDataSomeValuesFrom dataSome = DataSomeValuesFrom(DP, DataIntersectionOf(DTA, DTB));
        OWLClassExpression du = ObjectUnionOf(B, C);
        OWLDataUnionOf eu = DataUnionOf(DTA, DTB);
        return Stream.of(
            Arguments.of(SubClassOf(A, ObjectIntersectionOf(objectSome, objectSome)),
                SubClassOf(A, objectSome)),
            Arguments.of(SubClassOf(A, ObjectUnionOf(dataSome, dataSome)), SubClassOf(A, dataSome)),
            Arguments.of(SubClassOf(A, ObjectIntersectionOf(du, du)), SubClassOf(A, du)), Arguments
                .of(DatatypeDefinition(DTA, DataUnionOf(eu, eu)), DatatypeDefinition(DTA, eu)));
    }

    @ParameterizedTest
    @MethodSource("nonSymmetricAxiomsRoundTrippingTestCase")
    void shouldRoundTripAReadableVersion(OWLAxiom in, OWLAxiom out) {
        OWLOntology output = createAnon();
        output.add(in);
        OWLOntology o = roundTrip(output, new FunctionalSyntaxDocumentFormat());
        assertEquals(1, o.logicalAxioms().count());
        assertEquals(out, o.logicalAxioms().iterator().next());
    }
}
