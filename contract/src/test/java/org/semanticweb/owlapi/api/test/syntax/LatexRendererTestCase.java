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
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.LatexDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class LatexRendererTestCase extends TestBase {

    @Test
    public void shouldRenderEscapingUnderscores() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF xmlns=\"http://namespace.owl#\"\n"
                        + "     xml:base=\"http://namespace.owl\"\n"
                        + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                        + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                        + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                        + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                        + "    <owl:Ontology rdf:about=\"http://namespace.owl\"/>\n"
                        + "    <owl:Class rdf:about=\"http://namespace.owl#C_Test\"/>"
                        + "<owl:ObjectProperty rdf:about=\"http://namespace.owl#p\"/>"
                        + "</rdf:RDF>";
        OWLOntology o = loadOntologyFromString(input, new LatexDocumentFormat());
        assertTrue(saveOntology(o, new LatexDocumentFormat()).toString().contains("C\\_Test"));
    }

    @Test
    public void shouldSaveInverses() throws OWLOntologyStorageException {
        String input = "@prefix : <http://www.semanticweb.org/jslob/ontologies/2014/9/untitled-ontology-61#> .\n"
                        + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                        + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                        + "@base <http://www.semanticweb.org/jslob/ontologies/2014/9/untitled-ontology-61> .\n\n"
                        + "<http://www.semanticweb.org/jslob/ontologies/2014/9/untitled-ontology-61> rdf:type owl:Ontology .\n\n"
                        + ":buzz_of rdf:type owl:ObjectProperty ;\n owl:inverseOf :fizzle_of .\n\n :fizzle_of rdf:type owl:ObjectProperty .";
        String expected = "\\documentclass{article}\n"
                        + "\\parskip 0pt\n\\parindent 0pt\n\\oddsidemargin 0cm\n\\textwidth 19cm\n\\begin{document}\n\n"
                        + "\\section*{Object properties}\\subsubsection*{buzz\\_of}\n\n"
            + "buzz\\_of~\\ensuremath{\\equiv}~fizzle\\_of\\ensuremath{^-}\n\n" + "\\subsubsection*{fizzle\\_of}\n\n"
                        + "buzz\\_of~\\ensuremath{\\equiv}~fizzle\\_of\\ensuremath{^-}\n\n"
                        + "\\section*{Data properties}\\section*{Individuals}\\section*{Datatypes}\\end{document}\n";
        OWLOntology o = loadOntologyFromString(input, new LatexDocumentFormat());
        assertEquals(expected, saveOntology(o, new LatexDocumentFormat()).toString());
    }
}
