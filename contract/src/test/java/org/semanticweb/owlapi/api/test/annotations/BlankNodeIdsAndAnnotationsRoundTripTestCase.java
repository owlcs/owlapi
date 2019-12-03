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
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.api.test.baseclasses.AbstractRoundTrippingTestCase;
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
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class BlankNodeIdsAndAnnotationsRoundTripTestCase extends AbstractRoundTrippingTestCase {
    private final OWLAnnotation ann1 = df.getRDFSComment("test for anon class in object position");
    private final OWLAnnotation ann2 = df.getRDFSComment("test for named class in object position");
    private final OWLAnnotation ann3 =
        df.getRDFSComment("test for anon individual in object position");
    private final OWLAnnotation ann4 = df.getOWLAnnotation(df.getRDFSComment(),
        df.getOWLLiteral("test for second anon class in object position"),
        df.getRDFSLabel("nested label"));
    private final OWLAnnotation ann5 =
        df.getRDFSComment("test for second named class in object position");
    private final OWLAnnotation ann6 =
        df.getRDFSComment("test for third anon class in object position");
    private final OWLClass c4 = df.getOWLClass("urn:test:c4");
    private final OWLClass c5 = df.getOWLClass("urn:test:c5");
    private final OWLObjectProperty op = df.getOWLObjectProperty("urn:test:op");
    private final OWLDataProperty dp = df.getOWLDataProperty("urn:test:dp");
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

    @Override
    protected OWLOntology createOntology() {
        OWLOntology ont1 = getOWLOntology();
        ont1.add(
            df.getOWLClassAssertionAxiom(df.getOWLObjectComplementOf(c4), a, Arrays.asList(ann1)),
            df.getOWLClassAssertionAxiom(df.getOWLObjectComplementOf(c5), a, Arrays.asList(ann5)),
            df.getOWLClassAssertionAxiom(ce1, a), df.getOWLClassAssertionAxiom(ce2, a),
            df.getOWLClassAssertionAxiom(ce3, a),
            df.getOWLClassAssertionAxiom(ce3, a, Arrays.asList(ann6)),
            df.getOWLClassAssertionAxiom(ce4, a),
            df.getOWLClassAssertionAxiom(ce4, a, Arrays.asList(ann4)),
            df.getOWLObjectPropertyAssertionAxiom(op, a1, a2, Arrays.asList(ann3)),
            df.getOWLClassAssertionAxiom(ce1, b), df.getOWLClassAssertionAxiom(ce2, b),
            df.getOWLClassAssertionAxiom(c4, a),
            df.getOWLClassAssertionAxiom(c4, a, Arrays.asList(ann2)));

        return ont1;
    }

    @Override
    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        // Axioms without annotations are lost if identical axioms with annotations exist.
        // This is not a code defect, it's a consequence of the mapping specs.
        // To allow roundtripping to work and help verify the rest of the axioms are accurately
        // written out and parsed, this method adds the lost unannotated axioms.
        if (singleAxiomsLost.contains(ont2.getFormat())) {
            OWLIndividual i = ont2.axioms(AxiomType.CLASS_ASSERTION)
                .filter(x -> x.annotations().anyMatch(ann2::equals)).map(x -> x.getIndividual())
                .findAny().orElse(null);
            if (i != null) {
                ont2.add(df.getOWLClassAssertionAxiom(ce3, i), df.getOWLClassAssertionAxiom(c4, i),
                    df.getOWLClassAssertionAxiom(ce4, i));
            }
        }
        return super.equal(ont1, ont2);
    }
}
