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
package org.semanticweb.owlapi.latex.renderer;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public final class LatexBracketChecker implements OWLClassExpressionVisitor {

    private boolean requiresBracket;
    private static final @Nonnull LatexBracketChecker INSTANCE = new LatexBracketChecker();

    private LatexBracketChecker() {}

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        requiresBracket = true;
    }

    /**
     * @param node
     *        node
     */
    @SuppressWarnings("unused")
    public void visit(OWLDataCardinalityRestriction node) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        requiresBracket = true;
    }

    /**
     * @param node
     *        node
     */
    @SuppressWarnings("unused")
    public void visit(OWLObjectCardinalityRestriction node) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        requiresBracket = false;
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLClass ce) {
        requiresBracket = false;
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        requiresBracket = true;
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        requiresBracket = true;
    }

    /**
     * @param classExpression
     *        class expression
     * @return true if bracket required
     */
    public static boolean requiresBracket(OWLClassExpression classExpression) {
        INSTANCE.requiresBracket = true;
        classExpression.accept(INSTANCE);
        return INSTANCE.requiresBracket;
    }
}
