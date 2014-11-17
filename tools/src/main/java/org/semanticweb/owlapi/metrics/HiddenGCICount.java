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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Counts the number of "hidden" GCIs in an ontology imports closure. A GCI is
 * regarded to be a "hidden" GCI if it is essentially introduce via an
 * equivalent class axiom and a subclass axioms where the LHS of the subclass
 * axiom is nameed. For example, A equivalentTo p some C, A subClassOf B results
 * in a "hidden" GCI.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.1
 */
public class HiddenGCICount extends IntegerValuedMetric {

    /**
     * Instantiates a new hidden gci count.
     * 
     * @param o
     *        ontology to use
     */
    public HiddenGCICount(@Nonnull OWLOntology o) {
        super(o);
    }

    @Override
    protected void disposeMetric() {}

    @Override
    protected boolean isMetricInvalidated(
            @Nonnull List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange()
                    && chg.getAxiom() instanceof OWLEquivalentClassesAxiom
                    || chg.getAxiom() instanceof OWLSubClassOfAxiom) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Integer recomputeMetric() {
        Set<OWLClass> processed = new HashSet<>();
        Set<OWLClass> result = new HashSet<>();
        for (OWLOntology ont : asList(getOntologies())) {
            for (OWLClass cls : asList(ont.classesInSignature())) {
                if (!processed.contains(cls)) {
                    processed.add(cls);
                } else {
                    boolean foundEquivalentClassesAxiom = false;
                    boolean foundSubClassAxiom = false;
                    for (OWLOntology o : asList(getOntologies())) {
                        if (!foundEquivalentClassesAxiom) {
                            foundEquivalentClassesAxiom = o
                                    .equivalentClassesAxioms(cls).count() > 0;
                        }
                        if (!foundSubClassAxiom) {
                            foundSubClassAxiom = o.subClassAxiomsForSubClass(
                                    cls).count() > 0;
                        }
                        if (foundSubClassAxiom && foundEquivalentClassesAxiom) {
                            result.add(cls);
                            break;
                        }
                    }
                }
            }
        }
        return result.size();
    }

    @Override
    public String getName() {
        return "Hidden GCI Count";
    }
}
