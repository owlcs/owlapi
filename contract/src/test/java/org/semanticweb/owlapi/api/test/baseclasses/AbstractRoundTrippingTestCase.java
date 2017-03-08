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
package org.semanticweb.owlapi.api.test.baseclasses;

import org.junit.Test;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.KRSS2DocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public abstract class AbstractRoundTrippingTestCase extends TestBase {

    protected abstract OWLOntology createOntology();

    @Test
    public void testRDFXML() throws Exception {
        roundTripOntology(createOntology());
    }

    @Test
    public void testRDFJSON() throws Exception {
        roundTripOntology(createOntology(), new RDFJsonDocumentFormat());
    }

    @Test
    public void testOWLXML() throws Exception {
        roundTripOntology(createOntology(), new OWLXMLDocumentFormat());
    }

    @Test
    public void testFunctionalSyntax() throws Exception {
        roundTripOntology(createOntology(), new FunctionalSyntaxDocumentFormat());
    }

    @Test
    public void testTurtle() throws Exception {
        roundTripOntology(createOntology(), new TurtleDocumentFormat());
    }

    @Test
    public void testManchesterOWLSyntax() throws Exception {
        roundTripOntology(createOntology(), new ManchesterSyntaxDocumentFormat());
    }

    @Test
    public void testTrig() throws Exception {
        roundTripOntology(createOntology(), new TrigDocumentFormat());
    }

    @Test
    public void testJSONLD() throws Exception {
        roundTripOntology(createOntology(), new RDFJsonLDDocumentFormat());
    }

    @Test
    public void testNTriples() throws Exception {
        roundTripOntology(createOntology(), new NTriplesDocumentFormat());
    }

    @Test
    public void testNQuads() throws Exception {
        roundTripOntology(createOntology(), new NQuadsDocumentFormat());
    }

    public void testKRSS2() throws Exception {
        roundTripOntology(createOntology(), new KRSS2DocumentFormat());
    }

    public void testKRSS() throws Exception {
        roundTripOntology(createOntology(), new KRSS2DocumentFormat());
    }

    public void testDLSyntax() throws Exception {
        roundTripOntology(createOntology(), new DLSyntaxDocumentFormat());
    }

    @Test
    public void roundTripRDFXMLAndFunctionalShouldBeSame() throws OWLOntologyStorageException {
        OWLOntology ont = createOntology();
        OWLOntology o1 = roundTrip(ont, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(ont, new FunctionalSyntaxDocumentFormat());
        equal(ont, o1);
        equal(o1, o2);
    }
}
