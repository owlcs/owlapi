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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveAxiom;

import com.google.common.collect.Iterators;

/**
 * This composite change will convert a defined class to a primitive class by replacing equivalent
 * classes axioms where the class in question is a class in the equivalent classes axioms to a set
 * of subclass axioms whose superclasses are the set of classes which were originally equivalent to
 * the class in question.<br>
 * More formally, for a given class A, a set of ontologies S, and a target ontology T, this
 * composite change will remove all equivalent axioms from each ontology O in S where the equivalent
 * class axiom contains A as a 'top level' class (e.g. EquivalentClasses(A, C, D)). For each class,
 * D, that was made equivalent to A via an equivalent classes axiom, a subclass axiom SubClassOf(A,
 * D) will be added to the target ontology T.<br>
 * This change supports a common pattern of working, where a class is converted from a defined class
 * to a primitive class.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public class ConvertEquivalentClassesToSuperClasses extends AbstractCompositeOntologyChange {

    private static final OWLClassExpressionVisitorEx<Stream<? extends OWLClassExpression>> INTERSECTION_SPLITTER =
        new OWLClassExpressionVisitorEx<Stream<? extends OWLClassExpression>>() {

            @Override
            public Stream<? extends OWLClassExpression> visit(OWLObjectIntersectionOf ce) {
                return ce.operands();
            }

            @Override
            public Stream<? extends OWLClassExpression> doDefault(OWLObject o) {
                return empty();
            }
        };
    private final OWLOntology targetOntology;
    private final OWLClass cls;
    private final Collection<OWLOntology> ontologies;
    private final boolean intersectionsShouldBeSplit;

    /**
     * Instantiates a new convert equivalent classes to super classes.
     *
     * @param dataFactory the data factory
     * @param cls the class to convert
     * @param ontologies the ontologies to use
     * @param targetOntology the target ontology
     * @param splitIntersections whether or not intersections should be split
     */
    public ConvertEquivalentClassesToSuperClasses(OWLDataFactory dataFactory, OWLClass cls,
        Collection<OWLOntology> ontologies, OWLOntology targetOntology,
        boolean splitIntersections) {
        super(dataFactory);
        this.targetOntology = checkNotNull(targetOntology, "targetOntology cannot be null");
        this.cls = checkNotNull(cls, "cls cannot be null");
        this.ontologies = checkNotNull(ontologies, "ontologies cannot be null");
        intersectionsShouldBeSplit = splitIntersections;
        generateChanges();
    }

    private void generateChanges() {
        Set<OWLClassExpression> supers = new HashSet<>();
        for (OWLOntology o : ontologies) {
            o.equivalentClassesAxioms(cls).forEach(ax -> {
                addChange(new RemoveAxiom(o, ax));
                ax.classExpressions().forEach(c -> collectClassExpressions(c, supers));
            });
        }
        supers.remove(cls);
        supers.forEach(
            sup -> addChange(new AddAxiom(targetOntology, df.getOWLSubClassOfAxiom(cls, sup))));
    }

    private void collectClassExpressions(OWLClassExpression desc,
        Collection<OWLClassExpression> supers) {
        if (!intersectionsShouldBeSplit) {
            supers.add(desc);
            return;
        }
        Iterator<? extends OWLClassExpression> iterator =
            desc.accept(INTERSECTION_SPLITTER).iterator();
        if (iterator.hasNext()) {
            Iterators.addAll(supers, iterator);
        } else {
            supers.add(desc);
        }
    }
}
