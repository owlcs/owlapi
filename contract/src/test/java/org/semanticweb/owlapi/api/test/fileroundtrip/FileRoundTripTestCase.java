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

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class FileRoundTripTestCase extends AbstractFileRoundTrippingTestCase {

    public FileRoundTripTestCase(String f) {
        super(f);
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(
        // AnnotatedPropertyAssertions
        "AnnotatedPropertyAssertions.rdf",
        // ComplexSubPropertyAxiom
        "ComplexSubProperty.rdf",
        // DataAllValuesFrom
        "DataAllValuesFrom.rdf",
        // DataCardinalityWithWhiteSpace
        "cardinalitywithwhitespace.owl",
        // DataComplementOf
        "DataComplementOf.rdf",
        // DataHasValue
        "DataHasValue.rdf",
        // DataIntersectionOf
        "DataIntersectionOf.rdf",
        // DataMaxCardinality
        "DataMaxCardinality.rdf",
        // DataMinCardinality
        "DataMinCardinality.rdf",
        // DataOneOf
        "DataOneOf.rdf",
        // DataSomeValuesFrom
        "DataSomeValuesFrom.rdf",
        // DataUnionOf
        "DataUnionOf.rdf",
        // DatatypeRestriction
        "DatatypeRestriction.rdf",
        // Declarations
        "TestDeclarations.rdf",
        // Deprecated
        "Deprecated.rdf",
        // DisjointClasses
        "DisjointClasses.rdf",
        // HasKey
        "HasKey.rdf",
        // InverseOf
        "InverseOf.rdf",
        // ObjectAllValuesFrom
        "ObjectAllValuesFrom.rdf",
        // ObjectCardinality
        "ObjectCardinality.rdf",
        // ObjectComplementOf
        "ObjectComplementOf.rdf",
        // ObjectHasSelf
        "ObjectHasSelf.rdf",
        // ObjectHasValue
        "ObjectHasValue.rdf",
        // ObjectIntersectionOf
        "ObjectIntersectionOf.rdf",
        // ObjectMaxCardinality
        "ObjectMaxCardinality.rdf",
        // ObjectMaxQualifiedCardinality
        "ObjectMaxQualifiedCardinality.rdf",
        // ObjectMinCardinality
        "ObjectMinCardinality.rdf",
        // ObjectMinQualifiedCardinality
        "ObjectMinQualifiedCardinality.rdf",
        // ObjectOneOf
        "ObjectOneOf.rdf",
        // ObjectQualifiedCardinality
        "ObjectQualifiedCardinality.rdf",
        // ObjectSomeValuesFrom
        "ObjectSomeValuesFrom.rdf",
        // ObjectUnionOfTestCase
        "ObjectUnionOf.rdf",
        // PrimerFunctionalSyntaxRoundTripping
        "primer.functionalsyntax.txt",
        // PrimerOWLXMLRoundTripping
        "primer.owlxml.xml",
        // PrimerRDFXMLRoundTripping
        "primer.rdfxml.xml",
        // RDFSClass
        "RDFSClass.rdf",
        // StructuralReasonerRecursion
        "koala.owl",
        // SubClassAxiom
        "SubClassOf.rdf",
        // TestParser06
        "TestParser06.rdf",
        // TestParser07
        "TestParser07.rdf",
        // TestParser10
        "TestParser10.rdf",
        // TurtleSharedBlankNode
        "annotatedpropertychain.ttl.rdf",
        // UntypedSubClassOf
        "UntypedSubClassOf.rdf",
        // SubClassOfUntypedOWLClass
        "SubClassOfUntypedOWLClass.rdf",
        // SubClassOfUntypedSomeValuesFrom
        "SubClassOfUntypedSomeValuesFrom.rdf");
    }
}
