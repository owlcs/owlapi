package org.semanticweb.owlapi;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
