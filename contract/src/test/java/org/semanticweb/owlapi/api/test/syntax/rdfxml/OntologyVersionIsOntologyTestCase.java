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
package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;

public class OntologyVersionIsOntologyTestCase extends TestBase {

    @Test
    public void testLoad() throws Exception {
        String in =
            "<https://mobi.com/ontologies/12/2018/VersionIsItself> a <http://www.w3.org/2002/07/owl#Ontology>;\n"
                + "  <http://www.w3.org/2002/07/owl#versionIRI> <https://mobi.com/ontologies/12/2018/VersionIsItself> ;\n"
                + "  <http://purl.org/dc/terms/title> \"versionIsItself\" .";
        OWLOntology o = loadOntologyFromString(in, new RioTurtleDocumentFormat());
        StringDocumentTarget saveOntology = saveOntology(o, new RioTurtleDocumentFormat());
        OWLOntology o1 =
            loadOntologyFromString(saveOntology.toString(), new RioTurtleDocumentFormat());
        equal(o, o1);
    }
}
