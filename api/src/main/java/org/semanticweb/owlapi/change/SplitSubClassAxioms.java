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

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Given a set of ontologies, this composite change will replace all subclass
 * axioms in each ontology, whose super class is an object intersection
 * (conjuction) with multiple subclass axioms - one for each conjunct. For
 * example, A subClassOf (B and C), would be replaced with two subclass axioms,
 * A subClassOf B, and A subClassOf C.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.1
 */
public class SplitSubClassAxioms extends AbstractCompositeOntologyChange {

    private static final long serialVersionUID = 40000L;

    /**
     * Creates a composite change to split subclass axioms into multiple more
     * fine grained subclass axioms.
     * 
     * @param ontologies
     *        The ontologies whose subclass axioms should be processed.
     * @param dataFactory
     *        The data factory which should be used to create new axioms.
     */
    public SplitSubClassAxioms(@Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLDataFactory dataFactory) {
        super(dataFactory);
        ontologies.forEach(o -> o.axioms(AxiomType.SUBCLASS_OF).forEach(
                ax -> split(o, ax)));
    }

    protected void split(OWLOntology o, OWLSubClassOfAxiom ax) {
        ConjunctSplitter splitter = new ConjunctSplitter();
        ax.getSuperClass().accept(splitter);
        if (splitter.result.size() > 1) {
            addChange(new RemoveAxiom(o, ax));
            splitter.result.forEach(desc -> addChange(new AddAxiom(o, df
                    .getOWLSubClassOfAxiom(ax.getSubClass(), desc))));
        }
    }

    /** The Class ConjunctSplitter. */
    private static class ConjunctSplitter implements OWLClassExpressionVisitor {

        /** The result. */
        final Set<OWLClassExpression> result = new HashSet<>();

        @Override
        public void doDefault(Object object) {
            result.add((OWLClassExpression) object);
        }

        /** Instantiates a new conjunct splitter. */
        ConjunctSplitter() {}

        @Override
        public void visit(OWLObjectIntersectionOf ce) {
            ce.operands().forEach(op -> op.accept(this));
        }
    }
}
