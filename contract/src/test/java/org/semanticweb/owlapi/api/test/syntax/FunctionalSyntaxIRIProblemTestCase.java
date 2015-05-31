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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class FunctionalSyntaxIRIProblemTestCase extends TestBase {

    @Test
    public void testmain() throws Exception {
        OWLOntology ontology = getOWLOntology();
        OWLObjectProperty p = df.getOWLObjectProperty("http://example.org/A_#part_of");
        OWLClass a = Class(IRI("http://example.org/A_A"));
        OWLClass b = Class(IRI("http://example.org/A_B"));
        ontology.add(Declaration(p), Declaration(a), Declaration(b),
                SubClassOf(b, df.getOWLObjectSomeValuesFrom(p, a)));
        OWLOntology loadOntology = roundTrip(ontology, new RDFXMLDocumentFormat());
        FunctionalSyntaxDocumentFormat functionalFormat = new FunctionalSyntaxDocumentFormat();
        functionalFormat.asPrefixOWLOntologyFormat().setPrefix("example", "http://example.org/");
        OWLOntology loadOntology2 = roundTrip(ontology, functionalFormat);
        // won't reach here if functional syntax fails - comment it out and
        // uncomment this to test Manchester
        ManchesterSyntaxDocumentFormat manchesterFormat = new ManchesterSyntaxDocumentFormat();
        manchesterFormat.asPrefixOWLOntologyFormat().setPrefix("example", "http://example.org/");
        OWLOntology loadOntology3 = roundTrip(ontology, manchesterFormat);
        assertEquals(ontology, loadOntology);
        assertEquals(ontology, loadOntology2);
        assertEquals(ontology, loadOntology3);
        assertEquals(asSet(ontology.axioms()), asSet(loadOntology.axioms()));
        assertEquals(asSet(ontology.axioms()), asSet(loadOntology2.axioms()));
        assertEquals(asSet(ontology.axioms()), asSet(loadOntology3.axioms()));
    }
}
