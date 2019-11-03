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
package org.semanticweb.owlapi6.apitest.baseclasses;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING1;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING2;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING3;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING4;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING5;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING6;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING7;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING8;
import static org.semanticweb.owlapi6.apitest.TestFiles.STRING9;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.3.0
 */
public class LiteralWithStringTypeTestCase extends AbstractRoundTrippingTestCase {

    @Override
    protected OWLOntology createOntology() {
        try {
            return m.createOntology(Stream.of(df.getOWLDataPropertyAssertionAxiom(
                df.getOWLDataProperty("http://owlapi.sourceforge.net/ontology#prop"),
                df.getOWLNamedIndividual("http://owlapi.sourceforge.net/ontology#A"), df.getOWLLiteral("test url"))));
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format)
        throws OWLOntologyStorageException, OWLOntologyCreationException {
        String string = saveOntology(ont, format).toString();
        assertTrue(format.getKey() + "\n" + string,
            format.getKey().equals("RDF/JSON") || !string.contains("^^xsd:string"));
        return super.roundTripOntology(ont, format);
    }

    @Test
    public void shouldParseInputWithoutExplicitString() throws OWLOntologyCreationException {
        for (String s : Arrays.asList(STRING1, STRING2, STRING3, STRING4, STRING5, STRING6, STRING7, STRING8,
            STRING9)) {
            assertTrue(loadOntologyFromString(new StringDocumentSource(s)).containsAxiom(df
                .getOWLDataPropertyAssertionAxiom(df.getOWLDataProperty("http://owlapi.sourceforge.net/ontology#prop"),
                    df.getOWLNamedIndividual("http://owlapi.sourceforge.net/ontology#A"),
                    df.getOWLLiteral("test url"))));
        }
    }
}
