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

import static org.semanticweb.owlapi.search.EntitySearcher.getEquivalentClasses;
import static org.semanticweb.owlapi.search.EntitySearcher.getSubClasses;

import java.util.List;
import java.util.function.Predicate;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Counts the number of "hidden" GCIs in an ontology imports closure. A GCI is regarded to be a
 * "hidden" GCI if it is essentially introduce via an equivalent class axiom and a subclass axioms
 * where the LHS of the subclass axiom is nameed. For example, A equivalentTo p some C, A subClassOf
 * B results in a "hidden" GCI.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.1
 */
public class HiddenGCICount extends IntegerValuedMetric {

    Predicate<OWLAxiom> equivalentOrSubclass = ax -> ax instanceof OWLEquivalentClassesAxiom
        || ax instanceof OWLSubClassOfAxiom;
    Predicate<OWLClass> hasEquivalent = c -> getEquivalentClasses(c, getOntologies()).count() > 0;
    Predicate<OWLClass> isSubclass = c -> getSubClasses(c, getOntologies()).count() > 0;

    /**
     * Instantiates a new hidden gci count.
     *
     * @param o ontology to use
     */
    public HiddenGCICount(OWLOntology o) {
        super(o);
    }

    @Override
    protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        return changes.stream().filter(OWLOntologyChange::isAxiomChange)
            .map(OWLOntologyChange::getAxiom).anyMatch(equivalentOrSubclass);
    }

    @Override
    protected Integer recomputeMetric() {
        return Integer.valueOf((int) getOntologies().flatMap(OWLOntology::classesInSignature)
            .distinct().filter(hasEquivalent).filter(isSubclass).count());
    }

    @Override
    public String getName() {
        return "Hidden GCI Count";
    }
}
