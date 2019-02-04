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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
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

    // ongoing work to use OBO as one of the roundtripping formats
    // @Test
    // public void testOBO() throws Exception {
    // OWLOntology createOntology = createOntology();
    // createOntology.applyChange(new SetOntologyID(createOntology,
    // IRI.create("http://purl.obolibrary.org/obo/test.owl")));
    // StringDocumentTarget saveOntology =
    // saveOntology(createOntology, new FunctionalSyntaxDocumentFormat());
    // String s = saveOntology.toString()
    // //
    // .replace("http://www.semanticweb.org/owlapi/test#",
    // "http://purl.obolibrary.org/obo/test#")
    // //
    // .replace("http://www.semanticweb.org/ontologies/declarations#",
    // "http://purl.obolibrary.org/obo/test#")
    // //
    // .replace("http://www.semanticweb.org/ontologies/test/annotationont#",
    // "http://purl.obolibrary.org/obo/test#");
    // createOntology = loadOntologyFromString(s, new FunctionalSyntaxDocumentFormat());
    // createOntology.removeAxioms(asList(createOntology.axioms(AxiomType.CLASS_ASSERTION)
    // .filter(ax -> ax.getClassExpression().isOWLThing())));
    // OBODocumentFormat format = new OBODocumentFormat();
    // StringDocumentTarget target = saveOntology(createOntology, format);
    // OWLOntology o1 = loadOntologyFromString(target, format);
    // createOntology.removeAxioms(asList(createOntology.axioms(AxiomType.CLASS_ASSERTION).filter(
    // ax -> ax.getClassExpression().isOWLThing() || ax.getIndividual().isAnonymous())));
    // o1.removeAxioms(asList(o1.axioms(AxiomType.CLASS_ASSERTION).filter(
    // ax -> ax.getClassExpression().isOWLThing() || ax.getIndividual().isAnonymous())));
    // OWLAnnotationProperty version = df.getOWLAnnotationProperty(
    // "http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion");
    // OWLAnnotationProperty id =
    // df.getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#id");
    // createOntology.remove(asList(createOntology.axioms(AxiomType.ANNOTATION_ASSERTION)));
    // o1.remove(asList(o1.axioms(AxiomType.ANNOTATION_ASSERTION)));
    // o1.applyChanges(asList(o1.annotations().filter(a -> a.getProperty().equals(version))
    // .map(a -> new RemoveOntologyAnnotation(o1, a))));
    // createOntology.remove(asList(createOntology.axioms(AxiomType.DECLARATION)));
    // o1.remove(asList(o1.axioms(AxiomType.DECLARATION)));
    // System.out.println("TestBase.roundTripOntology() ont1 " + createOntology.getOntologyID());
    // createOntology.axioms().forEach(System.out::println);
    // System.out.println("TestBase.roundTripOntology() \n" + target);
    // System.out.println("TestBase.roundTripOntology() ont2 " + o1.getOntologyID());
    // o1.axioms().forEach(System.out::println);
    //
    // equal(createOntology, o1);
    // }

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
    public void roundTripRDFXMLAndFunctionalShouldBeSame()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ont = createOntology();
        OWLOntology o1 = roundTrip(ont, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(ont, new FunctionalSyntaxDocumentFormat());
        equal(ont, o1);
        equal(o1, o2);
    }
}
