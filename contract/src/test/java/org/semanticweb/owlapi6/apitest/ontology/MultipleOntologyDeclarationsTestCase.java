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
package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.io.UnparsableOntologyException;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class MultipleOntologyDeclarationsTestCase extends TestBase {

    @Test
    public void shouldLoadFirstOfMultipleOntologyDeclarationsRdfXml() throws Exception {
        OWLOntology o =
            m.loadOntologyFromOntologyDocument(new StringDocumentSource(TestFiles.doubleOntology,
                "urn:test:t1", new TurtleDocumentFormat(), null));
        assertEquals(o.getOntologyID(), df.getOWLOntologyID(df.getIRI("urn:test:Ontology1"),
            df.getIRI("urn:test:Ontology1Version1")));
    }

    @Test(expected = UnparsableOntologyException.class)
    public void shouldNotLoadMultipleOntologyDeclarationsRioRdfXml() throws Exception {
        m.loadOntologyFromOntologyDocument(new StringDocumentSource(TestFiles.doubleOntology,
            "urn:test:t1", new RioTurtleDocumentFormat(), null),
            new OntologyConfigurator().setStrict(true));
    }
}
