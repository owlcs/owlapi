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

import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 */
public class AverageAssertedNamedSuperclassCount extends DoubleValuedMetric {

    /**
     * Instantiates a new average asserted named superclass count.
     * 
     * @param o
     *        ontology to use
     */
    public AverageAssertedNamedSuperclassCount(@Nonnull OWLOntology o) {
        super(o);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Average number of named superclasses";
    }

    @Override
    public Double recomputeMetric() {
        int total = 0;
        int count = 0;
        Set<OWLClass> processedClasses = new HashSet<>();
        for (OWLOntology ont : asList(getOntologies())) {
            for (OWLClass cls : asList(ont.classesInSignature())) {
                if (!processedClasses.contains(cls)) {
                    count++;
                    int prevTotal = total;
                    processedClasses.add(cls);
                    for (OWLClassExpression desc : asList(sup(
                            ont.subClassAxiomsForSubClass(cls),
                            OWLClassExpression.class))) {
                        if (!desc.isAnonymous()) {
                            total++;
                        }
                    }
                    if (prevTotal == total) {
                        total++;
                    }
                }
            }
        }
        return (double) total / count;
    }

    @Override
    protected boolean isMetricInvalidated(
            List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange()
                    && chg.getAxiom() instanceof OWLSubClassOfAxiom) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void disposeMetric() {}
}
