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
package org.semanticweb.owlapi.metrics;

import static org.semanticweb.owlapi.search.Searcher.equivalent;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.NamedConjunctChecker;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public class NumberOfClassesWithMultipleInheritance extends IntegerValuedMetric {

    /**
     * Instantiates a new number of classes with multiple inheritance.
     *
     * @param o ontology to use
     */
    public NumberOfClassesWithMultipleInheritance(OWLOntology o) {
        super(o);
    }

    @Override
    public String getName() {
        return "Number of classes with asserted multiple inheritance";
    }

    @Override
    public Integer recomputeMetric() {
        Set<OWLClass> processed = new HashSet<>();
        Set<OWLClass> clses = new HashSet<>();
        NamedConjunctChecker checker = new NamedConjunctChecker();
        for (OWLOntology ont : asList(getOntologies())) {
            for (OWLClass cls : asList(ont.classesInSignature())) {
                if (processed.contains(cls)) {
                    continue;
                }
                processed.add(cls);
                int count = 0;
                for (OWLClassExpression sup : asList(equivalent(ont.equivalentClassesAxioms(cls),
                    OWLClassExpression.class))) {
                    if (checker.hasNamedConjunct(sup)) {
                        count++;
                    }
                    if (count > 1) {
                        clses.add(cls);
                        break;
                    }
                }
            }
        }
        return Integer.valueOf(clses.size());
    }

    @Override
    protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange() && change.getAxiom() instanceof OWLSubClassOfAxiom) {
                return true;
            }
        }
        return false;
    }
}
