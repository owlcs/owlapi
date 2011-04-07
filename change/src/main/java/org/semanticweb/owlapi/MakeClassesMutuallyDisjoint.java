/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * Copyright 2011, The University of Manchester
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

package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jul-2007<br><br>
 * <p/>
 * Given a set of class expressions, this composite change will make them mutually disjoint.
 * The composite change offers the option of using one disjoint classes axiom to do this
 * or using multiple disjoint classes axioms to make them pairwise disjoint (for backwards
 * compatibility with OWL 1.0).
 */
public class MakeClassesMutuallyDisjoint extends AbstractCompositeOntologyChange {

    private Set<? extends OWLClassExpression> classExpressions;

    private boolean usePairwiseDisjointAxioms;

    private OWLOntology targetOntology;

    private List<OWLOntologyChange> changes;


    /**
     * Creates a composite change which makes a set of classes mutually disjoint
     *
     * @param dataFactory               The data factory which should be used for creating the axioms
     * @param classExpressions              The class expressions which should be made mutually disjoint.
     * @param usePairwiseDisjointAxioms <code>true</code> if multiple disjoint classes
     *                                  axioms should be used to make the class expressions pairwise disjoint (for backwards
     *                                  compatibility with OWL 1.0), or <code>false</code> if one disjoint classes axiom
     *                                  should be used (preferred OWL 1.1 method).
     * @param targetOntology            The target ontology which the changes will be applied to.
     */
    public MakeClassesMutuallyDisjoint(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> classExpressions,
                                       boolean usePairwiseDisjointAxioms, OWLOntology targetOntology) {
        super(dataFactory);
        this.classExpressions = classExpressions;
        this.usePairwiseDisjointAxioms = usePairwiseDisjointAxioms;
        this.targetOntology = targetOntology;
        generateChanges();
    }


    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        if (usePairwiseDisjointAxioms) {
            List<OWLClassExpression> descList = new ArrayList<OWLClassExpression>(classExpressions);
            for (int i = 0; i < descList.size(); i++) {
                for (int j = i + 1; j < descList.size(); j++) {
                    changes.add(new AddAxiom(targetOntology,
                            getDataFactory().getOWLDisjointClassesAxiom(CollectionFactory.createSet(
                                    descList.get(i),
                                    descList.get(j)))));
                }
            }
        } else {
            changes.add(new AddAxiom(targetOntology, getDataFactory().getOWLDisjointClassesAxiom(classExpressions)));
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
