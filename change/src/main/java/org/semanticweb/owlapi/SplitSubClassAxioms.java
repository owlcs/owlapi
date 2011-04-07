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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;


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
     * @param ontologies The ontologies whose subclass axioms should be processed.
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


    private static class ConjunctSplitter implements OWLClassExpressionVisitor {

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
