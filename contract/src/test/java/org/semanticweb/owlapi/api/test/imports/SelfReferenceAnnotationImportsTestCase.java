/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Test for bug #1080: when an ontology declares owl:imports and has a
 * self-referencing annotation (an annotation whose object IRI equals the
 * ontology IRI), OWL API incorrectly assigns an imported ontology's IRI
 * as the main ontology IRI.
 *
 * @author Ibrahim Mohammad
 */
@SuppressWarnings("javadoc")
public class SelfReferenceAnnotationImportsTestCase extends TestBase {

    private static final String ONTOLOGY_IRI = "http://example.org/test-ontology";
    private static final String IMPORTED_IRI = "http://example.org/imported-ontology";

    /**
     * An ontology with owl:imports and rdfs:isDefinedBy pointing to itself
     * should retain its own IRI as the ontology IRI.
     */
    @Test
    public void testSelfReferenceWithImports() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"" + ONTOLOGY_IRI + "\">\n"
            + "        <owl:imports rdf:resource=\"" + IMPORTED_IRI + "\"/>\n"
            + "        <rdfs:isDefinedBy rdf:resource=\"" + ONTOLOGY_IRI + "\"/>\n"
            + "    </owl:Ontology>\n"
            + "</rdf:RDF>\n";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.getOntologyConfigurator().setMissingImportHandlingStrategy(
            org.semanticweb.owlapi.model.MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(rdf));

        assertTrue("Ontology IRI should be present",
            ontology.getOntologyID().getOntologyIRI().isPresent());
        assertEquals("Ontology IRI should be the declared IRI, not an imported one",
            ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get().toString());
    }

    /**
     * An ontology with owl:imports and multiple self-referencing annotations
     * (rdfs:isDefinedBy, dcterms:identifier, schema:url) should retain its
     * own IRI as the ontology IRI.
     */
    @Test
    public void testMultipleSelfReferencesWithImports() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "         xmlns:dcterms=\"http://purl.org/dc/terms/\"\n"
            + "         xmlns:schema=\"http://schema.org/\">\n"
            + "    <owl:Ontology rdf:about=\"" + ONTOLOGY_IRI + "\">\n"
            + "        <owl:imports rdf:resource=\"" + IMPORTED_IRI + "\"/>\n"
            + "        <rdfs:isDefinedBy rdf:resource=\"" + ONTOLOGY_IRI + "\"/>\n"
            + "        <dcterms:identifier rdf:resource=\"" + ONTOLOGY_IRI + "\"/>\n"
            + "        <schema:url rdf:resource=\"" + ONTOLOGY_IRI + "\"/>\n"
            + "    </owl:Ontology>\n"
            + "</rdf:RDF>\n";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.getOntologyConfigurator().setMissingImportHandlingStrategy(
            org.semanticweb.owlapi.model.MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(rdf));

        assertEquals("Ontology IRI should be the declared IRI despite multiple self-refs",
            ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get().toString());
    }

    /**
     * An ontology with owl:imports but NO self-referencing annotation should
     * work correctly (regression test — the fix must not break normal cases).
     */
    @Test
    public void testImportsWithoutSelfReference() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"" + ONTOLOGY_IRI + "\">\n"
            + "        <owl:imports rdf:resource=\"" + IMPORTED_IRI + "\"/>\n"
            + "        <rdfs:comment>Test ontology</rdfs:comment>\n"
            + "    </owl:Ontology>\n"
            + "</rdf:RDF>\n";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.getOntologyConfigurator().setMissingImportHandlingStrategy(
            org.semanticweb.owlapi.model.MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(rdf));

        assertEquals("Ontology IRI should be correct without self-refs",
            ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get().toString());
    }

    /**
     * An ontology with a self-referencing annotation but NO owl:imports should
     * work correctly (regression test — the bug only manifests with imports).
     */
    @Test
    public void testSelfReferenceWithoutImports() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"" + ONTOLOGY_IRI + "\">\n"
            + "        <rdfs:isDefinedBy rdf:resource=\"" + ONTOLOGY_IRI + "\"/>\n"
            + "    </owl:Ontology>\n"
            + "</rdf:RDF>\n";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(rdf));

        assertEquals("Ontology IRI should be correct without imports",
            ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get().toString());
    }

    /**
     * An annotation whose value is a DIFFERENT IRI (not the ontology IRI)
     * should not cause any issue.
     */
    @Test
    public void testAnnotationWithDifferentIRI() throws OWLOntologyCreationException {
        String otherIri = "http://example.org/other";
        String rdf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"" + ONTOLOGY_IRI + "\">\n"
            + "        <owl:imports rdf:resource=\"" + IMPORTED_IRI + "\"/>\n"
            + "        <rdfs:isDefinedBy rdf:resource=\"" + otherIri + "\"/>\n"
            + "    </owl:Ontology>\n"
            + "</rdf:RDF>\n";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.getOntologyConfigurator().setMissingImportHandlingStrategy(
            org.semanticweb.owlapi.model.MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(rdf));

        assertEquals("Ontology IRI should be correct when annotation points elsewhere",
            ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get().toString());
    }

    /**
     * A literal annotation whose value equals the ontology IRI (as a string)
     * should not trigger the bug.
     */
    @Test
    public void testSelfReferenceAsLiteral() throws OWLOntologyCreationException {
        String rdf = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"" + ONTOLOGY_IRI + "\">\n"
            + "        <owl:imports rdf:resource=\"" + IMPORTED_IRI + "\"/>\n"
            + "        <rdfs:isDefinedBy>" + ONTOLOGY_IRI + "</rdfs:isDefinedBy>\n"
            + "    </owl:Ontology>\n"
            + "</rdf:RDF>\n";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.getOntologyConfigurator().setMissingImportHandlingStrategy(
            org.semanticweb.owlapi.model.MissingImportHandlingStrategy.SILENT);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(rdf));

        assertEquals("Ontology IRI should be correct with literal self-reference",
            ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get().toString());
    }
}
