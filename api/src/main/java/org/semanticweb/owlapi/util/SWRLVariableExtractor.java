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

import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObject;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Jul-2007<br><br>
 *
 * Extracts the variables from rules
 */
public class SWRLVariableExtractor implements SWRLObjectVisitor {

    private Set<SWRLVariable> variables;


    public SWRLVariableExtractor() {
        variables = new LinkedHashSet<SWRLVariable>();
    }


    public Set<SWRLVariable> getVariables() {
        return new LinkedHashSet<SWRLVariable>(variables);
    }

    public void reset() {
        variables.clear();
    }


    public void visit(SWRLRule node) {
        for(SWRLAtom atom : node.getBody()) {
            atom.accept(this);
        }
        for(SWRLAtom atom : node.getHead()) {
            atom.accept(this);
        }
    }


    public void visit(SWRLClassAtom node) {
        node.getArgument().accept(this);
    }


    public void visit(SWRLDataRangeAtom node) {
        node.getArgument().accept(this);
    }


    public void visit(SWRLObjectPropertyAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDataPropertyAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLBuiltInAtom node) {
        for(SWRLObject o : node.getArguments()) {
            o.accept(this);
        }
    }


    public void visit(SWRLVariable node) {
        variables.add(node);
    }

    @SuppressWarnings("unused")
    public void visit(SWRLIndividualArgument node) {

    }

    @SuppressWarnings("unused")
    public void visit(SWRLLiteralArgument node) {
    }


    public void visit(SWRLSameIndividualAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }
}
