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
package org.semanticweb.owlapi.api.test.ontology;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractAxiomsRoundTrippingTestCase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Jul-2009
 */
public class SWRLRuleTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        SWRLVariable varX = Factory.getFactory().getSWRLVariable(
                IRI("urn:swrl#x"));
        SWRLVariable varY = Factory.getFactory().getSWRLVariable(
                IRI("urn:swrl#y"));
        SWRLVariable varZ = Factory.getFactory().getSWRLVariable(
                IRI("urn:swrl#z"));
        Set<SWRLAtom> body = new HashSet<SWRLAtom>();
        body.add(Factory.getFactory()
                .getSWRLClassAtom(Class(getIRI("A")), varX));
        SWRLIndividualArgument indIArg = Factory.getFactory()
                .getSWRLIndividualArgument(NamedIndividual(getIRI("i")));
        SWRLIndividualArgument indJArg = Factory.getFactory()
                .getSWRLIndividualArgument(NamedIndividual(getIRI("j")));
        body.add(Factory.getFactory().getSWRLClassAtom(Class(getIRI("D")),
                indIArg));
        body.add(Factory.getFactory()
                .getSWRLClassAtom(Class(getIRI("B")), varX));
        SWRLVariable varQ = Factory.getFactory().getSWRLVariable(
                IRI("urn:swrl#q"));
        SWRLVariable varR = Factory.getFactory().getSWRLVariable(
                IRI("urn:swrl#r"));
        body.add(Factory.getFactory().getSWRLDataPropertyAtom(
                DataProperty(getIRI("d")), varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = Factory.getFactory()
                .getSWRLLiteralArgument(lit);
        body.add(Factory.getFactory().getSWRLDataPropertyAtom(
                DataProperty(getIRI("d")), varY, litArg));
        Set<SWRLAtom> head = new HashSet<SWRLAtom>();
        head.add(Factory.getFactory()
                .getSWRLClassAtom(Class(getIRI("C")), varX));
        head.add(Factory.getFactory().getSWRLObjectPropertyAtom(
                ObjectProperty(getIRI("p")), varY, varZ));
        head.add(Factory.getFactory().getSWRLSameIndividualAtom(varX, varY));
        head.add(Factory.getFactory().getSWRLSameIndividualAtom(indIArg,
                indJArg));
        head.add(Factory.getFactory().getSWRLDifferentIndividualsAtom(varX,
                varZ));
        head.add(Factory.getFactory().getSWRLDifferentIndividualsAtom(varX,
                varZ));
        head.add(Factory.getFactory().getSWRLDifferentIndividualsAtom(indIArg,
                indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(
                ObjectProperty(getIRI("p")), Class(getIRI("A")));
        head.add(Factory.getFactory().getSWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<SWRLDArgument>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(Factory.getFactory().getSWRLBuiltInAtom(
                IRI("http://www.owlapi#myBuiltIn"), args));
        axioms.add(Factory.getFactory().getSWRLRule(body, head));
        return axioms;
    }

    @Test
    @Override
    public void testManchesterOWLSyntax() throws Exception {
        try {
            super.testManchesterOWLSyntax();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
