package org.coode.owlapi.latex;

import org.semanticweb.owlapi.model.*;
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
 * Medical Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 */
public class LatexBracketChecker implements OWLClassExpressionVisitor {

    private boolean requiresBracket;

    private static LatexBracketChecker instance;

    private LatexBracketChecker() {

    }

    public void visit(OWLObjectIntersectionOf node) {
        requiresBracket = true;
    }

    public void visit(OWLDataAllValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLDataCardinalityRestriction node) {
        requiresBracket = true;
    }

    public void visit(OWLDataSomeValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLDataHasValue node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectAllValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectCardinalityRestriction node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectSomeValuesFrom node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectHasValue node) {
        requiresBracket = true;
    }

    public void visit(OWLObjectComplementOf node) {
        requiresBracket = false;
    }

    public void visit(OWLObjectUnionOf node) {
        requiresBracket = true;
    }

    public void visit(OWLClass node) {
        requiresBracket = false;
    }

    public void visit(OWLObjectOneOf node) {
        requiresBracket = true;
    }


    public void visit(OWLDataExactCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLDataMaxCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLDataMinCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectExactCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectMaxCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectMinCardinality desc) {
        requiresBracket = true;
    }


    public void visit(OWLObjectHasSelf owlHasSelf) {
        requiresBracket = true;
    }


    public static boolean requiresBracket(OWLClassExpression classExpression) {
        if (instance == null) {
            instance = new LatexBracketChecker();
        }
        instance.requiresBracket = true;
        classExpression.accept(instance);
        return instance.requiresBracket;

    }
}
