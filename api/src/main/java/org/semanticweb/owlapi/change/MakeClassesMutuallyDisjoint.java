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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.pairs;

import java.util.Collection;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Given a set of class expressions, this composite change will make them mutually disjoint. The
 * composite change offers the option of using one disjoint classes axiom to do this or using
 * multiple disjoint classes axioms to make them pairwise disjoint (for backwards compatibility with
 * OWL 1.0).
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public class MakeClassesMutuallyDisjoint extends AbstractCompositeOntologyChange {

    /**
     * Creates a composite change which makes a set of classes mutually disjoint.
     *
     * @param dataFactory The data factory which should be used for creating the axioms
     * @param classExpressions The class expressions which should be made mutually disjoint.
     * @param usePairwiseDisjointAxioms the use pairwise disjoint axioms
     * @param targetOntology The target ontology which the changes will be applied to. {@code true}
     * if multiple disjoint classes axioms should be used to make the class expressions pairwise
     * disjoint (for backwards compatibility with OWL 1.0), or {@code false} if one disjoint classes
     * axiom should be used (preferred OWL 1.1 method).
     */
    public MakeClassesMutuallyDisjoint(OWLDataFactory dataFactory,
        Collection<? extends OWLClassExpression> classExpressions,
        boolean usePairwiseDisjointAxioms, OWLOntology targetOntology) {
        super(dataFactory);
        checkNotNull(classExpressions, "classExpressions cannot be null");
        checkNotNull(targetOntology, "targetOntology cannot be null");
        generateChanges(classExpressions, usePairwiseDisjointAxioms, targetOntology);
    }

    private void generateChanges(Collection<? extends OWLClassExpression> classExpressions,
        boolean usePairwiseDisjointAxioms, OWLOntology targetOntology) {
        if (usePairwiseDisjointAxioms) {
            pairs(classExpressions).forEach(v -> addChange(
                new AddAxiom(targetOntology, df.getOWLDisjointClassesAxiom(v.i, v.j))));
        } else {
            addChange(new AddAxiom(targetOntology,
                df.getOWLDisjointClassesAxiom(classExpressions)));
        }
    }
}
