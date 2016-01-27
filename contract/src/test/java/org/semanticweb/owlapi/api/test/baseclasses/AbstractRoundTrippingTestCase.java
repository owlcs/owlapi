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

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.formats.*;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public abstract class AbstractRoundTrippingTestCase extends TestBase {

    private OWLOntology ont;

    @Nonnull
    protected abstract OWLOntology createOntology();

    @Nonnull
    protected OWLOntology getOnt() {
        if (ont == null) {
            ont = createOntology();
        }
        return ont;
    }

    @Test
    public void testRDFXML() throws Exception {
        roundTripOntology(getOnt());
    }

    @Test
    public void testRDFJSON() throws Exception {
        roundTripOntology(getOnt(), new RDFJsonDocumentFormat());
    }

    @Test
    public void testOWLXML() throws Exception {
        roundTripOntology(getOnt(), new OWLXMLDocumentFormat());
    }

    @Test
    public void testFunctionalSyntax() throws Exception {
        roundTripOntology(getOnt(), new FunctionalSyntaxDocumentFormat());
    }

    @Test
    public void testTurtle() throws Exception {
        roundTripOntology(getOnt(), new TurtleDocumentFormat());
    }

    @Test
    public void testManchesterOWLSyntax() throws Exception {
        roundTripOntology(getOnt(), new ManchesterSyntaxDocumentFormat());
    }

    @Override
    protected boolean isIgnoreDeclarationAxioms(OWLDocumentFormat format) {
        return format instanceof ManchesterSyntaxDocumentFormat;
    }

    @Test
    public void testTrig() throws Exception {
        roundTripOntology(getOnt(), new TrigDocumentFormat());
    }

    @Test
    public void testJSONLD() throws Exception {
        roundTripOntology(getOnt(), new RDFJsonLDDocumentFormat());
    }

    @Test
    public void testNTriples() throws Exception {
        roundTripOntology(getOnt(), new NTriplesDocumentFormat());
    }

    @Test
    public void testNQuads() throws Exception {
        roundTripOntology(getOnt(), new NQuadsDocumentFormat());
    }

    @Test
    public void roundTripRDFXMLAndFunctionalShouldBeSame() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLOntology o1 = roundTrip(getOnt(), new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(getOnt(), new FunctionalSyntaxDocumentFormat());
        equal(getOnt(), o1);
        equal(o1, o2);
    }
}
