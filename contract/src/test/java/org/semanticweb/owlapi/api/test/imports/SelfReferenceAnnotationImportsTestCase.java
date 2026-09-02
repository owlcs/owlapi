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
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * Regression tests for issue #1080: when an ontology declares {@code owl:imports}
 * and has a self-referencing annotation (an annotation whose IRI value equals the
 * ontology IRI, e.g. {@code rdfs:isDefinedBy → <ontology-iri>}), the RDF/XML parser
 * incorrectly assigns an imported ontology's IRI as the main ontology IRI.
 *
 * <p>Self-referencing annotations are valid and standard semantic web practice
 * (e.g. {@code rdfs:isDefinedBy}, {@code omv:URI}, {@code omv:resourceLocator}).
 * The bug is in {@code OWLRDFConsumer.chooseAndSetOntologyIRI()}, which removes
 * the ontology IRI from candidates when it appears as an annotation value, without
 * checking whether it is also the subject of an {@code owl:imports} triple.
 *
 * <p>These tests use {@code MissingImportHandlingStrategy.SILENT} to avoid
 * network access for imported ontologies. The imports are not needed for the
 * tests — only the {@code owl:imports} triple itself matters for the bug.
 *
 * @see <a href="https://github.com/owlcs/owlapi/issues/1080">Issue #1080</a>
 */
public class SelfReferenceAnnotationImportsTestCase extends TestBase {

    private static final IRI ONT_IRI = IRI.create("http://example.org/test-ontology");
    private static final IRI IMPORT_IRI = IRI.create("http://example.org/imported-ontology");

    private OWLOntology load(String rdfXml) throws OWLOntologyCreationException {
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration()
            .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        return m.loadOntologyFromOntologyDocument(
            new org.semanticweb.owlapi.io.StringDocumentSource(rdfXml), config);
    }

    /**
     * Core bug scenario: ontology with {@code owl:imports} and a self-referencing
     * {@code rdfs:isDefinedBy} annotation. The parser must preserve the declared
     * ontology IRI, not replace it with the imported ontology's IRI.
     */
    @Test
    public void testSelfReferenceWithImports() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <owl:imports rdf:resource=\"" + IMPORT_IRI + "\"/>"
            + "    <rdfs:isDefinedBy rdf:resource=\"" + ONT_IRI + "\"/>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
    }

    /**
     * Multiple self-referencing annotations ({@code rdfs:isDefinedBy},
     * {@code rdfs:seeAlso}, {@code omv:URI}) plus imports. The ontology IRI
     * must still be preserved.
     */
    @Test
    public void testMultipleSelfReferencesWithImports() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\""
            + "         xmlns:omv=\"http://omv.ontoware.org/2005/05/ontology#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <owl:imports rdf:resource=\"" + IMPORT_IRI + "\"/>"
            + "    <rdfs:isDefinedBy rdf:resource=\"" + ONT_IRI + "\"/>"
            + "    <rdfs:seeAlso rdf:resource=\"" + ONT_IRI + "\"/>"
            + "    <omv:URI rdf:resource=\"" + ONT_IRI + "\"/>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
    }

    /**
     * Ontology with imports but no self-referencing annotation. This should
     * work correctly regardless of the fix — no regression.
     */
    @Test
    public void testImportsWithoutSelfReference() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <owl:imports rdf:resource=\"" + IMPORT_IRI + "\"/>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
    }

    /**
     * Ontology with a self-referencing annotation but no imports. This should
     * work correctly regardless of the fix — no regression.
     */
    @Test
    public void testSelfReferenceWithoutImports() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <rdfs:isDefinedBy rdf:resource=\"" + ONT_IRI + "\"/>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
    }

    /**
     * Self-referencing annotation whose value is a literal (not an IRI).
     * This should not trigger the bug because the value is not an IRI.
     */
    @Test
    public void testSelfReferenceAsLiteral() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <owl:imports rdf:resource=\"" + IMPORT_IRI + "\"/>"
            + "    <rdfs:comment>" + ONT_IRI + "</rdfs:comment>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
    }

    /**
     * Annotation whose IRI value is different from the ontology IRI. This
     * should not affect the ontology IRI selection.
     */
    @Test
    public void testAnnotationWithDifferentIRI() throws OWLOntologyCreationException {
        IRI otherIRI = IRI.create("http://example.org/other");
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <owl:imports rdf:resource=\"" + IMPORT_IRI + "\"/>"
            + "    <rdfs:seeAlso rdf:resource=\"" + otherIRI + "\"/>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
    }

    /**
     * Version IRI present alongside self-referencing annotation and imports.
     * The ontology IRI must be preserved and the version IRI must remain intact.
     */
    @Test
    public void testVersionIRIPreservedWithSelfRef() throws OWLOntologyCreationException {
        IRI versionIRI = IRI.create("http://example.org/test-ontology/1.0");
        String rdf = "<?xml version=\"1.0\"?>"
            + "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">"
            + "  <rdf:Description rdf:about=\"" + ONT_IRI + "\">"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>"
            + "    <owl:versionIRI rdf:resource=\"" + versionIRI + "\"/>"
            + "    <owl:imports rdf:resource=\"" + IMPORT_IRI + "\"/>"
            + "    <rdfs:isDefinedBy rdf:resource=\"" + ONT_IRI + "\"/>"
            + "  </rdf:Description>"
            + "</rdf:RDF>";
        OWLOntology o = load(rdf);
        assertEquals(ONT_IRI, o.getOntologyID().getOntologyIRI().get());
        assertTrue(o.getOntologyID().getVersionIRI().isPresent());
        assertEquals(versionIRI, o.getOntologyID().getVersionIRI().get());
    }
}
