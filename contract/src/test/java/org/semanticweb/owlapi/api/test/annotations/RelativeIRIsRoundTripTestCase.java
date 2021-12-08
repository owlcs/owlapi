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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

class RelativeIRIsRoundTripTestCase extends TestBase {
    private static final List<OWLAnnotation> ANNS =
        l(Annotation(AP, Literal("x")), Annotation(propP, Literal("y")));

    protected OWLOntology relativeIRIsRoundTripTestCase() {
        OWLClassExpression c1 = DataAllValuesFrom(DP, Boolean());
        OWLClassExpression c2 = ObjectSomeValuesFrom(P, OWLThing());
        OWLAxiom a = SubClassOf(ANNS, c1, c2);
        OWLOntology ont1 = create(iri("http://www.semanticweb.org/owlapi/", ""));
        ont1.add(a);
        return ont1;
    }

    @Override
    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        if (!ont2.containsDataPropertyInSignature(DP.getIRI())) {
            OWLEntityRenamer renamer = new OWLEntityRenamer(ont2.getOWLOntologyManager(), l(ont2));
            ont2.applyChanges(renamer.changeIRI(relativise(DP), DP.getIRI()));
            ont2.applyChanges(renamer.changeIRI(relativise(P), P.getIRI()));
            ont2.applyChanges(renamer.changeIRI(relativise(AP), AP.getIRI()));
            ont2.applyChanges(renamer.changeIRI(relativise(propP), propP.getIRI()));
        }
        return super.equal(ont1, ont2);
    }

    protected IRI relativise(HasIRI entity) {
        return iri("http://www.semanticweb.org/owlapi/", entity.getIRI().toString());
    }

    @ParameterizedTest
    @MethodSource("formats")
    void testFormat(OWLDocumentFormat format) {
        roundTripOntology(relativeIRIsRoundTripTestCase(), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSame() {
        OWLOntology ont = relativeIRIsRoundTripTestCase();
        OWLOntology o1 = roundTrip(ont, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(ont, new FunctionalSyntaxDocumentFormat());
        equal(ont, o1);
        equal(o1, o2);
    }
}
