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
package org.semanticweb.owlapi.api.test.annotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

class BlankNodeIdsAndAnnotationsRoundTripTestCase extends TestBase {
    private static OWLAnnotation comment(String value) {
        return df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral(value));
    }

    private static OWLAnnotation label(String value) {
        return df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(value));
    }

    private final OWLAnnotation ann1 = comment("test for anon class in object position");
    private final OWLAnnotation ann2 = comment("test for named class in object position");
    private final OWLAnnotation ann3 = comment("test for anon individual in object position");
    private final OWLAnnotation ann4 = df.getOWLAnnotation(df.getRDFSComment(),
        df.getOWLLiteral("test for second anon class in object position"),
        Collections.singleton(label("nested label")));
    private final OWLAnnotation ann5 = comment("test for second named class in object position");
    private final OWLAnnotation ann6 = comment("test for third anon class in object position");
    private final OWLClass c4 = df.getOWLClass(IRI.create("urn:test:c4"));
    private final OWLClass c5 = df.getOWLClass(IRI.create("urn:test:c5"));
    private final OWLObjectProperty op = df.getOWLObjectProperty(IRI.create("urn:test:op"));
    private final OWLDataProperty dp = df.getOWLDataProperty(IRI.create("urn:test:dp"));
    private final OWLAnonymousIndividual a = df.getOWLAnonymousIndividual();
    private final OWLAnonymousIndividual b = df.getOWLAnonymousIndividual();
    private final OWLAnonymousIndividual a1 = df.getOWLAnonymousIndividual();
    private final OWLAnonymousIndividual a2 = df.getOWLAnonymousIndividual();
    private final OWLClassExpression ce1 = df.getOWLObjectAllValuesFrom(op, c4);
    private final OWLClassExpression ce2 = df.getOWLObjectSomeValuesFrom(op, df.getOWLThing());
    private final OWLClassExpression ce3 = df.getOWLObjectExactCardinality(1, op);
    private final OWLClassExpression ce4 =
        df.getOWLObjectIntersectionOf(df.getOWLObjectExactCardinality(2, op),
            df.getOWLDataExactCardinality(3, dp), df.getOWLObjectComplementOf(ce3), ce3);

    private final Set<OWLDocumentFormat> singleAxiomsLost =
        new HashSet<>(Arrays.asList(new TrigDocumentFormat(), new RDFJsonLDDocumentFormat(),
            new NTriplesDocumentFormat(), new RDFXMLDocumentFormat(), new RDFJsonDocumentFormat(),
            new NQuadsDocumentFormat(), new TurtleDocumentFormat(), new RioTurtleDocumentFormat(),
            new RioRDFXMLDocumentFormat()));

    protected OWLOntology blankNodeIdsAndAnnotationsRoundTripTestCase(OWLOntology ont1) {
        ont1.getOWLOntologyManager().addAxioms(ont1,
            new HashSet<>(Arrays.asList(
                df.getOWLClassAssertionAxiom(df.getOWLObjectComplementOf(c4), a,
                    Collections.singleton(ann1)),
                df.getOWLClassAssertionAxiom(df.getOWLObjectComplementOf(c5), a,
                    Collections.singleton(ann5)),
                df.getOWLClassAssertionAxiom(ce1, a), df.getOWLClassAssertionAxiom(ce2, a),
                df.getOWLClassAssertionAxiom(ce3, a),
                df.getOWLClassAssertionAxiom(ce3, a, Collections.singleton(ann6)),
                df.getOWLClassAssertionAxiom(ce4, a),
                df.getOWLClassAssertionAxiom(ce4, a, Collections.singleton(ann4)),
                df.getOWLObjectPropertyAssertionAxiom(op, a1, a2, Collections.singleton(ann3)),
                df.getOWLClassAssertionAxiom(ce1, b), df.getOWLClassAssertionAxiom(ce2, b),
                df.getOWLClassAssertionAxiom(c4, a),
                df.getOWLClassAssertionAxiom(c4, a, Collections.singleton(ann2)))));

        return ont1;
    }

    @Override
    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        // Axioms without annotations are lost if identical axioms with
        // annotations exist.
        // This is not a code defect, it's a consequence of the mapping specs.
        // To allow roundtripping to work and help verify the rest of the axioms are accurately
        // written out and parsed, this method adds the lost unannotated axioms.
        if (singleAxiomsLost.contains(ont2.getOWLOntologyManager().getOntologyFormat(ont2))) {
            ont2.getAxioms(AxiomType.CLASS_ASSERTION).stream()
                .filter(x -> x.getAnnotations().stream().anyMatch(ann2::equals))
                .map(x -> x.getIndividual()).findAny()
                .ifPresent(individual -> ont2.getOWLOntologyManager().addAxioms(ont2,
                    new HashSet<>(Arrays.asList(df.getOWLClassAssertionAxiom(ce3, individual),
                        df.getOWLClassAssertionAxiom(c4, individual),
                        df.getOWLClassAssertionAxiom(ce4, individual)))));
        }
        return super.equal(ont1, ont2);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void testFormat(OWLDocumentFormat format) {
        roundTripOntology(blankNodeIdsAndAnnotationsRoundTripTestCase(createAnon()), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSame() {
        OWLOntology o = blankNodeIdsAndAnnotationsRoundTripTestCase(createAnon());
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
        equal(o1, o2);
    }
}
