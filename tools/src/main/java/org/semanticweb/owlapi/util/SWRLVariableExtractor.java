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
package org.semanticweb.owlapi.util;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Extracts the variables from rules.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public class SWRLVariableExtractor implements SWRLObjectVisitorEx<Collection<SWRLVariable>> {

    private final LinkedHashSet<SWRLVariable> variables = new LinkedHashSet<>();

    /**
     * @return the set of variables
     */
    public LinkedHashSet<SWRLVariable> getVariables() {
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLRule node) {
        node.body().forEach(a -> a.accept(this));
        node.head().forEach(a -> a.accept(this));
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLClassAtom node) {
        node.getArgument().accept(this);
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLDataRangeAtom node) {
        node.getArgument().accept(this);
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLObjectPropertyAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLDataPropertyAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLBuiltInAtom node) {
        node.getArguments().forEach(a -> a.accept(this));
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLVariable node) {
        variables.add(node);
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLIndividualArgument node) {
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLLiteralArgument node) {
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLSameIndividualAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        return variables;
    }

    @Override
    public Collection<SWRLVariable> visit(SWRLDifferentIndividualsAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        return variables;
    }
}
