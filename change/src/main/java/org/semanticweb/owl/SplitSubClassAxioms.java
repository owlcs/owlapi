package org.semanticweb.owl;

import org.semanticweb.owl.model.*;

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
 *
 * Given a set of ontologies, this composite change will replace
 * all subclass axioms in each ontology, whose super class is an
 * object intersection (conjuction) with multiple subclass axioms -
 * one for each conjunct.  For example, A subClassOf (B and C),
 * would be replaced with two subclass axioms, A subClassOf B, and
 * A subClassOf C.
 *
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
        for(OWLOntology ont : ontologies) {
            for(OWLSubClassAxiom ax : ont.getAxioms(AxiomType.SUBCLASS)) {
                ConjunctSplitter splitter = new ConjunctSplitter();
                ax.getSuperClass().accept(splitter);
                if(splitter.result.size() > 1) {
                    changes.add(new RemoveAxiom(ont, ax));
                    for(OWLDescription desc : splitter.result) {
                        OWLAxiom replAx = getDataFactory().getOWLSubClassAxiom(ax.getSubClass(), desc);
                        changes.add(new AddAxiom(ont, replAx));
                    }
                }
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }


    private class ConjunctSplitter implements OWLDescriptionVisitor {

        private Set<OWLDescription> result;


        public ConjunctSplitter() {
            result = new HashSet<OWLDescription>();
        }


        public void visit(OWLClass desc) {
            result.add(desc);
        }


        public void visit(OWLDataAllRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLDataSomeRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLDataValueRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectAllRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectComplementOf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectExactCardinalityRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectIntersectionOf desc) {
            for(OWLDescription op : desc.getOperands()) {
                op.accept(this);
            }
        }


        public void visit(OWLObjectMaxCardinalityRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectMinCardinalityRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectOneOf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectSelfRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectSomeRestriction desc) {
            result.add(desc);
        }


        public void visit(OWLObjectUnionOf desc) {
            result.add(desc);
        }


        public void visit(OWLObjectValueRestriction desc) {
            result.add(desc);
        }
    }
}
