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

import java.util.Collection;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * This composite change will create a value partion - see "pattern 2" in "Representing Specified
 * Values in OWL: "value partitions" and "value sets""
 * (http://www.w3.org/TR/swbp-specified-values.)<br>
 * A value partition is an ontology design pattern which is used to represent a set of closed values
 * for a particular property. For example the property hasSize might only take values from
 * SmallSize, MediumSize and LargeSize. In this case, the value partition is Size, and has the
 * values SmallSize, MediumSize and LargeSize. This composite change will set hasSize to be
 * functional and its range as Size. Size will be covered by SmallSize, MediumSize and LargeSize and
 * these classes which represent the values will be made disjoint with eachother.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public class CreateValuePartition extends AbstractCompositeOntologyChange {

    /**
     * Creates a composite change that will create a value partition.
     *
     * @param dataFactory A data factory which can be used to create the necessary axioms
     * @param valuePartitionClass The class which represents the value partition.
     * @param valuePartionClasses The classes that represent the various values of the value
     * partition.
     * @param valuePartitionProperty the property which should be used in conjunction with the value
     * partition.
     * @param targetOntology The target ontology which the axioms that are necessary to create the
     * value partition will be added to.
     */
    public CreateValuePartition(OWLDataFactory dataFactory, OWLClass valuePartitionClass,
        Collection<OWLClass> valuePartionClasses,
        OWLObjectProperty valuePartitionProperty, OWLOntology targetOntology) {
        super(dataFactory);
        generateChanges(checkNotNull(targetOntology, "targetOntology cannot be null"),
            checkNotNull(valuePartionClasses, "valuePartionClasses cannot be null"),
            checkNotNull(valuePartitionClass, "valuePartitionClass cannot be null"),
            checkNotNull(valuePartitionProperty,
                "valuePartitionProperty cannot be null"));
    }

    private void generateChanges(OWLOntology targetOntology,
        Collection<OWLClass> valuePartitionClasses, OWLClass valuePartitionClass,
        OWLObjectProperty valuePartitionProperty) {
        // To create a value partition from a set of classes which represent the
        // values, a value partition class, a property we...
        // 1) Make the classes which represent the values, subclasses of the
        // value partition class
        for (OWLClassExpression valuePartitionValue : valuePartitionClasses) {
            addChange(new AddAxiom(targetOntology,
                df.getOWLSubClassOfAxiom(valuePartitionValue, valuePartitionClass)));
        }
        // 2) Make the values disjoint
        addChange(new AddAxiom(targetOntology,
            df.getOWLDisjointClassesAxiom(valuePartitionClasses)));
        // 3) Add a covering axiom to the value partition
        OWLClassExpression union = df.getOWLObjectUnionOf(valuePartitionClasses);
        addChange(new AddAxiom(targetOntology,
            df.getOWLSubClassOfAxiom(valuePartitionClass, union)));
        // 4) Make the property functional
        addChange(new AddAxiom(targetOntology,
            df.getOWLFunctionalObjectPropertyAxiom(valuePartitionProperty)));
        // 5) Set the range of the property to be the value partition
        addChange(new AddAxiom(targetOntology, df.getOWLObjectPropertyRangeAxiom(
            valuePartitionProperty, valuePartitionClass)));
    }
}
