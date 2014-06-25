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
package uk.ac.manchester.owl.owlapi.tutorial;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * This class demonstrates some aspects of the OWL API. Given a class in an
 * ontology, it will determine the subclass axioms that define the class. For
 * each of these, if the superclass is a conjunction of existential
 * restrictions, then an additional subclass axiom will be added to the
 * ontology, "closing" the restrictions.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management
 *         Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public class ClosureAxioms {

    @Nonnull
    private final OWLOntologyManager manager;
    @Nonnull
    private final OWLOntology ontology;
    private final OWLDataFactory factory;

    public ClosureAxioms(@Nonnull OWLOntologyManager manager,
            @Nonnull OWLOntology ontology) {
        this.manager = manager;
        this.ontology = ontology;
        factory = manager.getOWLDataFactory();
    }

    public void addClosureAxioms(@Nonnull OWLClass clazz) {
        /* Get the class axioms */
        Set<OWLSubClassOfAxiom> axioms = ontology
                .getAxioms(AxiomType.SUBCLASS_OF);
        /* Collect those that assert superclasses of the class */
        SubClassCollector collector = new SubClassCollector(clazz);
        for (OWLClassAxiom axiom : axioms) {
            axiom.accept(collector);
        }
        Map<OWLObjectPropertyExpression, Set<OWLClassExpression>> restrictions = new HashMap<>();
        /* For each axiom.... */
        for (OWLSubClassOfAxiom axiom : collector.getAxioms()) {
            /* Get the superclass */
            OWLClassExpression superClass = axiom.getSuperClass();
            /* Collect any existentials */
            ExistentialCollector ec = new ExistentialCollector(restrictions);
            superClass.accept(ec);
        }
        /* For any existentials.... */
        for (OWLObjectPropertyExpression prop : restrictions.keySet()) {
            assert prop != null;
            System.out.println("prop: " + prop);
            Set<OWLClassExpression> fillers = restrictions.get(prop);
            for (OWLClassExpression filler : fillers) {
                System.out.println("------> " + filler);
            }
            /* Create a union of the fillers */
            OWLClassExpression union = factory.getOWLObjectUnionOf(fillers);
            /* Create a universal restriction */
            OWLClassExpression universal = factory.getOWLObjectAllValuesFrom(
                    prop, union);
            /* Create a new axiom */
            OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(clazz, universal);
            /* Now add the axiom to the ontology */
            AddAxiom addAxiom = new AddAxiom(ontology, newAxiom);
            /* Use the manager to apply the change */
            manager.applyChange(addAxiom);
        }
    }
}
