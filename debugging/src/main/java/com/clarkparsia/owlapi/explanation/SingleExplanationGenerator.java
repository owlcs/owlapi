/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, Clark & Parsia, LLC
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
 * Copyright 2011, Clark & Parsia, LLC
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

package com.clarkparsia.owlapi.explanation;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;


public interface SingleExplanationGenerator {

    /**
     * Get the ontology manager for this explanation generator.
     */
    public OWLOntologyManager getOntologyManager();

//	/**
//	 * Sets the set of ontologies according to which the explanations are
//	 * generated to the import closure of the given ontologies.
//	 */
//    public void setOntology(OWLOntology ontology);
//
//    /**
//     * Sets the set of ontologies according to which the explanations are generated.
//     */
//    public void setOntologies(Set<OWLOntology> ontologies);

	/**
	 * Gets the ontologies according to which the explanations are generated
     * @return ont
	 */
    public OWLOntology getOntology();
//
//    /**
//     * Returns the set of ontologies according to which the explanations are generated.
//     */
//    public Set<OWLOntology> getOntologies();


//    /**
//     * Sets the reasoner that will be used to generate explanations. This
//     * function is provided in addition to
//     * {@link #setReasonerFactory(OWLReasonerFactory)} because the reasoning
//     * results already computed by the given reasoner can be reused. It is
//     * guaranteed that the state of this reasoner will not be invalidated by
//     * explanation generation, i.e. if the reasoner was in classified state it
//     * will stay in classified state.
//     */
//    public void setReasoner(OWLReasoner reasoner);


    /**
     * Returns the reasoner associated with this generator.
     */
    public OWLReasoner getReasoner();


//    /**
//     * Sets the reasoner factory that will be used to generate fresh reasoners.
//     * We create new reasoner instances to avoid invalidating the reasoning
//     * state of existing reasoners. Explanation generation process will modify
//     * the original ontology and/or reason over a subset of the original
//     * ontology. Using an alternate fresh reasoner for these tasks ensures
//     * efficient explanation generation without side effects to anything outside
//     * the explanation generator.
//     */
//    public void setReasonerFactory(OWLReasonerFactory reasonerFactory);


    /**
     * Returns the reasoner factory used to create fresh reasoners.
     */
    public OWLReasonerFactory getReasonerFactory();


    /**
     * Get a single explanation for an arbitrary class expression, or empty set
     * if the given expression is satisfiable.
     * @param unsatClass arbitrary class expression whose unsatisfiability will be
     *                   explained
     * @return set of axioms explaining the unsatisfiability of given class
     *         expression, or empty set if the given expression is satisfiable.
     */
    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass);
}
