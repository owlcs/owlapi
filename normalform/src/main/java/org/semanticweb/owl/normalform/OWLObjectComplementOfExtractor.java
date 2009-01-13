package org.semanticweb.owl.normalform;

import org.semanticweb.owl.model.*;

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
 *
 * Extracts the parts of a class expression which are negated.
 * For example, A and not (B or C or not D) would extract
 * {(B or C or notD), D}
 */
public class OWLObjectComplementOfExtractor implements OWLDescriptionVisitor {

    private Set<OWLDescription> result;


    public OWLObjectComplementOfExtractor() {
        result = new HashSet<OWLDescription>();
    }

    public Set<OWLDescription> getComplementedDescriptions(OWLDescription desc) {
        reset();
        desc.accept(this);
        return new HashSet<OWLDescription>(result);
    }

    public void reset() {
        result.clear();
    }

    public void visit(OWLClass desc) {
    }


    public void visit(OWLDataAllRestriction desc) {
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
    }


    public void visit(OWLDataSomeRestriction desc) {
    }


    public void visit(OWLDataValueRestriction desc) {
    }


    public void visit(OWLObjectAllRestriction desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectComplementOf desc) {
        result.add(desc.getOperand());
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectIntersectionOf desc) {
        for(OWLDescription op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {

    }


    public void visit(OWLObjectSelfRestriction desc) {

    }


    public void visit(OWLObjectSomeRestriction desc) {
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectUnionOf desc) {
        for(OWLDescription op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectValueRestriction desc) {
        
    }
}
