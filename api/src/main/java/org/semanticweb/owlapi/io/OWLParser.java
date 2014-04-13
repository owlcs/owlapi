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
package org.semanticweb.owlapi.io;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * An {@code OWLParser} parses an ontology document and adds the axioms of the
 * parsed ontology to the {@code OWLOntology} object passed to the {@code parse}
 * methods. OWLParsers are typically used by {@link OWLOntologyManager
 * OWLOntologyManagers} to populate empty {@link OWLOntology OWLOntologies}, but
 * can be used to add axioms to an {@code OWLOntology} that already contains
 * axioms.<br>
 * One such case is parsing {@code owl:imports} which point to documents that
 * are not ontologies. In this case, any axioms parsed from the imported
 * document are added to the existing ontology, which already contains axioms
 * parsed from a different document.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLParser {

    /**
     * Parses the ontology with a concrete representation available at
     * {@code documentIRI} and adds its axioms to {@code ontology}. Implementors
     * of this method should load imported ontologies through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     * 
     * @param documentIRI
     *        the IRI of the document to parse
     * @param ontology
     *        the ontology to which the parsed axioms are added
     * @return the format of the parsed ontology
     * @throws OWLParserException
     *         if there was a parsing problem parsing the ontology.
     * @throws IOException
     *         if there was an IO problem during parsing.
     * @throws OWLOntologyChangeException
     *         if there was a problem updating {@code ontology}. Typically this
     *         depends on the document being parsed containing an ontology with
     *         an ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException
     *         if one or more imports could not be loaded.
     */
    @Nonnull
    OWLOntologyFormat parse(@Nonnull IRI documentIRI,
            @Nonnull OWLOntology ontology) throws IOException,
            OWLOntologyChangeException;

    /**
     * Parses the ontology with a concrete representation in
     * {@code documentSource} and adds its axioms to {@code ontology}.
     * Implementors of this method should load imported ontologies through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     * 
     * @param documentSource
     *        the source of a concrete representation of the document to parse.
     * @param ontology
     *        the ontology to which the parsed axioms are added
     * @return the format of the parsed ontology
     * @throws OWLParserException
     *         if there was a parsing problem parsing the ontology.
     * @throws IOException
     *         if there was an IO problem during parsing.
     * @throws OWLOntologyChangeException
     *         if there was a problem updating {@code ontology}. Typically this
     *         depends on the document being parsed containing an ontology with
     *         an ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException
     *         if one or more imports could not be loaded.
     */
    @Nonnull
    OWLOntologyFormat parse(@Nonnull OWLOntologyDocumentSource documentSource,
            @Nonnull OWLOntology ontology) throws IOException,
            OWLOntologyChangeException;

    /**
     * Parses the ontology with a concrete representation in
     * {@code documentSource} and adds its axioms to {@code ontology}.
     * Implementors of this method should load imported ontologies through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     * 
     * @param documentSource
     *        the source of a concrete representation of the document to parse
     * @param ontology
     *        the ontology to which the parsed axioms are added
     * @param configuration
     *        parsing options for the parser
     * @return the format of the parsed ontology
     * @throws OWLParserException
     *         if there was a parsing problem parsing the ontology.
     * @throws IOException
     *         if there was an IO problem during parsing.
     * @throws OWLOntologyChangeException
     *         if there was a problem updating {@code ontology}. Typically this
     *         depends on the document being parsed containing an ontology with
     *         an ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException
     *         if one or more imports could not be loaded.
     */
    @Nonnull
    OWLOntologyFormat parse(@Nonnull OWLOntologyDocumentSource documentSource,
            @Nonnull OWLOntology ontology,
            @Nonnull OWLOntologyLoaderConfiguration configuration)
            throws IOException, OWLOntologyChangeException;

    /** @return a name for the parser, typically the simple class name */
    @Nonnull
    String getName();

    /**
     * @return set of supported format factories
     */
    Set<OWLOntologyFormatFactory> getSupportedFormats();

    /**
     * @return set of supported format classes
     */
    Set<Class<OWLOntologyFormat>> getSupportedFormatClasses();
}
