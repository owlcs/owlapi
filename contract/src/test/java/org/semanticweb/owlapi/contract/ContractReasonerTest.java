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
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.NullReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasonerRuntimeException;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.TimedConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractReasonerTest {
    @Test
    public void shouldTestBufferingMode() throws OWLException {
        BufferingMode testSubject0 = BufferingMode.BUFFERING;
        BufferingMode[] result0 = BufferingMode.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestConsoleProgressMonitor() throws OWLException {
        ConsoleProgressMonitor testSubject0 = new ConsoleProgressMonitor();
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
    }

    @Test
    public void shouldTestFreshEntitiesException() throws OWLException {
        FreshEntitiesException testSubject0 = new FreshEntitiesException(
                Utils.mockSet(Utils.mockOWLEntity()));
        new FreshEntitiesException(Utils.mockOWLEntity());
        String result0 = testSubject0.getMessage();
        Collection<OWLEntity> result1 = testSubject0.getEntities();
        Throwable result3 = testSubject0.getCause();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestFreshEntityPolicy() throws OWLException {
        FreshEntityPolicy testSubject0 = FreshEntityPolicy.ALLOW;
        FreshEntityPolicy[] result0 = FreshEntityPolicy.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestIllegalConfigurationException() throws OWLException {
        IllegalConfigurationException testSubject0 = new IllegalConfigurationException(
                new RuntimeException(), mock(OWLReasonerConfiguration.class));
        new IllegalConfigurationException("",
                mock(OWLReasonerConfiguration.class));
        new IllegalConfigurationException("", new RuntimeException(),
                mock(OWLReasonerConfiguration.class));
        OWLReasonerConfiguration result0 = testSubject0.getConfiguration();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInconsistentOntologyException() throws OWLException {
        InconsistentOntologyException testSubject0 = new InconsistentOntologyException();
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestIndividualNodeSetPolicy() throws OWLException {
        IndividualNodeSetPolicy testSubject0 = IndividualNodeSetPolicy.BY_NAME;
        IndividualNodeSetPolicy[] result0 = IndividualNodeSetPolicy.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestInferenceType() throws OWLException {
        InferenceType testSubject0 = InferenceType.CLASS_ASSERTIONS;
        InferenceType[] result1 = InferenceType.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestInterfaceNode() throws OWLException {
        Node<OWLObject> testSubject0 = Utils.mockNode(OWLObject.class);
        boolean result0 = testSubject0.contains(mock(OWLObject.class));
        int result1 = testSubject0.getSize();
        Set<OWLObject> result2 = testSubject0.getEntities();
        boolean result3 = testSubject0.isTopNode();
        boolean result4 = testSubject0.isBottomNode();
        Set<OWLObject> result5 = testSubject0
                .getEntitiesMinus(mock(OWLObject.class));
        Set<OWLObject> result6 = testSubject0.getEntitiesMinusTop();
        Set<OWLObject> result7 = testSubject0.getEntitiesMinusBottom();
        boolean result8 = testSubject0.isSingleton();
        OWLObject result9 = testSubject0.getRepresentativeElement();
        Iterator<OWLObject> result10 = testSubject0.iterator();
    }

    @Test
    public void shouldTestInterfaceNodeSet() throws OWLException {
        NodeSet<OWLObject> testSubject0 = Utils.mockNodeSet(OWLObject.class);
        boolean result0 = testSubject0.isEmpty();
        boolean result1 = testSubject0.isSingleton();
        Set<OWLObject> result2 = testSubject0.getFlattened();
        boolean result3 = testSubject0.containsEntity(mock(OWLObject.class));
        boolean result4 = testSubject0.isTopSingleton();
        boolean result5 = testSubject0.isBottomSingleton();
        Set<Node<OWLObject>> result6 = testSubject0.getNodes();
        Iterator<Node<OWLObject>> result7 = testSubject0.iterator();
    }

    @Test
    public void shouldTestNullReasonerProgressMonitor() throws OWLException {
        NullReasonerProgressMonitor testSubject0 = new NullReasonerProgressMonitor();
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
    }

    @Test
    public void shouldTestInterfaceOWLReasonerConfiguration()
            throws OWLException {
        OWLReasonerConfiguration testSubject0 = mock(OWLReasonerConfiguration.class);
        ReasonerProgressMonitor result0 = testSubject0.getProgressMonitor();
        long result1 = testSubject0.getTimeOut();
        FreshEntityPolicy result2 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result3 = testSubject0
                .getIndividualNodeSetPolicy();
    }

    @Test
    public void shouldTestInterfaceOWLReasonerFactory() throws OWLException {
        OWLReasonerFactory testSubject0 = mock(OWLReasonerFactory.class);
        String result0 = testSubject0.getReasonerName();
        OWLReasoner result1 = testSubject0.createNonBufferingReasoner(Utils
                .getMockOntology());
        OWLReasoner result2 = testSubject0.createNonBufferingReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
        OWLReasoner result3 = testSubject0.createReasoner(Utils
                .getMockOntology());
        OWLReasoner result4 = testSubject0.createReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
    }

    @Test
    public void shouldTestOWLReasonerRuntimeException() throws OWLException {
        OWLReasonerRuntimeException testSubject0 = new OWLReasonerRuntimeException() {
            private static final long serialVersionUID = 40000L;
        };
        new OWLReasonerRuntimeException(new RuntimeException()) {
            private static final long serialVersionUID = 40000L;
        };
        new OWLReasonerRuntimeException("") {
            private static final long serialVersionUID = 40000L;
        };
        new OWLReasonerRuntimeException("", new RuntimeException()) {
            private static final long serialVersionUID = 40000L;
        };
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestReasonerInternalException() throws OWLException {
        ReasonerInternalException testSubject0 = new ReasonerInternalException(
                new RuntimeException());
        new ReasonerInternalException("");
        new ReasonerInternalException("", new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestReasonerInterruptedException() throws OWLException {
        ReasonerInterruptedException testSubject0 = new ReasonerInterruptedException();
        new ReasonerInterruptedException("");
        new ReasonerInterruptedException("", new RuntimeException());
        new ReasonerInterruptedException(new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceReasonerProgressMonitor()
            throws OWLException {
        ReasonerProgressMonitor testSubject0 = mock(ReasonerProgressMonitor.class);
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
    }

    public void shouldTestTimedConsoleProgressMonitor() throws OWLException {
        TimedConsoleProgressMonitor testSubject0 = new TimedConsoleProgressMonitor();
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
    }

    @Test
    public void shouldTestTimeOutException() throws OWLException {
        TimeOutException testSubject0 = new TimeOutException();
        new TimeOutException("");
        new TimeOutException("", new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestUnsupportedEntailmentTypeException()
            throws OWLException {
        UnsupportedEntailmentTypeException testSubject0 = new UnsupportedEntailmentTypeException(
                mock(OWLAxiom.class));
        OWLAxiom result0 = testSubject0.getAxiom();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }
}
