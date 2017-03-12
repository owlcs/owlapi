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
package org.semanticweb.owlapi.api.test.literals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.util.OWLLiteralReplacer;
import org.semanticweb.owlapi.util.OWLObjectTransformer;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class TypedLiteralsTestCase extends TestBase {

    OWLDataProperty prop = DataProperty(iri("p"));
    OWLNamedIndividual ind = NamedIndividual(iri("i"));

    protected OWLOntology createAxioms() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        o.add(DataPropertyAssertion(prop, ind, Literal(3)));
        o.add(DataPropertyAssertion(prop, ind, Literal(33.3)));
        o.add(DataPropertyAssertion(prop, ind, Literal(true)));
        o.add(DataPropertyAssertion(prop, ind, Literal(33.3f)));
        o.add(DataPropertyAssertion(prop, ind, Literal("33.3")));
        return o;
    }

    @Test
    public void shouldReplaceLiterals() throws OWLOntologyCreationException {
        OWLOntology o = createAxioms();
        OWLLiteralReplacer replacer =
            new OWLLiteralReplacer(o.getOWLOntologyManager(), Collections.singleton(o));
        Map<OWLLiteral, OWLLiteral> replacements = new HashMap<>();
        replacements.put(Literal(true), Literal(false));
        replacements.put(Literal(3), Literal(4));
        List<OWLOntologyChange> results = replacer.changeLiterals(replacements);
        assertResults(o, results);
    }

    protected void assertResults(OWLOntology o, List<OWLOntologyChange> r) {
        assertTrue(r.contains(new AddAxiom(o, DataPropertyAssertion(prop, ind, Literal(4)))));
        assertTrue(r.contains(new AddAxiom(o, DataPropertyAssertion(prop, ind, Literal(false)))));
        assertTrue(r.contains(new RemoveAxiom(o, DataPropertyAssertion(prop, ind, Literal(3)))));
        assertTrue(r.contains(new RemoveAxiom(o, DataPropertyAssertion(prop, ind, Literal(true)))));
        assertEquals(4, r.size());
    }

    @Test
    public void shouldReplaceLiteralsWithTransformer() throws OWLOntologyCreationException {
        OWLOntology o = createAxioms();
        final Map<OWLLiteral, OWLLiteral> replacements = new HashMap<>();
        replacements.put(Literal(true), Literal(false));
        replacements.put(Literal(3), Literal(4));
        OWLObjectTransformer<OWLLiteral> replacer =
            new OWLObjectTransformer<>((x) -> true, (input) -> {
                OWLLiteral l = replacements.get(input);
                if (l == null) {
                    return input;
                }
                return l;
            }, df, OWLLiteral.class);
        List<OWLOntologyChange> results = replacer.change(o);
        assertResults(o, results);
    }
}
