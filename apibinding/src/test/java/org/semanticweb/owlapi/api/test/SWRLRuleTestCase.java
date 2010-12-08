package org.semanticweb.owlapi.api.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Jul-2009
 */
public class SWRLRuleTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLDataFactory df = getFactory();
        SWRLVariable varX = df.getSWRLVariable(IRI.create("http://www.owlapi#x"));
        SWRLVariable varY = df.getSWRLVariable(IRI.create("http://www.owlapi#y"));
        SWRLVariable varZ = df.getSWRLVariable(IRI.create("http://www.owlapi#z"));
        Set<SWRLAtom> body = new HashSet<SWRLAtom>();
        body.add(df.getSWRLClassAtom(getOWLClass("A"), varX));
        SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(getOWLIndividual("i"));
        SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(getOWLIndividual("j"));
        body.add(df.getSWRLClassAtom(getOWLClass("D"), indIArg));
        body.add(df.getSWRLClassAtom(getOWLClass("B"), varX));
        SWRLVariable varQ = df.getSWRLVariable(IRI.create("http://www.owlapi#q"));
        SWRLVariable varR = df.getSWRLVariable(IRI.create("http://www.owlapi#r"));
        body.add(df.getSWRLDataPropertyAtom(getOWLDataProperty("d"), varX, varQ));
        OWLLiteral lit = df.getOWLLiteral(33);
        SWRLLiteralArgument litArg = df.getSWRLLiteralArgument(lit);
        body.add(df.getSWRLDataPropertyAtom(getOWLDataProperty("d"), varY, litArg));
        Set<SWRLAtom> head = new HashSet<SWRLAtom>();
        head.add(df.getSWRLClassAtom(getOWLClass("C"), varX));
        head.add(df.getSWRLObjectPropertyAtom(getOWLObjectProperty("p"), varY, varZ));
        head.add(df.getSWRLSameIndividualAtom(varX, varY));
        head.add(df.getSWRLSameIndividualAtom(indIArg, indJArg));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(indIArg, indJArg));

        List<SWRLDArgument> args = new ArrayList<SWRLDArgument>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(df.getSWRLBuiltInAtom(IRI.create("http://www.owlapi#myBuiltIn"), args));
        axioms.add(df.getSWRLRule(body, head));
        return axioms;
    }
}
