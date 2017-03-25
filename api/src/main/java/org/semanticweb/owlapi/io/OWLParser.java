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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * An {@code OWLParser} parses an ontology document and adds the axioms of the parsed ontology to
 * the {@code OWLOntology} object passed to the {@code parse} methods. <br>
 * {@code OWLParser} implementations are supposed to be stateless, and therefore immutable. By
 * default, implementation factories are injected in OWLOntologyManager instances by a Guice
 * injector at creation in OWLManager. This is not mandatory, and a specific manager can have
 * different implementations injected at any time after creation.<br>
 * OWLParsers are typically used by {@link OWLOntologyManager OWLOntologyManagers} to populate empty
 * {@link OWLOntology OWLOntologies}, but can be used to add axioms to an {@code OWLOntology} that
 * already contains axioms.<br>
 * One such case is parsing {@code owl:imports} which point to documents that are not ontologies. In
 * this case, any axioms parsed from the imported document are added to the existing ontology, which
 * already contains axioms parsed from a different document.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLParser extends Serializable {

    /**
     * Parses the ontology with a concrete representation available at {@code documentIRI} and adds
     * its axioms to {@code ontology}. Implementors of this method should load imported ontologies
     * through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     *
     * @param documentIRI the IRI of the document to parse
     * @param ontology the ontology to which the parsed axioms are added
     * @return the format of the parsed ontology
     * @throws OWLParserException if there was a parsing problem parsing the ontology.
     * @throws OWLOntologyChangeException if there was a problem updating {@code ontology}.
     *         Typically this depends on the document being parsed containing an ontology with an
     *         ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException if one or more imports could not be loaded.
     */
    default OWLDocumentFormat parse(IRI documentIRI, OWLOntology ontology) {
        return new IRIDocumentSource(documentIRI, null, null).acceptParser(this, ontology,
            ontology.getOWLOntologyManager().getOntologyConfigurator());
    }

    /**
     * @return a unique name for the parser, typically the simple class name
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * @return The supported format for this parser.
     */
    OWLDocumentFormatFactory getSupportedFormat();

    /**
     * Parses the ontology with a concrete representation in {@code in} and adds its axioms to
     * {@code ontology}. Implementors of this method should load imported ontologies through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     *
     * @param in the source of a concrete representation of the document to parse
     * @param parameters the combination of ontology, loading config, document IRI and other parser
     *        parameters
     * @return the format of the parsed ontology
     * @throws OWLParserException if there was a parsing problem parsing the ontology. @throws
     *         OWLOntologyChangeException if there was a problem updating {@code ontology}.
     *         Typically this depends on the document being parsed containing an ontology with an
     *         ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException if one or more imports could not be loaded.
     */
    default OWLDocumentFormat parse(String in, OWLParserParameters parameters) {
        return parse(new StringReader(in), parameters);
    }

    /**
     * Parses the ontology with a concrete representation in {@code r} and adds its axioms to {@code
     * ontology}. Implementors of this method should load imported ontologies through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     *
     * @param r the source of a concrete representation of the document to parse
     * @param parameters the combination of ontology, loading config, document IRI and other parser
     *        parameters
     * @return the format of the parsed ontology
     * @throws OWLParserException if there was a parsing problem parsing the ontology. @throws
     *         OWLOntologyChangeException if there was a problem updating {@code ontology}.
     *         Typically this depends on the document being parsed containing an ontology with an
     *         ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException if one or more imports could not be loaded.
     */
    OWLDocumentFormat parse(Reader r, OWLParserParameters parameters);

    /**
     * Parses the ontology with a concrete representation in {@code in} and adds its axioms to
     * {@code ontology}. Implementors of this method should load imported ontologies through
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration)
     * makeLoadImportRequest()}.
     *
     * @param in the source of a concrete representation of the document to parse
     * @param parameters the combination of ontology, loading config, document IRI and other parser
     *        parameters
     * @return the format of the parsed ontology
     * @throws OWLParserException if there was a parsing problem parsing the ontology. @throws
     *         OWLOntologyChangeException if there was a problem updating {@code ontology}.
     *         Typically this depends on the document being parsed containing an ontology with an
     *         ontology IRI clashing with one already loaded.
     * @throws UnloadableImportException if one or more imports could not be loaded.
     */
    default OWLDocumentFormat parse(InputStream in, OWLParserParameters parameters) {
        try (Reader r = new InputStreamReader(in, parameters.getEncoding())) {
            return parse(r, parameters);
        } catch (IOException e) {
            throw new OWLParserException(e.getMessage(), e, 1, 1);
        }
    }
}
