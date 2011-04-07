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
 * Copyright 2011, University of Manchester
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

package org.semanticweb.owlapi.util;

import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiomChange;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.RemoveAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Dec-2006<br><br>
 * <p/>
 * Provides a convenient method to filter add/remove axiom changes based
 * on the type of axiom that is being added or removed from an ontology.
 * <p/>
 * The general pattern of use is to simply create an instance of the <code>OWLOntologyChangeFilter</code>
 * and override the appropriate visit methods corresponding to the types of axioms that are of interest.
 * Each visit corresponds to a single change and the <code>isAdd</code> or <code>isRemove</code> methods can
 * be used to determine if the axiom corresponding to the change is being added or removed from an ontology
 * - the ontology can be obtained via the <code>getOntology</code> method.
 * <p/>
 * Example:  Suppose we are interested in changes that alter the domain of an object property.  We receive
 * a list of changes, <code>ontChanges</code>, from an ontology change listener.  We can use the
 * <code>OWLOntologyChangeFilter</code> to filter out the changes that alter the domain of an object
 * property in the following way:
 * <p/>
 * <pre>
 * OWLOntologyChangeFilter filter = new OWLOntologyChangeFilter() {
 * <p/>
 *      // Override the object property domain visit method
 *      public void visit(OWLObjectPropertyDomainAxiom axiom) {
 *          // Determine if the axiom is being added or removed
 *          if(isAdd()) {
 *              // Get hold of the ontology that the change applied to
 *              OWLOntology ont = getOntology();
 *              // Do something here
 *          }
 *      }
 * }
 * // Process the list of changes
 * filter.processChanges(ontChanges);
 * </pre>
 */
public class OWLOntologyChangeFilter extends OWLAxiomVisitorAdapter implements OWLAxiomVisitor {

    private boolean add;

    private OWLOntology ontology;

    private OWLOntologyChangeVisitor changeVisitor;


    public OWLOntologyChangeFilter() {
        changeVisitor = new OWLOntologyChangeVisitorAdapter() {
            @Override
			public void visit(AddAxiom change) {
                add = true;
                processChange(change);
            }


            @Override
			public void visit(RemoveAxiom change) {
                add = false;
                processChange(change);
            }
        };
    }


    final public void processChanges(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange change : changes) {
            change.accept(changeVisitor);
        }
    }


    private void processChange(OWLAxiomChange change) {
        ontology = change.getOntology();
        change.getAxiom().accept(this);
        ontology = null;
    }


    /**
     * Determines if the current change caused an axiom to be added to an ontology.
     */
    final protected boolean isAdd() {
        return add;
    }


    /**
     * Determines if the current change caused an axiom to be removed from an ontology.
     */
    final protected boolean isRemove() {
        return !add;
    }


    /**
     * Gets the ontology which the current change being visited was applied to.
     *
     * @return The ontology or <code>null</code> if the filter is not in a change
     *         visit cycle.  When called from within a <code>visit</code> method, the
     *         return value is guarenteed not to be <code>null</code>.
     */
    final protected OWLOntology getOntology() {
        return ontology;
    }
}
