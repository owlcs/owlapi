package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
/*
 * Copyright (C) 2009, University of Manchester
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
        OWLLiteral lit = df.getOWLTypedLiteral(33);
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
