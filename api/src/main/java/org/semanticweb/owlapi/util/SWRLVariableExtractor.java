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
