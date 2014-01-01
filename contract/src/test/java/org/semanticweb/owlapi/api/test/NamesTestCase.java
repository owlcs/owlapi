/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.coode.owl.krssparser.KRSSOWLParser;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxOWLParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyParser;
import org.coode.owlapi.owlxmlparser.OWLXMLParser;
import org.coode.owlapi.rdfxml.parser.RDFXMLParser;
import org.junit.Test;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AddImportData;
import org.semanticweb.owlapi.change.AddOntologyAnnotationData;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.change.RemoveImportData;
import org.semanticweb.owlapi.change.RemoveOntologyAnnotationData;
import org.semanticweb.owlapi.change.SetOntologyIDData;
import org.semanticweb.owlapi.metrics.AverageAssertedNamedSuperclassCount;
import org.semanticweb.owlapi.metrics.AxiomCount;
import org.semanticweb.owlapi.metrics.HiddenGCICount;
import org.semanticweb.owlapi.metrics.ImportClosureSize;
import org.semanticweb.owlapi.metrics.MaximumNumberOfNamedSuperclasses;
import org.semanticweb.owlapi.metrics.NumberOfClassesWithMultipleInheritance;
import org.semanticweb.owlapi.metrics.UnsatisfiableClassCountMetric;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2Profile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParser;
import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OWLParser;

@SuppressWarnings("javadoc")
public class NamesTestCase {
    @Test
    public void shoudReturnRightName() throws OWLOntologyCreationException {
        assertEquals("AddAxiomData", new AddAxiomData(mock(OWLAxiom.class)) {
            private static final long serialVersionUID = 6721581006563915342L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("AddImportData", new AddImportData(
                mock(OWLImportsDeclaration.class)) {
            private static final long serialVersionUID = 2561394366351548647L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("AddOntologyAnnotationData",
                new AddOntologyAnnotationData(mock(OWLAnnotation.class)) {
                    private static final long serialVersionUID = 7438496788137119984L;

                    @Override
                    public String getName() {
                        return super.getName();
                    }
                }.getName());
        assertEquals("OWLOntologyChangeRecord",
                new OWLOntologyChangeRecord<OWLAxiom>(
                        mock(OWLOntologyID.class),
                        mock(OWLOntologyChangeData.class)) {
                    private static final long serialVersionUID = 1738715002719055965L;

                    @Override
                    public String getName() {
                        return super.getName();
                    }
                }.getName());
        assertEquals("RemoveAxiomData", new RemoveAxiomData(
                mock(OWLAxiom.class)) {
            private static final long serialVersionUID = 3509217375432737384L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveImportData", new RemoveImportData(
                mock(OWLImportsDeclaration.class)) {
            private static final long serialVersionUID = 2566579395436954130L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveOntologyAnnotationData",
                new RemoveOntologyAnnotationData(mock(OWLAnnotation.class)) {
                    private static final long serialVersionUID = 3019208333794944242L;

                    @Override
                    public String getName() {
                        return super.getName();
                    }
                }.getName());
        assertEquals("SetOntologyIDData",
                new SetOntologyIDData(new OWLOntologyID(
                        IRI.create("urn:test1"), IRI.create("urn:test2"))) {
                    private static final long serialVersionUID = 7310142638187830473L;

                    @Override
                    public String getName() {
                        return super.getName();
                    }
                }.getName());
        assertEquals("OWL 2 DL", new OWL2DLProfile().getName());
        assertEquals("OWL 2 EL", new OWL2ELProfile().getName());
        assertEquals("OWL 2", new OWL2Profile().getName());
        assertEquals("OWL 2 QL", new OWL2QLProfile().getName());
        assertEquals("OWL 2 RL", new OWL2RLProfile().getName());
        assertEquals("KRSS2OWLParser", new KRSS2OWLParser().getName());
        assertEquals("KRSSOWLParser", new KRSSOWLParser().getName());
        assertEquals("OWLFunctionalSyntaxOWLParser",
                new OWLFunctionalSyntaxOWLParser().getName());
        assertEquals("ManchesterOWLSyntaxOntologyParser",
                new ManchesterOWLSyntaxOntologyParser().getName());
        assertEquals("OWLXMLParser", new OWLXMLParser().getName());
        assertEquals("RDFXMLParser", new RDFXMLParser().getName());
        assertEquals("TurtleOntologyParser",
                new TurtleOntologyParser().getName());
        OWLOntology createOntology = Factory.getManager().createOntology();
        assertEquals("Average number of named superclasses",
                new AverageAssertedNamedSuperclassCount(createOntology)
                        .getName());
        assertEquals("Axiom", new AxiomCount(createOntology).getName());
        assertEquals("Hidden GCI Count",
                new HiddenGCICount(createOntology).getName());
        assertEquals("Imports closure size", new ImportClosureSize(
                createOntology).getName());
        assertEquals("Maximum number of asserted named superclasses",
                new MaximumNumberOfNamedSuperclasses(createOntology).getName());
        assertEquals("Number of classes with asserted multiple inheritance",
                new NumberOfClassesWithMultipleInheritance(createOntology)
                        .getName());
        OWLReasoner mock = mock(OWLReasoner.class);
        when(mock.getRootOntology()).thenReturn(createOntology);
        assertEquals("Unsatisfiable class count",
                new UnsatisfiableClassCountMetric(mock).getName());
    }
}
