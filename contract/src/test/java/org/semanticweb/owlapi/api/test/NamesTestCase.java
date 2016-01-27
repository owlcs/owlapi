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
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.change.*;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParser;
import org.semanticweb.owlapi.krss1.parser.KRSSOWLParser;
import org.semanticweb.owlapi.krss2.parser.KRSS2OWLParser;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParser;
import org.semanticweb.owlapi.metrics.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParser;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParser;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.google.common.base.Optional;

@SuppressWarnings({ "javadoc", })
public class NamesTestCase extends TestBase {

    @Test
    public void shoudReturnRightName() throws OWLOntologyCreationException {
        assertEquals("AddAxiomData", new AddAxiomData(mock(OWLAxiom.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("AddImportData", new AddImportData(mock(OWLImportsDeclaration.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("AddOntologyAnnotationData", new AddOntologyAnnotationData(mock(OWLAnnotation.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("OWLOntologyChangeRecord", new OWLOntologyChangeRecord(mock(OWLOntologyID.class), mock(
            OWLOntologyChangeData.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveAxiomData", new RemoveAxiomData(mock(OWLAxiom.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveImportData", new RemoveImportData(mock(OWLImportsDeclaration.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveOntologyAnnotationData", new RemoveOntologyAnnotationData(mock(OWLAnnotation.class)) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("SetOntologyIDData", new SetOntologyIDData(new OWLOntologyID(Optional.of(IRI.create("urn:test1")),
            Optional.of(IRI.create("urn:test2")))) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("KRSS2OWLParser", new KRSS2OWLParser().getName());
        assertEquals("KRSSOWLParser", new KRSSOWLParser().getName());
        assertEquals("OWLFunctionalSyntaxOWLParser", new OWLFunctionalSyntaxOWLParser().getName());
        assertEquals("ManchesterOWLSyntaxOntologyParser", new ManchesterOWLSyntaxOntologyParser().getName());
        assertEquals("OWLXMLParser", new OWLXMLParser().getName());
        assertEquals("RDFXMLParser", new RDFXMLParser().getName());
        assertEquals("TurtleOntologyParser", new TurtleOntologyParser().getName());
        OWLOntology createOntology = m.createOntology();
        assertEquals("Average number of named superclasses", new AverageAssertedNamedSuperclassCount(createOntology)
            .getName());
        assertEquals("Axiom", new AxiomCount(createOntology).getName());
        assertEquals("Hidden GCI Count", new HiddenGCICount(createOntology).getName());
        assertEquals("Imports closure size", new ImportClosureSize(createOntology).getName());
        assertEquals("Maximum number of asserted named superclasses", new MaximumNumberOfNamedSuperclasses(
            createOntology).getName());
        assertEquals("Number of classes with asserted multiple inheritance", new NumberOfClassesWithMultipleInheritance(
            createOntology).getName());
        OWLReasoner mock = mock(OWLReasoner.class);
        when(mock.getRootOntology()).thenReturn(createOntology);
        assertEquals("Unsatisfiable class count", new UnsatisfiableClassCountMetric(mock).getName());
    }
}
