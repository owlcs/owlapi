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
package org.semanticweb.owlapi.change;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;

/**
 * Given a set of ontologies S, for each ontology, O, in S, this change combines
 * multiple subclass axioms with a common left hand side into one subclass
 * axiom. For example, given A subClassOf B, A subClassOf C, this change will
 * remove these two axioms and replace them by adding one subclass axiom, A
 * subClassOf (B and C).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.1
 */
public class AmalgamateSubClassAxioms extends AbstractCompositeOntologyChange {

    /**
     * Instantiates a new amalgamate sub class axioms.
     * 
     * @param dataFactory
     *        the data factory
     * @param ontologies
     *        the ontologies to use
     */
    public AmalgamateSubClassAxioms(OWLDataFactory dataFactory, Collection<OWLOntology> ontologies) {
        super(dataFactory);
        checkNotNull(ontologies, "ontologies cannot be null");
        ontologies.forEach(o -> o.classesInSignature().forEach(cls -> amalgamate(o, cls)));
    }

    protected void amalgamate(OWLOntology ont, OWLClass cls) {
        List<OWLSubClassOfAxiom> axioms = asList(ont.subClassAxiomsForSubClass(cls));
        if (axioms.size() < 2) {
            return;
        }
        axioms.forEach(ax -> addChange(new RemoveAxiom(ont, ax)));
        Stream<OWLClassExpression> superclasses = axioms.stream().map(ax -> ax.getSuperClass());
        OWLObjectIntersectionOf intersection = df.getOWLObjectIntersectionOf(superclasses);
        addChange(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cls, intersection)));
    }
}
