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
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.metrics.DoubleValuedMetric;
import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.metrics.OWLMetricManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractMetricsTest {

    @Test
    public void shouldTestAbstractOWLMetric() throws OWLException {
        AbstractOWLMetric<Object> testSubject0 = new AbstractOWLMetric<Object>(
                Utils.getMockOntology()) {

            @Override
            public String getName() {
                return "";
            }

            @Override
            protected Object recomputeMetric() {
                return new Object();
            }

            @Override
            protected boolean isMetricInvalidated(
                    List<? extends OWLOntologyChange<?>> changes) {
                return false;
            }

            @Override
            protected void disposeMetric() {}
        };
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result2 = testSubject0.getOntologies();
        OWLOntology result3 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Test
    public void shouldTestDoubleValuedMetric() throws OWLException {
        DoubleValuedMetric testSubject0 = new DoubleValuedMetric(
                Utils.getMockOntology()) {

            @Override
            public String getName() {
                return "";
            }

            @Override
            protected Double recomputeMetric() {
                return 0D;
            }

            @Override
            protected boolean isMetricInvalidated(
                    List<? extends OWLOntologyChange<?>> changes) {
                return false;
            }

            @Override
            protected void disposeMetric() {}
        };
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result2 = testSubject0.getOntologies();
        OWLOntology result3 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Test
    public void shouldTestIntegerValuedMetric() throws OWLException {
        IntegerValuedMetric testSubject0 = new IntegerValuedMetric(
                Utils.getMockOntology()) {

            @Override
            public String getName() {
                return "";
            }

            @Override
            protected Integer recomputeMetric() {
                return 1;
            }

            @Override
            protected boolean isMetricInvalidated(
                    List<? extends OWLOntologyChange<?>> changes) {
                return false;
            }

            @Override
            protected void disposeMetric() {}
        };
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result2 = testSubject0.getOntologies();
        OWLOntology result3 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Test
    public void shouldTestInterfaceOWLMetric() throws OWLException {
        OWLMetric<Object> testSubject0 = mock(OWLMetric.class);
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        OWLOntology result2 = testSubject0.getOntology();
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result3 = testSubject0.getManager();
        boolean result4 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Test
    public void shouldTestOWLMetricManager() throws OWLException {
        OWLMetricManager testSubject0 = new OWLMetricManager(
                new ArrayList<OWLMetric<?>>());
        testSubject0.setOntology(Utils.getMockOntology());
        List<OWLMetric<?>> result1 = testSubject0.getMetrics();
    }
}
