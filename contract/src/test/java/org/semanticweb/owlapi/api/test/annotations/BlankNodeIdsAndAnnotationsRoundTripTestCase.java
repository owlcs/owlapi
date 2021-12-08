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
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

class BlankNodeIdsAndAnnotationsRoundTripTestCase extends TestBase {
    static final OWLAnnotation ann1 = RDFSComment("test for anon class in object position");
    static final OWLAnnotation ann2 = RDFSComment("test for named class in object position");
    static final OWLAnnotation ann3 = RDFSComment("test for anon individual in object position");
    static final OWLAnnotation ann4 = Annotation(RDFSLabel("nested label"), RDFSComment(),
        Literal("test for second anon class in object position"));
    static final OWLAnnotation ann5 = RDFSComment("test for second named class in object position");
    static final OWLAnnotation ann6 = RDFSComment("test for third anon class in object position");
    static final OWLAnonymousIndividual a = AnonymousIndividual();
    static final OWLAnonymousIndividual b = AnonymousIndividual();
    static final OWLAnonymousIndividual a1 = AnonymousIndividual();
    static final OWLAnonymousIndividual a2 = AnonymousIndividual();
    static final OWLClassExpression ce1 = ObjectAllValuesFrom(op1, C4);
    static final OWLClassExpression ce2 = ObjectSomeValuesFrom(op1, OWLThing());
    static final OWLClassExpression ce3 = ObjectExactCardinality(1, op1, OWLThing());
    static final OWLClassExpression ce4 =
        ObjectIntersectionOf(ObjectExactCardinality(2, op1, OWLThing()),
            DataExactCardinality(3, DP), ObjectComplementOf(ce3), ce3);
    static final Set<OWLDocumentFormat> singleAxiomsLost = set(new TrigDocumentFormat(),
        new RDFJsonLDDocumentFormat(), new NTriplesDocumentFormat(), new RDFXMLDocumentFormat(),
        new RDFJsonDocumentFormat(), new NQuadsDocumentFormat(), new TurtleDocumentFormat(),
        new RioTurtleDocumentFormat(), new RioRDFXMLDocumentFormat());

    protected OWLOntology blankNodeIdsAndAnnotationsRoundTripTestCase(OWLOntology ont1) {
        ont1.add(ClassAssertion(ann1, ObjectComplementOf(C4), a),
            ClassAssertion(ann5, ObjectComplementOf(C5), a), ClassAssertion(ce1, a),
            ClassAssertion(ce2, a), ClassAssertion(ce3, a), ClassAssertion(ann6, ce3, a),
            ClassAssertion(ce4, a), ClassAssertion(ann4, ce4, a),
            ObjectPropertyAssertion(ann3, op1, a1, a2), ClassAssertion(ce1, b),
            ClassAssertion(ce2, b), ClassAssertion(C4, a), ClassAssertion(ann2, C4, a));
        return ont1;
    }

    @Override
    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        // Axioms without annotations are lost if identical axioms with
        // annotations exist.
        // This is not a code defect, it's a consequence of the mapping specs.
        // To allow roundtripping to work and help verify the rest of the axioms are accurately
        // written out and parsed, this method adds the lost unannotated axioms.
        if (singleAxiomsLost.contains(ont2.getFormat())) {
            OWLIndividual annotated = ont2.axioms(AxiomType.CLASS_ASSERTION)
                .filter(assertion -> assertion.annotations().anyMatch(ann2::equals))
                .map(assertion -> assertion.getIndividual()).findAny().orElse(null);
            if (annotated != null) {
                ont2.add(ClassAssertion(ce3, annotated), ClassAssertion(C4, annotated),
                    ClassAssertion(ce4, annotated));
            }
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
