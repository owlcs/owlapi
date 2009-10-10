package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;
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


    public void visit(SWRLIndividualArgument node) {

    }


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
