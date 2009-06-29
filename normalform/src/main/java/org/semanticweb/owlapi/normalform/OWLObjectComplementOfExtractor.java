package org.semanticweb.owlapi.normalform;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
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
 * Date: 13-Oct-2007<br><br>
 * <p/>
 * Extracts the parts of a class expression which are negated.
 * For example, A and not (B or C or not D) would extract
 * {(B or C or notD), D}
 */
public class OWLObjectComplementOfExtractor implements OWLClassExpressionVisitor {

    private Set<OWLClassExpression> result;


    public OWLObjectComplementOfExtractor() {
        result = new HashSet<OWLClassExpression>();
    }

    public Set<OWLClassExpression> getComplementedClassExpressions(OWLClassExpression desc) {
        reset();
        desc.accept(this);
        return new HashSet<OWLClassExpression>(result);
    }

    public void reset() {
        result.clear();
    }

    public void visit(OWLClass desc) {
    }


    public void visit(OWLDataAllValuesFrom desc) {
    }


    public void visit(OWLDataExactCardinality desc) {
    }


    public void visit(OWLDataMaxCardinality desc) {
    }


    public void visit(OWLDataMinCardinality desc) {
    }


    public void visit(OWLDataSomeValuesFrom desc) {
    }


    public void visit(OWLDataHasValue desc) {
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectComplementOf desc) {
        result.add(desc.getOperand());
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectExactCardinality desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectIntersectionOf desc) {
        for (OWLClassExpression op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectMaxCardinality desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectMinCardinality desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {

    }


    public void visit(OWLObjectHasSelf desc) {

    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectUnionOf desc) {
        for (OWLClassExpression op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectHasValue desc) {

    }
}
