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

import static org.junit.jupiter.api.Assertions.fail;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.util.List;

import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.IllegalElementNameException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class NoQNameRoundTripTestCase extends TestBase {

    OWLOntology noQNameRoundTripTestCase() {
        OWLOntology ont =
            o(ObjectPropertyAssertion(ObjectProperty(iri("http://example.com/place/123", "")),
                NamedIndividual(
                    iri("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395A", "")),
                NamedIndividual(
                    iri("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395B", ""))));
        ont.unsortedSignature()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, INCLUDED))
            .forEach(entity -> ont.add(Declaration(entity)));
        return ont;
    }

    @Test
    void testRDFXML() {
        try {
            roundTripOntology(noQNameRoundTripTestCase(), new RDFXMLDocumentFormat());
            fail("Expected an exception specifying that a QName could not be generated");
        } catch (OWLRuntimeException ex) {
            if (!(ex.getCause().getCause() instanceof IllegalElementNameException)) {
                throw ex;
            }
        }
    }

    @Test
    void testRioRDFXML() {
        try {
            roundTripOntology(noQNameRoundTripTestCase(), new RioRDFXMLDocumentFormat());
            fail("Expected an exception specifying that a QName could not be generated");
        } catch (OWLRuntimeException exc) {
            Throwable ex = exc.getCause();
            while (ex != null && !(ex instanceof RDFHandlerException)) {
                ex = ex.getCause();
            }
            if (ex == null || !ex.getMessage().contains("http://example.com/place/123")) {
                throw exc;
            }
        }
    }

    static List<OWLDocumentFormat> noQNameFormats() {
        return l(new RDFJsonDocumentFormat(), new OWLXMLDocumentFormat(),
            new FunctionalSyntaxDocumentFormat(), new TurtleDocumentFormat(),
            new RioTurtleDocumentFormat(), new ManchesterSyntaxDocumentFormat(),
            new TrigDocumentFormat(), new RDFJsonLDDocumentFormat(), new NTriplesDocumentFormat(),
            new NQuadsDocumentFormat());
    }

    @ParameterizedTest
    @MethodSource("noQNameFormats")
    void testFormat(OWLDocumentFormat format) {
        roundTripOntology(noQNameRoundTripTestCase(), format);
    }
}
