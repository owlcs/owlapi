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
package org.semanticweb.owlapi.api.test.individuals;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;

import java.util.Arrays;
import java.util.HashSet;

import org.semanticweb.owlapi.api.test.baseclasses.AxiomsRoundTrippingBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class SameIndividualsQuadrupletTestCase extends AxiomsRoundTrippingBase {

    private static final OWLClass a = Class(iri("a"));
    private static final OWLClass b = Class(iri("b"));
    private static final OWLClass c = Class(iri("c"));
    private static final OWLClass d = Class(iri("d"));

    private static final OWLDataProperty p = DataProperty(iri("p"));
    private static final OWLDataProperty q = DataProperty(iri("q"));
    private static final OWLDataProperty r = DataProperty(iri("r"));
    private static final OWLDataProperty s = DataProperty(iri("s"));

    private static final OWLObjectProperty t = ObjectProperty(iri("t"));
    private static final OWLObjectProperty u = ObjectProperty(iri("u"));
    private static final OWLObjectProperty w = ObjectProperty(iri("w"));
    private static final OWLObjectProperty z = ObjectProperty(iri("z"));

    private static final OWLNamedIndividual i = NamedIndividual(iri("i"));
    private static final OWLNamedIndividual j = NamedIndividual(iri("j"));
    private static final OWLNamedIndividual k = NamedIndividual(iri("k"));
    private static final OWLNamedIndividual l = NamedIndividual(iri("l"));
    private static final OWLSameIndividualAxiom axiom1 = SameIndividual(i, j, k, l);
    private static final OWLEquivalentClassesAxiom axiom2 = EquivalentClasses(a, b, c, d);
    private static final OWLEquivalentObjectPropertiesAxiom axiom3 =
        EquivalentObjectProperties(t, u, w, z);
    private static final OWLEquivalentDataPropertiesAxiom axiom4 =
        EquivalentDataProperties(p, q, r, s);

    public SameIndividualsQuadrupletTestCase() {
        super((AxiomBuilder) () -> new HashSet<>(Arrays.asList(axiom1, axiom2, axiom3, axiom4)));
    }

    @Override
    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        // More than two individuals in a sameAs cannot be roundtripped traditionally in RDF based
        // languages
        assertTrue(ont1.containsAxiom(axiom1));
        assertTrue(ont1.containsAxiom(axiom2));
        assertTrue(ont1.containsAxiom(axiom3));
        assertTrue(ont1.containsAxiom(axiom4));
        if (!ont2.containsAxiom(axiom1)) {
            axiom1.splitToAnnotatedPairs().forEach(ax -> assertAxiomContained(ax, ont2));
        }
        if (!ont2.containsAxiom(axiom2)) {
            axiom2.splitToAnnotatedPairs().forEach(ax -> assertAxiomContained(ax, ont2));
        }
        if (!ont2.containsAxiom(axiom3)) {
            axiom3.splitToAnnotatedPairs().forEach(ax -> assertAxiomContained(ax, ont2));
        }
        if (!ont2.containsAxiom(axiom4)) {
            axiom4.splitToAnnotatedPairs().forEach(ax -> assertAxiomContained(ax, ont2));
        }
        return true;
    }

    protected void assertAxiomContained(OWLAxiom ax, OWLOntology ont2) {
        assertTrue(ax + "\t" + ont2.toString(), ont2.containsAxiom(ax));
    }

    @Override
    public void roundTripRDFXMLAndFunctionalShouldBeSame()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ont = createOntology();
        OWLOntology o1 = roundTrip(ont, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(ont, new FunctionalSyntaxDocumentFormat());
        equal(ont, o1);
        equal(o2, o1);
    }
}
