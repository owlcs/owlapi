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
package org.semanticweb.owlapi.profiles;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.StructureWalker;

/**
 * A specialized walker that skips visiting annotation assertion literals. This
 * is used by profile checkers to skip literals in annotations.
 * 
 * @author ignazio
 */
public class OWLOntologyProfileWalker extends OWLOntologyWalker {

    /**
     * @param objects
     *        ontologies to walk
     */
    public OWLOntologyProfileWalker(@Nonnull Set<OWLOntology> objects) {
        super(objects);
        setStructureWalker(new StructureWalker<OWLOntology>(this) {

            @Override
            public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
                process(axiom);
                if (axiom.getSubject() instanceof IRI) {
                    // do not visit anonymous nodes from annotations
                    axiom.getSubject().accept(this);
                }
                axiom.getAnnotation().accept(this);
            }

            @Override
            public void visit(OWLAnnotation node) {
                process(node);
                node.getProperty().accept(this);
                // only visit IRIs
                if (node.getValue() instanceof IRI) {
                    node.getValue().accept(this);
                }
            }

            @Override
            public void visit(OWLDeclarationAxiom axiom) {
                process(axiom);
                walkerCallback.setAxiom(axiom);
                // do not visit entities from declarations, only their IRIs
                axiom.getEntity().getIRI().accept(this);
            }
        });
    }
}
