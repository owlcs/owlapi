package org.semanticweb.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
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
 * Date: 15-Aug-2007<br><br>
 * <p/>
 * Given a set of ontologies, this composite change will replace
 * all subclass axioms in each ontology, whose super class is an
 * object intersection (conjuction) with multiple subclass axioms -
 * one for each conjunct.  For example, A subClassOf (B and C),
 * would be replaced with two subclass axioms, A subClassOf B, and
 * A subClassOf C.
 */
public class SplitSubClassAxioms extends AbstractCompositeOntologyChange {

    private List<OWLOntologyChange> changes;


    /**
     * Creates a composite change to split subclass axioms into multiple more
     * fine grained subclass axioms.
     *
     * @param ontologies  The ontologies whose subclass axioms should be processed.
     * @param dataFactory The data factory which should be used to create new axioms.
     */
    public SplitSubClassAxioms(Set<OWLOntology> ontologies, OWLDataFactory dataFactory) {
        super(dataFactory);
        changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            for (OWLSubClassOfAxiom ax : ont.getAxioms(AxiomType.SUBCLASS_OF)) {
                ConjunctSplitter splitter = new ConjunctSplitter();
                ax.getSuperClass().accept(splitter);
                if (splitter.result.size() > 1) {
                    changes.add(new RemoveAxiom(ont, ax));
                    for (OWLClassExpression desc : splitter.result) {
                        OWLAxiom replAx = getDataFactory().getOWLSubClassOfAxiom(ax.getSubClass(), desc);
                        changes.add(new AddAxiom(ont, replAx));
                    }
                }
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }


    private class ConjunctSplitter implements OWLClassExpressionVisitor {

        private Set<OWLClassExpression> result;


        public ConjunctSplitter() {
            result = new HashSet<OWLClassExpression>();
        }


        public void visit(OWLClass desc) {
            result.add(desc);
        }


        public void visit(OWLDataAllValuesFrom desc) {
            result.add(desc);
        }


        public void visit(OWLDataExactCardinality desc) {
            result.add(desc);
        }


        public void visit(OWLDataMaxCardinality desc) {
            result.add(desc);
        }


        public void visit(OWLDataMinCardinality desc) {
            result.add(desc);
        }


        public void visit(OWLDataSomeValuesFrom desc) {
            result.add(desc);
        }


        public void visit(OWLDataHasValue desc) {
            result.add(desc);
        }


        public void visit(OWLObjectAllValuesFrom desc) {
            result.add(desc);
        }


        public void visit(OWLObjectComplementOf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectExactCardinality desc) {
            result.add(desc);
        }


        public void visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
            }
        }


        public void visit(OWLObjectMaxCardinality desc) {
            result.add(desc);
        }


        public void visit(OWLObjectMinCardinality desc) {
            result.add(desc);
        }


        public void visit(OWLObjectOneOf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectHasSelf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            result.add(desc);
        }


        public void visit(OWLObjectUnionOf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectHasValue desc) {
            result.add(desc);
        }
    }
}
