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
package uk.ac.manchester.cs.owl.owlapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.PriorityCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ontology factory that creates ontologies by parsing documents containing
 * concrete representations of ontologies. This ontology factory will claim that
 * it is suitable for creating an ontology if the document IRI can be opened for
 * reading. This factory will not create empty ontologies. Parsers are
 * instantiated by using a list of {@code OWLParserFactory} objects that are
 * obtained from the {@code OWLParserFactoryRegistry}.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class ParsableOWLOntologyFactory extends AbstractInMemOWLOntologyFactory {

    private static final long serialVersionUID = 40000L;
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ParsableOWLOntologyFactory.class);
    private final Set<String> parsableSchemes = new HashSet<>(Arrays.asList(
            "http", "https", "file", "ftp"));

    /**
     * @param builder
     *        injected ontology builder
     */
    @Inject
    public ParsableOWLOntologyFactory(@Nonnull OWLOntologyBuilder builder) {
        super(builder);
    }

    /**
     * Overriden - We don't create new empty ontologies - this isn't our
     * responsibility.
     * 
     * @param documentIRI
     *        ignored
     * @return false
     */
    @Override
    public boolean canCreateFromDocumentIRI(IRI documentIRI) {
        return false;
    }

    @Override
    public boolean canLoad(@Nonnull OWLOntologyDocumentSource documentSource) {
        if (documentSource.isReaderAvailable()) {
            return true;
        }
        if (documentSource.isInputStreamAvailable()) {
            return true;
        }
        if (parsableSchemes.contains(documentSource.getDocumentIRI()
                .getScheme())) {
            return true;
        }
        // If we can open an input stream then we can attempt to parse the
        // ontology
        // TODO: Take into consideration the request type!
        try {
            documentSource.getDocumentIRI().toURI().toURL().openConnection();
            return true;
        } catch (UnknownHostException e) {
            LOGGER.info("Unknown host: {}", e.getMessage(), e);
        } catch (MalformedURLException e) {
            LOGGER.info("Malformed URL: {}", e.getMessage(), e);
        } catch (FileNotFoundException e) {
            LOGGER.info("File not found: {}", e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.info("IO Exception: {}", e.getMessage(), e);
        }
        return false;
    }

    @Override
    public OWLOntology loadOWLOntology(OWLOntologyManager manager,
            OWLOntologyDocumentSource documentSource,
            OWLOntologyCreationHandler handler,
            OWLOntologyLoaderConfiguration configuration)
            throws OWLOntologyCreationException {
        // Attempt to parse the ontology by looping through the parsers. If the
        // ontology is parsed successfully then we break out and return the
        // ontology.
        // I think that this is more reliable than selecting a parser based on a
        // file extension
        // for example (perhaps the parser list could be ordered based on most
        // likely parser, which
        // could be determined by file extension).
        Map<OWLParser, OWLParserException> exceptions = new LinkedHashMap<>();
        // Call the super method to create the ontology - this is needed,
        // because
        // we throw an exception if someone tries to create an ontology directly
        OWLOntology existingOntology = null;
        IRI iri = documentSource.getDocumentIRI();
        if (manager.contains(iri)) {
            existingOntology = manager.getOntology(iri);
        }
        OWLOntologyID ontologyID = new OWLOntologyID();
        OWLOntology ont = createOWLOntology(manager, ontologyID,
                documentSource.getDocumentIRI(), handler);
        // Now parse the input into the empty ontology that we created
        // select a parser if the input source has format information and MIME
        // information
        PriorityCollection<OWLParserFactory> parsers = getParsers(documentSource,
                manager.getOntologyParsers());
        for (OWLParserFactory parserFactory : parsers) {
            OWLParser parser = parserFactory.createParser();
            try {
                if (existingOntology == null && !ont.isEmpty()) {
                    // Junk from a previous parse. We should clear the ont
                    manager.removeOntology(ont);
                    ont = createOWLOntology(manager, ontologyID,
                            documentSource.getDocumentIRI(), handler);
                }
                OWLDocumentFormat format = parser.parse(documentSource, ont,
                        configuration);
                handler.setOntologyFormat(ont, format);
                return ont;
            } catch (IOException e) {
                // No hope of any parsers working?
                // First clean up
                manager.removeOntology(ont);
                throw new OWLOntologyCreationIOException(e);
            } catch (UnloadableImportException e) {
                // First clean up
                manager.removeOntology(ont);
                throw e;
            } catch (OWLParserException e) {
                // Record this attempts and continue trying to parse.
                exceptions.put(parser, e);
            } catch (RuntimeException e) {
                // Clean up and rethrow
                manager.removeOntology(ont);
                throw e;
            }
        }
        if (existingOntology == null) {
            manager.removeOntology(ont);
        }
        // We haven't found a parser that could parse the ontology properly.
        // Throw an
        // exception whose message contains the stack traces from all of the
        // parsers
        // that we have tried.
        throw new UnparsableOntologyException(documentSource.getDocumentIRI(),
                exceptions, configuration);
    }

    /**
     * Select parsers by MIME type and format of the input source, if known. If
     * format and MIME type are not known or not matched by any parser, return
     * all known parsers.
     * 
     * @param documentSource
     *        document source
     * @param parsers
     *        parsers
     * @return selected parsers
     */
    private static PriorityCollection<OWLParserFactory> getParsers(
            OWLOntologyDocumentSource documentSource,
            PriorityCollection<OWLParserFactory> parsers) {
        if (parsers.isEmpty()) {
            return parsers;
        }
        if (!documentSource.isFormatKnown()
                && !documentSource.isMIMETypeKnown()) {
            return parsers;
        }
        PriorityCollection<OWLParserFactory> candidateParsers = parsers;
        if (documentSource.isFormatKnown()) {
            OWLDocumentFormat format = documentSource.getFormat();
            assert format != null;
            candidateParsers = getParsersByFormat(format, parsers);
        }
        if (candidateParsers.isEmpty() && documentSource.isMIMETypeKnown()) {
            String mimeType = documentSource.getMIMEType();
            assert mimeType != null;
            candidateParsers = getParserCandidatesByMIME(mimeType, parsers);
        }
        if (candidateParsers.isEmpty()) {
            return parsers;
        }
        return candidateParsers;
    }

    /**
     * Use the format to select a sublist of parsers.
     * 
     * @param format
     *        document format
     * @param parsers
     *        parsers
     * @return candidate parsers
     */
    private static PriorityCollection<OWLParserFactory> getParsersByFormat(
            @Nonnull OWLDocumentFormat format,
            PriorityCollection<OWLParserFactory> parsers) {
        PriorityCollection<OWLParserFactory> candidateParsers = new PriorityCollection<>();
        for (OWLParserFactory parser : parsers) {
            if (parser.getSupportedFormat().getKey().equals(format.getKey())) {
                candidateParsers.add(parser);
            }
        }
        return candidateParsers;
    }

    /**
     * Use the MIME type it to select a sublist of parsers.
     * 
     * @param mimeType
     *        MIME type
     * @param parsers
     *        parsers
     * @return candidate parsers
     */
    private static PriorityCollection<OWLParserFactory> getParserCandidatesByMIME(
            @Nonnull String mimeType, PriorityCollection<OWLParserFactory> parsers) {
        PriorityCollection<OWLParserFactory> candidateParsers = parsers
                .getByMIMEType(mimeType);
        return candidateParsers;
    }
}
