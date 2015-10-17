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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.PriorityCollection;

import com.google.common.collect.Sets;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class OWLOntologyFactoryImpl implements OWLOntologyFactory {

    private final Set<String> parsableSchemes = Sets.newHashSet("http", "https", "file", "ftp");
    private final OWLOntologyBuilder ontologyBuilder;

    /**
     * @param ontologyBuilder
     *        ontology builder
     */
    @Inject
    public OWLOntologyFactoryImpl(OWLOntologyBuilder ontologyBuilder) {
        this.ontologyBuilder = verifyNotNull(ontologyBuilder);
    }

    @Override
    public boolean canCreateFromDocumentIRI(IRI documentIRI) {
        return true;
    }

    @Override
    public boolean canAttemptLoading(OWLOntologyDocumentSource source) {
        return !source.hasAlredyFailedOnStreams() || !source.hasAlredyFailedOnIRIResolution()
            && parsableSchemes.contains(source.getDocumentIRI().getScheme());
    }

    @Override
    public OWLOntology createOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID, IRI documentIRI,
        OWLOntologyCreationHandler handler) {
        OWLOntology ont = ontologyBuilder.createOWLOntology(manager, ontologyID);
        handler.ontologyCreated(ont);
        handler.setOntologyFormat(ont, new RDFXMLDocumentFormat());
        return ont;
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
    private static PriorityCollection<OWLParserFactory> getParsers(OWLOntologyDocumentSource documentSource,
        PriorityCollection<OWLParserFactory> parsers) {
        if (parsers.isEmpty()) {
            return parsers;
        }
        Optional<OWLDocumentFormat> format = documentSource.getFormat();
        Optional<String> mimeType = documentSource.getMIMEType();
        if (!format.isPresent() && !mimeType.isPresent()) {
            return parsers;
        }
        PriorityCollection<OWLParserFactory> candidateParsers = parsers;
        if (format.isPresent()) {
            candidateParsers = getParsersByFormat(format.get(), parsers);
        }
        if (candidateParsers.isEmpty() && mimeType.isPresent()) {
            candidateParsers = getParserCandidatesByMIME(mimeType.get(), parsers);
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
    private static PriorityCollection<OWLParserFactory> getParsersByFormat(OWLDocumentFormat format,
        PriorityCollection<OWLParserFactory> parsers) {
        PriorityCollection<OWLParserFactory> candidateParsers = new PriorityCollection<>(
            PriorityCollectionSorting.NEVER);
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
    private static PriorityCollection<OWLParserFactory> getParserCandidatesByMIME(String mimeType,
        PriorityCollection<OWLParserFactory> parsers) {
        return parsers.getByMIMEType(mimeType);
    }

    @Override
    public OWLOntology loadOWLOntology(OWLOntologyManager manager, OWLOntologyDocumentSource documentSource,
        OWLOntologyCreationHandler handler, OWLOntologyLoaderConfiguration configuration)
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
        OWLOntology ont = createOWLOntology(manager, ontologyID, documentSource.getDocumentIRI(), handler);
        // Now parse the input into the empty ontology that we created
        // select a parser if the input source has format information and MIME
        // information
        PriorityCollection<OWLParserFactory> parsers = getParsers(documentSource, manager.getOntologyParsers());
        for (OWLParserFactory parserFactory : parsers) {
            OWLParser parser = parserFactory.createParser();
            try {
                if (existingOntology == null && !ont.isEmpty()) {
                    // Junk from a previous parse. We should clear the ont
                    manager.removeOntology(ont);
                    ont = createOWLOntology(manager, ontologyID, documentSource.getDocumentIRI(), handler);
                }
                OWLDocumentFormat format = parser.parse(documentSource, ont, configuration);
                handler.setOntologyFormat(ont, format);
                return ont;
            } catch (UnloadableImportException e) {
                // If an import cannot be located, all parsers will fail. Again,
                // terminate early
                // First clean up
                manager.removeOntology(ont);
                throw e;
            } catch (OWLParserException e) {
                if (e.getCause() instanceof IOException || e.getCause() instanceof OWLOntologyInputSourceException) {
                    // For input/output exceptions, we assume that it means the
                    // source cannot be read regardless of the parsers, so we
                    // stop
                    // early
                    // First clean up
                    manager.removeOntology(ont);
                    throw new OWLOntologyCreationIOException(e.getCause());
                }
                // Record this attempts and continue trying to parse.
                exceptions.put(parser, e);
            } catch (RuntimeException e) {
                // Clean up and rethrow
                exceptions.put(parser, new OWLParserException(e));
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
        throw new UnparsableOntologyException(documentSource.getDocumentIRI(), exceptions, configuration);
    }
}
